package co.cboadam.ticket_rabbit.controller;

import co.cboadam.ticket_rabbit.model.FormularioTicket;
import co.cboadam.ticket_rabbit.service.TicketPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class TicketController {

    @Autowired
    private TicketPublisherService publisher;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("ticket", new FormularioTicket());
        return "formulario";
    }

    @PostMapping("/solicitar-ticket")
    public String enviarFormulario(FormularioTicket ticket) {
        publisher.publicarSolicitud(ticket);
        return "redirect:/?success";
    }

    @GetMapping("/pagar")
    public String mostrarPago(@RequestParam String id, Model model) {
        FormularioTicket ticket = publisher.obtenerTicketPorId(id);

        if (ticket == null) {
            return "error";
        }

        model.addAttribute("ticket", ticket);
        return "pago";
    }

    @PostMapping("/procesar-pago")
    public String procesarPago(@RequestParam String id) {
        publisher.enviarPago(id);
        return "redirect:/pago-exitoso?id=" + id;
    }

    @GetMapping("/pago-exitoso")
    public String pagoExitoso() {
        return "pago-exitoso";
    }
}
