package com.movieit.controllers;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
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
import org.springframework.web.multipart.MultipartFile;

import com.movieit.models.Favourite;
import com.movieit.models.Movie;
import com.movieit.models.Rating;
import com.movieit.models.Review;
import com.movieit.repositories.FavouriteRepository;
import com.movieit.repositories.MovieRepository;
import com.movieit.repositories.RatingRepository;
import com.movieit.repositories.ReviewRepository;
import com.movieit.repositories.TagRepository;
import com.movieit.repositories.UserinfoRepository;
import com.movieit.services.FavouriteService;
import com.movieit.services.MovieService;
import com.movieit.services.RatingService;
import com.movieit.services.UserService;

@Controller
public class MovieController {

	@Autowired
	private UserService us;
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private MovieRepository movierepo;
	
	@Autowired
	private RatingRepository ratingRepo;
	
	@Autowired
	private FavouriteService favouriteService;
	
	@Autowired
	private FavouriteRepository favouriteRepo;
	
	@Autowired
	private MovieService ms;
	
	@Autowired
	private TagRepository tagRepo;
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private UserinfoRepository userinfoRepo;
	
	private static final String path = ".\\src\\main\\resources\\static\\images\\";
	
	 @GetMapping("/addMovie")
	 public String addMovieForm(Model model, HttpSession session) {
		 model.addAttribute("movie", new Movie());
		 return "views/addMovie";
	 }
	 
