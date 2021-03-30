package com.codingdojo.BeltExam.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.BeltExam.models.Idea;
import com.codingdojo.BeltExam.models.User;
import com.codingdojo.BeltExam.services.IdeaService;
import com.codingdojo.BeltExam.services.UserService;
import com.codingdojo.BeltExam.validation.UserValidation;


@Controller
public class ExamController {
	@Autowired
	private UserService uservice;
	@Autowired
	private UserValidation uvalidation;
	@Autowired
	private IdeaService iservice;
	
	@RequestMapping("/")
	public String index(@ModelAttribute("user")User user) {
		return "loginreg.jsp";
	}
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		uvalidation.validate(user, result);
		if (result.hasErrors()) {
			return "loginreg.jsp";
		}
		User newUser = uservice.registerUser(user);
		session.setAttribute("user_id", newUser.getId());
		return "redirect:/ideas";
	}
	 @RequestMapping(value="/login", method=RequestMethod.POST)
	 public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirs) {
	     // if the user is authenticated, save their user id in session
		 boolean isAuthenticated = uservice.authenticateUser(email, password);
		 if(isAuthenticated) {
			 User u = uservice.findUserbyEmail(email);
			 session.setAttribute("user_id", u.getId());
			 return "redirect:/ideas";
		 }
	     // else, add error messages and return the login page
		 else {
			 redirs.addFlashAttribute("error", "Invalid Email/Password");
			 return "redirect:/";
		 }
	 }
	 @RequestMapping("/logout")
	 public String logout(HttpSession session) {
	     // invalidate session
		 session.invalidate();
	     // redirect to login page
		 return "redirect:/";
	 }
	 @RequestMapping("/ideas")
	 public String homePage(@ModelAttribute("idea") Idea idea, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 model.addAttribute("ideas", iservice.findIdeas());
		 return "ideas.jsp";
	 }
	 @RequestMapping("/ideas/new")
	 public String newIdea(@ModelAttribute("idea") Idea idea, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 return "new.jsp";
	 }
	 @PostMapping(value="/ideas/new")
	 public String CreateIdea(@Valid @ModelAttribute("idea") Idea idea, BindingResult result, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (result.hasErrors()) {
			 return "redirect:/new";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 iservice.createIdea(idea);
		 return "redirect:/ideas";
	 }
	 @RequestMapping("/ideas/{id}")
	 public String show(@PathVariable("id") Long id, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 model.addAttribute("idea", iservice.findbyId(id));
		 return "show.jsp";
	 }
	 @RequestMapping("/ideas/edit/{id}")
	 public String editIdea(@PathVariable("id") Long id, HttpSession session, Model model) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		User user = uservice.findUserbyId(user_id);
		Idea idea = iservice.findbyId(id);
		if (idea == null || user != idea.getIdeamaker()) {
			return "redirect:/ideas";
		}
		model.addAttribute("user", user);
		model.addAttribute("idea", idea);
		return "edit.jsp";
	 }
	 @RequestMapping(value="/ideas/edit/{id}", method=RequestMethod.POST)
	 public String editIdea2(@Valid @ModelAttribute("idea") Idea idea, BindingResult result, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (result.hasErrors()) {
			 return "redirect:/ideas/edit/{id}";
		 }
		User user = uservice.findUserbyId(user_id);
		model.addAttribute("user", user);
		model.addAttribute("idea", idea);
		iservice.updateIdea(idea);
		return "redirect:/ideas";
	 }
	@RequestMapping("/ideas/delete/{id}")
	public String Delete(@PathVariable("id") Long id, HttpSession session, Model model) {
		this.iservice.delete(id);
		return "redirect:/ideas";
	}
	@PostMapping("/{id}/a/like")
	public String like(@PathVariable("id") Long id, HttpSession session) {
		Long user_id = (Long)session.getAttribute("user_id");
		//get event
		Idea idea = iservice.findbyId(id);
		//get attendees
		List<User> likers = idea.getLikers();
		//set attendee
		User liker = uservice.findUserbyId(user_id);
		likers.add(liker);
		//update event with attendee
		iservice.updateIdea(idea);
		return "redirect:/ideas";
	}
	@PostMapping("/{id}/a/unlike")
	public String unLike(@PathVariable("id") Long id, HttpSession session) {
		Long user_id = (Long)session.getAttribute("user_id");
		//get event
		Idea idea = iservice.findbyId(id);
		//get attendees
		List<User> likers = idea.getLikers();
		//set attendee
		User liker = uservice.findUserbyId(user_id);
		likers.remove(liker);
		//update event with attendee
		iservice.updateIdea(idea);
		return "redirect:/ideas";
	}
}
