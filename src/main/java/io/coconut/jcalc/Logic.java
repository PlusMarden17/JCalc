package io.coconut.jcalc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logic implements ActionListener {
    private final UI ui;
    private String currentInput = "";
    private double firstOperand = 0;
    private String operator = "";

    public Logic(UI ui) {
        this.ui = ui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                currentInput += command;
                ui.updateDisplay(currentInput);
            }
            case "." -> {
                if (!currentInput.contains(".")) {
                    currentInput += ".";
                    ui.updateDisplay(currentInput);
                }
            }
            case "+/-" -> {
                if (!currentInput.isEmpty() && !currentInput.equals("0")) {
                    currentInput = currentInput.startsWith("-") ?
                            currentInput.substring(1) : "-" + currentInput;
                    ui.updateDisplay(currentInput);
                }
            }
            case "%" -> {
                if (!currentInput.isEmpty()) {
                    double value = Double.parseDouble(currentInput);
                    currentInput = String.valueOf(value / 100.0);
                    ui.updateDisplay(currentInput);
                }
            }
            case "+", "-", "*", "/" -> {
                if (!currentInput.isEmpty()) {
                    firstOperand = Double.parseDouble(currentInput);
                    operator = command;
                    ui.updateDisplay(firstOperand + " " + operator);
                    currentInput = "";
                }
            }
            case "=" -> {
                if (!currentInput.isEmpty() && !operator.isEmpty()) {
                    double secondOperand = Double.parseDouble(currentInput);
                    double result = switch (operator) {
                        case "+" -> firstOperand + secondOperand;
                        case "-" -> firstOperand - secondOperand;
                        case "*" -> firstOperand * secondOperand;
                        case "/" -> secondOperand != 0 ? firstOperand / secondOperand : Double.NaN;
                        default -> 0;
                    };
                    if (Double.isNaN(result)) {
                        ui.updateDisplay("Error: Division by Zero!");
                    } else {
                        ui.updateDisplay(String.valueOf(result));
                        currentInput = String.valueOf(result);
                        firstOperand = result;
                        operator = "";
                    }
                }
            }
            case "Del" -> {
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    ui.updateDisplay(currentInput);
                }
            }
            case "C" -> {
                currentInput = "";
                firstOperand = 0;
                operator = "";
                ui.updateDisplay("");
            }
        }
    }
}
