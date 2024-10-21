import java.io.*;
import java.util.*;

class Doctor {

    // Method to scan the ID and return the index or -1 if not found
    private int scan(int id) {
        int idFromFile;
        int result = 0;
        int index = 0;

        try (Scanner fin = new Scanner(new File("uniqueid.txt"))) {
            while (fin.hasNextInt()) {
                idFromFile = fin.nextInt();
                if (idFromFile == id) {
                    result = 1;
                    break;
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: uniqueid.txt not found.");
        }

        return (result == 0) ? -1 : index;
    }

    // Method to add history to a medical record
    private void addHistory(int index) {
        System.out.println("Enter Medical Data to be added (use '||' instead of newline): ");
        String addmed;

        try (Scanner scanner = new Scanner(System.in)) {
            addmed = scanner.nextLine();
        }

        List<String> data = new ArrayList<>();

        // Read current medical records from file
        try (BufferedReader fin = new BufferedReader(new FileReader("medrec.txt"))) {
            String line;
            while ((line = fin.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error: medrec.txt not found.");
        }

        // Insert the new medical record at the specified index
        if (index >= 0 && index <= data.size()) {
            data.add(index, addmed);
        }

        // Write the updated list back to the file
        try (BufferedWriter fout = new BufferedWriter(new FileWriter("medrec.txt"))) {
            for (String d : data) {
                fout.write(d);
                fout.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to medrec.txt.");
        }

        System.out.println("Data added successfully.");
    }

    // Method to view medical history
    private void viewHistory(int index) {
        try (BufferedReader fin = new BufferedReader(new FileReader("medrec.txt"))) {
            String record;
            int ind = 0;

            // Skip lines until reaching the desired index
            while (ind < index && (record = fin.readLine()) != null) {
                ind++;
            }

            // Print the record at the index if available
            if (ind == index && (record = fin.readLine()) != null) {
                if (record.contains("||")) {
                    System.out.println(record.replace("||", "\n"));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from medrec.txt.");
        }
    }

    // Main menu for doctor-related actions
    public void themain() {
        int index;
        System.out.println("Welcome to MedPlus Doctor!");

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("MENU:\n1. Enter EPIC ID\n2. View Appointments");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    index = scanningProcess();
                    choose(index);
                    break;
                case 2:
                    appointments();
                    break;
                default:
                    System.out.println("Invalid Input.");
            }
        }
    }

    // Method for scanning the EPIC ID
    private int scanningProcess() {
        int id;
        int index;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter EPIC ID:");
            id = scanner.nextInt();
        }

        int result = scan(id);
        if (result == -1) {
            System.out.println("ID not found, please enter the correct EPIC ID.");
            return scanningProcess();  // Retry if not found
        } else {
            index = result;
        }

        return index;
    }

    // Method to choose between viewing and adding to medical records
    private void choose(int index) {
        int choice;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("1. View Medical Records");
            System.out.println("2. Add to Medical Records");
            choice = scanner.nextInt();
        }

        switch (choice) {
            case 1:
                viewHistory(index);
                break;
            case 2:
                addHistory(index);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method to display appointments to the doctor
    private void appointments() {
        DisplaytoDoc();
    }

    // Method to display appointments (similar to your original DisplaytoDoc function)
    private void DisplaytoDoc() {
        try (BufferedReader reader = new BufferedReader(new FileReader("appointments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading appointments.txt.");
        }
    }

    public static void main(String[] args) {
        Doctor doc = new Doctor();
        doc.themain();
    }
}
