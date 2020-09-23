package com.company.storeemail.rest.web;

import com.company.storeemail.rest.model.RequestEmail;
import com.company.storeemail.rest.port.EmailPort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/email")
public class EmailRestApi {

    private final EmailPort emailPort;

    public EmailRestApi(EmailPort emailPort) {
        this.emailPort = emailPort;
    }

    @PostMapping("/send")
    @ResponseBody
    public boolean sendEmail(@RequestBody RequestEmail requestEmail){
        return emailPort.sendEmail(requestEmail);
    }
}
