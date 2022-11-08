package cn.msw.jsqlparser;

public enum sql {


    mainSql("select\n" +
            "inHos.patient_name as \"patientName\",\n" +
            "bed.bed_code as \"bedNo\",\n" +
            "bed.SORT_NO as \"sortNo\",\n" +
            "(select leDict.nursing_level_name\n" +
            "          from zoedict.DIC_NURSING_LEVEL_DICT leDict\n" +
            "         where leDict.Nursing_Level_Code = inHos.Nursing_Level_Code) as \"nursingLevelName\",\n" +
            "inHos.In_Dept_Code  as \"inDeptCode\",\n" +
            "inHos.NOW_CONDITION_CODE as \"nowConditionCode\",\n" +
            "(select VALUE_NAME\n" +
            "          from zoedict.DIC_BASIC_DICT\n" +
            "         where dict_name = 'SEX'\n" +
            "           and VALUE_CODE = inHos.patient_sex) as \"sexName\",\n" +
            "inHos.patient_sex as \"sexCode\",\n" +
            "inHos.Event_No as \"eventNo\",\n" +
            "bed.BED_STATUS_CODE as \"bedStatusCode\",\n" +
            "(select DT.VALUE_NAME\n" +
            "          from zoedict.Dic_Basic_Dict DT\n" +
            "         where DT.VALUE_CODE = bed.BED_STATUS_CODE\n" +
            "           and DT.VALID_FLAG = 1\n" +
            "           and DT.DICT_NAME = 'BED_STATUS_DICT') \"bedStatusName\",\n" +
            "inHos.patient_id \"patientId\",\n" +
            "inHos.AGE as \"age\",\n" +
            " (select pat.bed_no\n" +
            "          from zoepatient.PAT_IN_HOSPITAL    pat,\n" +
            "               zoepatient.pat_bed_use_record record\n" +
            "         where bed.bed_code = record.bed_no\n" +
            "           and bed.nursing_dept_code = record.ward\n" +
            "           and record.out_bed_date is null\n" +
            "           and pat.IN_HOSPITAL_STATUS_CODE in ('2', '3', '4', '5')\n" +
            "           and pat.event_no = record.event_no\n" +
            "           and rownum = 1) \"mainBedNo\",\n" +
            "(select pat.room_no\n" +
            "          from zoepatient.PAT_IN_HOSPITAL    pat,\n" +
            "               zoepatient.pat_bed_use_record record\n" +
            "         where bed.bed_code = record.bed_no\n" +
            "           and bed.nursing_dept_code = record.ward\n" +
            "           and record.out_bed_date is null\n" +
            "           and pat.IN_HOSPITAL_STATUS_CODE in ('2', '3', '4', '5')\n" +
            "           and pat.event_no = record.event_no\n" +
            "           and rownum = 1) \"mainRoomNo\",\n" +
            "(select pi.item_code\n" +
            "          from ZOEPRES.PRES_INP_PRES_RECORD pi\n" +
            "         where pi.event_no = inHos.Event_No\n" +
            "           and pi.PRES_STATUS_CODE = '6'\n" +
            "           and (pi.item_code = '200009' or pi.item_code = '6859')\n" +
            "           and rownum = 1) \"itemCode\",\n" +
            "decode(trunc(inHos.admission_dept_time),trunc(sysdate),'今日入院') as \"inFlag\",\n" +
            "'' as \"void\",\n" +
            "(SELECT\n" +
            "            case when\n" +
            "           TRUNC(MAX(A.PRE_DISCHARGED_TIME)) > trunc(sysdate)\n" +
            "            then '计划出院'\n" +
            "            when TRUNC(MAX(A.PRE_DISCHARGED_TIME))  = trunc(sysdate)\n" +
            "            then '今日出院'\n" +
            "            when TRUNC(MAX(A.PRE_DISCHARGED_TIME))  < trunc(sysdate)\n" +
            "            then '待办理出院'\n" +
            "            else '' end\n" +
            "          FROM ZOEPATIENT.PAT_PRE_DISCHARGED_REGISTER A\n" +
            "         WHERE a.STATUS='2'\n" +
            "           and a.EVENT_NO = inHos.EVENT_NO\n" +
            "           and inHos.IN_HOSPITAL_STATUS_CODE not in ('6')) as \"dischargeFlag\",\n" +
            "'0' \"babyFlag\",\n" +
            "(select t.STAFF_NAME\n" +
            "          from zoecomm.COM_STAFF_BASIC_INFO t\n" +
            "         where t.STAFF_NO= inHos.DIRECTOR_DOCTOR_CODE )\n" +
            "       as \"主治医生\",\n" +
            "to_date(sysdate) - to_date(inHos.ADMISSION_DEPT_TIME)+1||'天' as \"住院时长\",\n" +
            "( select decode(t.poverty_relief_flag,'1','贫困','')\n" +
            "  from zoeinsur.ins_patient_insur_info t\n" +
            " where t.patient_id = inHos.PATIENT_ID) as \"贫困\",\n" +
            "(select (select d.insur_name\n" +
            "          from zoeinsur.ins_base_dict d\n" +
            "         where d.insur_catalog_code = '4'\n" +
            "           and d.insur_dict_name_code = 'MEDICAL_RESCUE_TYPE'\n" +
            "           and t.medical_rescue_type = d.insur_code) medical_rescue_type\n" +
            "  from zoeinsur.ins_patient_insur_info t\n" +
            " where t.patient_id = inHos.PATIENT_ID) as \"救助人员类型\",\n" +
            "(select decode(ebi.nursing_item_value, 1, '跌倒/坠床高危', '') \"nursingItemValue\"\n" +
            "          from (select 1 nursing_item_value\n" +
            "                  from zoeemr.emr_nursing_table_master a,\n" +
            "                       zoeemr.emr_nursing_table_detail b,\n" +
            "                       zoeemr.emr_basic_info           c\n" +
            "                 where a.nursing_row_id = b.nursing_row_id\n" +
            "                   and c.emr_id = a.emr_id\n" +
            "                   and c.event_no = inHos.event_no\n" +
            "                   and c.patient_id = inHos.patient_id\n" +
            "                   and c.template_code in (420, 1099)\n" +
            "                   and b.nursing_item_id = 'total_point'\n" +
            "                   and to_number(b.nursing_item_value) >= 15\n" +
            "                   and rownum = 1\n" +
            "                 order by a.CREATE_TIME desc) ebi\n" +
            "         where rownum = 1) as \"nursingItemValue\",\n" +
            "inHos.Inp_Case_No as \"caseno\"\n" +
            "from zoedict.dic_bed_dict          bed,\n" +
            "       zoepatient.PAT_IN_HOSPITAL    inHos,\n" +
            "       zoepatient.pat_bed_use_record useRecord\n" +
            " where inHos.Bed_No(+) = bed.bed_code\n" +
            "   and inHos.Nursing_Dept_Code(+) = bed.Nursing_Dept_Code\n" +
            "   and useRecord.bed_no(+) = inHos.bed_no\n" +
            "   and useRecord.room_no(+) = inHos.Room_No\n" +
            "   and useRecord.in_dept(+) = inHos.Nursing_Dept_Code\n" +
            "   and useRecord.event_no(+) = inHos.event_no\n" +
            "   and useRecord.in_dept(+) = inHos.in_dept_code\n" +
            "   and useRecord.Out_Bed_Date is null\n" +
            "   and bed.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
            "   and bed.VALID_FLAG = '1'\n" +
            "   and inHos.IN_HOSPITAL_STATUS_CODE(+) not IN ('0', '1','7','9') \n" +
            "#condition query=1#\n" +
            "and inHos.IN_HOSPITAL_STATUS_CODE(+) not IN ('6')\n" +
            "#condition query=2#\n" +
            " and inHos.NURSING_LEVEL_CODE=#queryParam#\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')\n" +
            "#condition query=3#\n" +
            "and inHos.NOW_CONDITION_CODE=#queryParam#\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')\n" +
            "#condition query=4#\n" +
            "and trunc(inHos.admission_dept_time)=trunc(sysdate)\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE not in ('0', '1', '7', '9')\n" +
            "#condition query=5#\n" +
            "and exists(select 1 from zoepatient.PAT_TRANSFER_RECORD c\n" +
            "                where c.TARGET_DEPT_CODE=inHos.IN_DEPT_CODE\n" +
            "                And c.EVENT_NO=inHos.Event_No\n" +
            "                And c.EVENT_TYPE='1'\n" +
            "                And c.EVENT_STATUS='1'\n" +
            "                And trunc(c.END_TIME)=trunc(sysdate) )\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')\n" +
            "#condition query=6#\n" +
            "and exists(select 1 from zoepatient.PAT_TRANSFER_RECORD c\n" +
            "                where c.TARGET_DEPT_CODE=inHos.NURSING_DEPT_CODE\n" +
            "                And c.EVENT_NO=inHos.Event_No\n" +
            "                And c.EVENT_TYPE='3'\n" +
            "                And c.EVENT_STATUS='1'\n" +
            "               And trunc(c.END_TIME)=trunc(sysdate)  )\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')        \n" +
            "#condition query=7#\n" +
            "and trunc(inHos.DISCHARGE_TIME)=trunc(sysdate)\n" +
            "    and inHos.In_Hospital_Status_Code ='6'\n" +
            "#condition query=8#\n" +
            "and exists (select 1\n" +
            "                from zoepres.PRES_OPERATION_RECORD t\n" +
            "                where t.OPERATE_STATUS_CODE = '2'\n" +
            "                and t.event_no = inHos.event_no)\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')\n" +
            "#condition query=9#\n" +
            " and exists (select 1\n" +
            "                from zoepres.pres_operation_record t\n" +
            "                where 1 = 1\n" +
            "                and t.event_no = inHos.event_no\n" +
            "                and trunc(t.operate_time) = trunc(sysdate))\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')\n" +
            "#condition query=11#\n" +
            "and trunc(inHos.DISCHARGE_TIME)=trunc(sysdate)\n" +
            "    and inHos.In_Hospital_Status_Code ='5' \n" +
            "#condition query=19#\n" +
            "and inHos.Event_No in (select distinct b.event_no\n" +
            "   from zoepres.PRES_INP_PRES_RECORD b\n" +
            "   where b.item_name = '嘱24小时留陪人'\n" +
            "   and b.event_no = inHos.event_no\n" +
            "   and b.PRES_STATUS_CODE = '5')\n" +
            "\n" +
            "#condition query=20#\n" +
            "and inHos.IN_HOSPITAL_STATUS_CODE in('2')\n"),


