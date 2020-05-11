package ro.gcloud.mycinema.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.gcloud.mycinema.services.EmailService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/emails")
public class EmailController {
    @Autowired
    private EmailService emailService;
    private Logger logger = LogManager.getLogger(this.getClass());

    @GetMapping
    public String sendTestEmail() {
        emailService.sendTestEmail("qqsergiu@yahoo.com");
        return "Email Sent";
    }

}
