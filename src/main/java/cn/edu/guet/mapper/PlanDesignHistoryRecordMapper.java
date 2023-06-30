package cn.edu.guet.mapper;

import cn.edu.guet.bean.PlanDesignHistoryRecord;

/**
* @author fanFengLi
* @description 针对表【t_plan_design_history_record(规划设计评估历史分析记录表)】的数据库操作Mapper
* @createDate 2023-06-29 15:51:32
* @Entity cn.edu.guet.bean.PlanDesignHistoryRecord
*/
public interface PlanDesignHistoryRecordMapper{
    int saveHistory(PlanDesignHistoryRecord historyRecord);

}




