package edu.oop.library.lab3.model;
import java.time.LocalDate;
public class Loan {
    private final Book book;
    private final Reader reader;
    private final LocalDate dueDate;
    private boolean closed;
    public Loan(Book book, Reader reader, LocalDate start) {
        if (!book.isAvailable() || !reader.canBorrow()) throw new IllegalStateException("Видача неможлива");
        this.book = book; this.reader = reader; this.dueDate = start.plusDays(book.getLoanDays());
        book.borrow(); reader.registerLoan();
    }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isClosed() { return closed; }
    public boolean isOverdue(LocalDate day) { return !closed && day.isAfter(dueDate); }
    public void close() {
        if (closed) return;
        closed = true; book.returnToLibrary(); reader.closeLoan();
    }
    @Override public String toString() { return "Видача до " + dueDate + ", завершена=" + closed; }
}
