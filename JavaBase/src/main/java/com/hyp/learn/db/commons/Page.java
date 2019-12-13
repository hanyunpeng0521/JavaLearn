package com.hyp.learn.db.commons;

import java.util.List;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.db
 * hyp create at 19-12-11
 **/
public class Page {
    private int pageSize = 10;  //每页显示条数
    private Long totalCount;//数据总数
    private int pageNo;//当前页码
    private int start;//记录开始数
    private int totalPages;  //页码总数
    private List dates;//分页记录集

    public Page() {
    }


    public static Page of(Long totalCount, int pageNo, int pageSize, List dates) {
        return new Page(totalCount, pageNo, pageSize, dates);
    }


    public Page(Long totalCount, int pageNo, int pageSize, List dates) {//数据总数,当前页码,每页显示条数，分页记录集
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.dates = dates;
        if (this.totalCount % this.pageSize == 0) {
            this.totalPages = (int) (this.totalCount / this.pageSize);
        } else {
            this.totalPages = (int) (this.totalCount / this.pageSize) + 1;
        }
    }

    public boolean hasFirst() {  //有首页
        return getPageNo() > 1;                //当前页面不是首页就有首页
    }

    public boolean hasLast() {  //有尾页
        return getPageNo() < getTotalPages();                //当前页面小于最大页码数
    }

    public boolean hasNext() {  //有下一页
        return getPageNo() < getTotalPages();                //当前不是尾页，且总页数大于2
    }

    public boolean hasPrevious() {  //有上一页
        return getPageNo() > 1;                //当前不是尾页，且总页数大于2
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotalPages() {         //获取总页数
        totalPages = (int) (totalCount / pageSize);
        if (totalCount % pageSize != 0) {
            totalPages++;
        }
        return totalPages;
    }

    public List getDates() {
        return dates;
    }

    public void setDates(List dates) {
        this.dates = dates;
    }
   /*
   public static void main(String[] args) {
	Page p=new Page();
	p.setTotalCount(53344);
	System.out.println("总页数"+p.getTotalPages());
	p.setPageNo(50);
	System.out.println("有首页:"+p.hasFirst());
	System.out.println("有上一页:"+p.hasPrevious());
	System.out.println("有下一页:"+p.hasNext());
	System.out.println("有尾页页:"+p.hasLast());

  } */

    @Override
    public String toString() {
        return "Page{" +
                "pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", pageNo=" + pageNo +
                ", start=" + start +
                ", totalPages=" + totalPages +
                ", dates=" + dates +
                '}';
    }
}

