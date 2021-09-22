package com.example.laboratoriosql2021;

import  android.content.Context;
import java.io.Serializable;

public  class Dto implements  Serializable{
    String descripcion;
    int codigo;
    double precio;

    public Dto() {}
    public  Dto(int codigo,String descripcion,double precio){
        this.codigo=codigo;
        this.descripcion=descripcion;
        this.precio=precio;
    }
    public int getCodigo(){
        return codigo;
    }
    public int setCodigo(int i){
        return codigo;
    }
    public String getdescripcion(){
        return descripcion;
    }
    public String setdescripcion(String string){
        return descripcion;
    }
    public double getprecio(){
        return precio;
    }
    public double setprecio(double v){
        return precio;
    }

    public Object getDescripcion(){
        return descripcion;
    }
}
