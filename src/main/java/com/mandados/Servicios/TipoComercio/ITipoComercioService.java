package com.mandados.Servicios.TipoComercio;

import java.util.List;
import java.util.Optional;
import com.mandados.Entidades.Tipos_comerciosEntity;

public interface ITipoComercioService {
    public List<Tipos_comerciosEntity>listar();
	public Optional<Tipos_comerciosEntity>listarId(int d);
	public int save(Tipos_comerciosEntity p);
	public void delete(int d);
}
