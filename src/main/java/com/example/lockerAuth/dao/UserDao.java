package com.example.lockerAuth.dao;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class UserDao {
    private Integer id_usuario;
    private String u_name;
    private String u_password;
    private String email;
}
