package com.mandados.Servicios.Comercio;

import java.util.List;
import java.util.Optional;
import com.mandados.Entidades.ComerciosEntity;

public interface IComercioService {
	public List<ComerciosEntity>listar();
	public Optional<ComerciosEntity>listarId(int d);
	public int save(ComerciosEntity p);
	public void delete(int d);
}