package cn.msw.Regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class RegExp01
{

    public static void main(String[] args) {

        String content = "a1bcA1B2C";
        // [^a-zA-Z] 匹配除了a-z和A-Z之外的所有字符
        Pattern compile = compile("[^a-zA-Z]");
//        Pattern compile = compile("(?i)abc");
        // (?i)abc  (?i)表示忽略大小写, a((?i)b)c 表示abc aBc都可以匹配,CASE_INSENSITIVE 也可以表示忽略大小写
//        Pattern compile = compile("a((?i)b)c", CASE_INSENSITIVE);
//        Pattern compile = compile("a(?i)bc");
        Matcher matcher = compile.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }
}
