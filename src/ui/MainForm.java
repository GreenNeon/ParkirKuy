/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import control.*;
import entity.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.hsqldb.rights.Right;

/**
 *
 * @author Yudho
 */
public class MainForm extends javax.swing.JFrame {

    private static int cPetugas = 0;
    private AdminControl ac;
    private KendaraanControl kc;
    
    private Admin adtemp;
    private Kendaraan ktemp;
    private Admin AkunAktif;
    private String action = "";
    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        ac = new AdminControl();
        kc = new KendaraanControl();
        ScrollView.getVerticalScrollBar().setUnitIncrement(16);
        anilabel();
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
    private void anilabel(){
        Thread thread = new Thread(){
          public void run(){
              while(true){
                    lblNoticeManager.setForeground(Color.GREEN);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {

                    }
                    lblNoticeManager.setForeground(Color.YELLOW);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {

                    }
              }
          }  
        };
        thread.start();
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
        
        lblManager.setText("MANAGER KENDARAAN");
        btnManagerTambah.setIcon(new ImageIcon(getClass().getResource("images/btn_checkin.png")));
        btnManagerTambah.setSelectedIcon(new ImageIcon(getClass().getResource("images/btn_checkin_rollover.png")));
        btnManagerTambah.setRolloverIcon(new ImageIcon(getClass().getResource("images/btn_checkin_rollover.png")));
        
        for(Kendaraan item : data){
            PanelKendaraan pk;
            switch(item.getJenis()){
                case 1:
                    pk = new PanelKendaraan((Mobil) item);
                    break;
                case 2:
                    pk = new PanelKendaraan((Motor) item);
                    break;
                default:
                    pk = new PanelKendaraan((Sepeda) item);
                    break;
            }
            
            pk.addMouseListener(new MouseAdapter() { 
                @Override
                public void mousePressed(MouseEvent me) { 
                  card.show(pnlMain, "cardTwoEdit");
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

        dlgLogin = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        txtLogid = new javax.swing.JTextField();
        txtLogpass = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        rdGroupKelamin = new javax.swing.ButtonGroup();
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
        lblNoticeManager = new javax.swing.JLabel();
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
        pnlCardThree = new javax.swing.JPanel();
        lblManagerNama1 = new javax.swing.JLabel();
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
        pnlBorder = new javax.swing.JPanel();
        btnKendaraanCheckIn = new javax.swing.JButton();
        btnKendaraanCheckOut = new javax.swing.JButton();
        txtKendaraanSearch = new javax.swing.JTextField();
        ScrollViewKendaraan = new javax.swing.JScrollPane();
        pnlBoxKendaraan = new javax.swing.JPanel();
        pnlSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnLog = new javax.swing.JButton();
        btnManager = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();

        dlgLogin.setTitle("ParkirKuy: Login");
        dlgLogin.setAlwaysOnTop(true);
        dlgLogin.setBackground(new java.awt.Color(0, 0, 0));
        dlgLogin.setMinimumSize(new java.awt.Dimension(449, 241));
        dlgLogin.setSize(new java.awt.Dimension(449, 255));
        dlgLogin.setType(java.awt.Window.Type.POPUP);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        txtLogid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtLogpass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ID Petugas");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Password");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnLogin.setText("LOGIN");

        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("LOGIN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnLogin)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLogid)
                            .addComponent(txtLogpass, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(56, 56, 56))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLogid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLogpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogin)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dlgLoginLayout = new javax.swing.GroupLayout(dlgLogin.getContentPane());
        dlgLogin.getContentPane().setLayout(dlgLoginLayout);
        dlgLoginLayout.setHorizontalGroup(
            dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgLoginLayout.setVerticalGroup(
            dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ParkirKuy | Manager");
        setBackground(new java.awt.Color(255, 255, 204));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName("frmMain"); // NOI18N

        pnlMain.setBackground(new java.awt.Color(51, 51, 51));
        pnlMain.setLayout(new java.awt.CardLayout(20, 20));

        pnlCardOne.setBackground(new java.awt.Color(51, 51, 51));

        pnlCreator.setBackground(new java.awt.Color(51, 51, 51));
        pnlCreator.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.darkGray, java.awt.Color.black, java.awt.Color.black, java.awt.Color.darkGray));

        jLabel1.setFont(new java.awt.Font("Segoe UI Historic", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/logo.png"))); // NOI18N
        jLabel1.setText("ParkirKuy");

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

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Uli Wijayanti");

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));
        jLabel3.setText("Bryan B");

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 204));
        jLabel4.setText("Penta Putra");

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 204));
        jLabel6.setText("Robertus Yudho");

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
                .addContainerGap(354, Short.MAX_VALUE)
                .addComponent(pnlCreator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        pnlCardOneLayout.setVerticalGroup(
            pnlCardOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardOneLayout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addComponent(pnlCreator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        pnlMain.add(pnlCardOne, "cardOne");

        pnlCardTwo.setBackground(new java.awt.Color(51, 51, 51));
        pnlCardTwo.setForeground(new java.awt.Color(255, 255, 255));

        lblManager.setFont(new java.awt.Font("OCR-A BT", 0, 24)); // NOI18N
        lblManager.setForeground(new java.awt.Color(255, 204, 204));
        lblManager.setText("USER MANAGER");

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

        btnManagerTambah.setBackground(new java.awt.Color(6, 17, 50));
        btnManagerTambah.setForeground(new java.awt.Color(252, 252, 239));
        btnManagerTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah.png"))); // NOI18N
        btnManagerTambah.setBorderPainted(false);
        btnManagerTambah.setContentAreaFilled(false);
        btnManagerTambah.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnManagerTambah.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnManagerTambah.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnManagerTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagerTambahActionPerformed(evt);
            }
        });