	 @PostMapping("/addMovie")
	 	public String addMovieImpl(HttpSession session,@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult,
	 			Model model,@RequestParam("file") MultipartFile image, @RequestParam(value = "action_tag", defaultValue = "off") String action,
	 			@RequestParam(value = "comedy_tag", defaultValue = "off") String comedy,
	 			@RequestParam(value = "drama_tag", defaultValue = "off") String drama,
	 			@RequestParam(value = "romantic_tag", defaultValue = "off") String romantic,
	 			@RequestParam(value = "horror_tag", defaultValue = "off") String horror,
	 			@RequestParam(value = "crime_tag", defaultValue = "off") String crime,
	 			@RequestParam(value = "adventure_tag", defaultValue = "off") String adventure,
	 			@RequestParam(value = "animation_tag", defaultValue = "off") String animation,
	 			@RequestParam(value = "scifi_tag", defaultValue = "off") String scifi,
	 			@RequestParam(value = "thriller_tag", defaultValue = "off") String thriller,
	 			@RequestParam(value = "mystery_tag", defaultValue = "off") String mystery) {
			if(bindingResult.hasErrors()) {
				return "views/addMovie";
			}
			try {
				movie.setImage_url(path + image.getOriginalFilename());
		        Files.write(Paths.get(movie.getImage_url()), image.getBytes());
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
			
			System.out.println(action);
			System.out.println(comedy);
			ms.createMovie(movie,action,comedy,drama,romantic,horror,crime,adventure,animation,scifi,thriller,mystery);
			return "views/MovieSuccess";
		}
	
	 
	 @GetMapping("/movie/{name}")
	 public String movieHandler(Model model, HttpSession session, @PathVariable(value="name") String movie_name) {
		
		 model.addAttribute("movie", new Movie());
		 double score = 0.0;
		 boolean isRated = false;
		 boolean isFavourite = false;
		
		 Movie myMovie = ms.findByName(movie_name);
		 if(myMovie.getTotal_votes() != 0) {
			
			 score = (double)myMovie.getTotal_score()/(double)myMovie.getTotal_votes();
			
			 String scores = null;
			 DecimalFormat df = new DecimalFormat();
			 df.setRoundingMode(RoundingMode.DOWN);
			 scores=df.format(score);
			 model.addAttribute("rating", scores);
		 }
		 else{
			model.addAttribute("rating","No Rating yet"); 
		 }
		 String email = (String) session.getAttribute("email");
		 
		 Rating rating = new Rating();
		 rating.setUser_email(email);
		 rating.setMovie_id(myMovie.getMovie_id());
		 isRated=ratingService.findIfAlreadyRated(rating);
		 if(isRated)model.addAttribute("isRated",true);
		 else model.addAttribute("isRated",false);
		 
		 Favourite favourite=new Favourite();
		 favourite.setUser_email(email);
		 favourite.setMovie_id(myMovie.getMovie_id());
		 isFavourite=favouriteService.findIfFavourite(favourite);
		 if(isFavourite)model.addAttribute("isFavourite",true);
		 else model.addAttribute("isFavourite",false);
		 
		 
		 
		 List<Object[]> reviewList=reviewRepo.findReviewsforMovie(myMovie.getMovie_id());
		 List<Review> finalReviewList=new ArrayList<Review>();
		 for(Object[] review1:reviewList){
			 String review_body = (String)review1[0];
			 
			 Date review_date = (Date)review1[1];
			
			 String user_email = (String)review1[2];
			
			 Review review = new Review();
			 review.setMovie_id(myMovie.getMovie_id());
			 review.setReview_body(review_body);
			 review.setReview_date(review_date);
			 
			 review.setUser_email(userinfoRepo.getUsername(user_email));
			 finalReviewList.add(review);
		 }
			
			
				
		myMovie.setImage_url(myMovie.getImage_url().replace(".\\src\\main\\resources\\static\\images\\", "/images/"));
			
			//tags...
			model.addAttribute("tags", tagRepo.findTags(myMovie.getMovie_id()));
			//related movies
			
			
			model.addAttribute("relatedMovies", ms.findRelatedMovies(tagRepo.findTags(myMovie.getMovie_id()),myMovie.getMovie_id()));
			
		 model.addAttribute("movies", myMovie);
		 model.addAttribute("reviews", finalReviewList);
		 model.addAttribute("review",new Review());
		 session.setAttribute("movie", myMovie);
		 return "views/movie";
	 }
	 
	 @PostMapping("/rate")
	 	public String movieRating(HttpSession session,@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult,
	 			Model model) {
			if(bindingResult.hasErrors()) {
				return "views/movie";
			}
			Movie myMovie = (Movie) session.getAttribute("movie");
			
			String email = (String) session.getAttribute("email");
			Rating rating = new Rating();
			rating.setMovie_id(myMovie.getMovie_id());
			rating.setUser_email(email);
			
			ratingRepo.save(rating);
			
			myMovie.setTotal_score(movie.getTotal_score()+myMovie.getTotal_score());
			myMovie.setTotal_votes(myMovie.getTotal_votes()+1);
			movierepo.save(myMovie);
			
			return "redirect:/movie/"+myMovie.getName();
		
		}
	 
	 @PostMapping("/addToFavourites")
	 	public String addToFavourites(HttpSession session,@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult,
	 			Model model) {
			if(bindingResult.hasErrors()) {
				return "views/movie";
			}
			Movie myMovie = (Movie) session.getAttribute("movie");
			
			String email = (String) session.getAttribute("email");
			Favourite favourite = new Favourite();
			favourite.setMovie_id(myMovie.getMovie_id());
			favourite.setUser_email(email);
			
			favouriteRepo.save(favourite);

			return "redirect:/movie/"+myMovie.getName();
	
		}
	 
	 @PostMapping("/removeFromFavourites")
	 	public String removeFromFavourites(HttpSession session,@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult,
	 			Model model) {
			if(bindingResult.hasErrors()) {
				return "views/movie";
			}
			Movie myMovie = (Movie) session.getAttribute("movie");
			
			String email = (String) session.getAttribute("email");
			Favourite favourite = new Favourite();
			favourite.setMovie_id(myMovie.getMovie_id());
			favourite.setUser_email(email);
			
			favouriteRepo.delete(favourite);

			return "redirect:/movie/"+myMovie.getName();
			
		}
	 
	 @PersistenceContext
	 private EntityManager manager;
	    
	 @Transactional
		@PostMapping("/addReview")
	 	public String addReview(HttpSession session,@Valid @ModelAttribute("review") Review review,BindingResult bindingResult, Model model) {
			if(bindingResult.hasErrors()) {
				//return "redirect:/profile";
			}
			Movie myMovie = (Movie) session.getAttribute("movie");
			String email = (String) session.getAttribute("email");
			review.setMovie_id(myMovie.getMovie_id());
			review.setUser_email(email);
			review.setReview_date(new Date(System.currentTimeMillis()));
			
			List<Review> ReviewList=new ArrayList<Review>();
			ReviewList=reviewRepo.findifReviewed(review.getMovie_id(), email);
			if(ReviewList.isEmpty())reviewRepo.save(review);
			else reviewRepo.updateReview(review.getMovie_id(),email,review.getReview_body(),review.getReview_date());
			
			return "redirect:/movie/"+myMovie.getName();
		}
	 
}