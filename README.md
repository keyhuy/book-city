# 书城项目（BookCity）的编写

## 一、项目简介

> 学习视频：尚硅谷https://www.bilibili.com/video/BV1Y7411K7zz

### 1.1 功能简介

* 基本功能
  * 用户模块：登录、注册
  * 图书模块：添加图书、删除图书、修改图书信息。。
  * 购物车模块：加购商品、删除商品、清空购物车、修改购物车信息。。
  * 订单模块：查看订单详情、查看我的订单、签收订单、查看所有订单、发货。。
* 主要功能：浏览图书商品、将商品加购物车、下单等。。

***

### 1.2 使用工具

* 语言：Java（后台）、html、css、JS（前端）

* 框架：JQuery（前端）

* 数据库相关：Mysql、druid 德鲁伊连接池、DBUtil工具类库、SQLyog

* 开发工具：idea、maven

  > PS：在idea 中使用骨架创建web 项目，不使用骨架会麻烦点。。

* 服务器：tomcat

## 二、用户模块

### 2.1 模块分析与功能说明

#### 2.1.1 JS实现简单的表单校验

* 在regist.html 静态页面中添加JQuery 文件，并使用JS 实现表单校验

  ```html
  <!-- 引入JQuery 文件 -->
  <script type="text/javascript" src="../../static/script/jquery-1.7.2.js"></script>
  
  <!-- js实现表单校验的功能 -->
  <script type="text/javascript">
      // 页面加载完成之后
      $(function () {
  
          // 给注册按钮绑定点击事件
          $("#sub_btn").click(function () {
              // 验证用户名：必须由字母、数字、下划线组成，长度5-12位
              // 1. 获取表单内容
              let nameText = $("#username").val();
  
              // 2. 创建正则表达式对象
              let namePat = /^\w{5,12}/;
  
              // 3. 用test() 方法验证
              if (! namePat.test(nameText)) {
                  // 4. 如果信息不合法，提示信息
                  $("span.errorMsg").text("用户名不合法！");
  
                  // 必须要加上，才能阻止表单的提交
                  return false;
              }
  
              // 验证密码：必须由字母、数字、下划线组成，长度5-12位
              // 1. 获取表单内容
              let pwdText = $("#password").val();
  
              // 2. 创建正则表达式对象
              let pwdPat = /^\w{5,12}/;
  
              // 3. 用test() 方法验证
              if (! pwdPat.test(pwdText)) {
                  // 4. 如果信息不合法，提示信息
                  $("span.errorMsg").text("密码格式不合法！");
  
                  // 必须要加上，才能阻止表单的提交
                  return false;
              }
  
              // 验证确认密码：确认密码必须与密码一样
              // 1. 获取确认密码的内容
              let rePwdText = $("#repwd").val();
  
              // 2. 与密码进行比较
              if (rePwdText !== pwdText) {
                  // 提示错误信息
                  $("span.errorMsg").text("确认密码与密码不一致！");
  
                  return false;
              }
  
              // 验证邮箱：根据正则表达式验证即可
              // 1. 获取表单内容
              let emailText = $("#email").val();
  
              // 2. 创建正则表达式对象
              let emailPat = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
  
              // 3. 与正则表达式比较
              if (! emailPat.test(emailText)) {
                  // 4. 如果信息不合法，提示信息
                  $("span.errorMsg").text("邮箱格式不合法！");
  
                  // 必须要加上，才能阻止表单的提交
                  return false;
              }
  
              // 验证验证码：暂时先根据空字符串和null来判断
              let codeText = $("#code").val();
  
              if (codeText == null || codeText === "") {
                  // 4. 如果信息不合法，提示信息
                  $("span.errorMsg").text("验证码不正确！");
  
                  // 必须要加上，才能阻止表单的提交
                  return false;
              }
  
              // 最后如果没有不合法信息，就把提示信息去掉
              $("span.errorMsg").text("");
          })
      })
  </script>
  ```

#### 2.1.2 数据库表的创建

* 创建数据库——book_city

  ```sql
  create database book_city;
  user book_city;
  ```

* 创建用户表——b_user

  > 字段有id、用户名（username）、密码（password）、邮箱（email）

  ```sql
  create table b_user (
  	`id` int not null primary key auto_increment,
      `username` varchar(20) not null unique,
      `password` varchar(32) not null,
      `email` varchar(200)
  );
  ```

#### 2.1.3 创建数据库表对应的JavaBean

* 在entity 包下创建User 类，成员变量分别对应b_user 的各个字段

  ```java
  public class User {
      private Integer id;
      private String username;
      private String password;
      private String email;
  
      public User() {
      }
  
      public User(Integer id, String username, String password, String email) {
          this.id = id;
          this.username = username;
          this.password = password;
          this.email = email;
      }
  
      public Integer getId() {
          return id;
      }
  
      public void setId(Integer id) {
          this.id = id;
      }
  
      public String getUsername() {
          return username;
      }
  
      public void setUsername(String username) {
          this.username = username;
      }
  
      public String getPassword() {
          return password;
      }
  
      public void setPassword(String password) {
          this.password = password;
      }
  
      public String getEmail() {
          return email;
      }
  
      public void setEmail(String email) {
          this.email = email;
      }
  
      @Override
      public String toString() {
          return "User{" +
                  "id=" + id +
                  ", username='" + username + '\'' +
                  ", password='" + password + '\'' +
                  ", email='" + email + '\'' +
                  '}';
      }
  }
  ```

#### 2.1.4 创建Jdbc工具类

* 在utils 包下创建JdbcUtil 类，在resource资源包下创建jdbc.properties 属性文件，然后在JdbcUtil 类中加载属性文件，并获取到数据库连接池对象，以及创建获取连接对象和归还资源的方法

  1. jdbc.properties

     ```properties
     driverClassName=com.mysql.cj.jdbc.Driver
     url=jdbc:mysql://localhost:3306/book_city
     username=root
     password=Myroot_ljh
     # 初始化连接对象数量
     initialSize=5
     # 最大连接数
     maxActive=10
     # 超时等待时间
     maxWait=3000
     ```

  2. JdbcUtil 类

     ```java
     public class JdbcUtil {
         private static DataSource ds;
     
         static {
             try {
                 // 创建属性文件类
                 Properties pro = new Properties();
                 // 将外部属性文件加载到内存
                 pro.load(JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
     
                 // 通过属性类对象获取数据库连接池对象，并将属性文件中各个属性注入连接池
                 ds = DruidDataSourceFactory.createDataSource(pro);
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     
         /**
          * 获取连接对象
          * @return 返回对象
          */
         public static Connection getConn() throws SQLException {
             return ds.getConnection();
         }
     
         /**
          * 将资源归还给连接池
          * @param connection 连接对象资源
          */
         public static void returnResource(Connection connection) {
             // 如果对象不为null，就归还
             if (null != connection) {
                 try {
                     connection.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
         }
     }
     ```

#### 2.1.5 创建用户的dao类

##### a. <u>创建BaseDao 类</u>

* 创建BaseDao，作为数据库访问类的基本类，类中==创建CRUD的基本操作方法==

  > PS1：由于BaseDao 类只作为数据库操作的基本类，其他dao类只需继承BaseDao 类即可，不需要对其创建对象，所以可以将BaseDao 类==设置为抽象类==
  >
  > PS2：`queryRunner.query()`方法中添加`new BasicRowProcessor(new GenerousBeanProcessor()`参数，实现查询返回结果==下划线转驼峰命名==，参考博文——https://blog.csdn.net/weixin_41705396/article/details/104393190

  ```java
  public abstract class BaseDao {
      private final QueryRunner queryRunner = new QueryRunner();
  
      /**
       * 更新数据——增删改
       * @param sql SQL语句
       * @param args 可变参数，对应SQL语句中占位符的值
       * @return 返回更新数据库表后的影响行数，如果查询失败就返回-1
       */
      public int updateInfo(String sql, Object ...args) {
          // 初始化连接对象
          Connection conn = null;
  
          try {
              // 获取连接对象
              conn = JdbcUtil.getConn();
  
              // 调用queryRunner的方法
              return queryRunner.update(conn, sql, args);
          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
              // 归还资源
              JdbcUtil.returnResource(conn);
          }
  
          return -1;
      }
  
      /**
       * 查询单个对象
       * @param clazz 返回对象的类型
       * @param sql SQL语句
       * @param args 可变参数，对应SQL语句中占位符的值
       * @param <T> 返回类型的泛型
       * @return 返回查询到的结果对象
       */
      public <T> T queryForOne(Class<T> clazz, String sql, Object ...args) {
          // 初始化连接对象
          Connection conn = null;
  
          try {
              // 获取连接对象
              conn = JdbcUtil.getConn();
  
              // 调用queryRunner对应方法，并返回结果
              return queryRunner.query(conn, sql, new BeanHandler<>(clazz), args);
          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
              // 归还资源
              JdbcUtil.returnResource(conn);
          }
  
          return null;
      }
  
      /**
       * 查询多个对象，返回对象集合
       * @param clazz 返回对象的类型
       * @param sql SQL语句
       * @param args 可变参数，对应SQL语句中占位符的值
       * @param <T> 返回类型的泛型
       * @return 返回查询到的结果对象集合
       */
      public <T>List<T> queryForList(Class<T> clazz, String sql, Object ...args) {
          // 初始化连接对象
          Connection conn = null;
  
          try {
              // 获取连接对象
              conn = JdbcUtil.getConn();
  
              // 调用queryRunner对应方法，并返回结果
              return queryRunner.query(conn, 
                                       sql, 
                      new BeanListHandler<>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), 
                                       args);
          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
              // 归还资源
              JdbcUtil.returnResource(conn);
          }
  
          return null;
      }
  
      /**
       * 查询某个值
       * @param sql SQL语句
       * @param args 可变参数，对应SQL语句中占位符的值
       * @return 返回查询的值
       */
      public Object queryForValue(String sql, Object ...args) {
          // 初始化连接对象
          Connection conn = null;
  
          try {
              // 获取连接对象
              conn = JdbcUtil.getConn();
  
              // 调用对应方法
              queryRunner.query(conn, sql, new ScalarHandler<>(), args);
          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
              // 归还资源
              JdbcUtil.returnResource(conn);
          }
  
          return null;
      }
  }
  
  ```

##### b. <u>创建UserDao接口和实现类</u>

* UserDao 接口

  > UserDao中包含以下几个方法
  >
  > 1. `User queryUserByName(String username)`：通过姓名查询用户信息，用于注册
  > 2. `User queryUserByNameAndPwd(String username, String pwd)`：通过姓名和密码查询用户信息，用于登录
  > 3. `int saveUserInfo(User user)`：保存用户信息，并返回影响行数，注册之后就保存新的用户信息

  ```java
  public interface UserDao {
  
      /**
       * 通过姓名查询用户信息
       *  - 用于注册验证，当数据库表中没有该名字时才能验证成功
       * @param username 用户名
       * @return 返回用户对象
       */
      User queryUserByName(String username);
  
      /**
       * 通过用户名和密码查询用户信息
       *  - 用于登录验证，当数据库表中由该名字和密码时才能验证成功
       * @param username 用户名
       * @param pwd 密码
       * @return 返回用户信息
       */
      User queryUserByNameAndPwd(String username, String pwd);
  
      /**
       * 保存用户信息
       *  - 用于保存成功注册后的新用户信息
       * @param user 新的用户对象
       * @return 返回影响行数
       */
      int saveUserInfo(User user);
  }
  ```

* UserDao 实现类

  ```java
  public class UserDaoImpl extends BaseDao implements UserDao {    /**     * 通过姓名查询用户信息     *  - 用于注册验证，当数据库表中没有该名字时才能验证成功     * @param username 用户名     * @return 返回用户对象     */    @Override    public User queryUserByName(String username) {        // 创建SQL语句        String sql = "select * from b_user where username = ?";        return queryForOne(User.class, sql, username);    }    /**     * 通过用户名和密码查询用户信息     *  - 用于登录验证，当数据库表中由该名字和密码时才能验证成功     * @param username 用户名     * @param pwd 密码     * @return 返回用户信息     */    @Override    public User queryUserByNameAndPwd(String username, String pwd) {        // 创建SQL语句        String sql = "select * from b_user where username = ? and password = ?";                return queryForOne(User.class, sql, username, pwd);    }    /**     * 保存用户信息     *  - 用于保存成功注册后的新用户信息     * @param user 新的用户对象     * @return 返回影响行数     */    @Override    public int saveUserInfo(User user) {        // 创建SQL语句        String sql = "insert into b_user values(null, ?, ?, ?)";                return updateInfo(sql, user.getUsername(), user.getPassword(), user.getEmail());    }}
  ```

* 测试类

  ```java
  package dao;
  
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
  public class DaoTest {
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
  ```

#### 2.1.6 创建用户service接口和实现类

* UserService接口

  > UserService中包含以下几个方法
  >
  > 1. `void register(User newUser)`：注册功能，保存新的用户名信息
  > 2. `User login(User user)`：登录功能，根据用户名和密码登录，并返回登录后的用户对象
  > 3. `boolean isExistsName(String username)`：判断用户是否已经存在，用于判断注册和登录是否成功

  ```java
  public interface UserService {    /**     * 注册功能     * @param newUser 新的用户对象     */    void register(User newUser);    /**     * 登录功能     * @param user 用户对象     * @return 返回登录后的用户信息     */    User login(User user);    /**     * 根据用户名判断用户是否已经存在     * @param username 用户名     * @return 如果存在，就返回true，反之返回false     */    boolean isExistsName(String username);}
  ```

* UserService 实现类

  ```java
  public class UserServiceImpl implements UserService {    private final UserDao userDao = new UserDaoImpl();    /**     * 注册功能     * @param newUser 新的用户对象     */    @Override    public void register(User newUser) {        // 保存新的用户信息        int info = userDao.saveUserInfo(newUser);        System.out.println(info);    }    /**     * 登录功能     * @param user 用户对象     * @return 返回登录后的用户信息     */    @Override    public User login(User user) {        // 根据用户名和密码查询出对应的用户信息        return userDao.queryUserByNameAndPwd(user.getUsername(), user.getPassword());    }    /**     * 根据用户名判断用户是否已经存在     * @param username 用户名     * @return 如果存在，就返回true，反之返回false     */    @Override    public boolean isExistsName(String username) {        // 根据用户名查询数据库，如果返回对象为null，则不存在该用户        return null != userDao.queryUserByName(username);    }}
  ```

* 测试类

  ```java
  public class ServiceTest {    UserService uService = new UserServiceImpl();    /**     * 测试注册功能     */    @Test    public void testRegister() {        User user = new User();        user.setUsername("jay");        user.setPassword("a123");        user.setEmail("baby@qq.com");        uService.register(user);    }    /**     * 测试登录功能     */    @Test    public void testLogin() {        User user = new User();        user.setUsername("Tom");        user.setPassword("123456");        User loginUser = uService.login(user);        System.out.println(loginUser);    }    /**     * 测试判断用户是否存在     */    @Test    public void testExists() {        boolean isExists = uService.isExistsName("Tmm");        System.out.println(isExists);    }}
  ```

#### 2.1.7 后台servlet和前端页面的编写

##### a. <u>注册功能</u>

* 后台Servlet —— RegisterServlet

  * RegisterServlet 类

    ```java
    public class RegisterServlet extends HttpServlet {    private final UserService uService = new UserServiceImpl();    @Override    protected void doGet(HttpServletRequest req,                          HttpServletResponse resp) throws ServletException, IOException {        this.doPost(req, resp);    }    @Override    protected void doPost(HttpServletRequest req,                           HttpServletResponse resp) throws ServletException, IOException {        // 1. 获取请求参数        String username = req.getParameter("username");        String password = req.getParameter("password");        String email = req.getParameter("email");        String code = req.getParameter("code");        // 2. 验证码校验（忽略大小写）：验证码先写死：abc        if (! "abc".equalsIgnoreCase(code)) {            // 2.1. 验证码不正确 ——> 转发回到注册页面            System.out.println("验证码错误！");            req.getRequestDispatcher("/pages/user/regist.html").forward(req, resp);        } else { // 2.2. 验证码正确 ——> 再验证码用户名是否已经存在            // 3. 调用service类方法验证用户名是否已经存在            if (uService.isExistsName(username)) {                // 3.1 用户名已经存在 ——> 注册失败，转发回到注册页面                System.out.println("用户名已存在！");                req.getRequestDispatcher("/pages/user/regist.html").forward(req, resp);            } else {                // 3.2 用户名不存在 ——> 注册成功，调用service类方法，保存新的用户信息，并转发到注册成功页面                uService.register(new User(null, username, password, email));                req.getRequestDispatcher("/pages/user/regist_success.html").forward(req, resp);            }        }    }}
    ```

  * web.xml 中配置Servlet

    ```xml
    <!-- 先给Servlet实现类创建一个名字 --><servlet>    <!-- 创建一个名字（标识） -->	<servlet-name>registerServlet</servlet-name>    <!-- 要把实现类的全类名写上 -->    <servlet-class>com.key.web.servlet.registerServlet</servlet-class></servlet><!-- 再通过实现类的名字与资源路径配对上 --><servlet-mapping>	<servlet-name>registerServlet</servlet-name>    <!-- 资源路径 -->    <url-pattern>/registerServlet</url-pattern></servlet-mapping>
    ```

* 前端页面——regist.html、regist_success.html：在页面中添加`<base href="http://loacalhost:8080/BookCity">`，并将所有资源的路径改成相对路径，即将前面的`../../`删除

##### b. <u>登录功能</u> 

