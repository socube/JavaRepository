package com.yf.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yf.bean.ProductView;
import com.yf.dao.Tb_productDao;

public class ProductServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page=Integer.parseInt(request.getParameter("page"));//��ǰҳ�� ����ҳ(ҳ�洫����)
		int count=0;//�ܹ���������¼(��ѯ���ݿ�)
		int pageSize=Integer.parseInt(request.getParameter("pageSize"));//ÿҳ��ʾ����������(ҳ�洫����)
		Tb_productDao dao=new Tb_productDao();
		List<ProductView> list=dao.queryProductView();
		count=list.size();//��ȡ�ܼ�¼��
		list=dao.queryProductView(page, pageSize);//��ѯָ��ҳ������
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("count", count);
		request.getRequestDispatcher("system/clothing.jsp").forward(request, response);
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}

}