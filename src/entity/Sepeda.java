/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author Yudho
 */
public class Sepeda extends Kendaraan{
    String id,noplat;
    double harga;

    public Sepeda(String noplat, int jenis) {
        super(jenis);
        this.noplat = noplat;
        this.harga = 1000;
    }
    public Sepeda(String noplat,int id, int jenis, Date waktu_masuk, Date waktu_keluar) {
        super(id,jenis,waktu_masuk,waktu_keluar);
        this.noplat = noplat;
        this.harga = 1000;
    }

    public String getNoktp() {
        return noplat;
    }

    public void setNoktp(String noktp) {
        this.noplat = noktp;
    }

    public double getHarga() {
        return harga;
    }
    
}
