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
    private java.sql.Time horaPedido;
    @Column
    private java.sql.Time horaRecoleccion;
    @Column
    private java.sql.Time horaEntrega;
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

    public java.sql.Time getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(java.sql.Time horaPedido) {
        this.horaPedido = horaPedido;
    }

    public java.sql.Time getHoraRecoleccion() {
        return horaRecoleccion;
    }

    public void setHoraRecoleccion(java.sql.Time horaRecoleccion) {
        this.horaRecoleccion = horaRecoleccion;
    }

    public java.sql.Time getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(java.sql.Time horaEntrega) {
        this.horaEntrega = horaEntrega;
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
		return "PedidosEntity [destino=" + destino + ", fecha=" + fecha + ", horaEntrega=" + horaEntrega
				+ ", horaPedido=" + horaPedido + ", horaRecoleccion=" + horaRecoleccion + ", id=" + id
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
