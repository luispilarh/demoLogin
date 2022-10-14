package com.fym.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
public class UserController {
	@GetMapping("/principal")
	public Principal retrievePrincipal(Principal principal) {
		return principal;
	}


	@GetMapping("user")
	public Boolean isUser(){
		return true;
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("admin")
	public Boolean isAdmin(){
		return true;
	}
}
