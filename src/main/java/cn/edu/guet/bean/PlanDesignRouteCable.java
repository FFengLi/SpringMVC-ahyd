package cn.edu.guet.bean;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 */
@Data
public class PlanDesignRouteCable {
    private Long id;
    private Long plan_design_id;
    private Long plan_design_result_id;
    private String cable_seg_name;
    private String station_a;
    private String station_z;
    private String cable_name;
    private Integer is_main_backup;
    private Timestamp create_time;

}
