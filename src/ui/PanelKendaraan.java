/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import entity.*;
import java.awt.Color;
/**
 *
 * @author Yudho
 */
public class PanelKendaraan extends javax.swing.JPanel {

    /**
     * Creates new form PanelKendaraan
     */
    public PanelKendaraan(Mobil K) {
        initComponents();
        lblIDKendaraan.setText(String.format("%03d", K.getId()));
        lblNoPlat.setText(K.getNoplat().toUpperCase());
        lblJenis.setText("MOBIL");
        lblMasuk.setText(K.getWaktu_Masuk().toString());
        if(K.getWaktu_keluar() != null)
            lblKeluar.setText(K.getWaktu_keluar().toString());
        else
            lblKeluar.setText("");
        pnlBand.setBackground(Color.ORANGE);
    }
    public PanelKendaraan(Motor K) {
        initComponents();
        lblIDKendaraan.setText(String.format("%03d", K.getId()));
        lblNoPlat.setText(K.getNoplat());
        lblJenis.setText("MOTOR");
        lblMasuk.setText(K.getWaktu_Masuk().toString());
        if(K.getWaktu_keluar() != null)
            lblKeluar.setText(K.getWaktu_keluar().toString());
        else
            lblKeluar.setText("");
        pnlBand.setBackground(Color.YELLOW);
    }
    public PanelKendaraan(Sepeda K) {
        initComponents();
        lblIDKendaraan.setText(String.format("%03d", K.getId()));
        lblNoPlat.setText(K.getNoktp());
        lblJenis.setText("SEPEDA");
        lblMasuk.setText(K.getWaktu_Masuk().toString());
        if(K.getWaktu_keluar() != null)
            lblKeluar.setText(K.getWaktu_keluar().toString());
        else
            lblKeluar.setText("");
        pnlBand.setBackground(Color.BLUE);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBand = new javax.swing.JPanel();
        lblNoPlat = new javax.swing.JLabel();
        lblJenis = new javax.swing.JLabel();
        lblMasuk = new javax.swing.JLabel();
        lblKeluar = new javax.swing.JLabel();
        btnCheckout = new javax.swing.JButton();
        lblIDKendaraan = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 102, 102));
        setMaximumSize(new java.awt.Dimension(32767, 40));
        setMinimumSize(new java.awt.Dimension(717, 40));
        setPreferredSize(new java.awt.Dimension(717, 40));

        pnlBand.setBackground(new java.awt.Color(0, 51, 255));

        javax.swing.GroupLayout pnlBandLayout = new javax.swing.GroupLayout(pnlBand);
        pnlBand.setLayout(pnlBandLayout);
        pnlBandLayout.setHorizontalGroup(
            pnlBandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        pnlBandLayout.setVerticalGroup(
            pnlBandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblNoPlat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNoPlat.setForeground(new java.awt.Color(255, 255, 0));
        lblNoPlat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNoPlat.setText("AB 9067 RY");
        lblNoPlat.setToolTipText("No Indetifikasi Kendaraan");

        lblJenis.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblJenis.setForeground(new java.awt.Color(204, 255, 204));
        lblJenis.setText("SEPEDA");
        lblJenis.setToolTipText("Jenis Kendaraan");

        lblMasuk.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMasuk.setForeground(new java.awt.Color(0, 204, 51));
        lblMasuk.setText("04/09/2017");
        lblMasuk.setToolTipText("Waktu Masuk Kendaraan");

        lblKeluar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblKeluar.setForeground(new java.awt.Color(255, 51, 51));
        lblKeluar.setText("05/09/2017");
        lblKeluar.setToolTipText("Waktu Keluar Kendaraan");

        btnCheckout.setBackground(new java.awt.Color(0, 0, 204));
        btnCheckout.setForeground(new java.awt.Color(255, 102, 255));
        btnCheckout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout.png"))); // NOI18N
        btnCheckout.setToolTipText("Jika Kendaraan Ingin Keluar");
        btnCheckout.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 102)));
        btnCheckout.setBorderPainted(false);
        btnCheckout.setContentAreaFilled(false);
        btnCheckout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCheckout.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnCheckout.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnCheckout.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_checkout_rollover.png"))); // NOI18N
        btnCheckout.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutActionPerformed(evt);
            }
        });

        lblIDKendaraan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblIDKendaraan.setForeground(new java.awt.Color(255, 255, 255));
        lblIDKendaraan.setText("001");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIDKendaraan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNoPlat, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(lblMasuk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblKeluar)
                .addGap(57, 57, 57)
                .addComponent(btnCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblIDKendaraan)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNoPlat)
                            .addComponent(lblJenis)
                            .addComponent(lblMasuk)
                            .addComponent(lblKeluar))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCheckout)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCheckoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckout;
    private javax.swing.JLabel lblIDKendaraan;
    private javax.swing.JLabel lblJenis;
    private javax.swing.JLabel lblKeluar;
    private javax.swing.JLabel lblMasuk;
    private javax.swing.JLabel lblNoPlat;
    private javax.swing.JPanel pnlBand;
    // End of variables declaration//GEN-END:variables
}