        lblNoticeManager.setForeground(new java.awt.Color(51, 255, 0));
        lblNoticeManager.setText("KLIK TABLE UNTUK MASUK KE DETAIL DAN EDIT");

        javax.swing.GroupLayout pnlCardTwoLayout = new javax.swing.GroupLayout(pnlCardTwo);
        pnlCardTwo.setLayout(pnlCardTwoLayout);
        pnlCardTwoLayout.setHorizontalGroup(
            pnlCardTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardTwoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardTwoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblNoticeManager)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 239, Short.MAX_VALUE)
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
                .addComponent(ScrollView, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnManagerSearch)
                        .addComponent(txtManagerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblNoticeManager))
                .addContainerGap())
        );

        pnlMain.add(pnlCardTwo, "cardTwo");

        pnlCardTwoEdit.setBackground(new java.awt.Color(51, 51, 51));

        lblManagerNama.setFont(new java.awt.Font("OCR-A BT", 0, 24)); // NOI18N
        lblManagerNama.setForeground(new java.awt.Color(255, 204, 204));
        lblManagerNama.setText("USER MANAGER:EDIT");

        lblEditIDPetugas.setFont(new java.awt.Font("Century", 0, 24)); // NOI18N
        lblEditIDPetugas.setForeground(new java.awt.Color(255, 255, 255));
        lblEditIDPetugas.setText("%USR-IDP");
        lblEditIDPetugas.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        btnEditLihat.setBackground(new java.awt.Color(6, 17, 50));
        btnEditLihat.setForeground(new java.awt.Color(252, 252, 239));
        btnEditLihat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_lihatkendaraan.png"))); // NOI18N
        btnEditLihat.setBorderPainted(false);
        btnEditLihat.setContentAreaFilled(false);
        btnEditLihat.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_lihatkendaraan_rollover.png"))); // NOI18N
        btnEditLihat.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_lihatkendaraan_rollover.png"))); // NOI18N
        btnEditLihat.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_lihatkendaraan_rollover.png"))); // NOI18N
        btnEditLihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditLihatActionPerformed(evt);
            }
        });

        txtEditNama.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        txtEditNama.setText("Robertus Yudho");
        txtEditNama.setToolTipText("");
        txtEditNama.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 204));
        jLabel9.setText("Nama");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 204));
        jLabel11.setText("Kelamin");

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

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 204));
        jLabel12.setText("Umur");

        txtEditUmur.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        txtEditUmur.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEditUmur.setText("21");
        txtEditUmur.setToolTipText("");
        txtEditUmur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 204));
        jLabel13.setText("Telepon");

        txtEditTelepon.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        txtEditTelepon.setText("081387018705907");
        txtEditTelepon.setToolTipText("");
        txtEditTelepon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblEditTotalWaktu.setFont(new java.awt.Font("Century", 0, 18)); // NOI18N
        lblEditTotalWaktu.setForeground(new java.awt.Color(204, 255, 255));
        lblEditTotalWaktu.setText("Total Petugas Bekerja 999 Hari ");
        lblEditTotalWaktu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblEditTotalKendaraan.setFont(new java.awt.Font("Century", 0, 18)); // NOI18N
        lblEditTotalKendaraan.setForeground(new java.awt.Color(204, 204, 255));
        lblEditTotalKendaraan.setText("Total Kendaraan Yang Dilayani 999 Kendaraan");
        lblEditTotalKendaraan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        cbEditAdmin.setBackground(new java.awt.Color(102, 102, 102));
        cbEditAdmin.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbEditAdmin.setForeground(new java.awt.Color(204, 255, 204));
        cbEditAdmin.setText("Admin");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 204));
        jLabel14.setText("Tipe Petugas");

        txtEditPassword.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        txtEditPassword.setText("jPasswordField1");
        txtEditPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 204));
        jLabel15.setText("Pin");

        cbEditPassword.setBackground(new java.awt.Color(102, 102, 102));
        cbEditPassword.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbEditPassword.setForeground(new java.awt.Color(204, 255, 204));
        cbEditPassword.setText("Lihat Pin");
        cbEditPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEditPasswordActionPerformed(evt);
            }
        });

        btnEditPetugas.setBackground(new java.awt.Color(6, 17, 50));
        btnEditPetugas.setForeground(new java.awt.Color(252, 252, 239));
        btnEditPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_edit.png"))); // NOI18N
        btnEditPetugas.setBorderPainted(false);
        btnEditPetugas.setContentAreaFilled(false);
        btnEditPetugas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_edit_rollover.png"))); // NOI18N
        btnEditPetugas.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_edit_rollover.png"))); // NOI18N
        btnEditPetugas.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_edit_rollover.png"))); // NOI18N
        btnEditPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPetugasActionPerformed(evt);
            }
        });

        txtEditKtp.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        txtEditKtp.setText("201716120000133");
        txtEditKtp.setToolTipText("");
        txtEditKtp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 204));
        jLabel10.setText("No KTP");

        txaEditAlamat.setColumns(20);
        txaEditAlamat.setRows(5);
        txaEditAlamat.setText("Jalan Mayor Oking 12, Cibinong , Bogor, Jawa Barat.");
        jScrollPane1.setViewportView(txaEditAlamat);

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 204));
        jLabel16.setText("Alamat");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
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

        pnlCardThree.setBackground(new java.awt.Color(51, 51, 51));
        pnlCardThree.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblManagerNama1.setFont(new java.awt.Font("OCR-A BT", 0, 24)); // NOI18N
        lblManagerNama1.setForeground(new java.awt.Color(255, 204, 204));
        lblManagerNama1.setText("LAPORAN USER");

        javax.swing.GroupLayout pnlCardThreeLayout = new javax.swing.GroupLayout(pnlCardThree);
        pnlCardThree.setLayout(pnlCardThreeLayout);
        pnlCardThreeLayout.setHorizontalGroup(
            pnlCardThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardThreeLayout.createSequentialGroup()
                .addContainerGap(540, Short.MAX_VALUE)
                .addComponent(lblManagerNama1)
                .addGap(23, 23, 23))
        );
        pnlCardThreeLayout.setVerticalGroup(
            pnlCardThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardThreeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblManagerNama1)
                .addContainerGap(513, Short.MAX_VALUE))
        );

        pnlMain.add(pnlCardThree, "cardThree");

        pnlCardFour.setBackground(new java.awt.Color(51, 51, 51));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel19.setForeground(new java.awt.Color(102, 255, 153));
        jLabel19.setText("ID Petugas");

        jLabel20.setForeground(new java.awt.Color(102, 255, 153));
        jLabel20.setText("Pin Petugas");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 102, 102));
        jLabel21.setText("LOGIN");

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
                .addContainerGap(214, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213))
        );
        pnlCardFourLayout.setVerticalGroup(
            pnlCardFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardFourLayout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
        );

        pnlMain.add(pnlCardFour, "cardFour");

        pnlCardFive.setBackground(new java.awt.Color(51, 51, 51));

        pnlBorder.setBackground(new java.awt.Color(20, 19, 19));

        javax.swing.GroupLayout pnlBorderLayout = new javax.swing.GroupLayout(pnlBorder);
        pnlBorder.setLayout(pnlBorderLayout);
        pnlBorderLayout.setHorizontalGroup(
            pnlBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlBorderLayout.setVerticalGroup(
            pnlBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        btnKendaraanCheckIn.setBackground(new java.awt.Color(6, 17, 50));
        btnKendaraanCheckIn.setForeground(new java.awt.Color(252, 252, 239));
        btnKendaraanCheckIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah.png"))); // NOI18N
        btnKendaraanCheckIn.setBorderPainted(false);
        btnKendaraanCheckIn.setContentAreaFilled(false);
        btnKendaraanCheckIn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnKendaraanCheckIn.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnKendaraanCheckIn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnKendaraanCheckIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKendaraanCheckInActionPerformed(evt);
            }
        });

        btnKendaraanCheckOut.setBackground(new java.awt.Color(6, 17, 50));
        btnKendaraanCheckOut.setForeground(new java.awt.Color(252, 252, 239));
        btnKendaraanCheckOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah.png"))); // NOI18N
        btnKendaraanCheckOut.setBorderPainted(false);
        btnKendaraanCheckOut.setContentAreaFilled(false);
        btnKendaraanCheckOut.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnKendaraanCheckOut.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnKendaraanCheckOut.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_tambah_rollover.png"))); // NOI18N
        btnKendaraanCheckOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKendaraanCheckOutActionPerformed(evt);
            }
        });

        txtKendaraanSearch.setBackground(new java.awt.Color(0, 0, 0));
        txtKendaraanSearch.setForeground(new java.awt.Color(204, 255, 204));
        txtKendaraanSearch.setText("Search Box");

        ScrollViewKendaraan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 0, 51)));

        pnlBoxKendaraan.setBackground(new java.awt.Color(51, 51, 51));
        pnlBoxKendaraan.setLayout(new javax.swing.BoxLayout(pnlBoxKendaraan, javax.swing.BoxLayout.Y_AXIS));
        ScrollViewKendaraan.setViewportView(pnlBoxKendaraan);

        javax.swing.GroupLayout pnlCardFiveLayout = new javax.swing.GroupLayout(pnlCardFive);
        pnlCardFive.setLayout(pnlCardFiveLayout);
        pnlCardFiveLayout.setHorizontalGroup(
            pnlCardFiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBorder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlCardFiveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtKendaraanSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 357, Short.MAX_VALUE)
                .addComponent(btnKendaraanCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnKendaraanCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(ScrollViewKendaraan)
        );
        pnlCardFiveLayout.setVerticalGroup(
            pnlCardFiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardFiveLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(pnlCardFiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnKendaraanCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKendaraanCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKendaraanSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ScrollViewKendaraan, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );

        pnlMain.add(pnlCardFive, "cardFive");

        pnlSidebar.setBackground(new java.awt.Color(245, 245, 245));

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/login.png"))); // NOI18N

        btnLog.setBackground(new java.awt.Color(6, 17, 50));
        btnLog.setForeground(new java.awt.Color(252, 252, 239));
        btnLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_login.png"))); // NOI18N
        btnLog.setBorderPainted(false);
        btnLog.setContentAreaFilled(false);
        btnLog.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_login_rollover.png"))); // NOI18N
        btnLog.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_login_rollover.png"))); // NOI18N
        btnLog.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_login_rollover.png"))); // NOI18N
        btnLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogActionPerformed(evt);
            }
        });

        btnManager.setBackground(new java.awt.Color(6, 17, 50));
        btnManager.setForeground(new java.awt.Color(252, 252, 239));
        btnManager.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_manager.png"))); // NOI18N
        btnManager.setBorderPainted(false);
        btnManager.setContentAreaFilled(false);
        btnManager.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_manager_rollover.png"))); // NOI18N
        btnManager.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_manager_rollover.png"))); // NOI18N
        btnManager.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_manager_rollover.png"))); // NOI18N
        btnManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagerActionPerformed(evt);
            }
        });

        btnLaporan.setBackground(new java.awt.Color(6, 17, 50));
        btnLaporan.setForeground(new java.awt.Color(252, 252, 239));
        btnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_laporan.png"))); // NOI18N
        btnLaporan.setBorderPainted(false);
        btnLaporan.setContentAreaFilled(false);
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
                .addGap(27, 27, 27)
                .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSidebarLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnManager, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLog, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        else
            card.show(pnlMain, "cardThree");
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

    private void btnManagerSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagerSearchActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        ShowAdmin(ac.AmbilPetugasNama(txtManagerSearch.getText()), card);
        
        pnlBox.revalidate();
        pnlBox.repaint();
    }//GEN-LAST:event_btnManagerSearchActionPerformed

    private void ScrollViewMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_ScrollViewMouseWheelMoved
        
    }//GEN-LAST:event_ScrollViewMouseWheelMoved

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

    private void btnManagerTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagerTambahActionPerformed
        CardLayout card = (CardLayout) pnlMain.getLayout();
        card.show(pnlMain, "cardTwoEdit");
        ChangeTambah();
        
    }//GEN-LAST:event_btnManagerTambahActionPerformed

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

    private void btnKendaraanCheckInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKendaraanCheckInActionPerformed
