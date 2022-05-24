package com.revature.thevault.utility.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.thevault.service.classes.LoginService;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	LoginService loginService;
	
	@Autowired
	public LoginSuccessHandler(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		System.out.println("IN THE SUCCESS HANDLER");
		
		ObjectMapper mapper = new ObjectMapper();
		String username = authentication.getName();
		String jsonResponse = mapper.writeValueAsString(loginService.getLoginCredentialFromLogin(username));
		
		response.setContentType("application/json");
		response.getWriter().write(jsonResponse);
		
		clearAuthenticationAttributes(request);
	}

}
