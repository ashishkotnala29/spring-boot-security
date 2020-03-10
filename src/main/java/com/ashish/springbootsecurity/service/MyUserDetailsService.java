package com.ashish.springbootsecurity.service;

import com.ashish.springbootsecurity.models.MyUserDetails;
import com.ashish.springbootsecurity.models.Users;
import com.ashish.springbootsecurity.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepo userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Users> user = userRepository.findByUserName(userName);
		user.orElseThrow(()->
			new UsernameNotFoundException("User not found: "+ userName)
		);
		
		return user.map(MyUserDetails::new).get();
	}

}
