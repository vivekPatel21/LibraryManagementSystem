public abstract class Book {
    public String author;
    public String isbn;
    public String name;
    public String pages;



    public Book(){}

    public Book(String isbn, String name, String author, int quantity) {

    }

    public abstract String toString();

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }


    public abstract int compareTo(NonfictionBook otherBook);

    public abstract int compareTo(FictionBook otherBook);
//    make instance of with one class, create the methods for each instance anf check for one
}