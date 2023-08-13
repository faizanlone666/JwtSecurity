package com.security;

import com.security.entity.ApplicationUser;
import com.security.entity.Role;
import com.security.repository.RoleRepository;
import com.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class JwtSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;;
			Role adminRole = roleRepository.save(Role.builder().authority("ADMIN").build());
			roleRepository.save(Role.builder().authority("USER").build());

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);
			ApplicationUser admin = ApplicationUser.builder().userId(1).userName("admin").password(passwordEncoder.encode("password")).authorities(roles).build();
			userRepository.save(admin);
		};
	}

}
