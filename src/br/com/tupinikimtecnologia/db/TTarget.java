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
    public int insertTarget(String url, String method){
        try{
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO Target (url, method) "+"VALUES ('"+url+"',+'"+method+"');";
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

    public boolean checkIfTargetExists(String url, String method){
        boolean isTarget;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Target where url = '"+url+"' AND method = '"+method+"';";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                isTarget = true;
            }else{
                isTarget = false;
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            isTarget = false;
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return isTarget;
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

    public int selectIdByUrlAndMethod(String url, String method){
        int id = -1;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Target where url = '"+url+"' AND method = +'"+method+"';";
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
