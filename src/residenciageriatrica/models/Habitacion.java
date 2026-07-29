package residenciageriatrica.models;

public class Habitacion {

    private String codHabitacion;
    private Estado estado;

    public Habitacion(String codHabitacion, Estado estado) {
        this.codHabitacion = codHabitacion;
        this.estado = estado;
    }

    public String getCodHabitacion() {
        return codHabitacion;
    }

    public void setCodHabitacion(String codHabitacion) {
        this.codHabitacion = codHabitacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "codHabitacion='" + codHabitacion + '\'' +
                ", estado=" + estado +
                '}';
    }
}
