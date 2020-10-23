package com.mandados.Entidades;
import javax.persistence.*;
// import java.util.Set;


@Entity
@Table(name = "Categorias")
public class CategoriasEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    
	@Column 
	private String nombree;

	@Column
    private String descripcion;
	
	// @ManyToMany
	// Set<ComerciosEntity> comercios;
    
}
