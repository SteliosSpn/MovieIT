package com.movieit.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
import org.springframework.web.multipart.MultipartFile;

import com.movieit.models.Movie;
import com.movieit.services.MovieService;
import com.movieit.services.UserService;

@Controller
public class MovieController {

	@Autowired
	private UserService us;
	
	@Autowired
	private MovieService ms;
	
	private static final String path = "C:\\Users\\Geraki\\Documents\\workspace-sts-3.9.6.RELEASE\\MovieIT\\src\\main\\resources\\images\\";
	
	 @GetMapping("/addMovie")
	 public String addMovieForm(Model model, HttpSession session) {
		 model.addAttribute("movie", new Movie());
		 return "views/addMovie";
	 }
	 
	 @PostMapping("/addMovie")
	 	public String addMovieImpl(HttpSession session,@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult,
	 			Model model,@RequestParam("file") MultipartFile image) {
			if(bindingResult.hasErrors()) {
				return "views/addMovie";
			}
			try {
				movie.setImage_url(path + image.getOriginalFilename());
		        Files.write(Paths.get(movie.getImage_url()), image.getBytes());
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
			System.out.println(movie.getImage_url());
			System.out.println(image.getOriginalFilename());
			ms.createMovie(movie);
			return "views/MovieSuccess";
		}
	
	 //th:href="@{/movie(id=${movie.name})}" || th:href="@{/movie/${movie.name}}" || session
	 @GetMapping("/movie/{name}")
	 public String movieHandler(Model model, HttpSession session, @PathVariable(value="name") String movie_name) {
		//model.addAttribute("movie", new Movie());
		 System.out.println(movie_name);
		 model.addAttribute("movies", ms.findByName(movie_name));
		 return "views/movie";
	 }
}
