package edu.oop.library.lab3.model;
public class Book {
    private final String isbn;
    private String title;
    private String author;
    private boolean available = true;
    private int loanDays = 21;
    public Book(String isbn, String title, String author) {
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("Порожній ISBN");
        this.isbn = isbn; setTitle(title); setAuthor(author);
    }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }
    public int getLoanDays() { return loanDays; }
    public void setTitle(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Порожня назва");
        this.title = title.strip();
    }
    public void setAuthor(String author) {
        if (author == null || author.isBlank()) throw new IllegalArgumentException("Порожній автор");
        this.author = author.strip();
    }
    public void setLoanDays(int loanDays) {
        if (loanDays < 1 || loanDays > 90) throw new IllegalArgumentException("Термін 1..90 днів");
        this.loanDays = loanDays;
    }
    public void borrow() {
        if (!available) throw new IllegalStateException("Примірник уже виданий");
        available = false;
    }
    public void returnToLibrary() { available = true; }
    protected String protectedInventoryLabel() { return "ІНВ-" + isbn; }
    String packageCode() { return "MODEL-" + isbn.substring(0, Math.min(3, isbn.length())); }
    @Override public String toString() {
        return "Книга: " + title + ", автор " + author + ", доступна=" + available;
    }
}
