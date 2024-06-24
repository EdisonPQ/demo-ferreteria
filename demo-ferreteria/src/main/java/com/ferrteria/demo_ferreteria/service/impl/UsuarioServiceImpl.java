package com.ferrteria.demo_ferreteria.service.impl;

import com.ferrteria.demo_ferreteria.constantes.Constants;
import com.ferrteria.demo_ferreteria.dao.UsuarioDAO;
import com.ferrteria.demo_ferreteria.entity.Usuarios;
import com.ferrteria.demo_ferreteria.security.CustomerDetailService;
import com.ferrteria.demo_ferreteria.security.jwt.JwtUtil;
import com.ferrteria.demo_ferreteria.service.UsuarioService;
import com.ferrteria.demo_ferreteria.util.MarketUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerDetailService customerDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> SingUp(Map<String, String> requestMap) {
        log.info("Ingreso a Registrar Usuario");
        usuarioDAO.save(getUsuarioMap(requestMap));
        log.info("Termino de Registrar Usuario");
        return MarketUtils.getResponseEntity(Constants.MSG_USUARIO_CREADO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("INGRESO LOGIN");
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password")));
            if(authentication.isAuthenticated()){
                if(customerDetailService.getUsuarios().getStatus() == Constants.ACTIVO){
                    return  new ResponseEntity<String>(jwtUtil.generateToken(customerDetailService.getUsuarios().getEmail(),
                            customerDetailService.getUsuarios().getRole()),HttpStatus.OK);
                }
            }else {
                return new ResponseEntity<String>("Error en Login", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("Tu clave está mal",HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<Usuarios> obtenerTodos() {
        return usuarioDAO.findAll();
    }



    private Usuarios getUsuarioMap(Map<String, String> requestMap) {
        Usuarios usuarios = new Usuarios();
        usuarios.setNombre(requestMap.get("nombre"));
        usuarios.setNumeroContacto(requestMap.get("numeroContacto"));
        usuarios.setEmail(requestMap.get("email"));
        usuarios.setPassword(requestMap.get("password"));
        usuarios.setStatus(Constants.ACTIVO);
        usuarios.setRole(Constants.ROLE_USUARIO);

        return usuarios;
    }
}
