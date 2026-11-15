package edu.oop.library.lab3.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class LibraryCatalog {
    private final String name;
    private final List<Book> books = new ArrayList<>();
    private String section;
    public LibraryCatalog(String name, String section) {
        this.name = name; setSection(section);
    }
    public String getName() { return name; }
    public String getSection() { return section; }
    public void setSection(String section) {
        if (section == null || section.isBlank()) throw new IllegalArgumentException("Порожній розділ");
        this.section = section;
    }
    public void addBook(Book book) {
        if (book == null) throw new IllegalArgumentException("Книга не може бути null");
        books.add(book);
    }
    public List<Book> getBooks() { return Collections.unmodifiableList(books); }
    public int countBooks() { return books.size(); }
    @Override public String toString() { return "Каталог " + name + ": " + books.size() + " примірник(ів)"; }
}
