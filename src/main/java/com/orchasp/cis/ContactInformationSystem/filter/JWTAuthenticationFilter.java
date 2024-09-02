package com.orchasp.cis.ContactInformationSystem.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.orchasp.cis.ContactInformationSystem.service.JWTServiceImp;
import com.orchasp.cis.ContactInformationSystem.service.UserDetailsServiceImp;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTServiceImp jwtservice;
	@Autowired
	private UserDetailsServiceImp userdetailsimp;

	public JWTAuthenticationFilter(JWTServiceImp jwtservice, UserDetailsServiceImp userdetailsimp) {
		super();
		this.jwtservice = jwtservice;
		this.userdetailsimp = userdetailsimp;
	}

	@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	final	String authHeader = request.getHeader("Authorization");
	final String token;
	final String username;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		 token = authHeader.substring(7);
	     username = jwtservice.extractUsername(token);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userdetails = userdetailsimp.loadUserByUsername(username);
			if (jwtservice.isTokenValid(token, userdetails)) {
				UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(userdetails,
						null, userdetails.getAuthorities());

				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authtoken);

			}
		}
		filterChain.doFilter(request, response);

	}

}
