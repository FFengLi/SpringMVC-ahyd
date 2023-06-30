package cn.edu.guet.service;

import cn.edu.guet.bean.PlanDesignDTO;
import cn.edu.guet.bean.PlanDesignInfo;
import cn.edu.guet.common.ResponseData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author fanFengLi
* @description 针对表【t_plan_design_info(规划设计评估主表)】的数据库操作Service
* @createDate 2023-06-28 17:37:09
*/
public interface PlanDesignInfoService{

    ResponseData searchBill(PlanDesignDTO planDesignDTO);

    String getPlanBillNo();

    Long getPlanDesignIdByPlanBiilNo(String planBillNo);

    ResponseData upload(HttpServletRequest request, HttpServletResponse response);

    ResponseData createBill(PlanDesignInfo planDesignInfo);

    ResponseData createBillAndAnalyse(PlanDesignInfo planDesignInfo);
}
