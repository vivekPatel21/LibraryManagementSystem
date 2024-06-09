import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class LibraryRunner {
    public static void main(String[] args) {
        Library library = new Library("inventory.txt","borrowed_books.txt");
        Scanner scan = new Scanner(System.in);
        library.inventory(library.getPath());

        System.out.println("Hello, welcome to the library, please pick from the options below");



        while(true) {
            System.out.println("1:registration" +
                    "\n2:Sort based on isbn or quantity" +
                    "\n3:Search based on isbn" +
                    "\n4:Borrow Book based on isbn" +
                    "\n5:Return Book"+
                    "\n6:bar graph of inventory" +
                    "\n7:Quit");
//            System.out.println(library.countNonfictionBooks());
            String response = scan.nextLine();

            try {
                int properResponse = Integer.parseInt(response);
                switch (properResponse) {
                    case 1:
                        System.out.println("Please enter your name.");
                        String nameResponse = scan.nextLine();
                        library.registerStudent(nameResponse);
                        break;


                    case 2:
                        // code for sorting based on isbn
                        System.out.println("Please type the isbn or quantity you would like to sort by");
                        String sortISBN = scan.nextLine();
                        try{
                            int intSortISBN = Integer.parseInt(sortISBN);
                            library.sort(intSortISBN);
                            System.out.println("Check the inventory, you should see the book close to the isbn" +
                                    " on the top of the list");
                        } catch(IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }

                        break;


                    case 3:
                        // code for searching based on isbn
                        System.out.println("what is the ISBN you want to search by?");
                        String searchISBN = scan.nextLine();
                        Book savedBook = library.search(searchISBN);
                        try{
                            savedBook.toString();
                        } catch(Exception e){
                            throw new RuntimeException(e);
                        }

                        break;


                    case 4:
                        // code for borrowing book based on isbn
                        System.out.println("What is your name?");
                        String inputName = scan.nextLine();

                        //this will check for the student existing.
                        ArrayList<Student> students =library.getStudents();
                        boolean check = false;
                        int mark = 0;
                        for(int i = 0; i < students.size(); i++){
                            if(inputName.equals(students.get(i).getName())){
                                check = true;
                                mark = i;
                                break;
                            }
                        }

                        //calls student borrow book
                        if(check == true){
                            System.out.println("What is the book ISBN you are trying to borrow?");
                            String inputIsbn = scan.nextLine();
                            students.get(mark).borrowBook(inputIsbn,students.get(mark).getRegistrationNumber(),library);
                        } else {
                            System.out.println("You are not in the system, adding you to system" +
                                    "after you are added try again.");
                            library.registerStudent(inputName);
                        }



                        break;
                    case 5:
                        // code for returning book
                        System.out.println("What is your name?");
                        inputName = scan.nextLine();

                        //this will check for the student existing.
                        students = library.getStudents();
                        check = false;
                        mark = 0;
                        for(int i = 0; i < students.size(); i++){
                            if(inputName.equals(students.get(i).getName())){
                                check = true;
                                mark = i;
                                break;
                            }
                        }

                        //calls student to give book back
                        if(check == true){
                            System.out.println("What is the book ISBN you are trying to return?");
                            String inputIsbn = scan.nextLine();
                            students.get(mark).returnBook(inputIsbn,students.get(mark).getRegistrationNumber(),library);
                        }  else {
                            System.out.println("You are not in the system, adding you to system" +
                                    "after you are added try again.");
                            library.registerStudent(inputName);
                        }
                        break;
                    case 6:
                        int[] available = library.availableBooks();
                        int numNonfiction = available[1];
                        int numFiction = available[0];
                        List<Integer> books = Arrays.asList(numNonfiction, numFiction);
                        InventoryChart chart = new InventoryChart(books, "Inventory Chart");
                        chart.displayGraph();
                        break;


//                        call inventoryChart

                    case 7:
                        //code to quit
                        System.out.println("Thank you for using the library.");
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid input. Please enter a number between 1 and 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            if(response.equals("7")){
                break;
            }
        }
    }
}