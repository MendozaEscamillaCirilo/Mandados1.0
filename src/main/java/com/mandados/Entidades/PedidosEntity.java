package com.mandados.Entidades;
import javax.persistence.*;

@Entity
@Table(name = "Pedidos")
public class PedidosEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
    private String fecha;
    @Column
    private String hora_pedido;
    @Column
    private String hora_recoleeccion;
    @Column
    private String hora_entrega;
    @Column
    private Double total;

}
