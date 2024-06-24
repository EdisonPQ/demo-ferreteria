package com.ferrteria.demo_ferreteria.controller;

import com.ferrteria.demo_ferreteria.constantes.Constants;
import com.ferrteria.demo_ferreteria.entity.Usuarios;
import com.ferrteria.demo_ferreteria.service.UsuarioService;
import com.ferrteria.demo_ferreteria.util.MarketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuariosController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/singup")
    public ResponseEntity<String> registrarUsuario(@RequestBody(required = true)Map<String,String> requeMap){
        try {
            return usuarioService.SingUp(requeMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return MarketUtils.getResponseEntity(Constants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String,String> requeMap){
        try {
            return usuarioService.login(requeMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return MarketUtils.getResponseEntity(Constants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/todos")
    public List<Usuarios> todosUsuarios(){
        try {
            return usuarioService.obtenerTodos();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
