package br.com.tupinikimtecnologia.view;

import br.com.tupinikimtecnologia.constants.GeralConstants;
import br.com.tupinikimtecnologia.http.Flooder;
import br.com.tupinikimtecnologia.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by felipe on 14/08/15.
 */
public class MainForm {
    private JPanel panel1;
    private JTextField urlTextField;
    private JRadioButton getRadioButton;
    private JRadioButton postRadioButton;
    private JCheckBox randAgentCheckBox;
    private JTextField postDataField;
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

    public MainForm() {

        delaySpinner.setModel(new SpinnerNumberModel(0,0,10000,1));

        userAgentComboBox.setModel(new DefaultComboBoxModel(GeralConstants.USER_ANGET));
        userAgentComboBox.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXX");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startFlooder();
            }
        });
        postRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                postDataField.setEnabled(true);
            }
        });
        getRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                postDataField.setEnabled(false);
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



    public void startFlooder(){
        if(startButton.getText().equals("START!")){
            if(validateForm()) {
                startButton.setText("STOP!");
                startButton.setForeground(Color.RED);

                if (getRadioButton.isSelected()) {
                    flooder = new Flooder(urlTextField.getText());
                } else if (postRadioButton.isSelected()) {
                    flooder = new Flooder(urlTextField.getText(), postDataField.getText());
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

    public void startRespCodeThread(){
        respCodeThRunning = true;
        responseCodeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(respCodeThRunning){
                    responseCodeText.setText(""+flooder.getLastResponseCode());
                }
            }
        });
        responseCodeThread.start();
    }

    public boolean validateForm(){
        if(urlTextField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter the Target URL field", "Target URL Empty", JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            if(postDataField.isEnabled() && postDataField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter the Post Data field", "Post Data Empty", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HttpFlooder");
        frame.setContentPane(new MainForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
