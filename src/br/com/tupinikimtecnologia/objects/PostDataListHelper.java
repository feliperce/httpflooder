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

    public PostDataListHelper(){ }
    
    public PostDataListHelper(int id, String postData){
        this.id = id;
        this.postData = postData;
    }
    @Override
    public String toString() {
        return postData;
    }
    
}