    detail("select '总人数' as \"name\",\n" +
            "(SELECT count(1) \"totlePatientNum\"\n" +
            "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
            "         WHERE 1 = 1\n" +
            "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
            "           and a.IN_HOSPITAL_STATUS_CODE not in ('0', '1', '6', '7', '9')) as \"count\",\n" +
            "'1' as \"queryIndex\",\n" +
            "'-' as \"queryParam\"\n" +
            "from dual\n" +
            "union all\n" +
            "select '二级护理' as \"name\",\n" +
            "(SELECT count(1) \"nursingLevelNum\"\n" +
            "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
            "         WHERE 1 = 1\n" +
            "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
            "           and a.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4')\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t  \n" +
            "           AND a.NURSING_LEVEL_CODE = '2') as \"count\",\n" +
            "'2' as \"queryIndex\",\n" +
            "'2' as \"queryParam\"\n" +
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
            "select '一级护理' as \"name\",\n" +
            "(SELECT count(1) \"nursingLevelNum\"\n" +
            "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
            "         WHERE 1 = 1\n" +
            "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
            "           and a.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4')\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t  \n" +
            "           AND a.NURSING_LEVEL_CODE = '1') as \"count\",\n" +
            "'2' as \"queryIndex\",\n" +
            "'1' as \"queryParam\"\n" +
            "from dual\n" +
            "union all\n" +
            "select '特级护理' as \"name\",\n" +
            "(SELECT count(1) \"nursingLevelNum\"\n" +
            "          FROM zoepatient.PAT_IN_HOSPITAL a\n" +
            "         WHERE 1 = 1\n" +
            "           and a.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
            "           and a.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4')\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t  \n" +
            "           AND a.NURSING_LEVEL_CODE in ('4', '5', '6', '7', '8')) as \"count\",\n" +
            "'2' as \"queryIndex\",\n" +
            "'4' as \"queryParam\"\n" +
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
            "\t\t\t\t\t\t\t\t\t\t\t\t\t  \n" +
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
            "\t\t\t\t\t\t\t\t\t\t\t\t\t  \n" +
            "           AND b.EVENT_NO = a.Event_No\n" +
            "           AND b.EVENT_TYPE = '3'\n" +
            "           AND b.EVENT_STATUS = '1'\n" +
            "     AND trunc(b.END_TIME) = trunc(sysdate)    \n" +
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
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  \n" +
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
            "from dual\n"),

