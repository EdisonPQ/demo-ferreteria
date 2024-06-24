package com.ferrteria.demo_ferreteria.service;

import com.ferrteria.demo_ferreteria.entity.Usuarios;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UsuarioService {
    ResponseEntity<String> SingUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

    List<Usuarios> obtenerTodos();


}
