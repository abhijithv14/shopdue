package com.shopdue.model;
import jakarta.persistence.*; import java.math.BigDecimal; import java.time.LocalDate;
@Entity @Table(name="transactions")
public class Transaction {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Customer customer;
 @Enumerated(EnumType.STRING) private Type type;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal amount;
 private String note; private LocalDate transactionDate; private LocalDate dueDate;
 public enum Type { CREDIT,PAYMENT }
 public Transaction(){} public Long getId(){return id;} public Customer getCustomer(){return customer;} public void setCustomer(Customer v){customer=v;}
 public Type getType(){return type;} public void setType(Type v){type=v;} public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
 public String getNote(){return note;} public void setNote(String v){note=v;} public LocalDate getTransactionDate(){return transactionDate;} public void setTransactionDate(LocalDate v){transactionDate=v;}
 public LocalDate getDueDate(){return dueDate;} public void setDueDate(LocalDate v){dueDate=v;}
}