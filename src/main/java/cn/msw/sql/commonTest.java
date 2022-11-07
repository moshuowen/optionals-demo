package cn.msw.sql;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class commonTest {

    public static void main(String[] args) {
        LinkedHashMap<String, String> alias = getAlias();
        assert alias != null;
        for(Map.Entry<String, String> entry : alias.entrySet()) {
            System.out.println("key:" + entry.getKey() + "   value:" + entry.getValue());
        }
    }


    static String getCommon() {
        String regex1 = "#condition query=\\d+#";
        String regex2 = "as\s\"([^\"]*)\"";
        String[] split = sql.mainSql.getSql().split(regex1);
        String commonSql = split[0].trim();
        String[] split1 = commonSql.split(regex2);
        String s = null;
        if (split1.length >= 1) {
            s = split1[split1.length - 1];
        }
        assert s != null;
        return s.trim();
    }

    static String getTopPartSql() {
        String regex1 = "#condition query=\\d+#";
        String regex2 = "as\s\"([^\"]*)\"";
        String[] split = sql.mainSql.getSql().split(regex1);
        String commonSql = split[0].trim();
        String replace = commonSql.replace(getCommon(), "");
        return replace;
    }

    static String removeStartString(final String regexStart,final  String sql) {
        Pattern compile = compile(regexStart, CASE_INSENSITIVE);
        String tempSql = sql;
        Matcher matcher2 = compile.matcher(sql);
        if (matcher2.lookingAt()) {
            int end = matcher2.end();
            tempSql = sql.substring(end);
        }
        return tempSql;
    }

    static LinkedHashMap<String, String> getAlias() {
        String regex = "as\s\"([^\"]*)\"";
        String topPartSql = getTopPartSql();
        //正则去除开始的select
        String regexSelect  = "select\s?";
        topPartSql = removeStartString(regexSelect, topPartSql);
        Matcher matcher = compile(regex).matcher(topPartSql);
        ArrayList<String> alias = new ArrayList<>();
        while(matcher.find()) {
            alias.add(matcher.group(1));
        }
        ArrayList<String> values = new ArrayList<>();
        String[] split = topPartSql.split(regex);
        for (int i = 0; i < split.length; i++) {
            String item = split[i];
//            if ("\n".equals(item)) {
//
//            }else{
//                split[i] = removeStartString("\n?(,)\n?", split[i]);
//                values.add(split[i]);
//            }

            boolean matches = compile("(\"?\n+\"?)").matcher(item).matches();
            if (matches) {

            } else {
                split[i] = removeStartString(",?\n", item);
                values.add(split[i]);
            }
        }
        if(values.size() == alias.size()) {
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            for (int i = 0; i < values.size(); i++) {
                map.put(alias.get(i), values.get(i));
            }
            return map;
        }
        return null;
    }


}
