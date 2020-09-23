package com.company.storeemail.rest.port;

import com.company.storeemail.rest.model.RequestEmail;

public interface EmailPort {
    boolean sendEmail(RequestEmail requestEmail);
}

