package com.softeem.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String methodName = request.getParameter("method");
		
		if(methodName==null||methodName.isEmpty()){
			methodName="execute";
			return;
		}
		
		Class<? extends BaseServlet> clz = this.getClass();
		
         try {
			Method method = clz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			String  result = (String) method.invoke(this, request,response);
			if(result!=null&&!result.isEmpty()){
				request.getRequestDispatcher(result).forward(request, response);
			}
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	

}
