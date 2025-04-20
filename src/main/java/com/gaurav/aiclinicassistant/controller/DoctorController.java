package com.gaurav.aiclinicassistant.controller;

import com.gaurav.aiclinicassistant.assistant.DoctorAssistant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DoctorController {

    private DoctorAssistant doctorAssistant;

    @GetMapping("/doctor/chat")
    public String recommendDoctor(@RequestParam String userMessage) {
        return doctorAssistant.chat(userMessage);
    }

//    @GetMapping("/doctor/chat")
//    public String doctorChat(@RequestParam String userMessage) {
//        // Mocked response
//        return "Mocked Doctor Response: Based on your message '" + userMessage + "', please consult a specialist.";
//    }
}
