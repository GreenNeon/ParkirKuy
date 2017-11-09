/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.time.Instant;
import java.util.Date;


/**
 *
 * @author Yudho
 */
public abstract class Kendaraan {
    String id,jenis;
    Date waktu_masuk,waktu_keluar;

    public Kendaraan(String jenis) {
        this.jenis = jenis;
        this.waktu_masuk = Date.from(Instant.now());
    }
    public int getTotalJam(){
        return 0;
    }

    public String getId() {
        return id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Date getWaktu_keluar() {
        return waktu_keluar;
    }

    public void setWaktu_keluar(Date waktu_keluar) {
        this.waktu_keluar = waktu_keluar;
    }
    
}
