package com.mandados.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long>  {
    public List<Authority> findAll();
}