package cn.msw.jsqlparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

public class main {
    public static void main(String[] args) throws JSQLParserException {

        //1、获取原始sql输入
        String mainSql = sql.topPart.getSql();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Select select = (Select) CCJSqlParserUtil.parse(mainSql);
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        List<String> collect = selectItems.stream().map(Object::toString).toList();
        Pattern compile = compile("as?\s+\"(?<alias>[^\"]*)\"", CASE_INSENSITIVE);
        for (String selectItem : collect)
        {
            Matcher matcher = compile.matcher(selectItem);
            if (matcher.find())
            {
                map.put(matcher.group("alias"), selectItem);
            }
        }

        System.out.println(map);
    }
}
