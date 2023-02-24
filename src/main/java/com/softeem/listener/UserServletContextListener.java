package com.softeem.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import com.softeem.dto.User;

public class UserServletContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
       Map<User, HttpSession> map=new HashMap<>();
       sce.getServletContext().setAttribute("userMap", map);
	}

}
