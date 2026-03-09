import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class IndianLoanCalculator {

    private JFrame frame;
    private JTextField loanAmountField;
    private JTextField interestRateField;
    private JTextField yearsField;
    private JTextArea resultArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                IndianLoanCalculator window = new IndianLoanCalculator();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public IndianLoanCalculator() {
        initialize();
    }

    private void initialize() {

        frame = new JFrame("Indian Loan Calculator");
        frame.setBounds(100, 100, 420, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Background color
        frame.getContentPane().setBackground(new Color(230, 240, 255));

        Font labelFont = new Font("Arial", Font.BOLD, 13);

        // Loan Amount
        JLabel loanAmountLabel = new JLabel("Loan Amount (INR):");
        loanAmountLabel.setBounds(10, 10, 150, 25);
        loanAmountLabel.setFont(labelFont);
        loanAmountLabel.setForeground(new Color(0, 70, 140));
        frame.getContentPane().add(loanAmountLabel);

        loanAmountField = new JTextField();
        loanAmountField.setBounds(170, 10, 150, 25);
        frame.getContentPane().add(loanAmountField);

        // Interest Rate
        JLabel interestRateLabel = new JLabel("Interest Rate (%):");
        interestRateLabel.setBounds(10, 45, 150, 25);
        interestRateLabel.setFont(labelFont);
        interestRateLabel.setForeground(new Color(0, 70, 140));
        frame.getContentPane().add(interestRateLabel);

        interestRateField = new JTextField();
        interestRateField.setBounds(170, 45, 150, 25);
        frame.getContentPane().add(interestRateField);

        // Years
        JLabel yearsLabel = new JLabel("Loan Duration (Years):");
        yearsLabel.setBounds(10, 80, 170, 25);
        yearsLabel.setFont(labelFont);
        yearsLabel.setForeground(new Color(0, 70, 140));
        frame.getContentPane().add(yearsLabel);

        yearsField = new JTextField();
        yearsField.setBounds(170, 80, 100, 25);
        frame.getContentPane().add(yearsField);

        // Button
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(10, 120, 120, 30);
        calculateButton.setBackground(new Color(46, 139, 87));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        frame.getContentPane().add(calculateButton);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBounds(10, 160, 370, 100);
        resultArea.setBackground(new Color(245, 245, 245));
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 13));
        frame.getContentPane().add(resultArea);

        // Button Action
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    double loanAmount = Double.parseDouble(loanAmountField.getText());
                    double annualInterestRate = Double.parseDouble(interestRateField.getText());
                    int years = Integer.parseInt(yearsField.getText());

                    double monthlyInterestRate = annualInterestRate / 100 / 12;
                    int numberOfPayments = years * 12;

                    double monthlyPayment = calculateMonthlyPayment(
                            loanAmount,
                            monthlyInterestRate,
                            numberOfPayments);

                    double totalPayment = monthlyPayment * numberOfPayments;
                    double totalInterest = totalPayment - loanAmount;

                    String formattedMonthlyPayment = formatCurrency(monthlyPayment);
                    String formattedTotalPayment = formatCurrency(totalPayment);
                    String formattedTotalInterest = formatCurrency(totalInterest);

                    resultArea.setText(
                            "Monthly Payment: " + formattedMonthlyPayment + "\n\n" +
                                    "Total Payment:   " + formattedTotalPayment + "\n\n" +
                                    "Total Interest:  " + formattedTotalInterest
                    );

                } catch (NumberFormatException ex) {
                    resultArea.setText("Please enter valid numbers.");
                }
            }
        });
    }

    private double calculateMonthlyPayment(double loanAmount,
                                           double monthlyInterestRate,
                                           int numberOfPayments) {

        if (monthlyInterestRate == 0) {
            return loanAmount / numberOfPayments;
        }

        return (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
    }

    private String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("\u20B9#,##0.00");
        return formatter.format(amount);
    }
}