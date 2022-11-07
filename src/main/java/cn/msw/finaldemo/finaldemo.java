package cn.msw.finaldemo;

public class finaldemo {

    public static void main(String[] args) {

    }


    protected void handleLog(final String a, String b) {
        // 无法将值赋给 final 变量 'a'
        //  a  = "a";
        b = "b";
    }
}

