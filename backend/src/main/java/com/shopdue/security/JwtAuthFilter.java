package com.shopdue.security;
import com.shopdue.repository.ShopUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
 private final JwtService jwt; private final ShopUserRepository users;
 public JwtAuthFilter(JwtService jwt,ShopUserRepository users){this.jwt=jwt;this.users=users;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");
  if(h!=null&&h.startsWith("Bearer ")){try{
   String token=h.substring(7),email=jwt.extractEmail(token);
   if(SecurityContextHolder.getContext().getAuthentication()==null&&users.findByEmail(email).isPresent()&&jwt.isValid(token,email))
    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(email,null,List.of(new SimpleGrantedAuthority("ROLE_OWNER"))));
  }catch(Exception ignored){}}
  chain.doFilter(req,res);
 }
}