    baby("select\n" +
            "bed.SORT_NO as \"sortNo\",\n" +
            "(select leDict.nursing_level_name\n" +
            "          from zoedict.DIC_NURSING_LEVEL_DICT leDict\n" +
            "         where leDict.Nursing_Level_Code = inHos.Nursing_Level_Code) as \"nursingLevelName\",\n" +
            "inHos.In_Dept_Code \"inDeptCode\",\n" +
            "inHos.NOW_CONDITION_CODE as \"nowConditionCode\",\n" +
            "inHos.Event_No as \"eventNo\",\n" +
            "bed.BED_STATUS_CODE as \"bedStatusCode\",\n" +
            "(select DT.VALUE_NAME\n" +
            "          from zoedict.Dic_Basic_Dict DT\n" +
            "         where DT.VALUE_CODE = bed.BED_STATUS_CODE\n" +
            "           and DT.VALID_FLAG = 1\n" +
            "           and DT.DICT_NAME = 'BED_STATUS_DICT') \"bedStatusName\",\n" +
            "inHos.patient_id \"patientId\",\n" +
            " (select pat.bed_no\n" +
            "          from zoepatient.PAT_IN_HOSPITAL    pat,\n" +
            "               zoepatient.pat_bed_use_record record\n" +
            "         where bed.bed_code = record.bed_no\n" +
            "           and bed.nursing_dept_code = record.ward\n" +
            "           and record.out_bed_date is null\n" +
            "           and pat.IN_HOSPITAL_STATUS_CODE in ('2', '3', '4', '5')\n" +
            "           and pat.event_no = record.event_no\n" +
            "           and rownum = 1) \"mainBedNo\",\n" +
            "(select pat.room_no\n" +
            "          from zoepatient.PAT_IN_HOSPITAL    pat,\n" +
            "               zoepatient.pat_bed_use_record record\n" +
            "         where bed.bed_code = record.bed_no\n" +
            "           and bed.nursing_dept_code = record.ward\n" +
            "           and record.out_bed_date is null\n" +
            "           and pat.IN_HOSPITAL_STATUS_CODE in ('2', '3', '4', '5')\n" +
            "           and pat.event_no = record.event_no\n" +
            "           and rownum = 1) \"mainRoomNo\",\n" +
            "(select pi.item_code\n" +
            "          from ZOEPRES.PRES_INP_PRES_RECORD pi\n" +
            "         where pi.event_no = inHos.Event_No\n" +
            "           and pi.PRES_STATUS_CODE = '6'\n" +
            "           and (pi.item_code = '200009' or pi.item_code = '6859')\n" +
            "           and rownum = 1) \"itemCode\",\n" +
            "decode(trunc(inHos.admission_dept_time),trunc(sysdate),'今日入院') as \"inFlag\",\n" +
            "'' as \"void\",\n" +
            "(select t.STAFF_NAME\n" +
            "          from zoecomm.COM_STAFF_BASIC_INFO t\n" +
            "         where t.STAFF_NO= inHos.DIRECTOR_DOCTOR_CODE )\n" +
            "       as \"主治医生\",\n" +
            "to_date(sysdate) - to_date(inHos.ADMISSION_DEPT_TIME)+1||'天' as \"住院时长\",\n" +
            "( select decode(t.poverty_relief_flag,'1','贫困','')\n" +
            "  from zoeinsur.ins_patient_insur_info t\n" +
            " where t.patient_id = inHos.PATIENT_ID) as \"贫困\",\n" +
            "(select (select d.insur_name\n" +
            "          from zoeinsur.ins_base_dict d\n" +
            "         where d.insur_catalog_code = '4'\n" +
            "           and d.insur_dict_name_code = 'MEDICAL_RESCUE_TYPE'\n" +
            "           and t.medical_rescue_type = d.insur_code) medical_rescue_type\n" +
            "  from zoeinsur.ins_patient_insur_info t\n" +
            " where t.patient_id = inHos.PATIENT_ID) as \"救助人员类型\",\n" +
            "(select decode(ebi.nursing_item_value, 1, '跌倒/坠床高危', '') \"nursingItemValue\"\n" +
            "          from (select 1 nursing_item_value\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "                  from zoeemr.emr_nursing_table_master a,\t\t\t \n" +
            "                       zoeemr.emr_nursing_table_detail b,\n" +
            "                       zoeemr.emr_basic_info           c\t\t\t\t\t\t\t\t\n" +
            "                 where a.nursing_row_id = b.nursing_row_id\n" +
            "                   and c.emr_id = a.emr_id\n" +
            "                   and c.event_no = inHos.event_no\t\t\t\t\t\t\t  \n" +
            "                   and c.patient_id = inHos.patient_id\n" +
            "                   and c.template_code in (420, 1099)\n" +
            "                   and b.nursing_item_id = 'total_point'\n" +
            "                   and to_number(b.nursing_item_value) >= 15\n" +
            "                   and rownum = 1\n" +
            "                 order by a.CREATE_TIME desc) ebi\n" +
            "         where rownum = 1) as \"nursingItemValue\",\n" +
            "inHos.Inp_Case_No as \"caseno\",\n" +
            "baby.BABY_NAME as \"patientName\",\n" +
            "(select p.BED_NO\n" +
            "           from zoepatient.PAT_IN_HOSPITAL p\n" +
            "          where p.EVENT_NO = baby.EVENT_NO) || 'B' || baby.BABY_SEQ as \"bedNo\",\n" +
            "'1' as \"babyFlag\",\n" +
            "(select VALUE_NAME\n" +
            "           from zoedict.DIC_BASIC_DICT\n" +
            "          where dict_name = 'SEX'\n" +
            "            and VALUE_CODE = baby.BABY_SEX) \"sexName\",\n" +
            "baby.BABY_SEX as \"sexCode\",\n" +
            "baby.GESTATIONAL_AGE as \"age\",\n" +
            "(select '计划出院'\n" +
            "          from  ZOEPRES.PRES_INP_PRES_RECORD pi\n" +
            "         where pi.event_no=inHos.Event_No\n" +
            "        and pi.PRES_STATUS_CODE = '6'\n" +
            "        and (pi.item_code='200009' or pi.item_code='6859')\t\t\t\t\t  \n" +
            "           and rownum = 1)  as \"dischargeFlag\"\n" +
            "from zoedict.dic_bed_dict           bed,\n" +
            "        zoepatient.PAT_BABY_RECORD        baby,\n" +
            "        zoepatient.PAT_IN_HOSPITAL        inHos,\n" +
            "        zoedict.DIC_NURSING_LEVEL_DICT leDict,\n" +
            "        zoepatient.pat_bed_use_record     useRecord\n" +
            "  where inHos.Bed_No(+) = bed.bed_code\n" +
            "    and baby.EVENT_NO = inHos.EVENT_NO\n" +
            "    and baby.NURSE_DEPT_CODE = inHos.WARD\n" +
            "    and inHos.Nursing_Dept_Code(+) = bed.Nursing_Dept_Code\n" +
            "    and leDict.nursing_level_code(+) = inHos.Nursing_Level_Code\n" +
            "    and useRecord.bed_no(+) = inHos.bed_no\n" +
            "    and useRecord.room_no(+) = inHos.Room_No\n" +
            "    and useRecord.in_dept(+) = inHos.Nursing_Dept_Code\n" +
            "    and useRecord.event_no(+) = inHos.event_no\n" +
            "    and useRecord.in_dept(+) = inHos.in_dept_code\n" +
            "    and useRecord.Out_Bed_Date is null\n" +
            "  and bed.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
            "   and bed.VALID_FLAG = '1'\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE(+) not IN ('0', '1','7','9')\n" +
            "#condition query=1#\n" +
            "and inHos.IN_HOSPITAL_STATUS_CODE(+) not IN ('6')\n" +
            "#condition query=2#\n" +
            " and inHos.NURSING_LEVEL_CODE=#queryParam#\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')\n" +
            "#condition query=3#\n" +
            "and inHos.NOW_CONDITION_CODE=#queryParam#\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')\n" +
            "#condition query=4#\n" +
            "and trunc(inHos.admission_dept_time)=trunc(sysdate)\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE not in ('0', '1', '7', '9')\n" +
            "#condition query=5#\n" +
            "and exists(select 1 from zoepatient.PAT_TRANSFER_RECORD c\n" +
            "                where c.TARGET_DEPT_CODE=inHos.IN_DEPT_CODE\n" +
            "                And c.EVENT_NO=inHos.Event_No\n" +
            "                And c.EVENT_TYPE='1'\n" +
            "                And c.EVENT_STATUS='1'\n" +
            "                And trunc(c.END_TIME)=trunc(sysdate) )\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')\n" +
            "#condition query=6#\n" +
            "and exists(select 1 from zoepatient.PAT_TRANSFER_RECORD c\n" +
            "                where c.TARGET_DEPT_CODE=inHos.NURSING_DEPT_CODE\n" +
            "                And c.EVENT_NO=inHos.Event_No\n" +
            "                And c.EVENT_TYPE='3'\n" +
            "                And c.EVENT_STATUS='1'\n" +
            "               And trunc(c.END_TIME)=trunc(sysdate)  )\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')        \n" +
            "#condition query=7#\n" +
            "and trunc(inHos.DISCHARGE_TIME)=trunc(sysdate)\n" +
            "    and inHos.In_Hospital_Status_Code ='6'\n" +
            "#condition query=8#\n" +
            "and exists (select 1\n" +
            "                from zoepres.PRES_OPERATION_RECORD t\n" +
            "                where t.OPERATE_STATUS_CODE = '2'\n" +
            "                and t.event_no = inHos.event_no)\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')\n" +
            "#condition query=9#\n" +
            " and exists (select 1\n" +
            "                from zoepres.pres_operation_record t\n" +
            "                where 1 = 1\n" +
            "                and t.event_no = inHos.event_no\n" +
            "                and trunc(t.operate_time) = trunc(sysdate))\n" +
            "    and inHos.IN_HOSPITAL_STATUS_CODE IN ('2', '3', '4', '5')\n" +
            "#condition query=10#\n" +
            "and (1=1)\n" +
            "#condition query=11#\n" +
            "and trunc(inHos.DISCHARGE_TIME)=trunc(sysdate)\n" +
            "    and inHos.In_Hospital_Status_Code ='5' \n" +
            "#condition query=19#\n" +
            "and inHos.Event_No in (select distinct b.event_no\n" +
            "   from zoepres.PRES_INP_PRES_RECORD b\n" +
            "   where b.item_name = '嘱24小时留陪人'\n" +
            "   and b.event_no = inHos.event_no\n" +
            "   and b.PRES_STATUS_CODE = '5')\n" +
            "\n" +
            "#condition query=20#\n" +
            "and inHos.IN_HOSPITAL_STATUS_CODE in('2')\n"),


