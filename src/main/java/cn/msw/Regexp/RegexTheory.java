package cn.msw.Regexp;

import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * @author moshu
 */
public class RegexTheory
{

    public static void main(String[] args)
    {

        String content = "1998年12月8日，第二代Java平台的企业版J2EE发布。1999年6月，Sun公司发布了第二代Java平台（简称为Java2）的3个版本：J2ME（Java2 Micro " +
                "Edition，Java2平台的微型版），应用于移动、1233无线及有限资源的环境；J2SE（Java 2 Standard Edition，Java " +
                "2平台的标准版），应用于桌面环境；J2EE1231（Java 2Enterprise Edition，Java 2平台的企业版），应用于基于Java的应用服务器。Java " +
                "2平台的发布，是Java发展过程中最重要的一个里程碑，标志着Java的应用开始普及。";

//        String regStr = "\\d{4}";
        String regStr = "(\\d\\d)(\\d\\d)";
        //第一个括号是第一个分组，第二个括号是第二个分组
        Matcher matcher = compile(regStr).matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }
    }
}
