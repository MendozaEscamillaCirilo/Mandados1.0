package com.mandados.Servicios.Repartidor;

import java.util.List;
import java.util.Optional;
import com.mandados.Entidades.RepartidoresEntity;

public interface IRepartidorService {
	public List<RepartidoresEntity>listar();
	public Optional<RepartidoresEntity>listarId(int d);
	public int save(RepartidoresEntity p);
	public void delete(int d);
}