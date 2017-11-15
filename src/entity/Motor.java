/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author Yudho
 */
public class Motor extends Kendaraan{
    String noplat;
    double harga;

    public Motor(String noplat, int jenis) {
        super(jenis);
        this.noplat = noplat;
        this.harga = 4000;
    }
    public Motor(String noplat,int id, int jenis, Date waktu_masuk, Date waktu_keluar) {
        super(id,jenis,waktu_masuk,waktu_keluar);
        this.noplat = noplat;
        this.harga = 4000;
    }

    public String getNoplat() {
        return noplat;
    }

    public void setNoplat(String noplat) {
        this.noplat = noplat;
    }
    public int getTotalHari(Date wk){
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(wk);
        int days;
        days = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(waktu_masuk);
        days = cal.get(Calendar.DAY_OF_YEAR)-days;
        return days;
    }
    public double getTotalHarga(Date wk){
        return harga + (5000*getTotalHari(wk));
    }
    public double getHarga() {
        return harga;
    }
    
}
