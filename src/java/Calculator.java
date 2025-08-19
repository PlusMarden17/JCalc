import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator implements ActionListener {

    JTextField display;
    String currentInput = "";
    double firstOperand = 0;
    String operator = "";

    public Calc() {
        JFrame frame = new JFrame("JCalc 0.1.2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

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
            button.addActionListener(this);
            if (label.equals("C")) {
                button.setBackground(Color.RED);
                button.setForeground(Color.WHITE);
            }
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {
            currentInput += command;
            display.setText(currentInput);
        } else if (command.equals(".")) {
            if (!currentInput.contains(".")) {
                currentInput += command;
                display.setText(currentInput);
            }
        } else if (command.equals("+/-")) {
            if (!currentInput.isEmpty() && !currentInput.equals("0")) {
                if (currentInput.startsWith("-")) {
                    currentInput = currentInput.substring(1);
                } else {
                    currentInput = "-" + currentInput;
                }
                display.setText(currentInput);
            }
        } else if (command.equals("%")) {
            if (!currentInput.isEmpty()) {
                double value = Double.parseDouble(currentInput);
                currentInput = String.valueOf(value / 100.0);
                display.setText(currentInput);
            }
        } else if (command.matches("[+\\-*/]")) {
            if (!currentInput.isEmpty()) {
                firstOperand = Double.parseDouble(currentInput);
                operator = command;
                display.setText(firstOperand + " " + operator);
                currentInput = "";
            }
        } else if (command.equals("=")) {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                double secondOperand = Double.parseDouble(currentInput);
                double result = 0;
                switch (operator) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        if (secondOperand != 0) {
                            result = firstOperand / secondOperand;
                        } else {
                            display.setText("Error: Division by Zero!");
                            return;
                        }
                        break;
                }
                display.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
                operator = "";
                firstOperand = result;
            }
        } else if (command.equals("Del")) {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                display.setText(currentInput);
            }
        } else if (command.equals("C")) {
            currentInput = "";
            display.setText("");
            firstOperand = 0;
            operator = "";
        }
    }

    public static void main(String[] args) {
        new Calc();
    }

}
