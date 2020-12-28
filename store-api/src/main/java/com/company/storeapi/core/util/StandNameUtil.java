package com.company.storeapi.core.util;

public class StandNameUtil {

    public static String toCapitalLetters(String valor) {
        if (valor == null || valor.isEmpty()) {
            return valor;
        } else {
            return  valor.toUpperCase().charAt(0) + valor.substring(1).toLowerCase();
        }
    }
}
