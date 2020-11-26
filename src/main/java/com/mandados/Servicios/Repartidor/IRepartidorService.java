package com.mandados.Servicios.Repartidor;

import java.util.List;
import com.mandados.Entidades.RepartidoresEntity;

public interface IRepartidorService {
	public List<RepartidoresEntity>listar();
	public int save(RepartidoresEntity p);
	public void delete(Long d);
}