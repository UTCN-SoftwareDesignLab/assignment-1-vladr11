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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.utcn.se.vladrusu.model.ActivityLog;
import com.utcn.se.vladrusu.services.IAdministratorService;

@RestController
@RequestMapping("/admin")
public class ActivityLogController {
	
	@Autowired
	IAdministratorService administratorService;
	
	@RequestMapping(value = "/activity-log/{employee_id}", method = RequestMethod.GET)
	public ModelAndView getPage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int employee_id) {
		String username = getSessionUsername(request.getSession());
		if (!administratorService.hasAdminPermission(username)) {
			return new ModelAndView("redirect:http://localhost:8080/assignment1/login/");
		}
		
		ModelAndView model =  new ModelAndView("activity_log", "employee_id", new Integer(employee_id));
		return model;
	}
	
	@RequestMapping(value = "/employees/{employee_id}/activity-log", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String activityLog(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int employee_id) {
		String usr = getSessionUsername(request.getSession());
		if (!administratorService.hasAdminPermission(usr)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		List<ActivityLog> activityLogs = administratorService.getActivityLogForEmployee(employee_id);
		
		String content = "[";
		for (int i = 0; i < activityLogs.size(); i++) {
			ActivityLog activityLog = activityLogs.get(i);
			content += activityLog.toString();
			if (i != activityLogs.size() - 1) {
				content += ",";
			}
		}
		content += "]";
		
		String json = "{\"count\":" + activityLogs.size() +
				",\"results\":" + content + "}";
		
		return json;
	}
	
	private String getSessionUsername(HttpSession session) {
		String username = (String)session.getAttribute("token");
		return username;
	}

}