* 后台Servlet —— LoginServlet

  * LoginServlet 类

    ```java
    public class LoginServlet extends HttpServlet {    private final UserService uService = new UserServiceImpl();    @Override    protected void doGet(HttpServletRequest req,                          HttpServletResponse resp) throws ServletException, IOException {        this.doPost(req, resp);    }    @Override    protected void doPost(HttpServletRequest req,                           HttpServletResponse resp) throws ServletException, IOException {        // 1. 获取请求参数        String username = req.getParameter("username");        String password = req.getParameter("password");        // 2. 调用service类方法，判断对应用户名和密码的用户是否存在（不能只判断用户名，因为密码可能不正确）        User loginUser = uService.login(new User(null, username, password, null));        if (null == loginUser) {            // 2.1 用户不存在 ——> 转发回到登录页面            System.out.println("用户不存在，登陆失败！");            req.getRequestDispatcher("/pages/user/login.html").forward(req, resp);        } else {            // 2.2 用户存在 ——> 调用service类方法，查询出对应的用户对象，并转发到登录成功的页面            System.out.println(loginUser);            req.getRequestDispatcher("/pages/user/login_success.html").forward(req, resp);        }    }}
    ```

  * web.xml 中配置Servlet

    ```xml
    <!-- 先给Servlet实现类创建一个名字 --><servlet>    <!-- 创建一个名字（标识） -->	<servlet-name>loginServlet</servlet-name>    <!-- 要把实现类的全类名写上 -->    <servlet-class>com.key.web.servlet.LoginServlet</servlet-class></servlet><!-- 再通过实现类的名字与资源路径配对上 --><servlet-mapping>	<servlet-name>loginServlet</servlet-name>    <!-- 资源路径 -->    <url-pattern>/loginServlet</url-pattern></servlet-mapping>
    ```

* 前端页面——login.html、login_success.html：在页面中添加`<base href="http://loacalhost:8080/BookCity">`，并将所有资源的路径改成相对路径，即将前面的`../../`删除

***

### 2.2 代码和功能的优化

#### 2.2.1 将所有html改成jsp

1. 在所有html 页面上添加jsp 的指令

   ```jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   ```

2. 将后缀名改成`.jsp`

3. 将原来引用html 页面的其他文件中的`.html`也改成`.jsp`：可以在idea中使用快捷键Ctrl+Shift+R

#### 2.2.2 抽取jsp中的公共部分

1. 创建新的jsp文件，存放抽取出来的原jsp 页面中公共部分，如抽取出登录后的菜单栏

   ```jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %><div>    <span>欢迎<span class="um_span">辉哥</span>光临尚硅谷书城</span>    <a href="../order/order.jsp">我的订单</a>    <a href="../../index.jsp">注销</a>&nbsp;&nbsp;    <a href="../../index.jsp">返回</a></div>
   ```

2. 在原jsp 页面中用jsp 包含指令取代对应的公共部分

   ```jsp
   <%@ include file="/pages/common/loginMenu.jsp"%>
   ```

#### 2.2.3 将base标签值改成动态

1. 使用jsp脚本，动态获取地址中的协议、IP地址、端口号、虚拟目录（工程路径）

   ```jsp
   <%-- 动态获取base标签值 --%><%    String basePath = request.getScheme() + "://" +            request.getServerName() + ":" +            request.getServerPort() +            request.getContextPath() + "/";%>
   ```

   > PS：注意`request.getContextPath()`获取的是虚拟路径（工程路径），即`/BookCity`，不是工程名，前面是带`/`的，所以`request.getContextPath()`前面不用再加`"/"`，如果加了会对session产生影响（暂时不知为啥）

2. 将原来的`<base>`标签值改成动态的`<%=basePath%>`

   ```jsp
   <!-- base标签，固定相对路径跳转的结果（固定路径的前面一部分） --><base href="<%=basePath%>">
   ```

#### 2.2.4 表单校验错误的回显信息

1. 在后台Servlet中将表单的内容以及错误提示信息==存放到request域==中（这里只演示验证码错误的情况）

   ```java
   // 将验证码错误信息和表单部分内容存到request域中
   req.setAttribute("msg", "该用户名已存在！");
   req.setAttribute("username", username);
   req.setAttribute("email", email);
   ```

2. 在jsp页面中取出request域中内容，并回显到页面

   ```jsp
   <%-- 显示错误信息 --%>
   <span class="errorMsg">
   	<%=request.getAttribute("msg") == null ? "" : request.getAttribute("msg")%>
   </span>
   ```

#### 2.2.5 合并LoginServlet和RegisterServlet

1. 在登录和注册对应的jsp页面上==添加隐藏域==

   > 隐藏域：在页面上不显示的`<input>`标签，需要在添加到表单`<form>`中

   ```jsp
   <%-- 添加隐藏域 --%>
   <input type="hidden" name="action" value="login">
   ```

2. 将登录和注册两个Servlet合并为UserServlet

   > PS：一般项目中一个模块对应一个Servlet，登录和注册都是用户模块的功能，所以需要合并

   ```java
   public class UserServlet extends HttpServlet {
   
       private final UserService uService = new UserServiceImpl();
   
       /**
        * 登录功能
        * @param req 请求
        * @param resp 响应
        */
       protected void login(HttpServletRequest req, 
                            HttpServletResponse resp) throws ServletException, IOException {
           // 1. 获取请求参数
           String username = req.getParameter("username");
           String password = req.getParameter("password");
   
           // 2. 调用service类方法，判断对应用户名和密码的用户是否存在（不能只判断用户名，因为密码可能不正确）
           User loginUser = uService.login(new User(null, username, password, null));
           if (null == loginUser) {
   
               // 将错误信息和用户名存到request域中
               req.setAttribute("msg", "用户名或密码错误！");
               req.setAttribute("username", username);
   
               // 2.1 用户不存在 ——> 转发回到登录页面
               System.out.println("用户不存在，登陆失败！");
               req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
           } else {
   
               // 2.2 用户存在 ——> 调用service类方法，查询出对应的用户对象，并转发到登录成功的页面
               System.out.println(loginUser);
               req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
           }
       }
   
       /**
        * 注册功能
        * @param req 请求
        * @param resp 响应
        */
       protected void register(HttpServletRequest req, 
                               HttpServletResponse resp) throws ServletException, IOException {
           // 1. 获取请求参数
           String username = req.getParameter("username");
           String password = req.getParameter("password");
           String email = req.getParameter("email");
           String code = req.getParameter("code");
   
           // 2. 验证码校验（忽略大小写）：验证码先写死：abc
           if (! "abc".equalsIgnoreCase(code)) {
               // 将验证码错误信息和表单部分内容存到request域中
               req.setAttribute("msg", "验证码不正确！");
               req.setAttribute("username", username);
               req.setAttribute("email", email);
   
               // 2.1. 验证码不正确 ——> 转发回到注册页面
               System.out.println("验证码错误！");
               req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
           } else { // 2.2. 验证码正确 ——> 再验证码用户名是否已经存在
   
               // 3. 调用service类方法验证用户名是否已经存在
               if (uService.isExistsName(username)) {
   
                   // 将验证码错误信息和表单部分内容存到request域中
                   req.setAttribute("msg", "该用户名已存在！");
                   req.setAttribute("username", username);
                   req.setAttribute("email", email);
   
                   // 3.1 用户名已经存在 ——> 注册失败，转发回到注册页面
                   System.out.println("用户名已存在！");
                   req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
               } else {
                   // 3.2 用户名不存在 ——> 注册成功，调用service类方法，保存新的用户信息，并转发到注册成功页面
                   uService.register(new User(null, username, password, email));
                   req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
               }
           }
       }
   
       @Override
       protected void doGet(HttpServletRequest req, 
                            HttpServletResponse resp) throws ServletException, IOException {
           this.doPost(req, resp);
       }
   
       @Override
       protected void doPost(HttpServletRequest req, 
                             HttpServletResponse resp) throws ServletException, IOException {
           // 获取隐藏域中的请求参数
           String action = req.getParameter("action");
   
           // 根据获取的隐藏域参数判断执行哪一个功能
           if ("register".equals(action)) {
               // 执行注册功能
               register(req, resp);
           } else if ("login".equals(action)) {
               // 执行登录功能
               login(req, resp);
           }
       }
   }
   ```

3. 用==反射==取代UserServlet中的`if...else`语句

   > 使用反射时需要注意
   >
   > 1. 使用反射代替`if...else`语句时，需要根据隐藏域中的`value`属性值判断当前的功能，从而选择对应的方法，所以==`value`属性值必须和对应功能方法名保持一致==
   > 2. 通过反射获取对应方法对象时，使用`getDeclaredMethod()`，而不是`getMethod()`，因为Servlet里面的方法都是用`protected`修饰，`getMethod()`只能获取所有`public`修饰的方法
   > 3. 以后在用户模块（UserServlet）中添加功能时，就不用修改`doPost()`中的代码，只需添加功能对应的方法即可，但注意方法名必须和隐藏域中的`value`值一样

   ```java
   @Overrideprotected void doPost(HttpServletRequest req,                       HttpServletResponse resp) throws ServletException, IOException {    // 获取隐藏域中的请求参数    String action = req.getParameter("action");    try {        // 通过反射获取方法对象，注意这里不能使用getMethod()，因为这里的方法都不是public        Method userMethod = this.getClass().getDeclaredMethod(            action,            HttpServletRequest.class,            HttpServletResponse.class);        // 执行对应功能的方法        userMethod.invoke(this, req, resp);    } catch (Exception e) {        e.printStackTrace();    }}
   ```

#### 2.2.6 创建BaseServlet

* 将每个模块Servlet中的公共部分抽取出来，如

  1. 获取隐藏域中的`action`参数
  2. 根据隐藏域中的`value`值调用对应的功能方法
  3. 调用service类的方法，实现具体业务逻辑操作

* 将抽取出来的公共部分放到一个`BaseServlet`中，作为==每一个模块Servlet的父类==

  > PS：具有相同部分的模块，只需集成`BaseServlet`即可，不用集成`HttpServlet`

  ```java
  public abstract class BaseServlet extends HttpServlet {    @Override    protected void doGet(HttpServletRequest req,                          HttpServletResponse resp) throws ServletException, IOException {        this.doPost(req, resp);    }    @Override    protected void doPost(HttpServletRequest req,                           HttpServletResponse resp) throws ServletException, IOException {        // 获取隐藏域中的请求参数        String action = req.getParameter("action");        try {            // 通过反射获取方法对象，注意这里不能使用getMethod()，因为这里的方法都不是public            Method userMethod = this.getClass().getDeclaredMethod(                    action,                    HttpServletRequest.class,                    HttpServletResponse.class);            // 执行对应功能的方法            userMethod.invoke(this, req, resp);        } catch (Exception e) {            e.printStackTrace();        }    }}
  ```

#### 2.2.7 使用BeanUtils工具类

* 使用BeanUtils 工具类向对应的Bean 对象中注入参数值，简化操作

  1. 创建一个自定义的工具类`WebUtil`，在类中创建通用的方法，实现向bean中注入参数值

     ```java
     public class WebUtil {    /**     * 使用BeanUtils工具类注入参数到bean对象中     * @param paramMap 参数Map     * @param bean 需要注入参数（属性值）的对象     * @param <T> 返回对象泛型     * @return 返回注入参数后的bean     */    public static <T> T injectParamToBean(Map paramMap, T bean) {        try {            // 通过BeanUtils 工具类注入参数到Bean中            BeanUtils.populate(bean, paramMap);        } catch (IllegalAccessException | InvocationTargetException e) {            e.printStackTrace();        }        return bean;    }}
     ```

  2. 在模块Servlet 中的功能方法中，可以直接根据WebUtil 工具类向bean中注入请求参数值

     ```java
     protected void login(HttpServletRequest req,                      HttpServletResponse resp) throws ServletException, IOException {    // 1. 通过工具类获取请求参数    User user = WebUtil.injectParamToBean(req.getParameterMap(), new User());    String username = user.getUsername();    String password = user.getPassword();    // code...}
     ```

#### 2.2.8 使用EL表达式实现错误回显

* 将原来用脚本语言实现的错误信息回显改成用EL表达式实现，如对用户名和密码错误的信息回显

  ```jsp
  <%-- 获取request域中内容并显示 --%><span class="errorMsg">    ${empty requestScope.msg ? "请输入用户名和密码" : requestScope.msg}</span>
  ```

## 三、图书模块

### 3.1 模块分析与功能说明

#### 3.1.1 数据库表的创建

* 创建图书表——b_book

  > 表中的字段有：id、书名（title）、作者（author）、价格（price）、销量（sales）、库存（stock）、封面地址（img_path）
  >
  > PS：价格的类型为decimal(11,2)，其中11表示最多存储11位十进制数，包括小数点后的位数；2表示小数点后的位数。==对应java的数据类型位BigDecimal==（该类型是math包下的，不是lang包）

  ```sql
  CREATE TABLE b_book (
  	`id` INT PRIMARY KEY AUTO_INCREMENT,
  	`title` VARCHAR(100) NOT NULL,
  	`author` VARCHAR(100) NOT NULL,
  	`price` DECIMAL(11,2),
  	`sales` INT,
  	`stock` INT,
  	`img_path` VARCHAR(200) NOT NULL
  );
  ```

#### 3.1.2 创建数据库表对应的JavaBean

* 在entity 包下创建Book 类，成员变量分别对应b_book 的各个字段

  > PS：图书封面不能为空，所以需要给成员变量`imgPath`初始化默认的封面路径`"static/img/default.jpg"`

  ```java
  public class Book {
      private Integer id;
      private String title;
      private String author;
      private BigDecimal price;
      private Integer sales;
      private Integer stock;
      private String imgPath = "static/img/default.jpg";
  
      public Book() {
      }
  
      public Book(Integer id, String title, String author, BigDecimal price, Integer sales, Integer stock, String imgPath) {
          this.id = id;
          this.title = title;
          this.author = author;
          this.price = price;
          this.sales = sales;
          this.stock = stock;
  
          // 封面不能为空
          if (null != imgPath && ! "".equals(imgPath)) {
              this.imgPath = imgPath;
          }
      }
  
      public Integer getId() {
          return id;
      }
  
      public void setId(Integer id) {
          this.id = id;
      }
  
      public String getTitle() {
          return title;
      }
  
      public void setTitle(String title) {
          this.title = title;
      }
  
      public String getAuthor() {
          return author;
      }
  
      public void setAuthor(String author) {
          this.author = author;
      }
  
      public BigDecimal getPrice() {
          return price;
      }
  
      public void setPrice(BigDecimal price) {
          this.price = price;
      }
  
      public Integer getSales() {
          return sales;
      }
  
      public void setSales(Integer sales) {
          this.sales = sales;
      }
  
      public Integer getStock() {
          return stock;
      }
  
      public void setStock(Integer stock) {
          this.stock = stock;
      }
  
      public String getImgPath() {
          return imgPath;
      }
  
      public void setImgPath(String imgPath) {
          // 封面不能为空
          if (null != imgPath && ! "".equals(imgPath)) {
              this.imgPath = imgPath;
          }
      }
  
      @Override
      public String toString() {
          return "Book{" +
                  "id=" + id +
                  ", title='" + title + '\'' +
                  ", author='" + author + '\'' +
                  ", price=" + price +
                  ", sales=" + sales +
                  ", stock=" + stock +
                  ", imgPath='" + imgPath + '\'' +
                  '}';
      }
  }
  ```

#### 3.2.3 创建对应的Dao类

* BookDao 接口

  ```java
  public interface BookDao {    /**     * 添加新的图书     * @param newBook 新的图书对象     * @return 返回影响行数     */    int addBook(Book newBook);    /**     * 根据id删除对应的图书     * @param id 图书id     * @return 返回影响行数     */    int deleteBookById(int id);    /**     * 更新图书信息     * @param book 图书对象     * @return 返回影响行数     */    int updateBookInfo(Book book);    /**     * 根据id查询一条图书记录     * @param id 图书id     * @return 返回对应图书对象     */    Book queryBookById(int id);    /**     * 查询所有图书信息     * @return 返回图书列表     */    List<Book> queryBookList();}
  ```

* BookDao 实现类

  ```java
  public class BookDaoImpl extends BaseDao implements BookDao {    /**     * 添加新的图书     * @param newBook 新的图书对象     * @return 返回影响行数     */    @Override    public int addBook(Book newBook) {        // 创建SQL语句        String sql = "insert into b_book values(null, ?, ?, ?, ?, ?, ?)";        // 调用父类BaseDao的方法，更新数据库信息        return updateInfo(sql, newBook.getTitle(), newBook.getAuthor(),                newBook.getPrice(), newBook.getSales(), newBook.getStock(), newBook.getImgPath());    }    /**     * 根据id删除对应的图书     * @param id 图书id     * @return 返回影响行数     */    @Override    public int deleteBookById(int id) {        // 创建SQL语句        String sql = "delete from b_book where id = ?";        return updateInfo(sql, id);    }    /**     * 更新图书信息     * @param book 图书对象     * @return 返回影响行数     */    @Override    public int updateBookInfo(Book book) {        // 创建SQL语句        String sql = "update b_book " +                "set " +                "book_name = ?, author = ?, price = ?, sales = ?, stock = ?, img_path = ? " +                "where id = ?";        return updateInfo(sql, book.getTitle(), book.getAuthor(), book.getPrice(),                book.getSales(), book.getStock(), book.getImgPath(), book.getId());    }    /**     * 根据id查询一条图书记录     * @param id 图书id     * @return 返回对应图书对象     */    @Override    public Book queryBookById(int id) {        // 创建SQL语句        String sql = "select * from b_book where id = ?";        return queryForOne(Book.class, sql, id);    }    /**     * 查询所有图书信息     * @return 返回图书列表     */    @Override    public List<Book> queryBookList() {        // 创建SQL语句        String sql = "select * from b_book";        return queryForList(Book.class, sql);    }}
  ```

* 测试类

  ```java
  public class BookDaoTest {    BookDao bookDao = new BookDaoImpl();    @Test    public void addBook() {        int i = bookDao.addBook(new Book(null, "《java》", "小明",                new BigDecimal(999), 200, 400, null));        System.out.println(i);    }    @Test    public void deleteBookById() {        int i = bookDao.deleteBookById(3);        System.out.println(i);    }    @Test    public void updateBookInfo() {        int i = bookDao.updateBookInfo(new Book(1, "《C++》", "小红",                new BigDecimal(899), 200, 400, null));        System.out.println(i);    }    @Test    public void queryBookById() {        Book book = bookDao.queryBookById(2);        System.out.println(book);    }    @Test    public void queryBookList() {        List<Book> books = bookDao.queryBookList();        books.forEach(System.out :: println);    }}
  ```

#### 3.1.4 创建对应的Service类

* BookService 接口

  ```java
  public interface BookService {    /**     * 添加新的图书     * @param newBook 新的图书对象     * @return 返回影响行数     */    int addBook(Book newBook);    /**     * 根据id删除对应的图书     * @param id 图书id     * @return 返回影响行数     */    int deleteBook(int id);    /**     * 更新图书信息     * @param book 图书对象     * @return 返回影响行数     */    int updateBookInfo(Book book);    /**     * 根据id查询一条图书记录     * @param id 图书id     * @return 返回对应图书对象     */    Book queryBook(int id);    /**     * 查询所有图书信息     * @return 返回图书列表     */    List<Book> queryBooks();}
  ```

