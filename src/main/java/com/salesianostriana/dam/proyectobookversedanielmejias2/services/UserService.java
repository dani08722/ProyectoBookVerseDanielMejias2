package com.salesianostriana.dam.proyectobookversedanielmejias2.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.User;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.UserRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.base.BaseServiceImpl;

@Service
public class UserService extends BaseServiceImpl<User, Long, UserRepository> implements UserDetailsService {

	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findFirstByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
	}

}
