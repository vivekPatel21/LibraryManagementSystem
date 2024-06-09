import java.util.*;
import java.io.*;

public class Library implements LibraryManagementSystem{



    private ArrayList<FictionBook> fictionBooks = new ArrayList<>();
    private HashMap<String, Integer> inventory = new HashMap<>();
    private ArrayList<NonfictionBook> nonfictionBooks = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();


    private final String path;
    private final String path2;

    public Library(String path,String path2){
        this.path = path;
        this.path2 = path2;
    }

    public String getPath() {
        return path;
    }

    public String getPath2() {
        return path2;
    }

    public int[] availableBooks() {
        int[] count = new int[2];
        count[0] = countFictionBooks();
        count[1] = countNonfictionBooks();
        return count;
    }

    private int countFictionBooks() {
        int count = 0;
        for (FictionBook book : fictionBooks) {
            if (inventory.getOrDefault(book.getIsbn(), 0) > 0) {
                count++;
            }
        }
        return count;
    }

    private int countNonfictionBooks() {
        int count = 0;
        for (NonfictionBook book : nonfictionBooks) {
            if (inventory.getOrDefault(book.getIsbn(), 0) > 0) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<NonfictionBook> setNonfictionBooks(ArrayList<NonfictionBook> nonfictionBooks) {
        this.nonfictionBooks = nonfictionBooks;
        return this.nonfictionBooks;
    }

    public ArrayList<FictionBook> getFictionBooks(){
        return fictionBooks;
    }
    public HashMap<String, Integer> getInventory(){
        return inventory;
    }
    public ArrayList<NonfictionBook>getNonfictionBooks(){
        return nonfictionBooks;
    }
    public ArrayList<Student>getStudents(){
        return students;
    }


    public ArrayList<FictionBook> setFictionBooks(ArrayList<FictionBook> books) {
        this.fictionBooks = books;
        return this.fictionBooks;
    }


    public void setInventory(HashMap<String, Integer> newInventory) {
        this.inventory = newInventory;
    }



    @Override
    public void inventory(String filePath) {
        //this will put everything from inventory into a 2d arrayList so that it is easier to work with.
        ArrayList<String> sortPart1 = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                sortPart1.add(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        ArrayList<ArrayList<String>> sortPart2 = new ArrayList<>();

        for (String inputString : sortPart1) {
            String[] inputArray = inputString.split(",");
            ArrayList<String> outputArray = new ArrayList<>();
            for (String value : inputArray) {
                outputArray.add(value);
            }
            sortPart2.add(outputArray);
        }

        for(int i = 0;i < sortPart2.size();i++){
            System.out.println(sortPart2.get(i));
        }


        for (int i = 0; i < sortPart2.size(); i++) {
            if(sortPart2.get(i).get(5).equals("nonfiction")){
                String bookIsbn = sortPart2.get(i).get(0);
                String bookName = sortPart2.get(i).get(1);
                String bookAuthor = sortPart2.get(i).get(2);
                String bookPages = sortPart2.get(i).get(3);
                String bookQuantity = sortPart2.get(i).get(4);
                String bookType = sortPart2.get(i).get(5);

                int pages;
                int quantity;
                try{

                  pages = Integer.parseInt(bookPages);
                  quantity = Integer.parseInt(bookQuantity);
                  NonfictionBook book = new NonfictionBook(bookIsbn,bookName,bookAuthor,pages,quantity);

                  //System.out.println(book.toString());
                  nonfictionBooks.add(book);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Error creating NonfictionBook with ISBN "
                            + bookIsbn + ", name " + bookName + ", author " + bookAuthor +
                            ", pages " + bookPages + ", and quantity " + bookQuantity +
                            ": " + e.getMessage());
                }
            }

            if(sortPart2.get(i).get(5).equals("fiction")){
                String bookIsbn = sortPart2.get(i).get(0);
                String bookName = sortPart2.get(i).get(1);
                String bookAuthor = sortPart2.get(i).get(2);
                String bookPages = sortPart2.get(i).get(3);
                String bookQuantity = sortPart2.get(i).get(4);
                String bookType = sortPart2.get(i).get(5);
                try{
                    int pages;
                    int quantity;
                    pages = Integer.parseInt(bookPages);
                    quantity = Integer.parseInt(bookQuantity);
                    FictionBook book = new FictionBook(bookIsbn,bookName,bookAuthor,pages,quantity);

//                    System.out.println(book.toString());
                    fictionBooks.add(book);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Error creating Fiction with ISBN "
                            + bookIsbn + ", name " + bookName + ", author " + bookAuthor +
                            ", pages " + bookPages + ", and quantity " + bookQuantity +
                            ": " + e.getMessage());
                }
            }

            try{
                String bookName = sortPart2.get(i).get(0);
//                System.out.println(bookName);
                String bookQuantity =sortPart2.get(i).get(4);
//                System.out.println(bookQuantity);

                int quantity = Integer.parseInt(bookQuantity);
//                System.out.println(quantity);

                inventory.put(bookName,quantity);
            } catch (RuntimeException e){
                throw new RuntimeException("Error adding something to inventory");
            }

        }
        /*for(int i = 0; i < this.nonfictionBooks.size();i++){
            System.out.println(nonfictionBooks.get(i).toString());
        }*/
    }
    @Override
    public void lend(String isbn) {
        Integer quantity = inventory.get(isbn);
        if (quantity == null || quantity == 0) {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " is not available for lending.");
        }
        inventory.put(isbn, quantity - 1);
        for(int i = 0;i < this.nonfictionBooks.size();i++){
            if(isbn.equals(nonfictionBooks.get(i).getIsbn())){
                nonfictionBooks.get(i).setQuantity(nonfictionBooks.get(i).getQuantity() - 1);
                break;
            }
        }
        for(int i = 0;i < this.fictionBooks.size();i++){
            if(isbn.equals(fictionBooks.get(i).getIsbn())){
                fictionBooks.get(i).setQuantity(fictionBooks.get(i).getQuantity() - 1);
                break;
            }
        }
    }


