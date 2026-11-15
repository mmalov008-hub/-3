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
