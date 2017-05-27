/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cassiano.trackingsolarandroidarduino;

/**
 *
 * @author guilherme
 */
public class ItemListView {

    private String texto;
    private int iconeRid;
    private String telefone;

    public ItemListView() {
    }

    public ItemListView(String texto, int iconeRid, String telefone) {
        this.texto = texto;
        this.iconeRid = iconeRid;
        this.telefone = telefone;    }

    public int getIconeRid() {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid) {
        this.iconeRid = iconeRid;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.texto = telefone;
    }
}
