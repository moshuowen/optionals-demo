package cn.msw.Regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 限定符的使用
 *
 * @author moshu
 *
 */
public class RegExp03
{

    public static void main(String[] args) {

        String content = "1111111aaa";
        // {n} 匹配前面的字符n次 {n,} 匹配前面的字符至少n次 {n,m} 匹配前面的字符至少n次,最多m次
        Pattern compile = compile("\\d{3,}", Pattern.CASE_INSENSITIVE);

        Matcher matcher = compile.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }
}
