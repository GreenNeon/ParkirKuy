/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.ResultSet;
import java.sql.Statement;
import entity.*;
import java.util.ArrayList;
import org.apache.commons.lang.BooleanUtils;
/**
 *
 * @author Yudho
 */
public class AdminDAO extends IDaoServer{
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
    public ArrayList<Admin> AmbilSemuaPetugas(){
        ArrayList<Admin> list = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Petugas";
        System.out.println("Mengambil Table Petugas ..\n");
        
        try{
            Statement stat = CON.createStatement();
            rs = stat.executeQuery(sql);
            
            while (rs.next()) {
                InfoAdmin info = new InfoAdmin(rs.getString("Nama"), rs.getString("NoKTP"), rs.getString("Alamat"),rs.getString("Telepon"),
                        Integer.parseInt(rs.getString("Kelamin")), Integer.parseInt(rs.getString("Umur")));
                Admin admin = new Admin(Integer.parseInt(rs.getString("IDPetugas")),info,rs.getString("Pin"), Integer.parseInt(rs.getString("TotalWaktu")),
                        Integer.parseInt(rs.getString("TotalKendaraan")),Boolean.parseBoolean(rs.getString("Admin")));

                list.add(admin);
            }
            
            rs.close();
            stat.close();
        }catch(Exception e){
            System.out.println("Error AmbilPetugas ...");
            System.out.println(e);
        }
        return list;
    }
    public ArrayList<Admin> AmbilPetugasNama(String nama){
        ArrayList<Admin> list = new ArrayList<>();
        ResultSet rs = null;
        int idpetugas = 0;
        String sql = "";
        
        try{
            idpetugas = Integer.parseInt(nama.replaceFirst("USR-", ""));
        }
        catch(NumberFormatException ne){
            sql = "SELECT * FROM Petugas WHERE Nama Like '%"+nama+"%'";
        }
        finally{
            sql = "SELECT * FROM Petugas WHERE Nama Like '%"+nama+"%' OR IDPetugas Like '"+idpetugas+"'";
        }
         
        System.out.println("Mencari "+nama+" ATAU "+idpetugas+" Table Petugas ..\n");
        
        try{
            Statement stat = CON.createStatement();
            rs = stat.executeQuery(sql);
            
            while (rs.next()) {
                InfoAdmin info = new InfoAdmin(rs.getString("Nama"), rs.getString("NoKTP"), rs.getString("Alamat"),rs.getString("Telepon"),
                        Integer.parseInt(rs.getString("Kelamin")), Integer.parseInt(rs.getString("Umur")));
                Admin admin = new Admin(Integer.parseInt(rs.getString("IDPetugas")),info,rs.getString("Pin"), Integer.parseInt(rs.getString("TotalWaktu")),
                        Integer.parseInt(rs.getString("TotalKendaraan")),Boolean.parseBoolean(rs.getString("Admin")));

                list.add(admin);
            }
            
            rs.close();
            stat.close();
        }catch(Exception e){
            System.out.println("Error AmbilPetugas ...");
            System.out.println(e);
        }
        return list;
    }
    public ArrayList<Admin> AmbilPetugasJenis(boolean jenis){
        ArrayList<Admin> list = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Petugas WHERE Admin LIKE '"+String.valueOf(jenis).toUpperCase()+"'";
        
         
        System.out.println("Mencari "+jenis+"   Table Petugas ..\n");
        
        try{
            Statement stat = CON.createStatement();
            rs = stat.executeQuery(sql);
            
            while (rs.next()) {
                InfoAdmin info = new InfoAdmin(rs.getString("Nama"), rs.getString("NoKTP"), rs.getString("Alamat"),rs.getString("Telepon"),
                        Integer.parseInt(rs.getString("Kelamin")), Integer.parseInt(rs.getString("Umur")));
                Admin admin = new Admin(Integer.parseInt(rs.getString("IDPetugas")),info,rs.getString("Pin"), Integer.parseInt(rs.getString("TotalWaktu")),
                        Integer.parseInt(rs.getString("TotalKendaraan")),Boolean.parseBoolean(rs.getString("Admin")));

                list.add(admin);
            }
            
            rs.close();
            stat.close();
        }catch(Exception e){
            System.out.println("Error AmbilPetugas ...");
            System.out.println(e);
        }
        return list;
    }
    public void EditPetugas(Admin admin){
        String sql = "UPDATE Petugas SET Nama = '"+admin.getNama()+
                     "', NoKTP = '"+admin.getNoktp()+
                     "', Alamat = '"+admin.getAlamat()+
                     "', Telepon = '"+admin.getTelepon()+
                     "', Kelamin = '"+admin.getKelamin()+
                     "', Umur = '"+admin.getUmur()+
                     "', Pin = '"+admin.getPin()+
                     "', TotalWaktu = '"+admin.getTotal_waktu()+
                     "', TotalKendaraan = '"+admin.getTotal_kendaraan()+
                     "', Admin = '"+admin.isTipeadmin()+
                     "' WHERE IDPetugas = '"+admin.getId()+"'";
        
        System.out.println("Editing Petugas FROM TABLE ..." + sql +"\n");
        
        try{
            Statement stat = CON.createStatement();
            int result = stat.executeUpdate(sql);
            System.out.println("Edit" + result + "Karyawan\n");
            stat.close();
        } catch(Exception e){
            System.out.println("Error Editing A Karyawan ...");
            System.out.println(e);
        } 
    }
     public void TambahPetugas(Admin admin){
        String sql = "INSERT INTO Petugas (Nama,NoKTP,Alamat,Telepon,Kelamin,Umur,Pin,TotalWaktu,TotalKendaraan,Admin) VALUES("+
                     "'"+admin.getNama()+
                     "','"+admin.getNoktp()+
                     "','"+admin.getAlamat()+
                     "','"+admin.getTelepon()+
                     "','"+admin.getKelamin()+
                     "','"+admin.getUmur()+
                     "','"+admin.getPin()+
                     "','"+admin.getTotal_waktu()+
                     "','"+admin.getTotal_kendaraan()+
                     "','"+admin.isTipeadmin()+
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
     public Admin LoginPetugas(int id,String pin){
        Admin admin = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Petugas WHERE IDPetugas Like '%"+id+"%' AND Pin Like '"+pin+"'";
         
        System.out.println("LOGIN AKUN ..\n");
        
        try{
            Statement stat = CON.createStatement();
            rs = stat.executeQuery(sql);
            
            while (rs.next()) {
                InfoAdmin info = new InfoAdmin(rs.getString("Nama"), rs.getString("NoKTP"), rs.getString("Alamat"),rs.getString("Telepon"),
                        Integer.parseInt(rs.getString("Kelamin")), Integer.parseInt(rs.getString("Umur")));
                admin = new Admin(Integer.parseInt(rs.getString("IDPetugas")),info,rs.getString("Pin"), Integer.parseInt(rs.getString("TotalWaktu")),
                        Integer.parseInt(rs.getString("TotalKendaraan")),Boolean.parseBoolean(rs.getString("Admin")));
            }
            
            rs.close();
            stat.close();
        }catch(Exception e){
            System.out.println("Error AmbilPetugas ...");
            System.out.println(e);
        }
        return admin;
     }
     public void HapusAdmin(Admin admin){
        String sql = "DELETE FROM Petugas WHERE IDPetugas Like '"+admin.getId()+"'";
         
        System.out.println("DELETE AKUN ..\n");
        
        try{
            Statement stat = CON.createStatement();
            stat.executeUpdate(sql);
            
            stat.close();
        }catch(Exception e){
            System.out.println("Error DeletePetugas ...");
            System.out.println(e);
        }
     }
}
