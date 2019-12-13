package com.hyp.learn.db.dao02;

import com.hyp.learn.db.utils.DruidUtils;
import com.hyp.learn.db.utils.MStringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 通过反射进行操作
 *
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.db.dao02
 * hyp create at 19-12-11
 **/
public class BaseDaoImpl implements BaseDao {
    private Connection conn = null;
    private PreparedStatement ps;
    private ResultSet rs;

    public BaseDaoImpl() {
        try {
            this.conn = DruidUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入sql语句
     *
     * @param sql
     * @param obj java bean实体对象
     * @return
     */
    @Override
    public int add(String sql, Object obj) {
        int flag = 0;
        //正则匹配String sql="insert into t_comment(article_id,nickname,content,time,star,diss) values(?,?,?,?,?,?)"; 语句中的括号内的所有参数
        Matcher m = Pattern.compile("\\(.+?\\)").matcher(sql);
        String params = null;
        if (m.find()) {//仅返回插入参数
            System.out.println(m.group());
            params = m.group();
        }
        params = params.substring(1, params.length() - 1);
        System.out.println("params:" + params);
        //(article_id,nickname,content,time,star,diss)通过逗号分割获取参数数组
        String[] arr = params.split(",");

        //通过反射获取参数obj （javabean）实体的所有get方法,方便获取对应参数的值
        Class clazz = obj.getClass();
        Method[] methods = clazz.getMethods();
        List methodsList = Arrays.asList(methods);
        //获取实体类的所有get方法,
        methodsList = (List) methodsList.stream().filter(e -> e.toString().contains("get")).collect(Collectors.toList());
        methodsList.forEach(m1 -> System.out.println(m1));
        //创建与输入参数map存放instance中的具体参数
        Map<String, Object[]> preParams = new HashMap<>();
        try {
            //获取传入对象instance的具体值
            for (int i = 0; i < arr.length; i++) {
                String columnStr = MStringUtil.toUpperCaseFirstOne(arr[i]);
                for (Object o : methodsList) {
                    if (o.toString().contains(columnStr)) {//数据库表中字段名跟javabean中get方法要一一对应方便调用，如：comment.getContent();
                        Object tempResult = ((Method) o).invoke(obj);
                        System.out.println("反射调用get：" + tempResult);
                        if (null != tempResult) {
                            //index:0 存放对应数据库字段名的值，1存放该字段在参数中的索引
                            //然后put进入map中
                            Object[] tem = new Object[2];
                            tem[0] = tempResult;
                            tem[1] = i + 1;
                            preParams.put(arr[i], tem);
                        }
                    }
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (preParams != null) {
            try {
                ps = conn.prepareStatement(sql);
                int count = ps.getParameterMetaData().getParameterCount();
                for (int i = 0; i < count; i++) {
                    if (preParams.size() > 0) {
                        //遍历map中sql的参数 和 值，然后ps.setObject()
                        for (Map.Entry<String, Object[]> entry : preParams.entrySet()) {
                            System.out.println("entry:" + entry.getKey() + "    entryvalue:" + entry.getValue());
                            if ((Integer) entry.getValue()[1] == i + 1) {
                                ps.setObject((Integer) entry.getValue()[1], entry.getValue()[0]);
                                break;
                            } else {
                                ps.setObject(i + 1, "");
                            }
                        }

                    }
                }
                System.out.println("ps toString:" + ps.toString());
                flag = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn, ps, null);
            }
        }
        return flag;
    }

    @Override
    public void delete(String sql, int[] ids) {
        int[] flag;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            if (ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    ps.setInt(1, ids[i]);
                    ps.addBatch();
                }
            }
            flag = ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, ps, null);
        }

    }


    @Override
    public int update(String sql, Object[] params) {
        int flag = 0;
        try {
            ps = conn.prepareStatement(sql);
            int count = ps.getParameterMetaData().getParameterCount();
            if (params != null && params.length > 0) {
                for (int i = 0; i < count; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            flag = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, ps, null);
        }

        return flag;
    }

    @Override
    public int update(String sql, Object obj) {
        int flag = 0;             //执行更新操作后的返回的影响行数
        //获取sql语句中的参数
        String[] paramsArr = MStringUtil.getParamsArr(sql);
        //存放paramsArr中参数对应的值，通过反射代码获取
        Object[] paramsValue = new Object[paramsArr.length];
        int paramValuIndex = 0;
        Class clazz = obj.getClass();
        Method[] methods = clazz.getMethods();
        methods = Arrays.stream(methods).filter(method -> method.toString().contains("get")).toArray(Method[]::new);
        for (int i = 0; i < paramsArr.length; i++) {
            for (Method method : methods) {
                //将参数的首字母转换成大写：user->User ,
                String methodName = MStringUtil.toUpperCaseFirstOne(paramsArr[i].toLowerCase());
                //判断javabean中user.getUser() 是否存在“User”字符串,如果存在则调用getUser方法获取对象中的参数值
                if (method.toString().contains(methodName)) {
                    try {
                        Object result = method.invoke(obj);
                        //将参数值存入数组中
                        paramsValue[paramValuIndex] = result;
                        paramValuIndex++;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        if (paramsValue != null) {
            flag = this.update(sql, paramsValue);
        }

        return flag;
    }

    @Override
    public <T> T query(String sql, Object[] params, Class<T> clazz) {
        T t = null;
        try {
            ps = conn.prepareStatement(sql);
            int count = ps.getParameterMetaData().getParameterCount();
            if (params != null && params.length > 0) {
                for (int i = 0; i < count; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int countMetaData = rsMetaData.getColumnCount();

            if (rs.next()) {
                t = clazz.newInstance();
                for (int i = 0; i < countMetaData; i++) {
                    String columnName = rsMetaData.getColumnName(i + 1);
                    Object columnValue = rs.getObject(columnName);
                    //BeanUtils.setProperty(t,columnName,columnValue);
                    BeanUtils.copyProperty(t, columnName, columnValue);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, ps, rs);

        }
        return t;
    }

    @Override
    public <T> List<T> queryAll(String sql, Object[] params, Class<T> clazz) {
        T t = null;
        List<T> listT = new ArrayList<T>();
        try {
            ps = conn.prepareStatement(sql);
            int count = ps.getParameterMetaData().getParameterCount();
            if (params != null && params.length > 0) {
                for (int i = 0; i < count; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int countMetaData = rsMetaData.getColumnCount();

            while (rs.next()) {
                t = clazz.newInstance();
                for (int i = 0; i < countMetaData; i++) {
                    String columnName = rsMetaData.getColumnName(i + 1);
                    Object columnValue = rs.getObject(columnName);
                    //BeanUtils.setProperty(t,columnName,columnValue);
                    BeanUtils.copyProperty(t, columnName, columnValue);
                }
                listT.add(t);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, ps, rs);

        }
        return listT;
    }

}
