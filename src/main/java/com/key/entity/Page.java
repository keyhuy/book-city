package com.key.entity;

import java.util.List;

/**
 * 分页通用实体类
 *  - currentPage：当前页
 *  - pageSize：每页记录数
 *  - totalPage：总页数
 *  - totalCount：总记录数
 *  - infoPerPage：每页显示的信息
 *  - reqPath：请求地址，用于区分分页条请求的不同资源地址
 *
 * @author Key
 * @date 2021/09/14/19:44
 **/
public class Page<T> {
    public static final Integer PAGE_SIZE = 4;

    private Integer currentPage;
    private Integer pageSize = PAGE_SIZE;
    private Integer totalPage;
    private Integer totalCount;
    private List<T> infoPerPage;
    private String reqPath;

    public Page() {
    }

    public Page(Integer currentPage, Integer pageSize, Integer totalPage, Integer totalCount, List<T> infoPerPage) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.infoPerPage = infoPerPage;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getInfoPerPage() {
        return infoPerPage;
    }

    public void setInfoPerPage(List<T> infoPerPage) {
        this.infoPerPage = infoPerPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getReqPath() {
        return reqPath;
    }

    public void setReqPath(String reqPath) {
        this.reqPath = reqPath;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                ", infoPerPage=" + infoPerPage +
                ", reqPath='" + reqPath + '\'' +
                '}';
    }
}
