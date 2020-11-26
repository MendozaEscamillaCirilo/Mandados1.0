package com.mandados.Servicios.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.User;

@Repository
public interface IUser extends CrudRepository<User, Long>{

}
