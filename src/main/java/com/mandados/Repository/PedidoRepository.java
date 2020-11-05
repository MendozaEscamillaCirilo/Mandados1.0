package com.mandados.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.PedidosEntity;

@Repository
public interface PedidoRepository extends CrudRepository<PedidosEntity, Long>  {
    public List<PedidosEntity> findAll();
}
