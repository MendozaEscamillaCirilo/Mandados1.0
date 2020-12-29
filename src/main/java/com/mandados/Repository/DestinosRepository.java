package com.mandados.Repository;

import com.mandados.Entidades.DestinosEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DestinosRepository extends CrudRepository<DestinosEntity,Long>{
    
}