* BookService 实现类

  ```java
  public class BookServiceImpl implements BookService {    private final BookDao bDao = new BookDaoImpl();    /**     * 添加新的图书     * @param newBook 新的图书对象     * @return 返回影响行数     */    @Override    public int addBook(Book newBook) {      return bDao.addBook(newBook);    }    /**     * 根据id删除对应的图书     * @param id 图书id     * @return 返回影响行数     */    @Override    public int deleteBook(int id) {        return bDao.deleteBookById(id);    }    /**     * 更新图书信息     * @param book 图书对象     * @return 返回影响行数     */    @Override    public int updateBookInfo(Book book) {        return bDao.updateBookInfo(book);    }    /**     * 根据id查询一条图书记录     * @param id 图书id     * @return 返回对应图书对象     */    @Override    public Book queryBook(int id) {        return bDao.queryBookById(id);    }    /**     * 查询所有图书信息     * @return 返回图书列表     */    @Override    public List<Book> queryBooks() {        return bDao.queryBookList();    }}
  ```

* 测试类

  ```java
  public class BookServiceTest {    BookService bookService = new BookServiceImpl();    @Test    public void addBook() {        int i = bookService.addBook(new Book(null, "《JQuery》", "小蓝",                new BigDecimal(119), 200, 400, null));        System.out.println(i);    }    @Test    public void deleteBook() {        int i = bookService.deleteBook(3);        System.out.println(i);    }    @Test    public void updateBookInfo() {        int i = bookService.updateBookInfo(new Book(2, "《Mybatis》", "小谭",                new BigDecimal(899), 200, 400, null));        System.out.println(i);    }    @Test    public void queryBook() {        Book book = bookService.queryBook(2);        System.out.println(book);    }    @Test    public void queryBooks() {        List<Book> books = bookService.queryBooks();        books.forEach(System.out :: println);    }}
  ```

#### 3.1.5 后台Servlet和前端页面的编写

> BookServlet 继承BaseServlet，里面只需创建功能方法
>
> 1. 添加图书：`addBook()`
> 2. 删除图书：`deleteBook()`
> 3. 修改图书信息：`updateBookInfo()`
> 4. 查询所有图书信息：`queryBooks()`
>
> 注意：BookServlet的资源路径设置为`/manager/bookServlet`，因为图书模块属于后台管理，需要权限才能访问，用于区分前台的Servlet

##### a. 查询所有图书信息

1. 在后台管理页面（manager.jsp）上点击 <u>图书管理</u> 的超链接，先跳转到BookServlet的`queryBooks()`方法上，查询所有图书信息

   ```jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %><div>  <a href="manager/bookServlet?action=queryBooks">图书管理</a>  <a href="order_manager.jsp">订单管理</a>  <a href="../../index.jsp">返回商城</a></div>
   ```

2. 在BookServlet的`queryBooks()`中，先调用service类方法查询所有图书信息，然后将信息存储到request域中，再请求转发到图书列表页面（book_manager.jsp）

   ```java
   protected void queryBooks(HttpServletRequest req,                           HttpServletResponse resp) throws ServletException, IOException {    // 1. 调用service类方法查询所有图书信息    List<Book> books = bService.queryBooks();    // 2. 将信息存储到request域中    req.setAttribute("books", books);    // 3. 请求转发到图书列表页面（book_manager.jsp）    req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);}
   ```

3. 在图书列表页面（book_manager.jsp）获取request域中的图书信息，并使用JSTL标签在页面上展示出所有图书信息

   ```jsp
   <%-- 使用JSTL标签中的forEach，遍历request域中的list信息 --%><c:forEach items="${requestScope.books}" var="book">    <tr>        <td>${book.title}</td>        <td>${book.price}</td>        <td>${book.author}</td>        <td>${book.sales}</td>        <td>${book.stock}</td>        <td><a href="book_edit.jsp">修改</a></td>        <td><a href="#">删除</a></td>    </tr></c:forEach>
   ```

##### b. 添加图书

1. 在图书列表页面（book_manager.jsp）点击 <u>添加图书</u> 的超链接，会跳转到添加图书页面（book_edit.jsp）

2. 在添加图书页面（book_edit.jsp）编辑要添加的图书信息，再点击 <u>提交</u> 按钮，将表单信息提交给BookServlet（跳转到BookServlet），并调用其`addBook()`方法

   ```jsp
   <%-- 添加新图书的表单 --%><form action="manager/bookServlet" method="post">    <%-- 添加隐藏域，用于标识对应的功能方法 --%>    <input type="hidden" name="action" value="addBook">    <%-- code... --%></form>
   ```

3. BookServlet的`addBook()`方法中，先获取请求参数（表单信息），并将参数封装成对象，再调用service类方法将该对象添加到数据库中，然后采用重定向跳转回到BookServlet，并访问`queryBooks()`方法，即回到图书列表页面

   > PS1：这里资源跳转采用重定向，不能使用请求转发，使用请求转发会出现==表单重复提交的问题==！问题的根本原因是采用请求转发时，Servlet处理完请求后会直接转发到目标页面，而且请求转发是一次请求，所以在浏览器中点击刷新（点F5）会一直处理之前的请求（增删改操作都不能使用请求转发！）
   >
   > PS2：最后重定向跳转回图书列表页面，==不能直接跳转回book_manager.jsp==，需要先跳转回BookServlet同时带上action=queryBooks的请求参数，重新请求所有图书信息（相当于刷新页面）

   ```java
   protected void addBook(HttpServletRequest req,                        HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取请求参数，并封装成对象    Book newBook = WebUtil.injectParamToBean(req.getParameterMap(), new Book());    // 2. 调用service类方法，将新的图书信息添加到数据库中    bService.addBook(newBook);    // 3. 采用重定向跳转回到图书列表页面（/manager/bookServlet?action=queryBooks），注意这里不是直接跳回去book_manager.jsp    resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=queryBooks");}
   ```

##### c. 删除图书

1. 在图书列表页面（book_manager.jsp）上每一行图书都有 <u>删除</u> 的超链接，点击后会==先弹出提示框==，提示`是否删除该图书信息？`，如果点击确认键就跳转到BookServlet，带上参数`action=deleteBook&id=${book.id}`，并调用对应功能方法`deleteBook()`；如果点击取消则页面不跳转，也不删除对应图书信息

   ```jsp
   <%-- 通过JS代码向删除按键绑定单击事件 --%><script type="text/javascript">    // 页面加载完成后    $(function () {        // 给删除按钮绑定单机事件，弹出提示框        $("a.delClass").click(function () {            // 直接返回，如果点击确认返回true，提交信息到servlet，如果点击取消返回false，不提交            return confirm("您确定要删除["+                            $(this).parent().parent().find("td:first").text() +                           "]的全部信息吗？");        })    })</script><%--+----------------------------------------------------------------+--%><td><a class="delClass" href="manager/bookServlet?action=deleteBook&id=${book.id}">删除</a></td>
   ```

2. 跳转到BookServlet后，调用`deleteBook()`方法，方法中先获取请求参数，然后调用service类方法实现对应图书信息的删除，最后再采用重定向跳转回BookServlet的`queryBooks()`方法，即跳转回图书列表页面

   > PS：在`deleteBook()`方法中获取请求参数时，需要将获取的id值（String类型）转成int型，再调用service方法

   ```java
   protected void deleteBook(HttpServletRequest req,                           HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取请求参数    String id = req.getParameter("id");    // 1.1 将id转成int型，如果转化失败，返回默认值0    int bookId = WebUtil.parseInt(id, 0);    // 2. 调用service类方法，实现删除功能    bService.deleteBook(bookId);    // 3. 重定向跳转回图书列表页面，并刷新数据    resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=queryBooks");}
   ```

##### d. 更新图书信息

1. 在图书列表页面（book_manager.jsp）每一行图书信息都有一个 <u>修改</u> 超链接，点击先跳转到BookServlet，并带上参数`action=getBookInfo&id=${book.id}`，调用BookServlet中的`getBookInfo()`方法

   ```jsp
   <td><a href="manager/bookServlet?action=getBookInfo&id=${book.id}">修改</a></td>
   ```

2. 在BookServlet的`getBookInfo()`方法中，先获取请求参数中的id值，然后根据id调用service类方法，查询出对应的图书对象，并将该对象存储到request域中，最后请求转发到图书修改页面（book_edit.jsp）

   ```java
   protected void getBookInfo(HttpServletRequest req,                            HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取图书id，先转成int型    int id = WebUtil.parseInt(req.getParameter("id"), 0);    // 2. 根据id调用service方法，查询对应图书对象    Book book = bService.queryBook(id);    // 3. 将该对象存储到request域中    req.setAttribute("book", book);    // 4. 最后请求转发到图书更新页面    req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req, resp);}
   ```

3. 在图书修改页面（book_edit.jsp）上回显原来图书的各个信息，在修改信息后点击提交按键，会跳转回BookServlet，并带上表单的各个参数值（包括隐藏域中`action=updateBookInfo`和`action=id`），然后调用BookServlet中的`updateBookInfo()`方法

   ```jsp
   <input type="hidden" name="action" value="${empty requestScope.book ? "addBook" : "updateBookInfo"}"><input type="hidden" name="id" value="${requestScope.book.id}">
   ```

   > PS：第一个隐藏域中的value属性值是一个动态值，因为==`book_edit.jsp`是图书添加和图书修改共用的页面==，所以value值不能写死，需要根据当前==request域中的book对象是否为空==来判断当前访问的是修改页面还是添加页面

4. 在BookServlet的`updateBookInfo()`方法中，先获取请求参数并将参数封装成图书对象，然后调用service类方法，将该对象信息存储到数据库中，最后采用重定向跳转到`/manager/bookServlet?action=queryBooks`，回到图书列表页面，刷新列表数据

   ```java
   protected void updateBookInfo(HttpServletRequest req,                               HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取表单的内容，并封装成图书对象    Book newBook = WebUtil.injectParamToBean(req.getParameterMap(), new Book());    // 2. 调用service方法，将更新后的图书对象存储到数据库中    bService.updateBookInfo(newBook);    // 3. 最后重定向跳转到图书列表页面    resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=queryBooks");}
   ```

***

***

### 3.2 分页功能的实现1.0

> PS：对后台图书管理列表进行分页展示

#### 3.2.1 前端页面修改

1. 将首页中的 <u>图书管理</u> 超链接按键跳转修改成`/manager/bookServlet?action=pageBooks`，表示一开始进入图书列表页面就进行分页，而不是查询出全部图书信息

   ```jsp
   <a href="manager/bookServlet?action=pageBooks">图书管理</a>
   ```

2. 修改图书列表页面（book_manager.jsp）中遍历集合，之前是直接遍历全部图书信息，现改成仅遍历当前页的图书信息

   ```jsp
   <c:forEach items="${requestScope.booksPerPage.infoPerPage}" var="book">    <%-- code... --%></c:forEach>
   ```


#### 3.2.2 创建分页对象Page

1. 创建一个分页对象（Page），用于封装分页所需的数据，Page对象中有以下几个成员变量

   * currentPage：当前页；从客服端获取

   * pageSize：每页记录数量；从客服端获取，默认是4

   * totalCount：总记录数；访问数据库获取

     > SQL语句：select count(*) from b_book;

   * totalPage：总页码；根据总记录数和每页记录数量计算得出，如果总记录数/每页记录数量余数不为0，需要再加1

     > 计算公式：totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1)

   * infoPerPage：每页显示的数据；访问数据库获取

     > SQL语句（分页查询）：select * from b_book limit [初始页码] , [查询记录数];
     >
     > * 初始页码 = (currentPage - 1) * pageSize
     > * 查询记录数 = pageSize

   ```java
   public class Page<T> {
       public static final Integer PAGE_SIZE = 4;
   
       private Integer currentPage;
       private Integer pageSize = PAGE_SIZE;
       private Integer totalPage;
       private Integer totalCount;
       private List<T> infoPerPage;
   
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
   
       @Override
       public String toString() {
           return "Page{" +
                   "currentPage=" + currentPage +
                   ", pageSize=" + pageSize +
                   ", totalPage=" + totalPage +
                   ", totalCount=" + totalCount +
                   ", infoPerPage=" + infoPerPage +
                   '}';
       }
   }
   ```


#### 3.2.3 servlet、service和dao类的分页方法

1. 在图书列表页面点击对应页数，跳转到BookServlet，并带上参数currentPage（当前页）和pageSize（每页显示数据数量），调用BookServlet中的`PageBooks()`方法

2. 在BookServlet的`PageBooks()`方法中，先获取请求参数currentPage和pageSize，再根据两个参数值调用service类方法获取对应的分页对象，然后将该对象存储到request域中，最后请求转发到图书列表页面（boo_manager.jsp）

   > PS：最后使用请求转发直接回到boo_manager.jsp 即可，不需要刷新页面，因为数据没有更新

   ```java
   protected void pageBooks(HttpServletRequest req,                          HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取请求参数 --> currentPage、pageSize，如果获取不到，就采用默认值1和4    int currentPage = WebUtil.parseInt(req.getParameter("currentPage"), 1);    int pageSize = WebUtil.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);    // 2. 根据获取的参数调用service方法，获取分页对象    Page<Book> booksPerPage = bService.pageBooks(currentPage, pageSize);    // 3. 将获取的对象存储到request域中    req.setAttribute("booksPerPage", booksPerPage);    // 4. 请求转发到图书列表页面    req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);}
   ```

3. 在service类的分页方法中，需要对分页对象的各个成员变量进行赋值，然后返回封装好数据的分页对象。当前页面和每页数量从形参（Servlet传过来）中可以获取，总记录数和每页显示的数据需要调用dao类访问数据库获取，总页码需要根据总记录数和每页记录数量计算得出

   * service实现类的分页方法

     ```java
     @Override
     public Page<Book> pageBooks(int currentPage, int pageSize) {
         // 先创建出一个分页对象
         Page<Book> booksPerPage = new Page<>();
     
         // 1. 注入当前页码
         booksPerPage.setCurrentPage(currentPage);
     
         // 2. 注入每页记录数
         booksPerPage.setPageSize(pageSize);
     
         // 3. 注入总记录数：调用dao类方法，查询出总记录数
         int totalCount = bDao.queryTotalCount();
         booksPerPage.setTotalCount(totalCount);
     
         // 4. 注入总页码：通过总记录数和每页记录数计算得出
         int totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
         booksPerPage.setTotalPage(totalPage);
     
         // 5. 注入每页显示的信息：调用dao类方法，查询出每页信息
         // 5.1 先算出初始页码
         int beginPage = (currentPage - 1) * pageSize;
         List<Book> books = bDao.queryBooksPerPage(beginPage, pageSize);
         booksPerPage.setInfoPerPage(books);
     
         return booksPerPage;
     }
     ```

   * dao实现类的方法

     ```java
     // 查询每页显示的信息
     @Override
     public List<Book> queryBooksPerPage(int beginPage, int pageSize) {
         // 创建SQL语句
         String sql = "select * from b_book limit ? , ?";
     
         // 调用BaseDao方法
         return queryForList(Book.class, sql, beginPage, pageSize);
     }
     
     // 查询总记录数
     @Override
     public int queryTotalCount() {
         // 创建SQL语句
         String sql = "select count(*) from b_book";
     
         // 这里方法返回值是Object，需要先转成Number类型，再转成int
         Number count = (Number) queryForValue(sql);
     
         return count.intValue();
     }
     ```

     > PS：Number类型


#### 3.2.4 添加并修改分页条

1. 在图书列表下添加分页条

   ```jsp
   <%-- 分页条 --%><div id="page_nav"> <a href="#">首页</a> <a href="#">上一页</a> <a href="#">3</a> 【4】 <a href="#">5</a> <a href="#">下一页</a> <a href="#">末页</a> 共10页，30条记录 到第<input value="4" name="pn" id="pn_input"/>页 <input type="button" value="确定"></div>
   ```

2. 将某些静态值改成动态，以及修改跳转页面的按键

   ```jsp
   <div id="page_nav">    <a href="manager/bookServlet?action=pageBooks&currentPage=1">首页</a>    <a href="manager/bookServlet?action=pageBooks&currentPage=${requestScope.booksPerPage.currentPage - 1}">上一页</a>    <a href="#">3</a>    【${requestScope.booksPerPage.currentPage}】    <a href="#">5</a>    <a href="manager/bookServlet?action=pageBooks&currentPage=${requestScope.booksPerPage.currentPage + 1}">下一页</a>    <a href="manager/bookServlet?action=pageBooks&currentPage=${requestScope.booksPerPage.totalPage}">末页</a>    共${requestScope.booksPerPage.totalPage}页，${requestScope.booksPerPage.totalCount}条记录    到第<input value="4" name="pn" id="pn_input"/>页    <input type="button" value="确定"></div>
   ```

3. 根据当前页码是首页还是末页，判断是否保留各个跳转页面的按键

   ```jsp
   <%-- 如果当前页码大于1，即不是首页，才显示首页和上一页的按键 --%><c:if test="${requestScope.booksPerPage.currentPage > 1}">    <a href="manager/bookServlet?action=pageBooks&currentPage=1">首页</a>    <a href="manager/bookServlet?action=pageBooks&currentPage=${requestScope.booksPerPage.currentPage - 1}">上一页</a></c:if><%-- code... --%><%-- 如果当前页码小于总页码，才显示下一页和末页的按键 --%><c:if test="${requestScope.booksPerPage.currentPage < requestScope.booksPerPage.totalPage}">    <a href="manager/bookServlet?action=pageBooks&currentPage=${requestScope.booksPerPage.currentPage + 1}">下一页</a>    <a href="manager/bookServlet?action=pageBooks&currentPage=${requestScope.booksPerPage.totalPage}">末页</a></c:if>
   ```

4. 跳转到指定页码，通过JS中的`location`对象修改浏览器地址栏的地址，实现跳转到指定页码，并页面上回显当前页码

   ```jsp
   到第<input value="4" name="pn" id="pn_input"/>页<input id="searchPageBth" type="button" value="确定"><%-- 利用JS代码给按键绑定单击事件，实现指定页码的跳转 --%><script type="text/javascript">    // 当页面加载完成后    $(function () {        // 绑定单机事件        $("#searchPageBth").click(function () {            // 先获取文本框内容，即需要跳转的页码            let pageText = $("#pn_input").val();            // 通过location对象修改地址栏地址，实现页面跳转            location.href = "${pageScope.basePath}manager/bookServlet?action=pageBooks&currentPage=" + pageText;        })    })</script>
   ```

   > PS：${pageScope.basePath}表示地址的前缀，即`协议://IP地址:端口号/工程目录/`

