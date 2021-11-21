import com.key.entity.Book;
import com.key.entity.Page;
import com.key.service.impl.BookServiceImpl;
import com.key.service.inter.BookService;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * 图书service测试类
 *
 * @author Key
 * @date 2021/09/12/19:57
 **/
public class BookServiceTest {

    BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        int i = bookService.addBook(new Book(null, "《JQuery》", "小蓝",
                new BigDecimal(119), 200, 400, null));
        System.out.println(i);
    }

    @Test
    public void deleteBook() {
        int i = bookService.deleteBook(3);
        System.out.println(i);
    }

    @Test
    public void updateBookInfo() {
        int i = bookService.updateBookInfo(new Book(2, "《Mybatis》", "小谭",
                new BigDecimal(899), 200, 400, null));
        System.out.println(i);
    }

    @Test
    public void queryBook() {
        Book book = bookService.queryBook(2);
        System.out.println(book);
    }

    @Test
    public void queryBooks() {
        List<Book> books = bookService.queryBooks();

        books.forEach(System.out :: println);
    }

    @Test
    public void testPageBooks() {
        Page<Book> books = bookService.pageBooks(2, 4);

        System.out.println(books);
    }

    @Test
    public void pageCliBooksByPrice() {
        Page<Book> books = bookService.pageCliBooksByPrice(2, 4, 200, 900);

        System.out.println(books);
    }
}