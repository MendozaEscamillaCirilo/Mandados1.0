package com.mandados.Entidades;
import javax.persistence.*;
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
    private java.sql.Time hora_pedido;
    @Column
    private java.sql.Time hora_recoleeccion;
    @Column
    private java.sql.Time hora_entrega;
    @Column
    private Double total;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Pedidos_Productos",
            joinColumns = {@JoinColumn(name = "pedido_id")},
            inverseJoinColumns = {@JoinColumn(name = "producto_id")}
    )
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

    public java.sql.Time getHora_pedido() {
        return hora_pedido;
    }

    public void setHora_pedido(java.sql.Time hora_pedido) {
        this.hora_pedido = hora_pedido;
    }

    public java.sql.Time getHora_recoleeccion() {
        return hora_recoleeccion;
    }

    public void setHora_recoleeccion(java.sql.Time hora_recoleeccion) {
        this.hora_recoleeccion = hora_recoleeccion;
    }

    public java.sql.Time getHora_entrega() {
        return hora_entrega;
    }

    public void setHora_entrega(java.sql.Time hora_entrega) {
        this.hora_entrega = hora_entrega;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

	public String toString() {
		return "PedidosEntity [destino=" + destino + ", fecha=" + fecha + ", hora_entrega=" + hora_entrega
				+ ", hora_pedido=" + hora_pedido + ", hora_recoleeccion=" + hora_recoleeccion + ", id=" + id
				+ ", operador=" + operador + ", productos=" + productos + ", repartidor=" + repartidor + ", total="
				+ total + "]";
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


}
