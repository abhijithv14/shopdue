package com.shopdue.repository;
import com.shopdue.model.Customer; import com.shopdue.model.ShopUser; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface CustomerRepository extends JpaRepository<Customer,Long>{
 List<Customer> findByOwnerOrderByNameAsc(ShopUser owner);
 Optional<Customer> findByIdAndOwner(Long id,ShopUser owner);
}