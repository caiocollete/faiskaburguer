package com.faiskaburguer.db.viacep.exceptions;

public class InvalidFormat extends RuntimeException{
    String error;

    public InvalidFormat(String error){
        this.error = error;
    }

    @Override
    public String getMessage(){
        return error;
    }
}
