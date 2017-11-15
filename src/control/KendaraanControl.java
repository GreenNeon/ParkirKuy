/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.KendaraanDAO;
import entity.*;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Yudho
 */
public class KendaraanControl {
    KendaraanDAO dao = new KendaraanDAO();
    
    public ArrayList<Kendaraan> AmbilSemuaKendaraan(){
        dao.makeConnection();
        ArrayList<Kendaraan> list = dao.AmbilSemuaKendaraan();
        dao.closeConnection();
        return list;
    }
    public ArrayList<Kendaraan> AmbilKendaraanJenis(int jenis){
        dao.makeConnection();
        ArrayList<Kendaraan> list = dao.AmbilKendaraanJenis(jenis);
        dao.closeConnection();
        return list;
    }
    public ArrayList<Kendaraan> AmbilKendaraanPlat(String plat){
        dao.makeConnection();
        ArrayList<Kendaraan> list = dao.AmbilKendaraanPlat(plat);
        dao.closeConnection();
        return list;
    }
    public void TambahKendaraan(Kendaraan K,int idPetugas){
        dao.makeConnection();
        switch(K.getJenis()){
            case 1: dao.TambahMobil((Mobil) K,idPetugas);break;
            case 2: dao.TambahMotor((Motor) K,idPetugas);break;
            default: dao.TambahSepeda((Sepeda) K,idPetugas);break;
        }
        dao.closeConnection();
    }
    public void EditKendaraan(Kendaraan K){
        dao.makeConnection();
        switch(K.getJenis()){
            case 1: dao.EditMobil((Mobil) K);break;
            case 2: dao.EditMotor((Motor) K);break;
            default: dao.EditSepeda((Sepeda) K);break;
        }
        dao.closeConnection();
    }
    public void SetIDLaporan(int idKendaraan,int idLaporan){
        dao.makeConnection();
        dao.SetIDLaporan(idKendaraan, idLaporan);
        dao.closeConnection();
    }
}
