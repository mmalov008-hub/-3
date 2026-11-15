package edu.oop.library.lab3.model;
public class Reader {
    private final String cardNumber;
    private String name;
    private int activeLoans;
    private int maximumLoans = 5;
    public Reader(String cardNumber, String name) {
        if (cardNumber == null || cardNumber.isBlank()) throw new IllegalArgumentException("Немає квитка");
        this.cardNumber = cardNumber; setName(name);
    }
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Порожнє ПІБ");
        this.name = name.strip();
    }
    public String getName() { return name; }
    public String getCardNumber() { return cardNumber; }
    public int getActiveLoans() { return activeLoans; }
    public int getMaximumLoans() { return maximumLoans; }
    public void setMaximumLoans(int maximumLoans) {
        if (maximumLoans < activeLoans || maximumLoans > 20 || maximumLoans < 1)
            throw new IllegalArgumentException("Некоректний ліміт");
        this.maximumLoans = maximumLoans;
    }
    public boolean canBorrow() { return activeLoans < maximumLoans; }
    public void registerLoan() {
        if (!canBorrow()) throw new IllegalStateException("Ліміт вичерпано");
        activeLoans++;
    }
    public void closeLoan() {
        if (activeLoans == 0) throw new IllegalStateException("Немає видач");
        activeLoans--;
    }
    @Override public String toString() {
        return "Читач " + name + " (" + cardNumber + "), видач " + activeLoans;
    }
}
