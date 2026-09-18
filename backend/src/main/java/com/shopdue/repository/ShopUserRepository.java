package com.shopdue.repository;
import com.shopdue.model.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ShopUserRepository extends JpaRepository<ShopUser,Long>{
 Optional<ShopUser> findByEmail(String email);
 boolean existsByEmail(String email);
}