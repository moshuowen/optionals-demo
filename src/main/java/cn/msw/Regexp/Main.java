package cn.msw.Regexp;

import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

public class Main {

    public static void main(String[] args) {

        String str = "1995年，互联网的蓬勃发展给了Oak机会。业界为了使死板、单调的静态网页能够“灵活”起来，急需一种软件技术来开发一种程序，这种程序可以通过网络传播并且能够跨平台运行。于是，世界各大IT企业为此纷纷投入了大量的人力、物力和财力。这个时候，Sun公司想起了那个被搁置起来很久的Oak，并且重新审视了那个用软件编写的试验平台，由于它是按照嵌入式系统硬件平台体系结构进行编写的，所以非常小，特别适用于网络上的传输系统，而Oak也是一种精简的语言，程序非常小，适合在网络上传输。Sun公司首先推出了可以嵌入网页并且可以随同网页在网络上传输的Applet（Applet是一种将小程序嵌入到网页中进行执行的技术），并将Oak更名为Java。5月23日，Sun公司在Sun world会议上正式发布Java和HotJava浏览器。IBM、Apple、DEC、Adobe、HP、Oracle、Netscape和微软等各大公司都纷纷停止了自己的相关开发项目，竞相购买了Java使用许可证，并为自己的产品开发了相应的Java平台。";
        // 匹配器按照模式对象到str匹配字符串，Pattern 模式对象
        // 匹配所有英文单词[a-zA-z]+
        //匹配所有数字[\\d]+
        //匹配所有中文[\u4e00-\u9fa5]
        // 数字和英文单词
        Matcher matcher = compile("[\\d\\w]+").matcher(str);
        while (matcher.find()) {
            // 输出匹配到的字符串
//            System.out.println(matcher.group(0));
        }

        final String sql = "select '总人数1' as \"name\",\n" +
                "(SELECT count(1) \"totlePatientNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE not in ('0', '1', '6', '7', '9')) as \"count\",\n" +
                "'1' as \"queryIndex\",\n" +
                "'-' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '一级护理' as \"name\",\n" +
                "(SELECT count(1) \"nursingLevelNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4')\n" +
                "\n" +
                "           AND a.NURSING_LEVEL_CODE = '1') as \"count\",\n" +
                "'2' as \"queryIndex\",\n" +
                "'1' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '三级护理' as \"name\",\n" +
                "(SELECT count(1) \"nursingLevelNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4')\n" +
                "           AND a.NURSING_LEVEL_CODE = '3') as \"count\",\n" +
                "'2' as \"queryIndex\",\n" +
                "'3' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '特级护理' as \"name\",\n" +
                "(SELECT count(1) \"nursingLevelNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4')\n" +
                "\n" +
                "           AND a.NURSING_LEVEL_CODE in ('4', '5', '6', '7', '8')) as \"count\",\n" +
                "'2' as \"queryIndex\",\n" +
                "'4' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '二级护理' as \"name\",\n" +
                "(SELECT count(1) \"nursingLevelNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4')\n" +
                "\n" +
                "           AND a.NURSING_LEVEL_CODE = '2') as \"count\",\n" +
                "'2' as \"queryIndex\",\n" +
                "'2' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '病重' as \"name\",\n" +
                "(SELECT count(1)\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4')\n" +
                "           and a.NOW_CONDITION_CODE = '2') as \"count\",\n" +
                "'3' as \"queryIndex\",\n" +
                "'2' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '病危' as \"name\",\n" +
                "(SELECT count(1)\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4')\n" +
                "           and a.NOW_CONDITION_CODE = '1') as \"count\",\n" +
                "'3' as \"queryIndex\",\n" +
                "'1' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '入院' as \"name\",\n" +
                "(SELECT count(1) \"inPatientNum\"\n" +
                "          FROM zoepatient.PAT_TRANSFER_RECORD a\n" +
                "         WHERE a.EVENT_TYPE = '0'\n" +
                "           AND a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           AND a.EVENT_STATUS = '1'\n" +
                "           AND trunc(a.END_TIME) = trunc(sysdate)) as \"count\",\n" +
                "'4' as \"queryIndex\",\n" +
                "'-' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '转入' as \"name\",\n" +
                "(SELECT count(*) \"transferNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL     a,\n" +
                "               zoepatient.PAT_TRANSFER_RECORD b\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           AND b.TARGET_DEPT_CODE = a.IN_DEPT_CODE\n" +
                "\n" +
                "           AND b.EVENT_NO = a.Event_No\n" +
                "           AND b.EVENT_TYPE = '1'\n" +
                "           AND b.EVENT_STATUS = '1'\n" +
                "           AND trunc(b.END_TIME) = trunc(sysdate)\n" +
                "           AND a.IN_HOSPITAL_STATUS_CODE in ('2', '3')) as \"count\",\n" +
                "'5' as \"queryIndex\",\n" +
                "'-' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '迁入' as \"name\",\n" +
                "(SELECT count(*) \"migrateNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL     a,\n" +
                "               zoepatient.PAT_TRANSFER_RECORD b\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           AND b.NURSING_DEPT_CODE = a.NURSING_DEPT_CODE\n" +
                "\n" +
                "           AND b.EVENT_NO = a.Event_No\n" +
                "           AND b.EVENT_TYPE = '3'\n" +
                "           AND b.EVENT_STATUS = '1'\n" +
                "     AND trunc(b.END_TIME) = trunc(sysdate)\n" +
                "           AND a.IN_HOSPITAL_STATUS_CODE in ('2', '4')) as \"count\",\n" +
                "'6' as \"queryIndex\",\n" +
                "'-' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '出院' as \"name\",\n" +
                "(SELECT count(1) \"outPatientNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE a.IN_DEPT_CODE in\n" +
                "               (select d.DEPT_CODE\n" +
                "                  from zoedict.DIC_DEPT_DICT d\n" +
                "                 where 1 = 1\n" +
                "                   and d.PARENT_DEPT_CODE = #nursingDeptCode#)\n" +
                "           AND trunc(a.DISCHARGE_TIME) = trunc(sysdate)\n" +
                "\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE = '6') as \"count\",\n" +
                "'7' as \"queryIndex\",\n" +
                "'-' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '手术排台' as \"name\",\n" +
                "(SELECT count(1) \"num\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE in ('2', '3', '4')\n" +
                "           and exists (select 1\n" +
                "                  from zoepres.PRES_OPERATION_RECORD t\n" +
                "                 where t.event_no = a.event_no\n" +
                "                   and t.OPERATE_STATUS_CODE = '2')) as \"count\",\n" +
                "'8' as \"queryIndex\",\n" +
                "'-' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '今日手术' as \"name\",\n" +
                "(SELECT count(1) \"num\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE in ('2', '3', '4')\n" +
                "           and exists\n" +
                "         (select 1\n" +
                "                  from zoepres.pres_operation_record t\n" +
                "                 where 1 = 1\n" +
                "                   and t.event_no = a.event_no\n" +
                "                   and trunc(t.operate_time) = trunc(sysdate))) as \"count\",\n" +
                "'9' as \"queryIndex\",\n" +
                "'-' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '挂床' as \"name\",\n" +
                "(SELECT count(1) \"hangBedNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE a.IN_DEPT_CODE in\n" +
                "               (select d.DEPT_CODE\n" +
                "                  from zoedict.DIC_DEPT_DICT d\n" +
                "                 where 1 = 1\n" +
                "                   and d.PARENT_DEPT_CODE = #nursingDeptCode#)\n" +
                "           and a.IN_HOSPITAL_STATUS_CODE = '5') as \"count\",\n" +
                "'11' as \"queryIndex\",\n" +
                "'-' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '24小时留陪人' as \"name\",\n" +
                "(SELECT count(1) \"totlePatientNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           and exists (select distinct b.event_no\n" +
                "                  from zoepres.PRES_INP_PRES_RECORD b\n" +
                "                 where b.item_name = '嘱24小时留陪人'\n" +
                "                   and b.event_no = a.event_no\n" +
                "                   and b.PRES_STATUS_CODE = '5')) as \"count\",\n" +
                "'19' as \"queryIndex\",\n" +
                "'-' as \"queryParam\"\n" +
                "from dual\n" +
                "union all\n" +
                "select '在科' as \"name\",\n" +
                "(SELECT count(*) \"migrateNum\"\n" +
                "          FROM zoepatient.PAT_IN_HOSPITAL     a\n" +
                "         WHERE 1 = 1\n" +
                "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
                "           AND a.IN_HOSPITAL_STATUS_CODE in ('2')) as \"count\",\n" +
                "'20' as \"queryIndex\",\n" +
                "'-' as \"queryParam\"\n" +
                "from dual\n";
        String[] union_alls = sql.split("union all");
        String sql1 = union_alls[0];
        System.out.println(union_alls.length);
//        Matcher matcher1 = compile("select '(\\S*)' as \"name\"").matcher(sql1);
//        Matcher matcher2 = compile("'(\\S*)' as \"queryIndex\"").matcher(sql1);
//        Matcher matcher2 = compile("\\(+(\\S*)+\\) as \"count\"").matcher(sql1);
        for (int i = 0; i < union_alls.length; i++) {
            Matcher matcher1 = compile("'(\\S*)' as \"name\"").matcher(union_alls[i]);
//            \\S 匹配任何非空白字符。等价于 [^ \\f\\n\\r\\t\\v]。
            Matcher matcher2 = compile("\\(?(\\S*)\\)+ as \"count\"").matcher(union_alls[i]);
            Matcher matcher3 = compile("'(\\S*)' as \"queryIndex\"").matcher(union_alls[i]);
            Matcher matcher4 = compile("'(\\S*)' as \"queryParam\"").matcher(union_alls[i]);
            while (matcher1.find() && matcher2.find() && matcher3.find()&& matcher4.find()) {
                System.out.print(i + " "+matcher1.group(1)+"\t");
                System.out.print("count:" +matcher2.group(1)+"\t");
                System.out.print("queryIndex:" +matcher3.group(1)+"\t");
                System.out.print("queryParam:" +matcher4.group(1));
                System.out.println();
            }
        }

    }
}
