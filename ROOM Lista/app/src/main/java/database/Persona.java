package database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "persona")
public class Persona {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int ID;
    @ColumnInfo (name = "nombre")
    private String nombre;
    @ColumnInfo (name = "apellido")
    private String apellido;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Persona() {
    }
}
