package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A add customer to customer list memo

public class AddCustomerMemo extends JPanel
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel = new DefaultListModel();

    private static final String addString = "Add";
    private static final String cancelString = "Cancel";
    private JButton cancelButton = new JButton(cancelString);
    private JTextField customerName = new JTextField(10);
    private JTextField customerAge = new JTextField(3);
    private JLabel label1 = new JLabel("Name");
    private JLabel label2 = new JLabel("Age");
    private JButton addButton = new JButton(addString);
    private JPanel buttonPane = new JPanel();

    // construct an add customer memo and add two different action listeners for cancel
    //   and add btn.


    public AddCustomerMemo() {
        super(new BorderLayout());
        initial();
        AddListener al = new AddListener(addButton);
        addButton.addActionListener(al);
        addButton.setEnabled(false);

        cancelButton.addActionListener(new CancelListener());

        customerName.addActionListener(al);
        customerName.getDocument().addDocumentListener(al);

        customerAge.addActionListener(al);
        customerAge.getDocument().addDocumentListener(al);
        //Create a panel that uses BoxLayout.

        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(cancelButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(label1);
        buttonPane.add(customerName);
        buttonPane.add(label2);
        buttonPane.add(customerAge);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(buttonPane, BorderLayout.PAGE_END);
    }

    // EFFECT: instantiate add customer class field

    private void initial() {
        listModel.addElement("Jack 20");
        listModel.addElement("Mary 24");

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane, BorderLayout.CENTER);
    }

    //EFFECT: when the cancel btn is click, the customer will be removed.


    class CancelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                cancelButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //REQUIRE:This listener is shared by the text field and the hire button.
    //EFFECT: when the cancel btn is click, the customer will be removed.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = customerName.getText();
            String age = customerAge.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                customerName.requestFocusInWindow();
                customerName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(customerName.getText() + " " + customerAge.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            customerName.requestFocusInWindow();
            customerName.setText("");
            customerAge.requestFocusInWindow();
            customerAge.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //EFFECT: This method tests for string equality. You could certainly
        //        get more sophisticated about the algorithm.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                cancelButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                cancelButton.setEnabled(true);
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");


        //Create and set up the content pane.
        JComponent newContentPane = new AddCustomerMemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    static void startToRun() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}