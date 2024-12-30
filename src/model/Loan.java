package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class Loan {
    private int loanId;
    private int userId;
    private int bookId;
    private LocalDate loanDate;
    private LocalDate returnDate = null;
}
