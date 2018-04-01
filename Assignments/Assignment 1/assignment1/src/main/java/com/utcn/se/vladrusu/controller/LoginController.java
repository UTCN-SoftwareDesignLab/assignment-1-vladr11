package com.utcn.se.vladrusu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.utcn.se.vladrusu.services.IGeneralService;

@RestController
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	IGeneralService generalService;
	
	@RequestMapping(value = "/")
	public ModelAndView redirect(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("redirect:http://localhost:8080/assignment1/login/");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LoginController - GET");
		ModelAndView model = new ModelAndView("login");
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String performLogin(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (generalService.logIn(username, password)) {
			response.setStatus(200);
			HttpSession session = request.getSession();
			session.setAttribute("token", username);
			String next = null;
			if (generalService.isAdministrator(username)) {
				next = "admin";
			} else {
				next = "employee";
			}
			return "{\"Success\": 1,\"next\":\"" + next + "\"}";
		} else {
			response.setStatus(403);
			return "{\"error\" : \"Invalid credentials\"}";
		}
	}

}
