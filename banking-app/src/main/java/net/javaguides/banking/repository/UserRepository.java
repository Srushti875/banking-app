package net.javaguides.banking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import net.javaguides.banking.entity.User;


public interface  UserRepository extends JpaRepository<User, Long> 
{


        Optional<User> findByUsername(String username);
  
    
}
