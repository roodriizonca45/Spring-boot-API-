package com.utn.productos.exception;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String msg) {
        super(msg);
    }
}
