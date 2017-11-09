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
public class Sepeda extends Kendaraan{
    String id,noktp;
    double harga;

    public Sepeda(String noktp, String jenis) {
        super(jenis);
        this.noktp = noktp;
        this.harga = harga;
    }

    public String getNoktp() {
        return noktp;
    }

    public void setNoktp(String noktp) {
        this.noktp = noktp;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
    
}
