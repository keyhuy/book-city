import com.key.entity.User;
import com.key.service.impl.UserServiceImpl;
import com.key.service.inter.UserService;
import org.junit.Test;

/**
 * service层测试类
 *
 * @author Key
 * @date 2021/09/10/20:01
 **/
public class UserServiceTest {
    UserService uService = new UserServiceImpl();

    /**
     * 测试注册功能
     */
    @Test
    public void testRegister() {
        User user = new User();
        user.setUsername("jay");
        user.setPassword("a123");
        user.setEmail("baby@qq.com");

        uService.register(user);
    }

    /**
     * 测试登录功能
     */
    @Test
    public void testLogin() {
        User user = new User();
        user.setUsername("Tom");
        user.setPassword("123456");

        User loginUser = uService.login(user);
        System.out.println(loginUser);
    }

    /**
     * 测试判断用户是否存在
     */
    @Test
    public void testExists() {

        boolean isExists = uService.isExistsName("Tmm");
        System.out.println(isExists);
    }
}
