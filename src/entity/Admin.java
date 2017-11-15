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
public class Admin extends InfoAdmin {
    //data petugas
    private String pin;
    private boolean tipeadmin;
    private int id,total_waktu,total_kendaraan;
    private ArrayList<Kendaraan> isiparkir;
    
    public Admin(int id,InfoAdmin admin,String pin, int total_waktu, int total_kendaraan,boolean tipeadmin) {
        super(admin.nama, admin.noktp, admin.alamat,admin.telepon,admin.kelamin, admin.umur);
        this.id = id;
        this.pin = pin;
        this.tipeadmin = tipeadmin;
        this.total_waktu = total_waktu;
        this.total_kendaraan = total_kendaraan;
    }

    public Admin() {
        super(null, null, null, null, 0, 0);
        tipeadmin = false;
        this.id = 1;
    }
    
    

    public boolean isTipeadmin() {
        return tipeadmin;
    }

    public void setTipeadmin(boolean tipeadmin) {
        this.tipeadmin = tipeadmin;
    }
    
    public void addKendaraan(Kendaraan k){
        this.isiparkir.add(k);
    }
    public ArrayList<Kendaraan> getKendaraan(){
        return this.isiparkir;
    }
    public void setInfo(InfoAdmin info){
        super.setNama(info.nama);
        super.setNoktp(info.noktp);
        super.setAlamat(info.alamat);
        super.setKelamin(info.kelamin);
        super.setUmur(info.umur);
    }
    public InfoAdmin getInfo(){
        InfoAdmin admin = new InfoAdmin(nama,noktp,alamat,telepon,kelamin,umur);
        return admin;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getTotal_waktu() {
        return total_waktu;
    }

    public void setTotal_waktu(int total_waktu) {
        this.total_waktu = total_waktu;
    }

    public int getTotal_kendaraan() {
        return total_kendaraan;
    }

    public void setTotal_kendaraan(int total_kendaraan) {
        this.total_kendaraan = total_kendaraan;
    }
    
}
