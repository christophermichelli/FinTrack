package FinTrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FinTrack {

    // Inner class to represent a transaction
    static class Transaction {
        String category;
        double amount; // Positive for spending, negative for income if you like

        Transaction(String category, double amount) {
            this.category = category;
            this.amount = amount;
        }
    }

    // List to store all transactions
    private ArrayList<Transaction> transactions;
    private double balance;

    public FinTrack(double startingBalance) {
        transactions = new ArrayList<>();
        balance = startingBalance;
    }

    // Add spending transaction
    public void addTransaction(String category, double amount) {
        transactions.add(new Transaction(category, amount));
        balance -= amount; // spending decreases balance
        System.out.println("Added spending: " + category + " - $" + amount);
    }

    // Add income transaction
    public void addIncome(String category, double amount) {
        transactions.add(new Transaction(category, -amount)); // negative if you want to keep category totals consistent
        balance += amount; // income increases balance
        System.out.println("Added income: " + category + " - $" + amount);
    }

    // View current balance
    public void viewBalance() {
        System.out.println("Current Balance: $" + balance);
    }

    // Show spending by category (ignores income for simplicity)
    public void viewCategorySummary() {
        HashMap<String, Double> categoryTotals = new HashMap<>();
        for (Transaction t : transactions) {
            double amt = t.amount;
            if (amt > 0) { // only count spending
                categoryTotals.put(t.category,
                        categoryTotals.getOrDefault(t.category, 0.0) + amt);
            }
        }
        System.out.println("Spending by Category:");
        for (String category : categoryTotals.keySet()) {
            System.out.println(category + ": $" + categoryTotals.get(category));
        }
    }

    // Predict next month spending (simple average)
    public void predictNextMonth(String category) {
        double total = 0;
        int count = 0;
        for (Transaction t : transactions) {
            if (t.category.equalsIgnoreCase(category) && t.amount > 0) {
                total += t.amount;
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No data for category: " + category);
        } else {
            double predicted = total / count;
            System.out.println("Predicted spending next month for " + category + ": $" + predicted);
        }
    }

    // Console-based interaction
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nFinTrack Menu:");
            System.out.println("1. Add Spending Transaction");
            System.out.println("2. Add Income");
            System.out.println("3. View Balance");
            System.out.println("4. View Category Summary");
            System.out.println("5. Predict Next Month Spending");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter spending category: ");
                    String spendCat = sc.nextLine();
                    System.out.print("Enter amount: $");
                    double spendAmt = sc.nextDouble();
                    sc.nextLine();
                    addTransaction(spendCat, spendAmt);
                    break;
                case 2:
                    System.out.print("Enter income source: ");
                    String incomeCat = sc.nextLine();
                    System.out.print("Enter amount: $");
                    double incomeAmt = sc.nextDouble();
                    sc.nextLine();
                    addIncome(incomeCat, incomeAmt);
                    break;
                case 3:
                    viewBalance();
                    break;
                case 4:
                    viewCategorySummary();
                    break;
                case 5:
                    System.out.print("Enter category for prediction: ");
                    String predCat = sc.nextLine();
                    predictNextMonth(predCat);
                    break;
                case 6:
                    System.out.println("Exiting FinTrack. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your starting balance: $");
        double startingBalance = sc.nextDouble();
        sc.nextLine(); // consume newline

        FinTrack tracker = new FinTrack(startingBalance);
        tracker.run();
    }
}