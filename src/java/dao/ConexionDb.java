/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Juan
 */
public class ConexionDb {

    public DriverManagerDataSource conectar() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/productos");
        dataSource.setUsername("root");
        
        dataSource.setPassword("");

        return dataSource;
    }

}
