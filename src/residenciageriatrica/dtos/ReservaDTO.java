package residenciageriatrica.dtos;

public class ReservaDTO {

    private String idResidente;
    private String codHabitacion;

    public ReservaDTO(String idResidente, String codHabitacion) {
        this.idResidente = idResidente;
        this.codHabitacion = codHabitacion;
    }

    public String getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(String idResidente) {
        this.idResidente = idResidente;
    }

    public String getCodHabitacion() {
        return codHabitacion;
    }

    public void setCodHabitacion(String codHabitacion) {
        this.codHabitacion = codHabitacion;
    }
}