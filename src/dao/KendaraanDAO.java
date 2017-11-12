/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.IDaoServer.CON;
import entity.Admin;
import entity.Kendaraan;
import entity.Mobil;
import entity.Motor;
import entity.Sepeda;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Yudho
 */
public class KendaraanDAO extends IDaoServer{
    public int MaxIDPetugas(){
        String sql = "SELECT MAX(IDPetugas) AS MPETUGAS FROM Petugas";
        ResultSet rs = null;
        int maxID = 0;
        System.out.println("Mendapatkan MAX IDPetugas ..\n");
        
        try{
            Statement stat = CON.createStatement();
            rs = stat.executeQuery(sql);
            
            while (rs.next()) {
                maxID = rs.getInt("MPETUGAS");
            }
            
            rs.close();
            stat.close();
        }catch(Exception e){
            System.out.println("Error HitungPetugas ...");
            System.out.println(e);
        }
        return ++maxID;
    }
    public ArrayList<Kendaraan> AmbilSemuaKendaraan(){
        ArrayList<Kendaraan> list = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Kendaraan";
        System.out.println("Mengambil Table Kendaraan ..\n");
        
        try{
            Statement stat = CON.createStatement();
            rs = stat.executeQuery(sql);
            
            while (rs.next()) {
                Kendaraan K;
                
                switch (rs.getInt("Jenis")) {
                    case 1:
                        K = new Mobil(rs.getString("NoPlat"),rs.getInt("IDKendaraan"), rs.getInt("Jenis"),rs.getDate("Masuk"),rs.getDate("Keluar"));
                        break;
                    case 2:
                        K = new Motor(rs.getString("NoPlat"),rs.getInt("IDKendaraan"), rs.getInt("Jenis"),rs.getDate("Masuk"),rs.getDate("Keluar"));
                        break;
                    default:
                        K = new Sepeda(rs.getString("NoPlat"),rs.getInt("IDKendaraan"), rs.getInt("Jenis"),rs.getDate("Masuk"),rs.getDate("Keluar"));
                        break;
                }
                list.add(K);
            }
            
            rs.close();
            stat.close();
        }catch(Exception e){
            System.out.println("Error AmbilKendaraan ...");
            System.out.println(e);
        }
        return list;
    }
    public ArrayList<Kendaraan> AmbilKendaraanPlat(String plat){
        ArrayList<Kendaraan> list = new ArrayList<>();
        ResultSet rs = null;
        int idpetugas = 0;
        String sql = "";
        
        try{
            idpetugas = Integer.parseInt(plat.replaceFirst("KND-", ""));
        }
        catch(NumberFormatException ne){
            sql = "SELECT * FROM Kendaraan WHERE NoPlat Like '%"+plat+"%'";
        }
        finally{
            sql = "SELECT * FROM Kendaraan WHERE NoPlat Like '%"+plat+"%' OR IDKendaraan Like '"+idpetugas+"'";
        }
         
        System.out.println("Mencari "+plat+" ATAU "+idpetugas+" Table Petugas ..\n");
        
        try{
            Statement stat = CON.createStatement();
            rs = stat.executeQuery(sql);
            
            while (rs.next()) {
                Kendaraan K;
                switch(rs.getInt("Jenis")){
                    case 1:
                        K = new Mobil(rs.getString("NoPlat"),rs.getInt("IDKendaraan"), Integer.parseInt(rs.getString("Jenis")),rs.getDate("Masuk"),rs.getDate("Keluar"));
                        break;
                    case 2:
                        K = new Motor(rs.getString("NoPlat"),rs.getInt("IDKendaraan"), Integer.parseInt(rs.getString("Jenis")),rs.getDate("Masuk"),rs.getDate("Keluar"));
                        break;
                    default:
                        K = new Sepeda(rs.getString("NoPlat"),rs.getInt("IDKendaraan"), Integer.parseInt(rs.getString("Jenis")),rs.getDate("Masuk"),rs.getDate("Keluar"));
                        break;
                }
                list.add(K);
            }
            
            rs.close();
            stat.close();
        }catch(Exception e){
            System.out.println("Error AmbilKendaraan ...");
            System.out.println(e);
        }
        return list;
    }
    public void EditMotor(Motor M){
        String sql = "UPDATE Petugas SET Jenis = '"+M.getJenis()+
                     "', NoPlat = '"+M.getNoplat()+
                     "', Masuk = '"+M.getWaktu_keluar()+
                     "', Keluar = '"+M.getWaktu_Masuk()+
                     "' WHERE IDKendaraan = '"+M.getId()+"'";
        
        System.out.println("Editing Petugas FROM TABLE ..." + sql +"\n");
        
        try{
            Statement stat = CON.createStatement();
            int result = stat.executeUpdate(sql);
            System.out.println("Edit" + result + "Motor\n");
            stat.close();
        } catch(Exception e){
            System.out.println("Error Editing A Motor ...");
            System.out.println(e);
        } 
    }
    public void EditMobil(Mobil M){
        String sql = "UPDATE Petugas SET Jenis = '"+M.getJenis()+
                     "', NoPlat = '"+M.getNoplat()+
                     "', Masuk = '"+M.getWaktu_keluar()+
                     "', Keluar = '"+M.getWaktu_Masuk()+
                     "' WHERE IDKendaraan = '"+M.getId()+"'";
        
        System.out.println("Editing Petugas FROM TABLE ..." + sql +"\n");
        
        try{
            Statement stat = CON.createStatement();
            int result = stat.executeUpdate(sql);
            System.out.println("Edit" + result + "Mobil\n");
            stat.close();
        } catch(Exception e){
            System.out.println("Error Editing A Mobil ...");
            System.out.println(e);
        } 
    }
    public void EditSepeda(Sepeda M){
        String sql = "UPDATE Petugas SET Jenis = '"+M.getJenis()+
                     "', NoPlat = '"+M.getNoktp()+
                     "', Masuk = '"+M.getWaktu_keluar()+
                     "', Keluar = '"+M.getWaktu_Masuk()+
                     "' WHERE IDKendaraan = '"+M.getId()+"'";
        
        System.out.println("Editing Petugas FROM TABLE ..." + sql +"\n");
        
        try{
            Statement stat = CON.createStatement();
            int result = stat.executeUpdate(sql);
            System.out.println("Edit" + result + "Mobil\n");
            stat.close();
        } catch(Exception e){
            System.out.println("Error Editing A Mobil ...");
            System.out.println(e);
        } 
    }
     public void TambahKendaraan(Kendaraan K){
        String sql = "INSERT INTO Petugas (Jenis,Masuk,Keluar) VALUES("+
                     "'"+K.getJenis()+
                     "','"+K.getWaktu_Masuk()+
                     "','"+K.getWaktu_keluar()+
                     "')";
        
        System.out.println("Menambah Petugas Ke TABLE ..." + sql +"\n");
        
        try{
            Statement stat = CON.createStatement();
            int result = stat.executeUpdate(sql);
            System.out.println("Edit" + result + "Karyawan\n");
            stat.close();
        } catch(Exception e){
            System.out.println("Error Menambah A Karyawan ...");
            System.out.println(e);
        } 
    }

}
