package com.mandados.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.mandados.Entidades.ComerciosEntity;

@Repository
public interface ComercioRepository extends CrudRepository<ComerciosEntity, Long> {
    public List<ComerciosEntity> findAll();
    public Optional<ComerciosEntity> findById(Long id);
    public ComerciosEntity findByNombre(String nombre);
    public ComerciosEntity findByEmail(String email);
    public ComerciosEntity findByEmailAndEstatus(String email,boolean estatus);
}