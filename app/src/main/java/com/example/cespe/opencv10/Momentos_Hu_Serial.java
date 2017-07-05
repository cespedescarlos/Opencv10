/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.cespe.opencv10;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cespe
 */
public class Momentos_Hu_Serial implements Serializable {
    int posicion_momento;
    double m0;
	double m1;
	double m2;
	double m3;
	double m4;
	double m5;
	double m6;

    double promedio;

    public Momentos_Hu_Serial() {
        posicion_momento=0;
        m0=Double.NaN;
        m1=Double.NaN;
        m2=Double.NaN;
        m3=Double.NaN;
        m4=Double.NaN;
        m5=Double.NaN;
        m6=Double.NaN;
        promedio=Double.NaN;
    }

    public Momentos_Hu_Serial(int posicion_momento, double m0, double m1, double m2, double m3, double m4, double m5, double m6, double promedio) {
        this.posicion_momento = posicion_momento;
        this.m0 = m0;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.m5 = m5;
        this.m6 = m6;
        this.promedio = promedio;
    }

    public void set(int posicion, double valor){
        switch(posicion){
            case 0: this.posicion_momento=(int)valor;break;
            case 1: this.m0=valor;break;
            case 2: this.m1=valor;break;
            case 3: this.m2=valor;break;
            case 4: this.m3=valor;break;
            case 5: this.m4=valor;break;
            case 6: this.m5=valor;break;
            case 7: this.m6=valor;break;
            case 8: this.promedio=valor;break;
        }
    }

    public double get(int posicion){
        switch(posicion){
            case 0: return this.posicion_momento;
            case 1: return this.m0;
            case 2: return this.m1;
            case 3: return this.m2;
            case 4: return this.m3;
            case 5: return this.m4;
            case 6: return this.m5;
            case 7: return this.m6;
            case 8: return this.promedio;
        }
        return Double.NaN;
    }

    public void setPosicion_momento(int posicion_momento) {
        this.posicion_momento = posicion_momento;
    }

    public int getPosicion_momento() {
        return posicion_momento;
    }

    public double getM0() {
        return m0;
    }

    public double getM1() {
        return m1;
    }

    public double getM2() {
        return m2;
    }

    public double getM3() {
        return m3;
    }

    public double getM4() {
        return m4;
    }

    public double getM5() {
        return m5;
    }

    public double getM6() {
        return m6;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setM0(double m0) {
        this.m0 = m0;
    }

    public void setM1(double m1) {
        this.m1 = m1;
    }

    public void setM2(double m2) {
        this.m2 = m2;
    }

    public void setM3(double m3) {
        this.m3 = m3;
    }

    public void setM4(double m4) {
        this.m4 = m4;
    }

    public void setM5(double m5) {
        this.m5 = m5;
    }

    public void setM6(double m6) {
        this.m6 = m6;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }
    
    public boolean comparacion(Momentos_Hu_Serial momento1, Momentos_Hu_Serial momento2){
        if(momento1.posicion_momento==momento2.posicion_momento && 
           momento1.m0==momento2.m0 && momento1.m1==momento2.m1 &&
           momento1.m2==momento2.m2 && momento1.m3==momento2.m3 &&
           momento1.m4==momento2.m4 && momento1.m5==momento2.m5 &&
           momento1.m6==momento2.m6){
            
            return true;
        }
        return false;
    }
    
    public static List<Momentos_Hu_Serial> limpiar_lista(List<Momentos_Hu_Serial> list){
        if(list!=null &&list.size()>0){
            List<Momentos_Hu_Serial> nueva=new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Momentos_Hu_Serial get = list.get(i);
                if(Double.compare(get.promedio, 0.0)!=0){
                    nueva.add(get);
                }
            }
            return nueva;
        }
        return null;
    }
    
    
}
