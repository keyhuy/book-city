import com.key.dao.impl.UserDaoImpl;
import com.key.dao.inter.UserDao;
import com.key.entity.User;
import org.junit.Test;

/**
 * dao类测试
 *
 * @author Key
 * @date 2021/09/10/17:33
 **/
public class UserDaoTest {
    UserDao userDao = new UserDaoImpl();

    /**
     * 测试通过用户名查询
     */
    @Test
    public void testQueryByName() {
        User tom = userDao.queryUserByName("Tom");

        System.out.println(tom);
    }

    /**
     * 测试通过用户名和密码查询
     */
    @Test
    public void testQueryByNameAndPwd() {
        User user = userDao.queryUserByNameAndPwd("key", "4562244");
        System.out.println(user);
    }

    @Test
    public void testSaveInfo() {
        User user = new User();
        user.setUsername("baby");
        user.setPassword("a123");
        user.setEmail("baby@qq.com");

        int i = userDao.saveUserInfo(user);
        System.out.println(i);
    }
}
