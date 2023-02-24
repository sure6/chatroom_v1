package com.softeem.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.softeem.dto.User;
import com.softeem.utils.BaseServlet;
import com.softeem.dao.UserDAO;

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	//退出
	public String exit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// 获得session对象
		HttpSession session = request.getSession();
		//将session销毁.
		session.invalidate();
		// 页面转向.
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		return null;
	}
	public String sendMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// 1.接收数据
		String from = request.getParameter("from");
		String face = request.getParameter("face");
		String to = request.getParameter("to");
		String color = request.getParameter("color");
		String content = request.getParameter("content");
		// 发言时间,正常情况下使用SimpleDateFormat
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sendTime = sdf.format(new Date());
		// 获得ServletContext对象.
		ServletContext context = getServletContext();
	   //  从ServletContext中获取消息
		String sourceMessage = (String) context.getAttribute("message");
		// 拼接发言的内容:xx 对 yy 说 xxx
		sourceMessage+= "<font color='blue'><strong>" + from+ "</strong></font><font color='#CC0000'>" + face
				+ "</font>对<font color='green'>[" + to + "]</font>说:"
				+ "<font color='" + color + "'>" + content + "</font>("
				+ sendTime + ")<br>";
		// 将消息存入到application的范围
		context.setAttribute("message", sourceMessage);
		return getMessage(request, response);
	}
	
	
	
	 //检查session是否过期
	public String check(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// 从session中获得用户的信息
		User login = (User) request.getSession().getAttribute("login");
		// 判断session中的用户是否过期
		if(login == null){
			// 登录的信息已经过期了!
			response.getWriter().println("1");
		}else{
			// 登录的信息没有过期
			response.getWriter().println("2");
		}
		return null;
	}
	//踢人功能
	public String kick(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// 1.接受参数
		 int id = Integer.parseInt(request.getParameter("id"));
		// 2.踢人:从userMap中将用户对应的session销毁.
		 Map<User,HttpSession> userMap = (Map<User, HttpSession>) getServletContext().getAttribute("userMap");
		// 获得这个用户对应的session.如何知道是哪个用户呢? id已经传递过来.去数据库中查询.
			// 重写user的equals 和 hashCode 方法 那么只要用户的id相同就认为是同一个用户.
		 User user=new User();
		 user.setId(id);
		// 从map集合中获取用户的对应的session 
		 HttpSession session = userMap.get(user);
		// 销毁session
		 session.invalidate();
		 //重定向
		 response.sendRedirect(request.getContextPath()+"/main.jsp");
		 return null;
	}
	
	//发送消息
	public String getMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String message = (String) getServletContext().getAttribute("message");
		if(message!=null){
			response.getWriter().println(message);
		}
		return null;
	}
	//登录
	public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String[]> map = request.getParameterMap();
		String[] strings = map.get("method");
		String[] string1 = map.get("username");
		String[] string2 = map.get("password");
		String[] string3 = map.get("type");
		User user = new User();

		try {
			//封装数据
			BeanUtils.populate(user, map);
		    UserDAO udao=new UserDAO();
		    User login = udao.login(user);
		    if(login==null){
		    	request.setAttribute("msg", "用户名或密码错误");
		    	return  "/index.jsp";
		    }else{
		    	//用户第二次登录时,清除第一次登录的session
		    	request.getSession().invalidate();
		    	
		    	@SuppressWarnings("unchecked")
				Map<User, HttpSession> userMap = (Map<User, HttpSession>) getServletContext()
						.getAttribute("userMap");
		    	if(userMap.containsKey(login)){
		    		HttpSession session = userMap.get(login);
		    		session.invalidate();
		    	}
		    	
		    	request.getSession().setAttribute("login", login);
		    	//获取servlet容器
		    	ServletContext application = getServletContext();
		    	
		    	//采用拼接字符串的形式来存储上线用户的显示
		    	String sourceMsg="";
		    	//这是将之前登录的信息保留下来
		    	if(application.getAttribute("message")!=null){
		    		sourceMsg=application.getAttribute("message").toString();
		    	}
		    	sourceMsg+="系统公告:<font style='color:red;'>"+login.getUsername()+"进入聊天室了</font><br>";
		    	//将消息发送到整个servlet域里,保证在线人都能看到
		    	application.setAttribute("message", sourceMsg);
		    	response.sendRedirect(request.getContextPath() + "/main.jsp");
		    	return null;
		    }
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
