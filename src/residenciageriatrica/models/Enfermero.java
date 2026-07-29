package residenciageriatrica.models;

import residenciageriatrica.models.Persona;


public class Enfermero extends Persona {
    
    public Enfermero(String id, String dni, String nombre, String apellido) {
        super(id, dni, nombre, apellido);
    }


    @Override
    public String toString() {
        return "Enfermero{" + '}';
    }   
    

}
