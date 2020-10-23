package com.mandados.Restaurante;

import java.util.List;
import java.util.Optional;
import com.mandados.Entidades.Restaurante;

public interface IRestauranteService {
	public List<Restaurante>listar();
	public Optional<Restaurante>listarId(int d);
	public int save(Restaurante p);
	public void delete(int d);
}