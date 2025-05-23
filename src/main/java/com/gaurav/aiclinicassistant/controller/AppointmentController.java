package com.gaurav.aiclinicassistant.controller;

import com.gaurav.aiclinicassistant.assistant.AppointmentAssistant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AppointmentController {

    private AppointmentAssistant appointmentAssistant;

    @GetMapping("/patient/chat")
    public String bookAppointment(@RequestParam String userMessage) {
        return appointmentAssistant.chat(userMessage);
    }

//    @GetMapping("/patient/chat")
//    public String patientChat(@RequestParam String userMessage) {
//        // Mocked response
//        return "Mocked Patient Response: We received your message '" + userMessage + "'. We will help you shortly.";
//    }
}
