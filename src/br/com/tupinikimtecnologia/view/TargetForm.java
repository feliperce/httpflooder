package br.com.tupinikimtecnologia.view;

import javax.swing.*;

/**
 * Created by felipe on 06/04/16.
 */
public class TargetForm {
    private JPanel targetPanel;
    private JList targetList;
    private JPanel panel1;
    private JButton removeTargetButton;
    private JPanel postPanel;
    private JList postList;
    private JButton removeButton;
    private JPanel removePostButton;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Target History");
        frame.setContentPane(new TargetForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
