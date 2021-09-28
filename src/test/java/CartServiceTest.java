import com.key.entity.Cart;
import com.key.entity.CartItem;
import com.key.service.impl.CartServiceImpl;
import com.key.service.inter.CartService;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author Key
 * @date 2021/09/20/16:48
 **/
public class CartServiceTest {
    private final CartServiceImpl cService = new CartServiceImpl(new Cart());

    @Test
    public void addItem() {
        cService.addItem(new CartItem(1, "java", 1, new BigDecimal(23), new BigDecimal(23)));
        cService.addItem(new CartItem(1, "java", 1, new BigDecimal(23), new BigDecimal(23)));
        cService.addItem(new CartItem(2, "python", 2, new BigDecimal(93), new BigDecimal(93)));

        System.out.println(cService.getCart());
    }

    @Test
    public void deleteItem() {
        cService.addItem(new CartItem(1, "java", 1, new BigDecimal(23), new BigDecimal(23)));
        cService.addItem(new CartItem(1, "java", 1, new BigDecimal(23), new BigDecimal(23)));
        cService.addItem(new CartItem(2, "python", 2, new BigDecimal(93), new BigDecimal(186)));

        cService.deleteItem(1);

        System.out.println(cService.getCart());
    }

    @Test
    public void clearCart() {
        cService.addItem(new CartItem(1, "java", 1, new BigDecimal(23), new BigDecimal(23)));
        cService.addItem(new CartItem(1, "java", 1, new BigDecimal(23), new BigDecimal(23)));
        cService.addItem(new CartItem(2, "python", 2, new BigDecimal(93), new BigDecimal(186)));

        cService.clearCart();

        System.out.println(cService.getCart());
    }

    @Test
    public void updateItemCount() {
        cService.addItem(new CartItem(1, "java", 1, new BigDecimal(23), new BigDecimal(23)));
        cService.addItem(new CartItem(1, "java", 1, new BigDecimal(23), new BigDecimal(23)));
        cService.addItem(new CartItem(2, "python", 2, new BigDecimal(93), new BigDecimal(186)));

        cService.updateItemCount(2, 5);

        System.out.println(cService.getCart());
    }

}