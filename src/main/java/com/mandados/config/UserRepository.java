package com.mandados.config;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.mandados.Entidades.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>  {
    // public Optional<User> findByUsername(String username);
    public Optional<User> findByUsername(String username);
}