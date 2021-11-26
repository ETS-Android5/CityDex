package com.tlbail.ptuts3androidapp.Model.Localisation;

public class CityNotFoundException extends Exception{

    public String getMessage() {

        return super.getMessage() + " Ville non détectée ";
    }

}
