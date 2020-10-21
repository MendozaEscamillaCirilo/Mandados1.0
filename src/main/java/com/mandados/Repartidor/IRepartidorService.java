package com.mandados.Repartidor;

import java.util.List;
import java.util.Optional;
import com.mandados.Entidades.Repartidor;

public interface IRepartidorService {
	public List<Repartidor>listar();
	public Optional<Repartidor>listarId(int d);
	public int save(Repartidor p);
	public void delete(int d);
}