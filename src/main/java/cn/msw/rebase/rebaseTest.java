package cn.msw.rebase;

public class rebaseTest {

    public static void main(String[] args) {
            String s1 = new String("字符串");
            String s2 = "字符串";
            System.out.println(s2 == s2.intern());
            System.out.println(s1 == s1.intern());
            System.out.println(s1.intern() == s2.intern());
    }
}
