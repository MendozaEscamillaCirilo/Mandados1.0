package com.mandados.Entidades;

public class ProductosParaPedidos {
	private Long id;
	private String nombre;
	private Double precio;
	private Double total;
    private ComerciosEntity comercio;
    private int cantidad;
    private String comentario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ComerciosEntity getComercio() {
        return comercio;
    }

    public void setComercio(ComerciosEntity comercio) {
        this.comercio = comercio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ProductosParaPedidos(Long id, String nombre, Double precio, Double total, ComerciosEntity comercio,
            int cantidad,String comentario) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.total = total;
        this.comercio = comercio;
        this.cantidad = cantidad;
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "ProductosParaPedidos [cantidad=" + cantidad + ", comercio=" + comercio + ", id=" + id + ", nombre="
                + nombre + ", precio=" + precio + ", total=" + total + "]";
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
}