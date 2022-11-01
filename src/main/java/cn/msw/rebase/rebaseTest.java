package cn.msw.rebase;

public class rebaseTest {

    public static void main(String[] args) {
            String s1 = new String("字符串");
            String s2 = "字符串";
            System.out.println(s2 == s2.intern()); // 因为字面量的创建方式是在字符串常量池中生成实例，而 intern() 方法返回常量池中的字符串引用，两个引用自然是同一个
            System.out.println(s1 == s1.intern()); // 因为 new String() 的方式是在堆（Heap）上创建实例，二者不是同一个引用
            System.out.println(s1.intern() == s2.intern()); //s1 的 intern() 返回的是 s2 的引用
    }
}