//        PanelKendaraan pp = new PanelKendaraan();
//        pnlBoxKendaraan.add(pp);
//        pnlBoxKendaraan.add(Box.createVerticalStrut(8));
//        pnlBoxKendaraan.repaint();
//        pnlBoxKendaraan.revalidate();
    }//GEN-LAST:event_btnKendaraanCheckInActionPerformed

    private void btnKendaraanCheckOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKendaraanCheckOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKendaraanCheckOutActionPerformed

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
    private javax.swing.JScrollPane ScrollViewKendaraan;
    private javax.swing.JButton btnEditLihat;
    private javax.swing.JButton btnEditPetugas;
    private javax.swing.JButton btnKendaraanCheckIn;
    private javax.swing.JButton btnKendaraanCheckOut;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnLog;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLoginAction;
    private javax.swing.JButton btnManager;
    private javax.swing.JButton btnManagerSearch;
    private javax.swing.JButton btnManagerTambah;
    private javax.swing.JCheckBox cbEditAdmin;
    private javax.swing.JCheckBox cbEditPassword;
    private javax.swing.JDialog dlgLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEditIDPetugas;
    private javax.swing.JLabel lblEditTotalKendaraan;
    private javax.swing.JLabel lblEditTotalWaktu;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblManager;
    private javax.swing.JLabel lblManagerNama;
    private javax.swing.JLabel lblManagerNama1;
    private javax.swing.JLabel lblNoticeManager;
    private javax.swing.JPanel pnlBar1;
    private javax.swing.JPanel pnlBorder;
    private javax.swing.JPanel pnlBox;
    private javax.swing.JPanel pnlBoxKendaraan;
    private javax.swing.JPanel pnlCardFive;
    private javax.swing.JPanel pnlCardFour;
    private javax.swing.JPanel pnlCardOne;
    private javax.swing.JPanel pnlCardThree;
    private javax.swing.JPanel pnlCardTwo;
    private javax.swing.JPanel pnlCardTwoEdit;
    private javax.swing.JPanel pnlCreator;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JRadioButton rdEditGaje;
    private javax.swing.JRadioButton rdEditLaki;
    private javax.swing.JRadioButton rdEditPerempuan;
    private javax.swing.ButtonGroup rdGroupKelamin;
    private javax.swing.JTextArea txaEditAlamat;
    private javax.swing.JTextField txtEditKtp;
    private javax.swing.JTextField txtEditNama;
    private javax.swing.JPasswordField txtEditPassword;
    private javax.swing.JTextField txtEditTelepon;
    private javax.swing.JTextField txtEditUmur;
    private javax.swing.JTextField txtKendaraanSearch;
    private javax.swing.JTextField txtLogid;
    private javax.swing.JFormattedTextField txtLoginID;
    private javax.swing.JPasswordField txtLoginPin;
    private javax.swing.JPasswordField txtLogpass;
    private javax.swing.JTextField txtManagerSearch;
    // End of variables declaration//GEN-END:variables
}
