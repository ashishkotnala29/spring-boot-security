package com.ashish.springbootsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.springbootsecurity.Models.AuthenticationRequest;
import com.ashish.springbootsecurity.Models.AuthenticationResponse;
import com.ashish.springbootsecurity.services.MyUserDetailsService;
import com.ashish.springbootsecurity.util.JwtUtil;

@SpringBootApplication
@RestController
public class SpringBootSecurityApplication {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
	}

	@GetMapping("/")
	public String welcome() {
		return ("<h1>Welcome</h1>");
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

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		System.out.println("debug");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (AuthenticationException e) {
			System.out.println(e.getMessage());
			throw new BadCredentialsException("Incorrect username/password!");
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}

}
