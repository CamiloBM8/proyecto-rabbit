package co.cboadam.ticket_rabbit.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FormularioTicket implements Serializable {
    private String id;
    private String nombre;
    private String correo;
    private String zona;
    private int cantidad;
    private boolean pagado;
    private String asiento;

    public FormularioTicket() {}

}