5. 分页条中页码的输出：每次最多显示5个页码，将当前页码标识出来，而且当前页码要在中间（不能是边界值）

   > PS：下面用currPage和totalPage分别代替当前页码和总页码

   * 分类

     1. totalPage小于5，显示页码范围就是 [1 , totalPage]

        ```jsp
        <%-- 1. 总页码小于5，页码范围 [1,totalPage] --%><c:when test="${requestScope.pageOfBook.totalPage < 5}">    <c:forEach begin="1" end="${requestScope.pageOfBook.totalPage}" var="i">        <%-- 如果是当前页码，就标识出来，并不能点击 --%>        <c:if test="${i == requestScope.pageOfBook.currentPage}">            【${i}】        </c:if>        <%-- 如果不是当前页码，就设置为超链接，跳转到对应页码的页面 --%>        <c:if test="${i != requestScope.pageOfBook.currentPage}">            <a href="manager/bookServlet?action=pageBooks&currentPage=${i}">${i}</a>        </c:if>    </c:forEach></c:when>
        ```

     2. totalPage大于5

        * currPage为 1、2、3时，显示页码范围为 [1 , 5]

        ```jsp
        <%-- 2.1 当前页码为1、2、3，页码范围 [1,5] --%><c:when test="${requestScope.pageOfBook.currentPage <= 3}">    <c:forEach begin="1" end="5" var="i">        <%-- 如果是当前页码，就标识出来，并不能点击 --%>        <c:if test="${i == requestScope.pageOfBook.currentPage}">            【${i}】        </c:if>        <%-- 如果不是当前页码，就设置为超链接，跳转到对应页码的页面 --%>        <c:if test="${i != requestScope.pageOfBook.currentPage}">            <a href="manager/bookServlet?action=pageBooks&currentPage=${i}">${i}</a>        </c:if>    </c:forEach></c:when>
        ```

        * currPage为 totalPage-2、totalPage-1、totalPage 时，显示页码范围为 [totalPage-4 , totalPage]

        ```jsp
        <%-- 2.2 当前页码为totalPage-2、totalPage-1、totalPage，页码范围 [totalPage-4,totalPage] --%><c:when test="${requestScope.pageOfBook.currentPage >= requestScope.pageOfBook.totalPage - 2}">    <c:forEach begin="${requestScope.pageOfBook.totalPage - 4}" end="${requestScope.pageOfBook.totalPage}" var="i">        <%-- 如果是当前页码，就标识出来，并不能点击 --%>        <c:if test="${i == requestScope.pageOfBook.currentPage}">            【${i}】        </c:if>        <%-- 如果不是当前页码，就设置为超链接，跳转到对应页码的页面 --%>        <c:if test="${i != requestScope.pageOfBook.currentPage}">            <a href="manager/bookServlet?action=pageBooks&currentPage=${i}">${i}</a>        </c:if>    </c:forEach></c:when>
        ```

        * currPage为其他情况时，显示页码范围为 [currPage-2 , currPage+2]

        ```jsp
        <%-- 2.3 当前页码为其他情况，页码范围 [currPage-2,currPage+2] --%><c:otherwise>    <c:forEach begin="${requestScope.pageOfBook.currentPage - 2}"               end="${requestScope.pageOfBook.currentPage + 2}" var="i">        <%-- 如果是当前页码，就标识出来，并不能点击 --%>        <c:if test="${i == requestScope.pageOfBook.currentPage}">            【${i}】        </c:if>        <%-- 如果不是当前页码，就设置为超链接，跳转到对应页码的页面 --%>        <c:if test="${i != requestScope.pageOfBook.currentPage}">            <a href="manager/bookServlet?action=pageBooks&currentPage=${i}">${i}</a>        </c:if>    </c:forEach></c:otherwise>
        ```

   * 合并代码并简化

     ```jsp
     <%-- 页码的显示 --%><c:choose>    <%-- 1. 总页码小于5，页码范围 [1,totalPage] --%>    <c:when test="${requestScope.pageOfBook.totalPage < 5}">        <%-- 直接修改循环遍历的起始和结束 --%>        <c:set var="beginPage" value="1"></c:set>        <c:set var="endPage" value="${requestScope.pageOfBook.totalPage}"></c:set>    </c:when>    <%-- 2. 总页码大于5，继续细分 --%>    <c:otherwise>        <c:choose>            <%-- 2.1 当前页码为1、2、3，页码范围 [1,5] --%>            <c:when test="${requestScope.pageOfBook.currentPage <= 3}">                <%-- 直接修改循环遍历的起始和结束 --%>                <c:set var="beginPage" value="1"></c:set>                <c:set var="endPage" value="5"></c:set>            </c:when>            <%-- 2.2 当前页码为totalPage-2、totalPage-1、totalPage，页码范围 [totalPage-4,totalPage] --%>            <c:when test="${requestScope.pageOfBook.currentPage >= requestScope.pageOfBook.totalPage - 2}">                <%-- 直接修改循环遍历的起始和结束 --%>                <c:set var="beginPage" value="${requestScope.pageOfBook.totalPage - 4}"></c:set>                <c:set var="endPage" value="${requestScope.pageOfBook.totalPage}"></c:set>            </c:when>            <%-- 2.3 当前页码为其他情况，页码范围 [currPage-2,currPage+2] --%>            <c:otherwise>                <%-- 直接修改循环遍历的起始和结束 --%>                <c:set var="beginPage" value="${requestScope.pageOfBook.currentPage - 2}"></c:set>                <c:set var="endPage" value="${requestScope.pageOfBook.currentPage + 2}"></c:set>            </c:otherwise>        </c:choose>    </c:otherwise></c:choose><%-- 将每种情况的页码显示都要循环的操作抽取出来 --%><c:forEach begin="${beginPage}"           end="${endPage}" var="i">    <%-- 如果是当前页码，就标识出来，并不能点击 --%>    <c:if test="${i == requestScope.pageOfBook.currentPage}">        【${i}】    </c:if>    <%-- 如果不是当前页码，就设置为超链接，跳转到对应页码的页面 --%>    <c:if test="${i != requestScope.pageOfBook.currentPage}">        <a href="manager/bookServlet?action=pageBooks&currentPage=${i}">${i}</a>    </c:if></c:forEach>
     ```

#### 3.2.5 数据有效性校验

* 前台数据校验：==通过JS代码==对文本框的内容进行校验，如果输入的页码值不合法就弹出警告框，阻止提交到服务器，如果合法就直接跳转

  ```jsp
  <%-- 利用JS代码给按键绑定单击事件，实现指定页码的跳转 --%><script type="text/javascript">    // 当页面加载完成后    $(function () {        // 绑定单机事件        $("#searchPageBth").click(function () {            // 先获取文本框内容，即需要跳转的页码            let pageText = $("#pn_input").val();            // 获取总页码            let totalPage = ${requestScope.pageOfBook.totalPage};            // 判断文本框内容即页码是否合法，不合法就弹出警示框，并阻止跳转            if (pageText <= 0 || pageText > totalPage) {                alert("输入页码不合法！页码范围是：[1 , " + totalPage +"]");            } else {                // 通过location对象修改地址栏地址，实现页面跳转                location.href = "${pageScope.basePath}manager/bookServlet?action=pageBooks&currentPage=" + pageText;            }        })    })</script>
  ```

* 后台数据校验

  > 为什么要做后台的数据校验：因为用户可能会直接修改浏览器地址栏中链接的页码，从而跳过前端，直接访问服务器端，数据也就直接进到服务器端，因此有必要在后台也进行数据的校验

  1. 在WebUtil工具类中创建检验输入页码合法性并返回合法页码的方法`getLegalPage()`

     ```java
     /** * 判断页码是否合法 * @param currPage 未校验的当前页码 * @param totalPage 总页码 * @return 如果页码小于1，就返回1，如果页码大于总页码，就返回总页码 */public static int getLegalPage(int currPage, int totalPage) {    int legalPage;    // 如果页码小于1，就设置为1    legalPage = Math.max(currPage, 1);    // 如果页码大于总页码，就设置为总页码    legalPage = currPage > totalPage ? totalPage : legalPage;    return legalPage;}
     ```

  2. 修改service实现类对应的分页方法，加上页码的校验

     ```java
     @Overridepublic Page<Book> pageBooks(int currentPage, int pageSize) {    // 1. 调用dao类方法，查询出总记录数    int totalCount = bDao.queryTotalCount();    // 2. 通过总记录数和每页记录数计算得出总页码    int totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);    /*             3. 对当前页码进行校验，获取合法值               必须在查询每页信息前以及在计算出总页码后，进行校验，不然查出的信息会出错        */    int legalCurrPage = WebUtil.getLegalPage(currentPage, totalPage);    // 4. 调用dao类方法，查询出每页信息    // 4.1 先算出初始页码，用合法的当前页码进行计算    int beginPage = (legalCurrPage - 1) * pageSize;    List<Book> booksPerPage = bDao.queryBooksPerPage(beginPage, pageSize);    // 5. 注入各个属性并返回    return new Page<>(legalCurrPage, pageSize, totalPage, totalCount, booksPerPage);}
     ```

#### 3.2.6 添加分页后对增删改的影响

* 添加图书功能的修改

  > 修改说明：在添加图书信息提交表单后，回到图书列表列表页面应该是最后一页，显示出用户新添加的用户信息

  1. 在BookServlet 中增删改的对应功能方法的重定向操作中，将跳转的资源路径修改成跳转到BookServlet的分页方法中，即`/manager/bookServlet?action=pageBooks`

  2. 在图书列表页面上的 <u>添加图书</u> 超链接跳转的资源上添加请求参数，将==最后一页的页码==传给下一个页面，即添加图书页面（book_edit.jsp）

     ```jsp
     <a href="pages/manager/book_edit.jsp?updatePage=${requestScope.pageOfBook.totalPage}">添加图书</a>
     ```

  3. 在添加图书页面即图书编辑页面（book_edit.jsp）上的表单中再添加一个隐藏域，隐藏域中的`value`值为列表页面传过来的最后一页的页码，提交表单后就能将该页码一并提交给BookServlet

     ```jsp
     <input type="hidden" name="updatePage" value="${param.updatePage}">
     ```

  4. 在BookServlet 的添加功能方法`addBook()`中，获取隐藏域中的参数值，即末页的页码，然后在获取的页码基础上再加一得到新的页码参数，最后将该新的页码参数作为==当前页码（currentPage）请求参数==，添加到重定向的跳转资源路径后

     > 页码加一的目的
     >
     > 1. 如果添加新的图书前，末页中能显示的图书信息已满，则再添加新的图书后，就会在原来基础上增加一页，所以此时的末页已经不是原来的末页，必要在原基础上加一才能显示出最后一条信息（新信息）
     > 2. 如果添加新的图书前，末页中能显示的图书信息未满，则再添加新的图书后，不会增加新一页，即传给后台Servlet的末页页码不变，而后台对末页页码再加一，虽然改变了末页页码，但由于后台的数据有效性校验，页码超过末页后还是会显示末页的信息，所以加一的操作对信息的显示没影响

     ```java
     protected void addBook(HttpServletRequest req,                        HttpServletResponse resp) throws ServletException, IOException {    // 获取隐藏域中的末页页码，转成int型并加一（默认值是0，因为要加1）    int updatePage = WebUtil.parseInt(req.getParameter("updatePage"), 0) + 1;    // code...        resp.sendRedirect(req.getContextPath() +                       "/manager/bookServlet?action=pageBooks&currentPage=" +                       updatePage);}
     ```

* 删除功能的修改（修改功能类似）

  1. 在图书列表页面（book_manager.jsp）上的 <u>删除</u> 超链接跳转的资源路径后添加请求参数，将==当前页码==传给BookServlet的对应功能方法中

     ```jsp
     <a class="delClass" href="manager/bookServlet?action=deleteBook&id=${book.id}&updatePage=${requestScope.pageOfBook.currentPage}">    删除</a>
     ```

  2. 在BookServlet 的`deletBook()`方法中，获取请求参数值，即当前页码并作为请求参数，添加到重定向的资源路径后

     ```java
     resp.sendRedirect(req.getContextPath() +                  "/manager/bookServlet?action=pageBooks&currentPage=" +                  req.getParameter("updatePage"));
     ```

***

### 3.3 分页功能的实现2.0

> PS：对前台（首页）的图书列表页面进行分页展示

#### 3.3.1 创建ClientBookServlet

```java
public class ClientBookServlet extends BaseServlet {    private final BookService bService = new BookServiceImpl();    /**     * 查询查询每页的图书信息     */    protected void pageCBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {                // code...    }}
```

#### 3.3.2 修改index.jsp 

1. 在pages目录下创建一个client目录，并在client目录下再创建一个bookCityIndex.jsp，与web目录下的index.jsp一样

2. 在 index.jsp 中不获取数据，也不展示数据，只需要请求转发到ClientBookServlet，并带上请求参数`action=pageBooks`，即访问ClientBookServlet中的`pageBooks()`方法

   ```jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %><%-- 请求转发到ClientBookServlet --%><jsp:forward page="client/cBookServlet?action=pageCBooks"></jsp:forward>
   ```

3. 在ClientBookServlet的`pageCBooks()`方法中获取当前页码currentPage和每页显示的记录数pageSize，然后根据这两个参数值调用service类方法，获取对应的分页对象，再将该对象存储到request域中，最后请求转发到 bookCityIndex.jsp

   > PS：`pageCBooks()`和BookServlet中的`pageBooks()`方法的实现差不多，就是重定向时跳转的路径不同

   ```java
   protected void pageCBooks(HttpServletRequest req,                          HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取请求参数 --> currentPage、pageSize，如果获取不到，就采用默认值1和4    int currentPage = WebUtil.parseInt(req.getParameter("currentPage"), 1);    int pageSize = WebUtil.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);    // 2. 根据获取的参数调用service方法，获取分页对象    Page<Book> pageOfBook = bService.pageBooks(currentPage, pageSize);    // 3. 将获取的对象存储到request域中    req.setAttribute("pageOfBook", pageOfBook);    // 4. 请求转发到bookCityIndex.jsp    req.getRequestDispatcher("/pages/client/bookCityIndex.jsp").forward(req, resp);}
   ```

4. 在 bookCityIndex.jsp 中用jstl标签遍历图书信息，并展示到页面上

   ```jsp
   <%-- 图书信息列表，用jstl标签循环遍历 --%><c:forEach items="${requestScope.pageOfBook.infoPerPage}" var="book">    <div class="b_list">        <div class="img_div">            <img class="book_img" alt="" src="${book.imgPath}" />        </div>        <div class="book_info">            <div class="book_name">                <span class="sp1">书名:</span>                <span class="sp2">${book.title}</span>            </div>            <div class="book_author">                <span class="sp1">作者:</span>                <span class="sp2">${book.author}</span>            </div>            <div class="book_price">                <span class="sp1">价格:</span>                <span class="sp2">￥${book.price}</span>            </div>            <div class="book_sales">                <span class="sp1">销量:</span>                <span class="sp2">${book.sales}</span>            </div>            <div class="book_amount">                <span class="sp1">库存:</span>                <span class="sp2">${book.stock}</span>            </div>            <div class="book_add">                <button>加入购物车</button>            </div>        </div>    </div></c:forEach>
   ```

#### 3.3.3 抽取分页条

1. 在分页对象Page 中添加成员变量——请求地址reqPath，用于区分分页条对不同资源的请求

   ```java
   private String reqPath;
   ```

2. 在ClientBookServlet和BookServlet两个类中对应的分页方法中，在将分页对象存储到request域前，对分页对象的请求地址reqPath进行赋值

   > PS：千万注意路径前面不要再加 `/` ！

   ```java
   // ClientBookServlet 中设置请求路径pageOfBook.setReqPath("client/cBookServlet?action=pageCBooks");// BookServlet 中设置请求路径pageOfBook.setReqPath("manager/bookServlet?action=pageBooks");
   ```

3. 将分页条代码中的请求地址改成动态的，即用EL表达式获取request域中分页对象的reqPath属性值，即将原来写死的`manager/bookServlet?action=pageBooks`改成`${requestScope.pageOfBook.reqPath}`

   ```jsp
   <%-- 分页条 --%><div id="page_nav">    <%-- 如果当前页码大于1，即不是首页，才显示首页和上一页的按键 --%>    <c:if test="${requestScope.pageOfBook.currentPage > 1}">        <a href="${requestScope.pageOfBook.reqPath}&currentPage=1">首页</a>        <a href="${requestScope.pageOfBook.reqPath}&currentPage=${requestScope.pageOfBook.currentPage - 1}">上一页</a>    </c:if>    <%-- 页码的显示 --%>    <c:choose>        <%-- 1. 总页码小于5，页码范围 [1,totalPage] --%>        <c:when test="${requestScope.pageOfBook.totalPage < 5}">            <%-- 直接修改循环遍历的起始和结束 --%>            <c:set var="beginPage" value="1"></c:set>            <c:set var="endPage" value="${requestScope.pageOfBook.totalPage}"></c:set>        </c:when>        <%-- 2. 总页码大于5，继续细分 --%>        <c:otherwise>            <c:choose>                <%-- 2.1 当前页码为1、2、3，页码范围 [1,5] --%>                <c:when test="${requestScope.pageOfBook.currentPage <= 3}">                    <%-- 直接修改循环遍历的起始和结束 --%>                    <c:set var="beginPage" value="1"></c:set>                    <c:set var="endPage" value="5"></c:set>                </c:when>                <%-- 2.2 当前页码为totalPage-2、totalPage-1、totalPage，页码范围 [totalPage-4,totalPage] --%>                <c:when test="${requestScope.pageOfBook.currentPage >= requestScope.pageOfBook.totalPage - 2}">                    <%-- 直接修改循环遍历的起始和结束 --%>                    <c:set var="beginPage" value="${requestScope.pageOfBook.totalPage - 4}"></c:set>                    <c:set var="endPage" value="${requestScope.pageOfBook.totalPage}"></c:set>                </c:when>                <%-- 2.3 当前页码为其他情况，页码范围 [currPage-2,currPage+2] --%>                <c:otherwise>                    <%-- 直接修改循环遍历的起始和结束 --%>                    <c:set var="beginPage" value="${requestScope.pageOfBook.currentPage - 2}"></c:set>                    <c:set var="endPage" value="${requestScope.pageOfBook.currentPage + 2}"></c:set>                </c:otherwise>            </c:choose>        </c:otherwise>    </c:choose>    <%-- 将每种情况的页码显示都要循环的操作抽取出来 --%>    <c:forEach begin="${beginPage}"               end="${endPage}" var="i">        <%-- 如果是当前页码，就标识出来，并不能点击 --%>        <c:if test="${i == requestScope.pageOfBook.currentPage}">            【${i}】        </c:if>        <%-- 如果不是当前页码，就设置为超链接，跳转到对应页码的页面 --%>        <c:if test="${i != requestScope.pageOfBook.currentPage}">            <a href="${requestScope.pageOfBook.reqPath}&currentPage=${i}">${i}</a>        </c:if>    </c:forEach>    <%-- 如果当前页码小于总页码，才显示下一页和末页的按键 --%>    <c:if test="${requestScope.pageOfBook.currentPage < requestScope.pageOfBook.totalPage}">        <a href="${requestScope.pageOfBook.reqPath}&currentPage=${requestScope.pageOfBook.currentPage + 1}">下一页</a>        <a href="${requestScope.pageOfBook.reqPath}&currentPage=${requestScope.pageOfBook.totalPage}">末页</a>    </c:if>    共${requestScope.pageOfBook.totalPage}页，${requestScope.pageOfBook.totalCount}条记录    到第<input value="${requestScope.pageOfBook.currentPage}" name="pn" id="pn_input"/>页    <input id="searchPageBth" type="button" value="确定"></div>
   ```

