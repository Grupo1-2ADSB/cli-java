/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author BELLA
 */
public class ConexaoBancoNuvem {
    
    private final JdbcTemplate connectionNuvem;

    public ConexaoBancoNuvem() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource​.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        dataSource​.setUrl("jdbc:sqlserver://smart-sam.database.windows.net:1433;database=bd_smart;user=admin-sam@smart-sam;password={#Gfgrupo1};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
        
        dataSource​.setUsername("admin-sam");

        dataSource​.setPassword("#Gfgrupo1");

        this.connectionNuvem = new JdbcTemplate(dataSource);

    }

    public JdbcTemplate getConnection() {
        return connectionNuvem;
    }
    
}
