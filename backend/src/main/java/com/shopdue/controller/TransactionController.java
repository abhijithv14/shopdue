package com.shopdue.controller;
import com.shopdue.model.*; import com.shopdue.repository.*; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.time.LocalDate; import java.util.*;
@RestController @RequestMapping("/api/transactions")
public class TransactionController {
 private final CustomerRepository customers; private final TransactionRepository tx; private final ShopUserRepository users;
 public TransactionController(CustomerRepository c,TransactionRepository t,ShopUserRepository u){customers=c;tx=t;users=u;}
 private ShopUser owner(Authentication a){return users.findByEmail(a.getName()).orElseThrow();}
 @PostMapping public ResponseEntity<?> create(@RequestBody Map<String,Object> body,Authentication a){
  Long customerId=Long.valueOf(body.get("customerId").toString());var c=customers.findByIdAndOwner(customerId,owner(a)).orElse(null);if(c==null)return ResponseEntity.badRequest().body(Map.of("message","Customer not found"));
  var x=new Transaction();x.setCustomer(c);x.setType(Transaction.Type.valueOf(body.get("type").toString()));x.setAmount(new java.math.BigDecimal(body.get("amount").toString()));x.setNote((String)body.getOrDefault("note",""));x.setTransactionDate(LocalDate.parse((String)body.getOrDefault("transactionDate",LocalDate.now().toString())));if(body.get("dueDate")!=null&&!body.get("dueDate").toString().isBlank())x.setDueDate(LocalDate.parse(body.get("dueDate").toString()));return ResponseEntity.status(HttpStatus.CREATED).body(tx.save(x));
 }
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id,Authentication a){var x=tx.findByIdAndCustomerOwner(id,owner(a));if(x.isEmpty())return ResponseEntity.notFound().build();tx.delete(x.get());return ResponseEntity.noContent().build();}
}