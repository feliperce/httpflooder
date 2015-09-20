package br.com.tupinikimtecnologia.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by felipe on 20/09/15.
 */
public class TTarget {

    private Connection conn;
    private Statement stmt;

    public TTarget(Connection conn){
        this.conn = conn;
    }
    public int insertUrl(String url){
        try{
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO Target (url) "+"VALUES ('"+url+"');";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
        } catch ( Exception e ) {
            System.out.println("Erro ao inserir dados tabela TARGET");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    return 1;
    }

    public void selectTargetAll(){

    }

    public boolean checkIfUrlExists(String url){
        boolean isUrl;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "SELECT url FROM Target where url = '"+url+"';";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                isUrl = true;
            }else{
                isUrl = false;
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            isUrl = false;
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return isUrl;
    }

    public int selectIdByUrl(String url){
        int id = -1;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Target where url = '"+url+"';";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if(rs.getString("url").equals(url)){
                    id = rs.getInt("id");
                    return id;
                }
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {

            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return id;
    }

}