    @Override
    public void putBack(String isbn) {
        if (!inventory.containsKey(isbn)) {
            throw new IllegalArgumentException("Book not found in inventory.");
        }
        int quantity = inventory.get(isbn);
        inventory.put(isbn, quantity + 1);
        for(int i = 0;i < this.nonfictionBooks.size();i++){
            if(isbn.equals(nonfictionBooks.get(i).getIsbn())){
                nonfictionBooks.get(i).setQuantity(nonfictionBooks.get(i).getQuantity() + 1);
                break;
            }
        }
        for(int i = 0;i < this.fictionBooks.size();i++){
            if(isbn.equals(fictionBooks.get(i).getIsbn())){
                fictionBooks.get(i).setQuantity(fictionBooks.get(i).getQuantity() + 1);
                break;
            }
        }
    }


    @Override
    public void registerStudent(String Name){
        boolean flag = true;
        for (Student s : students) {
            if (s.getName().equals(Name)) {
                System.out.println("Student already Registered");
                flag = false;
                break;
            }
            flag = true;
        }
        if (flag == true) {
            int regNum = (int) (Math.random() * 10000) + 10000;
            String strRegNum = (String.valueOf(regNum));
            Student student = new Student(Name,strRegNum);
            students.add(student);
            System.out.println(Name +" your registration number is " + regNum+".");
        }

    }

    public Book search(String isbn) {
        // Combine the fiction and non-fiction book arrays
        int fictionLength = fictionBooks.size();
        int nonfictionLength = nonfictionBooks.size();
        int totalLength = fictionLength + nonfictionLength;
        Book[] allBooks = new Book[totalLength];
        System.arraycopy(fictionBooks.toArray(), 0, allBooks, 0, fictionLength);
        System.arraycopy(nonfictionBooks.toArray(), 0, allBooks, fictionLength, nonfictionLength);

        // Sort the combined array by ISBN using merge sort
        mergeSort(allBooks, 0, totalLength - 1);

        // Perform a binary search on the sorted array
        int index = binarySearch(allBooks, isbn, 0, totalLength - 1);

        if (index == -1) {
            // The book was not found
            return null;
        } else {
            // The book was found
            Book foundBook = allBooks[index];
            System.out.println(foundBook.toString());
            return foundBook;
            //polymorphism in Library Runner will let us display a variety of messages.
        }
    }

