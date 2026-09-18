package com.shopdue.repository;
import com.shopdue.model.Transaction; import com.shopdue.model.ShopUser; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface TransactionRepository extends JpaRepository<Transaction,Long>{
 List<Transaction> findByCustomerIdAndCustomerOwnerOrderByTransactionDateDesc(Long customerId,ShopUser owner);
 List<Transaction> findByCustomerOwner(ShopUser owner);
 Optional<Transaction> findByIdAndCustomerOwner(Long id,ShopUser owner);
}