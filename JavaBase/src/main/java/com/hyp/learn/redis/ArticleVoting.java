package com.hyp.learn.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ZParams;

import java.util.*;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.redis
 * hyp create at 19-11-23
 **/
public class ArticleVoting {
    private static final int ONE_WEEK_IN_SECONDS = 7 * 86400;          //文章发布期满一周后，用户不能在对它投票。
    private static final int VOTE_SCORE = 432;                        //计算评分时间与支持票数相乘的常量，通过将一天的秒数除(86400)以文章展示一天所需的支持票数(200)得出的
    private static final int ARTICLES_PER_PAGE = 25;

    public static final void main(String[] args) {
        new ArticleVoting().run();
    }

    /**
     * 发布并获取文章
     * 1、发布一篇新文章需要创建一个新的文章id，可以通过对一个计数器(count)执行INCY命令来完成。
     * 2、程序需要使用SADD将文章发布者的ID添加到记录文章已投票用户名单的集合中，
     * 并使用EXPIRE命令为这个集合设置一个过期时间，让Redis在文章发布期满一周之后自动删除这个集合。
     * 3、之后程序会使用HMSET命令来存储文章的相关信息，并执行两个ZADD，将文章的初始评分和发布时间分别添加到两个相应的有序集合里面。
     */
    public String postArticle(Jedis conn, String user, String title, String link) {
        //1、生成一个新的文章ID
        String articleId = String.valueOf(conn.incr("article:"));     //String.valueOf(int i) : 将 int 变量 i 转换成字符串

        String voted = "voted:" + articleId;
        //2、添加到记录文章已投用户名单中，
        conn.sadd(voted, user);
        //3、设置一周为过期时间
        conn.expire(voted, ONE_WEEK_IN_SECONDS);

        long now = System.currentTimeMillis() / 1000;
        String article = "article:" + articleId;
        //4、创建一个HashMap容器。
        HashMap<String, String> articleData = new HashMap<String, String>();
        articleData.put("title", title);
        articleData.put("link", link);
        articleData.put("user", user);
        articleData.put("now", String.valueOf(now));
        articleData.put("oppose", "0");
        articleData.put("votes", "1");
        //5、将文章信息存储到一个散列里面。
        //HMSET key field value [field value ...]
        //同时将多个 field-value (域-值)对设置到哈希表 key 中。
        //此命令会覆盖哈希表中已存在的域。
        conn.hmset(article, articleData);
        //6、将文章添加到更具评分排序的有序集合中
        //ZADD key score member [[score member] [score member] ...]
        //将一个或多个 member 元素及其 score 值加入到有序集 key 当中
        conn.zadd("score:", now + VOTE_SCORE, article);
        //7、将文章添加到更具发布时间排序的有序集合。
        conn.zadd("time:", now, article);

        return articleId;
    }

    /**
     * 对文章进行投票
     * 实现投票系统的步骤：
     * 1、当用户尝试对一篇文章进行投票时，程序要使用ZSCORE命令检查记录文字发布时间的有序集合，判断文章的发布时间是否超过一周。
     * 2、如果文章仍然处于可投票的时间范围之内，那么程序将使用SADD命令，尝试将用户添加到记录文章的已投票用户名单的集合中。
     * 3、如果投票执行成功的话，那么说明用户是第一次对这篇文章进行投票，程序将使用ZINCYBY命令为文章的评分增加432(ZINCYBY命令用于对有序集合成员的分值进行自增操作)，
     * 并使用HINCRBY命令对散列记录的文章投票数量进行更新(HINCRBY命令用于对散列存储的值执行自增操作)
     */
    public void articleVote(Jedis conn, String user, String article) {
        //1、计算文章投票截止时间。
        long cutoff = (System.currentTimeMillis() / 1000) - ONE_WEEK_IN_SECONDS;
        //2、检查是否还可以对文章进行投票，(虽然使用散列也可以获取文章的发布时间，但有序集合返回文章发布时间为浮点数，可以不进行转换，直接使用)
        if (conn.zscore("time:", article) < cutoff) {
            return;
        }

        //3、从articleId标识符里面取出文章ID。
        //nt indexOf(int ch,int fromIndex)函数：就是字符ch在字串fromindex位后出现的第一个位置.没有找到返加-1
        //String.Substring (Int32)    从此实例检索子字符串。子字符串从指定的字符位置开始。
        String articleId = article.substring(article.indexOf(':') + 1);
        //4、检查用户是否第一次为这篇文章投票，如果是第一次，则在增加这篇文章的投票数量和评分。
        if (conn.sadd("voted:" + articleId, user) == 1) {                       //将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
            //为有序集 key 的成员 member 的 score 值加上增量 increment 。
            //可以通过传递一个负数值 increment ，让 score 减去相应的值，
            //当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member 。
            //ZINCRBY salary 2000 tom   # tom 加薪啦！
            conn.zincrby("score:", VOTE_SCORE, article);

            //为哈希表 key 中的域 field 的值加上增量 increment 。
            //增量也可以为负数，相当于对给定域进行减法操作。
            //HINCRBY counter page_view 200
            conn.hincrBy(article, "votes", 1L);
        }
    }