    private int binarySearch(Book[] books, String isbn, int low, int high) {
        if (low > high) {
            // The book was not found
            return -1;
        }

        int mid = (low + high) / 2;
        String midIsbn = books[mid].getIsbn();

        if (isbn.compareTo(midIsbn) == 0) {
            // The book was found
            return mid;
        } else if (isbn.compareTo(midIsbn) < 0) {
            // The book may be in the lower half of the array
            return binarySearch(books, isbn, low, mid - 1);
        } else {
            // The book may be in the upper half of the array
            return binarySearch(books, isbn, mid + 1, high);
        }
    }

    private void mergeSort(Book[] books, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(books, low, mid);
            mergeSort(books, mid + 1, high);
            merge(books, low, mid, high);
        }
    }

    private void merge(Book[] books, int low, int mid, int high) {
        Book[] temp = new Book[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= high) {
            String iIsbn = books[i].getIsbn();
            String jIsbn = books[j].getIsbn();
            if (iIsbn.compareTo(jIsbn) <= 0) {
                temp[k] = books[i];
                i++;
            } else {
                temp[k] = books[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            temp[k] = books[i];
            i++;
            k++;
        }

        while (j <= high) {
            temp[k] = books[j];
            j++;
            k++;
        }

        System.arraycopy(temp, 0, books, low, temp.length);
    }



    @Override
    public ArrayList<Book> sort(int num) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type 1 to sort by ISBN or 2 to sort by quantity");
        String userResponse = scan.nextLine();
        int response;
        try{
            response = Integer.parseInt(userResponse);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        switch(response){
            // sort by ISBN
            case 1:
                for (int i = 0; i < nonfictionBooks.size(); i++) {
                    for (int j = i + 1; j < nonfictionBooks.size(); j++) {
                        if (nonfictionBooks.get(i).getIsbn().compareTo(nonfictionBooks.get(j).getIsbn()) > 0) {
                            NonfictionBook temp = nonfictionBooks.get(i);
                            nonfictionBooks.set(i, nonfictionBooks.get(j));
                            nonfictionBooks.set(j, temp);
                        }
                    }
                }
                for (int i = 0; i < fictionBooks.size(); i++) {
                    for (int j = i + 1; j < fictionBooks.size(); j++) {
                        if (fictionBooks.get(i).getIsbn().compareTo(fictionBooks.get(j).getIsbn()) > 0) {
                            FictionBook temp = fictionBooks.get(i);
                            fictionBooks.set(i, fictionBooks.get(j));
                            fictionBooks.set(j, temp);
                        }
                    }
                }
                break;

            // sort by Quantity
            case 2:
                for (int i = 0; i < nonfictionBooks.size(); i++) {
                    for (int j = i + 1; j < nonfictionBooks.size(); j++) {
                        if (nonfictionBooks.get(i).getQuantity() > nonfictionBooks.get(j).getQuantity()) {
                            NonfictionBook temp = nonfictionBooks.get(i);
                            nonfictionBooks.set(i, nonfictionBooks.get(j));
                            nonfictionBooks.set(j, temp);
                        }
                    }
                }
                for (int i = 0; i < fictionBooks.size(); i++) {
                    for (int j = i + 1; j < fictionBooks.size(); j++) {
                        if (fictionBooks.get(i).getQuantity() > fictionBooks.get(j).getQuantity()) {
                            FictionBook temp = fictionBooks.get(i);
                            fictionBooks.set(i, fictionBooks.get(j));
                            fictionBooks.set(j, temp);
                        }
                    }
                }
                break;

            default:
                throw new RuntimeException("Invalid response. Please enter 1 or 2.");
        }

        // combine sorted lists into one
        ArrayList<Book> sortedBooks = new ArrayList<>(nonfictionBooks.size() + fictionBooks.size());
        sortedBooks.addAll(nonfictionBooks);
        sortedBooks.addAll(fictionBooks);

        for(int i = 0;i < sortedBooks.size();i++){
            System.out.println(sortedBooks.get(i));
        }
        return sortedBooks;
    }

}
