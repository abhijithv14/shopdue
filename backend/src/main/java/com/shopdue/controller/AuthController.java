package com.shopdue.controller;
import com.shopdue.model.ShopUser;
import com.shopdue.repository.ShopUserRepository;
import com.shopdue.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final ShopUserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(ShopUserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 public record RegisterRequest(@NotBlank @Email String email,@NotBlank @Size(min=6,max=100) String password,@NotBlank String shopName){}
 public record LoginRequest(@NotBlank @Email String email,@NotBlank String password){}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req){
  String email=req.email().trim().toLowerCase(); if(users.existsByEmail(email))return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message","Email already registered"));
  var u=new ShopUser();u.setEmail(email);u.setPasswordHash(encoder.encode(req.password()));u.setShopName(req.shopName().trim());users.save(u);
  return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token",jwt.generate(email),"user",Map.of("email",email,"shopName",u.getShopName())));
 }
 @PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req){
  String email=req.email().trim().toLowerCase();var u=users.findByEmail(email).orElse(null);
  if(u==null||!encoder.matches(req.password(),u.getPasswordHash()))return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Invalid email or password"));
  return ResponseEntity.ok(Map.of("token",jwt.generate(email),"user",Map.of("email",email,"shopName",u.getShopName())));
 }
 @GetMapping("/me") public ResponseEntity<?> me(Authentication auth){
  if(auth==null)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  return users.findByEmail(auth.getName()).map(u->ResponseEntity.ok(Map.of("email",u.getEmail(),"shopName",u.getShopName()))).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
 }
}