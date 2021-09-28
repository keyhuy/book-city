package com.key.dao.impl;

import com.key.dao.BaseDao;
import com.key.dao.inter.BookDao;
import com.key.entity.Book;

import java.util.List;

/**
 * 图书dao实现类
 *
 * @author Key
 * @date 2021/09/12/19:18
 **/
public class BookDaoImpl extends BaseDao implements BookDao {

    /**
     * 添加新的图书
     * @param newBook 新的图书对象
     * @return 返回影响行数
     */
    @Override
    public int addBook(Book newBook) {
        // 创建SQL语句
        String sql = "insert into b_book values(null, ?, ?, ?, ?, ?, ?)";

        // 调用父类BaseDao的方法，更新数据库信息
        return updateInfo(sql, newBook.getTitle(), newBook.getAuthor(),
                newBook.getPrice(), newBook.getSales(), newBook.getStock(), newBook.getImgPath());
    }

    /**
     * 根据id删除对应的图书
     * @param id 图书id
     * @return 返回影响行数
     */
    @Override
    public int deleteBookById(int id) {
        // 创建SQL语句
        String sql = "delete from b_book where id = ?";

        return updateInfo(sql, id);
    }

    /**
     * 更新图书信息
     * @param book 图书对象
     * @return 返回影响行数
     */
    @Override
    public int updateBookInfo(Book book) {
        // 创建SQL语句
        String sql = "update b_book " +
                "set " +
                "title = ?, author = ?, price = ?, sales = ?, stock = ?, img_path = ? " +
                "where id = ?";

        return updateInfo(sql, book.getTitle(), book.getAuthor(), book.getPrice(),
                book.getSales(), book.getStock(), book.getImgPath(), book.getId());
    }

    /**
     * 根据id查询一条图书记录
     * @param id 图书id
     * @return 返回对应图书对象
     */
    @Override
    public Book queryBookById(int id) {
        // 创建SQL语句
        String sql = "select * from b_book where id = ?";

        return queryForOne(Book.class, sql, id);
    }

    /**
     * 查询所有图书信息
     * @return 返回图书列表
     */
    @Override
    public List<Book> queryBookList() {
        // 创建SQL语句
        String sql = "select * from b_book";

        return queryForList(Book.class, sql);
    }

    /**
     * 查询每页的信息
     * @param beginPage 每页的初始页码
     * @param pageSize 每页的记录数
     * @return 返回每页的信息集合
     */
    @Override
    public List<Book> queryBooksPerPage(int beginPage, int pageSize) {
        // 创建SQL语句
        String sql = "select * from b_book limit ? , ?";

        // 调用BaseDao方法
        return queryForList(Book.class, sql, beginPage, pageSize);
    }

    /**
     * 查询总记录数
     * @return 返回总记录数
     */
    @Override
    public int queryTotalCount() {
        // 创建SQL语句
        String sql = "select count(*) from b_book";

        Number count = (Number) queryForValue(sql);

        return count.intValue();
    }

    /**
     * 根据价格区间查询总记录数
     * @param minPrice 最小价格
     * @param maxPrice 最大价格
     * @return 返回总记录数
     */
    @Override
    public int queryTotalCountByPrice(int minPrice, int maxPrice) {
        // 创建SQL语句
        String sql = "select count(*) from b_book where price between ? and ?";

        Number number = (Number) queryForValue(sql, minPrice, maxPrice);

        return number.intValue();
    }

    /**
     * 根据价格区间查询每页的图书信息
     * @param beginPage 每页初始页码
     * @param pageSize 每页记录数
     * @param minPrice 最小价格
     * @param maxPrice 最大价格
     * @return 返回每页图书信息
     */
    @Override
    public List<Book> queryBooksPerPageByPrice(int beginPage, int pageSize, int minPrice, int maxPrice) {
        // 创建SQL语句
        String sql = "select * from b_book where price between ? and ? order by price limit ? , ?";

        return queryForList(Book.class, sql, minPrice, maxPrice, beginPage, pageSize);
    }
}
