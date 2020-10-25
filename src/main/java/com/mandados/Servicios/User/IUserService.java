package com.mandados.Servicios.User;

import java.util.List;
import java.util.Optional;
import com.mandados.Entidades.User;

public interface IUserService {
	public List<User>listar();
	public Optional<User>listarId(int d);
	public int save(User p);
	public void delete(int d);
}