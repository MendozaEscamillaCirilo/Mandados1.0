package com.mandados.Servicios.Sucursal;

import java.util.List;
import java.util.Optional;
import com.mandados.Entidades.SucursalesEntity;

public interface ISucursalService {
	public List<SucursalesEntity>listar();
	public Optional<SucursalesEntity>listarId(int d);
	public int save(SucursalesEntity p);
	public void delete(int d);
}