package com.shopdue.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name="shop_users", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class ShopUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Email @Column(nullable=false,unique=true) private String email;
 @NotBlank @Column(nullable=false) private String passwordHash;
 @NotBlank @Column(nullable=false) private String shopName;
 @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
 public ShopUser(){}
 public Long getId(){return id;} public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public String getShopName(){return shopName;} public void setShopName(String v){shopName=v;}
 public LocalDateTime getCreatedAt(){return createdAt;}
}