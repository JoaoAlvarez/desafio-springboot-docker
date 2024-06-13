package com.projeto.api.web.rest.error;

public class ImportCsvException extends Exception  {

    private static final long serialVersionUID = 1149241039409861914L;

    public ImportCsvException(String msg){
        super(msg);
    }

    public ImportCsvException(String msg, Throwable cause){
        super(msg, cause);
    }
}