package cn.edu.guet.dao.impl;

import cn.edu.guet.bean.Page;
import cn.edu.guet.bean.PlanDesignDTO;
import cn.edu.guet.bean.PlanDesignInfo;
import cn.edu.guet.common.ResponseData;
import cn.edu.guet.dao.BaseDao;
import cn.edu.guet.dao.PlanDesignInfoDao;
import cn.edu.guet.util.DBConnection;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;


import java.sql.*;
import java.util.*;

public class PlanDesignInfoDaoImpl extends BaseDaoImpl<PlanDesignInfo>implements PlanDesignInfoDao {

    @Override
    public List<String> getPlanBillNo() throws SQLException {
        Connection conn = DBConnection.getConn();
        String sql = "SELECT plan_bill_no FROM planDesignInfo ORDER BY plan_bill_no DESC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<String> planBillNumbers = new ArrayList<>();
        while (rs.next()) {
            String planBillNo = rs.getString(1);
            planBillNumbers.add(planBillNo);
        }
        return planBillNumbers;
    }

    @Override
    public Page<PlanDesignInfo> searchBill(PlanDesignDTO planDesignDTO) throws SQLException {
         /*
        动态SQL
        SELECT * FROM plandesigninfo WHERE a=? and b=? c=? and time between ? and ?
         */
        String sql = "SELECT * FROM plandesigninfo";
        StringBuffer where = new StringBuffer();
        String whereClause = "";
        Map<Integer, Object> map = new HashMap<>();
        int count = 1;
        if (!StringUtils.isBlank(planDesignDTO.getPlanDesignName())) {
            if (whereClause.length() == 0) {
                where.append(" WHERE plan_design_name LIKE ?");
                whereClause = where.toString();
            } else {
                where.append(" AND plan_design_name LIKE ?");
                whereClause = where.toString();
            }
            map.put(count++, planDesignDTO.getPlanDesignName());
        }
        if (planDesignDTO.getSpecId() != null) {
            if (whereClause.length() == 0) {
                where.append(" WHERE spec_id = ?");
                whereClause = where.toString();
            } else {
                where.append(" AND spec_id = ?");
                whereClause = where.toString();
            }
            map.put(count++, planDesignDTO.getSpecId());
        }
        if (!StringUtils.isBlank(planDesignDTO.getDesigner())) {
            if (whereClause.length() == 0) {
                where.append(" WHERE designer LIKE ?");
                whereClause = where.toString();
            } else {
                where.append(" AND designer LIKE ?");
                whereClause = where.toString();
            }
            map.put(count++, planDesignDTO.getDesigner());
        }
        if (CollectionUtil.isNotEmpty(planDesignDTO.getCreateTime())) {
            if (whereClause.length() == 0) {
                where.append(" WHERE create_time between ? AND ?");
                whereClause = where.toString();
            } else {
                where.append(" AND create_time between ? AND ?");
                whereClause = where.toString();
            }
            map.put(count++, planDesignDTO.getCreateTime());
        }
        String pagination = " LIMIT ?,?";
//        SELECT * FROM t_plan_design_business_route  LIMIT 0,4
        // 4：每页4条记录
        // 0：第XX页
        int currentPage = (planDesignDTO.getCurrent() - 1) * planDesignDTO.getSize();
        int begin = count++;
        int limit = count++;
        map.put(begin, currentPage);
        map.put(limit, planDesignDTO.getSize());
        sql = sql + whereClause + pagination;
        Connection conn = DBConnection.getConn();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        Set<Integer> keySet = map.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            Object value = map.get(key);
            if (ClassUtils.isAssignable(value.getClass(), String.class)) {
                preparedStatement.setObject(key, "%" + value + "%");
            } else if (ClassUtils.isAssignable(value.getClass(), Timestamp.class)) {
                preparedStatement.setString(key, DateUtil.format((Timestamp) value, "yyyy-MM-dd HH:mm:ss"));
            } else {
                preparedStatement.setObject(key, value);
            }
        }
        /*map.forEach((key, value) -> {
            try {
                if (ClassUtils.isAssignable(value.getClass(), String.class)) {
                    preparedStatement.setObject(key, "%" + value + "%");
                } else if (ClassUtils.isAssignable(value.getClass(), List.class)) {
                    for (Timestamp timestamp : planDesignDTO.getCreateTime()) {
                        key++;
                        preparedStatement.setString(key, DateUtil.format(timestamp, "yyyy-MM-dd HH:mm:ss"));
                    }
                } else {
                    preparedStatement.setObject(key, value);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });*/
        List<PlanDesignInfo> planDesignInfoList = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            PlanDesignInfo planDesignInfo = new PlanDesignInfo();
            planDesignInfo.setId(rs.getLong("id"));
            planDesignInfo.setPlan_bill_no(rs.getString("plan_bill_no"));
            planDesignInfo.setPlan_design_name(rs.getString("plan_design_name"));
            planDesignInfo.setDesign_company(rs.getString("design_company"));
            planDesignInfo.setSpec_id(rs.getInt("spec_id"));
            planDesignInfo.setProject_director(rs.getString("project_director"));
            planDesignInfo.setSpec_leader(rs.getString("spec_leader"));
            planDesignInfo.setDesigner(rs.getString("designer"));
            planDesignInfo.setReviewer(rs.getString("reviewer"));
            planDesignInfo.setSource(rs.getInt("source"));
            planDesignInfo.setSystem_cad_file_name(rs.getString("system_cad_file_name"));
            planDesignInfo.setSystem_cad_file_url(rs.getString("system_cad_file_url"));
            planDesignInfo.setSystem_excel_file_name(rs.getString("system_excel_file_name"));
            planDesignInfo.setSystem_excel_file_url(rs.getString("system_excel_file_url"));
            planDesignInfo.setChannel_excel_file_name(rs.getString("channel_excel_file_name"));
            planDesignInfo.setChannel_excel_file_url(rs.getString("channel_excel_file_url"));
            planDesignInfo.setCad_coord_left(rs.getInt("cad_coord_left"));
            planDesignInfo.setCad_coord_top(rs.getInt("cad_coord_top"));
            planDesignInfo.setCad_coord_right(rs.getInt("cad_coord_right"));
            planDesignInfo.setCad_coord_bottom(rs.getInt("cad_coord_bottom"));
            planDesignInfo.setStaff_id(rs.getInt("staff_id"));
            planDesignInfo.setStaff_name(rs.getString("staff_name"));
            planDesignInfo.setCreate_time(rs.getTimestamp("create_time"));
            planDesignInfo.setUpdate_time(rs.getTimestamp("update_time"));
            planDesignInfoList.add(planDesignInfo);
        }
        Page<PlanDesignInfo> paginations = new Page<>();
        // 如何得出总记录数？执行SELECT COUNT(*) FROM plandesigninfo
        // 页数：总记录数/size，如果能整除，就是：总记录数/size，否则：总记录数/size+1
        sql = "SELECT COUNT(*) FROM plandesigninfo"+whereClause;
        preparedStatement.close();
        preparedStatement = conn.prepareStatement(sql);
        map.remove(begin);
        map.remove(limit);
        Set<Integer> keySet1 = map.keySet();
        Iterator<Integer> iterator1 = keySet1.iterator();
        while (iterator1.hasNext()) {
            Integer key = iterator1.next();
            Object value = map.get(key);
            if (ClassUtils.isAssignable(value.getClass(), String.class)) {
                preparedStatement.setObject(key, "%" + value + "%");
            } else if (ClassUtils.isAssignable(value.getClass(), Timestamp.class)) {
                preparedStatement.setString(key, DateUtil.format((Timestamp) value, "yyyy-MM-dd HH:mm:ss"));
            } else {
                preparedStatement.setObject(key, value);
            }
        }
        rs = preparedStatement.executeQuery();
        rs.next();
        int totalRow = rs.getInt(1);
        int pages = 0;
        if (totalRow % planDesignDTO.getSize() == 0) {
            pages = totalRow / planDesignDTO.getSize();
        } else {
            pages = totalRow / planDesignDTO.getSize() + 1;
        }
        paginations.setTotal(totalRow);
        paginations.setPages(pages);
        paginations.setSize(planDesignDTO.getSize());
        paginations.setRecords(planDesignInfoList);
        return paginations;
    }

    @Override
    public Long getPlanDesignIdByPlanBiilNo(String planBillNo) throws SQLException {
        String sql = "SELECT id FROM plandesigninfo WHERE plan_bill_no = ?";
        Connection conn = DBConnection.getConn();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1,planBillNo);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        Long planDesignId = rs.getLong(1);
        return planDesignId;
    }
}