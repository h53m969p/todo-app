package jp.eightbit.exam.todoapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    @Autowired
//    private UserService userService;
//
//    public SecurityConfig(UserService userService) {
//    	this.userService = userService;
//    }
//    
    //リクエストに対する認証・認可をする
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	  http.authorizeHttpRequests(authorizeRequests ->
    	  	authorizeRequests
    	  		.requestMatchers("/register", "/login").permitAll()
    	  		.anyRequest().authenticated()	//それ以外はすべて認証なしの場合アクセス不許可
		  	)
    	  	//フォームからusernameとpasswordを受け取って認証
    	  	.formLogin(login -> login	//フォーム認証する
    	  			//.loginProcessingUrl("/login")
    	  			.loginPage("/login")
    	  			//認証成功後にリダイレクトするURLを指定
    	  			.defaultSuccessUrl("/tasks")
    	  			.failureUrl("/login?error")
    	  			.permitAll()	//認証不要
    	  		)
    	  		.logout(logout -> logout
    	  				.logoutSuccessUrl("/login?logout")
    	  		);
    	  
    	  return http.build();
    }
//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}