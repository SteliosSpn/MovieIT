package com.movieit.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.movieit.models.User;
import com.movieit.models.Userinfo;
import com.movieit.repositories.UserinfoRepository;
import com.movieit.services.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserinfoRepository userinfoRepo;

	@GetMapping("/register")
	public String registerForm(Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("userinfo",new Userinfo());
		return "views/registerForm";
	}
	
	
	@PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,@Valid @ModelAttribute("userinfo") Userinfo userinfo, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "views/registerForm";
		}
		if(userService.isUserPresent(user.getEmail())) {
			model.addAttribute("exist",true);

			return "views/registerForm";

		}
		userinfo.setEmail(user.getEmail());
		userService.createUser(user);
		userinfoRepo.save(userinfo);
		
		return "views/success";

	}

}
