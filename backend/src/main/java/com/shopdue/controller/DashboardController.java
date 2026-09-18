package com.shopdue.controller;
import com.shopdue.model.*; import com.shopdue.model.Transaction.Type; import com.shopdue.repository.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.math.*; import java.time.*; import java.util.*;
@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
 private final CustomerRepository customers; private final TransactionRepository tx; private final ShopUserRepository users;
 public DashboardController(CustomerRepository c,TransactionRepository t,ShopUserRepository u){customers=c;tx=t;users=u;}
 @GetMapping public Map<String,Object> summary(Authentication a){var o=users.findByEmail(a.getName()).orElseThrow();var all=tx.findByCustomerOwner(o);BigDecimal credit=BigDecimal.ZERO,payment=BigDecimal.ZERO;long overdue=0;for(var x:all){if(x.getType()==Type.CREDIT)credit=credit.add(x.getAmount());else payment=payment.add(x.getAmount());if(x.getType()==Type.CREDIT&&x.getDueDate()!=null&&x.getDueDate().isBefore(LocalDate.now()))overdue++;}return Map.of("customers",customers.findByOwnerOrderByNameAsc(o).size(),"totalCredit",credit,"totalPaid",payment,"outstanding",credit.subtract(payment).max(BigDecimal.ZERO),"overdueTransactions",overdue);}
}