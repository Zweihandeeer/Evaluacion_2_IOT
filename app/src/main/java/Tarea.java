import androidx.annotation.NonNull;
import java.io.Serializable;

public class Tarea implements Serializable {
    private int id;
    private String titulo;
    private String descripcion;

    @NonNull
    @Override
    public String toString() {
        return "ID: " + id + ", Titulo: " + titulo + ", : descripción -> " + descripcion;
    }

    public void setID(int nuevoID) {
        id = nuevoID;
    }

    public int getID(){
        return id;
    }

    public void setTitulo(String nuevoTitulo) {
        titulo = nuevoTitulo;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setDescripcion(String nuevaDescripcion) {
        descripcion = nuevaDescripcion;
    }

    public String getDescripcion(){ return descripcion; }
}