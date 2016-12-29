package com.reijn.easydrive;

/**
 * Created by reijn on 29-12-2016.
 */

public class Transport {
    String  id, transDate, transCompany, transPlate, transFrom, transToo;

    public Transport() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransCompany() {
        return transCompany;
    }

    public void setTransCompany(String transCompany) {
        this.transCompany = transCompany;
    }

    public String getTransPlate() {
        return transPlate;
    }

    public void setTransPlate(String transPlate) {
        this.transPlate = transPlate;
    }

    public String getTransFrom() {
        return transFrom;
    }

    public void setTransFrom(String transFrom) {
        this.transFrom = transFrom;
    }

    public String getTransToo() {
        return transToo;
    }

    public void setTransToo(String transToo) {
        this.transToo = transToo;
    }

    @Override
    public String toString() {
        return "Datum: " + transDate + " , Kenteken: " + transPlate;
    }
}
