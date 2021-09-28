package com.key.service.inter;

import com.key.entity.Book;
import com.key.entity.Page;

import java.util.List;

/**
 * 图书Service接口
 *
 * @author Key
 * @date 2021/09/12/19:48
 **/
public interface BookService {

    /**
     * 添加新的图书
     * @param newBook 新的图书对象
     * @return 返回影响行数
     */
    int addBook(Book newBook);

    /**
     * 根据id删除对应的图书
     * @param id 图书id
     * @return 返回影响行数
     */
    int deleteBook(int id);

    /**
     * 更新图书信息
     * @param book 图书对象
     * @return 返回影响行数
     */
    int updateBookInfo(Book book);

    /**
     * 根据id查询一条图书记录
     * @param id 图书id
     * @return 返回对应图书对象
     */
    Book queryBook(int id);

    /**
     * 查询所有图书信息
     * @return 返回图书列表
     */
    List<Book> queryBooks();

    /**
     * 处理分页的逻辑，给分页所需数据赋值，封装成对象
     * @param currentPage 当前页码
     * @param pageSize 每页记录数
     * @return 返回分页对象
     */
    Page<Book> pageBooks(int currentPage, int pageSize);

    /**
     * 处理根据价格区间分页的逻辑
     * @param currPage 当前页码
     * @param pageSize 每页记录数
     * @param minPrice 最小价格
     * @param maxPrice 最大价格
     * @return 返回分页对象
     */
    Page<Book> pageCliBooksByPrice(int currPage, int pageSize, int minPrice, int maxPrice);
}