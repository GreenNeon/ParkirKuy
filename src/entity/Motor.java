/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Yudho
 */
public class Motor extends Kendaraan{
    String noplat;
    double harga;

    public Motor(String noplat, String jenis) {
        super(jenis);
        this.noplat = noplat;
        this.harga = 4000;
    }

    public String getNoplat() {
        return noplat;
    }

    public void setNoplat(String noplat) {
        this.noplat = noplat;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
    
}