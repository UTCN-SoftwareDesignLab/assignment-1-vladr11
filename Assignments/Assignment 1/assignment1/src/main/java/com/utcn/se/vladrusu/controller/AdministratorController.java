package com.utcn.se.vladrusu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utcn.se.vladrusu.exceptions.MissingFieldsException;
import com.utcn.se.vladrusu.exceptions.PasswordTooShortException;
import com.utcn.se.vladrusu.exceptions.UsernameAlreadyExistsException;
import com.utcn.se.vladrusu.model.EmployeeAccount;
import com.utcn.se.vladrusu.model.EmployeeRole;
import com.utcn.se.vladrusu.services.IAdministratorService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin")
public class AdministratorController {
	
	@Autowired
	IAdministratorService administratorService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView adminPanel(HttpServletRequest request, HttpServletResponse response) {
		String username = getSessionUsername(request.getSession());
		if (!administratorService.hasAdminPermission(username)) {
			return new ModelAndView("redirect:http://localhost:8080/assignment1/login/");
		}
		ModelAndView model = new ModelAndView("admin");
		return model;
	}
	
	@RequestMapping(value = "/employees", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String getEmployees(HttpServletRequest request, HttpServletResponse response) {
		String username = getSessionUsername(request.getSession());
		if (!administratorService.hasAdminPermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		List<EmployeeAccount> employees = administratorService.getAllEmployees();
		String content = "[";
		for (int i = 0; i < employees.size(); i++) {
			EmployeeAccount account = employees.get(i);
			content += account.toString();
			if (i != employees.size() - 1) {
				content += ",";
			}
		}
		content += "]";
		String json = "{\"count\":" + Integer.toString(employees.size()) +
				", \"results\":" + content + "}";
		return json;
	}
	
	@RequestMapping(value = "/employees", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String addEmployee(HttpServletRequest request, HttpServletResponse response) {
		String usr = getSessionUsername(request.getSession());
		if (!administratorService.hasAdminPermission(usr)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		String username = request.getParameter("username");
		String fullname = request.getParameter("fullname");
		String password = request.getParameter("password");
		EmployeeRole role;
		if (Integer.parseInt(request.getParameter("role")) == 0) {
			role = EmployeeRole.EMPLOYEE;
		} else {
			role = EmployeeRole.ADMINISTRATOR;
		}
		EmployeeAccount account = null;
		try {
			account = administratorService.addEmployee(fullname, username, password, role);
		} catch (UsernameAlreadyExistsException e) {
			response.setStatus(400);
			return "{\"error\":\"Employee already exists!\"}";
		} catch (PasswordTooShortException e) {
			response.setStatus(400);
			return "{\"error\":\"Password too short!\"}";
		} catch (MissingFieldsException e) {
			response.setStatus(400);
			return "{\"error\":\"All fields are mandatory!\"}";
		}
		if (account != null) {
			return account.toString();
		} else {
			return "[]";
		}
	}
	
	@RequestMapping(value = "/employees/{employee_id}", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String editEmployee(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int employee_id) {
		String usr = getSessionUsername(request.getSession());
		if (!administratorService.hasAdminPermission(usr)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		String username = request.getParameter("username");
		String fullname = request.getParameter("fullname");
		String password = request.getParameter("password");
		System.out.println(username + fullname + password);
		EmployeeRole role;
		if (Integer.parseInt(request.getParameter("role")) == 0) {
			role = EmployeeRole.EMPLOYEE;
		} else {
			role = EmployeeRole.ADMINISTRATOR;
		}
		
		try {
			administratorService.editEmployee(employee_id, fullname, username, password, role);
		} catch (MissingFieldsException e) {
			response.setStatus(400);
			return "{\"error\":\"All fields are required!\"}";
		}
		
		EmployeeAccount account = administratorService.getEmployeeById(employee_id);
		
		if (account != null) {
			return account.toString();
		} else {
			return "";
		}
	}
	
	@RequestMapping(value = "/employees/{employee_id}", method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteEmployee(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int employee_id) {
		String usr = getSessionUsername(request.getSession());
		if (!administratorService.hasAdminPermission(usr)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		boolean status = administratorService.deleteEmployee(employee_id);
		if (status) {
			return "{\"success\":1}";
		} else {
			response.setStatus(404);
			return "{\"error\":\"Employee not found\"}";
		}
	}
	
	private String getSessionUsername(HttpSession session) {
		String username = (String)session.getAttribute("token");
		return username;
	}

}
