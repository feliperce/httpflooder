package br.com.tupinikimtecnologia.db;

import br.com.tupinikimtecnologia.constants.GeralConstants;
import java.sql.*;

/**
 * Created by felipe on 20/09/15.
 */
public class Db {

    private Connection conn;
    private Statement stmt;

    public Connection conectDb(){
        conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:httpflooder.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        createTableTarget();
        createTablePostData();
        return conn;
    }
    
    public boolean isClosed() throws SQLException{
        return conn.isClosed();
    }

    public void closeDb() throws SQLException {
        conn.close();
    }

    public int createTableTarget() {
        try{
            stmt = conn.createStatement();
            String sql =
                    "CREATE TABLE IF NOT EXISTS 'Target' ("+
                            "'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                            "'url' TEXT NOT NULL);";
            if(GeralConstants.Debug.SQL_SHOW){
                System.out.println("--- Table CREATE ---");
                System.out.println(sql);
            }
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.out.println("Erro ao criar tabela TARGET");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
        return 1;
    }

    public int createTablePostData(){
        try{
            stmt = conn.createStatement();
            String sql =
                    "CREATE TABLE IF NOT EXISTS 'PostData' ("+
                            "'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                            "'post_data' TEXT NOT NULL,"+
                            "'id_target' INTEGER, "+
                            "FOREIGN KEY('id_target') REFERENCES Target(id));";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.out.println("Erro ao criar tabela PostData");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
        return 1;
    }

}
