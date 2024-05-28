package jp.eightbit.exam.todoapp.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.eightbit.exam.todoapp.entity.LoginUserDetails;
import jp.eightbit.exam.todoapp.entity.User;
import jp.eightbit.exam.todoapp.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository =  userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	//ユーザー名からユーザー情報を検索
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	//ユーザー情報を保存
	public User save(User user) {
		return userRepository.saveAndFlush(user);
	}
	
	//ユーザーの登録
	@Transactional
    public void registerUser(User user) {
    	//パスワードのハッシュ化
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	user.setUsername(user.getUsername());
    	user.setEmail(user.getEmail());
    	user.setRole("USER");
    	
    	//DBにユーザー情報を保存
    	userRepository.save(user);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Username received in loadUserByUsername method: " + username);
		Optional<User> checkuser = Optional.ofNullable(findByUsername(username));
		return checkuser.map(user -> new LoginUserDetails(user))
				.orElseThrow(() -> new UsernameNotFoundException("username not found."));	
	}
}