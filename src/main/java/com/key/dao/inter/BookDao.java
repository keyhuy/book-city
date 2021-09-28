package com.key.dao.inter;

import com.key.entity.Book;

import java.util.List;

/**
 * 图书dao接口
 *
 * @author Key
 * @date 2021/09/12/19:13
 **/
public interface BookDao {

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
    int deleteBookById(int id);

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
    Book queryBookById(int id);

    /**
     * 查询所有图书信息
     * @return 返回图书列表
     */
    List<Book> queryBookList();

    /**
     * 查询每页的信息
     * @param beginPage 每页的初始页码
     * @param pageSize 每页的记录数
     * @return 返回每页的信息集合
     */
    List<Book> queryBooksPerPage(int beginPage, int pageSize);

    /**
     * 查询总记录数
     * @return 返回总记录数
     */
    int queryTotalCount();

    /**
     * 根据价格区间查询总记录数
     * @param minPrice 最小价格
     * @param maxPrice 最大价格
     * @return 返回总记录数
     */
    int queryTotalCountByPrice(int minPrice, int maxPrice);

    /**
     * 根据价格区间查询每页的图书信息
     * @param beginPage 每页初始页码
     * @param pageSize 每页记录数
     * @param minPrice 最小价格
     * @param maxPrice 最大价格
     * @return 返回每页图书信息
     */
    List<Book> queryBooksPerPageByPrice(int beginPage, int pageSize, int minPrice, int maxPrice);
}