package com.mandados.Servicios.User;


import java.util.List;

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
	public int save(User p) {
		User user=data.save(p);
		if(!user.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(Long id) {
		data.deleteById(id);
	}

}
