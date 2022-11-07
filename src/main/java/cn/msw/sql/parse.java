package cn.msw.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parse {

    public static void main(String[] args) {
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
        ;
        String[] split = sql.split("union all");
        // 值
        String v1 = "'([^']*)'\sas";
        // 别名
        String v2 = "as\s\"([^\"]*)\"";

        for (String s : split) {

            Pattern pattern = Pattern.compile(v1);
            Matcher matcher = pattern.matcher(split[0]);
            System.out.println(s);
            List<String> list = new ArrayList<>();
            while (matcher.find()) {
                String name = matcher.group(0).replaceAll(v1, "$1");
                list.add(name);
            }
            HashMap<Object, Object> map = new HashMap<>();
            for (String as : s.split("as")) {
                // 获取到count里面的语法内容
                if (as.contains("count")) {
                    if (as.toLowerCase().contains("where")) {
                        map.put("count", as.substring(as.indexOf("("), as.lastIndexOf(")") + 1));
                    }

                }
            }
            map.put("name", list.get(0));
            map.put("queryIndex", list.get(1));
            map.put("queryParam", list.get(2));

            System.out.println(map.toString());
        }
    }
}
