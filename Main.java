package edu.oop.library.lab3.app;
import edu.oop.library.lab3.model.*;
import edu.oop.library.lab3.extensions.SpecialBook;
import java.time.LocalDate;
public class Main {
    public static void main(String[] args) {
        SpecialBook book = new SpecialBook("978-617-000-001-1", "Java. Основи", "О. Петренко");
        Reader reader = new Reader("R-104", "Марія Коваль");
        Librarian librarian = new Librarian("E-07", "Ірина Бондар", "Абонемент");
        LibraryBranch branch = new LibraryBranch("Центральна", "вул. Соборна, 10", 1000);
        LibraryCatalog catalog = new LibraryCatalog("Основний", "Програмування");
        catalog.addBook(book);
        book.setLoanDays(14);
        Loan loan = new Loan(book, reader, LocalDate.of(2026, 10, 1));
        System.out.println("=== Практична робота №3 ===");
        System.out.println(book); System.out.println(reader); System.out.println(librarian);
        System.out.println(branch); System.out.println(catalog); System.out.println(loan);
        System.out.println("private: читання ISBN через getter -> " + book.getIsbn());
        System.out.println("package-private (через клас того самого пакета) -> " + PackageInspector.inspect(book));
        System.out.println("protected (нащадок в іншому пакеті) -> " + book.inventoryLabelForDemo());
        System.out.println("public: термін видачі -> " + book.getLoanDays());
        // book.packageCode();  // НЕ компілюється: доступ лише у тому самому пакеті.
        // book.protectedInventoryLabel(); // НЕ компілюється з цього не-нащадка.
        // book.title = "...";   // НЕ компілюється: private поле.
        try { book.setLoanDays(-10); }
        catch (IllegalArgumentException ex) {
            System.out.println("Валідація відхилила некоректний термін: " + ex.getMessage());
        }
        loan.close();
        System.out.println("Після закриття: " + book + "; активних видач=" + reader.getActiveLoans());
    }
}
