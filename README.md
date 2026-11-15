=== Практична робота №3 ===
Книга: Java. Основи, автор О. Петренко, доступна=false
Читач Марія Коваль (R-104), видач 1
Бібліотекар Ірина Бондар / Абонемент
Філія Центральна, вул. Соборна, 10, 1000 місць
Каталог Основний: 1 примірник(ів)
Видача до 2026-10-15, завершена=false
private: читання ISBN через getter -> 978-617-000-001-1
package-private (через клас того самого пакета) -> MODEL-978
protected (нащадок в іншому пакеті) -> ІНВ-978-617-000-001-1
public: термін видачі -> 14
Валідація відхилила некоректний термін: Термін 1..90 днів
Після закриття: Книга: Java. Основи, автор О. Петренко, доступна=true; активних видач=0

#!/usr/bin/env bash
set -eu
cd "$(dirname "$0")"
mkdir -p build
find src -name '*.java' -print0 | xargs -0 javac -encoding UTF-8 -d build
java -cp build edu.oop.library.lab3.app.Main

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

package edu.oop.library.lab3.extensions;
import edu.oop.library.lab3.model.Book;
public class SpecialBook extends Book {
    public SpecialBook(String isbn, String title, String author) {
        super(isbn, title, author);
    }
    // Інший пакет: protected доступний у нащадка через this.
    public String inventoryLabelForDemo() { return protectedInventoryLabel(); }
}

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

package edu.oop.library.lab3.model;
public class Librarian {
    private final String employeeId;
    private String name;
    private String department;
    public Librarian(String employeeId, String name, String department) {
        this.employeeId = employeeId; this.name = name; setDepartment(department);
    }
    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) {
        if (department == null || department.isBlank()) throw new IllegalArgumentException("Відділ обов'язковий");
        this.department = department.strip();
    }
    public boolean mayProcessLoans() { return !department.equalsIgnoreCase("Архів"); }
    @Override public String toString() { return "Бібліотекар " + name + " / " + department; }
}

package edu.oop.library.lab3.model;
public class LibraryBranch {
    private final String name;
    private String address;
    private int capacity;
    public LibraryBranch(String name, String address, int capacity) {
        this.name = name; setAddress(address); setCapacity(capacity);
    }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getCapacity() { return capacity; }
    public void setAddress(String address) {
        if (address == null || address.isBlank()) throw new IllegalArgumentException("Порожня адреса");
        this.address = address;
    }
    public void setCapacity(int capacity) {
        if (capacity < 1) throw new IllegalArgumentException("Місткість додатна");
        this.capacity = capacity;
    }
    public boolean hasSpaceFor(int booksCount) { return booksCount < capacity; }
    @Override public String toString() { return "Філія " + name + ", " + address + ", " + capacity + " місць"; }
}

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

package edu.oop.library.lab3.model;
public final class PackageInspector {
    private PackageInspector() {}
    // Той самий пакет: package-private метод Book.packageCode() доступний.
    public static String inspect(Book book) { return book.packageCode(); }
}

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

Практична робота №3
Java 17+ (перевірено на OpenJDK 21).

Linux/macOS:
bash run.sh

Windows PowerShell (у каталозі цієї практичної):
New-Item -ItemType Directory -Force build | Out-Null
$files = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d build $files
java -cp build edu.oop.library.lab3.app.Main

Фактичний перевірений вивід: console_output.txt
