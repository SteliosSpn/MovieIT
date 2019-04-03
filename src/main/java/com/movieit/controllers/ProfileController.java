package com.movieit.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.chicago.models.Log;
//import com.chicago.services.UserService;

@Controller
public class ProfileController {
	
	/*@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public String showProfilePage(HttpSession session,Model model, Principal principal, @RequestParam(defaultValue="")  String activity) {
		List<Log> showLogs = new ArrayList<Log>();
		String email = principal.getName();
		session.setAttribute("email", email);
		for(Log logs: userService.findByActivity(activity)) {
			if(logs.getEmail().equals(email)) {
				showLogs.add(logs);
			}
		}
		model.addAttribute("logs", showLogs);
		return "views/profile";
	}*/

}
