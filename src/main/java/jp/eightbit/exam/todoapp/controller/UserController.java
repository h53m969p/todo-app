package jp.eightbit.exam.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.eightbit.exam.todoapp.entity.User;
import jp.eightbit.exam.todoapp.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	
	//ユーザー登録フォームを表示
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	//ユーザーを登録
	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user) {
		userService.registerUser(user);
		return "redirect:/login";
	}
	
	//ログインフォームを表示
	@GetMapping("/login")
	public String showLoginForm(Model model, @RequestParam(name = "error", required = false) String error) {
		if (error != null) {
			model.addAttribute("error", true);
		}
		return "login";
	}
	
	//ログアウト
	@PostMapping("/logout")
	public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		this.logoutHandler.logout(request, response, authentication);
		return "redirect:/login?logout";
	}
}