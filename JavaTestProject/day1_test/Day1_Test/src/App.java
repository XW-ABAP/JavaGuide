public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        boolean flag = true;
        for (int i = 0; i <= 10; i++) {
            if (i == 0) {
                System.out.println("0");
            } else if (i == 1) {
                System.out.println("1");
                continue;
            } else if (i == 2) {
                System.out.println("2");
                flag = false;
            } else if (i == 3) {
                System.out.println("3");
                break;
            } else if (i == 4) {
                System.out.println("4");
            }
            System.out.println("xixi");
        }
        if (flag) {
            System.out.println("haha");
            return;
        }
        System.out.println("hehe");

        // int age = 18;

        // // 破坏实验 1：类型牛头不对马嘴
        // age = "Hello"; // ❌ 试试看这里会不会拉红线？（把字符串塞给整数）

        // // 破坏实验 2：调用根本不存在的功能
        // System.out.println(age.length()); // ❌ ❌ 试试看这里？（基本类型 int 根本没有 length() 方法）

        // // 破坏实验 3：变量作用域越界
        // if (age > 10) {
        // int money = 100;
        // }
        // System.out.println(money); // ❌ ❌ ❌ 试试看这里？（money 定义在 if 内部，在外面根本找不到它）

        int age = 18;
        String name = new String("Xiao Yong");
        System.out.println(name + " 今年 " + age + " 岁");

        // 💡 核心改动：第一种经典的非静态内部类调用方式
        // 1. 先实例化外部类对象（先买好 App 牌的房子）
        App outer = new App();

        // 2. 拿着外部类对象去 new 内部类（通过 outer 这个房子，建好里面的 Test 主卧）
        App.Test t = outer.new Test();

        // 3. 成功调用方法！
        t.test();

        StaticTest st = new StaticTest();
        st.test();

        // 1. 因为 OrderServiceImpl 是非静态内部类，必须用我们上面创建好的外部类对象 outer 来 new 它
        OrderService os = outer.new OrderServiceImpl();

        // 2. 潇洒地调用接口方法
        os.createOrder("SAP10002026");

    }

    public class Test {
        public void test() {
            System.out.println("这是一个测试方法");
        }
    }

    public static class StaticTest {
        public void test() {
            System.out.println("这是一个静态内部类的测试方法");
        }
    }

    public interface OrderService {
        void createOrder(String orderId);
    }

    public class OrderServiceImpl implements OrderService {
        @Override
        public void createOrder(String orderId) {
            System.out.println("订单 " + orderId + " 已创建");
        }
    }

}
