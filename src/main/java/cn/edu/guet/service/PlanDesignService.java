package cn.edu.guet.service;

import cn.edu.guet.bean.PlanDesignDTO;
import cn.edu.guet.bean.PlanDesignInfo;
import cn.edu.guet.common.ResponseData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PlanDesignService {
    ResponseData selectRouteCableList();

    ResponseData createBill(PlanDesignInfo planDesignInfo);
    String getPlanBillNo();
    ResponseData createBillAndAnalyse(PlanDesignInfo planDesignInfo);

    ResponseData upload(HttpServletRequest request, HttpServletResponse response);


    /**
    * @Description: 创建外部工单
    * @Param: [planDesignInfo] 工单信息
    * @return: 创建结果
    */
    ResponseData createOutBill(PlanDesignInfo planDesignInfo);

    ResponseData searchBill(PlanDesignDTO planDesignDTO);
}
