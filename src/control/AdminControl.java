/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.AdminDAO;
import entity.*;
import java.util.ArrayList;


/**
 *
 * @author Yudho
 */
public class AdminControl {
    AdminDAO dao = new AdminDAO();
    
    public int HitungPetugas(){
        dao.makeConnection();
        int max = dao.MaxIDPetugas();
        dao.closeConnection();
        return max;
    }
    public ArrayList<Admin> AmbilSemuaPetugas(){
        dao.makeConnection();
        ArrayList<Admin> list = dao.AmbilSemuaPetugas();
        dao.closeConnection();
        return list;
    }
    public ArrayList<Admin> AmbilPetugasNama(String nama){
        dao.makeConnection();
        ArrayList<Admin> list = dao.AmbilPetugasNama(nama);
        dao.closeConnection();
        return list;
    }
    public ArrayList<Admin> AmbilPetugasJenis(boolean jenis){
        dao.makeConnection();
        ArrayList<Admin> list = dao.AmbilPetugasJenis(jenis);
        dao.closeConnection();
        return list;
    }
    public void EditPetugas(Admin admin){
        dao.makeConnection();
        dao.EditPetugas(admin);
        dao.closeConnection();
    }
    public void TambahPetugas(Admin admin){
        dao.makeConnection();
        dao.TambahPetugas(admin);
        dao.closeConnection();
    }
    public Admin LoginPetugas(int id,String pin){
        dao.makeConnection();
        Admin admin = dao.LoginPetugas(id, pin);
        dao.closeConnection();
        return admin;
    }
    public void HapusPetugas(Admin admin){
        dao.makeConnection();
        dao.HapusAdmin(admin);
        dao.closeConnection();
    }
}