    topPart("select\n" +
            "inHos.patient_name as \"patientName\",\n" +
            "bed.bed_code as \"bedNo\",\n" +
            "bed.SORT_NO as \"sortNo\",\n" +
            "(select leDict.nursing_level_name\n" +
            "          from zoedict.DIC_NURSING_LEVEL_DICT leDict\n" +
            "         where leDict.Nursing_Level_Code = inHos.Nursing_Level_Code) as \"nursingLevelName\",\n" +
            "inHos.In_Dept_Code \"inDeptCode\",\n" +
            "inHos.NOW_CONDITION_CODE as \"nowConditionCode\",\n" +
            "(select VALUE_NAME\n" +
            "          from zoedict.DIC_BASIC_DICT\n" +
            "         where dict_name = 'SEX'\n" +
            "           and VALUE_CODE = inHos.patient_sex) as \"sexName\",\n" +
            "inHos.patient_sex as \"sexCode\",\n" +
            "inHos.Event_No as \"eventNo\",\n" +
            "bed.BED_STATUS_CODE as \"bedStatusCode\",\n" +
            "(select DT.VALUE_NAME\n" +
            "          from zoedict.Dic_Basic_Dict DT\n" +
            "         where DT.VALUE_CODE = bed.BED_STATUS_CODE\n" +
            "           and DT.VALID_FLAG = 1\n" +
            "           and DT.DICT_NAME = 'BED_STATUS_DICT') \"bedStatusName\",\n" +
            "inHos.patient_id \"patientId\",\n" +
            "inHos.AGE as \"age\",\n" +
            " (select pat.bed_no\n" +
            "          from zoepatient.PAT_IN_HOSPITAL    pat,\n" +
            "               zoepatient.pat_bed_use_record record\n" +
            "         where bed.bed_code = record.bed_no\n" +
            "           and bed.nursing_dept_code = record.ward\n" +
            "           and record.out_bed_date is null\n" +
            "           and pat.IN_HOSPITAL_STATUS_CODE in ('2', '3', '4', '5')\n" +
            "           and pat.event_no = record.event_no\n" +
            "           and rownum = 1) \"mainBedNo\",\n" +
            "(select pat.room_no\n" +
            "          from zoepatient.PAT_IN_HOSPITAL    pat,\n" +
            "               zoepatient.pat_bed_use_record record\n" +
            "         where bed.bed_code = record.bed_no\n" +
            "           and bed.nursing_dept_code = record.ward\n" +
            "           and record.out_bed_date is null\n" +
            "           and pat.IN_HOSPITAL_STATUS_CODE in ('2', '3', '4', '5')\n" +
            "           and pat.event_no = record.event_no\n" +
            "           and rownum = 1) \"mainRoomNo\",\n" +
            "(select pi.item_code\n" +
            "          from ZOEPRES.PRES_INP_PRES_RECORD pi\n" +
            "         where pi.event_no = inHos.Event_No\n" +
            "           and pi.PRES_STATUS_CODE = '6'\n" +
            "           and (pi.item_code = '200009' or pi.item_code = '6859')\n" +
            "           and rownum = 1) \"itemCode\",\n" +
            "decode(trunc(inHos.admission_dept_time),trunc(sysdate),'今日入院') as \"inFlag\",\n" +
            "'' as \"void\",\n" +
            "(SELECT\n" +
            "            case when\n" +
            "           TRUNC(MAX(A.PRE_DISCHARGED_TIME)) > trunc(sysdate)\n" +
            "            then '计划出院'\n" +
            "            when TRUNC(MAX(A.PRE_DISCHARGED_TIME))  = trunc(sysdate)\n" +
            "            then '今日出院'\n" +
            "            when TRUNC(MAX(A.PRE_DISCHARGED_TIME))  < trunc(sysdate)\n" +
            "            then '待办理出院'\n" +
            "            else '' end\n" +
            "          FROM ZOEPATIENT.PAT_PRE_DISCHARGED_REGISTER A\n" +
            "         WHERE a.STATUS='2'\n" +
            "           and a.EVENT_NO = inHos.EVENT_NO\n" +
            "           and inHos.IN_HOSPITAL_STATUS_CODE not in ('6')) as \"dischargeFlag\",\n" +
            "'0' \"babyFlag\",\n" +
            "(select t.STAFF_NAME\n" +
            "          from zoecomm.COM_STAFF_BASIC_INFO t\n" +
            "         where t.STAFF_NO= inHos.DIRECTOR_DOCTOR_CODE )\n" +
            "       as \"主治医生\",\n" +
            "to_date(sysdate) - to_date(inHos.ADMISSION_DEPT_TIME)+1||'天' as \"住院时长\",\n" +
            "( select decode(t.poverty_relief_flag,'1','贫困','')\n" +
            "  from zoeinsur.ins_patient_insur_info t\n" +
            " where t.patient_id = inHos.PATIENT_ID) as \"贫困\",\n" +
            "(select (select d.insur_name\n" +
            "          from zoeinsur.ins_base_dict d\n" +
            "         where d.insur_catalog_code = '4'\n" +
            "           and d.insur_dict_name_code = 'MEDICAL_RESCUE_TYPE'\n" +
            "           and t.medical_rescue_type = d.insur_code) medical_rescue_type\n" +
            "  from zoeinsur.ins_patient_insur_info t\n" +
            " where t.patient_id = inHos.PATIENT_ID) as \"救助人员类型\",\n" +
            "(select decode(ebi.nursing_item_value, 1, '跌倒/坠床高危', '') \"nursingItemValue\"\n" +
            "          from (select 1 nursing_item_value\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "                  from zoeemr.emr_nursing_table_master a,\t\t\t \n" +
            "                       zoeemr.emr_nursing_table_detail b,\n" +
            "                       zoeemr.emr_basic_info           c\t\t\t\t\t\t\t\t\n" +
            "                 where a.nursing_row_id = b.nursing_row_id\n" +
            "                   and c.emr_id = a.emr_id\n" +
            "                   and c.event_no = inHos.event_no\t\t\t\t\t\t\t  \n" +
            "                   and c.patient_id = inHos.patient_id\n" +
            "                   and c.template_code in (420, 1099)\n" +
            "                   and b.nursing_item_id = 'total_point'\n" +
            "                   and to_number(b.nursing_item_value) >= 15\n" +
            "                   and rownum = 1\n" +
            "                 order by a.CREATE_TIME desc) ebi\n" +
            "         where rownum = 1) as \"nursingItemValue\",\n" +
            "inHos.Inp_Case_No as \"caseno\"\n" +
            "from zoedict.dic_bed_dict          bed,\n" +
            "       zoepatient.PAT_IN_HOSPITAL    inHos,\n" +
            "       zoepatient.pat_bed_use_record useRecord\n" +
            " where inHos.Bed_No(+) = bed.bed_code\n" +
            "   and inHos.Nursing_Dept_Code(+) = bed.Nursing_Dept_Code\n" +
            "   and useRecord.bed_no(+) = inHos.bed_no\n" +
            "   and useRecord.room_no(+) = inHos.Room_No\n" +
            "   and useRecord.in_dept(+) = inHos.Nursing_Dept_Code\n" +
            "   and useRecord.event_no(+) = inHos.event_no\n" +
            "   and useRecord.in_dept(+) = inHos.in_dept_code\n" +
            "   and useRecord.Out_Bed_Date is null\n" +
            "   and bed.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
            "   and bed.VALID_FLAG = '1'\n" +
            "   and inHos.IN_HOSPITAL_STATUS_CODE(+) not IN ('0', '1','7','9') "),

