package com.mandados.Servicios.User;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandados.Entidades.User;

@Service
public class UserService implements IUserService{

	@Autowired
	private IUser data;
	
	@Override
	public List<User> listar() {
		return (List<User>)data.findAll();
	}

	@Override
	public Optional<User> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(User p) {
		User user=data.save(p);
		if(!user.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
