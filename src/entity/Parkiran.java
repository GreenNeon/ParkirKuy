/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author Yudho
 */
public class Parkiran{
    int maxkendaraan, total_sepeda, total_motor, total_mobil;
    ArrayList<Kendaraan> isiparkir;

    public Parkiran(int maxkendaraan) {
        this.maxkendaraan = maxkendaraan;
        this.isiparkir = new ArrayList<Kendaraan>();
        this.total_sepeda = 0;
        this.total_motor = 0;
        this.total_mobil = 0;
    }

    public int getMaxkendaraan() {
        return maxkendaraan;
    }

    public void setMaxkendaraan(int maxkendaraan) {
        this.maxkendaraan = maxkendaraan;
    }
    
    public void addKendaraan(Kendaraan k){
        this.isiparkir.add(k);
    }
    public int getTotal_sepeda() {
        return total_sepeda;
    }

    public void setTotal_sepeda(int total_sepeda) {
        this.total_sepeda = total_sepeda;
    }

    public int getTotal_motor() {
        return total_motor;
    }

    public void setTotal_motor(int total_motor) {
        this.total_motor = total_motor;
    }

    public int getTotal_mobil() {
        return total_mobil;
    }

    public void setTotal_mobil(int total_mobil) {
        this.total_mobil = total_mobil;
    }
    
}
