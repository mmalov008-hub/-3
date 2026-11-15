package edu.oop.library.lab3.model;
public final class PackageInspector {
    private PackageInspector() {}
    // Той самий пакет: package-private метод Book.packageCode() доступний.
    public static String inspect(Book book) { return book.packageCode(); }
}
