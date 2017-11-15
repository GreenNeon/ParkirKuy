/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import control.*;
import entity.*;

import java.awt.event.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Yudho
 */
public class MainForm extends javax.swing.JFrame {
    private static int cPetugas = 0;
    private AdminControl ac;
    private KendaraanControl kc;
    private LaporanControl lc;
    
    private Admin adtemp;
    private Kendaraan ktemp;
    private Laporan ltemp;
    private Admin AkunAktif;
    private String action = "";
    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        
        AkunAktif = new Admin();
        ac = new AdminControl();
        kc = new KendaraanControl();
        lc = new LaporanControl();
        java.util.Date dtNow = new java.util.Date();
        Date now = new Date(dtNow.getTime()); 
        java.util.Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        ltemp = lc.AmbilLaporanHari(cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR));
        if(ltemp == null)
        {
            ltemp = new Laporan(lc.MaxIDLaporan(), 0, 0.0, now);
            lc.TambahLaporan(ltemp);
        }
        
        ScrollView.getVerticalScrollBar().setUnitIncrement(16);
    }
    private void EnableEdit(boolean val){
        txtEditNama.setEnabled(val);
        txtEditKtp.setEnabled(val);
        txtEditUmur.setEnabled(val);
        txtEditTelepon.setEnabled(val);
        txtEditPassword.setEnabled(val);
        txaEditAlamat.setEnabled(val);
        cbEditAdmin.setEnabled(val);
        rdEditLaki.setEnabled(val);
        rdEditPerempuan.setEnabled(val);
        rdEditGaje.setEnabled(val);
        
        btnEditLihat.setEnabled(!val);
        
        if(val)
            action = "EditPetugas";
    }
    private void EnableTambah(boolean val){
        txtEditNama.setEnabled(val);
        txtEditKtp.setEnabled(val);
        txtEditUmur.setEnabled(val);
        txtEditTelepon.setEnabled(val);
        txtEditPassword.setEnabled(val);
        txaEditAlamat.setEnabled(val);
        cbEditAdmin.setEnabled(val);
        rdEditLaki.setEnabled(val);
        rdEditPerempuan.setEnabled(val);
        rdEditGaje.setEnabled(val);
        
        btnEditLihat.setEnabled(false);
        
        if(val)
            action = "TambahPetugas";
    }
    private int getrdGroupKelamin(){
        if(rdEditLaki.isSelected())
            return 1;
        else if(rdEditPerempuan.isSelected())
            return 2;
        else
            return 3;
    }
    private void ChangeEdit(Admin admin){
        EnableEdit(false);
        
        btnEditPetugas.setIcon(new ImageIcon(getClass().getResource("images/btn_edit.png")));
        btnEditPetugas.setSelectedIcon(new ImageIcon(getClass().getResource("images/btn_edit_rollover.png")));
        btnEditPetugas.setRolloverIcon(new ImageIcon(getClass().getResource("images/btn_edit_rollover.png")));
        
        lblManagerNama.setText("USER MANAGER:EDIT");
        lblEditIDPetugas.setText(String.format("USR-%03d", admin.getId()));
        txtEditNama.setText(admin.getNama());
        txtEditKtp.setText(admin.getNoktp());
        txtEditUmur.setText(String.valueOf(admin.getUmur()));
        txtEditTelepon.setText(admin.getTelepon());
        txtEditPassword.setText(admin.getPin());
        txaEditAlamat.setText(admin.getAlamat());
        cbEditAdmin.setSelected(admin.isTipeadmin());
        
        lblEditTotalWaktu.setText(String.format("Total Petugas Bekerja %03d Hari", admin.getTotal_waktu()));
        lblEditTotalKendaraan.setText(String.format("Total Kendaraan Yang Dilayani %03d Kendaraan", admin.getTotal_kendaraan()));
        
        if(admin.getKelamin() == 1)
            rdEditLaki.setSelected(true);
        else if(admin.getKelamin() == 2)
            rdEditPerempuan.setSelected(true);
        else
            rdEditGaje.setSelected(true);
    }
    private void ChangeTambah(){
        EnableTambah(true);
        
        btnEditPetugas.setIcon(new ImageIcon(getClass().getResource("images/btn_editselesai.png")));
        btnEditPetugas.setSelectedIcon(new ImageIcon(getClass().getResource("images/btn_editselesai_rollover.png")));
        btnEditPetugas.setRolloverIcon(new ImageIcon(getClass().getResource("images/btn_editselesai_rollover.png")));
        
        lblManagerNama.setText("USER MANAGER:TAMBAH");
        lblEditIDPetugas.setText(String.format("USR-%03d", ac.HitungPetugas()));
        txtEditNama.setText("");
        txtEditKtp.setText("");
        txtEditUmur.setText("");
        txtEditTelepon.setText("");
        txtEditPassword.setText("");
        txaEditAlamat.setText("");
        cbEditAdmin.setSelected(false);
        
        lblEditTotalWaktu.setText("");
        lblEditTotalKendaraan.setText("");
        
        rdGroupKelamin.clearSelection();
    }
    private void ChangeDetail(Kendaraan K){      
        lblDetailIDKendaraan.setText(String.format("KND-%03d", K.getId()));
        switch(K.getJenis()){
            case 1:
                lblDetailJenis.setText(String.format("<html><p>%c</p><p>%c</p><p>%c</p><p>%c</p><p>%c</p></html>", 'M','O','B','I','L'));
                lblDetailHarga.setText(String.format("Harga Parkir, RP. %.2f Rupiah",((Mobil) K).getHarga()));
                lblDetailNoPlat.setText(String.format("Nomor Plat : %s", ((Mobil) K).getNoplat().toUpperCase()));
                break;
            case 2:
                lblDetailJenis.setText(String.format("<html><p>%c</p><p>%c</p><p>%c</p><p>%c</p><p>%c</p></html>", 'M','O','T','O','R'));
                lblDetailHarga.setText(String.format("Harga Parkir, RP. %.2f Rupiah",((Motor) K).getHarga()));
                lblDetailNoPlat.setText(String.format("Nomor Plat : %s", ((Motor) K).getNoplat().toUpperCase()));
                break;
            default:
                lblDetailJenis.setText(String.format("<html><p>%c</p><p>%c</p><p>%c</p><p>%c</p><p>%c</p><p>%c</p></html>", 'S','E','P','E','D','A'));
                lblDetailHarga.setText(String.format("Harga Parkir, RP. %.2f Rupiah",((Sepeda) K).getHarga()));
                lblDetailNoPlat.setText(String.format("Nomor Plat : %s", ((Sepeda) K).getNoktp().toUpperCase()));
                break;
        }

        lblDetailMasuk.setText(String.format("Waktu Masuk : %ta, %td/%tm/%ty",K.getWaktu_Masuk(),K.getWaktu_Masuk(),K.getWaktu_Masuk(),K.getWaktu_Masuk()));

        if(K.getWaktu_keluar() != null)
            lblDetailKeluar.setText(String.format("Waktu Keluar : %ta, %td/%tm/%ty",K.getWaktu_keluar(),K.getWaktu_Masuk(),K.getWaktu_Masuk(),K.getWaktu_Masuk()));
        else
            lblDetailKeluar.setText("Waktu Keluar : Belum Keluar");
        
    }
    private void ChangeCheckout(Kendaraan K){
        java.util.Date dtNow = new java.util.Date();
        switch(K.getJenis()){
            case 1:
                lblDetailHarga1.setText(String.format("Harga Parkir, RP. %.f Rupiah",((Mobil) K).getHarga()));
                lblDetailNoPlat1.setText(String.format("Nomor Plat : %s", ((Mobil) K).getNoplat().toUpperCase()));
                lblDetailNoPlat2.setText(String.format("Total Hari : %d Hari", ((Mobil) K).getTotalHari(new Date(dtNow.getTime()))+1));
                lblDetailHarga2.setText(String.format("Total Pembayaran, RP. %.2f Rupiah", ((Mobil) K).getTotalHarga(new Date(dtNow.getTime()))));
                break;
            case 2:
                lblDetailHarga1.setText(String.format("Harga Parkir, RP. %,f Rupiah",((Motor) K).getHarga()));
                lblDetailNoPlat1.setText(String.format("Nomor Plat : %s", ((Motor) K).getNoplat().toUpperCase()));
                lblDetailNoPlat2.setText(String.format("Total Hari : %d Hari", ((Motor) K).getTotalHari(new Date(dtNow.getTime()))+1));
                lblDetailHarga2.setText(String.format("Total Pembayaran, RP. %,f Rupiah", ((Motor) K).getTotalHarga(new Date(dtNow.getTime()))));
                break;
            default:
                lblDetailHarga1.setText(String.format("Harga Parkir, RP. %.2f Rupiah",((Sepeda) K).getHarga()));
                lblDetailNoPlat1.setText(String.format("Nomor Plat : %s", ((Sepeda) K).getNoktp().toUpperCase()));
                lblDetailNoPlat2.setText(String.format("Total Hari : %d Hari", ((Sepeda) K).getTotalHari(new Date(dtNow.getTime()))+1));
                lblDetailHarga2.setText(String.format("Total Pembayaran, RP. %.2f Rupiah", ((Sepeda) K).getTotalHarga(new Date(dtNow.getTime()))));
                break;
        }
        
        lblDetailMasuk1.setText(String.format("Waktu Masuk : %ta, %td/%tm/%ty",K.getWaktu_Masuk(),K.getWaktu_Masuk(),K.getWaktu_Masuk(),K.getWaktu_Masuk()));

        if(K.getWaktu_keluar() != null)
            lblDetailKeluar1.setText(String.format("Waktu Keluar : %ta, %td/%tm/%ty",K.getWaktu_keluar(),K.getWaktu_Masuk(),K.getWaktu_Masuk(),K.getWaktu_Masuk()));
        else
            lblDetailKeluar1.setText("Waktu Keluar : Belum Keluar");
    }
    
    private void ShowAdmin(ArrayList<Admin> data,CardLayout card){
        pnlBox.removeAll();
        
        lblManager.setText("MANAGER PETUGAS");
        btnManagerTambah.setIcon(new ImageIcon(getClass().getResource("images/btn_tambah.png")));
        btnManagerTambah.setSelectedIcon(new ImageIcon(getClass().getResource("images/btn_tambah_rollover.png")));
        btnManagerTambah.setRolloverIcon(new ImageIcon(getClass().getResource("images/btn_tambah_rollover.png")));
        
        for(Admin item : data){
            PanelPetugas pp = new PanelPetugas(item, ++cPetugas);
            pp.addMouseListener(new MouseAdapter() { 
                @Override
                public void mousePressed(MouseEvent me) { 
                  card.show(pnlMain, "cardTwoEdit");
                    ChangeEdit(pp.getAdmin());
                    adtemp = pp.getAdmin();
                } 
              });
            pp.btnHapus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pnlBox.remove(pp);
                    pnlBox.repaint();
                    pnlBox.revalidate();
                }
            });
            pnlBox.add(pp); 
        }
        pnlBox.repaint();
        pnlBox.revalidate();
    }
    private void ShowKendaraan(ArrayList<Kendaraan> data,CardLayout card){
        pnlBox.removeAll();
        cPetugas = 0;
        
        lblManager.setText("MANAGER KENDARAAN");
        btnManagerTambah.setIcon(new ImageIcon(getClass().getResource("images/btn_checkin.png")));
        btnManagerTambah.setSelectedIcon(new ImageIcon(getClass().getResource("images/btn_checkin_rollover.png")));
        btnManagerTambah.setRolloverIcon(new ImageIcon(getClass().getResource("images/btn_checkin_rollover.png")));
        
        for(Kendaraan item : data){
            PanelKendaraan pk = new PanelKendaraan(item, ++cPetugas);
            
            pk.addMouseListener(new MouseAdapter() { 
                @Override
                public void mousePressed(MouseEvent me) { 
                  card.show(pnlMain, "cardTwoDetail");
                    ChangeDetail(pk.GetKendaraan());
                    ktemp = pk.GetKendaraan();
                } 
              });
            pnlBox.add(Box.createVerticalStrut(8));
            pnlBox.add(pk); 
        }
        pnlBox.repaint();
        pnlBox.revalidate();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rdGroupKelamin = new javax.swing.ButtonGroup();
        rdGroupManager = new javax.swing.ButtonGroup();
        rdGroupCheckin = new javax.swing.ButtonGroup();
        pnlMain = new javax.swing.JPanel();
        pnlCardOne = new javax.swing.JPanel();
        pnlCreator = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlBar1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pnlCardTwo = new javax.swing.JPanel();
        lblManager = new javax.swing.JLabel();
        txtManagerSearch = new javax.swing.JTextField();
        btnManagerSearch = new javax.swing.JButton();
        ScrollView = new javax.swing.JScrollPane();
        pnlBox = new javax.swing.JPanel();
        btnManagerTambah = new javax.swing.JButton();
        rdManager1 = new javax.swing.JRadioButton();
        rdManager2 = new javax.swing.JRadioButton();
        rdManagerSemua = new javax.swing.JRadioButton();
        rdManager4 = new javax.swing.JRadioButton();
        pnlCardTwoEdit = new javax.swing.JPanel();
        lblManagerNama = new javax.swing.JLabel();
        lblEditIDPetugas = new javax.swing.JLabel();
        btnEditLihat = new javax.swing.JButton();
        txtEditNama = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        rdEditLaki = new javax.swing.JRadioButton();
        rdEditPerempuan = new javax.swing.JRadioButton();
        rdEditGaje = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        txtEditUmur = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtEditTelepon = new javax.swing.JTextField();
        lblEditTotalWaktu = new javax.swing.JLabel();
        lblEditTotalKendaraan = new javax.swing.JLabel();
        cbEditAdmin = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        txtEditPassword = new javax.swing.JPasswordField();
        jLabel15 = new javax.swing.JLabel();
        cbEditPassword = new javax.swing.JCheckBox();
        btnEditPetugas = new javax.swing.JButton();
        txtEditKtp = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaEditAlamat = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        pnlCardTwoDetail = new javax.swing.JPanel();
        lblManagerNama2 = new javax.swing.JLabel();
        lblDetailIDKendaraan = new javax.swing.JLabel();
        lblDetailJenis = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnDetailCheckout = new javax.swing.JButton();
        btnDetailKembali = new javax.swing.JButton();
        lblDetailMasuk = new javax.swing.JLabel();
        lblDetailKeluar = new javax.swing.JLabel();
        lblDetailNoPlat = new javax.swing.JLabel();
        lblDetailHarga = new javax.swing.JLabel();
        pnlCardThree = new javax.swing.JPanel();
        lblLaporanTitle = new javax.swing.JLabel();
        pnlOtherTable = new javax.swing.JPanel();
        lblLaporanTableTitle = new javax.swing.JLabel();
        lblLaporanLihat = new javax.swing.JButton();
        ScrollView2 = new javax.swing.JScrollPane();
        pnlLaporanBox = new javax.swing.JPanel();
        lblLaporanGreen1 = new javax.swing.JLabel();
        lblLaporanGreen2 = new javax.swing.JLabel();
        lblLaporanGreen = new javax.swing.JLabel();
        lblLaporanYellow1 = new javax.swing.JLabel();
        lblLaporanYellow2 = new javax.swing.JLabel();
        lblLaporanRed = new javax.swing.JLabel();
        datePick = new com.github.lgooddatepicker.components.DatePicker();
        pnlCardFour = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnLoginAction = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtLoginPin = new javax.swing.JPasswordField();
        txtLoginID = new javax.swing.JFormattedTextField();
        pnlCardFive = new javax.swing.JPanel();
        lblManagerNama3 = new javax.swing.JLabel();
        lblDetailMasuk1 = new javax.swing.JLabel();
        lblDetailKeluar1 = new javax.swing.JLabel();
        lblDetailNoPlat1 = new javax.swing.JLabel();
        lblDetailHarga1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblDetailNoPlat2 = new javax.swing.JLabel();
        lblDetailHarga2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtCheckoutBayar = new javax.swing.JFormattedTextField();
        btnCheckoutFail = new javax.swing.JButton();
        btnCheckoutGo = new javax.swing.JButton();
        pnlCardSix = new javax.swing.JPanel();
        rdCheckinMobil = new javax.swing.JRadioButton();
        rdCheckinMotor = new javax.swing.JRadioButton();
        rdCheckinSepeda = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblLaporanYellow3 = new javax.swing.JLabel();
        txtCheckin = new javax.swing.JTextField();
        btnCheckin = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        pnlSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnLog = new javax.swing.JButton();
        btnManager = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ParkirKuy | Manager");
        setBackground(new java.awt.Color(255, 255, 204));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1037, 603));
        setName("frmMain"); // NOI18N

        pnlMain.setBackground(new java.awt.Color(51, 51, 51));
        pnlMain.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                pnlMainComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pnlMainComponentResized(evt);
            }
        });
        pnlMain.setLayout(new java.awt.CardLayout(20, 20));

        pnlCardOne.setBackground(new java.awt.Color(51, 51, 51));

        pnlCreator.setBackground(new java.awt.Color(51, 51, 51));
        pnlCreator.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.darkGray, java.awt.Color.black, java.awt.Color.black, java.awt.Color.darkGray));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/logo.png"))); // NOI18N
        jLabel1.setText("ParkirKuy");
        jLabel1.setFont(new java.awt.Font("Segoe UI Historic", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));

        pnlBar1.setBackground(new java.awt.Color(204, 204, 204));
        pnlBar1.setPreferredSize(new java.awt.Dimension(0, 5));

        javax.swing.GroupLayout pnlBar1Layout = new javax.swing.GroupLayout(pnlBar1);
        pnlBar1.setLayout(pnlBar1Layout);
        pnlBar1Layout.setHorizontalGroup(
            pnlBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );
        pnlBar1Layout.setVerticalGroup(
            pnlBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jLabel2.setText("Uli Wijayanti");
        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));

        jLabel3.setText("Bryan B");
        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));

        jLabel4.setText("Penta Putra");
        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 204));

        jLabel6.setText("Robertus Yudho");
        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 204));

        javax.swing.GroupLayout pnlCreatorLayout = new javax.swing.GroupLayout(pnlCreator);
        pnlCreator.setLayout(pnlCreatorLayout);
        pnlCreatorLayout.setHorizontalGroup(
            pnlCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreatorLayout.createSequentialGroup()
                .addGroup(pnlCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCreatorLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCreatorLayout.createSequentialGroup()
                        .addContainerGap(19, Short.MAX_VALUE)
                        .addGroup(pnlCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCreatorLayout.createSequentialGroup()
                                .addGroup(pnlCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(12, 12, 12)))))
                .addContainerGap())
        );
        pnlCreatorLayout.setVerticalGroup(
            pnlCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreatorLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlCardOneLayout = new javax.swing.GroupLayout(pnlCardOne);
        pnlCardOne.setLayout(pnlCardOneLayout);
        pnlCardOneLayout.setHorizontalGroup(
            pnlCardOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardOneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlCreator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        pnlCardOneLayout.setVerticalGroup(
            pnlCardOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardOneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlCreator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        pnlMain.add(pnlCardOne, "cardOne");

        pnlCardTwo.setBackground(new java.awt.Color(51, 51, 51));
        pnlCardTwo.setForeground(new java.awt.Color(255, 255, 255));

        lblManager.setText("USER MANAGER");
        lblManager.setFont(new java.awt.Font("OCR-A BT", 0, 24)); // NOI18N
        lblManager.setForeground(new java.awt.Color(255, 204, 204));

        txtManagerSearch.setBackground(new java.awt.Color(0, 0, 0));
        txtManagerSearch.setForeground(new java.awt.Color(204, 255, 204));

        btnManagerSearch.setText("Search");
        btnManagerSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagerSearchActionPerformed(evt);
            }
        });

        ScrollView.setAutoscrolls(true);
        ScrollView.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                ScrollViewMouseWheelMoved(evt);
            }
        });

        pnlBox.setBackground(new java.awt.Color(51, 51, 51));
        pnlBox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        pnlBox.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnlBox.setLayout(new javax.swing.BoxLayout(pnlBox, javax.swing.BoxLayout.Y_AXIS));
        ScrollView.setViewportView(pnlBox);

        btnManagerTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah.png"))); // NOI18N
        btnManagerTambah.setBackground(new java.awt.Color(6, 17, 50));
        btnManagerTambah.setBorderPainted(false);
        btnManagerTambah.setContentAreaFilled(false);
        btnManagerTambah.setForeground(new java.awt.Color(252, 252, 239));
        btnManagerTambah.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnManagerTambah.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnManagerTambah.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnManagerTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagerTambahActionPerformed(evt);
            }
        });

        rdManager1.setBackground(new java.awt.Color(51, 51, 51));
        rdGroupManager.add(rdManager1);
        rdManager1.setForeground(new java.awt.Color(204, 255, 0));
        rdManager1.setText("Admin");
        rdManager1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdManager1ActionPerformed(evt);
            }
        });

        rdManager2.setBackground(new java.awt.Color(51, 51, 51));
        rdGroupManager.add(rdManager2);
        rdManager2.setForeground(new java.awt.Color(204, 255, 0));
        rdManager2.setText("User");
        rdManager2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdManager2ActionPerformed(evt);
            }
        });

        rdManagerSemua.setBackground(new java.awt.Color(51, 51, 51));
        rdGroupManager.add(rdManagerSemua);
        rdManagerSemua.setForeground(new java.awt.Color(204, 255, 0));
        rdManagerSemua.setSelected(true);
        rdManagerSemua.setText("Semua");
        rdManagerSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdManagerSemuaActionPerformed(evt);
            }
        });

        rdManager4.setBackground(new java.awt.Color(51, 51, 51));
        rdGroupManager.add(rdManager4);
        rdManager4.setForeground(new java.awt.Color(204, 255, 0));
        rdManager4.setText("Sepeda");
        rdManager4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdManager4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCardTwoLayout = new javax.swing.GroupLayout(pnlCardTwo);
        pnlCardTwo.setLayout(pnlCardTwoLayout);
        pnlCardTwoLayout.setHorizontalGroup(
            pnlCardTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardTwoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardTwoLayout.createSequentialGroup()
                        .addComponent(rdManager1)
                        .addGap(18, 18, 18)
                        .addComponent(rdManager2)
                        .addGap(18, 18, 18)
                        .addComponent(rdManagerSemua)
                        .addGap(18, 18, 18)
                        .addComponent(rdManager4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtManagerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnManagerSearch)
                        .addGap(19, 19, 19))
                    .addGroup(pnlCardTwoLayout.createSequentialGroup()
                        .addComponent(ScrollView)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardTwoLayout.createSequentialGroup()
                        .addComponent(lblManager)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnManagerTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        pnlCardTwoLayout.setVerticalGroup(
            pnlCardTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardTwoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardTwoLayout.createSequentialGroup()
                        .addComponent(lblManager)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardTwoLayout.createSequentialGroup()
                        .addComponent(btnManagerTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(ScrollView, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnManagerSearch)
                    .addComponent(txtManagerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdManager1)
                    .addComponent(rdManager2)
                    .addComponent(rdManagerSemua)
                    .addComponent(rdManager4))
                .addContainerGap())
        );

        pnlMain.add(pnlCardTwo, "cardTwo");

        pnlCardTwoEdit.setBackground(new java.awt.Color(51, 51, 51));

        lblManagerNama.setText("USER MANAGER:EDIT");
        lblManagerNama.setFont(new java.awt.Font("OCR-A BT", 0, 24)); // NOI18N
        lblManagerNama.setForeground(new java.awt.Color(255, 204, 204));

        lblEditIDPetugas.setText("%USR-IDP");
        lblEditIDPetugas.setFont(new java.awt.Font("Century", 0, 24)); // NOI18N
        lblEditIDPetugas.setForeground(new java.awt.Color(255, 255, 255));
        lblEditIDPetugas.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        btnEditLihat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_lihatkendaraan.png"))); // NOI18N
        btnEditLihat.setBackground(new java.awt.Color(6, 17, 50));
        btnEditLihat.setBorderPainted(false);
        btnEditLihat.setContentAreaFilled(false);
        btnEditLihat.setForeground(new java.awt.Color(252, 252, 239));
        btnEditLihat.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_lihatkendaraan_rollover.png"))); // NOI18N
        btnEditLihat.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_lihatkendaraan_rollover.png"))); // NOI18N
        btnEditLihat.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_lihatkendaraan_rollover.png"))); // NOI18N
        btnEditLihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditLihatActionPerformed(evt);
            }
        });

        txtEditNama.setText("Robertus Yudho");
        txtEditNama.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEditNama.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        txtEditNama.setToolTipText("");

        jLabel9.setText("Nama");
        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 204));

        jLabel11.setText("Kelamin");
        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 204));

        rdEditLaki.setBackground(new java.awt.Color(102, 102, 102));
        rdGroupKelamin.add(rdEditLaki);
        rdEditLaki.setForeground(new java.awt.Color(204, 255, 204));
        rdEditLaki.setText("Laki-Laki");

        rdEditPerempuan.setBackground(new java.awt.Color(102, 102, 102));
        rdGroupKelamin.add(rdEditPerempuan);
        rdEditPerempuan.setForeground(new java.awt.Color(204, 255, 204));
        rdEditPerempuan.setText("Perempuan");

        rdEditGaje.setBackground(new java.awt.Color(102, 102, 102));
        rdGroupKelamin.add(rdEditGaje);
        rdEditGaje.setForeground(new java.awt.Color(204, 255, 204));
        rdEditGaje.setText("Tidak Jelas");

        jLabel12.setText("Umur");
        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 204));

        txtEditUmur.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEditUmur.setText("21");
        txtEditUmur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEditUmur.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        txtEditUmur.setToolTipText("");

        jLabel13.setText("Telepon");
        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 204));

        txtEditTelepon.setText("081387018705907");
        txtEditTelepon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEditTelepon.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        txtEditTelepon.setToolTipText("");

        lblEditTotalWaktu.setText("Total Petugas Bekerja 999 Hari ");
        lblEditTotalWaktu.setFont(new java.awt.Font("Century", 0, 18)); // NOI18N
        lblEditTotalWaktu.setForeground(new java.awt.Color(204, 255, 255));
        lblEditTotalWaktu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblEditTotalKendaraan.setText("Total Kendaraan Yang Dilayani 999 Kendaraan");
        lblEditTotalKendaraan.setFont(new java.awt.Font("Century", 0, 18)); // NOI18N
        lblEditTotalKendaraan.setForeground(new java.awt.Color(204, 204, 255));
        lblEditTotalKendaraan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        cbEditAdmin.setText("Admin");
        cbEditAdmin.setBackground(new java.awt.Color(102, 102, 102));
        cbEditAdmin.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbEditAdmin.setForeground(new java.awt.Color(204, 255, 204));

        jLabel14.setText("Tipe Petugas");
        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 204));

        txtEditPassword.setText("jPasswordField1");
        txtEditPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEditPassword.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N

        jLabel15.setText("Pin");
        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 204));

        cbEditPassword.setText("Lihat Pin");
        cbEditPassword.setBackground(new java.awt.Color(102, 102, 102));
        cbEditPassword.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbEditPassword.setForeground(new java.awt.Color(204, 255, 204));
        cbEditPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEditPasswordActionPerformed(evt);
            }
        });

        btnEditPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_edit.png"))); // NOI18N
        btnEditPetugas.setBackground(new java.awt.Color(6, 17, 50));
        btnEditPetugas.setBorderPainted(false);
        btnEditPetugas.setContentAreaFilled(false);
        btnEditPetugas.setForeground(new java.awt.Color(252, 252, 239));
        btnEditPetugas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_edit_rollover.png"))); // NOI18N
        btnEditPetugas.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_edit_rollover.png"))); // NOI18N
        btnEditPetugas.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_edit_rollover.png"))); // NOI18N
        btnEditPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPetugasActionPerformed(evt);
            }
        });

        txtEditKtp.setText("201716120000133");
        txtEditKtp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEditKtp.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        txtEditKtp.setToolTipText("");

        jLabel10.setText("No KTP");
        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 204));

        txaEditAlamat.setColumns(20);
        txaEditAlamat.setRows(5);
        txaEditAlamat.setText("Jalan Mayor Oking 12, Cibinong , Bogor, Jawa Barat.");
        jScrollPane1.setViewportView(txaEditAlamat);

        jLabel16.setText("Alamat");
        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 204));

        javax.swing.GroupLayout pnlCardTwoEditLayout = new javax.swing.GroupLayout(pnlCardTwoEdit);
        pnlCardTwoEdit.setLayout(pnlCardTwoEditLayout);
        pnlCardTwoEditLayout.setHorizontalGroup(
            pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblEditIDPetugas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardTwoEditLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblManagerNama)))
                .addContainerGap())
            .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlCardTwoEditLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel15)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel16))
                        .addGap(19, 19, 19)
                        .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                                        .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                                                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardTwoEditLayout.createSequentialGroup()
                                                        .addComponent(txtEditUmur, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(87, 87, 87)
                                                        .addComponent(jLabel11))
                                                    .addComponent(txtEditTelepon))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                                                        .addComponent(rdEditLaki)
                                                        .addGap(10, 10, 10)
                                                        .addComponent(rdEditPerempuan))
                                                    .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                                                        .addComponent(jLabel14)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(cbEditAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                            .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                                                .addComponent(txtEditPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cbEditPassword)))
                                        .addGap(10, 10, 10)
                                        .addComponent(rdEditGaje)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtEditNama)
                                    .addComponent(txtEditKtp))
                                .addGap(17, 17, 17))
                            .addComponent(jScrollPane1)))
                    .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditTotalKendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditTotalWaktu, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEditLihat, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22))
        );
        pnlCardTwoEditLayout.setVerticalGroup(
            pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblManagerNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblEditIDPetugas)
                .addGap(13, 13, 13)
                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditNama, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditKtp, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtEditUmur, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(rdEditLaki)
                    .addComponent(rdEditPerempuan)
                    .addComponent(rdEditGaje))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(cbEditAdmin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEditPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEditPassword))
                .addGap(18, 18, 18)
                .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlCardTwoEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardTwoEditLayout.createSequentialGroup()
                                .addComponent(lblEditTotalWaktu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEditTotalKendaraan)
                                .addGap(58, 58, 58))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardTwoEditLayout.createSequentialGroup()
                                .addComponent(btnEditLihat, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))))
                    .addGroup(pnlCardTwoEditLayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pnlMain.add(pnlCardTwoEdit, "cardTwoEdit");

        pnlCardTwoDetail.setBackground(new java.awt.Color(51, 51, 51));

        lblManagerNama2.setText("MANAGER KENDARAAN:DETAIL");
        lblManagerNama2.setFont(new java.awt.Font("OCR-A BT", 0, 24)); // NOI18N
        lblManagerNama2.setForeground(new java.awt.Color(255, 204, 204));

        lblDetailIDKendaraan.setText("KND-001");
        lblDetailIDKendaraan.setFont(new java.awt.Font("Century", 0, 24)); // NOI18N
        lblDetailIDKendaraan.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailIDKendaraan.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblDetailJenis.setText("<html>\n<p>S</p>\n<p>E</p>\n<p>P</p>\n<p>E</p>\n<p>D</p>\n<p>A</p>\n</html>");
        lblDetailJenis.setBackground(new java.awt.Color(255, 51, 51));
        lblDetailJenis.setFont(new java.awt.Font("Clear Sans", 0, 24)); // NOI18N
        lblDetailJenis.setForeground(new java.awt.Color(255, 51, 51));
        lblDetailJenis.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jPanel2.setBackground(new java.awt.Color(102, 255, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 153));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(102, 255, 153));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnDetailCheckout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout.png"))); // NOI18N
        btnDetailCheckout.setBackground(new java.awt.Color(0, 0, 204));
        btnDetailCheckout.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 102)));
        btnDetailCheckout.setBorderPainted(false);
        btnDetailCheckout.setContentAreaFilled(false);
        btnDetailCheckout.setForeground(new java.awt.Color(255, 102, 255));
        btnDetailCheckout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetailCheckout.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnDetailCheckout.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnDetailCheckout.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnDetailCheckout.setToolTipText("Jika Kendaraan Ingin Keluar");
        btnDetailCheckout.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnDetailCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailCheckoutActionPerformed(evt);
            }
        });

        btnDetailKembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout.png"))); // NOI18N
        btnDetailKembali.setBackground(new java.awt.Color(0, 0, 204));
        btnDetailKembali.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 102)));
        btnDetailKembali.setBorderPainted(false);
        btnDetailKembali.setContentAreaFilled(false);
        btnDetailKembali.setForeground(new java.awt.Color(255, 102, 255));
        btnDetailKembali.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetailKembali.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnDetailKembali.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnDetailKembali.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnDetailKembali.setToolTipText("Kembali ke sebelumnya..");
        btnDetailKembali.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnDetailKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailKembaliActionPerformed(evt);
            }
        });

        lblDetailMasuk.setText("Waktu Masuk : Senin, 19/09/2017");
        lblDetailMasuk.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        lblDetailMasuk.setForeground(new java.awt.Color(255, 229, 169));

        lblDetailKeluar.setText("Waktu Keluar : Senin, 19/09/2017");
        lblDetailKeluar.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        lblDetailKeluar.setForeground(new java.awt.Color(255, 204, 204));

        lblDetailNoPlat.setText("Nomor Plat : AB 9067 RY");
        lblDetailNoPlat.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        lblDetailNoPlat.setForeground(new java.awt.Color(255, 255, 255));

        lblDetailHarga.setText("Harga Parkir, RP. 5000,00 Rupiah");
        lblDetailHarga.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        lblDetailHarga.setForeground(new java.awt.Color(255, 102, 102));

        javax.swing.GroupLayout pnlCardTwoDetailLayout = new javax.swing.GroupLayout(pnlCardTwoDetail);
        pnlCardTwoDetail.setLayout(pnlCardTwoDetailLayout);
        pnlCardTwoDetailLayout.setHorizontalGroup(
            pnlCardTwoDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardTwoDetailLayout.createSequentialGroup()
                .addGroup(pnlCardTwoDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardTwoDetailLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblManagerNama2)
                        .addGap(11, 11, 11))
                    .addGroup(pnlCardTwoDetailLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(pnlCardTwoDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCardTwoDetailLayout.createSequentialGroup()
                                .addComponent(btnDetailKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pnlCardTwoDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnDetailCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDetailMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDetailKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDetailNoPlat, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDetailHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDetailJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblDetailIDKendaraan, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlCardTwoDetailLayout.setVerticalGroup(
            pnlCardTwoDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardTwoDetailLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblManagerNama2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDetailIDKendaraan)
                .addGap(18, 18, 18)
                .addGroup(pnlCardTwoDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardTwoDetailLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardTwoDetailLayout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardTwoDetailLayout.createSequentialGroup()
                        .addGroup(pnlCardTwoDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlCardTwoDetailLayout.createSequentialGroup()
                                .addGap(234, 234, 234)
                                .addComponent(lblDetailJenis))
                            .addGroup(pnlCardTwoDetailLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(lblDetailMasuk)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDetailKeluar)
                                .addGap(93, 93, 93)
                                .addComponent(lblDetailNoPlat)
                                .addGap(18, 18, 18)
                                .addComponent(lblDetailHarga)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlCardTwoDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnDetailCheckout)
                                    .addComponent(btnDetailKembali))))
                        .addGap(11, 11, 11)))
                .addGap(14, 14, 14))
        );

        pnlMain.add(pnlCardTwoDetail, "cardTwoDetail");

        pnlCardThree.setBackground(new java.awt.Color(51, 51, 51));

        lblLaporanTitle.setText("LAPORAN KENDARAAN");
        lblLaporanTitle.setFont(new java.awt.Font("OCR-A BT", 0, 24)); // NOI18N
        lblLaporanTitle.setForeground(new java.awt.Color(255, 204, 204));

        pnlOtherTable.setBackground(new java.awt.Color(64, 60, 60));

        lblLaporanTableTitle.setText("TABLE HARI INI");
        lblLaporanTableTitle.setForeground(new java.awt.Color(255, 255, 0));

        lblLaporanLihat.setText("Lihat");
        lblLaporanLihat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 255)));
        lblLaporanLihat.setContentAreaFilled(false);
        lblLaporanLihat.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        lblLaporanLihat.setForeground(new java.awt.Color(204, 255, 255));

        pnlLaporanBox.setBackground(new java.awt.Color(64, 60, 60));
        pnlLaporanBox.setMinimumSize(new java.awt.Dimension(310, 378));
        pnlLaporanBox.setLayout(new javax.swing.BoxLayout(pnlLaporanBox, javax.swing.BoxLayout.LINE_AXIS));
        ScrollView2.setViewportView(pnlLaporanBox);

        javax.swing.GroupLayout pnlOtherTableLayout = new javax.swing.GroupLayout(pnlOtherTable);
        pnlOtherTable.setLayout(pnlOtherTableLayout);
        pnlOtherTableLayout.setHorizontalGroup(
            pnlOtherTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtherTableLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlOtherTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOtherTableLayout.createSequentialGroup()
                        .addGroup(pnlOtherTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ScrollView2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlOtherTableLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
                                .addComponent(lblLaporanLihat, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26))
                    .addGroup(pnlOtherTableLayout.createSequentialGroup()
                        .addComponent(lblLaporanTableTitle)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlOtherTableLayout.setVerticalGroup(
            pnlOtherTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtherTableLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblLaporanTableTitle)
                .addGap(18, 18, 18)
                .addComponent(ScrollView2, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLaporanLihat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblLaporanGreen1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLaporanGreen1.setText("Total Motor Hari Ini, 2000 Motor");
        lblLaporanGreen1.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        lblLaporanGreen1.setForeground(new java.awt.Color(204, 255, 204));

        lblLaporanGreen2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLaporanGreen2.setText("Total Mobil Hari Ini, 2000 Mobil");
        lblLaporanGreen2.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        lblLaporanGreen2.setForeground(new java.awt.Color(204, 255, 204));

        lblLaporanGreen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLaporanGreen.setText("Total Sepeda Hari Ini, 2000 Sepeda");
        lblLaporanGreen.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        lblLaporanGreen.setForeground(new java.awt.Color(204, 255, 204));

        lblLaporanYellow1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLaporanYellow1.setText("INFO AGAK PENTING");
        lblLaporanYellow1.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        lblLaporanYellow1.setForeground(new java.awt.Color(255, 255, 102));

        lblLaporanYellow2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLaporanYellow2.setText("INFO AGAK PENTING");
        lblLaporanYellow2.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        lblLaporanYellow2.setForeground(new java.awt.Color(255, 255, 102));

        lblLaporanRed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLaporanRed.setText("SUATU DETAIL PENTING DISINI");
        lblLaporanRed.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        lblLaporanRed.setForeground(new java.awt.Color(255, 51, 0));

        datePick.setText("Hari Ini");
        datePick.setBackground(new java.awt.Color(0, 0, 0));
        datePick.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlCardThreeLayout = new javax.swing.GroupLayout(pnlCardThree);
        pnlCardThree.setLayout(pnlCardThreeLayout);
        pnlCardThreeLayout.setHorizontalGroup(
            pnlCardThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardThreeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardThreeLayout.createSequentialGroup()
                        .addComponent(lblLaporanTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(datePick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCardThreeLayout.createSequentialGroup()
                        .addComponent(pnlOtherTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(pnlCardThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLaporanGreen1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLaporanGreen2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLaporanGreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLaporanYellow1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLaporanYellow2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLaporanRed, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlCardThreeLayout.setVerticalGroup(
            pnlCardThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardThreeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlCardThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLaporanTitle)
                    .addComponent(datePick, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlCardThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardThreeLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(lblLaporanGreen1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLaporanGreen2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLaporanGreen)
                        .addGap(74, 74, 74)
                        .addComponent(lblLaporanYellow1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLaporanYellow2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblLaporanRed)
                        .addGap(59, 59, 59))
                    .addGroup(pnlCardThreeLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pnlOtherTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pnlMain.add(pnlCardThree, "cardThree");

        pnlCardFour.setBackground(new java.awt.Color(51, 51, 51));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel19.setText("ID Petugas");
        jLabel19.setForeground(new java.awt.Color(102, 255, 153));

        jLabel20.setText("Pin Petugas");
        jLabel20.setForeground(new java.awt.Color(102, 255, 153));

        jLabel21.setText("LOGIN");
        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 102, 102));

        btnLoginAction.setText("Login");
        btnLoginAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionActionPerformed(evt);
            }
        });

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/logo.png"))); // NOI18N

        txtLoginPin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtLoginID.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("USR-#000"))));
        txtLoginID.setText("USR-");
        txtLoginID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtLoginID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoginIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLoginAction)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(215, 215, 215)))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLoginPin, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(txtLoginID))
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel21)))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(txtLoginID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtLoginPin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(btnLoginAction)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlCardFourLayout = new javax.swing.GroupLayout(pnlCardFour);
        pnlCardFour.setLayout(pnlCardFourLayout);
        pnlCardFourLayout.setHorizontalGroup(
            pnlCardFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardFourLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213))
        );
        pnlCardFourLayout.setVerticalGroup(
            pnlCardFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardFourLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
        );

        pnlMain.add(pnlCardFour, "cardFour");

        pnlCardFive.setBackground(new java.awt.Color(51, 51, 51));
        pnlCardFive.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlCardFiveMouseClicked(evt);
            }
        });

        lblManagerNama3.setText("CHECKOUT KENDARAAN");
        lblManagerNama3.setFont(new java.awt.Font("OCR-A BT", 0, 24)); // NOI18N
        lblManagerNama3.setForeground(new java.awt.Color(255, 204, 204));

        lblDetailMasuk1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailMasuk1.setText("Waktu Masuk : Senin, 19/09/2017");
        lblDetailMasuk1.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        lblDetailMasuk1.setForeground(new java.awt.Color(255, 229, 169));

        lblDetailKeluar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailKeluar1.setText("Waktu Keluar : Senin, 19/09/2017");
        lblDetailKeluar1.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        lblDetailKeluar1.setForeground(new java.awt.Color(255, 204, 204));

        lblDetailNoPlat1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailNoPlat1.setText("Nomor Plat : AB 9067 RY");
        lblDetailNoPlat1.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        lblDetailNoPlat1.setForeground(new java.awt.Color(255, 255, 255));

        lblDetailHarga1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailHarga1.setText("Harga Parkir, RP. 5000,00 Rupiah");
        lblDetailHarga1.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        lblDetailHarga1.setForeground(new java.awt.Color(255, 102, 102));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        lblDetailNoPlat2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailNoPlat2.setText("Total Hari : 100 Hari");
        lblDetailNoPlat2.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        lblDetailNoPlat2.setForeground(new java.awt.Color(255, 255, 255));

        lblDetailHarga2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailHarga2.setText("Total Pembayaran, RP. 100000,00 Rupiah");
        lblDetailHarga2.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        lblDetailHarga2.setForeground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        jLabel17.setText("Pembayaran : RP.");
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));

        txtCheckoutBayar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        txtCheckoutBayar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCheckoutBayar.setText("0");
        txtCheckoutBayar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCheckoutBayar.setFont(new java.awt.Font("Geometr706 BlkCn BT", 0, 18)); // NOI18N
        txtCheckoutBayar.setToolTipText("Uang Yang Diterima");

        btnCheckoutFail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout.png"))); // NOI18N
        btnCheckoutFail.setBackground(new java.awt.Color(0, 0, 204));
        btnCheckoutFail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 102)));
        btnCheckoutFail.setBorderPainted(false);
        btnCheckoutFail.setContentAreaFilled(false);
        btnCheckoutFail.setForeground(new java.awt.Color(255, 102, 255));
        btnCheckoutFail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCheckoutFail.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnCheckoutFail.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnCheckoutFail.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnCheckoutFail.setToolTipText("Batalkan Transaksi");
        btnCheckoutFail.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCheckoutFail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutFailActionPerformed(evt);
            }
        });

        btnCheckoutGo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout.png"))); // NOI18N
        btnCheckoutGo.setBackground(new java.awt.Color(0, 0, 204));
        btnCheckoutGo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 102)));
        btnCheckoutGo.setBorderPainted(false);
        btnCheckoutGo.setContentAreaFilled(false);
        btnCheckoutGo.setForeground(new java.awt.Color(255, 102, 255));
        btnCheckoutGo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCheckoutGo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnCheckoutGo.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnCheckoutGo.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnCheckoutGo.setToolTipText("Validasi Transaksi");
        btnCheckoutGo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCheckoutGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutGoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCardFiveLayout = new javax.swing.GroupLayout(pnlCardFive);
        pnlCardFive.setLayout(pnlCardFiveLayout);
        pnlCardFiveLayout.setHorizontalGroup(
            pnlCardFiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardFiveLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(btnCheckoutGo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCheckoutFail, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
            .addGroup(pnlCardFiveLayout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addGroup(pnlCardFiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardFiveLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(lblManagerNama3))
                    .addGroup(pnlCardFiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblDetailNoPlat1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDetailHarga2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDetailMasuk1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDetailKeluar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDetailHarga1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDetailNoPlat2, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCardFiveLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(pnlCardFiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCardFiveLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtCheckoutBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel17))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlCardFiveLayout.setVerticalGroup(
            pnlCardFiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardFiveLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblManagerNama3)
                .addGap(33, 33, 33)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblDetailMasuk1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDetailKeluar1)
                .addGap(18, 18, 18)
                .addComponent(lblDetailNoPlat1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDetailHarga1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDetailNoPlat2)
                .addGap(18, 18, 18)
                .addComponent(lblDetailHarga2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCheckoutBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCardFiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCheckoutFail)
                    .addComponent(btnCheckoutGo))
                .addGap(25, 25, 25))
        );

        pnlMain.add(pnlCardFive, "cardFive");

        pnlCardSix.setBackground(new java.awt.Color(51, 51, 51));

        rdGroupCheckin.add(rdCheckinMobil);
        rdCheckinMobil.setSelected(true);
        rdCheckinMobil.setText("Mobil");
        rdCheckinMobil.setBackground(new java.awt.Color(51, 51, 51));
        rdCheckinMobil.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        rdCheckinMobil.setForeground(new java.awt.Color(51, 204, 255));

        rdGroupCheckin.add(rdCheckinMotor);
        rdCheckinMotor.setText("Motor");
        rdCheckinMotor.setBackground(new java.awt.Color(51, 51, 51));
        rdCheckinMotor.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        rdCheckinMotor.setForeground(new java.awt.Color(51, 204, 255));

        rdGroupCheckin.add(rdCheckinSepeda);
        rdCheckinSepeda.setText("Sepeda");
        rdCheckinSepeda.setBackground(new java.awt.Color(51, 51, 51));
        rdCheckinSepeda.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        rdCheckinSepeda.setForeground(new java.awt.Color(51, 204, 255));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/logo.png"))); // NOI18N

        jLabel23.setText("CHECKIN");
        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 102, 102));

        lblLaporanYellow3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLaporanYellow3.setText("TANGGAL, Senin 20/19/2019");
        lblLaporanYellow3.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        lblLaporanYellow3.setForeground(new java.awt.Color(255, 255, 102));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblLaporanYellow3, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblLaporanYellow3)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jLabel23))
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtCheckin.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtCheckin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCheckinActionPerformed(evt);
            }
        });

        btnCheckin.setText("Check In");
        btnCheckin.setBackground(new java.awt.Color(51, 51, 51));
        btnCheckin.setBorderPainted(false);
        btnCheckin.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        btnCheckin.setForeground(new java.awt.Color(255, 51, 51));
        btnCheckin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckinActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("No Identitas <Plat / KTP>:");

        javax.swing.GroupLayout pnlCardSixLayout = new javax.swing.GroupLayout(pnlCardSix);
        pnlCardSix.setLayout(pnlCardSixLayout);
        pnlCardSixLayout.setHorizontalGroup(
            pnlCardSixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardSixLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(pnlCardSixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(rdCheckinSepeda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdCheckinMotor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdCheckinMobil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(67, 67, 67)
                .addGroup(pnlCardSixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardSixLayout.createSequentialGroup()
                        .addGroup(pnlCardSixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCheckin)
                            .addComponent(btnCheckin))
                        .addGap(89, 89, 89))
                    .addGroup(pnlCardSixLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlCardSixLayout.setVerticalGroup(
            pnlCardSixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardSixLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlCardSixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardSixLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(rdCheckinMobil)
                        .addGap(18, 18, 18)
                        .addComponent(rdCheckinMotor)
                        .addGap(18, 18, 18)
                        .addComponent(rdCheckinSepeda)
                        .addGap(209, 209, 209))
                    .addGroup(pnlCardSixLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckin)
                        .addGap(248, 248, 248))))
        );

        pnlMain.add(pnlCardSix, "cardSix");

        pnlSidebar.setBackground(new java.awt.Color(245, 245, 245));

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/login.png"))); // NOI18N

        btnLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_login.png"))); // NOI18N
        btnLog.setBackground(new java.awt.Color(6, 17, 50));
        btnLog.setBorderPainted(false);
        btnLog.setContentAreaFilled(false);
        btnLog.setForeground(new java.awt.Color(252, 252, 239));
        btnLog.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_login_rollover.png"))); // NOI18N
        btnLog.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_login_rollover.png"))); // NOI18N
        btnLog.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_login_rollover.png"))); // NOI18N
        btnLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogActionPerformed(evt);
            }
        });

        btnManager.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_manager.png"))); // NOI18N
        btnManager.setBackground(new java.awt.Color(6, 17, 50));
        btnManager.setBorderPainted(false);
        btnManager.setContentAreaFilled(false);
        btnManager.setForeground(new java.awt.Color(252, 252, 239));
        btnManager.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_manager_rollover.png"))); // NOI18N
        btnManager.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_manager_rollover.png"))); // NOI18N
        btnManager.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_manager_rollover.png"))); // NOI18N
        btnManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagerActionPerformed(evt);
            }
        });

        btnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_laporan.png"))); // NOI18N
        btnLaporan.setBackground(new java.awt.Color(6, 17, 50));
        btnLaporan.setBorderPainted(false);
        btnLaporan.setContentAreaFilled(false);
        btnLaporan.setForeground(new java.awt.Color(252, 252, 239));
        btnLaporan.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_laporan_rollover.png"))); // NOI18N
        btnLaporan.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_laporan_rollover.png"))); // NOI18N
        btnLaporan.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_laporan_rollover.png"))); // NOI18N
        btnLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSidebarLayout = new javax.swing.GroupLayout(pnlSidebar);
        pnlSidebar.setLayout(pnlSidebarLayout);
        pnlSidebarLayout.setHorizontalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLog, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblLogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(btnManager, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        pnlSidebarLayout.setVerticalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lblLogo)
                .addGap(18, 18, 18)
                .addComponent(btnManager, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLog, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        if(AkunAktif == null)
            JOptionPane.showMessageDialog(this, "ANDA HARUS LOGIN TERLEBIH DAHULU","BELUM LOGIN",JOptionPane.ERROR_MESSAGE);
        else {
            card.show(pnlMain, "cardThree");
            if(AkunAktif.isTipeadmin())
            {
                
            } else {
                
            }
        }
    }//GEN-LAST:event_btnLaporanActionPerformed

    private void btnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogActionPerformed
        if(AkunAktif!=null)
        {
            btnLog.setIcon(new ImageIcon(getClass().getResource("images/btn_login.png")));
            btnLog.setSelectedIcon(new ImageIcon(getClass().getResource("images/btn_login_rollover.png")));
            btnLog.setRolloverIcon(new ImageIcon(getClass().getResource("images/btn_login_rollover.png")));
            
            lblLogo.setIcon(new ImageIcon(getClass().getResource("images/login.png")));
            CardLayout card = (CardLayout) pnlMain.getLayout();
            card.show(pnlMain, "cardOne");
            AkunAktif = null;
        }
        else
        {
            CardLayout card = (CardLayout) pnlMain.getLayout();
            card.show(pnlMain, "cardFour");
        }
    }//GEN-LAST:event_btnLogActionPerformed

    private void btnManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagerActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        
        if(AkunAktif == null)
            JOptionPane.showMessageDialog(this, "ANDA HARUS LOGIN TERLEBIH DAHULU","BELUM LOGIN",JOptionPane.ERROR_MESSAGE);
        else if(AkunAktif.isTipeadmin()){
            card.show(pnlMain, "cardTwo");
            ShowAdmin(ac.AmbilSemuaPetugas(), card);
        }
        else{
            card.show(pnlMain, "cardTwo");
            ShowKendaraan(kc.AmbilSemuaKendaraan(), card);
        }
    }//GEN-LAST:event_btnManagerActionPerformed

    private void btnEditLihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditLihatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditLihatActionPerformed

    private void cbEditPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEditPasswordActionPerformed
        if(cbEditPassword.isSelected())
            txtEditPassword.setEchoChar((char)0);
        else
            txtEditPassword.setEchoChar((char)'*');
    }//GEN-LAST:event_cbEditPasswordActionPerformed

    private void btnEditPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPetugasActionPerformed
        if(action.equalsIgnoreCase(""))
        {
            EnableEdit(true);
            btnEditPetugas.setIcon(new ImageIcon(getClass().getResource("images/btn_editselesai.png")));
            btnEditPetugas.setSelectedIcon(new ImageIcon(getClass().getResource("images/btn_editselesai_rollover.png")));
            btnEditPetugas.setRolloverIcon(new ImageIcon(getClass().getResource("images/btn_editselesai_rollover.png")));
        }
        else if(action.equalsIgnoreCase("EditPetugas"))
        {
            InfoAdmin info = new InfoAdmin(txtEditNama.getText(), txtEditKtp.getText(),
                    txaEditAlamat.getText(), txtEditTelepon.getText(),
                    getrdGroupKelamin() , Integer.parseInt(txtEditUmur.getText()));
            
            Admin admin = adtemp;
            admin.setId(Integer.parseInt(lblEditIDPetugas.getText().replaceFirst("USR-", "")));
            admin.setInfo(info);
            admin.setPin(String.valueOf(txtEditPassword.getPassword()));
            admin.setTipeadmin(cbEditAdmin.isSelected());
            ac.EditPetugas(admin);
            
            btnManagerActionPerformed(evt);
        }
        else if(action.equalsIgnoreCase("TambahPetugas"))
        {
            InfoAdmin info = new InfoAdmin(txtEditNama.getText(), txtEditKtp.getText(),
                    txaEditAlamat.getText(), txtEditTelepon.getText(),
                    getrdGroupKelamin() , Integer.parseInt(txtEditUmur.getText()));
            
            Admin admin = new Admin(0, info, "", 0, 0, false);
            admin.setPin(String.valueOf(txtEditPassword.getPassword()));
            admin.setTipeadmin(cbEditAdmin.isSelected());
            ac.TambahPetugas(admin);
            
            btnManagerActionPerformed(evt);
        }
    }//GEN-LAST:event_btnEditPetugasActionPerformed

    private void btnLoginActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionActionPerformed
        Admin logon = ac.LoginPetugas(  Integer.parseInt(txtLoginID.getValue().toString()), String.valueOf(txtLoginPin.getPassword()));
        if(logon!=(null))
        {
            AkunAktif = logon;
            JOptionPane.showMessageDialog(this, "BERHASIL LOGIN !!!");
            
            btnLog.setIcon(new ImageIcon(getClass().getResource("images/btn_logout.png")));
            btnLog.setSelectedIcon(new ImageIcon(getClass().getResource("images/btn_logout_rollover.png")));
            btnLog.setRolloverIcon(new ImageIcon(getClass().getResource("images/btn_logout_rollover.png")));
            
            if(logon.isTipeadmin())
                lblLogo.setIcon(new ImageIcon(getClass().getResource("images/login_admin.png")));
            else
                lblLogo.setIcon(new ImageIcon(getClass().getResource("images/login_petugas.png")));
        }
        else
            JOptionPane.showMessageDialog(this, "ID ATAU PIN SALAH !!!"); 
    }//GEN-LAST:event_btnLoginActionActionPerformed

    private void txtLoginIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoginIDActionPerformed
        
    }//GEN-LAST:event_txtLoginIDActionPerformed

    private void btnDetailCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailCheckoutActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        card.show(pnlMain, "cardFive");
        ChangeCheckout(ktemp);
    }//GEN-LAST:event_btnDetailCheckoutActionPerformed

    private void btnDetailKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailKembaliActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        card.show(pnlMain, "cardTwo");
    }//GEN-LAST:event_btnDetailKembaliActionPerformed

    private void pnlMainComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlMainComponentMoved

    }//GEN-LAST:event_pnlMainComponentMoved

    private void pnlMainComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlMainComponentResized
        
    }//GEN-LAST:event_pnlMainComponentResized

    private void btnCheckoutFailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutFailActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        card.show(pnlMain, "cardTwoDetail");
    }//GEN-LAST:event_btnCheckoutFailActionPerformed

    private void btnCheckoutGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutGoActionPerformed
        double harga = 0;
        switch(ktemp.getJenis()){
            case 1: harga = ((Mobil) ktemp).getHarga();break;
            case 2: harga = ((Motor) ktemp).getHarga();break;
            default: harga = ((Sepeda) ktemp).getHarga();break;
        }
        if(harga > Double.parseDouble(txtCheckoutBayar.getValue().toString())){
            JOptionPane.showMessageDialog(this, "Uang Pelanggan Kurang !!!", "KURANG",JOptionPane.ERROR_MESSAGE);
        } else {
            harga = Double.parseDouble(txtCheckoutBayar.getValue().toString()) - harga;
            JOptionPane.showMessageDialog(this, String.format("Kembalian : RP. %.2f Rupiah", harga), "Total Kembalian",JOptionPane.INFORMATION_MESSAGE);
            java.util.Date dtNow = new java.util.Date();
            ktemp.setWaktu_keluar(new Date(dtNow.getTime()));
            ltemp.setJmlKendaraan(ltemp.getJmlKendaraan()+1);
            ltemp.setTotalHarga(harga+ltemp.getTotalHarga());
            lc.EditLaporan(ltemp);
            kc.EditKendaraan(ktemp);
            kc.SetIDLaporan(ktemp.getId(), ltemp.getIdLaporan());
            btnManagerActionPerformed(evt);
        }
    
    }//GEN-LAST:event_btnCheckoutGoActionPerformed

    private void pnlCardFiveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCardFiveMouseClicked
        pnlCardFive.requestFocus();
    }//GEN-LAST:event_pnlCardFiveMouseClicked

    private void btnManagerTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagerTambahActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        if(AkunAktif.isTipeadmin())
        {
            card.show(pnlMain, "cardTwoEdit");
            ChangeTambah();
        }
        else
        {
            card.show(pnlMain, "cardSix");
        }

    }//GEN-LAST:event_btnManagerTambahActionPerformed

    private void ScrollViewMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_ScrollViewMouseWheelMoved

    }//GEN-LAST:event_ScrollViewMouseWheelMoved

    private void btnManagerSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagerSearchActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        if(AkunAktif.isTipeadmin())
            ShowAdmin(ac.AmbilPetugasNama(txtManagerSearch.getText()), card);
        else
            ShowKendaraan(kc.AmbilKendaraanPlat(txtManagerSearch.getText()), card);

        pnlBox.revalidate();
        pnlBox.repaint();
    }//GEN-LAST:event_btnManagerSearchActionPerformed

    private void rdManagerSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdManagerSemuaActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        if(AkunAktif.isTipeadmin())
            ShowAdmin(ac.AmbilSemuaPetugas(), card);
        else
            ShowKendaraan(kc.AmbilSemuaKendaraan(), card);
        
        pnlBox.revalidate();
        pnlBox.repaint();
    }//GEN-LAST:event_rdManagerSemuaActionPerformed

    private void rdManager1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdManager1ActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        if(AkunAktif.isTipeadmin())
            ShowAdmin(ac.AmbilPetugasJenis(true), card);
        else
            ShowKendaraan(kc.AmbilKendaraanJenis(1), card);
       
    }//GEN-LAST:event_rdManager1ActionPerformed

    private void rdManager2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdManager2ActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        if(AkunAktif.isTipeadmin())
            ShowAdmin(ac.AmbilPetugasJenis(false), card);
        else
            ShowKendaraan(kc.AmbilKendaraanJenis(2), card);        
    }//GEN-LAST:event_rdManager2ActionPerformed

    private void rdManager4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdManager4ActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        ShowKendaraan(kc.AmbilKendaraanJenis(3), card);
    }//GEN-LAST:event_rdManager4ActionPerformed

    private void txtCheckinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCheckinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCheckinActionPerformed

    private void btnCheckinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckinActionPerformed
        Kendaraan K;
        if(rdCheckinMobil.isSelected())
            K = new Mobil(txtCheckin.getText(), 1);
        else if(rdCheckinMotor.isSelected())
            K = new Motor(txtCheckin.getText(), 2);
        else
            K = new Sepeda(txtCheckin.getText(), 3);
        kc.TambahKendaraan(K,AkunAktif.getId());
        JOptionPane.showMessageDialog(this, "SUCCESS CHECKIN KENDARAAN");
        btnManagerActionPerformed(evt);
    }//GEN-LAST:event_btnCheckinActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollView;
    private javax.swing.JScrollPane ScrollView2;
    private javax.swing.JButton btnCheckin;
    private javax.swing.JButton btnCheckoutFail;
    private javax.swing.JButton btnCheckoutGo;
    private javax.swing.JButton btnDetailCheckout;
    private javax.swing.JButton btnDetailKembali;
    private javax.swing.JButton btnEditLihat;
    private javax.swing.JButton btnEditPetugas;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnLog;
    private javax.swing.JButton btnLoginAction;
    private javax.swing.JButton btnManager;
    private javax.swing.JButton btnManagerSearch;
    private javax.swing.JButton btnManagerTambah;
    private javax.swing.JCheckBox cbEditAdmin;
    private javax.swing.JCheckBox cbEditPassword;
    private com.github.lgooddatepicker.components.DatePicker datePick;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDetailHarga;
    private javax.swing.JLabel lblDetailHarga1;
    private javax.swing.JLabel lblDetailHarga2;
    private javax.swing.JLabel lblDetailIDKendaraan;
    private javax.swing.JLabel lblDetailJenis;
    private javax.swing.JLabel lblDetailKeluar;
    private javax.swing.JLabel lblDetailKeluar1;
    private javax.swing.JLabel lblDetailMasuk;
    private javax.swing.JLabel lblDetailMasuk1;
    private javax.swing.JLabel lblDetailNoPlat;
    private javax.swing.JLabel lblDetailNoPlat1;
    private javax.swing.JLabel lblDetailNoPlat2;
    private javax.swing.JLabel lblEditIDPetugas;
    private javax.swing.JLabel lblEditTotalKendaraan;
    private javax.swing.JLabel lblEditTotalWaktu;
    private javax.swing.JLabel lblLaporanGreen;
    private javax.swing.JLabel lblLaporanGreen1;
    private javax.swing.JLabel lblLaporanGreen2;
    private javax.swing.JButton lblLaporanLihat;
    private javax.swing.JLabel lblLaporanRed;
    private javax.swing.JLabel lblLaporanTableTitle;
    private javax.swing.JLabel lblLaporanTitle;
    private javax.swing.JLabel lblLaporanYellow1;
    private javax.swing.JLabel lblLaporanYellow2;
    private javax.swing.JLabel lblLaporanYellow3;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblManager;
    private javax.swing.JLabel lblManagerNama;
    private javax.swing.JLabel lblManagerNama2;
    private javax.swing.JLabel lblManagerNama3;
    private javax.swing.JPanel pnlBar1;
    private javax.swing.JPanel pnlBox;
    private javax.swing.JPanel pnlCardFive;
    private javax.swing.JPanel pnlCardFour;
    private javax.swing.JPanel pnlCardOne;
    private javax.swing.JPanel pnlCardSix;
    private javax.swing.JPanel pnlCardThree;
    private javax.swing.JPanel pnlCardTwo;
    private javax.swing.JPanel pnlCardTwoDetail;
    private javax.swing.JPanel pnlCardTwoEdit;
    private javax.swing.JPanel pnlCreator;
    private javax.swing.JPanel pnlLaporanBox;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlOtherTable;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JRadioButton rdCheckinMobil;
    private javax.swing.JRadioButton rdCheckinMotor;
    private javax.swing.JRadioButton rdCheckinSepeda;
    private javax.swing.JRadioButton rdEditGaje;
    private javax.swing.JRadioButton rdEditLaki;
    private javax.swing.JRadioButton rdEditPerempuan;
    private javax.swing.ButtonGroup rdGroupCheckin;
    private javax.swing.ButtonGroup rdGroupKelamin;
    private javax.swing.ButtonGroup rdGroupManager;
    private javax.swing.JRadioButton rdManager1;
    private javax.swing.JRadioButton rdManager2;
    private javax.swing.JRadioButton rdManager4;
    private javax.swing.JRadioButton rdManagerSemua;
    private javax.swing.JTextArea txaEditAlamat;
    private javax.swing.JTextField txtCheckin;
    private javax.swing.JFormattedTextField txtCheckoutBayar;
    private javax.swing.JTextField txtEditKtp;
    private javax.swing.JTextField txtEditNama;
    private javax.swing.JPasswordField txtEditPassword;
    private javax.swing.JTextField txtEditTelepon;
    private javax.swing.JTextField txtEditUmur;
    private javax.swing.JFormattedTextField txtLoginID;
    private javax.swing.JPasswordField txtLoginPin;
    private javax.swing.JTextField txtManagerSearch;
    // End of variables declaration//GEN-END:variables
}