4. 将分页条的代码抽取出来，单独放在一个jsp，然后在bookCityIndex.jsp和book_manager.jsp中用jsp指令引入分页条

   ```jsp
   <%-- 引入分页条 --%>
   <%@ include file="/pages/ui/pageNav.jsp"%>
   ```

***

### 3.4 价格区间搜索的实现

1. 在ClientBookServlet 中添加价格区间的分页方法`pageCliBooksByPrice()`

2. 修改首页（bookCityIndex.jsp）的价格文本框表单，表单提交到ClientBookServlet，并在表单中添加隐藏域，用于标识使用的功能方法是`pageCliBooksByPrice()`

   ```jsp
   <form action="client/cBookServlet" method="get">    <%-- 添加一个隐藏域，标识功能方法 --%>    <input type="hidden" name="action" value="pageCliBooksByPrice">    <%-- code... --%></form>
   ```

3. 在ClientBookServlet 的`pageCliBooksByPrice()`方法中，先获取当前页码currentPage和每页显示的记录数pageSize，以及表单内容——最小价格minPrice和最大价格maxPrice，根据这四个参数调用service类对应方法，获取对应的分页对象，然后将改对象存储到request域中，最后请求转发回到bookCityIndex.jsp 页面

   ```java
   protected void pageCliBooksByPrice(HttpServletRequest req,                                    HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取请求参数    int currentPage = WebUtil.parseInt(req.getParameter("currentPage"), 1);    int pageSize = WebUtil.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);    int minPrice = WebUtil.parseInt(req.getParameter("min"), -1);    int maxPrice = WebUtil.parseInt(req.getParameter("max"), Integer.MAX_VALUE);    // 2. 根据获取的参数调用service方法，获取对应的分页对象    Page<Book> pageOfBook = bService.pageCliBooksByPrice(currentPage, pageSize, minPrice, maxPrice);    // 3. 设置分页请求地址    pageOfBook.setReqPath("client/cBookServlet?action=pageCliBooksByPrice");    // 4. 将分页对象存储到request域中    req.setAttribute("pageOfBook", pageOfBook);    // 5. 请求转发到bookCityIndex.jsp    req.getRequestDispatcher("/pages/client/bookCityIndex.jsp").forward(req, resp);}
   ```

4. 在service类中创建出对应的价格区间分页方法

   ```java
   @Overridepublic Page<Book> pageCliBooksByPrice(int currPage, int pageSize, int minPrice, int maxPrice) {    // 1. 获取总记录数    int totalCount = bDao.queryTotalCountByPrice(minPrice, maxPrice);    // 2. 计算出总页码    int totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);    // 3. 根据总页码，获取合法的当前页码    int legalCurrPage = WebUtil.getLegalPage(currPage, totalPage);    // 4. 获取每页显示的数据    int beginPage = (legalCurrPage - 1) * pageSize;    List<Book> books = bDao.queryBooksPerPageByPrice(beginPage, pageSize, minPrice, maxPrice);    return new Page<>(legalCurrPage, pageSize, totalPage, totalCount, books);}
   ```

5. 在dao类中创建出对应的方法，其中每个方法中的SQL语句为

   * 查询某个范围的总记录数：`select count(*) from b_book where between [最小价格] and [最大价格]`

   ```java
   @Overridepublic int queryTotalCountByPrice(int minPrice, int maxPrice) {    // 创建SQL语句    String sql = "select count(*) from b_book where price between ? and ?";    Number number = (Number) queryForValue(sql, minPrice, maxPrice);    return number.intValue();}
   ```

   * 分页查询某个范围的数据，并根据价格升序排序：`select * from b_book where between [最小价格] and [最大价格] order by price limit [初始页码] , [每页记录数]`

   ```java
   @Overridepublic List<Book> queryBooksPerPageByPrice(int beginPage, int pageSize, int minPrice, int maxPrice) {    // 创建SQL语句    String sql = "select * from b_book where price between ? and ? order by price limit ? , ?";    return queryForList(Book.class, sql, minPrice, maxPrice, beginPage, pageSize);}
   ```

6. 搜索区间价格的回显：在价格区间的文本框上添加`value`属性，属性值为对应的价格参数值`${param.min}`或`${param.max}`

   ```jsp
   <input id="min" type="text" name="min" value="${param.min}"> 元 -
   <input id="max" type="text" name="max" value="${param.max}"> 元
   ```

7. 带上价格区间后，需要修改分页条的请求地址reqPath，在ClientBookServlet 的`pageCliBooksByPrice()`方法中，判断获取的最小价格和最大价格是否为null，如果不为null，就添加到请求地址中，作为请求参数转发给bookCityIndex.jsp，让页面始终显示对应价格区间的图书信息

   ```java
   // 判断价格区间是否为null
   StringBuilder sb = new StringBuilder("client/cBookServlet?action=pageCliBooksByPrice");
   if (minPrice != -1) {
       sb.append("&min=").append(req.getParameter("min"));
   }
   if (maxPrice != Integer.MAX_VALUE) {
       sb.append("&max=").append(req.getParameter("max"));
   }
   
   // 3. 设置分页请求地址
   pageOfBook.setReqPath(sb.toString());
   ```

8. 给价格区间确认按键绑定单击事件，用JS代码校验用户输入的价格值是否合法

   ```jsp
   <script type="text/javascript">
       // 页面加载完成后
       $(function () {
           // 价格输入框绑定单击事件
           $("#searchPriceBtn").click(function () {
               // 获取文本框内容
               let minText = $("#min").val();
               let maxText = $("#max").val();
   
               // 如果有任意一个文本框为空就弹出警示框，并阻止提交
               if (maxText === "" || minText === "") {
                   alert("输入价格不合法！");
                   return false;
               }
   
               // 设置数字正则表达式
               let numPat = /[0-9]/;
               // 如果价格输入框都不为空，就判断是否都是数字
               if (! numPat.test(maxText) || ! numPat.test(minText)) {
                   alert("输入价格不合法！");
                   return false;
               }
   
               // 如果价格都为数字，就比较最小价格和最大价格，如果最小价格比最大价格还大，就阻止提交
               // 必须先把文本内容转成int型，才能比较
               if (parseInt(maxText) < parseInt(minText)) {
                   alert("输入价格不合法！");
                   return false;
               }
   
               // 如果存在负数的价格，也阻止提交
               if (minText < 0 || maxText < 0) {
                   alert("输入价格不合法！");
                   return false;
               }
           });
       })
   </script>
   
   <input id="searchPriceBtn" type="submit" value="查询" />
   ```

## 四、用户模块的补充

### 4.1 登录和退出登录

#### 4.1.1 登录后用户名的回显

1. 在UserServlet 的登录功能方法中创建session，将登陆后的用户对象存储到session域中

   > PS：这里需要将对象存储到session域中，不能存储到request域中，因为获取用户信息后，不仅要在首页显示，点击其他功能页面时也要显示，即需要在多次请求间显示用户信息，而request域范围是一次请求间，session是一次会话多次请求间，而且一般用session存储敏感信息（如用户登陆后的信息），而不用cookie

   ```java
   // 将登录后的用户信息存储到session域中，用于回显用户名
   req.getSession().setAttribute("loginUsername", loginUser.getUsername());
   ```

2. 在登录成功后页面的头部菜单栏上用EL表达式获取session域中的用户对象的用户名属性值，从而实现登录后就在菜单栏显示对应的用户名

   ```jsp
   <span>欢迎<span class="um_span">${sessionScope.loginUsername}</span>光临尚硅谷书城</span>
   ```

3. 登陆后，不仅要在登录成功页面显示用户名，其他页面（前台页面）也要显示对应的登录用户名，因此需要修改首页头部菜单栏，加上jstl标签，如果用户未登录就显示 <u>登录/注册</u> 的超链接，如果用户登录后，就去掉该超链接，并加上 欢迎某某用户 的字样

   ```jsp
   <%-- 如果用户未登录，就显示登录和注册的按键 --%>
   <c:if test="${empty sessionScope.loginUsername}">
       <a href="pages/user/login.jsp">登录</a> |
       <a href="pages/user/regist.jsp">注册</a> &nbsp;&nbsp;
   </c:if>
   <%-- 如果用户已经登录，就不显示登陆注册的字样，并显示用户名 --%>
   <c:if test="${not empty sessionScope.loginUsername}">
       <span>欢迎<span class="um_span">${sessionScope.loginUsername}</span>光临尚硅谷书城</span>
       <a href="pages/order/order.jsp">我的订单</a>
       <a href="index.jsp">注销</a>&nbsp;&nbsp;
   </c:if>
   ```

#### 4.1.2 退出登录的实现

1. 在UserServlet 中添加注销账号的功能方法`logout()`，在方法中先销毁存储用户信息的session，然后采用重定向的方式跳转回首页

   ```java
   protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {    // 1. 销毁存储用户信息的session    req.getSession().invalidate();    // 2. 重定向跳转回到首页    resp.sendRedirect(req.getContextPath());}
   ```

2. 在首页和其他页面的头部菜单栏上，修改 <u>退出登录</u> 超链接的跳转资源地址，将资源地址修改成`userServlet?action=logout`，即跳转到UserServlet，并调用对应的功能方法

   ```jsp
   <a href="userServlet?action=logout">退出登录</a>
   ```

3. 给 <u>退出登录</u> 超链接绑定单击事件，在点击链接后询问用户是否确认退出登录，现在退出登录的`<a>`标签中添加`class`属性，属性值为`logoutClass`，然后用JS实现单击事件的绑定

   ```jsp
   <%-- 给注销按键绑定一个单击事件，提示是否要退出登录 --%><script type="text/javascript">    // 页面加载完成后    $(function () {        // 绑定单击事件        $("a.logoutClass").click(function () {            // 弹出对话框，提示是否要退出登录            return confirm("您确定要退出登录吗？");        })    })</script><%-- code... --%><a class="logoutClass" href="userServlet?action=logout">退出登录</a>
   ```

***

### 4.2 验证码的实现

#### 4.2.1 解决表单重复提交

##### a. 表单重复提交有哪些情况？

> PS：表单重复提交的问题主要有三种情况，后两种可用验证码来解决

* **表单提交后刷新**：如果在Servlet中更新数据库后，采用请求转发的方式跳转到目标页面，则提交表单信息后再刷新页面（点f5功能键），会造成表单的重复提交，因为在浏览器中刷新页面时，浏览器会重复处理最近一次的请求业务，而请求转发是一次请求的资源跳转，所以刷新页面会重复处理Servlet中更新数据库的操作

  解决方法：==将请求转发换成重定向转发==，因为重定向是两次请求间的资源跳转，此时浏览器最近一次请求是跳转到目标页面，即请求业务不是更新数据库，所以刷新页面只会重复跳转到目标页面，不会重复更新数据库

* **因网络延迟重复点提交键**：用户在点击提交按键后，由于网络延迟，用户可能会担心提交信息失败，就会重复点击提交按键，造成表单的重复提交

  解决方法：1、（前端）利用JS，再用户点击提交按键后就将按键置为不可点击；2、（后台）采用验证码

* **提交后回退页面再次点提交键**：用户提交表单并跳转到目标页面后，可能会点击回退，回到表单提交页面，然后再次点击提交按键，造成表单的重复提交

  解决方式：采用验证码

##### b. 验证码如何解决表单重复提交问题？

1. 在用户第一次访问表单页面时生成对应的验证码，并将验证码存储到session域中，再展示到页面
2. 用户点击提交按键后，后台Servlet中获取session中的验证码信息，==然后删除==，再获取用户在文本框中添加的验证码信息，将session中的验证码与用户提交的验证码进行比较，如果不相等就阻止提交，如果相等就进行下一步操作
3. 当遇到表单提交的后两种情况时，即用户重复点击提交按键造成表单重复提交的情况，由于后台获取第一次生成的验证码后就删除session域的对应信息，所以用户再次点击提交按键（相当于第二次访问表单提交页面），后台中在session域中获取的验证码信息就为空（null），此时无论用户在文本框中提交的信息为多少都与null不相等，从而实现阻止表单的提交

#### 4.2.2 验证码的简单使用

> PS：项目中使用的是谷歌验证码kaptcha

1. 导入谷歌验证码相关jar 包：kaptcah-...jar

   * kaptcha是什么？——kaptcha是google开源的一个非常实用的验证码生成工具类，可以利用kaptcha生成各种各样的验证码kaptcha的工作原理是调用com.google.code.kaptcha.servlet.KaptchaServlet生成一个验证码，相应给客户端，同时将生成的验证码字符串存到session中

   * 导入jar包遇到的问题：通过pom.xml 导入谷歌验证码时，不能直接导入`com.google.code`或者`com.google.code.kaptcha`的资源，因为kaptcha没有被上传到maven中央仓库，所以maven工程中，不能直接在pom.xml 导入相关jar 包并下载到本地仓库

   * 解决方法：导入`com.github.penggle`中的kaptcha相关jar 包，应该是别人将kaptcha下载到自己github仓库中，然后其他人就可以在他的github仓库中直接下载

2. 在web.xml 中配置谷歌验证码（kaptcha）Servlet

   ```xml
   <!-- 配置谷歌验证码Servlet --><servlet>    <servlet-name>kaptcha</servlet-name>    <servlet-class>com.google.code.kaptcha.servlet.KaptchaServlet</servlet-class></servlet><servlet-mapping>    <servlet-name>kaptcha</servlet-name>    <url-pattern>/verifiedCode.jsp</url-pattern></servlet-mapping>
   ```

3. 在注册页面（register.jsp）的表单中，将原来固定的验证码图片地址修改成谷歌验证码对应的Servlet资源路径，并修改图片的大小

   ```jsp
   <input class="itxt" type="text" name="code" style="width: 100px; height: 10px" id="code"/><img alt="" src="verifiedCode.jsp" style="float: right; margin-right: 40px; width: 110px; height: 30px;">
   ```

4. 在UserServlet 中的`register()`方法中添加代码，先在session域中获取对应的验证码信息，然后及时把session域中的验证码信息删除，避免出现重复的验证码，再获取表单内容中的验证码信息（用户在文本框内填写的），将两者进行比较，如果相等就进行下一步操作，如果不相等就阻止提交，并返回提示信息

   ```java
   // 获取session域中的验证码信息HttpSession session = req.getSession();String verifiedCode = (String) session.getAttribute(KAPTCHA_SESSION_KEY);// 再删除session域中的信息（必须及时删除）session.removeAttribute(KAPTCHA_SESSION_KEY);
   ```

   > PS：`KAPTCHA_SESSION_KEY`是kaptcha 的一个常量类（在jar包中）中的常量，标识存储到session域中的验证码字符串信息

#### 4.2.3 验证码的切换（刷新）

1. 给验证码图片（`<img>`标签）绑定一个单击事件，实现点击图片就刷新验证码的功能

   ```jsp
   <img id="vCodeId" alt="" src="verifiedCode.jpg" style="float: right; margin-right: 40px; width: 110px; height: 30px;">
   ```

2. 在`<scrip>`标签中实现JS代码的编写，在给验证码的`<img>`标签绑定单击事件后，需要对`<img>`的`src`属性==重复赋值==，即每次点击图片后，就给图片的`src`赋一次值，浏览器就会重复向服务器发起请求，实现刷新的目的

   ```js
   // 页面加载完成之后
   $(function () {
   
       // 给验证码图片绑定单击事件
       $("#vCodeId").click(function () {
           // 更新<img>标签中的src属性值，后面带上一个变化的参数值，用时间戳保证每次值都不一样
           this.src  ="verifiedCode.jpg?d=" + new Date();
       });
   })
   ```

3. 给`src`重复赋值可能会遇到==浏览器缓存使验证码不更新的问题==，解决方式是每次对`src`赋的值都不一样，才能更新验证码。但请求的路径又只能是谷歌验证码Servlet的资源路径`/verifiedCode.jpg`，所以需要添加一个==每次参数值都不一样的请求参数==，参数赋值为时间戳就能保证每次值都不一样，即实现每次请求链接都不一样，从而避免浏览器的缓存问题

   > 浏览器的缓存问题：在浏览器和服务器的一次请求中，浏览器为了让下一次请求能更快，会将请求的内容（资源路径和请求参数）缓存到浏览器中（可能是缓存到磁盘，也可能是缓存到内容中），因此下一次请求的内容是相同时，浏览器就会直接在本地找到对应的缓存信息，不会再向服务器请求

## 五、购物车模块

### 5.1 模块分析与功能说明

#### 5.1.1 创建购物车实体类

