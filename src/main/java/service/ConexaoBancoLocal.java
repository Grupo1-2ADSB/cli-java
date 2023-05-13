/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Leo
 */
public class ConexaoBancoLocal {

    private final JdbcTemplate connectionLocal;

    public ConexaoBancoLocal() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource​.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource​.setUrl("jdbc:mysql://localhost:3306/sam?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        
        dataSource​.setUsername("root");

        dataSource​.setPassword("#Gf53930390850");

        this.connectionLocal = new JdbcTemplate(dataSource);

    }

    public JdbcTemplate getConnection() {
        return connectionLocal;
    }

}
