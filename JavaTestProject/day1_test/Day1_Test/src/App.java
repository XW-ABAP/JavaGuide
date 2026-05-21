import static java.lang.System.out;

/**
 * 外部类 App：作为整个程序的容器
 */

class Testfind {
    int a = 10;
    static int b = 20; // ✅ 完全合法
    int c = 30; // 成员变量 c：存在【堆】内存中，属于对象的一部分

    public void test() {
        System.out.println("这是一个测试方法");
        System.out.println("成员变量 a 的值是：" + a);
        System.out.println("静态变量 b 的值是：" + b);
        out.println("成员变量 c 的值是：" + c);
        out.print("成员变量 c 的值是：" + c);
    }

    // 💡 这是一个经典的包装类转换方法（装箱）
    public static Integer valueOf(int i) {
        // 如果在 -128 到 127 之间，直接去缓存数组里“人肉提货”
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        // 如果超出了范围，老老实实去堆内存 new 一个新的
        return new Integer(i);
    }

    // 💡 内部缓存类
    private static class IntegerCache {
        static final int low = -128;
        static final int high; // 铁脑壳常量
        static final Integer cache[]; // ➔ 1. 源码里必须有这个大仓库数组！

        // 静态代码块：专门用来给 static final 变量赋初始值，并初始化数组
        static {
            int h = 127; // 临时定一个最高位为 127
            high = h; // ✅ 2. 在这里正式给 final 常量 high 赋值，解除红线！

            // 3. 捏出这个能装下 256 个数字的数组仓库 (从 -128 到 127 一共 256 个数字)
            cache = new Integer[(high - low) + 1];
            int j = low;
            for (int k = 0; k < cache.length; k++) {
                cache[k] = new Integer(j++); // 提前把 -128~127 的对象全部塞进仓库
            }
        }
    }
}

public class App {

    /**
     * 1. 非静态内部类 Testbegin
     * 包含了你最初测试的控制流、作用域实验以及堆栈分配实验
     */
    public class Testbegin {
        public void test() {
            System.out.println("Hello, World!");

            // 局部变量 flag：存在【栈】内存中
            boolean flag = true;

            // for 循环：控制流程练习 (i 从 0 循环到 10)
            for (int i = 0; i <= 10; i++) {
                if (i == 0) {
                    System.out.println("0");
                } else if (i == 1) {
                    System.out.println("1");
                    continue; // 🚨 强行结束本轮循环，直接跳到 i++，本轮循环底部的 "xixi" 不会打印
                } else if (i == 2) {
                    System.out.println("2");
                    flag = false; // 💡 在循环中途改变了状态位
                } else if (i == 3) {
                    System.out.println("3");
                    break; // 🚨 暴力砸碎并跳出整个 for 循环，i>=4 的代码全部作废
                } else if (i == 4) {
                    System.out.println("4");
                }
                // 只要没被 continue 或 break 拦截，每轮都会打印 xixi
                System.out.println("xixi");
            }

            // 循环结束后，因为 i==2 时 flag 被改成了 false，这里的 if 不成立
            if (flag) {
                System.out.println("haha");
                return; // 如果执行到这，整个 test() 方法会直接宣告结束
            }
            // 顺理成章走到这里
            System.out.println("hehe");

            /*
             * ---------- 内存堆栈与静态检查实验（已注释） ----------
             * int age = 18;
             * // 实验 1 报错：Type mismatch (类型不匹配)，int 变量不能塞 String 字符串
             * age = "Hello";
             * 
             * // 实验 2 报错：基本数据类型 int 在栈中只是纯数值，没有 .length() 方法
             * System.out.println(age.length());
             * 
             * // 实验 3 报错：块级作用域限制。money 定义在 if 的 {} 内部，出了括号在内存中已被自动销毁
             * if (age > 10) {
             * int money = 100;
             * }
             * System.out.println(money);
             * --------------------------------------------------
             */

            // 基础类型 age：数据实体直接死死钉在【栈】内存中
            int age = 18;
            // 引用类型 name：变量名（提货单）在【栈】上，后面的实体通过 new 存在【堆】大仓库中
            String name = new String("Xiao Yong");
            // 消费变量，消除 VS Code 的黄色未引用警告 (Warning)
            System.out.println(name + " 今年 " + age + " 岁");
        }
    }

    /**
     * 2. 非静态内部类 Test
     * 依赖外部类 App 的实例对象才能被实例化
     */
    public class Test {
        int a = 10; // 成员变量 a：存在【堆】内存中，属于对象的一部分
        // static int b = 20; // 静态成员变量 b：存在【方法区】内存中，属于类级别的属性
        // 上面这个代码怎么解决呢
        // 第一种方法
        // // 💡 加了 static，Test 独立了，里面自然就能放 static 变量了
        // public static class Test {
        // int a = 10;
        // static int b = 20; // ✅ 瞬间合法，红线消失！

