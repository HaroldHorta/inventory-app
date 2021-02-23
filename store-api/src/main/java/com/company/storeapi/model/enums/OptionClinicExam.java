package com.company.storeapi.model.enums;

public enum OptionClinicExam {

    NORMAL(1),
    ANORMAL(2);

    private final Integer id;

    OptionClinicExam(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
