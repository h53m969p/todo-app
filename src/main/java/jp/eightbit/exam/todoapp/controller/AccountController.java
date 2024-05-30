package jp.eightbit.exam.todoapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.eightbit.exam.todoapp.entity.User;
import jp.eightbit.exam.todoapp.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	
	//マイページを表示
	@GetMapping
	public String showAccountPage(Model model, Authentication authentication) {
		String username = authentication.getName();
		User user = userService.findByUsername(username);
		model.addAttribute("user", user);
		return "accountPage";
	}
	
	//マイページを更新
	@PostMapping
	public String updateAccount(@ModelAttribute User user,
								@RequestParam("iconFile") MultipartFile iconFile,
								Authentication authentication) throws IOException {
		String currentUsername = authentication.getName();
		User existingUser = userService.findByUsername(currentUsername);
		
		if (!iconFile.isEmpty()) {
			//ファイル名を「ユーザー名＋元々のファイル名」に変換する
			String filename = currentUsername + "_" + iconFile.getOriginalFilename();
			Path filePath = Paths.get("src/main/resources/static/iconimg/" + filename);
			Files.write(filePath, iconFile.getBytes());
			existingUser.setIcon("/iconimg/" + filename);
		}
		
		existingUser.setScreenname(user.getScreenname());
		userService.updateUser(existingUser);
		return "redirect:/tasks";
	}
}