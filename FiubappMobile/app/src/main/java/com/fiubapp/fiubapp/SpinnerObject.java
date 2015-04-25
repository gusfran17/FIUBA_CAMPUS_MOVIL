package com.fiubapp.fiubapp;

/**
 * Created by Gustavo.Franco on 23/04/2015.
 */
public class SpinnerObject {
    private int id;
    private String value;

    public SpinnerObject(int id, String value){
        this.id = id;
        this.value = value;
    }

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
