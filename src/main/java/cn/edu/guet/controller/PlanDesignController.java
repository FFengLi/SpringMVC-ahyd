package cn.edu.guet.controller;

import cn.edu.guet.bean.PlanDesignDTO;
import cn.edu.guet.bean.PlanDesignInfo;
import cn.edu.guet.common.ResponseData;
import cn.edu.guet.mvc.annotation.Controller;
import cn.edu.guet.mvc.annotation.RequestMapping;
import cn.edu.guet.service.PlanDesignInfoService;
import cn.edu.guet.util.TransactionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@Controller
public class PlanDesignController {
    public static Logger logger = LoggerFactory.getLogger(PlanDesignController.class);
    private PlanDesignInfoService planDesignInfoService;
    private TransactionHandler transactionHandler = new TransactionHandler();

    // 用于属性注入
    public void setPlanDesignInfoService(PlanDesignInfoService planDesignInfoService) {
//        this.planDesignInfoService = planDesignInfoService;
//        create proxy object
        this.planDesignInfoService = (PlanDesignInfoService) transactionHandler.creatProxyObject(planDesignInfoService);

    }

    @RequestMapping("/getPlanBillNo")
    public ResponseData getPlanBillNo() {
        String planBillNo = planDesignInfoService.getPlanBillNo();
        if (!planBillNo.isEmpty()) {
            logger.info("工单号：" +planBillNo);
            return ResponseData.ok(planBillNo);
        } else {
            return ResponseData.fail();
        }
    }



    @RequestMapping("/createBill")
    public ResponseData createBill(PlanDesignInfo planDesignInfo){
        logger.info("创建工单:{}",planDesignInfo);
        planDesignInfo.setReviewer("fanFengLi");
        ResponseData responseData = planDesignInfoService.createBill(planDesignInfo);
        return responseData;

    }

    @RequestMapping("/createBillAndAnalyse")
    public ResponseData createBillAndAnalyse(PlanDesignInfo planDesignInfo){
        // 返回对三种文件（系统规划CAD图纸、系统规划文件excel、波道规划文件excel）的分析结果
        planDesignInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        planDesignInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        ResponseData analyseResult = null;
        analyseResult = planDesignInfoService.createBillAndAnalyse(planDesignInfo);
        return analyseResult;
    }

    @RequestMapping("/searchBill")
    public ResponseData searchBill(PlanDesignDTO planDesignDTO){
        logger.info("搜索条件：{}", planDesignDTO);
        return planDesignInfoService.searchBill(planDesignDTO);
    }

    @RequestMapping("/upload")
    public ResponseData upload(HttpServletRequest request, HttpServletResponse response) {
        ResponseData upload = planDesignInfoService.upload(request, response);
        return upload;
    }
}
