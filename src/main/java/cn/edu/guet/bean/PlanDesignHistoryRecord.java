package cn.edu.guet.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author liwei
 * @Date 2023/6/11 15:19
 * @Version 1.0
 */
@Data
public class PlanDesignHistoryRecord {
    private Long id;
    private Long plan_design_id;
    private String analyse_no;
    private Integer analyse_status;
    private Timestamp analyse_begin_time;
    private Timestamp analyse_end_time;
    private Integer analyse_time;
    private String system_cad_file_url;
    private String system_cad_file_name;
    private String system_excel_file_url;
    private String system_excel_file_name;
    private String channel_excel_file_url;
    private String channel_excel_file_name;
    private String analyse_log;
    private Integer analyse_staff_id;
    private String analyse_staff_name;
    private Timestamp create_time;
}
