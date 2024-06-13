package com.projeto.api.service;

import com.projeto.api.domain.Usuario;
import com.projeto.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository userRepository;
    @Override
    public Usuario create(Usuario usuario) {
        Usuario existUsuario = userRepository.findByUsername(usuario.getUsername());

        if(existUsuario != null){
            throw new Error("Usuário ja existe!");
        }

        usuario.setPassword(passwordEncoder().encode(usuario.getPassword()));
        Usuario createdUsuario = userRepository.save(usuario);

        return createdUsuario;
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
