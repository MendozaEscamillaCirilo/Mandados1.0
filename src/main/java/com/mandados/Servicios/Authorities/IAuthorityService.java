package com.mandados.Servicios.Authorities;

import java.util.List;
import java.util.Optional;

import com.mandados.Entidades.Authority;

public interface IAuthorityService {
    public List<Authority>listar();
	public Optional<Authority>listarId(int d);
	public int save(Authority p);
	public void delete(int d);
}
