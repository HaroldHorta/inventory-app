package com.company.storeapi.model.payload.response.diagnosticplan;

import lombok.Data;
import java.util.Date;

@Data
public class ResponseDiagnosticPlan {
    private String id;
    private String description;
    private Date createAt;
    private String pdfs;
    private String labs;

}
