package co.cboadam.pago_consumer.model;

import lombok.Data;

@Data
public class FormularioTicket {
    private String id;
    private String nombre;
    private String correo;
    private String zona;
    private int cantidad;
    private String asiento;
    private boolean pagado;
}
