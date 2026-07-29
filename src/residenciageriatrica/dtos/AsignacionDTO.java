package residenciageriatrica.dtos;

public class AsignacionDTO {

    private String idEnfermero;
    private String codHabitacion;

    public AsignacionDTO(String idEnfermero, String codHabitacion) {
        this.idEnfermero = idEnfermero;
        this.codHabitacion = codHabitacion;
    }

    public String getIdEnfermero() {
        return idEnfermero;
    }

    public void setIdEnfermero(String idEnfermero) {
        this.idEnfermero = idEnfermero;
    }

    public String getCodHabitacion() {
        return codHabitacion;
    }

    public void setCodHabitacion(String codHabitacion) {
        this.codHabitacion = codHabitacion;
    }
}
