package com.mandados.Entidades;

public class ProductosParaPedidosSinComercio {
	private String producto;
	private String presentacion;
    private String comentario;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    @Override
    public String toString() {
        return "ProductosParaPedidosSinComercio [comentario=" + comentario + ", presentacion=" + presentacion
                + ", producto=" + producto + "]";
    }

    public ProductosParaPedidosSinComercio(String producto, String presentacion, String comentario) {
        this.producto = producto;
        this.presentacion = presentacion;
        this.comentario = comentario;
    }
    
}