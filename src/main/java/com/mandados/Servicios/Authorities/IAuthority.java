package com.mandados.Servicios.Authorities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.Authority;

@Repository
public interface IAuthority extends CrudRepository<Authority, Integer>{

}
