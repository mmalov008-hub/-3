package edu.oop.library.lab3.extensions;
import edu.oop.library.lab3.model.Book;
public class SpecialBook extends Book {
    public SpecialBook(String isbn, String title, String author) {
        super(isbn, title, author);
    }
    // Інший пакет: protected доступний у нащадка через this.
    public String inventoryLabelForDemo() { return protectedInventoryLabel(); }
}
