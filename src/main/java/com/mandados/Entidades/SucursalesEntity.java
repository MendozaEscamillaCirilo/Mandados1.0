package com.mandados.Entidades;

import javax.persistence.*;

@Entity
@Table(name = "Sucursales")
public class SucursalesEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String codigopostal;
    @Column
    private String nombre;
	@Column
    private Double calle;
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

    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "id", nullable = false)
    // private ComerciosEntity comercio;
    
}
