package cn.msw.jsqlparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.StringReader;
import java.util.List;

public class test01 {

    public static void main(String[] args) throws JSQLParserException {

        String SQL002 = "SELECT t1.a , t1.b  FROM tab1 AS t1 JOIN tab2 t2 ON t1.user_id  = t2.user_id";   // 多表SQL

        // 1.解析表名
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Statement statement = parserManager.parse(new StringReader(SQL002)); // 解析SQL为Statement对象
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder(); // 创建表名发现者对象
        List<String> tableNameList = tablesNamesFinder.getTableList(statement); // 获取到表名列表
        if (tableNameList != null && tableNameList.size() > 0) {
            tableNameList.forEach(System.err::println); // 循环打印解析到的表名 tab1 tab2
        }
        // 2.解析查询元素=》 列，函数等
        Select select = (Select) CCJSqlParserUtil.parse(SQL002);
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        selectItems.forEach(System.err::println); // t1.a , t1.b

        // 3.解析WHERE条件
        String SQL_WHERE = "SELECT *  FROM tableName WHERE ID = 8";
        PlainSelect plainSelectWhere = (PlainSelect) ((Select) CCJSqlParserUtil.parse(SQL_WHERE)).getSelectBody();
        EqualsTo equalsTo = (EqualsTo) plainSelectWhere.getWhere();
        Expression leftExpression = equalsTo.getLeftExpression();
        Expression rightExpression = equalsTo.getRightExpression();
        System.err.println(leftExpression); // ID
        System.err.println(rightExpression); // 8

        // 4.解析Join
        List<Join> joins = plainSelect.getJoins();
        joins.forEach(e -> {
            Expression onExpression = e.getOnExpression();
            System.err.println(onExpression); // 获取ON 表达式 t1.user_id = t2.user_id
        });

        // 5.解析IN
        String SQL_IN = "SELECT *  FROM tableName WHERE ID IN (8,9,10)";
        PlainSelect plainSelectIn = (PlainSelect) ((Select) CCJSqlParserUtil.parse(SQL_IN)).getSelectBody();
        InExpression inExpression = (InExpression) plainSelectIn.getWhere();
        ItemsList rightItemsList = inExpression.getRightItemsList();
        System.err.println(rightItemsList); // (8, 9, 10)


    }
}
