package com.movieit.controllers;


import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movieit.models.Favourite;
import com.movieit.models.Movie;

import com.movieit.models.Review;

import com.movieit.models.Userinfo;
import com.movieit.repositories.FavouriteRepository;
import com.movieit.repositories.MovieRepository;
import com.movieit.repositories.ReviewRepository;
import com.movieit.repositories.UserRepository;
import com.movieit.repositories.UserinfoRepository;
import com.movieit.services.FavouriteService;

import com.movieit.services.ReviewService;
import com.movieit.services.UserService;



@Controller
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FavouriteRepository favouriteRepo;
	
	@Autowired 
	private MovieRepository movieRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private UserinfoRepository userinfoRepo;
	
	@Autowired
	private FavouriteService favouriteService;
	
	@Autowired
	private ReviewService rs;
	
	@GetMapping("/profile")
	public String showProfilePage(HttpSession session,Model profilemodel, Principal principal, 
			@RequestParam(defaultValue="")  String activity) {
		
		profilemodel.addAttribute("movie", new Movie());
		String email=(String)session.getAttribute("email");
		//favourites...
		profilemodel.addAttribute("profilemovies", favouriteService.showFavouriteMovies(email));
		
		Userinfo userinfo= new Userinfo();
		Optional <Userinfo> userinfolist = userinfoRepo.findById(email);
		if(userinfolist.isPresent()){
			userinfo=userinfolist.get();
		}
		profilemodel.addAttribute("userinfo",userinfo);
		
		//reviews... 
		profilemodel.addAttribute("reviews",rs.showReviews(email));
		
		
		return "views/profile";
	}
	
	@PostMapping("/removeFromFavouritesPR")
 	public String removeFromFavourites(HttpSession session,@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult,
 			Model profilemodel) {
		if(bindingResult.hasErrors()) {
			return "redirect:/profile";
		}
		
		String email = (String) session.getAttribute("email");
		Favourite favourite = new Favourite();
		favourite.setMovie_id(movie.getMovie_id());
		favourite.setUser_email(email);
		System.out.println(movie.getMovie_id());
		favouriteRepo.delete(favourite);

		return "redirect:/profile";
	
	}
	
	@PostMapping("/editProfile")
 	public String editProfile(HttpSession session,@Valid @ModelAttribute("userinfo") Userinfo userinfo,BindingResult bindingResult, Model profilemodel) {
		if(bindingResult.hasErrors()) {
			//return "redirect:/profile";
		}
		String email = (String) session.getAttribute("email");
		Userinfo dbuser =new Userinfo();
		Optional <Userinfo> userlist = userinfoRepo.findById(email);
		if(userlist.isPresent()){
			dbuser=userlist.get();
		}
		userinfo.setEmail(email);
	    userinfo.setUsername(dbuser.getUsername());
		
		userinfoRepo.save(userinfo);
			return "redirect:/profile";
	}
	
	@GetMapping("/user/{name}")
	 public String userHandler(Model profilemodel, HttpSession session, @PathVariable(value="name") String username) {
		
		
		profilemodel.addAttribute("movie", new Movie());

		List<Movie> showMovies = new ArrayList<Movie>();
		String email=userinfoRepo.getEmail(username);
		Favourite favourite=new Favourite();
		favourite.setUser_email(email);
		List<Integer> favouriteList=favouriteRepo.findFavourites(email);
		for( Integer favmovies : favouriteList){
			
			Optional<Movie> tempMovie = movieRepo.findById(favmovies);
			if(tempMovie.isPresent()){
				Movie movie = tempMovie.get();
				movie.setImage_url(movie.getImage_url().replace(".\\src\\main\\resources\\static\\images\\", "/images/"));
				showMovies.add(movie);
				}
		}
		profilemodel.addAttribute("profilemovies", showMovies);
		

		
		Userinfo userinfo= new Userinfo();
		Optional <Userinfo> userinfolist = userinfoRepo.findById(email);
		if(userinfolist.isPresent()){
			userinfo=userinfolist.get();
		}
		profilemodel.addAttribute("userinfo",userinfo);
		
		 List<Object[]> reviewList=reviewRepo.findReviewsforUser(email);
		 List<Review> finalReviewList=new ArrayList<Review>();
		 for(Object[] review1:reviewList){
			 String review_body = (String)review1[0];
			 
			 Date review_date = (Date)review1[1];
			
			 Integer movie_id = (Integer)review1[2];
			
			 Review review = new Review();
			 review.setMovie_id(movie_id);
			 review.setReview_body(review_body);
			 review.setReview_date(review_date);
			 
			 review.setUser_email(email);
			 finalReviewList.add(review);
		 }
		
		 List<Movie> reviewListforMovies=new ArrayList<Movie>();
		for(Review review:finalReviewList){
			
			Optional<Movie> tempMovie = movieRepo.findById(review.getMovie_id());
			if(tempMovie.isPresent()){
		
				Movie movie = tempMovie.get();
				movie.setDescription(review.getReview_body());
				movie.setImage_url(movie.getImage_url().replace(".\\src\\main\\resources\\static\\images\\", "/images/"));
				reviewListforMovies.add(movie);
				}
		}
		 
		profilemodel.addAttribute("reviews",reviewListforMovies);
		
		profilemodel.addAttribute("pagetitle", username+"'s Profile");
		
		
		 return "views/visitprofile";
	 }
}