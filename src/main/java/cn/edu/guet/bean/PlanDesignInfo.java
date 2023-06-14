package cn.edu.guet.bean;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PlanDesignInfo {
    private Long id;
    private  String plan_bill_no;
    private String design_company;
    private int spec_id;
    private String project_director;
    private String spec_leader;
    private String designer;
    private  String plan_design_name;
    //    校审人
    private String reviewer;
    private int source;


    private String system_cad_file_name;
    private String system_cad_file_url;
    private String system_excel_file_name;
    private String system_excel_file_url;
    private String channel_excel_file_name;
    private String channel_excel_file_url;
    private int cad_coord_left;
    private int cad_coord_top;
    private int cad_coord_right;
    private int cad_coord_bottom;
    private Integer staff_id;
    private String staff_name;


    private Timestamp create_time;
    private Timestamp update_time;




}
