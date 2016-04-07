package br.com.tupinikimtecnologia.db;

import br.com.tupinikimtecnologia.constants.GeralConstants;
import br.com.tupinikimtecnologia.objects.Target;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by: Felipe Rodrigues
 * http://www.tupinikimtecnologia.com.br
 */
public class TTarget {

    private Connection conn;
    private Statement stmt;

    public TTarget(Connection conn){
        this.conn = conn;
    }
    public int insertTarget(String url){
        System.out.println("INSETT");
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

    public boolean checkIfUrlExists(String url){
        boolean isTarget;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Target where url = '"+url+"';";
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

    public List<Target> selectTargetAll(){
        List<Target> targetList = new ArrayList<Target>();
        Target target;
        try {

            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Target;";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                target = new Target();
                target.setId(rs.getInt("id"));
                target.setUrl(rs.getString("url"));
                targetList.add(target);
            }

            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return targetList;
    }

    public List<String> selectUrlAll(){
        List<String> urlList = new ArrayList<String>();
        try {

            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "SELECT url FROM Target;";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                urlList.add(rs.getString("url"));
            }

            for (String a : urlList) {
                System.out.println(a);
            }

            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return urlList;
    }

}
