package org.unimagdalena.tallermicroservicioapi.utils;

public enum EstadoPedido {
    PENDIENTE,
    ENVIADO,
    ENTREGADO;

    public static EstadoPedido fromString(String value) {
        return switch (value.toUpperCase()) {
            case "PENDIENTE" -> PENDIENTE;
            case "ENVIADO" -> ENVIADO;
            case "ENTREGADO" -> ENTREGADO;
            default -> throw new IllegalArgumentException("El valor es un estado invalido: " + value);
        };
    }

}