        // public void test() {
        // System.out.println("这是一个测试方法");
        // }
        // }
        // 第二种方法 新建一个 class Testfind 在外部类 App 的同级位置，专门用来放 static 变量
        public void test() {
            System.out.println("这是一个测试方法");
        }

        public void test2() {
            System.out.println("这是另一个测试方法");
            System.out.println("成员变量 a 的值是：" + a);
            int c = 30; // 局部变量 c：存在【栈】内存中，方法调用结束后自动销毁
            System.out.println("局部变量 c 的值是：" + c);
            // static int d = 40; // 🚨 错误：局部变量不能被 static 修饰，因为它们的生命周期和方法调用绑定在一起，无法满足 static
            // 的类级别要求
        }
    }

    /**
     * 3. 静态内部类 StaticTest
     * 加上了 static 关键字，独立于外部类，可在 main 中直接凭空 new 出来
     */
    public static class StaticTest {
        public void test() {
            System.out.println("这是一个静态内部类的测试方法");
        }
    }

    /**
     * 4. 业务接口 OrderService
     * 相当于 ABAP 的 DEFINITION 段，只负责声明方法结构，没有任何代码肉身
     */
    public interface OrderService {
        void createOrder(String orderId);
    }

    /**
     * 5. 接口实施类 OrderServiceImpl（非静态内部类）
     * 相当于 ABAP 的 IMPLEMENTATION，通过 implements 关键字来填充业务血肉
     */
    public class OrderServiceImpl implements OrderService {
        @Override // @Override 标签表示这个方法是重写/实现了父辈的方法
        public void createOrder(String orderId) {
            System.out.println("订单 " + orderId + " 已创建");
        }
    }

    /**
     * 6. 接口实施类 StaticOrderServiceImpl（静态内部类版本）
     */
    public static class StaticOrderServiceImpl implements OrderService {
        @Override
        public void createOrder(String orderId) {
            System.out.println("订单 " + orderId + " 已创建（静态内部类版本）");
        }
    }

    /**
     * 7. 接口实施类 OrderServiceSapImpl（非静态内部类-SAP 专属业务版）
     */
    public class OrderServiceSapImpl implements OrderService {
        @Override
        public void createOrder(String orderId) {
            System.out.println("订单 " + orderId + " 已创建（SAP 版本）");
        }
    }

    /**
     * 🚀 main 方法：整个 Java 程序的绝对入口（由 static 修饰，属于类级别）
     * 💡 已经修正为标准的 public static void main！
     */
    public static void main(String[] args) throws Exception {

        // ==========================================
        // 🛠️ 情况一：调用【非静态内部类】（Testbegin, Test, OrderServiceImpl, OrderServiceSapImpl）
        // 核心逻辑：必须先 new 出外部类对象 outer，再通过 outer.new 去呼唤内部类
        // ==========================================

        // 1. 先实例化外部类对象（买好 App 牌的房子）
        App outer = new App();

        // 2. 调用 Testbegin
        App.Testbegin tb = outer.new Testbegin();
        tb.test();

        // 3. 调用 Test
        App.Test t = outer.new Test();
        t.test();

        // 4. 调用 OrderServiceImpl（面向接口编程：左边用接口声明，右边用具体实现类实例化）
        OrderService os = outer.new OrderServiceImpl();
        os.createOrder("SAP10002026");

        // 5. 调用 OrderServiceSapImpl（体现了多态解耦：左边接口雷打不动，右边随时切换肉身）
        OrderService os1 = outer.new OrderServiceSapImpl();
        os1.createOrder("SAP88882026");

        // ==========================================
        // 🛠️ 情况二：调用【静态内部类】（StaticTest, StaticOrderServiceImpl）
        // 核心逻辑：由于加了 static 突破了高墙，直接凭空 new，完全不需要用到 outer 对象
        // ==========================================

        // 1. 调用 StaticTest
        StaticTest st = new StaticTest();
        st.test();

        // 2. 调用 StaticOrderServiceImpl（静态类结合接口多态）
        OrderService staticOs = new StaticOrderServiceImpl();
        staticOs.createOrder("SAP99992026");

        // 3. 💡 调用最上面搬出去的独立外部类 Testfind
        Testfind tf = new Testfind();
        tf.test();
        System.out.println("外部类的静态变量 b 值是：" + Testfind.b);

        // 4 测试名场面
        System.out.println("\n测试名场面：Integer 缓存机制");
        Integer x = Testfind.valueOf(100);
        Integer y = Testfind.valueOf(100);
        System.out.println(x == y); // ➔ 猜猜看？结果是 true！

        Integer m = Testfind.valueOf(200);
        Integer n = Testfind.valueOf(200);
        System.out.println(m == n); // ➔ 结果是 false！因为超出了 127，是两个不同的 new 对象！
    }
}