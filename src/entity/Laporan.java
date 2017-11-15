/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Yudho
 */
public class Laporan {
    private int idLaporan,jmlKendaraan;
    private double totalHarga;
    private Date tglLaporan;

    public Laporan() {
    }

    public Laporan(int idLaporan, int jmlKendaraan, double totalHarga, Date tglLaporan) {
        this.idLaporan = idLaporan;
        this.jmlKendaraan = jmlKendaraan;
        this.totalHarga = totalHarga;
        this.tglLaporan = tglLaporan;
    }

    public int getIdLaporan() {
        return idLaporan;
    }

    public int getJmlKendaraan() {
        return jmlKendaraan;
    }

    public void setJmlKendaraan(int jmlKendaraan) {
        this.jmlKendaraan = jmlKendaraan;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public Date getTglLaporan() {
        return tglLaporan;
    }

    public void setTglLaporan(Date tglLaporan) {
        this.tglLaporan = tglLaporan;
    }
    
    
}
