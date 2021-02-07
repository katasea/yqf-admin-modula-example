package cn.business.bean.po;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 5780371099533288435L;
    private String ymstr;
    private boolean isAdmin;
    private String date;
    private String month;
    private String year;
    private String userid;
    private String username;
    private String deptid;
    private String deptname;
    private String companyid;
    private String companyname;
    private List<String> roles;
    private List<String> resources;//还未赋值

    public void setBean(JSONObject o) {
        this.setAdmin(o.getBoolean("admin"));
        this.setUserid(o.getString("userid"));
        this.setUsername(o.getString("username"));
        this.setYmstr(o.getString("ymstr"));
        this.setDate(o.getString("date"));
        this.setMonth(o.getString("month"));
        this.setYear(o.getString("year"));
        this.setDeptid(o.getString("deptid"));
        this.setDeptname(o.getString("deptname"));
        this.setCompanyid(o.getString("companyid"));
        this.setCompanyname(o.getString("companyname"));
    }
}
