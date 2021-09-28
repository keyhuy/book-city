import com.key.dao.impl.BookDaoImpl;
import com.key.dao.inter.BookDao;
import com.key.entity.Book;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * 图书dao测试类
 *
 * @author Key
 * @date 2021/09/12/19:13
 **/
public class BookDaoTest {

    BookDao bookDao = new BookDaoImpl();

    @Test
    public void addBook() {
        int i = bookDao.addBook(new Book(null, "《java》", "小明",
                new BigDecimal(999), 200, 400, null));
        System.out.println(i);
    }

    @Test
    public void deleteBookById() {
        int i = bookDao.deleteBookById(3);
        System.out.println(i);
    }

    @Test
    public void updateBookInfo() {
        int i = bookDao.updateBookInfo(new Book(1, "《C++》", "小红",
                new BigDecimal(899), 200, 400, null));
        System.out.println(i);
    }

    @Test
    public void queryBookById() {
        Book book = bookDao.queryBookById(2);
        System.out.println(book);
    }

    @Test
    public void queryBookList() {
        List<Book> books = bookDao.queryBookList();

        books.forEach(System.out :: println);
    }

    @Test
    public void queryBooksPerPage() {
        List<Book> books = bookDao.queryBooksPerPage(3, 4);

        books.forEach(System.out :: println);
    }


    @Test
    public void queryTotalCount() {
        int i = bookDao.queryTotalCount();

        System.out.println(i);
    }

    @Test
    public void queryTotalCountByPrice() {
        int i = bookDao.queryTotalCountByPrice(200, 900);
        System.out.println(i);
    }

    @Test
    public void queryBooksPerPageByPrice() {
        List<Book> books = bookDao.queryBooksPerPageByPrice(0, 4, -1, 900);
        books.forEach(System.out :: println);
    }
}