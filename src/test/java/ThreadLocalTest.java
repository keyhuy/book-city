import java.util.Random;

/**
 * 我的线程，用于测试ThreadLocal
 *
 * @author Key
 * @date 2021/09/26/20:47
 **/
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

    public static void main(String[] args) {

        // 创建多个线程并执行run()
        for (int i = 0;i < 3;i++) {
            new Thread(new ThreadLocalTest()).start();
        }
    }
}
