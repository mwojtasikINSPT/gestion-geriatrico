package residenciageriatrica.dtos;

import residenciageriatrica.models.Estado;

public class HabitacionDTO {
    private String codHabitacion;
    private Estado estado;

    public HabitacionDTO(String codHabitacion, Estado estado) {
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
}