/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import control.AdminControl;
import entity.*;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
/**
 *
 * @author Yudho
 */
public class PanelPetugas extends javax.swing.JPanel {
    private Admin admin;
    private AdminControl ac;
    private int index;
    /**
     * Creates new form PanelPetugas
     * @param admin
     * @param index
     */
    public PanelPetugas(Admin admin,int index) {
        initComponents();
        this.admin = admin;
        this.index = index;
        ac = new AdminControl();
        
        if(index%2 != 0)
        {
            setBackground(new Color(19, 18, 18));
            setAllForeground(Color.WHITE);
        }
        else
        {
            setBackground(new Color(192, 192, 192));
            setAllForeground(Color.BLACK);
        }
        
        if(admin.isTipeadmin())
            lblUser.setIcon(new ImageIcon(getClass().getResource("images/user_admin.png")));
        else
            lblUser.setIcon(new ImageIcon(getClass().getResource("images/user_normal.png")));
        lblID.setText(String.format("USR-%03d", admin.getId()));
        lblNama.setText(admin.getNama().toUpperCase());
        lblTime.setText(String.format("Total %d Jam",admin.getTotal_waktu()));
        lblKendaraan.setText(String.format("Total %d Kendaraan",admin.getTotal_kendaraan()));
    }

    private void setAllForeground(Color fg) {
        super.setForeground(fg);
        lblID.setForeground(fg);
        lblNama.setForeground(fg);
        lblTime.setForeground(fg);
        lblKendaraan.setForeground(fg);
    }
    
    public Admin getAdmin(){
        return this.admin;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNama = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblKendaraan = new javax.swing.JLabel();
        btnHapus = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));
        setMaximumSize(new java.awt.Dimension(9999, 100));
        setMinimumSize(new java.awt.Dimension(0, 100));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        lblNama.setFont(new java.awt.Font("Century", 0, 24)); // NOI18N
        lblNama.setForeground(new java.awt.Color(255, 255, 255));
        lblNama.setText("$NAMA PETUGAS");
        lblNama.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/user_normal.png"))); // NOI18N

        lblID.setForeground(new java.awt.Color(255, 255, 255));
        lblID.setText("USR-000");

        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/count_time.png"))); // NOI18N
        lblTime.setText("Total 000 Jam ");

        lblKendaraan.setForeground(new java.awt.Color(255, 255, 255));
        lblKendaraan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/count_park.png"))); // NOI18N
        lblKendaraan.setText("Total 000 Kendaraan");

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_hapus.png"))); // NOI18N
        btnHapus.setBorderPainted(false);
        btnHapus.setContentAreaFilled(false);
        btnHapus.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_hapus_rollover.png"))); // NOI18N
        btnHapus.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_hapus_rollover.png"))); // NOI18N
        btnHapus.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/btn_hapus_rollover.png"))); // NOI18N
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(34, 34, 34)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblKendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblUser))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNama)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblID)
                            .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        setBackground(new Color(255,255,204));
        setAllForeground(Color.DARK_GRAY);
        this.repaint();
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        if(index%2 != 0)
        {
            setBackground(new Color(19, 18, 18));
            setAllForeground(Color.WHITE);
        }
        else
        {
            setBackground(new Color(192, 192, 192));
            setAllForeground(Color.BLACK);
        }
        this.repaint();
    }//GEN-LAST:event_formMouseExited

    
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        this.removeAll();
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_formMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        setBackground(new Color(204,255,204));
        setAllForeground(Color.DARK_GRAY);
        this.repaint();
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        setBackground(new Color(255,255,204));
        setAllForeground(Color.DARK_GRAY);
        this.repaint();
    }//GEN-LAST:event_formMouseReleased

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        ac.HapusPetugas(admin);
    }//GEN-LAST:event_btnHapusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnHapus;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblKendaraan;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblUser;
    // End of variables declaration//GEN-END:variables
}
