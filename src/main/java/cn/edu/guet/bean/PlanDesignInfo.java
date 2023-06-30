package cn.edu.guet.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName t_plan_design_info
 */
@Data
public class PlanDesignInfo implements Serializable {
    private Long id;

    private String planBillNo;

    private String planDesignName;

    private String designCompany;

    private Integer specId;

    private String projectDirector;

    private String specLeader;

    private String designer;

    private String reviewer;

    private Integer source;

    private Integer systemCadFileId;

    private String systemCadFileName;

    private String systemCadFileUrl;

    private Integer systemExcelFileId;

    private String systemExcelFileName;

    private String systemExcelFileUrl;

    private Integer channelExcelFileId;

    private String channelExcelFileName;

    private String channelExcelFileUrl;

    private Integer cadCoordLeft;

    private Integer cadCoordTop;

    private Integer cadCoordRight;

    private Integer cadCoordBottom;

    private Integer staffId;

    private String staffName;

    private Date createTime;

    private Date updateTime;

}