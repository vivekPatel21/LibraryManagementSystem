public class FictionBook extends Book implements Comparable<FictionBook> {

    private int numPages;
    private int quantity;

    public FictionBook(String isbn, String name, String author, int numPages, int quantity) {
        super(isbn, name, author, quantity);
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.numPages = numPages;
        this.quantity = quantity;
    }

    public int getNumPages() {
        return numPages;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Fiction Book [ISBN: " + getIsbn() + ", Name: " + getName() + ", Author: " + getAuthor() +
                ", Number of Pages: " + numPages + ", Quantity: " + quantity + "]";
    }

    @Override
    public int compareTo(FictionBook otherBook) {
        return Integer.compare(this.numPages, otherBook.numPages);
    }

    @Override
    public int compareTo(NonfictionBook otherBook) {
        return Integer.compare(this.numPages, otherBook.getNumPages());
    }
}