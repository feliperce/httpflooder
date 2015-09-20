package br.com.tupinikimtecnologia.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by felipe on 20/09/15.
 */
public class TPostData {

    private Connection conn;
    private Statement stmt;

    public TPostData(Connection conn){
        this.conn = conn;
    }



    public int insertPostData(String postData, int idTarget){
        try{
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO PostData (post_data, id_target) "+"VALUES ('"+postData+"','"+idTarget+"');";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
        } catch ( Exception e ) {
            System.out.println("Erro ao inserir dados tabela POSTDATA");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
        return 1;
    }

    public boolean checkIfPostDataExists(String postData, int idTarget){
        boolean isPostData;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM PostData where post_data = '"+postData+"' AND id_target = '"+idTarget+"';";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                isPostData = true;
            }else{
                isPostData = false;
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            isPostData = false;
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
        }
        return isPostData;
    }
}
