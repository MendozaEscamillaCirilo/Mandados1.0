package com.mandados.Entidades;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Entity
@Table(name = "Pedidos")
public class PedidosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private java.sql.Date fecha;
    @Column
    private java.sql.Time horapedido;
    @Column
    private java.sql.Time horarecoleccion;
    @Column
    private java.sql.Time horaentrega;
    @Column
    private Double total;
    @Column
    private Double costoenvio;
    @Column
    private String estatus;
    @Column
    private String comentarios;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "Pedidos_Productos", joinColumns = { @JoinColumn(name = "pedido_id") }, inverseJoinColumns = {
            @JoinColumn(name = "producto_id") })
    private Set<ProductosEntity> productos;

    @ManyToOne()
    @JoinColumn(name = "destino_id")
    private DestinosEntity destino;

    @ManyToOne()
    @JoinColumn(name = "repartidor_id")
    private RepartidoresEntity repartidor;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User operador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public java.sql.Time getHorapedido() {
        return horapedido;
    }

    public void setHorapedido(java.sql.Time horapedido) {
        this.horapedido = horapedido;
    }

    public java.sql.Time getHorarecoleccion() {
        return horarecoleccion;
    }

    public void setHorarecoleccion(java.sql.Time horarecoleccion) {
        this.horarecoleccion = horarecoleccion;
    }

    public java.sql.Time getHoraentrega() {
        return horaentrega;
    }

    public void setHoraentrega(java.sql.Time horaentrega) {
        this.horaentrega = horaentrega;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getCostoenvio() {
        return costoenvio;
    }

    public void setCostoenvio(Double costoenvio) {
        this.costoenvio = costoenvio;
    }

    public Set<ProductosEntity> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductosEntity> productos) {
        this.productos = productos;
    }

    public DestinosEntity getDestino() {
        return destino;
    }

    public void setDestino(DestinosEntity destino) {
        this.destino = destino;
    }

    public RepartidoresEntity getRepartidores() {
        return repartidor;
    }

    public void setRepartidores(RepartidoresEntity repartidor) {
        this.repartidor = repartidor;
    }

    public RepartidoresEntity getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(RepartidoresEntity repartidor) {
        this.repartidor = repartidor;
    }

    public User getOperador() {
        return operador;
    }

    public void setOperador(User operador) {
        this.operador = operador;
    }

    public PedidosEntity(Date fecha, Time horapedido, DestinosEntity destino, User operador) {
        this.fecha = fecha;
        this.horapedido = horapedido;
        this.destino = destino;
        this.operador = operador;
    }

    public PedidosEntity() {
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}


}
