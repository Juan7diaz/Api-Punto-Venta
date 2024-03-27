package org.unimagdalena.tallermicroservicioapi.utils;

public enum MetodoPago {
    EFECTIVO,
    TARJETA_CREDITO,
    PAYPAL,
    NEQUI,
    DAVIPLATA,
    PSE;

    public static MetodoPago fromString(String value) {
        return switch (value.toUpperCase()) {
            case "EFECTIVO" -> EFECTIVO;
            case "TARJETA_CREDITO" -> TARJETA_CREDITO;
            case "PAYPAL" -> PAYPAL;
            case "NEQUI" -> NEQUI;
            case "DAVIPLATA", "PSE" -> PSE;
            default -> throw new IllegalArgumentException("Valor de método de pago inválido: " + value);
        };
    }

}
