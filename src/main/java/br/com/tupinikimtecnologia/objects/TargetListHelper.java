/*
 * Created by: Felipe Rodrigues
 * http://www.tupinikimtecnologia.com.br
 */
package br.com.tupinikimtecnologia.objects;

/**
 *
 * @author felipe
 */
public class TargetListHelper extends Target {
    
    private int listId;

    public TargetListHelper(){ }
    
    public TargetListHelper(int id, String url){
        this.id = id;
        this.url = url;
    }
    
    @Override
    public String toString() {
        return url;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
    
    
    
}
