/*
 * Created by: Felipe Rodrigues
 * http://www.tupinikimtecnologia.com.br
 */
package br.com.tupinikimtecnologia.view;

import br.com.tupinikimtecnologia.constants.GeralConstants;
import br.com.tupinikimtecnologia.db.Db;
import br.com.tupinikimtecnologia.db.TPostData;
import br.com.tupinikimtecnologia.db.TTarget;
import br.com.tupinikimtecnologia.http.Flooder;
import br.com.tupinikimtecnologia.objects.PostData;
import br.com.tupinikimtecnologia.objects.Target;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

/**
 * Main Form
 * @author Felipe Rodrigues
 */
public class MainForm extends javax.swing.JFrame {
    
    private boolean respCodeThRunning;
    private Flooder flooder;
    private Thread flooderThread;
    private Thread responseCodeThread;
    private Db db;
    private Connection conn;
    private TTarget tTarget;
    private TPostData tPostData;
    private List<Target> targetList;

    public MainForm() {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        
        setDb();

        delaySpinner.setModel(new SpinnerNumberModel(0,0,10000,1));

        targetList = tTarget.selectTargetAll();
        
        closeDb();

        userAgentComboBox.setModel(new DefaultComboBoxModel(GeralConstants.RandomData.USER_ANGET));
        userAgentComboBox.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXX");

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
    
    private String insertUrlOnDb(){
        String url = urlField.getText().trim();
        if(!tTarget.checkIfUrlExists(url)){
            tTarget.insertTarget(url);
        }
        return url;
    }

    private boolean startFlooder(){
        boolean validate = validateForm();
        if(validate) {
            setDb();

            if (getRadioButton.isSelected()) {
                flooder = new Flooder(insertUrlOnDb());
            } else if (postRadioButton.isSelected()) {
                String postData = postDataComboBox.getSelectedItem().toString().trim();
                String url = urlField.getText();
                insertUrlOnDb();
                int targetId = tTarget.selectIdByUrl(url);
                if(!tPostData.checkIfPostDataExists(postData, targetId)){

                    if(targetId!=-1){
                        postDataComboBox.addItem(postData);
                        tPostData.insertPostData(postData, targetId);
                    }
                }
                flooder = new Flooder(url, postData);
            }

            if (!randAgentCheckBox.isSelected()) {
                flooder.setUserAgent(userAgentComboBox.getSelectedItem().toString());
                flooder.setRandomAgent(false);
            } else {
                flooder.setRandomAgent(true);
            }
            if(randomDataCheckBox.isSelected()){
                flooder.setRandomData(true);
            }
            flooder.setDelay((int) delaySpinner.getValue());
            flooderThread = new Thread(flooder);
            flooderThread.start();
            startRespCodeThread();
            progressBar1.setIndeterminate(true);
            closeDb();
        }
        return validate;
    }


    /**
     * Set the response code thread, the response label will change every request
     */
    private void startRespCodeThread(){
        respCodeThRunning = true;
        responseCodeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(respCodeThRunning){
                    int respCode = flooder.getLastResponseCode();
                    responseCodeText.setText(""+respCode);
                    responseCodeText.setForeground(setRespCodeColor(respCode));
                }
            }
        });
        responseCodeThread.start();
    }

    private Color setRespCodeColor(int respCode){
        if(respCode==-1){
            respCode = 0;
        }else{
            if(respCode>=200 && respCode<=226){
                return Color.GREEN;
            }else{
                if(respCode>=300 && respCode<=308){
                    return Color.yellow;
                }else{
                    if(respCode>=400 && respCode<=499){
                        return Color.red;
                    }else{
                        if(respCode>=500 && respCode<=599){
                            return new Color(117,8,8);
                        }
                    }
                }
            }
        }
        return Color.black;
    }

    private boolean validateForm(){
        if(urlField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter the Target URL field", "Target URL Empty", JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            if(postDataComboBox.isEnabled()) {
                if (postDataComboBox.getSelectedItem() == null || postDataComboBox.getSelectedItem().toString().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter the Post Data field", "Post Data Empty", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }
    
    public void setSelectedTarget(String url, List<PostData> postData){
        urlField.setText(url);
        postDataComboBox.removeAllItems();
        if(postData!=null){
            if(!postData.isEmpty()){
                for(PostData p : postData){
                    postDataComboBox.addItem(p.getPostData());
                }
            }
        }
        
    }
    
    public void setComponentesEnable(boolean enable){
        urlField.setEnabled(enable);
        getRadioButton.setEnabled(enable);
        postRadioButton.setEnabled(enable);
        randAgentCheckBox.setEnabled(enable);
        randomDataCheckBox.setEnabled(enable);
        delaySpinner.setEnabled(enable);
        if(enable){
            if(!randAgentCheckBox.isSelected()){
                userAgentComboBox.setEnabled(true);
            }
            if(!getRadioButton.isSelected()){
                postDataComboBox.setEnabled(true);
            }  
        }else{
            userAgentComboBox.setEnabled(false);
            postDataComboBox.setEnabled(false);
        }   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        methodButtonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        urlField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        userAgentComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        postDataComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        getRadioButton = new javax.swing.JRadioButton();
        postRadioButton = new javax.swing.JRadioButton();
        randAgentCheckBox = new javax.swing.JCheckBox();
        randomDataCheckBox = new javax.swing.JCheckBox();
        randomDataHelpButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        delaySpinner = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        responseCodeText = new javax.swing.JLabel();
        progressBar1 = new javax.swing.JProgressBar();
        startButton = new javax.swing.JToggleButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        targetHistoryMenuItem = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HttpFlooder");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Target URL:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, -1, -1));

        urlField.setName(""); // NOI18N
        getContentPane().add(urlField, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 35, 331, -1));

        jLabel2.setText("User Agent:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 68, -1, -1));

        userAgentComboBox.setEnabled(false);
        getContentPane().add(userAgentComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 91, 331, -1));

        jLabel3.setText("POST Data:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 124, -1, -1));

        postDataComboBox.setEditable(true);
        postDataComboBox.setEnabled(false);
        getContentPane().add(postDataComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 147, 331, -1));

        jLabel4.setText("Method:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(426, 12, -1, -1));

        methodButtonGroup.add(getRadioButton);
        getRadioButton.setSelected(true);
        getRadioButton.setText("GET");
        getRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getRadioButtonActionPerformed(evt);
            }
        });
        getContentPane().add(getRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 37, -1, -1));

        methodButtonGroup.add(postRadioButton);
        postRadioButton.setText("POST");
        postRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postRadioButtonActionPerformed(evt);
            }
        });
        getContentPane().add(postRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 37, -1, -1));

        randAgentCheckBox.setSelected(true);
        randAgentCheckBox.setText("Random User Agent");
        randAgentCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randAgentCheckBoxActionPerformed(evt);
            }
        });
        getContentPane().add(randAgentCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 93, -1, -1));

        randomDataCheckBox.setText("Random Data");
        getContentPane().add(randomDataCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 151, -1, -1));

        randomDataHelpButton.setText("?");
        randomDataHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomDataHelpButtonActionPerformed(evt);
            }
        });
        getContentPane().add(randomDataHelpButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(531, 147, 40, -1));

        jLabel5.setText("Delay: (seconds)");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));
        getContentPane().add(delaySpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 213, 92, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        responseCodeText.setText("Response Code: 0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(responseCodeText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 399, Short.MAX_VALUE)
                .addComponent(progressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(responseCodeText)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 262, 602, -1));

        startButton.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        startButton.setForeground(new java.awt.Color(17, 0, 255));
        startButton.setText("START FLOODER!");
        startButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                startButtonItemStateChanged(evt);
            }
        });
        getContentPane().add(startButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, 310, 40));

        jMenu1.setText("Target");

        targetHistoryMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/target_icon.png"))); // NOI18N
        targetHistoryMenuItem.setText("History");
        targetHistoryMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetHistoryMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(targetHistoryMenuItem);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clear_icon.png"))); // NOI18N
        jMenuItem3.setText("Clear");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/help_icon.png"))); // NOI18N
        jMenuItem1.setText("How to use?");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/about_icon.png"))); // NOI18N
        jMenuItem2.setText("About");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void randAgentCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randAgentCheckBoxActionPerformed
        if(randAgentCheckBox.isSelected()){
            userAgentComboBox.setEnabled(false);
        }else{
            userAgentComboBox.setEnabled(true);
        }
    }//GEN-LAST:event_randAgentCheckBoxActionPerformed

    private void postRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postRadioButtonActionPerformed
        postDataComboBox.setEnabled(true);
    }//GEN-LAST:event_postRadioButtonActionPerformed

    private void getRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getRadioButtonActionPerformed
        postDataComboBox.setEnabled(false);
    }//GEN-LAST:event_getRadioButtonActionPerformed

    private void targetHistoryMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetHistoryMenuItemActionPerformed
        closeDb();
        TargetForm targetForm = new TargetForm(this);
        targetForm.setVisible(true);
    }//GEN-LAST:event_targetHistoryMenuItemActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        AboutDialog aboutDialog = new AboutDialog(this, true);
        aboutDialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void randomDataHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomDataHelpButtonActionPerformed
        RandomHelpForm rand = new RandomHelpForm();
        rand.setVisible(true);
    }//GEN-LAST:event_randomDataHelpButtonActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Desktop d=Desktop.getDesktop();

        try {
            d.browse(new URI(GeralConstants.HOWTO_URL));
        } catch (IOException ex) {
            Logger.getLogger(AboutDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AboutDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void startButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_startButtonItemStateChanged
        if(evt.getStateChange()==ItemEvent.SELECTED){
            if(startFlooder()){
                startButton.setText("STOP FLOODER!");
                startButton.setForeground(Color.RED);
                setComponentesEnable(false);
            }
        }else if(evt.getStateChange()==ItemEvent.DESELECTED){
            startButton.setText("START FLOODER!");
            startButton.setForeground(Color.BLUE);
            setComponentesEnable(true);
            progressBar1.setIndeterminate(false);
            respCodeThRunning = false;
            responseCodeText.setText("0");
            responseCodeText.setForeground(Color.BLACK);
            if(flooder!=null){
                flooder.stop();
                flooderThread.interrupt();
                responseCodeThread.interrupt();
            }
        }
    }//GEN-LAST:event_startButtonItemStateChanged

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if(!startButton.isSelected()){
            urlField.setText("");
            postDataComboBox.removeAllItems();
        }else{
            JOptionPane.showMessageDialog(null, "Stop the flooder first", "Flooder running", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner delaySpinner;
    private javax.swing.JRadioButton getRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.ButtonGroup methodButtonGroup;
    private javax.swing.JComboBox<String> postDataComboBox;
    private javax.swing.JRadioButton postRadioButton;
    private javax.swing.JProgressBar progressBar1;
    private javax.swing.JCheckBox randAgentCheckBox;
    private javax.swing.JCheckBox randomDataCheckBox;
    private javax.swing.JButton randomDataHelpButton;
    private javax.swing.JLabel responseCodeText;
    private javax.swing.JToggleButton startButton;
    private javax.swing.JMenuItem targetHistoryMenuItem;
    private javax.swing.JTextField urlField;
    private javax.swing.JComboBox<String> userAgentComboBox;
    // End of variables declaration//GEN-END:variables

}
