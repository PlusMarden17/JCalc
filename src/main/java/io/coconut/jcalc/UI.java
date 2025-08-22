package io.coconut.jcalc;

import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UI {
    private JTextField display;
    private final Logic logic;

    public UI() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus LookAndFeel не вдалося застосувати.");
        }


        logic = new Logic(this);

        JFrame frame = new JFrame("JCalc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 460);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(saveItem);
        saveItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "In development", "Save", JOptionPane.INFORMATION_MESSAGE);
        });
        fileMenu.addSeparator();
        JMenuItem closeItem = new JMenuItem("Exit");
        fileMenu.add(closeItem);
        closeItem.addActionListener(new ActionListener ( ){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel displayPanel = new JPanel(new BorderLayout());
        display = new JTextField();
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        displayPanel.add(display, BorderLayout.NORTH);
        displayPanel.setPreferredSize(new Dimension(300, 60));
        frame.add(displayPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        String[] buttonLabels = {
                "C", "Del", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "=", "+/-"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.addActionListener(logic);
            if (label.equals("C")) {
                button.setBackground(Color.RED);
                button.setForeground(Color.WHITE);
            }
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void updateDisplay(String text) {
        display.setText(text);
    }

    public String getDisplayText() {
        return display.getText();
    }
}
