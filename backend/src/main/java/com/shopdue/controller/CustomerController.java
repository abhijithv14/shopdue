package com.shopdue.controller;
import com.shopdue.model.*; import com.shopdue.repository.*; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/customers")
public class CustomerController {
 private final CustomerRepository customers; private final TransactionRepository tx;
 public CustomerController(CustomerRepository c,TransactionRepository t){customers=c;tx=t;}
 @GetMapping public List<Customer> all(){return customers.findAll();}
 @PostMapping public ResponseEntity<Customer> create(@Valid @RequestBody Customer c){return ResponseEntity.status(HttpStatus.CREATED).body(customers.save(c));}
 @GetMapping("/{id}") public ResponseEntity<Customer> get(@PathVariable Long id){return customers.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){if(!customers.existsById(id))return ResponseEntity.notFound().build(); customers.deleteById(id); return ResponseEntity.noContent().build();}
 @GetMapping("/{id}/transactions") public List<Transaction> transactions(@PathVariable Long id){return tx.findByCustomerIdOrderByTransactionDateDesc(id);}
}