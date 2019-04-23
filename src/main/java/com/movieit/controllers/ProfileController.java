package com.movieit.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movieit.models.Favourite;
import com.movieit.repositories.FavouriteRepository;
import com.movieit.services.UserService;

//import com.chicago.models.Log;
//import com.chicago.services.UserService;

@Controller
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FavouriteRepository favouriteRepo;
	
	@GetMapping("/profile")
	public String showProfilePage(HttpSession session,Model model, Principal principal, @RequestParam(defaultValue="")  String activity) {
		//String email = principal.getName();
		//session.setAttribute("email", email);
		/*for(Log logs: userService.findByActivity(activity)) {
			if(logs.getEmail().equals(email)) {
				showLogs.add(logs);
			}
		}
		model.addAttribute("logs", showLogs);*/
		String email=(String)session.getAttribute("email");
		Favourite favourite=new Favourite();
		favourite.setUser_email(email);
		List<Integer> favouriteList=favouriteRepo.findFavourites(email);
		for( Integer favmovies : favouriteList){
			System.out.println(favmovies);
		}
		return "views/profile";
	}

}
