package com.utcn.se.vladrusu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.utcn.se.vladrusu.exceptions.MissingFieldsException;
import com.utcn.se.vladrusu.exceptions.PasswordTooShortException;
import com.utcn.se.vladrusu.exceptions.UsernameAlreadyExistsException;
import com.utcn.se.vladrusu.model.EmployeeAccount;
import com.utcn.se.vladrusu.model.EmployeeRole;
import com.utcn.se.vladrusu.services.IGeneralService;

@RestController
@RequestMapping("/")
public class RegisterController {
	
	@Autowired
	IGeneralService generalService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView displayRegister(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("register");
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String register(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String fullname = request.getParameter("fullname");
		String password = request.getParameter("password");
		Integer roleId = Integer.parseInt(request.getParameter("role"));
		EmployeeRole role;
		if (roleId == 0) {
			role = EmployeeRole.EMPLOYEE;
		} else {
			role = EmployeeRole.ADMINISTRATOR;
		}
		EmployeeAccount account = null;
		try {
			account = generalService.signUp(fullname, username, password, role);
		} catch (UsernameAlreadyExistsException e) {
			response.setStatus(400);
			return "{\"error\" : \"Username already exists!\"}";
		} catch (PasswordTooShortException e) {
			response.setStatus(400);
			return "{\"error\" : \"Password too short!\"}";
		} catch (MissingFieldsException e) {
			response.setStatus(400);
			return "{\"error\" : \"All fields are mandatory!\"}";
		}
		if (account != null) {
			return account.toString();
		} else {
			return "";
		}
	}

}
