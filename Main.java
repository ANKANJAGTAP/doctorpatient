import java.io.*;
import java.util.*;

class User {
    private String pw;
    public String name, email;
    public long mobNo;

    // Method to set password with verification
    private void Password() {
        try (Scanner scanner = new Scanner(System.in)) {
            String p1, p2;
            System.out.println("Enter Password: ");
            p1 = scanner.nextLine();
            System.out.println("Confirm Password: ");
            p2 = scanner.nextLine();

            if (!p1.equals(p2)) {
                System.out.println("Passwords do not match each other, please re-enter");
                Password();
            } else {
                pw = p2;
            }
        }
    }

    // Method to save user data into a text file
    private void Input() {
        try (BufferedWriter fout = new BufferedWriter(new FileWriter("store.txt", true))) {
            fout.write(name + "\n" + email + "\n" + mobNo + "\n" + pw + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    // Method for signing up a user
    public void signUp() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Create your profile\n");
            System.out.println("Enter your name: ");
            name = scanner.nextLine();
            System.out.println("Enter your Phone Number: ");
            mobNo = scanner.nextLong();
            scanner.nextLine(); // Consume leftover newline
            System.out.println("Enter your E-Mail ID: ");
            email = scanner.nextLine();
            verify();
            Password();
            Input();
        }
    }

    // Method for verifying user details
    public void verify() {
        try (Scanner scanner = new Scanner(System.in)) {
            char ch;
            System.out.println("\nPlease verify your Details\n");
            Display();

            System.out.println("Do you want to change anything? (y/n)");
            ch = scanner.next().charAt(0);
            if (ch == 'y' || ch == 'Y') {
                System.out.println("Enter 1 to change Name,\n2 to change Email Address,\n3 to change Phone Number");
                int c = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline
                switch (c) {
                    case 1:
                        System.out.println("Enter your Name: ");
                        name = scanner.nextLine();
                        break;
                    case 2:
                        System.out.println("Enter your E-mail: ");
                        email = scanner.nextLine();
                        break;
                    case 3:
                        System.out.println("Enter your Phone Number: ");
                        mobNo = scanner.nextLong();
                        break;
                    default:
                        System.out.println("Please enter a valid choice.");
                }
                verify(); // Re-verify after change
            } else if (ch != 'n' && ch != 'N') {
                System.out.println("Please input either 'y' or 'n'.");
                verify();
            }
        }
    }

    // Method to display user details
    public void Display() {
        System.out.println("Name: " + name + "\nE-mail Address: " + email + "\nPhone Number: " + mobNo);
    }
}

class Doctor {
    // Method to scan ID from file and return index
    private int scan(int id) {
        int id1;
        int r = 0;
        int index = 0;

        try (Scanner fin = new Scanner(new File("id.txt"))) {
            while (fin.hasNextInt()) {
                id1 = fin.nextInt();
                if (id1 == id) {
                    r = 1;
                    break;
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: id.txt not found.");
        }

        return (r == 0) ? -1 : index;
    }

    // Method to add medical history
    private void addHistory(int index) {
        String addmed;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter Medical Data to be added and separate data with '||' instead of newline:");
            addmed = scanner.nextLine();
        }

        List<String> data = new ArrayList<>();
        try (BufferedReader fin = new BufferedReader(new FileReader("medrec.txt"))) {
            String line;
            while ((line = fin.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading from medrec.txt.");
        }

        // Insert at specific index
        if (index >= 0 && index <= data.size()) {
            data.add(index, addmed);
        }

        try (BufferedWriter fout = new BufferedWriter(new FileWriter("medrec.txt", true))) {
            for (String d : data) {
                fout.write(d + "\n");
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

            // Print the record at the index
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
        try (Scanner scanner = new Scanner(System.in)) {
            int index;
            System.out.println("Welcome to MedPlus Doctor!");
            System.out.println("MENU: \n1. Enter EPIC ID\n2. View Appointments");
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
        int index;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter EPIC ID:");
            int id = scanner.nextInt();
            index = scan(id);

            if (index == -1) {
                System.out.println("ID not found, please enter correct EPIC ID.");
                return scanningProcess();
            }
        }
        return index;
    }

    // Choose between viewing and adding to medical records
    private void choose(int index) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("1. View Medical Records\n2. Add to Medical Records");
            int choice = scanner.nextInt();

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
    }

    // Method to view appointments
    private void appointments() {
        // Implement appointment functionality here
    }
}

public class Main {
    public static void main(String[] args) {
        whatUser();
    }

    // Method to ask user type (Patient or Staff)
    public static void whatUser() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Hello, Welcome to MedPlus\n");
            System.out.println("Are you a Patient or Staff?\nEnter 1 for Patient and 2 for Staff");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    SignIn_LogIn();
                    break;
                case 2:
                    System.out.println("Hello, the Staff site is under repair.");
                    break;
                default:
                    System.out.println("Please enter valid input.");
                    whatUser();
            }
        }
    }

    // SignIn or LogIn menu for patients
    public static void SignIn_LogIn() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome, Let's get you started\nEnter 1 to sign-up and 2 to log-in");
            int c = scanner.nextInt();

            switch (c) {
                case 1:
                    Create_Acc();
                    break;
                case 2:
                    // Implement login functionality here (file handling needed)
                    break;
                default:
                    System.out.println("Enter valid choice.");
                    SignIn_LogIn();
            }
        }
    }

    // Method to create a user account
    public static void Create_Acc() {
        User profile = new User();
        profile.signUp();
    }
}
