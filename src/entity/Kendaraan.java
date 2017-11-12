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
    int id;
    int jenis;
    Date waktu_masuk,waktu_keluar;

    public Kendaraan(int jenis) {
        this.jenis = jenis;
        this.waktu_masuk = Date.from(Instant.now());
    }
    public Kendaraan(int id,int jenis, Date waktu_masuk, Date waktu_keluar){
        this.id = id;
        this.jenis = jenis;
        this.waktu_masuk = waktu_masuk;
        this.waktu_keluar = waktu_keluar;
    }
    public Kendaraan(int jenis, Date waktu_masuk){
        this.jenis = jenis;
        this.waktu_masuk = waktu_masuk;
    }
    public int getTotalJam(){
        return 0;
    }

    public int getId() {
        return id;
    }

    public int getJenis() {
        return jenis;
    }

    public void setJenis(int jenis) {
        this.jenis = jenis;
    }

    public Date getWaktu_keluar() {
        return waktu_keluar;
    }

    public void setWaktu_keluar(Date waktu_keluar) {
        this.waktu_keluar = waktu_keluar;
    }
    public Date getWaktu_Masuk(){
        return waktu_masuk;
    }
}