    /**
     * 投反对票
     */
    public void articleOppose(Jedis conn, String user, String article) {

        long cutoff = (System.currentTimeMillis() / 1000) - ONE_WEEK_IN_SECONDS;

        //cutoff之前的发布的文章 就不能再投票了
        if (conn.zscore("time:", article) < cutoff) {
            return;
        }


        String articleId = article.substring(article.indexOf(':') + 1);

        //查看user是否给这篇文章投过票
        //set里面的key是唯一的 如果 sadd返回0 表示set里已经有数据了
        //如果返回1表示还没有这个数据
        if (conn.sadd("oppose:" + articleId, user) == 1) {
            conn.zincrby("score:", -VOTE_SCORE, article);
            conn.hincrBy(article, "votes", -1L);
        }
    }

    public List<Map<String, String>> getArticles(Jedis conn, int page) {
        //调用下面重载的方法
        return getArticles(conn, page, "score:");
    }

    /**
     * 取出评分最高的文章和取出最新发布的文章
     * 实现步骤：
     * 1、程序需要先使用ZREVRANGE取出多个文章ID，然后在对每个文章ID执行一次HGETALL命令来取出文章的详细信息，
     * 这个方法可以用于取出评分最高的文章，又可以用于取出最新发布的文章。
     * 需要注意的是：
     * 因为有序集合会根据成员的值从小到大排列元素，所以使用ZREVRANGE命令，已分值从大到小的排列顺序取出文章ID才是正确的做法
     */

    public List<Map<String, String>> getArticles(Jedis conn, int page, String order) {
        //1、设置获取文章的起始索引和结束索引。
        int start = (page - 1) * ARTICLES_PER_PAGE;
        int end = start + ARTICLES_PER_PAGE - 1;
        //2、获取多个文章ID,
        Set<String> ids = conn.zrevrange(order, start, end);
        List<Map<String, String>> articles = new ArrayList<Map<String, String>>();
        for (String id : ids) {
            //3、根据文章ID获取文章的详细信息
            Map<String, String> articleData = conn.hgetAll(id);
            articleData.put("id", id);
            //4、添加到ArrayList容器中
            articles.add(articleData);
        }

        return articles;
    }

    /**
     * 记录文章属于哪个群组
     * 将所属一个群组的文章ID记录到那个集合中
     * Redis不仅可以对多个集合执行操作，甚至在一些情况下，还可以在集合和有序集合之间执行操作
     */
    public void addGroups(Jedis conn, String articleId, String[] toAdd) {
        //1、构建存储文章信息的键名
        String article = "article:" + articleId;
        for (String group : toAdd) {
            //2、将文章添加到它所属的群组里面
            conn.sadd("group:" + group, article);
        }
    }

    public List<Map<String, String>> getGroupArticles(Jedis conn, String group, int page) {
        //调用下面重载的方法
        return getGroupArticles(conn, group, page, "score:");
    }

    /**
     * 取出群组里面的文章
     * 为了能够根据评分对群组文章进行排序和分页，网站需要将同一个群组里面的所有文章都按评分有序的存储到一个有序集合内，
     * 程序需要使用ZINTERSTORE命令选出相同成员中最大的那个分支作为交集成员的值：取决于所使用的排序选项，可以是评分或文章发布时间。
     */
    public List<Map<String, String>> getGroupArticles(Jedis conn, String group, int page, String order) {
        //1、为每个群组的每种排列顺序都创建一个键。
        String key = order + group;
        //2、检查是否有已缓存的排序结果，如果没有则进行排序。
        if (!conn.exists(key)) {
            //3、根据评分或者发布时间对群组文章进行排序
            ZParams params = new ZParams().aggregate(ZParams.Aggregate.MAX);
            conn.zinterstore(key, params, "group:" + group, order);
            //让Redis在60秒之后自动删除这个有序集合
            conn.expire(key, 60);
        }
        //4、调用之前定义的getArticles()来进行分页并获取文章数据
        return getArticles(conn, page, key);
    }

    public void run() {
        //1、初始化redis连接
        Jedis conn = new Jedis("localhost");
        conn.select(15);

        //2、发布文章
        String articleId = postArticle(
                conn, "pingxin", "A title", "https://pingxin0521.coding.me/");
        System.out.println("我发布了一篇文章，id为：: " + articleId);
        System.out.println("文章保存的散列格式如下：");
        Map<String, String> articleData = conn.hgetAll("article:" + articleId);
        for (Map.Entry<String, String> entry : articleData.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println();
        //2、测试文章的投票过程
        articleVote(conn, "other_user", "article:" + articleId);
        String votes = conn.hget("article:" + articleId, "votes");
        System.out.println("我们为该文章投票，目前该文章的票数 " + votes);
        assert Integer.parseInt(votes) > 1;
        //3、测试文章的投票过程
        articleOppose(conn, "other_user", "article:" + articleId);
        String oppose = conn.hget("article:" + articleId, "votes");
        System.out.println("我们为该文章投反对票，目前该文章的反对票数 " + oppose);
        assert Integer.parseInt(oppose) > 1;

        System.out.println("当前得分最高的文章是：");
        List<Map<String, String>> articles = getArticles(conn, 1);
        printArticles(articles);
        assert articles.size() >= 1;

        addGroups(conn, articleId, new String[]{"new-group"});
        System.out.println("我们将文章推到新的群组，其他文章包括：");
        articles = getGroupArticles(conn, "new-group", 1);
        printArticles(articles);
        assert articles.size() >= 1;
    }

    private void printArticles(List<Map<String, String>> articles) {
        if (null != articles) {
            articles.stream().forEach(
                    System.out::println
            );
        }
    }

}
