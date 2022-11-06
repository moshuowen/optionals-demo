package cn.msw.Regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class RegExp02
{

    public static void main(String[] args) {

        String content = "moshuowen莫硕文";
        // mo|莫 匹配mo或者莫
        Pattern compile = compile("mo|莫");

        Matcher matcher = compile.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }
}
