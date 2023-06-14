package cn.edu.guet.common;

/**
 * @Author liwei
 * @Date 2023/5/21 10:40
 * @Version 1.0
 */

public enum ResponseRecode {
    RS_SUCCESS(1, "成功"),
    RS_INVALID_FORMAT(2, "格式错误"),
    RS_CMD_NOACCEPT(3, "命令不支持"),
    RS_INVALID_PARA(4, "参数错误"),
    RS_INFC_ERROR(5, "接口错误"),
    RS_PERMISSION_LIMIT(6, "权限不够"),
    RS_TIME_OUT(7, "指令超时"),
    RS_IF_RETURN_ERROR(8, "接口返回错误"),
    RS_LOGIN_TIMEOUT(9, "登录超时"),
    RS_LOGIN_FALSE(10, "登录失败"),
    RS_LOGIN_NULL(11, "用户没有登录"),
    RS_IF_RETURN_DATA_ERROR(12, "接口返回数据解析失败"),
    RS_OHTER(99, "其他"),
    SUCCESS_CODE(200, "成功"),
    PARAM_ERROR_CODE(400, "保存失败"),
    LIMIT_ERROR_CODE(401, "限制调用"),
    TOKEN_TIMEOUT_CODE(402, "token 过期"),
    NO_AUTH_CODE(403, "禁止访问"),
    NOT_FOUND(404, "资源没找到"),
    SERVER_ERROR_CODE(500, "服务器错误"),
    DOWNGRADE(406, "服务降级中"),
    RT_NULL(1001, "空指针");

    private int recode;
    private String redesc;

    public int getRecode() {
        return this.recode;
    }

    public void setRecode(int recode) {
        this.recode = recode;
    }

    public String getRedesc() {
        return this.redesc;
    }

    public void setRedesc(String redesc) {
        this.redesc = redesc;
    }

    private ResponseRecode(int recode, String redesc) {
        this.recode = recode;
        this.redesc = redesc;
    }
}
