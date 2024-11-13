package com.faiskaburguer.db.util;

public class Mascaras {
    public static String mascFone(String input) {
        if (input == null) {
            return null;
        }
        String apenasNumeros = removeCharNoNum(input);


        // Detecta e aplica a máscara para telefone com 11 dígitos (formato (XX) XXXXX-XXXX)
        if (apenasNumeros.matches("\\d{11}")) {
            return apenasNumeros.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        }

        // Detecta e aplica a máscara para telefone com 10 dígitos (formato (XX) XXXX-XXXX)
        else if (apenasNumeros.matches("\\d{10}")) {
            return apenasNumeros.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        }

        // Retorna a string original caso o formato não seja reconhecido
        return input;
    }

    public static String mascCpf(String input) {
        if (input == null) {
            return null;
        }
        String apenasNumeros = removeCharNoNum(input);


        // Detecta e aplica a máscara para telefone com 11 dígitos (formato (XX) XXXXX-XXXX)
        if (apenasNumeros.matches("\\d{11}")) {
            return apenasNumeros.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }

        // Retorna a string original caso o formato não seja reconhecido
        return input;
    }

    public static String mascCnpj(String input) {
        if (input == null) {
            return null;
        }
        String apenasNumeros = removeCharNoNum(input);


        // Detecta e aplica a máscara para CNPJ (14 dígitos)
        if (apenasNumeros.matches("\\d{14}")) {
            return apenasNumeros.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        }

        // Retorna a string original caso o formato não seja reconhecido
        return input;
    }

    private static String removeCharNoNum(String input) {
        return input.replaceAll("\\D", "");
    }
}
