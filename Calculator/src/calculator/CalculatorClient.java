package calculator;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;
import java.awt.*;

public class CalculatorClient {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            CalculatorInterface calc =
                    (CalculatorInterface) registry.lookup("CalculatorService");

            JFrame frame = new JFrame("RMI Calculator");
            frame.setSize(300, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextField t1 = new JTextField();
            JTextField t2 = new JTextField();
            JLabel result = new JLabel("Result: ");

            JButton add = new JButton("+");
            JButton sub = new JButton("-");
            JButton mul = new JButton("*");
            JButton div = new JButton("/");

            add.addActionListener(e -> result.setText("Result: " + calcResult(calc, t1, t2, "+")));
            sub.addActionListener(e -> result.setText("Result: " + calcResult(calc, t1, t2, "-")));
            mul.addActionListener(e -> result.setText("Result: " + calcResult(calc, t1, t2, "*")));
            div.addActionListener(e -> result.setText("Result: " + calcResult(calc, t1, t2, "/")));

            frame.setLayout(new GridLayout(6, 1));
            frame.add(t1);
            frame.add(t2);
            frame.add(add);
            frame.add(sub);
            frame.add(mul);
            frame.add(div);
            frame.add(result);

            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static double calcResult(CalculatorInterface c, JTextField t1, JTextField t2, String op) {
        try {
            double a = Double.parseDouble(t1.getText());
            double b = Double.parseDouble(t2.getText());
            return switch (op) {
                case "+" -> c.add(a, b);
                case "-" -> c.subtract(a, b);
                case "*" -> c.multiply(a, b);
                case "/" -> c.divide(a, b);
                default -> 0;
            };
        } catch (Exception e) {
            return 0;
        }
    }
}