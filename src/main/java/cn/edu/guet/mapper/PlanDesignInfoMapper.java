package cn.edu.guet.mapper;

import cn.edu.guet.bean.Page;
import cn.edu.guet.bean.PlanDesignDTO;
import cn.edu.guet.bean.PlanDesignInfo;
import cn.edu.guet.common.ResponseData;

import java.sql.SQLException;
import java.util.List;

/**
* @author fanFengLi
* @description 针对表【t_plan_design_info(规划设计评估主表)】的数据库操作Mapper
* @createDate 2023-06-28 17:37:09
* @Entity cn.edu.guet.bean.PlanDesignInfo
*/
public interface PlanDesignInfoMapper{
    
    /**
    * @Description: 搜索工单
    * @Param: [planDesignDTO]
    * @return: 
    */
    List<PlanDesignInfo> searchBill(PlanDesignDTO planDesignDTO);

    List<String> getPlanBillNo();

    Long getPlanDesignIdByPlanBiilNo(String planBillNo);

    int saveBill(PlanDesignInfo planDesignInfo);

    int getPlanDesignCountByWhere(PlanDesignDTO planDesignDTO);
}




