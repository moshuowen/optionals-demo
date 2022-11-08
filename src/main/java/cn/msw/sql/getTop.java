package cn.msw.sql;

import java.util.regex.Pattern;

public class getTop
{

    public static void main(String[] args) {

        String top = sql.topPart2.getSql();

        String regex = "\\s*as?\s\"([^\"]*)\"";
        Pattern compile = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        String[] split = compile.split(top);
        for (int i = 0; i < split.length; i++) {
            System.out.println("===========");
            System.out.println(split[i]);
        }
        // 正则匹配sql别名
        String regex1 = "\\s*as?\s\"([^\"]*)\"";
    }
}
