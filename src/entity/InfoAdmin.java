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
public class InfoAdmin {
    //data pribadi
    protected String nama,noktp,telepon,alamat;
    protected int umur,kelamin;

    public InfoAdmin(String nama, String noktp, String alamat,String telepon,int kelamin, int Umur) {
        this.nama = nama;
        this.noktp = noktp;
        this.kelamin = kelamin;
        this.telepon = telepon;
        this.alamat = alamat;
        this.umur = Umur;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public int getKelamin() {
        return kelamin;
    }

    public void setKelamin(int kelamin) {
        this.kelamin = kelamin;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoktp() {
        return noktp;
    }

    public void setNoktp(String noktp) {
        this.noktp = noktp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int Umur) {
        this.umur = Umur;
    }
    
}
