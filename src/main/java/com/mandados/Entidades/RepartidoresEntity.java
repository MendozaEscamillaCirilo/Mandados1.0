package com.mandados.Entidades;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Repartidores")
public class RepartidoresEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Column
	private String nombre;
	@Column
	private String primer_apellido;
	@Column
	private String segundo_apellido;
	@Column
    private String calle;
    @Column
    private String numero;
    @Column
    private String colonia;
    @Column
    private String municipio;
    @Column
    private String telefono;
    @Column
    private String email;

    @OneToMany(mappedBy = "repartidor",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PedidosEntity> pedidos;

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

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<PedidosEntity> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<PedidosEntity> pedidos) {
        this.pedidos = pedidos;
    }

	@Override
	public String toString() {
		return "RepartidoresEntity [calle=" + calle + ", colonia=" + colonia + ", email=" + email + ", id=" + id
				+ ", municipio=" + municipio + ", nombre=" + nombre + ", numero=" + numero + ", pedidos=" + pedidos
				+ ", primer_apellido=" + primer_apellido + ", segundo_apellido=" + segundo_apellido + ", telefono="
				+ telefono + "]";
	}

}
