package com.mandados.Entidades;
import javax.persistence.*;

@Entity
@Table(name = "Pedidos")
public class PedidosEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora_pedido() {
        return hora_pedido;
    }

    public void setHora_pedido(String hora_pedido) {
        this.hora_pedido = hora_pedido;
    }

    public String getHora_recoleeccion() {
        return hora_recoleeccion;
    }

    public void setHora_recoleeccion(String hora_recoleeccion) {
        this.hora_recoleeccion = hora_recoleeccion;
    }

    public String getHora_entrega() {
        return hora_entrega;
    }

    public void setHora_entrega(String hora_entrega) {
        this.hora_entrega = hora_entrega;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PedidosEntity [fecha=" + fecha + ", hora_entrega=" + hora_entrega + ", hora_pedido=" + hora_pedido
                + ", hora_recoleeccion=" + hora_recoleeccion + ", id=" + id + ", total=" + total + "]";
    }

}
