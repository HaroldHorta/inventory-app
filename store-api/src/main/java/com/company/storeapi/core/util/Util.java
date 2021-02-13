package com.company.storeapi.core.util;

import org.springframework.data.domain.Pageable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String toCapitalLetters(String valor) {
        if (valor == null || valor.isEmpty()) {
            return valor;
        } else {
            return  valor.toUpperCase().charAt(0) + valor.substring(1).toLowerCase();
        }
    }

    public static int getLimitPaginator(Pageable pageable, int i, int i2) {
        int limitMin = i;
        if (pageable.getPageNumber() != 0) {
            limitMin = i2;
        }
        return limitMin;
    }

    public static String converterDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }
}
