package cn.edu.guet.service.impl;

import cn.edu.guet.bean.Page;
import cn.edu.guet.bean.PlanDesignDTO;
import cn.edu.guet.bean.PlanDesignHistoryRecord;
import cn.edu.guet.bean.PlanDesignInfo;
import cn.edu.guet.common.ResponseData;
import cn.edu.guet.dao.HistoryRecordDao;
import cn.edu.guet.dao.PlanDesignInfoDao;
import cn.edu.guet.dao.RouteCableDao;
import cn.edu.guet.dao.impl.BaseDaoImpl;
import cn.edu.guet.service.PlanDesignService;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PlanDesignServiceImpl implements PlanDesignService {
    public static Logger logger = LoggerFactory.getLogger(PlanDesignServiceImpl.class);
    private RouteCableDao routeCableDao;
    private PlanDesignInfoDao planDesignInfoDao;
    private HistoryRecordDao historyRecordDao;

    public PlanDesignInfoDao getPlanDesignInfoDao() {
        return planDesignInfoDao;
    }

    public void setPlanDesignInfoDao(PlanDesignInfoDao planDesignInfoDao) {
        this.planDesignInfoDao = planDesignInfoDao;
    }

    public void setRouteCableDao(RouteCableDao routeCableDao) {
        this.routeCableDao = routeCableDao;
    }
    public void setHistoryRecordDao(HistoryRecordDao historyRecordDao) {
        this.historyRecordDao = historyRecordDao;
    }

    /**
     * 查询路由光缆清单数据（目前无分类）
     * @return
     */
    @Override
    public ResponseData selectRouteCableList() {
        return ResponseData.ok(routeCableDao.getObjects());
    }

    @Override
    public ResponseData createBill(PlanDesignInfo planDesignInfo) {

        try {
            // 获取当前时间
            LocalDateTime currentDateTime = LocalDateTime.now();

            // 定义日期时间格式
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 格式化日期时间
            Timestamp now = Timestamp.valueOf(currentDateTime);
            logger.info("工单创建时间："+currentDateTime);
            logger.info("工单创建时间戳："+now);

            planDesignInfo.setCreate_time(now);
            planDesignInfo.setUpdate_time(now);


            int save =planDesignInfoDao.save(planDesignInfo);
            if(save==1){
                logger.info("工单创建成功");
                return  new ResponseData("工单创建成功");
            }

        } catch (SQLException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        logger.info("工单创建失败");
        return new ResponseData("工单创建失败");
    }
    @Override
    public String getPlanBillNo(){
        List<String> planBillNumbers = null;
        try {

            Date now = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String nowDate = sdf.format(now);

            planBillNumbers = planDesignInfoDao.getPlanBillNo();
            if (planBillNumbers.size() != 0) {
                String planBillNo = planBillNumbers.get(0);
                String planBillNoDate = planBillNo.substring(9,17);

                // 判断当天是否有工单号
                if (planBillNoDate.equals(nowDate)) {
                    logger.info("当天有工单号");
                    String number = planBillNo.substring(18);
                    int no = Integer.parseInt(number);
                    no++;
                    number = String.valueOf(no);
                    if (number.length() == 1) {
                        return "AHYD-PMS-" + planBillNoDate + "-00" + number;
                    } else if (number.length() == 2) {
                        return "AHYD-PMS-" + planBillNoDate + "-0" + number;
                    } else {
                        return "AHYD-PMS-" + planBillNoDate + "-" + number;
                    }
                } else {
                    // 当天没有工单号
                    logger.info("当天没有工单号");
                    return "AHYD-PMS-" + nowDate + "-001";
                }
            } else {
                // 数据库表中无工单号
                logger.info("数据库表中无工单号");
                return "AHYD-PMS-" + nowDate + "-001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseData createBillAndAnalyse(PlanDesignInfo planDesignInfo) {

        // 组装调用分析接口的参数
        Map<String, Object> map = new HashMap<>(8);
//        planDesignInfo.setSystem_cad_file_url("http://172.168.27.251:26066/fastdfs/service/fastdfs/download?fileId=150855&access_token=332ee272-cd5b-4839-99a9-d941209339c8&fileUrl=group1/M00/00/DE/rKg_r2R4mOboLTd-ABWb9YCSO8Q051.dxf");
//        planDesignInfo.setSystem_excel_file_url("http://172.168.27.251:26066/fastdfs/service/fastdfs/download?fileId=150856&access_token=332ee272-cd5b-4839-99a9-d941209339c8&fileUrl=group1/M00/00/DE/rKg_r2R4mne9tcZ7AAF2ALhvEg0468.xls");
//        planDesignInfo.setChannel_excel_file_url("http://172.168.27.251:26066/fastdfs/service/fastdfs/download?fileId=150857&access_token=332ee272-cd5b-4839-99a9-d941209339c8&fileUrl=group1/M00/00/DE/rKg_r2R4mu6zZ6T3AABqrcB1NLM19.xlsx");
//        planDesignInfo.setPlan_bill_no("AHYD-PMS-20230519-201");
//        planDesignInfo.setCad_coord_left(-215);
//        planDesignInfo.setCad_coord_top(-20254);
//        planDesignInfo.setCad_coord_right(179);
//        planDesignInfo.setCad_coord_bottom(-20441);
        map.put("systemCADFilePath", planDesignInfo.getSystem_cad_file_url());
        map.put("systemExcelFilePath", planDesignInfo.getSystem_excel_file_url());
        map.put("channelExcelFilePath", planDesignInfo.getChannel_excel_file_url());
        map.put("planBillNo", planDesignInfo.getPlan_bill_no());
        map.put("cadCoordLeft", planDesignInfo.getCad_coord_left());
        map.put("cadCoordTop", planDesignInfo.getCad_coord_top());
        map.put("cadCoordRight", planDesignInfo.getCad_coord_right());
        map.put("cadCoordBottom", planDesignInfo.getCad_coord_bottom());
        String queryJson = new Gson().toJson(map);
        Map<String, String> heads = new HashMap<>(1);
        heads.put("Content-Type", "application/json;charset=UTF-8");

        HttpResponse response = HttpRequest.post("http://localhost:9999/analyseCADCallApi")
                .headerMap(heads, false)
                .body(queryJson)
                .timeout(5 * 60 * 1000)
                .execute();
        ResponseData responseData = (ResponseData) new Gson().fromJson(response.body(), ResponseData.class);
        logger.info("分析业务返回："+responseData);
        // JSON转Map
        Map<String, Object> map2 = JSONUtil.parseObj(responseData.getData());

        PlanDesignHistoryRecord historyRecord = new PlanDesignHistoryRecord();
        // 先获取分析号
        historyRecord.setAnalyse_no((String) map2.get("analyseNo"));
        historyRecord.setAnalyse_status(1);
        historyRecord.setAnalyse_begin_time(new Timestamp(System.currentTimeMillis()));
        historyRecord.setSystem_cad_file_url(planDesignInfo.getSystem_cad_file_url());
        historyRecord.setSystem_cad_file_name(planDesignInfo.getSystem_cad_file_name());
        historyRecord.setSystem_excel_file_url(planDesignInfo.getSystem_excel_file_url());
        historyRecord.setSystem_excel_file_name(planDesignInfo.getSystem_excel_file_name());
        historyRecord.setChannel_excel_file_url(planDesignInfo.getChannel_excel_file_url());
        historyRecord.setChannel_excel_file_name(planDesignInfo.getChannel_excel_file_name());
        historyRecord.setCreate_time(new Timestamp(System.currentTimeMillis()));


        try {
            // 保存工单
            planDesignInfoDao.save(planDesignInfo);
            Long planDesignId = planDesignInfoDao.getPlanDesignIdByPlanBiilNo(planDesignInfo.getPlan_bill_no());

            // 保存历史记录
            historyRecord.setPlan_design_id(planDesignId);
            historyRecordDao.save(historyRecord);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new ResponseData("保存成功");
    }

    @Override
    public ResponseData upload(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String dir=System.getProperty("user.dir");
        dir=dir.substring(0,dir.lastIndexOf("\\"));

        Gson gson = new Gson();
        String realPath = dir + "/webapps/upload";
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart == true) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = null;
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            Iterator<FileItem> itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (!item.isFormField()) {
                    File fullFile = new File(item.getName());
                    File savedFile = new File(realPath + "/", fullFile.getName());
                    try {
                        item.write(savedFile);
                        String url = "http://localhost:8080/upload/" + fullFile.getName();
                        String[] strs = {url};
                        logger.info("上传业务返回：");
                        Arrays.asList(strs).stream().forEach(System.out::println);
                        return ResponseData.ok(strs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.print("the enctype must be multipart/form-data");
        }
        return null;
    }

    @Override
    public ResponseData createOutBill(PlanDesignInfo planDesignInfo) {
        return null;
    }

    @Override
    public ResponseData searchBill(PlanDesignDTO planDesignDTO) {
        Page<PlanDesignInfo> pagination = null;
        try {
            pagination = planDesignInfoDao.searchBill(planDesignDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseData.ok(pagination);
    }

    @Override
    public Long getPlanDesignIdByPlanBiilNo(String planBillNo) {
        Long id = null;
        try {
            id = planDesignInfoDao.getPlanDesignIdByPlanBiilNo(planBillNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
