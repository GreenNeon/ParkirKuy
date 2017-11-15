/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import static dao.IDaoServer.CON;
import entity.Laporan;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
/**
 *
 * @author Yudho
 */
public class LaporanDAO extends IDaoServer{
    public int MaxIDLaporan(){
        String sql = "SELECT MAX(IDLaporan) AS JML FROM Laporan";
        ResultSet rs = null;
        int maxID = 0;
        System.out.println("Mendapatkan MAX IDLaporan ..\n");
        
        try{
            Statement stat = CON.createStatement();
            rs = stat.executeQuery(sql);
            
            while (rs.next()) {
                maxID = rs.getInt("JML");
            }
            
            rs.close();
            stat.close();
        }catch(Exception e){
            System.out.println("Error HitungLaporan ...");
            System.out.println(e);
        }
        return ++maxID;
    }
    public void TambahLaporan(Laporan L){
        String sql = "INSERT INTO Laporan (Jumlah,Total,Hari) VALUES("+
                     "'"+L.getJmlKendaraan()+
                     "','"+L.getTotalHarga()+
                     "',#"+L.getTglLaporan()+
                     "#)";
        
        System.out.println("Menambah Laporan Ke TABLE ..." + sql +"\n");
        
        try{
            Statement stat = CON.createStatement();
            int result = stat.executeUpdate(sql);
            System.out.println("Edit" + result + "Laporan\n");
            stat.close();
        } catch(Exception e){
            System.out.println("Error Menambah A Laporan ...");
            System.out.println(e);
        } 
    }
    public void EditLaporan(Laporan L){
        String sql = "UPDATE Laporan SET "+
                     "Jumlah = '"+L.getJmlKendaraan()+
                     "', Total = '"+L.getTotalHarga()+
                     "', Hari = #"+L.getTglLaporan()+
                     "# WHERE IDLaporan LIKE "+L.getIdLaporan();
        
        System.out.println("Mengedit Laporan Ke TABLE ..." + sql +"\n");
        
        try{
            Statement stat = CON.createStatement();
            int result = stat.executeUpdate(sql);
            System.out.println("Edit" + result + "Laporan\n");
            stat.close();
        } catch(Exception e){
            System.out.println("Error Menambah A Laporan ...");
            System.out.println(e);
        } 
    }
    public Laporan AmbilLaporanHari(int day,int month,int year){
        Laporan lapor = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Laporan Where "+day+" = DAY(Hari) "+
                "AND "+month+" = MONTH(Hari) AND "+year+" = YEAR(Hari)";
        System.out.println("Mengambil Table Kendaraan ..\n");
        
        try{
            Statement stat = CON.createStatement();
            rs = stat.executeQuery(sql);
            
            while (rs.next()) {
                lapor = new Laporan(rs.getInt("IDLaporan"), rs.getInt("Jumlah"), rs.getDouble("Total"), rs.getDate("Hari"));
            }
            
            rs.close();
            stat.close();
        }catch(Exception e){
            System.out.println("Error AmbilKendaraan ...");
            System.out.println(e);
        }
        return lapor;
    }
}
