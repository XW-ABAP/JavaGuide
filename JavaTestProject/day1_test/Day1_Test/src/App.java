public class App 
{
    public static void main(String[] args) throws Exception 
    {
        System.out.println("Hello, World!");
        boolean flag = true;
        for (int i = 0; i <= 10; i++)
        {
            if (i == 0)
            {
                System.out.println("0");
            }
            else if (i == 1)
            {
                System.out.println("1");
                continue;
            }
            else if (i == 2)
            {
                System.out.println("2");
                flag = false;
            }
            else if (i == 3)
            {
                System.out.println("3");
                break;
            }
            else if (i == 4)
            {
                System.out.println("4");
            }
            System.out.println("xixi" );
        }
        if (flag)
        {
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
        //     int money = 100;
        // }
        // System.out.println(money); // ❌ ❌ ❌ 试试看这里？（money 定义在 if 内部，在外面根本找不到它）
    }
}
