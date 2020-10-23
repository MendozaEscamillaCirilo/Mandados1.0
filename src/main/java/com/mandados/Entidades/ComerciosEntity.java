package com.mandados.Entidades;

import javax.persistence.*;

// import java.io.Serializable;
// import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
// import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
// import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
// import javax.persistence.Transient;
// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.NotNull;
// import javax.validation.constraints.Size;

// import org.hibernate.annotations.GenericGenerator;

// import com.mandados.Entidades.Tipos_comerciosEntity;

@Entity
@Table(name = "Comercios")
public class ComerciosEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String nombre;

	@ManyToOne()
	@JoinColumn(name = "tipo_id")
	private Tipos_comerciosEntity tipoComercio;

	// @OneToMany(mappedBy = "comercio", fetch = FetchType.LAZY,
	// cascade = CascadeType.ALL)
	// private Set<SucursalesEntity> sucursales;

	// @ManyToMany
	// Set<CategoriasEntity> categoriasComercio;

}