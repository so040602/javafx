package com.itgroup.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SuperDao {
    private String driver;
    private String url;
    private String id;
    private String password;

    public SuperDao() {
        this.driver = "oracle.jdbc.driver.OracleDriver";
        this.url = "jdbc:oracle:thin:@localhost:1521:xe";
        this.id = "gproject";
        this.password = "so040602";

        try{
            Class.forName(driver);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    protected Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, id, password);
            if(conn != null){
                //System.out.println("접속 성공");
            }else {
                System.out.println("접속 실패");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return conn;
    }
}
