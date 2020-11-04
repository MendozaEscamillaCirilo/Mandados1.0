package com.mandados.Servicios.Authorities;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mandados.Entidades.Authority;

@Service
public class AuthorityService implements IAuthorityService{

	@Autowired
	private IAuthority data;
	
	@Override
	public List<Authority> listar() {
		return (List<Authority>)data.findAll();
	}

	@Override
	public Optional<Authority> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(Authority p) {
		Authority authority=data.save(p);
		if(!authority.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
