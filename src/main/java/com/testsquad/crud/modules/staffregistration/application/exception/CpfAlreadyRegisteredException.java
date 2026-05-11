package com.testsquad.crud.modules.staffregistration.application.exception;

public class CpfAlreadyRegisteredException extends RuntimeException {

    public CpfAlreadyRegisteredException(String cpf) {
        super("CPF already registered: " + cpf);
    }
}
