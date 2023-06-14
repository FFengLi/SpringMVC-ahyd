package cn.edu.guet.bean;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fanFengLi
 * @Date: 2023/06/14/11:25
 * @Description:
 */
@Data
public class Page<T> {
    // 当前页
    private Integer current;
    // 总记录数
    private Integer total;
    // 每页记录数
    private Integer size;
    // 总页数
    private Integer pages;

    private List<T> records;
}
