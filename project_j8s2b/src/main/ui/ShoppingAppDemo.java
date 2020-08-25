package ui;

import model.Customer;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

// A GUI design for Shopping App

public class ShoppingAppDemo extends JFrame implements ActionListener {
    private static final String CUSTOMERS_FILE = "./data/customer.txt";

    private JLabel personalInfoLabel;
    private JRadioButton lady;
    private JRadioButton men;
    private JComboBox<String> commandBox;
    private ButtonGroup buttonGroup;
    private JLabel label;

    // Construct a shopping app demo
    public ShoppingAppDemo(String title, int posX, int posY, int width, int height) {
        init();
        setTitle(title);
        setLocation(posX, posY);
        setSize(width, height);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        label = new JLabel();

    }

    // instantiate a shopping app deomo and add an action listener for the comboBox
    private void init() {
        setLayout(new FlowLayout());
        personalInfoLabel = new JLabel("Customer's Information");
        lady = new JRadioButton("Lady");
        men = new JRadioButton("Man");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(lady);
        buttonGroup.add(men);
        commandBox = new JComboBox<String>();
        commandBox.addItem("deposit");
        commandBox.addItem("withdraw");
        commandBox.addItem("add customer to the customer list");
        commandBox.addItem("save");
        commandBox.setSelectedIndex(0);
        commandBox.addActionListener(this);

        add(personalInfoLabel);
        add(lady);
        add(men);
        add(commandBox);

    }


    // Effect: when click different selection of combo boc it will appear different memo

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == commandBox) {
            JComboBox b = (JComboBox) e.getSource();
            String msg = (String) b.getSelectedItem();
            switch (msg) {
                case "deposit":
                    new DepositDemo().setVisible(true);
                    break;
                case "withdraw":
                    new WithdrawDemo().setVisible(true);
                    break;
                case "add customer to the customer list":
                    AddCustomerMemo.startToRun();
                    break;
                case "save":
                    new SaveDemo().setVisible(true);
                default:
                    label.setText("Sorry, there is an error..");
            }
        }

    }

    // A class for deposit memo

    class DepositDemo extends JFrame implements ActionListener {
        private JLabel label1;
        private JButton btn;
        private JTextField textField1;
        private JTextArea textArea1;
        private JLabel label2;

        // Construct a deposit memo and add an action listener for process btn

        public DepositDemo() {
            setTitle("Deposit Window");
            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            setSize(800, 200);
            textField1 = new JTextField();
            textArea1 = new JTextArea();
            label1 = new JLabel("Please enter the amount you want to add");
            Font font = new Font("Serief", Font.ITALIC + Font.BOLD, 28);
            label2 = new JLabel("Your Account Information");
            label2.setFont(font);
            btn = new JButton("Deposit");
            btn.addActionListener(this);
            getContentPane().add(label1);
            getContentPane().add(textArea1);
            getContentPane().add(textField1);
            getContentPane().add(btn);
            getContentPane().add(label2);
        }

        //EFFECT: when the process btn is click, it will appear how much you deposit

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btn) {
                label2.setText("Add " + textArea1.getText() + " dollars to your account successfully");

            }

        }

    }

    // A class for withdraw memo

    class WithdrawDemo extends JFrame implements ActionListener {
        private JLabel label3;
        private JButton btn1;
        private JTextField textField2;
        private JTextArea textArea2;
        private JLabel label4;

        // Construct a withdraw memo and add an action listener for process btn

        public WithdrawDemo() {
            setTitle("Withdraw Window");
            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            setSize(1300, 200);
            textField2 = new JTextField();
            textArea2 = new JTextArea();
            label3 = new JLabel("Please enter the amount you want to withdraw");
            Font font = new Font("Serief", Font.ITALIC + Font.BOLD, 28);
            label4 = new JLabel("Your Account Information");
            label4.setFont(font);
            btn1 = new JButton("Withdraw");
            btn1.addActionListener(this);
            getContentPane().add(label3);
            getContentPane().add(textArea2);
            getContentPane().add(textField2);
            getContentPane().add(btn1);
            getContentPane().add(label4);
        }

        //EFFECT: when the process btn is click, it will appear how much you withdraw

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btn1) {
                label4.setText("Withdraw " + textArea2.getText() + " dollars from your account successfully");
            }

        }
    }


    public static void main(String[] args) {
        new ShoppingAppDemo("Customer", 40, 50, 350, 200);
    }
}



