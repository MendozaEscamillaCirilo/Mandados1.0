package com.mandados.Servicios.Categoria;

import java.util.List;
import java.util.Optional;
import com.mandados.Entidades.CategoriasEntity;

public interface ICategoriaService {
	public List<CategoriasEntity>listar();
	public Optional<CategoriasEntity>listarId(int d);
	public int save(CategoriasEntity p);
	public void delete(int d);
}