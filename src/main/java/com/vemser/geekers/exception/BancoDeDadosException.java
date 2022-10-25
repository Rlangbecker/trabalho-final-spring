package com.vemser.geekers.exception;

import java.sql.SQLException;

public class BancoDeDadosException extends SQLException {

    public BancoDeDadosException(String message) {
        super(message);
    }
}
