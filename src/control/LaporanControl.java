/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.LaporanDAO;
import entity.Laporan;
import java.util.Date;

/**
 *
 * @author Yudho
 */
public class LaporanControl {
    private LaporanDAO dao = new LaporanDAO();

    public LaporanControl() {
    }
    public int MaxIDLaporan(){
        dao.makeConnection();
        int max = dao.MaxIDLaporan();
        dao.closeConnection();
        return max;
    }
    public void TambahLaporan(Laporan L){
        dao.makeConnection();
        dao.TambahLaporan(L);
        dao.closeConnection();
    }
    public Laporan AmbilLaporanHari(int day,int month,int year){
        dao.makeConnection();
        Laporan L = dao.AmbilLaporanHari(day,month,year);
        dao.closeConnection();
        return L;
    }
    public void EditLaporan(Laporan L){
        dao.makeConnection();
        dao.EditLaporan(L);
        dao.closeConnection();
    }
}
