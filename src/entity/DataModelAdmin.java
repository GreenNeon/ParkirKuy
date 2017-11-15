/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Yudho
 */
public class DataModelAdmin extends AbstractTableModel{
   private ArrayList<Admin> data;
   private ArrayList<String> columnNames;

    public DataModelAdmin(ArrayList<Admin> data,ArrayList<String> columns) {
        super();
        this.data = data;
        this.columnNames = columns;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Admin admin = data.get(row);
        switch(col) {
          case 0: return admin.getId();
          case 9: return admin.getPin();
          case 1: return admin.isTipeadmin();
          case 6: return admin.getNoktp();
          case 2: return admin.getNama();
          case 7: return admin.getKelamin();
          case 8: return admin.getUmur();
          case 3: return admin.getTelepon();
          case 4: return admin.getAlamat();
          case 5: return admin.getTotal_kendaraan();
          case 10: return admin.getTotal_waktu();
          // to complete here...
          default: return null;
        }
    }
    @Override
    public String getColumnName(int col) {
        return columnNames.get(col).toString();
    }
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }   
   
}
