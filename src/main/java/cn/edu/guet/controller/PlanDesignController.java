package cn.edu.guet.controller;

import cn.edu.guet.bean.PlanDesignDTO;
import cn.edu.guet.bean.PlanDesignInfo;
import cn.edu.guet.common.ResponseData;
import cn.edu.guet.mvc.annotation.Controller;
import cn.edu.guet.mvc.annotation.RequestMapping;
import cn.edu.guet.service.PlanDesignService;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Controller
public class PlanDesignController {
    public static Logger logger = LoggerFactory.getLogger(PlanDesignController.class);
    private PlanDesignService planDesignService;

    // 用于属性注入
    public void setPlanDesignService(PlanDesignService planDesignService) {
        this.planDesignService = planDesignService;
    }

    /**
     *查询主备路由光缆
     * @return json
     */
    @RequestMapping("/selectRouteCableList")
    public ResponseData selectRouteCableList(){
        ResponseData responseData = planDesignService.selectRouteCableList();
        return responseData;
    }

    @RequestMapping("/getPlanBillNo")
    public ResponseData getPlanBillNo() {
        String planBillNo = planDesignService.getPlanBillNo();
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

        ResponseData responseData = planDesignService.createBill(planDesignInfo);
        return responseData;

    }

    @RequestMapping("/createBillAndAnalyse")
    public ResponseData createBillAndAnalyse(PlanDesignInfo planDesignInfo){
        // 返回对三种文件（系统规划CAD图纸、系统规划文件excel、波道规划文件excel）的分析结果
        ResponseData analyseResult = planDesignService.createBillAndAnalyse(planDesignInfo);
        return analyseResult;
    }

    @RequestMapping("/searchBill")
    public ResponseData searchBill(PlanDesignDTO planDesignDTO){
        logger.info("搜索条件：{}", planDesignDTO);
        return planDesignService.searchBill(planDesignDTO);
    }

    @RequestMapping("/upload")
    public ResponseData upload(HttpServletRequest request, HttpServletResponse response) {
        ResponseData upload = planDesignService.upload(request, response);
        return upload;
    }



}
