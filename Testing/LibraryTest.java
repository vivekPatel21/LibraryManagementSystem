import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

class LibraryTest {

    @Test
    void testGetPath() {
        Library library = new Library("inventory.txt", "borrowed_books.txt");
        assertEquals("inventory.txt", library.getPath());
    }

    @Test
    void testGetPath2() {
        Library library = new Library("inventory.txt", "borrowed_books.txt");
        assertEquals("borrowed_books.txt", library.getPath2());
    }

    @Test
    public void testAvailableBooks() {
        // Create a new library object
        Library library = new Library("inventory.txt", "borrowed_books.txt");

        // Set up some sample inventory
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("12345", 1);
        inventory.put("67890", 0);
        inventory.put("11111", 2);
        inventory.put("22222", 0);
        inventory.put("33333", 3);
        inventory.put("44444", 0);
        library.setInventory(inventory);

        // Set up some sample fiction and nonfiction books
        List<FictionBook> fictionBooks = new ArrayList<>();
        List<NonfictionBook> nonfictionBooks = new ArrayList<>();
        fictionBooks.add(new FictionBook("12345", "The Great Gatsby", "F. Scott Fitzgerald", 180, 1));
        fictionBooks.add(new FictionBook("67890", "To Kill a Mockingbird", "Harper Lee", 281, 0));
        fictionBooks.add(new FictionBook("11111", "The Catcher in the Rye", "J.D. Salinger", 224, 2));
        nonfictionBooks.add(new NonfictionBook("22222", "The Elements of Style", "William Strunk Jr. and E.B. White", 105, 0));
        nonfictionBooks.add(new NonfictionBook("33333", "The Art of War", "Sun Tzu", 58, 3));
        nonfictionBooks.add(new NonfictionBook("44444", "The 7 Habits of Highly Effective People", "Stephen Covey", 381, 0));
        library.setFictionBooks((ArrayList<FictionBook>) fictionBooks);
        library.setNonfictionBooks((ArrayList<NonfictionBook>) nonfictionBooks);

        // Test the availableBooks() method
        int[] expectedCounts = {2, 1};
        int[] actualCounts = library.availableBooks();
        assertArrayEquals(expectedCounts, actualCounts);
    }


    @Test
    void testGetFictionBooks() {
        Library library = new Library("inventory.txt", "borrowed_books.txt");
        assertEquals(0, library.getFictionBooks().size());
    }

    @Test
    void testGetInventory() {
        Library library = new Library("inventory.txt", "borrowed_books.txt");
        assertEquals(0, library.getInventory().size());
    }

    @Test
    void testGetNonfictionBooks() {
        Library library = new Library("inventory.txt", "borrowed_books.txt");
        List<NonfictionBook> nonfictionBooks = new ArrayList<>();
        NonfictionBook book1 = new NonfictionBook("76349","How to Stop Worrying and Start Living","Dale Carnegie",301,10);
        assertEquals(nonfictionBooks, library.getNonfictionBooks());
    }

    @Test
    void testGetStudents() {
        Library library = new Library("inventory.txt", "borrowed_books.txt");
        assertEquals(0, library.getStudents().size());
    }

    @Test
    void testSetFictionBooks() {
        Library library = new Library("inventory.txt", "borrowed_books.txt");
        ArrayList<FictionBook> fictionBooks = new ArrayList<>();
        fictionBooks.add(new FictionBook("78350","1984","George Orwell",328,21));
        library.setFictionBooks(fictionBooks);
        assertEquals(fictionBooks, library.getFictionBooks());
    }

    @Test
    void testSetInventory() {
        Library library = new Library("inventory.txt", "borrowed_books.txt");

        // Create a new inventory
        HashMap<String, Integer> newInventory = new HashMap<>();
        newInventory.put("The Catcher in the Rye", 5);
        newInventory.put("To Kill a Mockingbird", 3);

        // Set the new inventory in the library
        library.setInventory(newInventory);

        // Check that the inventory has been updated correctly
        assertEquals(5, library.getInventory().get("The Catcher in the Rye"));
        assertEquals(3, library.getInventory().get("To Kill a Mockingbird"));

    }

    @Test
    void testInventory() {
        Library inventory = new Library("inventory.txt", "borrowed_books.txt");
        String filePath = "inventory.txt";
        inventory.inventory(filePath);

        ArrayList<FictionBook> fictionBooks = inventory.getFictionBooks();
        ArrayList<NonfictionBook> nonfictionBooks = inventory.getNonfictionBooks();
        HashMap<String, Integer> inventoryMap = inventory.getInventory();

        // check that the inventory contains the correct number of items
        assertEquals(33, fictionBooks.size());
        assertEquals(25, nonfictionBooks.size());
        assertEquals(59, inventoryMap.size());

        // check that the first fiction book was read correctly
        FictionBook firstFictionBook = inventory.getFictionBooks().get(0);
        assertEquals("78350", firstFictionBook.getIsbn());
        assertEquals("1984", firstFictionBook.getName());
        assertEquals("George Orwell", firstFictionBook.getAuthor());
        assertEquals(328, firstFictionBook.getNumPages());
        assertEquals(21, firstFictionBook.getQuantity());

        // check that the first nonfiction book was read correctly
        NonfictionBook firstNonfictionBook = inventory.getNonfictionBooks().get(0);
        assertEquals("82749", firstNonfictionBook.getIsbn());
        assertEquals("A Brief History of Time", firstNonfictionBook.getName());
        assertEquals("Stephen Hawking", firstNonfictionBook.getAuthor());
        assertEquals(212, firstNonfictionBook.getNumPages());
        assertEquals(13, firstNonfictionBook.getQuantity());

    }





    @Test
    void testLend() {
        Library library = new Library("inventory.txt", "borrowed_books.txt");
        String isbn = "72891";
        try {
            library.lend(isbn);
        } catch (IllegalArgumentException e) {
        }
    }

}