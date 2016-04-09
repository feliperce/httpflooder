/*
 * Created by: Felipe Rodrigues
 * http://www.tupinikimtecnologia.com.br
 */
package br.com.tupinikimtecnologia.objects;

/**
 *
 * @author felipe
 */
public class PostDataListHelper extends PostData {
    
    private int listId;

    public PostDataListHelper(){ }
    
    public PostDataListHelper(int id, String postData){
        this.id = id;
        this.postData = postData;
    }
    
    @Override
    public String toString() {
        return postData;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
    
}