    comMonSql("from zoedict.dic_bed_dict          bed,\n" +
            "       zoepatient.PAT_IN_HOSPITAL    inHos,\n" +
            "       zoepatient.pat_bed_use_record useRecord\n" +
            " where inHos.Bed_No(+) = bed.bed_code\n" +
            "   and inHos.Nursing_Dept_Code(+) = bed.Nursing_Dept_Code\n" +
            "   and useRecord.bed_no(+) = inHos.bed_no\n" +
            "   and useRecord.room_no(+) = inHos.Room_No\n" +
            "   and useRecord.in_dept(+) = inHos.Nursing_Dept_Code\n" +
            "   and useRecord.event_no(+) = inHos.event_no\n" +
            "   and useRecord.in_dept(+) = inHos.in_dept_code\n" +
            "   and useRecord.Out_Bed_Date is null\n" +
            "   and bed.NURSING_DEPT_CODE = #nursingDeptCode#\n" +
            "   and bed.VALID_FLAG = '1'\n" +
            "   and inHos.IN_HOSPITAL_STATUS_CODE(+) not IN ('0', '1','7','9')"),

    topPart2("select\n" +
            "inHos.patient_name as \"patientName\",\n" +
            "bed.bed_code as \"bedNo\",\n" +
            "bed.SORT_NO as \"sortNo\",\n" +
            "(select leDict.nursing_level_name\n" +
            "          from zoedict.DIC_NURSING_LEVEL_DICT leDict\n" +
            "         where leDict.Nursing_Level_Code = inHos.Nursing_Level_Code) as \"nursingLevelName\",\n" +
            "inHos.In_Dept_Code  as \"inDeptCode\",\n" +
            "inHos.NOW_CONDITION_CODE as \"nowConditionCode\",\n" +
            "(select VALUE_NAME\n" +
            "          from zoedict.DIC_BASIC_DICT\n" +
            "         where dict_name = 'SEX'\n" +
            "           and VALUE_CODE = inHos.patient_sex) as \"sexName\",\n" +
            "inHos.patient_sex as \"sexCode\",\n" +
            "inHos.Event_No as \"eventNo\",\n" +
            "bed.BED_STATUS_CODE as \"bedStatusCode\",\n" +
            "(select DT.VALUE_NAME\n" +
            "          from zoedict.Dic_Basic_Dict DT\n" +
            "         where DT.VALUE_CODE = bed.BED_STATUS_CODE\n" +
            "           and DT.VALID_FLAG = 1\n" +
            "           and DT.DICT_NAME = 'BED_STATUS_DICT') \"bedStatusName\",\n" +
            "inHos.patient_id \"patientId\",\n" +
            "inHos.AGE as \"age\",\n" +
            " (select pat.bed_no\n" +
            "          from zoepatient.PAT_IN_HOSPITAL    pat,\n" +
            "               zoepatient.pat_bed_use_record record\n" +
            "         where bed.bed_code = record.bed_no\n" +
            "           and bed.nursing_dept_code = record.ward\n" +
            "           and record.out_bed_date is null\n" +
            "           and pat.IN_HOSPITAL_STATUS_CODE in ('2', '3', '4', '5')\n" +
            "           and pat.event_no = record.event_no\n" +
            "           and rownum = 1) \"mainBedNo\",\n" +
            "(select pat.room_no\n" +
            "          from zoepatient.PAT_IN_HOSPITAL    pat,\n" +
            "               zoepatient.pat_bed_use_record record\n" +
            "         where bed.bed_code = record.bed_no\n" +
            "           and bed.nursing_dept_code = record.ward\n" +
            "           and record.out_bed_date is null\n" +
            "           and pat.IN_HOSPITAL_STATUS_CODE in ('2', '3', '4', '5')\n" +
            "           and pat.event_no = record.event_no\n" +
            "           and rownum = 1) \"mainRoomNo\",\n" +
            "(select pi.item_code\n" +
            "          from ZOEPRES.PRES_INP_PRES_RECORD pi\n" +
            "         where pi.event_no = inHos.Event_No\n" +
            "           and pi.PRES_STATUS_CODE = '6'\n" +
            "           and (pi.item_code = '200009' or pi.item_code = '6859')\n" +
            "           and rownum = 1) \"itemCode\",\n" +
            "decode(trunc(inHos.admission_dept_time),trunc(sysdate),'今日入院') as \"inFlag\",\n" +
            "'' as \"void\",\n" +
            "(SELECT\n" +
            "            case when\n" +
            "           TRUNC(MAX(A.PRE_DISCHARGED_TIME)) > trunc(sysdate)\n" +
            "            then '计划出院'\n" +
            "            when TRUNC(MAX(A.PRE_DISCHARGED_TIME))  = trunc(sysdate)\n" +
            "            then '今日出院'\n" +
            "            when TRUNC(MAX(A.PRE_DISCHARGED_TIME))  < trunc(sysdate)\n" +
            "            then '待办理出院'\n" +
            "            else '' end\n" +
            "          FROM ZOEPATIENT.PAT_PRE_DISCHARGED_REGISTER A\n" +
            "         WHERE a.STATUS='2'\n" +
            "           and a.EVENT_NO = inHos.EVENT_NO\n" +
            "           and inHos.IN_HOSPITAL_STATUS_CODE not in ('6')) as \"dischargeFlag\",\n" +
            "'0' \"babyFlag\",\n" +
            "(select t.STAFF_NAME\n" +
            "          from zoecomm.COM_STAFF_BASIC_INFO t\n" +
            "         where t.STAFF_NO= inHos.DIRECTOR_DOCTOR_CODE )\n" +
            "       as \"主治医生\",\n" +
            "to_date(sysdate) - to_date(inHos.ADMISSION_DEPT_TIME)+1||'天' as \"住院时长\",\n" +
            "( select decode(t.poverty_relief_flag,'1','贫困','')\n" +
            "  from zoeinsur.ins_patient_insur_info t\n" +
            " where t.patient_id = inHos.PATIENT_ID) as \"贫困\",\n" +
            "(select (select d.insur_name\n" +
            "          from zoeinsur.ins_base_dict d\n" +
            "         where d.insur_catalog_code = '4'\n" +
            "           and d.insur_dict_name_code = 'MEDICAL_RESCUE_TYPE'\n" +
            "           and t.medical_rescue_type = d.insur_code) medical_rescue_type\n" +
            "  from zoeinsur.ins_patient_insur_info t\n" +
            " where t.patient_id = inHos.PATIENT_ID) as \"救助人员类型\",\n" +
            "(select decode(ebi.nursing_item_value, 1, '跌倒/坠床高危', '') \"nursingItemValue\"\n" +
            "          from (select 1 nursing_item_value\n" +
            "                  from zoeemr.emr_nursing_table_master a,\n" +
            "                       zoeemr.emr_nursing_table_detail b,\n" +
            "                       zoeemr.emr_basic_info           c\n" +
            "                 where a.nursing_row_id = b.nursing_row_id\n" +
            "                   and c.emr_id = a.emr_id\n" +
            "                   and c.event_no = inHos.event_no\n" +
            "                   and c.patient_id = inHos.patient_id\n" +
            "                   and c.template_code in (420, 1099)\n" +
            "                   and b.nursing_item_id = 'total_point'\n" +
            "                   and to_number(b.nursing_item_value) >= 15\n" +
            "                   and rownum = 1\n" +
            "                 order by a.CREATE_TIME desc) ebi\n" +
            "         where rownum = 1) as \"nursingItemValue\",\n" +
            "inHos.Inp_Case_No as \"caseno\"");
    private String sql;

    sql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

}
