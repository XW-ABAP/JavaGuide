/**
 * 外部类 App：作为整个程序的容器
 */
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

            /* ---------- 内存堆栈与静态检查实验（已注释） ----------
            int age = 18;
            // 实验 1 报错：Type mismatch (类型不匹配)，int 变量不能塞 String 字符串
            age = "Hello"; 
            
            // 实验 2 报错：基本数据类型 int 在栈中只是纯数值，没有 .length() 方法
            System.out.println(age.length()); 
            
            // 实验 3 报错：块级作用域限制。money 定义在 if 的 {} 内部，出了括号在内存中已被自动销毁
            if (age > 10) {
                int money = 100;
            }
            System.out.println(money); 
            -------------------------------------------------- */

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
        public void test() {
            System.out.println("这是一个测试方法");
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
    }
}