* 在entity 包下创建购物车的实体类——Cart，Cart 的成员变量有

  1. itemTotalCount：商品总数
  2. itemTotalPrice：商品总价
  3. items：购物车商品集合

  > PS：items成员变量类型设置为Map<Integer, CartItem>，用商品的id值唯一标识一个商品对象

```java
public class Cart {    private Integer itemTotalCount;    private BigDecimal itemTotalPrice;    private final Map<Integer, CartItem> items = new LinkedHashMap<>();    public Cart() {    }    public Map<Integer, CartItem> getItems() {        return items;    }    public Integer getItemTotalCount() {        itemTotalCount = 0;        // 遍历商品项集合，统计每个商品项的数量        for (CartItem i : items.values()) {            itemTotalCount += i.getCount();        }        return itemTotalCount;    }    public BigDecimal getItemTotalPrice() {        itemTotalPrice = new BigDecimal(0);        // 遍历集合，统计每个商品项的总价格        for (CartItem i : items.values()) {            // 每次调用add()方法累加后再赋值给自己            itemTotalPrice = itemTotalPrice.add(i.getTotalPrice());        }        return itemTotalPrice;    }    @Override    public String toString() {        return "Cart{" +                "itemTotalCount=" + itemTotalCount +                ", itemTotalPrice=" + itemTotalPrice +                ", items=" + items +                '}';    }}
```

> PS：在`getItemTotalCount()`和`getItemTotalPrice()`方法中，分别遍历商品项对象集合，统计出商品总数和商品总价 

#### 5.1.2 创建购物车商品项实体类

* 在entity 包下创建购物车商品实体类——CartItem，CartItem 的成员变量有
  1. id：商品的编号
  2. name：商品名称
  3. price：商品单价
  4. count：商品数量
  5. totalPrice：商品总价

```java
public class CartItem {    private Integer id;    private String name;    private Integer count;    private BigDecimal price;    private BigDecimal totalPrice;    public CartItem() {    }    public CartItem(Integer id, String name, Integer count, BigDecimal price, BigDecimal totalPrice) {        this.id = id;        this.name = name;        this.count = count;        this.price = price;        this.totalPrice = totalPrice;    }    public Integer getId() {        return id;    }    public void setId(Integer id) {        this.id = id;    }    public String getName() {        return name;    }    public void setName(String name) {        this.name = name;    }    public Integer getCount() {        return count;    }    public void setCount(Integer count) {        this.count = count;    }    public BigDecimal getPrice() {        return price;    }    public void setPrice(BigDecimal price) {        this.price = price;    }    public BigDecimal getTotalPrice() {        return totalPrice;    }    public void setTotalPrice(BigDecimal totalPrice) {        this.totalPrice = totalPrice;    }    @Override    public String toString() {        return "CartItem{" +                "id=" + id +                ", name='" + name + '\'' +                ", count=" + count +                ", price=" + price +                ", totalPrice=" + totalPrice +                '}';    }}
```

#### 5.1.3 实现购物车的主要技术版本	

1. session版本：将购物车商品信息存储到session域中（本次项目采用这种）
2. 数据库版本：将购物车商品信息存储到数据库中
3. Redis + 数据库 + cookie版本：使用Redis缓存、数据库和cookie技术存储购物车的商品信息

#### 5.1.4 购物车功能简介 

* 功能实现说明：本项目采用的技术是session版本，即将购物车信息存储到session域中，所以就不需要将信息存储到数据库，因而就不需要创建dao类，购物车功能的具体逻辑实现直接在对应的service类中完成，然后创建对应的Servlet对象，在Servlet对象中也创建对应的功能方法，Servlet调用service 实现类中的方法

  > PS：由于购物车对象需要存储到session域中，然后在页面上展示数据，所以Servlet调用service类方法实现相应的业务逻辑后，购物车对象应该还是原来的对象，所以==创建service类对象时，需要传入购物车对象cart==，在service实现类中把cart对象作为成员变量来使用

* **主要功能**

  1. **加入购物车**：在首页对应图书上可以点击“加入购物车”的按键，将对应图书加入到购物车
  2. 在购物车上**删除对应商品**：在购物车页面点击 <u>删除</u> 超链接，将对应图书从购物车上移除
  3. **清空购物车**所有商品：在购物车页面点击 <u>清空购物车</u> 超链接，实现将购物车上所有商品全部移除
  4. **修改购物车的商品数量**：在购物车页面上可以修改对应图书的数量

***

### 5.2 加购功能的实现

1. 在service实现类中添加对应的功能方法——`addItem()`，方法中先根据传入的新商品项对象id值判断当前购物车中是否已经有一样的商品，如果已经有，就直接修改商品项的数量和总价格，如果没有就在购物车对象中添加新的商品项

   ```java
   @Overridepublic void addItem(CartItem newItem) {    // 1. 根据新商品对象的id值（购物车中商品对象集合是map集合，id是键）获取对应的商品项对象    CartItem cartItem = cart.getItems().get(newItem.getId());    // 2. 根据获取的对象是否为null判断购物车中是否已经存在相同的商品    if (null == cartItem) {        // 获取商品为null，说明购物车中无对应id的商品项，直接加入新的商品项对象        cart.getItems().put(newItem.getId(), newItem);    } else {        // 获取的商品项对象不为null，说明购车中已经存在相同的商品项，直接修改商品数量和商品总价即可        // 商品数量加一        cartItem.setCount(cartItem.getCount() + 1);        // 修改商品总价格，根据更新后的商品数量计算出商品总数（商品单价乘以商品数量）        cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));    }}
   ```

2. 在首页的jsp中，通过JS代码对`加入购物车`的按键绑定单击事件，点击按键后就通过`location.href`改变地址栏的链接，跳转到CartServlet，并将对应图书的id值传给后台，同时调用CartServlet 中的`addItem()`方法

   ```js
   // 给加入购物车绑定单击事件，点击后将商品（图书）id值传给后台$("button.addItemClass").click(function () {    // 获取自定义属性bookId的值（图书的id值）    let bookId = $(this).attr("bookId");    // 通过location对象，改变地址栏路径，跳转时将id传给后台    location.href = "${pageScope.basePath}cartServlet?action=addItem&id=" + bookId;});
   ```

3. 在CartServlet 的`addItem()`方法中，先获取请求参数中的id值，根据id调用BookService 类的方法，获取对应的图书对象，并将该图书对象转变成商品项对象，然后获取session域中的购物车对象，如果session中的购物车对象为null，说明用户还没加购过商品，就初始化一个购物车对象并存储到session域中，然后再调用CartService 实现类的对应方法，将图书商品项添加到购物车对象中，最后重定向跳转回到原来的页面

   > PS：最后重定向的跳转路径，不能简单地跳转回首页，因为直接跳转回首页，展示出来的总是第一页的图书信息，如果用户是在非第一页加购的图书，就不能实现跳转回原来的页面。所以重定向跳转的路径需要从请求头获取Referer的参数值，Referer的值就是页面跳转前的链接

   ```java
   protected void addItem(HttpServletRequest req,                        HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取请求参数中的id值    String booId = req.getParameter("id");    // 2. 根据获取的id值调用BookService方法，获取对应的图书对象    Book book = bService.queryBook(Integer.parseInt(booId));    // 3. 将图书对象转成商品项对象    CartItem bookItem = new CartItem(book.getId(), book.getTitle(), 1, book.getPrice(), book.getPrice());    // 4. 先获取session域中的购物车对象    HttpSession session = req.getSession();    Cart cart = (Cart) session.getAttribute("cart");    // 5. 判断从session中获取的对象是否为null    if (null == cart) {        // session中无购物车对象，说明用户还没加购过商品（图书）到车里，需要对购物车对象初始化        cart = new Cart();        // 再将购物车对象存储到session域中        session.setAttribute("cart", cart);    }    // 6. 先初始化CartService，再根据该图书对象调用CartService方法，将图书信息加入购物车    cService = new CartServiceImpl(cart);    cService.addItem(bookItem);    // 8. 获取请求头中的Referer的值，作为重定向跳转的路径    resp.sendRedirect(req.getHeader("Referer"));}
   ```

***

### 5.3 购物车商品的展示

1. 修改购物车页面（cart.jsp），用jstl标签和EL表达式，遍历session域中的购物车对象中的商品项集合，然后展示出每个商品项的信息

   ```jsp
   <%-- 使用jstl标签遍历session域中的购物车对象中的商品项集合 --%><c:forEach items="${sessionScope.cart.items}" var="entry">    <tr>        <td>${entry.value.name}</td>        <td>${entry.value.count}</td>        <td>${entry.value.price}</td>        <td>${entry.value.totalPrice}</td>        <td><a href="#">删除</a></td>    </tr></c:forEach>
   ```

2. 如果用户还没有添加过商品到购物车，购物车页面应该显示提示信息，提示信息添加超链接引导用户前去首页浏览器商品，并去掉下面的具体信息

   ```jsp
   <%-- 如果购物车中还没有添加过商品，就显示提示信息，并引导用户去首页 --%><c:if test="${empty sessionScope.cart.items}">    <tr>        <td colspan="5">亲，当前购物车为空，<a style="font-size: 16px" href="index.jsp">快去首页浏览商品吧</a></td>    </tr></c:if><%-- 如果当前购物车中已有添加过商品，就显示商品列表 --%><c:if test="${not empty sessionScope.cart.items}">    <%-- 使用jstl标签遍历session域中的购物车对象中的商品项集合 --%>    <c:forEach items="${sessionScope.cart.items}" var="entry">        <tr>            <td>${entry.value.name}</td>            <td>${entry.value.count}</td>            <td>${entry.value.price}</td>            <td>${entry.value.totalPrice}</td>            <td><a href="#">删除</a></td>        </tr>    </c:forEach></c:if><%-- code... --%><%-- 如果当前购物车没有商品，就不显示具体信息 --%><c:if test="${not empty sessionScope.cart.items}">    <div class="cart_info">        <span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.itemTotalCount}</span>件商品</span>        <span class="cart_span">总金额<span class="b_price">${sessionScope.cart.itemTotalPrice}</span>元</span>        <span class="cart_span"><a href="#">清空购物车</a></span>        <span class="cart_span"><a href="pages/cart/checkout.jsp">去结账</a></span>    </div></c:if>
   ```

***

### 5.4 删除商品项和清空购物车

#### 5.4.1 删除商品项功能实现

1. 在CartService 类中添加对应的功能方法`deleteItem()`，方法中根据传入的id值直接将商品项集合中对应的商品项对象移除即可

   ```java
   @Overridepublic void deleteItem(int itemId) {    // 根据id值将对应的商品项对象从集合中移除即可    cart.getItems().remove(itemId);}
   ```

2. 修改购物车页面（cart.jsp）中的 <u>删除</u> 超链接跳转路径，点击后跳转到cartServlet，并带上请求参数，把对应商品id传给后台并调用对应的功能方法`deleteItem()`

   ```jsp
   <a class="delItemClass" href="cartServlet?action=deleteItem&id=${entry.value.id}">删除</a>
   ```

3. 在CartServlet 的`deleteItem()`中，先获取请求参数中的id值，再获取session域中购物车对象，判断购物车对象不为null的情况下，调用service中对应的功能方法，实现删除逻辑，最后重定向跳转回到原来的页面（跳转路径与加入购物车功能一样）

   ```java
   protected void deleteItem(HttpServletRequest req, 
                             HttpServletResponse resp) throws ServletException, IOException {
       // 1. 获取请求参数中的id值
       int itemId = WebUtil.parseInt(req.getParameter("id"), 0);
   
       // 2. 获取session域中的购物车对象
       Cart cart = (Cart) req.getSession().getAttribute("cart");
   
       // 3. 在购物车对象不为null的情况下
       if (null != cart) {
           // 3.1 初始化service对象
           cService = new CartServiceImpl(cart);
   
           // 3.2 调用service的对应方法
           cService.deleteItem(itemId);
   
           // 3.3 最后重定向回到原页面
           resp.sendRedirect(req.getHeader("Referer"));
       }
   }
   ```

4. 给购物车页面（cart.jsp）中的 <u>删除</u> 超链接绑定一个单击事件，点击后先弹出提示框，询问用户是否确认删除对应信息，如果用户确认才把id传给后台并实现删除功能

   ```js
   // 页面加载完成后
   $(function () {
       // 绑定单击事件
       $("a.delItemClass").click(function () {
           // 弹出对话框，提示用户是否确认删除
           return confirm("您确认要将" + $(this).parent().parent().find("td:first").text() + "移出购物车吗？");
       });
   })
   ```

#### 5.4.2 清空购物车功能实现

1. 在CartService 类中添加对应的功能方法`clearCart()`，方法中只要调用商品项集合的`clear()`方法将元素全部移除即可

   ```java
   @Overridepublic void updateItemCount(int itemId, int updatedCount) {    // 1. 根据id值获取对应的商品项对象    CartItem item = cart.getItems().get(itemId);    // 2. 判断获取的对象是否为null，如果不为null，就将对应商品项信息修改    if (null != item) {        // 修改商品项数量        item.setCount(updatedCount);        // 商品数量影响商品总价格        item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));    }}
   ```

2. 在购物车页面（cart.jsp）中的 <u>清空购物车</u> 超链接中，设置跳转路径到CartServlet，并带上请求参数，调用CartServlet中对应的功能方法——`clearCart()`

   ```jsp
   <a id="clearId" href="cartServlet?action=clearCart">清空购物车</a>
   ```

3. 在CartServlet 的`clearCart()`方法中，先获取session域中的购物车对象，在购物车对象不为null的情况下，调用service 类的对应功能方法，最后重定向返回原页面

   ```java
   protected void clearCart(HttpServletRequest req,                          HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取session域中的购物车对象    Cart cart = (Cart) req.getSession().getAttribute("cart");    // 2. 在购物车对象不为null的情况下    if (null != cart) {        // 2.1 初始化cService对象        cService = new CartServiceImpl(cart);        // 2.2 调用service对应方法        cService.clearCart();        // 2.3 最后重定向回到原页面        resp.sendRedirect(req.getHeader("Referer"));    }}
   ```

4. 给 <u>清空购物车</u> 超链接绑定单击事件，点击后先弹出对话框，询问用户是否确认清空购物车，用户确认后才进行跳转

   ```js
   // 给清空购物车绑定单击事件$("#clearId").click(function () {    // 弹出对话框，询问用户是否确认清空    return confirm("您确认要清空购物车所有商品吗？");});
   ```

***

### 5.5 修改商品数量

1. 将购物车页面中商品项的数量显示改成文本框，并修改样式，实现可以直接在购物车页面修改商品数量的功能

   ```jsp
   <input type="text"       style="width: 50px"       class="updItemCountClass"       bookId="${entry.value.id}"       value="${entry.value.count}">
   ```

2. 给商品数量文本框绑定监听内容修改事件（onchange），当文本框内容修改后就弹出对话框询问用户是否确认修改，如果用户点击取消，就显示原来的商品数量，如果用户点击确认，则通过`location.href`修改地址栏链接，实现资源的跳转，跳转到CartServlet，并带上请求参数——图书商品的id、商品修改后的数量以及要调用的功能方法`updateItemCount()`

   > PS：对用户输入的数量值也要进行数据的校验，数量值只能为正数的数字

   ```js
   // 给商品项数量文本框绑定监听内容修改事件$(".updItemCountClass").change(function () {    // 获取更新后的商品数量    let updatedCount = this.value;    // 先判断用户输入的商品数量值是否合法    // 定义一个数字正则表达式    let numPat = /[0-9]/;    // 先判断输入值是否是数字    if (! numPat.test(updatedCount)) {        // 输入值不是数字，弹出警告框，并阻止提交        alert("请输入合法的商品数量值！");        // 回显原来的值        this.value = this.defaultValue;        return false;    }    // 输入值是数字，就判断是否是正数    if (updatedCount <= 0) {        // 输入值不是正数，弹出警告框，并阻止提交        alert("请输入合法的商品数量值！");        // 回显原来的值        this.value = this.defaultValue;        return false;    }    /* 输入值合法后，就弹出对话框 */    // 获取商品项名称    let itemName = $(this).parent().parent().find("td:first").text();    // 获取对应的商品id值    let bookId = $(this).attr("bookId");    // 弹出对话框，判断用户是否确认修改    if (! confirm("您确认要将" + itemName + "商品的数量修改成：" + updatedCount + "吗？")) {        // 用户点击取消，则显示修改前的数据        this.value = this.defaultValue;    } else {        // 用户点击确认，跳转到后台，进行修改的具体逻辑        location.href = "${pageScope.basePath}cartServlet?action=updateItemCount&id=" +            bookId + "&updatedCount=" + updatedCount;    }});
   ```

3. 在CartService 类中添加对应的功能方法`updateItemCount()`，方法中根据传进来的id值在商品项集合中找到对应的商品项对象，在该对象存在的情况下，根据传进来的更新数量修改商品项对象的数量以及商品的总价格

   ```java
   @Overridepublic void updateItemCount(int itemId, int updatedCount) {    // 1. 根据id值获取对应的商品项对象    CartItem item = cart.getItems().get(itemId);    // 2. 判断获取的对象是否为null，如果不为null，就将对应商品项信息修改    if (null != item) {        // 修改商品项数量        item.setCount(updatedCount);        // 商品数量影响商品总价格        item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));    }}
   ```

4. 在CartServlet 的`updateItemCount()`方法中，先获取请求参数中的id值和更新数量值，再获取session域中的购物车对象，在购物车对象不为null的情况下，根据id值和更新数量值调用service类对应的功能方法，最后重定向回到原页面

   ```java
   protected void updateItemCount(HttpServletRequest req,                                HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取请求参数中的id值和更新数量值    int bookId = WebUtil.parseInt(req.getParameter("id"), 0);    int updatedCount = WebUtil.parseInt(req.getParameter("updatedCount"), 1);    // 2. 获取session域中的购物车对象    Cart cart = (Cart) req.getSession().getAttribute("cart");    // 3. 在购物车对象存在的情况下    if (null != cart) {        // 3.1 初始化cService对象        cService = new CartServiceImpl(cart);        // 3.2 调用service对应功能方法        cService.updateItemCount(bookId, updatedCount);        // 3.3 最后重定向回到原页面        resp.sendRedirect(req.getHeader("Referer"));    }}
   ```

***

### 5.6 首页购物车信息回显

1. 首页需要回显用户购物车的商品数量和最近一次加入购物车的商品名

