package cn.edu.guet.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName t_plan_design_history_record
 */
@Data
public class PlanDesignHistoryRecord implements Serializable {
    private Long id;

    private Long planDesignId;

    private String analyseNo;

    private Integer analyseStatus;

    private Date analyseBeginTime;

    private Date analyseEndTime;

    private Integer analyseTime;

    private Integer systemCadFileId;

    private String systemCadFileName;

    private String systemCadFileUrl;

    private Integer systemExcelFileId;

    private String systemExcelFileName;

    private String systemExcelFileUrl;

    private Integer channelExcelFileId;

    private String channelExcelFileName;

    private String channelExcelFileUrl;

    private String analyseLog;

    private Long analyseStaffId;

    private String analyseStaffName;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}