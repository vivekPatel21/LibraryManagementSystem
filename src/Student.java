import java.io.BufferedReader;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Student {
    private String Name;
    private String RegistrationNumber;




    public Student(String Name, String RegistrationNumber){
        this.Name = Name;
        this.RegistrationNumber = RegistrationNumber;
    }




    //Getter and Setter for Name
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    //Getter and Setter for Reg. Number
    public String getRegistrationNumber() {
        return RegistrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    public void borrowBook(String isbn, String registrationNumber, Library lib) {
        Scanner scan = new Scanner(System.in);
        int count = 0;
        boolean control = false;
        boolean control2 = false;
        while (true) {
            System.out.println("To double check please re-enter your registration number");
            String inpReg = scan.nextLine();

            if (inpReg.equals(registrationNumber)) {
                System.out.println("Thank you");
                control = true;
                control2 = true;
                break;
            }

            if (count > 3) {
                System.out.println("Too many failed attempts, you will have to go through the progress again.");
            }
            count++;
        }

        // thanks to control, it can tell if the loop above was exited properly or was counted out.
        if (control == true) {
            lib.lend(isbn);

            try {
                BufferedReader reader = new BufferedReader(new FileReader(lib.getPath2()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    ArrayList<String> currentList = new ArrayList<String>();
                    String[] parts = line.split(",");
                    for (String part : parts) {
                        currentList.add(part);
                    }
                    if ((RegistrationNumber.equals(currentList.get(0)) && (isbn.equals(currentList.get(1))))) {
                        currentList.set(2, Integer.toString(Integer.parseInt(currentList.get(2)) + 1));
                        sb.append(String.join(",", currentList));
                        sb.append(System.lineSeparator());
                        control2 = false;
                    } else {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                    }
                }
                reader.close();

                BufferedWriter writer = new BufferedWriter(new FileWriter(lib.getPath2()));
                writer.write(sb.toString());
                writer.close();
            } catch (FileNotFoundException e) {
            System.err.println("Error: file not found: " + e.getMessage());
        }catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        if(control2 == true){
            try {
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(lib.getPath2()));
                // this makes it easier to access the inventory for library.
                HashMap<String, Integer> inventory = lib.getInventory();
                if (inventory.containsKey(isbn)) {
                    int quantity = inventory.get(isbn);
                    writer.write(registrationNumber + "," + isbn+","+1);
                }

                writer.close();
            } catch (FileNotFoundException e) {
                System.err.println("Error: file not found: " + e.getMessage());
            }catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }



    public void returnBook(String isbn, String RegistrationNumber, Library lib) {
        Scanner scan = new Scanner(System.in);
        int count = 0;
        boolean control = false;
        while (true) {
            System.out.println("To double check please re-enter your registration number");
            String inpReg = scan.nextLine();

            if (inpReg.equals(RegistrationNumber)) {
                System.out.println("Thank you");
                control = true;
                break;
            }

            if (count > 3) {
                System.out.println("Too many failed attempts, you will have to go through the progress again.");
            }
            count++;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(lib.getPath2()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                ArrayList<String> currentList = new ArrayList<String>();
                String[] parts = line.split(",");
                for (String part : parts) {
                    currentList.add(part);
                }
                if (RegistrationNumber.equals(currentList.get(0)) && isbn.equals(currentList.get(1))) {
                    int quantity = Integer.parseInt(currentList.get(2));
                    if (quantity == 0) {
                        // Skip adding the line to the StringBuilder
                        continue;
                    }
                    currentList.set(2, Integer.toString(quantity - 1));
                    sb.append(String.join(",", currentList));
                    sb.append(System.lineSeparator());

                    if (quantity - 1 == 0) {
                        // Remove the line from the file
                        continue;
                    }
                } else {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(lib.getPath2()));
            writer.write(sb.toString());
            System.out.println("book returned");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}