2. 在CartServlet 加入购物车方法`addItem()`中添加代码，将当前加入购物车的商品项对象名存储到session域中，方便前台获取并显示

   ```java
   // 将刚刚加入购物车的商品项对象名存储到session域中
   session.setAttribute("nameOfLastAddedItem", bookItem.getName());
   ```

3. 在首页（bookCityIndex.jsp）中将原来写死的显示数据用EL表达式修改，将session域中的数据获取并展示

   ```jsp
   <span>您的购物车中有 ${sessionScope.cart.itemTotalCount} 件商品</span>
   <div>
       您刚刚将<span style="color: red">${sessionScope.nameOfLastAddedItem}</span>加入到了购物车中
   </div>
   ```

4. 在首页中用jstl标签实现当用户购物车为空时，展示其他信息

   ```jsp
   <%-- 如果购物车不为空 --%>
   <c:if test="${not empty sessionScope.cart.items}">
       <span>您的购物车中有 ${sessionScope.cart.itemTotalCount} 件商品</span>
       <div>
           您刚刚将<span style="color: red">${sessionScope.nameOfLastAddedItem}</span>加入到了购物车中
       </div>
   </c:if>
   
   <%-- 如果购物车为空 --%>
   <c:if test="${empty sessionScope.cart.items}">
       <span></span>
       <div>
           <span style="color: deepskyblue">    当前购物车为空~</span>
       </div>
   </c:if>
   ```

## 六、订单模块

### 6.1 模块分析与功能说明

#### 6.1.1 功能说明

* 订单模块分为两种功能，分别面向用户和管理员
  * 面向用户
    1. 生成订单：用户在购物车页面可以点击 <u>去结账</u> 的超链接，然后后台生成订单
    2. 查看我的订单信息：用户可以查看自己的订单信息
    3. 签收订单：用户可以对订单进行签收，改变订单的状态
    4. 查看订单商品详情：用户可以查看自己订单中每一件商品的详情
  * 面向管理员
    1. 查看所有订单信息：管理员可以查看所有用户及其所有订单的信息
    2. 发货：管理员可以对订单商品进行发货处理，改变订单的状态
    3. 查看订单商品详情：与用户的功能一样

#### 6.1.2 数据库表的创建

##### a. 订单实体类对应的数据库表

* 创建数据库表——b_order

  > 表字段：订单号order_id（主键）、下单时间order_time、订单总价order_price、订单状态order_status、用户user_id（外键，一个用户对应多个订单）
  >
  > PS：订单状态的有3个值，分别为0、1、2，表示未发货、已发货和已签收

  ```sql
  /*创建订单表*/
  CREATE TABLE b_order (
  	`order_id` VARCHAR(50) PRIMARY KEY,
  	`order_time` DATETIME,
  	`order_price` DECIMAL(11,2),
  	`order_status` INT,
  	`user_id` INT,
  	FOREIGN KEY(`user_id`) REFERENCES b_user(`id`)
  );
  ```

##### b. 订单商品项实体类对应的数据库表

* 创建数据库表——b_order_item

  > 表字段：订单商品编号id（主键）、商品名name、商品数量count、商品单价price、商品总价total_price、订单号order_id（外键，一个订单号对应多个订单商品项）

  ```sql
  /*创建订单项表*/
  CREATE TABLE b_order_item (
  	`id` INT PRIMARY KEY AUTO_INCREMENT,
  	`name` VARCHAR(100) NOT NULL,
  	`count` INT,
  	`price` DECIMAL(11,2),
  	`total_price` DECIMAL(11,2),
  	`order_id` VARCHAR(50),
  	FOREIGN KEY(`order_id`) REFERENCES b_order(`order_id`)
  );
  ```

#### 6.1.3 创建数据库表对应的JavaBean

##### a. 订单实体类

* 在entity 包下创建订单实体类——Order

  ```java
  public class Order {
      private String orderId;
      private Date orderTime;
      private BigDecimal orderPrice;
      private Integer orderStatus;
      private Integer userId;
  
      public Order() {
      }
  
      public Order(String orderId, Date orderTime, BigDecimal orderPrice, Integer orderStatus, Integer userId) {
          this.orderId = orderId;
          this.orderTime = orderTime;
          this.orderPrice = orderPrice;
          this.orderStatus = orderStatus;
          this.userId = userId;
      }
  
      public String getOrderId() {
          return orderId;
      }
  
      public void setOrderId(String orderId) {
          this.orderId = orderId;
      }
  
      public Date getOrderTime() {
          return orderTime;
      }
  
      public void setOrderTime(Date orderTime) {
          this.orderTime = orderTime;
      }
  
      public BigDecimal getOrderPrice() {
          return orderPrice;
      }
  
      public void setOrderPrice(BigDecimal orderPrice) {
          this.orderPrice = orderPrice;
      }
  
      public Integer getOrderStatus() {
          return orderStatus;
      }
  
      public void setOrderStatus(Integer orderStatus) {
          this.orderStatus = orderStatus;
      }
  
      public Integer getUserId() {
          return userId;
      }
  
      public void setUserId(Integer userId) {
          this.userId = userId;
      }
  
      @Override
      public String toString() {
          return "Order{" +
                  "orderId='" + orderId + '\'' +
                  ", orderTime=" + orderTime +
                  ", orderPrice=" + orderPrice +
                  ", orderStatus=" + orderStatus +
                  ", userId=" + userId +
                  '}';
      }
  }
  ```

##### b. 订单商品项实体类

* 在entity 包下创建订单商品项实体类——OrderItem

  > PS：MySQL中`DATETIME`数据类型对应Java中的`java.sql.Timestamp`，参考博文——https://blog.csdn.net/qq_37418745/article/details/79126180

  ```java
  public class Order {
      private String orderId;
      private Timestamp orderTime;
      private BigDecimal orderPrice;
      private Integer orderStatus;
      private Integer userId;
  
      public Order() {
      }
  
      public Order(String orderId, Timestamp orderTime, BigDecimal orderPrice, Integer orderStatus, Integer userId) {
          this.orderId = orderId;
          this.orderTime = orderTime;
          this.orderPrice = orderPrice;
          this.orderStatus = orderStatus;
          this.userId = userId;
      }
  
      public String getOrderId() {
          return orderId;
      }
  
      public void setOrderId(String orderId) {
          this.orderId = orderId;
      }
  
      public Timestamp getOrderTime() {
          return orderTime;
      }
  
      public void setOrderTime(Timestamp orderTime) {
          this.orderTime = orderTime;
      }
  
      public BigDecimal getOrderPrice() {
          return orderPrice;
      }
  
      public void setOrderPrice(BigDecimal orderPrice) {
          this.orderPrice = orderPrice;
      }
  
      public Integer getOrderStatus() {
          return orderStatus;
      }
  
      public void setOrderStatus(Integer orderStatus) {
          this.orderStatus = orderStatus;
      }
  
      public Integer getUserId() {
          return userId;
      }
  
      public void setUserId(Integer userId) {
          this.userId = userId;
      }
  
      @Override
      public String toString() {
          return "Order{" +
                  "orderId='" + orderId + '\'' +
                  ", orderTime=" + orderTime +
                  ", orderPrice=" + orderPrice +
                  ", orderStatus=" + orderStatus +
                  ", userId=" + userId +
                  '}';
      }
  }
  ```

#### 6.1.4 创建dao类

##### a. 订单Dao

* 创建OrderDao接口，接口中有以下4种方法

  1. `addOrder(订单对象)`：添加订单对象
  2. `queryOrders()`：查询所有订单信息，返回订单对象集合
  3. `queryOrdersByUserId(userId)`：查询指定用户id的订单信息，返回对应id的订单对象集合
  4. `updateOrderStatus(orderId, updatedStatus)`：根据订单号修改订单状态

  ```java
  public interface OrderDao {    /**     * 添加新的订单对象     * @param newOrder 新订单对象     * @return 返回影响行数     */    int addOrder(Order newOrder);    /**     * 查询全部订单信息     * @return 返回订单对象集合     */    List<Order> queryOrders();    /**     * 根据用户id查询对应的全部订单信息     * @param userId 用户id     * @return 返回对应id的订单对象集合     */    List<Order> queryOrdersByUserId(int userId);    /**     * 更新订单的状态     * @param orderId 订单号     * @param newStatus 新的状态信息     * @return 返回影响行数     */    int updateOrderStatus(String orderId, int newStatus);}
  ```

##### b. 订单商品项Dao

* 创建OrderItemDao接口，接口中有以下2种方法

  1. `addOrderItem(订单商品项)`：在指定订单中添加订单商品项对象
  2. `queryOrderItems(orderId)`：根据订单号查询对应的订单商品项，返回订单商品项集合

  ```java
  public interface OrderItemDao {
  
      /**
       * 添加新的订单项对象
       * @param newItem 新的订单项对象
       * @return 返回影响行数
       */
      int addOrderItem(OrderItem newItem);
  
      /**
       * 根据订单号查询对应的全部订单商品项信息
       * @param orderId 订单号
       * @return 返回订单项对象集合
       */
      List<OrderItem> queryOrderItems(String orderId);
  }
  ```

#### 6.1.5 创建service类

* 创建OrderService 接口，接口中有6种方法（分别对应6种业务功能）

  > PS：功能方法的具体实现参见6.2和6.3

  1. `createOrder(cart, userId)`：根据指定用户的购物车所有商品生成订单
  2. `checkMyOrders(userId)`：查看我的订单
  3. `signForOrder(orderId)`：签收订单/确认收货
  4. `checkAllOrders()`：查看全部订单信息
  5. `sendOrder(orderId)`：发货（将订单状态改成已发货）
  6. `checkOrderItemDetails(orderId)`：查看订单详情

  ```java
  public interface OrderService {
  
      /**
       * 根据购物车信息和用户id生成订单
       * @param cart 购物车对象
       * @param userId 用户id
       * @return 返回订单号
       */
      String createOrder(Cart cart, int userId);
  
      /**
       * 查看我的订单
       * @param userId 用户id
       * @return 返回对应id的订单集合
       */
      List<Order> checkMyOrders(int userId);
  
      /**
       * 签收订单
       * @param orderId 订单号
       */
      void signForOrder(String orderId);
  
      /**
       * 查看对应订单号的全部订单项信息
       * @param orderId 订单号
       * @return 返回订单项对象集合
       */
      List<OrderItem> checkOrderItemDetails(String orderId);
  
      /**
       * 查看全部订单信息
       * @return 返回订单对象集合
       */
      List<Order> checkAllOrders();
  
      /**
       * 发货
       * @param orderId 订单号
       */
      void sendOrder(String orderId);
  }
  ```

#### 6.1.6 创建Servlet

* 创建OrderSevlet 类，类中添加6种功能方法，分别对应service 类中的方法

  > PS：功能方法的具体实现参见6.2和6.3

  1. `createOrder()`：创建订单
  2. `checkMyOrders()`：查看我的订单
  3. `signForOrder()`：签收订单
  4. `checkAllOrders()`：查看所有订单
  5. `sendOrder()`：发货
  6. `checkOrderItemDetails()`：查看订单详情

  ```java
  public class OrderServlet extends BaseServlet {    private final OrderService oService = new OrderServiceImpl();    /**     * 生成订单     */    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {   		// code...    }        // code...}
  ```

***

### 6.2 订单功能1.0

#### 6.2.1 创建订单

1. 在service 类的对应功能方法中，先创建一个订单号`orderId`，订单号由时间戳+用户id值组成，保证订单号的唯一性；再根据订单号、购物车总价、购物车商品总数、当前时间、订单状态（默认为0）以及对用户id创建出订单对象；然后调用OrderDao 类的对应方法，把创建出来的新订单对象存储到数据库；接着根据购物车中所有商品项对象+订单号转成订单项对象，然后再调用OrderItemDao 类的对应方法，把所有订单项对象存储到数据库中；==最后将清空购物车==，然后返回订单号

   ```java
   @Override
   public String createOrder(Cart cart, int userId) {
       // 1. 先创建出唯一的订单号= 时间戳+用户id
       String orderId = System.currentTimeMillis() + userId + "";
   
       // 2. 根据订单号创建订单对象
       Order newOrder = new Order(orderId, new Timestamp(System.currentTimeMillis()), 
                                  cart.getItemTotalPrice(), 0, userId);
   
       // 3. 调用dao方法，保存新订单对象
       oDao.addOrder(newOrder);
   
       // 4. 遍历购物车商品项，将商品项对象一一转化为订单项对象
       for (CartItem cItem : cart.getItems().values()) {
           // 4.1 直接转化成订单项对象
           OrderItem newItem = new OrderItem(cItem.getId(), cItem.getName(), cItem.getCount(),
                                             cItem.getPrice(), cItem.getTotalPrice(), orderId);
   
           // 4.2 调用OrderItemDao存储对象
           oItemDao.addOrderItem(newItem);
       }
   
       // 5. 生成订单后清空购物车
       CartService cartService = new CartServiceImpl(cart);
       cartService.clearCart();
   
       return orderId;
   }
   ```

2. 在OrderServlet 的`createOrder()`方法中，先从session域中获取购物车对象和用户对象，如果获取的用户对象为null，即用户还没登录，就直接请求转发到登录页面，先让用户登录；如果用户对象不为null，即已登陆，就获取用户id，然后根据购物车对象和用户id调用service 类方法，并返回订单号，再将订单号存储到session域中，最后重定向回到下单成功的页面（checkout.jsp）

   ```java
   protected void createOrder(HttpServletRequest req, 
                              HttpServletResponse resp) throws ServletException, IOException {
       // 先获取session对象
       HttpSession session = req.getSession();
   
       // 1. 获取session域中的用户对象
       User loginUser = (User) session.getAttribute("loginUser");
   
       // 2. 如果用户对象为null
       if (null == loginUser) {
           // 2.1 用户未登录，直接请求转发到登录页面，然后结束当前方法
           req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
           return ;
       }
   
       /* 以下是用户已登录的操作 */
   
       // 3. 获取session域中的购物车对象
       Cart cart = (Cart) session.getAttribute("cart");
   
       // 4. 调用service类方法，获取生成的订单号
       String orderId = oService.createOrder(cart, loginUser.getId());
   
       // 5. 将订单号存储到session域中
       session.setAttribute("orderId", orderId);
   
       // 6. 重定向到下单成功页面（checkout.jsp）
       resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
   }
   ```

3. 购物车页面（cart.jsp）由 <u>去结账</u> 的超链接，将链接的路径改成跳转到OrderServlet，并带上请求参数，调用对应的`createOrder()`方法，生成订单，并把订单号在页面上显示

   ```jsp
   <a href="orderServlet?action=createOrder">去结账</a>
   ```

4. 在service 方法中添加代码，修改生成订单后图书商品的销量和库存，把订单对象保存到数据库后，调用bookDao类方法，获取对应商品id的图书对象，然后修改图书的销量和库存数量，再调用bookDao类方法将修改后的图书对象更新到数据库中

   ```java
   // 4.3 调用bookDao类获取对应id的图书对象Book book = bDao.queryBookById(cItem.getId());// 4.4 修改图书销量和库存book.setSales(book.getSales() + cItem.getCount());book.setStock(book.getStock() - cItem.getCount());// 4.5 将更新后的图书对象存储回数据库bDao.updateBookInfo(book);
   ```

#### 6.2.2 查看我的订单

1. 在service 功能方法中，根据用户id调用dao类的对应方法，获取对应用户的全部订单对象，然后返回对象集合

   ```java
   @Overridepublic List<Order> checkMyOrders(int userId) {    // 调用dao类方法    return oDao.queryOrdersByUserId(userId);}
   ```

2. 在首页中点击 <u>我的订单</u> 超链接，跳转到OrderServlet，并带上请求参数，调用对应的功能方法`checkMyOrder()`

   ```jsp
   <a href="orderServlet?action=checkMyOrders">我的订单</a>
   ```

3. 在OrderServlet的`checkMyOrder()`方法中，先获取session域中的用户对象，因为 <u>我的订单</u> 只有在用户已经登录后才会显示，所以不用判断用户对象是否为null，直接获取用户id并调用service 方法，获取出对应的订单对象集合，再将集合存储到request域中，最后请求转发到订单页面（order.jsp）

   ```java
   protected void checkMyOrders(HttpServletRequest req,                              HttpServletResponse resp) throws ServletException, IOException {    // 先获取session对象    HttpSession session = req.getSession();    // 1. 获取session域中的用户对象    User loginUser = (User) session.getAttribute("loginUser");    /* 因为 [我的订单] 只有在用户已经登录后才会显示，所以不用判断loginUser是否为null */    // 2. 根据用户id调用service方法    List<Order> myOrders = oService.checkMyOrders(loginUser.getId());    // 3. 将集合存储到request域中    req.setAttribute("myOrders", myOrders);    // 4. 最后请求转发到订单页面    req.getRequestDispatcher("/pages/order/order.jsp").forward(req, resp);}
   ```

4. 在订单页面（order.jsp）中遍历订单对象集合，将订单信息全部显示出来，如果当前没有订单，就显示提示信息

   ```jsp
   <%-- 如果没有订单信息 --%><c:if test="${empty requestScope.myOrders}">    <tr>        <td colspan="5">亲，当前无订单信息，<a style="font-size: 16px" href="pages/cart/cart.jsp">快去购物车结账吧</a></td>    </tr></c:if><%-- 有订单信息就显示 --%><c:if test="${not empty requestScope.myOrders}">    <%-- 遍历订单信息 --%>    <c:forEach items="${requestScope.myOrders}" var="myOrder">        <tr>            <td>${myOrder.orderId}</td>            <td>${myOrder.orderTime}</td>            <td>${myOrder.orderPrice}</td>            <%-- 根据订单状态值显示对应的状态 --%>            <c:choose>                <c:when test="${myOrder.orderStatus == 0}">                    <td>未发货</td>                </c:when>                <c:when test="${myOrder.orderStatus == 1}">                    <td>已发货</td>                </c:when>                <c:otherwise>                    <td>已签收</td>                </c:otherwise>            </c:choose>            <td><a href="#">查看详情</a></td>        </tr>    </c:forEach></c:if>
   ```

#### 6.2.3 签收订单/确认收货

1. 在service 类对应功能方法中，根据订单号调用dao类方法，修改对应订单对象的状态，将状态值改成2，即已签收

   ```java
   @Overridepublic void signForOrder(String orderId) {    // 直接调用dao类方法    oDao.updateOrderStatus(orderId, 2);}
   ```

