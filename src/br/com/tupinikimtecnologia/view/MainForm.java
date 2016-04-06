package br.com.tupinikimtecnologia.view;

import br.com.tupinikimtecnologia.constants.GeralConstants;
import br.com.tupinikimtecnologia.db.Db;
import br.com.tupinikimtecnologia.db.TPostData;
import br.com.tupinikimtecnologia.db.TTarget;
import br.com.tupinikimtecnologia.http.Flooder;
import br.com.tupinikimtecnologia.objects.Target;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by felipe on 14/08/15.
 */
public class MainForm {
    private JPanel panel1;
    private JComboBox urlComboBox;
    private JRadioButton getRadioButton;
    private JRadioButton postRadioButton;
    private JCheckBox randAgentCheckBox;
    private JComboBox postDataComboBox;
    private JCheckBox randomDataCheckBox;
    private JButton randomDataHelpButton;
    private JSpinner delaySpinner;
    private JButton startButton;
    private JProgressBar progressBar1;
    private JLabel textField;
    private JLabel responseCodeText;
    private JComboBox userAgentComboBox;
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

        db = new Db();
        conn = db.conectDb();

        tTarget = new TTarget(conn);
        tPostData = new TPostData(conn);
        //tTarget.insertTableTarget("http://www.djisjdiasjd.com");
        //tPostData.insertTableTarget("postdataaaaaa", 1);

        delaySpinner.setModel(new SpinnerNumberModel(0,0,10000,1));

        targetList = tTarget.selectTargetAll();
        setUrlComboBoxAll();

        userAgentComboBox.setModel(new DefaultComboBoxModel(GeralConstants.USER_ANGET));
        userAgentComboBox.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXX");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startFlooder();
            }
        });
        postRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                postDataComboBox.setEnabled(true);
            }
        });
        getRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                postDataComboBox.setEnabled(false);
            }
        });
        randAgentCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(randAgentCheckBox.isSelected()){
                    userAgentComboBox.setEnabled(false);
                }else{
                    userAgentComboBox.setEnabled(true);
                }
            }
        });
    }

    private void setUrlComboBoxAll(){
        for(Target t : targetList){
            urlComboBox.addItem(t.getUrl());
        }
    }

    private String insertUrlOnDb(){
        String url = urlComboBox.getSelectedItem().toString().trim();

        if(!tTarget.checkIfUrlExists(url)){
            Target target = new Target();
            target.setUrl(url);
            tTarget.insertTarget(url);
            target.setId(tTarget.selectIdByUrl(url));
            targetList.add(target);
            urlComboBox.addItem(url);
        }
        return url;
    }

    private void startFlooder(){
        if(startButton.getText().equals("START!")){
            if(validateForm()) {


                startButton.setText("STOP!");
                startButton.setForeground(Color.RED);

                if (getRadioButton.isSelected()) {
                    flooder = new Flooder(insertUrlOnDb());
                } else if (postRadioButton.isSelected()) {
                    String postData = postDataComboBox.getSelectedItem().toString().trim();
                    String url = insertUrlOnDb();
                    int targetId = tTarget.selectIdByUrl(url);
                    if(!tPostData.checkIfPostDataExists(postData, targetId)){

                        if(targetId!=-1){
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
                flooder.setDelay((int) delaySpinner.getValue());
                flooderThread = new Thread(flooder);
                flooderThread.start();
                startRespCodeThread();
                progressBar1.setIndeterminate(true);
            }
        }else{
            respCodeThRunning = false;
            flooder.stop();
            flooderThread.interrupt();
            responseCodeThread.interrupt();
            startButton.setText("START!");
            startButton.setForeground(Color.BLUE);
            progressBar1.setIndeterminate(false);
        }
    }

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
        if(respCode>=200 && respCode<=226){
            return Color.GREEN;
        }
        if(respCode>=300 && respCode<=308){
            return Color.yellow;
        }
        if(respCode>=400 && respCode<=499){
            return Color.red;
        }
        if(respCode>=500 && respCode<=599){
            return new Color(117,8,8);
        }
        return Color.black;
    }

    private boolean validateForm(){
        if(urlComboBox.getSelectedItem() == null || urlComboBox.getSelectedItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter the Target URL field", "Target URL Empty", JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            if(postDataComboBox.isEnabled()) {
                if (postDataComboBox.getSelectedItem() != null) {
                    if (postDataComboBox.getSelectedItem().toString().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Enter the Post Data field", "Post Data Empty", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Enter the Post Data field", "Post Data Empty", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }

    public static void setMainMenu(JFrame frame){
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        //target menu
        menuBar = new JMenuBar();
        menu = new JMenu("Target");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        menuItem = new JMenuItem("Target History",
                new ImageIcon("img/target_icon.png"));
        menuItem.setMnemonic(KeyEvent.VK_T);
        menu.add(menuItem);

        //help menu
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(menu);

        menuItem = new JMenuItem("How it works?",
                new ImageIcon("img/help_icon.png"));
        menuItem.setMnemonic(KeyEvent.VK_O);
        menu.add(menuItem);

        menuItem = new JMenuItem("About",
                new ImageIcon("img/about_icon.png"));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        frame.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HttpFlooder");
        frame.setContentPane(new MainForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        setMainMenu(frame);
    }
}
