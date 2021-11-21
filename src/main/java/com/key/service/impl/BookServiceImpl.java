package com.key.service.impl;

import com.key.dao.impl.BookDaoImpl;
import com.key.dao.inter.BookDao;
import com.key.entity.Book;
import com.key.entity.Page;
import com.key.service.inter.BookService;
import com.key.util.WebUtils;

import java.util.List;

/**
 * 图书service实现类
 *
 * @author Key
 * @date 2021/09/12/19:53
 **/
public class BookServiceImpl implements BookService {
    private final BookDao bDao = new BookDaoImpl();

    /**
     * 添加新的图书
     * @param newBook 新的图书对象
     * @return 返回影响行数
     */
    @Override
    public int addBook(Book newBook) {
      return bDao.addBook(newBook);
    }

    /**
     * 根据id删除对应的图书
     * @param id 图书id
     * @return 返回影响行数
     */
    @Override
    public int deleteBook(int id) {
        return bDao.deleteBookById(id);
    }

    /**
     * 更新图书信息
     * @param book 图书对象
     * @return 返回影响行数
     */
    @Override
    public int updateBookInfo(Book book) {
        return bDao.updateBookInfo(book);
    }

    /**
     * 根据id查询一条图书记录
     * @param id 图书id
     * @return 返回对应图书对象
     */
    @Override
    public Book queryBook(int id) {
        return bDao.queryBookById(id);
    }

    /**
     * 查询所有图书信息
     * @return 返回图书列表
     */
    @Override
    public List<Book> queryBooks() {
        return bDao.queryBookList();
    }

    /**
     * 处理分页的逻辑，给分页所需数据赋值，封装成对象
     * @param currentPage 当前页码（未校验）
     * @param pageSize 每页记录数
     * @return 返回分页对象
     */
    @Override
    public Page<Book> pageBooks(int currentPage, int pageSize) {
        // 1. 调用dao类方法，查询出总记录数
        int totalCount = bDao.queryTotalCount();

        // 2. 通过总记录数和每页记录数计算得出总页码
        int totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);

        /*
             3. 对当前页码进行校验，获取合法值
               必须在查询每页信息前以及在即计算出总页码后，进行校验，不然查出的信息会出错
        */
        int legalCurrPage = WebUtils.getLegalPage(currentPage, totalPage);

        // 4. 调用dao类方法，查询出每页信息
        // 4.1 先算出初始页码，用合法的当前页码进行计算
        int beginPage = (legalCurrPage - 1) * pageSize;
        List<Book> booksPerPage = bDao.queryBooksPerPage(beginPage, pageSize);

        // 5. 注入各个属性并返回
        return new Page<>(legalCurrPage, pageSize, totalPage, totalCount, booksPerPage);
    }

    /**
     * 处理根据价格区间分页的逻辑
     * @param currPage 当前页码
     * @param pageSize 每页记录数
     * @param minPrice 最小价格
     * @param maxPrice 最大价格
     * @return 返回分页对象
     */
    @Override
    public Page<Book> pageCliBooksByPrice(int currPage, int pageSize, int minPrice, int maxPrice) {
        // 1. 获取总记录数
        int totalCount = bDao.queryTotalCountByPrice(minPrice, maxPrice);

        // 2. 计算出总页码
        int totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);

        // 3. 根据总页码，获取合法的当前页码
        int legalCurrPage = WebUtils.getLegalPage(currPage, totalPage);

        // 4. 获取每页显示的数据
        int beginPage = (legalCurrPage - 1) * pageSize;
        List<Book> books = bDao.queryBooksPerPageByPrice(beginPage, pageSize, minPrice, maxPrice);

        return new Page<>(legalCurrPage, pageSize, totalPage, totalCount, books);
    }
}
