package com.bank.acelera.controller.response;

import java.util.Date;

public class MovementResponse {
    private Float value;
    private int type;
    private Date data;

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
