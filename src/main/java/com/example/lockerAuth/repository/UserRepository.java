package com.example.lockerAuth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.lockerAuth.entity.Usuarios;

@Repository
public interface UserRepository extends JpaRepository<Usuarios, Integer> {
    @Query(value = "SELECT u.* FROM usuarios u WHERE u.u_nombre=?1", nativeQuery = true)
    Optional<Usuarios> findByU_nombre(String u_nombre);

}