2. 订单项详情页（orderItem.jsp）下列信息栏中，会有提示信息，如果管理员（商家）对该订单已经发货，即显示 <u>确认收货</u> 的超链接，点击后跳转到OrderServlet，带上请求参数，将当前订单号传给Servlet，并调用对应功能方法`signForOrder()`

   ```jsp
   <a style="font-size: 17px" href="orderServlet?action=signForOrder&orderId=${requestScope.orderId}">确认收货</a>
   ```

3. 在OrderServlet的`signForOrder()`方法中，先获取请求参数中的订单号，根据订单号调用service类的对应方法，修改订单对象状态，最后重定向跳转到`/orderServlet?action=checkMyOrders`，即刷新后的订单页面

   ```java
   protected void signForOrder(HttpServletRequest req, 
                               HttpServletResponse resp) throws ServletException, IOException {
       // 1. 获取请求参数中的订单号
       String orderId = req.getParameter("orderId");
   
       // 2. 调用service方法
       oService.signForOrder(orderId);
   
       // 3. 重定向到订单页面，并刷新
       resp.sendRedirect(req.getContextPath() + "/orderServlet?action=checkMyOrders");
   }
   ```

#### 6.2.4 查看订单详情

1. 在service 类对应功能方法中，根据订单号调用dao类方法，获取对应订单号的全部订单项信息，并返回

   ```java
   @Override
   public List<OrderItem> checkOrderItemDetails(String orderId) {
       // 直接调用orderItemDao对应方法
       return oItemDao.queryOrderItems(orderId);
   }
   ```

2. 订单页面（order.jsp）中每一个订单有 <u>查看详情</u> 超链接，点击跳转到OrderServlet，带上请求参数，将对应订单的订单号传给Servlet，并调用对应的功能方法`checkOrderItemDetails()`

   > PS：用户层面的查看订单详情的资源路径中，多了`flag`的请求参数，用来标识是用户的访问还是管理员的访问

   ```jsp
   <a href="orderServlet?action=checkOrderItemDetails&orderId=${myOrder.orderId}&flag=1">查看详情</a>
   ```

3. 在OrderServlet的`checkOrderItemDetails()`方法中，先获取请求参数中的订单号，然后根据该订单号调用service类方法，获取对应的订单项集合，再根据订单号调用service方法获取对应订单的状态值，然后将订单集合、订单号以及订单状态值存储到request域中，最后请求转发到订单项详情页（orderItem.jsp）

   > PS：存储订单号是为了方便签收订单时使用

   ```java
   protected void checkOrderItemDetails(HttpServletRequest req,                                      HttpServletResponse resp) throws ServletException, IOException {    // 1. 获取请求参数中的订单号    String orderId = req.getParameter("orderId");    // 2. 调用service方法    List<OrderItem> orderItems = oService.checkOrderItemDetails(orderId);    // 3. 根据订单号获取对应的订单状态值    int status = oService.getStatusByOrderId(orderId);    // 4. 将获取的集合以及订单号存储到request域中    req.setAttribute("orderItems", orderItems);    req.setAttribute("orderId", orderId);    req.setAttribute("status", status);    // 5. 最后请求转发到订单项详情页    req.getRequestDispatcher("/pages/order/orderItem.jsp").forward(req, resp);}
   ```

4. 在订单项详情页（orderItem.jsp）中遍历订单项集合，将信息展示到页面

   ```jsp
   <table>    <tr>        <td>商品名称</td>        <td>数量</td>        <td>单价</td>        <td>金额</td>    </tr>    <%-- 遍历订单项集合 --%>    <c:forEach items="${requestScope.orderItems}" var="item">        <tr>            <td>${item.name}</td>            <td>${item.count}</td>            <td>${item.price}</td>            <td>${item.totalPrice}</td>        </tr>    </c:forEach></table>
   ```

   5. 在订单详情页中，如果当前订单已经发货，就显示对应信息，并显示 <u>确认收货</u> 的超链接供用户点击，如果当前订单未发货或者已签收，就直接显示对应信息即可

   ```jsp
   <%-- 订单确认收货信息，这里需判断是否是用户访问，是才显示 --%><c:if test="${not empty param.flag}">    <div class="cart_info">        <%-- 根据订单状态显示对应信息 --%>        <c:choose>            <c:when test="${requestScope.status == 0}">                <span class="cart_span">订单未发货</span>            </c:when>            <c:when test="${requestScope.status == 1}">                <span class="cart_span">订单已发货，                    <a style="font-size: 17px" href="orderServlet?action=signForOrder&orderId=${requestScope.orderId}">确认收货</a>                </span>            </c:when>            <c:otherwise>                <span class="cart_span">订单已签收</span>            </c:otherwise>        </c:choose>    </div></c:if>
   ```

***

### 6.3 订单功能2.0

#### 6.3.1 查看所有订单

1. 在service方法对应功能方法中，直接调用dao类方法，获取全部用户及其全部订单信息

   ```java
   @Override
   public List<Order> checkAllOrders() {
       // 直接调用dao类获取全部订单对象
       return oDao.queryOrders();
   }
   ```

2. 在管理员页面中有 <u>订单管理</u> 超链接，点击后跳转到OrderServlet，带上请求参数，调用对应功能方法`checkOrders()`

   ```jsp
   <a href="orderServlet?action=checkAllOrders">订单管理</a>
   ```

3. 在OrderServlet的`checkAllOrders()`方法中，直接调用service类对应方法，获取订单对象集合，然后将集合存储到request域中，最后请求转发到订单管理页面（order_manager.jsp）

   ```java
   protected void checkAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // 1. 调用service方法，获取全部订单信息
       List<Order> allOrders = oService.checkAllOrders();
   
       // 2. 将订单集合存储到request域中
       req.setAttribute("allOrders", allOrders);
   
       // 3. 最后请求转发到订单管理页面
       req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req, resp);
   }
   ```

4. 在订单管理页面（order_manager.jsp）中，遍历全部订单信息，如果没有用户下过订单就显示提示信息

   ```jsp
   <%-- 如果还没有用户下过订单 --%>
   <c:if test="${empty requestScope.allOrders}">
       <tr>
           <td colspan="6">亲，当前未有用户下过订单~</td>
       </tr>
   </c:if>
   
   <%-- 有用户下过订单 --%>
   <c:if test="${not empty requestScope.allOrders}">
       <%-- 遍历订单信息 --%>
       <c:forEach items="${requestScope.allOrders}" var="order">
           <tr>
               <td>${order.orderId}</td>
               <td>${order.orderTime}</td>
               <td>${order.orderPrice}</td>
               <td>${order.userId}</td>
               <td><a href="#">查看详情</a></td>
               <td><a href="#">点击发货</a></td>
           </tr>
       </c:forEach>
   </c:if>
   ```

#### 6.3.2 发货

1. 在service 类对应功能方法中，调用dao类方法，修改对应订单的状态，将状态值改成1，即已发货状态

   ```java
   @Overridepublic void sendOrder(String orderId) {    // 直接调用dao    oDao.updateOrderStatus(orderId, 1);}
   ```

2. 在订单管理页面（order_manager.jsp）中每一项订单有 <u>发货</u> 的超链接，点击后跳转到OrderServlet，带上请求参数，将对应的订单号传给后台，并调用对应的功能方法`sendOrder()`

   ```jsp
   <a style="font-size: 17px" href="orderServlet?action=sendOrder&orderId=${order.orderId}">发货</a>
   ```

3. 在OrderServlet 的`sendOrder()`方法中，先获取请求参数中的订单号，再调用service类方法，修改对应订单的状态为已发货，最后重定向到`/orderServlet?action=checkAllOrders`，刷新订单管理页面

   ```java
   protected void sendOrder(HttpServletRequest req, 
                            HttpServletResponse resp) throws ServletException, IOException {
       // 1. 获取请求参数
       String orderId = req.getParameter("orderId");
   
       // 2. 调用service方法
       oService.sendOrder(orderId);
   
       // 3. 重定向到订单管理页面，并刷新
       resp.sendRedirect(req.getContextPath() + "/orderServlet?action=checkAllOrders");
   }
   ```

4. 在订单管理页面中根据订单当前的状态显示对应的状态信息

#### 6.3.3 查看订单详情

* 与用户层面的功能实现类似，修改对应页面及其跳转路径即可，这里不再重复

## 七、项目的功能改进

### 7.1 使用Filter实现权限管理

1. 在web包下的filter包下创建一个Filter过滤器，过滤器的`doFilter()`方法中，先将ServletRequest 类型的request对象转换成HttpServletRequest 类型，然后获取session域中的用户对象，判断用户对象是否为null，如果为null，即未登录，就直接请求转发到登录页面，如果不为null，就直接放行`filterChain.doFilter()`

   ```java
   @Override
   public void doFilter(ServletRequest servletRequest, 
                        ServletResponse servletResponse, 
                        FilterChain filterChain) throws IOException, ServletException {
       // 1. 先将req转成HttpServletRequest
       HttpServletRequest req = (HttpServletRequest) servletRequest;
   
       // 2. 获取session域中的用户对象
       User loginUser = (User) req.getSession().getAttribute("loginUser");
   
       // 3. 判断用户对象是否为null
       if (null == loginUser) {
           // 为null，表示没登陆，请求转发到登录页面
           req.getRequestDispatcher("/pages/user/login.jsp").forward(servletRequest, servletResponse);
       } else {
           // 不为null，已经登录，放行
           filterChain.doFilter(servletRequest, servletResponse);
       }
   }
   ```

2. 在web.xml 文件中配置Fliter过滤器，需要扫描的资源路径设置为`/pages/manager/*`和`/manager/bookServlet`，对管理员页面进行权限管理，必须登录后才能访问

   ```xml
   <!-- 配置过滤器 -->
   <filter>
       <filter-name>managerFilter</filter-name>
       <filter-class>com.key.web.filter.ManagerFilter</filter-class>
   </filter>
   <filter-mapping>
       <filter-name>managerFilter</filter-name>
       <url-pattern>/pages/manager/*</url-pattern>
       <url-pattern>/manager/bookServlet</url-pattern>
   </filter-mapping>
   ```

***

### 7.1 ThreadLocal是什么？

#### 7.1.1 ThreadLocal 简介

* 作用：可以给==当前线程关联一个数据==（普通数据、对象、集合等），解决==多线程数据安全问题==
* 特点
  1. ThreadLocal ==类似于Map集合==，key为当前线程，value为关联的数据
  2. 一个ThreadLocal 实例对象只能为当前线程关联一个数据，如果要为当前线程关联多个数据，需要创建多个ThreadLocal 实例对象
  3. 创建ThreadLocal 实例对象时，一般用`private static`修饰
  4. 在当前线程销毁后，ThreadLocal 中关联的数据会被JVM自动释放

#### 7.1.2 ThreadLocal 测试

* ThreadLocal 测试类

  > 这里采用main函数进行测试的原因：如果`@Test`测试会比较麻烦，因为`@Test`不会等待其他线程结束再去结束程序，只要`@Test`方法执行完成，那么这个程序也就结束了；而在main方法中运行时会区分子线程是否是守护线程。参考博文——[在@Test线程中多线程的方法并不起作用](https://blog.csdn.net/weixin_42779370/article/details/108157969?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522163266149416780265459949%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=163266149416780265459949&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~rank_v31_ecpm-1-108157969.pc_search_result_hbase_insert&utm_term=%40Test%E6%B5%8B%E8%AF%95%E7%BA%BF%E7%A8%8B&spm=1018.2226.3001.4187)

  ```java
  public class ThreadLocalTest implements Runnable {
      private static final java.lang.ThreadLocal<Object> threadLocal = new java.lang.ThreadLocal<>();
      private static final Random random = new Random();
  
      @Override
      public void run() {
          // 先生成一个随机数，用于被关联
          int i = random.nextInt(100);
  
          // 获取当前线程名，用于打印
          String currThreadName = Thread.currentThread().getName();
  
          // 打印当前线程中的对应的随机数
          System.out.println("线程【" + currThreadName + "】生成随机数是 --> " + i);
  
          // 将随机数保存到threadLocal中，即让threadLocal关联该数据
          threadLocal.set(i);
  
          // 然后当前线程休眠一段时间
          try {
              Thread.sleep(2000);
  
              // 线程结束时取出ThreadLocal中的数据并打印
              System.out.println("线程【" + currThreadName + "】快结束时的数据为 --> " + threadLocal.get());
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  
      /**
       * main函数测试
       */
      public static void main(String[] args) {
  
          // 创建多个线程并执行run()
          for (int i = 0;i < 3;i++) {
              new Thread(new ThreadLocalTest()).start();
          }
      }
  }
  ```

***

### 7.3 添加ThreadLocal+事务管理

#### 7.3.1 修改JdbcUtil 工具类

1. 在工具类中创建ThreadLocal 对象，泛型为`Connection`，即采用ThreadLocal关联当前线程的连接对象conn，保证当前线程中只有一个连接对象，从而实现项目中每一个功能操作都在一个事务中

   > 使用ThreadLocal的前提是：项目中所有功能操作都在一个线程中，即一个ThreadLocal 对象只能在一个线程中使用，整个项目正好就在一个线程中，可打印当前线程名验证

   ```java
   private static final ThreadLocal<Connection> threadLocalOfConn = new ThreadLocal<>();
   ```

2. 修改`getConn()`方法，先从ThreadLocal对象中获取连接对象，再判断获取的对象是否为null，如果为null，就从数据库连接池对象（druid连接池）中获取新的连接对象，并存储到ThreadLocal对象中，然后开启事务管理（关闭自动提交事务），最后再将连接对象返回

   ```java
   public static Connection getConn() throws SQLException {
       // 1. 先从threadLocalOfConn中获取连接对象
       Connection conn = threadLocalOfConn.get();
   
       // 2. 判断连接对象是为null
       if (null == conn) {
           // 2.1 从数据库连接池获取新的连接对象
           conn = ds.getConnection();
   
           // 2.2 将新的连接对象存储到threadLocal中
           threadLocalOfConn.set(conn);
   
           // 2.3 直接开启事务（关闭自动提交事务）
           conn.setAutoCommit(false);
       }
   
       return conn;
   }
   ```

3. 删除`returnResource()`方法，为事务提交和资源归还操作、事务回滚和资源归还操作分别创建对应的方法，在提交事务或回滚事务后，即一个事务结束后，必须==调用ThreadLocal对象的`remove()`方法==，不然会报错

   > 调用`remove()`方法的原因：tomcat服务器底层使用的是线程池技术。。。

   ```java
   /**
    * 提交事务并归还资源
    */
   public static void commitAndClose() {
       Connection conn = threadLocalOfConn.get();
   
       if (null != conn) {
           try {
               conn.commit();
           } catch (SQLException e) {
               e.printStackTrace();
           } finally {
               try {
                   conn.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }
   
       // 提交事务后一定要将treadLocal对象移除，因为tomcat底层使用了线程池技术
       threadLocalOfConn.remove();
   }
   
   /**
    * 回滚事务并归还资源
    */
   public static void rollbackAndClose() {
       Connection conn = threadLocalOfConn.get();
   
       if (null != conn) {
           try {
               conn.rollback();
           } catch (SQLException e) {
               e.printStackTrace();
           } finally {
               try {
                   conn.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }
   
       // 回滚事务后一定要将treadLocal对象移除，因为tomcat底层使用了线程池技术
       threadLocalOfConn.remove();
   }
   ```

#### 7.3.2 异常获取后抛出

> PS：因为项目中添加了事务管理，对于有更新的功能，出现异常时必须回滚事务，而回滚事务的操作放在事务过滤器中，所以在执行相关的功能方法时，不能获取异常后只处理，需要获取异常后再将异常往外抛出，一直抛到过滤器类中处理，才能实现事务的回滚

* 将BaseDaoe 类和BaseServlet 类中每一个方法的异常获取后都进行抛出

  ```java
  try {
  
      // code...
  } catch (SQLException e) {
      e.printStackTrace();
      // 异常获取后就抛出
      throw new RuntimeException(e);
  }
  ```

#### 7.3.3 创建事务管理过滤器

* 创建事务管理过滤器，直接在`doFilter()`方法中，对放行操作`filterChain.doFilter()`添加`try...catch()...`语句，并在语句中添加事务的提交和回滚以及归还资源操作，相当于给全部功能方法中的实现代码都添加了`try...catch()...`

  ```java
  @Override
  public void doFilter(ServletRequest servletRequest, 
                       ServletResponse servletResponse, 
                       FilterChain filterChain) throws IOException, ServletException {
  
      // 直接给放行方法添加try...catch...语句
      try {
          // 直接放行，相当于执行每一个功能方法
          filterChain.doFilter(servletRequest, servletResponse);
  
          // 提交事务并释放资源
          JdbcUtil.commitAndClose();
      } catch (IOException | ServletException e) {
          // 出现异常，回滚事务并释放资源
          JdbcUtil.rollbackAndClose();
  
          e.printStackTrace();
      }
  }
  ```

***

### 7.4 使用tomcat统一管理异常

1. 将事务过滤器中的异常捕获后再抛出，让tomcat服务器收到该异常

   ```java
   try {
       // 直接放行，相当于执行每一个功能方法
       filterChain.doFilter(servletRequest, servletResponse);
   
       // 提交事务并释放资源
       JdbcUtil.commitAndClose();
   } catch (IOException | ServletException e) {
       // 出现异常，回滚事务并释放资源
       JdbcUtil.rollbackAndClose();
   
       e.printStackTrace();
   
       // 事务回滚后、异常捕获后继续往外抛
       throw new RuntimeException(e);
   }
   ```

2. 创建对应的错误jsp页面，在页面中显示对应的错误信息

   ```jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
       <head>
           <title>404错误页面</title>
   
           <%-- 使用jsp指令，引入base标签和其他资源 --%>
           <%@ include file="/pages/common/head.jsp"%>
       </head>
       <body>
           <span>404 --> 很抱歉！您访问的页面不存在！或已被删除</span>
           <br/>
           <span><a href="index.jsp">返回首页</a></span>
       </body>
   </html>
   ```

3. 在wen.xml 中用`<error-page>`标签配置错误页面路径，将错误类型（响应码）与要显示的错误页面对应起来

   ```xml
   <!-- 配置错误页面 -->
   <error-page>
       <error-code>404</error-code>
       <location>/pages/error/error404.jsp</location>
   </error-page>
   ```

