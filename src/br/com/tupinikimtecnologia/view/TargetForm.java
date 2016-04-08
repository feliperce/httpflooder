/*
 * Created by: Felipe Rodrigues
 * http://www.tupinikimtecnologia.com.br
 */
package br.com.tupinikimtecnologia.view;

import br.com.tupinikimtecnologia.db.Db;
import br.com.tupinikimtecnologia.db.TPostData;
import br.com.tupinikimtecnologia.db.TTarget;
import br.com.tupinikimtecnologia.objects.PostData;
import br.com.tupinikimtecnologia.objects.PostDataListHelper;
import br.com.tupinikimtecnologia.objects.Target;
import br.com.tupinikimtecnologia.objects.TargetListHelper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Felipe Rodrigues
 */
public class TargetForm extends javax.swing.JFrame {
    
    private TTarget tTarget;
    private TPostData tPostData;
    private Target target;
    private List<Target> targetArrayList;
    private List<PostData> postArrayList;
    private Db db;
    private Connection conn;
    private DefaultListModel listUrlModel;
    private DefaultListModel listPostDataModel;
    private MainForm mainForm;
    
    public TargetForm(MainForm mainForm) {
        initComponents();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        
        listUrlModel = new DefaultListModel();
        listPostDataModel = new DefaultListModel();
        urlList.setModel(listUrlModel);
        postDataList.setModel(listPostDataModel);

        setUrlListAll();
        
        
    }
    
    private void setDb(){
        db = new Db();
        conn = db.conectDb();

        tTarget = new TTarget(conn);
        tPostData = new TPostData(conn);
    }
    
    private void closeDb(){
        try {
            db.closeDb();
        } catch (SQLException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setUrlListAll(){
        setDb();
        targetArrayList = tTarget.selectTargetAll();
        for(Target t : targetArrayList){
            listUrlModel.addElement(new TargetListHelper(t.getId(), t.getUrl()));
        }
        closeDb();
    }
    
    private void setPostDataList(TargetListHelper tHelper){
        setDb();
        postArrayList = tPostData.selectPostDataByTargetId(tHelper.getId());
        for(PostData p : postArrayList){
            listPostDataModel.addElement(new PostDataListHelper(p.getId(), p.getPostData()));
        }
        closeDb();
    }
    
    private void removeTarget(TargetListHelper tHelper){
        setDb();
        int id = tHelper.getId();
        tPostData.deletePostDataByTargetId(id);
        tTarget.deleteTargetById(id);
        listUrlModel.removeElementAt(urlList.getSelectedIndex());
        listPostDataModel.clear();
        closeDb();
    }
    
    private void removePostData(PostDataListHelper pHelper){
        setDb();
        int id = pHelper.getId();
        tPostData.deletePostDataById(id);
        listPostDataModel.removeElementAt(postDataList.getSelectedIndex());
        closeDb();
    }
    
    private void editTarget(TargetListHelper tHelper, String newUrl){
        setDb();
        if(newUrl!= null && !newUrl.equals("")){
            tTarget.editTarget(newUrl, tHelper.getId());
            tHelper.setUrl(newUrl);
            listUrlModel.setElementAt(tHelper, tHelper.getListId());
        }
        closeDb();
    }
    
    private void editPostData(PostDataListHelper pHelper, String newPostData){
        setDb();
        if(newPostData!= null && !newPostData.equals("")){
            tPostData.editPostData(newPostData, pHelper.getId());
            pHelper.setPostData(newPostData);
            listPostDataModel.setElementAt(pHelper, pHelper.getListId());
        }
        closeDb();
    }

    private int showRemoveDialog(String msg){
        return JOptionPane.showConfirmDialog(null, "Do yout want to remove selected "+msg+"?", "REMOVE WARNING", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    
    private String showEditDialog(String msg, String editText){
        return JOptionPane.showInputDialog(msg, editText);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        urlList = new javax.swing.JList<>();
        urlRemoveButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        urlEditButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        postDataList = new javax.swing.JList<>();
        postDataRemoveButton = new javax.swing.JButton();
        postDataEditButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Target History");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Target URL"));

        urlList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        urlList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                urlListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(urlList);

        urlRemoveButton.setText("REMOVE");
        urlRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlRemoveButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Noto Sans", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Double click to set target");

        urlEditButton.setText("EDIT");
        urlEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlEditButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(urlRemoveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(urlEditButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel1)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(urlRemoveButton)
                        .addGap(38, 38, 38)
                        .addComponent(urlEditButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("POST Data"));

        postDataList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(postDataList);

        postDataRemoveButton.setText("REMOVE");
        postDataRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postDataRemoveButtonActionPerformed(evt);
            }
        });

        postDataEditButton.setText("EDIT");
        postDataEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postDataEditButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(postDataRemoveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(postDataEditButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(postDataRemoveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(postDataEditButton)
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void urlListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_urlListMouseClicked
        listPostDataModel.clear();
        if(evt.getClickCount()==1){
            TargetListHelper targetHelper = (TargetListHelper)urlList.getSelectedValue();
            setPostDataList(targetHelper);
        }else if(evt.getClickCount()==2){
            setDb();
            TargetListHelper tHelper = (TargetListHelper)urlList.getSelectedValue();
            postArrayList = tPostData.selectPostDataByTargetId(tHelper.getId());

            closeDb();
            dispose();
        }
    }//GEN-LAST:event_urlListMouseClicked

    private void urlRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlRemoveButtonActionPerformed
        if(showRemoveDialog("Target")==0){
            TargetListHelper tHelper = (TargetListHelper)urlList.getSelectedValue();
            removeTarget(tHelper);
        }
    }//GEN-LAST:event_urlRemoveButtonActionPerformed

    private void postDataRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postDataRemoveButtonActionPerformed
        if(showRemoveDialog("POST Data")==0){
            PostDataListHelper pHelper = (PostDataListHelper)postDataList.getSelectedValue();
            removePostData(pHelper);
        }
    }//GEN-LAST:event_postDataRemoveButtonActionPerformed

    private void urlEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlEditButtonActionPerformed
        TargetListHelper tHelper = (TargetListHelper)urlList.getSelectedValue();
        tHelper.setListId(urlList.getSelectedIndex());
        editTarget(tHelper, showEditDialog("Target URL", tHelper.getUrl()));
    }//GEN-LAST:event_urlEditButtonActionPerformed

    private void postDataEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postDataEditButtonActionPerformed
        PostDataListHelper pHelper = (PostDataListHelper)postDataList.getSelectedValue();
        pHelper.setListId(postDataList.getSelectedIndex());
        editPostData(pHelper, showEditDialog("POST Data", pHelper.getPostData()));
    }//GEN-LAST:event_postDataEditButtonActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TargetForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TargetForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TargetForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TargetForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TargetForm(mainForm).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton postDataEditButton;
    private javax.swing.JList<PostDataListHelper> postDataList;
    private javax.swing.JButton postDataRemoveButton;
    private javax.swing.JButton urlEditButton;
    private javax.swing.JList<TargetListHelper> urlList;
    private javax.swing.JButton urlRemoveButton;
    // End of variables declaration//GEN-END:variables
}
