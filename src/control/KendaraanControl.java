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
    public ArrayList<Kendaraan> AmbilKendaraanPlat(String plat){
        dao.makeConnection();
        ArrayList<Kendaraan> list = dao.AmbilKendaraanPlat(plat);
        dao.closeConnection();
        return list;
    }
    public void EditMobil(Mobil M){
        dao.makeConnection();
        dao.EditMobil(M);
        dao.closeConnection();
    }
    public void EditMotor(Motor M){
        dao.makeConnection();
        dao.EditMotor(M);
        dao.closeConnection();
    }
    public void EditSepeda(Sepeda S){
        dao.makeConnection();
        dao.EditSepeda(S);
        dao.closeConnection();
    }
    public void HapusKendaraan(Date time){
        
    }
}
