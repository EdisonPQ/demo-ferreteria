package com.ferrteria.demo_ferreteria.dao;

import com.ferrteria.demo_ferreteria.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuarios, Integer> {
    Usuarios findByEmail(@Param("email") String email);
}
