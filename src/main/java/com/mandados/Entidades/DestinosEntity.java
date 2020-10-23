package com.mandados.Entidades;

import javax.persistence.*;

@Entity
@Table(name = "Destinos")
public class DestinosEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
	private String nombres;
	@Column
	private String primer_apellido;
	@Column
	private String segundo_apellido;
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

}
