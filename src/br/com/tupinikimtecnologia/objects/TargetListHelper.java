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

    public TargetListHelper(){ }
    
    public TargetListHelper(int id, String url){
        this.id = id;
        this.url = url;
    }
    @Override
    public String toString() {
        return url;
    }
    
}
