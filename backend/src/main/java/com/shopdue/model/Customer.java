package com.shopdue.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*; import jakarta.validation.constraints.*;
@Entity @Table(name="customers")
public class Customer {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank private String name; private String phone; private String address;
 @JsonIgnore
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="owner_id",nullable=false) private ShopUser owner;
 public Customer(){} public Customer(String name,String phone,String address){this.name=name;this.phone=phone;this.address=address;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;} public String getAddress(){return address;} public void setAddress(String v){address=v;}
 public ShopUser getOwner(){return owner;} public void setOwner(ShopUser v){owner=v;}
}