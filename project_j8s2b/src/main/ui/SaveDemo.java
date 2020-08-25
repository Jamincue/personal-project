package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SaveDemo extends JFrame implements ActionListener {
    private JLabel saveLabel;
    private ImageIcon icon;
    private JButton saveBtn;

    // Construct a save memo and add an action listener for save btn

    public SaveDemo() {
        setTitle("Save Window");
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        setSize(400, 250);
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(this);
        saveLabel = new JLabel();
        icon = new ImageIcon("/Users/lizhihua/Documents/download.jpeg");

        icon.setImage(icon.getImage().getScaledInstance(
                140, 200, Image.SCALE_DEFAULT));
        saveLabel.setIcon(icon);

        saveLabel.setIcon(icon);
        getContentPane().add(saveLabel);
        getContentPane().add(saveBtn);


    }

    //EFFECT: when the save btn is click, it will appear you save your info successfully

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            saveLabel.setText("Save to the file successfully");
        }

    }

}
