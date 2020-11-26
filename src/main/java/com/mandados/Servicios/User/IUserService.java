package com.mandados.Servicios.User;

import java.util.List;
import com.mandados.Entidades.User;

public interface IUserService {
	public List<User>listar();
	public int save(User p);
	public void delete(Long d);
}