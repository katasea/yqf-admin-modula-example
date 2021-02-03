package cn.business.config;


import cn.business.main.service.util.AutoFixTableMySQLService;
import cn.business.util.CommonUtil;
import cn.business.util.Global;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Date;

@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {

    @Resource(name = "autoFixTableService")
    private AutoFixTableMySQLService autoFixTableMySQLService;


    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
        String date = request.getParameter("date");
        if(CommonUtil.isEmpty(date)) {
            date = Global.year_month.format(new Date());
        }
        //自动修复表结构
        if(CommonUtil.isEmpty(Global.isCreatedTable.get(date.split("-")[0]))) {
            autoFixTableMySQLService.run(Integer.parseInt(date.split("-")[0]));
            Global.isCreatedTable.put(date.split("-")[0],"1");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {}

}