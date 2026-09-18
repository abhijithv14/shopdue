package com.shopdue.controller;
import com.shopdue.model.*; import com.shopdue.repository.*; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/customers")
public class CustomerController {
 private final CustomerRepository customers; private final TransactionRepository tx; private final ShopUserRepository users;
 public CustomerController(CustomerRepository c,TransactionRepository t,ShopUserRepository u){customers=c;tx=t;users=u;}
 private ShopUser owner(Authentication a){return users.findByEmail(a.getName()).orElseThrow();}
 @GetMapping public List<Customer> all(Authentication a){return customers.findByOwnerOrderByNameAsc(owner(a));}
 @PostMapping public ResponseEntity<Customer> create(@Valid @RequestBody Customer c,Authentication a){c.setOwner(owner(a));return ResponseEntity.status(HttpStatus.CREATED).body(customers.save(c));}
 @GetMapping("/{id}") public ResponseEntity<Customer> get(@PathVariable Long id,Authentication a){return customers.findByIdAndOwner(id,owner(a)).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
 @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody Customer input,Authentication a){
  var c=customers.findByIdAndOwner(id,owner(a)).orElse(null); if(c==null)return ResponseEntity.notFound().build();
  c.setName(input.getName().trim()); c.setPhone(input.getPhone()); c.setAddress(input.getAddress()); return ResponseEntity.ok(customers.save(c));
 }
 @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable Long id,Authentication a){
  var c=customers.findByIdAndOwner(id,owner(a)).orElse(null); if(c==null)return ResponseEntity.notFound().build();
  tx.deleteAll(tx.findByCustomerIdAndCustomerOwnerOrderByTransactionDateDesc(id,owner(a))); customers.delete(c); return ResponseEntity.noContent().build();
 }
 @GetMapping("/{id}/transactions") public ResponseEntity<?> transactions(@PathVariable Long id,Authentication a){var o=owner(a);if(customers.findByIdAndOwner(id,o).isEmpty())return ResponseEntity.notFound().build();return ResponseEntity.ok(tx.findByCustomerIdAndCustomerOwnerOrderByTransactionDateDesc(id,o));}
}