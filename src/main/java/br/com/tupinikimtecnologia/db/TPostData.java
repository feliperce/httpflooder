package br.com.tupinikimtecnologia.db;

import br.com.tupinikimtecnologia.constants.GeralConstants;
import br.com.tupinikimtecnologia.objects.PostData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            if(GeralConstants.Debug.SQL_SHOW){
                System.out.println("--- insertPostData(String postData, int idTarget) TPostData class ---");
                System.out.println(sql+"\n");
            }
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
        } catch ( Exception e ) {
            System.out.println("Insert PostData error");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
        return 1;
    }
    
    public int editPostData(String postData, int id){
        try{
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            String sql = "UPDATE PostData SET post_data= '"+postData+"' where id= "+id;
            if(GeralConstants.Debug.SQL_SHOW){
                System.out.println("--- editPostData(String postData, int id) TPostData class ---");
                System.out.println(sql+"\n");
            }
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
        } catch ( Exception e ) {
            System.out.println("Error on update PostData");
            System.err.println( e.getClass().getName() + ":: " + e.getMessage() );
            return 0;
        }
    return 1;
    }
    
    public int deletePostDataByTargetId(int idTarget){
        try{
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            String sql = "DELETE FROM PostData WHERE id_target = '"+idTarget+"';";
            if(GeralConstants.Debug.SQL_SHOW){
                System.out.println("--- deletePostDataByTargetId(int idTarget) TPostData class ---");
                System.out.println(sql+"\n");
            }
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
        } catch ( Exception e ) {
            System.out.println("Delete PostData by TargetId Error");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
        return 1;
    }
    
    public int deletePostDataById(int id){
        try{
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            String sql = "DELETE FROM PostData WHERE id = '"+id+"';";
            if(GeralConstants.Debug.SQL_SHOW){
                System.out.println("--- deletePostDataById(int id) TPostData class ---");
                System.out.println(sql+"\n");
            }
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
        } catch ( Exception e ) {
            System.out.println("Delete PostData by id Error");
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
            if(GeralConstants.Debug.SQL_SHOW){
                System.out.println("--- checkIfPostDataExists(String postData, int idTarget) TPostData class ---");
                System.out.println(sql+"\n");
            }
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
    
    public List<PostData> selectPostDataByTargetId(int id){
        List<PostData> postDataList = new ArrayList<PostData>();
        PostData postData;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM PostData where id_target = '"+id+"';";
            if(GeralConstants.Debug.SQL_SHOW){
                System.out.println("--- selectPostDataByTargetId(int id) TPostData class ---");
                System.out.println(sql+"\n");
            }
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                postData = new PostData();
                postData.setId(rs.getInt("id"));
                postData.setIdTarget(rs.getInt("id_target"));
                postData.setPostData(rs.getString("post_data"));
                postDataList.add(postData);
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {

            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return postDataList;
    }
}
