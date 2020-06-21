package com.bank.acelera.controller.request;

public class PersonRequest {
    
    public static enum Type { PHYSICAL, LEGAL };

    private String name;
    
    private String cpf;

    private String cnpj;

    private Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    public boolean isPhysical(){
        return this.type == Type.PHYSICAL;
    }
    
    public boolean isLegal(){
        return this.type == Type.LEGAL;
    }
    
}
