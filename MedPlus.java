import java.io.*;
import java.util.Scanner;

class User {
public String name, contact_number, email, password;

// Method to take user input
public void getInput(Scanner scanner) {
System.out.println("Enter Name: ");
name = scanner.nextLine();
System.out.println("Enter Contact Number: ");
contact_number = scanner.nextLine();
System.out.println("Enter Email ID: ");
email = scanner.nextLine();
System.out.println("Enter Password: ");
password = scanner.nextLine();
    }

// Method to save user information to a file
public boolean saveToFile() {
try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
writer.write(name + "," + contact_number + "," + email + "," + password + "\n");
return true;
        } catch (IOException e) {
e.printStackTrace();
return false;
        }
    }

// Method to check if user exists by email
public boolean checkIfUserExists(String emailToCheck) {
try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
String line;
while ((line = reader.readLine()) != null) {
if (line.contains(emailToCheck)) {
return true;
                }
            }
        } catch (IOException e) {
e.printStackTrace();
        }
return false;
    }

// Method to display user information
public void userinfo() {
System.out.println("Current Information:");
System.out.println("1. Name: " + name);
System.out.println("2. Contact Number: " + contact_number);
System.out.println("3. Email: " + email);
System.out.println("4. Password: " + password);
    }
}

class UpdatedUser extends User {
// Method to update user information
public void updateuserinfo(Scanner scanner) {
System.out.println("What would you like to update (1-4)?: ");
int choice = scanner.nextInt();
scanner.nextLine(); // consume newline

System.out.println("Enter new value: ");
String newValue = scanner.nextLine();
updateField(choice, newValue);
    }

// Method to update specific field
public void updateField(int choice, String newValue) {
switch (choice) {
case 1:
name = newValue;
break;
case 2:
contact_number = newValue;
break;
case 3:
email = newValue;
break;
case 4:
password = newValue;
break;
        }
    }
}

class Patient {
private String id;

public String generateUniqueID() {
return "PAT" + System.currentTimeMillis();
    }

public void setID(String id) {
this.id = id;
    }

public void saveIDToFile() {
try (BufferedWriter writer = new BufferedWriter(new FileWriter("patient_ids.txt", true))) {
writer.write(id + "\n");
        } catch (IOException e) {
e.printStackTrace();
        }
    }

public void getID() {
System.out.println("Your ID is: " + id);
    }

public void addRecord(String illness, String date) {
try (BufferedWriter writer = new BufferedWriter(new FileWriter("medical_records.txt", true))) {
writer.write("Illness: " + illness + ", Date: " + date + "\n");
        } catch (IOException e) {
e.printStackTrace();
        }
    }

public void printRecords() {
try (BufferedReader reader = new BufferedReader(new FileReader("medical_records.txt"))) {
String line;
while ((line = reader.readLine()) != null) {
System.out.println(line);
            }
        } catch (IOException e) {
e.printStackTrace();
        }
    }
}

class Doctor {
public void themain() {
System.out.println("Doctor Main Menu");
// Implement doctor-related features here
    }
}

public class MedPlus {

public static void signUp(Scanner scanner) {
User user = new User();
user.getInput(scanner);

if (user.checkIfUserExists(user.email)) {
System.out.println("User already exists with this email.");
return;
        }

if (user.saveToFile()) {
System.out.println("User registered successfully.");
        } else {
System.out.println("Failed to save user data.");
        }
    }

public static boolean loginUser(Scanner scanner) {
System.out.println("Enter Email ID: ");
String email = scanner.nextLine();
System.out.println("Enter Password: ");
String password = scanner.nextLine();

try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
String line;
while ((line = reader.readLine()) != null) {
if (line.contains(email) && line.contains(password)) {
System.out.println("Login successful!");
return true;
                }
            }
        } catch (IOException e) {
e.printStackTrace();
        }
System.out.println("Invalid email or password.");
return false;
    }

public static void userPatient(Scanner scanner) {
Patient patient = new Patient();
int choice;

System.out.println("\t\t\tWelcome! Please Signup or Login");
System.out.println("\t\t\t1: Sign Up");
System.out.println("\t\t\t2: Login");
System.out.println("\t\t\tEnter your choice: ");
choice = scanner.nextInt();
scanner.nextLine(); // consume newline

switch (choice) {
case 1:
signUp(scanner);
break;
case 2:
if (!loginUser(scanner)) {
System.out.println("Invalid credentials, please try again.");
                } else {
System.out.println("Welcome back!");
                }
break;
default:
System.out.println("Invalid choice");
break;
        }

do {
System.out.println("\n\t\t\t::MAIN MENU::");
System.out.println("0. EXIT");
System.out.println("1. Generate Epic ID");
System.out.println("2. View Epic ID");
System.out.println("3. Add Medical Record");
System.out.println("4. View Medical Record");
System.out.println("5. Update Personal Information");
System.out.println("6. Log Out");

choice = scanner.nextInt();
scanner.nextLine(); // consume newline

switch (choice) {
case 0:
System.out.println("Exited Successfully.");
break;
case 1:
String id = patient.generateUniqueID();
patient.setID(id);
patient.saveIDToFile();
System.out.println("Generated ID: " + id);
break;
case 2:
patient.getID();
break;
case 3:
System.out.println("Enter illness: ");
String illness = scanner.nextLine();
System.out.println("Enter date (DD/MM/YYYY): ");
String date = scanner.nextLine();
patient.addRecord(illness, date);
break;
case 4:
patient.printRecords();
break;
case 5:
if (loginUser(scanner)) {
UpdatedUser updatedUser = new UpdatedUser();
updatedUser.updateuserinfo(scanner);
                    }
break;
case 6:
System.out.println("Logged out.");
userPatient(scanner);
break;
default:
System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

public static void userDoctor(Scanner scanner) {
Doctor doctor = new Doctor();
int choice;

System.out.println("\t\t\tWelcome! Please Signup or Login");
System.out.println("\t\t\t1: Sign Up");
System.out.println("\t\t\t2: Login");
System.out.println("\t\t\tEnter your choice: ");
choice = scanner.nextInt();
scanner.nextLine(); // consume newline

switch (choice) {
case 1:
signUp(scanner);
break;
case 2:
if (!loginUser(scanner)) {
System.out.println("Invalid credentials, please try again.");
                } else {
System.out.println("Welcome back!");
                }
break;
default:
System.out.println("Invalid choice");
        }
doctor.themain();
    }

public static void whatUser(Scanner scanner) {
System.out.println("Hello, Welcome to MedPlus");
System.out.println("Are you a Patient or Doctor?");
System.out.println("Enter 1 for Patient and 2 for Doctor");

int choice = scanner.nextInt();
switch (choice) {
case 1:
userPatient(scanner);
break;
case 2:
userDoctor(scanner);
break;
default:
System.out.println("Please enter a valid input.");
whatUser(scanner);
        }
    }

public static void main(String[] args) {
try (Scanner scanner = new Scanner(System.in)) {
whatUser(scanner);
        }
    }
}
