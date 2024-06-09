import java.util.ArrayList;

public interface LibraryManagementSystem {
    public void inventory(String filePath);
    public void lend(String isbn);
    public void putBack(String isbn);
    public void registerStudent(String Name);

    public Book search(String isbn);
    public ArrayList<Book> sort(int num);
}
