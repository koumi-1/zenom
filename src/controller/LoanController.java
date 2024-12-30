package controller;

import model.Loan;
import utils.CSVUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanController {
    private static final String LOANS_FILE = "resources/loans.csv";
    private List<Loan> loans;

    public LoanController() {
        this.loans = new ArrayList<Loan>();
        this.loadLoansFromFile();
    }

    private void loadLoansFromFile() {
        loans.addAll(CSVUtils.readCSV(LOANS_FILE).stream().map(row -> new Loan(Integer.parseInt(row[0]), Integer.parseInt(row[1]), Integer.parseInt(row[2]), LocalDate.parse(row[3]), LocalDate.parse(row[4]))).toList());
    }

    private void saveLoansToFile() {
        List<String[]> data = loans.stream().map(loan -> new String[]{String.valueOf(loan.getLoanId()), String.valueOf(loan.getUserId()), String.valueOf(loan.getBookId()), String.valueOf(loan.getLoanDate()), String.valueOf(loan.getReturnDate())}).toList();
        CSVUtils.writeCSV(LOANS_FILE, data);
    }

    public void addLoan(Loan loan) {
        this.loans.add(loan);
        this.saveLoansToFile();
        System.out.printf("Loan added: %s.\n", loan.getLoanId());
    }

    public void editLoan(Loan paramLoan) {
        this.loans.stream().filter(loan -> loan.getLoanId() == paramLoan.getLoanId()).findFirst().ifPresentOrElse(loan -> {
            loan.setUserId(paramLoan.getUserId());
            loan.setBookId(paramLoan.getBookId());
            loan.setLoanDate(paramLoan.getLoanDate());
            loan.setReturnDate(paramLoan.getReturnDate());
            System.out.printf("Loan edited: %s.\n", paramLoan.getLoanId());
        }, () -> {
            System.out.printf("Loan with ID %s not found.\n", paramLoan.getLoanId());
        });
    }

    public void deleteUser(int id) {
        this.loans.removeIf(loan -> loan.getLoanId() == id);
        System.out.printf("User with ID %s deleted.\n", id);
    }

    public void listUsers() {
        if (this.loans.isEmpty()) System.out.println("No loans available");
        else this.loans.forEach(System.out::println);
    }
}
