package cn.edu.guet.bean;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fanFengLi
 * @Date: 2023/06/06/19:52
 * @Description:
 */
@Data
public class PlanDesignDTO {
//    planDesign规划设计
    private String planDesignName;
    private Integer specId;
    private String designer;
    private List<Timestamp> createTime;
    private Integer current;
    private Integer size;

}
