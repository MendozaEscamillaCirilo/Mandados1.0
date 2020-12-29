package com.mandados.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.PedidosEntity;

@Repository
public interface PedidoRepository extends CrudRepository<PedidosEntity, Long>  {
    public List<PedidosEntity> findAll();
    // @Query("SELECT fecha,horaentrega,horapedido,horarecoleccion,total FROM comercios as c inner join productos as p on p.comercio_id = c.id inner join pedidos_productos as pp on pp.producto_id = p.id inner join pedidos as pe on pe.id = pp.pedido_id where c.nombre like %:nombre%")
    @Query(value = "SELECT fecha,horaentrega,horapedido,horarecoleccion,total FROM pedidos as pe INNER JOIN pedidos_productos as pp on pp.pedido_id = pe.id INNER JOIN productos as pr on pr.id = pp.producto_id INNER JOIN comercios as c on c.id = pr.comercio_id where c.nombre like %:nombre%", nativeQuery = true)
    public List<PedidosEntity> searchByNombreLike(@Param("nombre") String nombre);
    @Query(value = "SELECT * FROM pedidos where fecha like %:nombre%", nativeQuery = true)
    public List<PedidosEntity> searchByFecha(@Param("nombre") String nombre);
}
