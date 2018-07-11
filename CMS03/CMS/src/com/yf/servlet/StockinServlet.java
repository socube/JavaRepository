package com.yf.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yf.bean.StockinView;
import com.yf.dao.Tb_stockinDao;

public class StockinServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page=Integer.parseInt(request.getParameter("page"));//当前页是 多少页(页面传过来)
		int count=0;//总共多少条记录(查询数据库)
		int pageSize=Integer.parseInt(request.getParameter("pageSize"));//每页显示多少条数据(页面传过来)
		Tb_stockinDao dao=new Tb_stockinDao();
		List<StockinView> list=dao.queryStockinView();
		count=list.size();//获取总记录数
		list=dao.queryStockinView(page, pageSize);//查询指定页的数据
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("count", count);
		request.getRequestDispatcher("stock/stockin.jsp").forward(request, response);
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
     this.doGet(request, response);
	}

}
