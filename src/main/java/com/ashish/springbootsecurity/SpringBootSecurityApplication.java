package com.ashish.springbootsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
	}
	
	@GetMapping("/")
	public String welcome() {
		return ("<h1>Welcome Anonymous</h1>"+
				"<br/><a href=\"/user\">user</a>"+
				"<br/><a href=\"/admin\">admin</a>");
	}

	@GetMapping("/user")
	public String user() {
		return ("<h1>Welcome User</h1>"+
				"<br/><a href=\"/logout\">logout</a>");
	}
	
	@GetMapping("/admin")
	public String admin() {
		return ("<h1>Welcome Admin</h1>"+
				"<br/><a href=\"/logout\">logout</a>");
	}
}
