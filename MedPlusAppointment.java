import java.io.*;
import java.util.Scanner;

class Appointment {
public String patientName;
public int age;
public String phoneNumber;
public String preferredTime;
public String preferredDate;

// Method to book an appointment
public void bookAppointment(Scanner scanner) {
System.out.println("Enter patient's name: ");
patientName = scanner.nextLine();
System.out.println("Enter patient's age: ");
age = scanner.nextInt();
scanner.nextLine(); // consume the newline
System.out.println("Enter patient's phone number: ");
phoneNumber = scanner.nextLine();
System.out.println("Enter preferred time: ");
preferredTime = scanner.nextLine();
System.out.println("Enter preferred date (YYYY-MM-DD): ");
preferredDate = scanner.nextLine();

// Displaying entered information
System.out.println("\nPlease confirm the entered information:");
System.out.println("Patient's Name: " + patientName);
System.out.println("Age: " + age);
System.out.println("Phone Number: " + phoneNumber);
System.out.println("Preferred Time: " + preferredTime);
System.out.println("Preferred Date: " + preferredDate);

// Confirmation
System.out.println("\nIs the entered information correct? (Y/N): ");
char confirm = scanner.next().charAt(0);
scanner.nextLine(); // consume the newline
if (confirm == 'N' || confirm == 'n') {
System.out.println("Please re-enter the information.\n");
bookAppointment(scanner);
        } else {
// Write the appointment details to the file
try (BufferedWriter writer = new BufferedWriter(new FileWriter("appointments.txt", true))) {
writer.write("Patient Name: " + patientName + "\n");
writer.write("Age: " + age + "\n");
writer.write("Phone Number: " + phoneNumber + "\n");
writer.write("Preferred Time: " + preferredTime + "\n");
writer.write("Preferred Date: " + preferredDate + "\n\n");
System.out.println("Appointment booked successfully!");
            } catch (IOException e) {
System.out.println("Unable to open file for writing.");
e.printStackTrace();
            }
        }
    }

// Method to cancel all appointments
public static void cancelAllAppointments(Scanner scanner) {
System.out.println("Are you sure you want to cancel all appointments? (yes/no): ");
String confirmation = scanner.nextLine();

if (confirmation.equalsIgnoreCase("yes")) {
try (BufferedWriter writer = new BufferedWriter(new FileWriter("appointments.txt"))) {
// Truncate the file (clear all contents)
writer.write("");
System.out.println("All appointments canceled successfully!");
            } catch (IOException e) {
System.out.println("Unable to open file.");
e.printStackTrace();
            }
        } else {
System.out.println("Operation canceled. No appointments were deleted.");
        }
    }

// Method to display all appointments to the doctor
public static void displayToDoctor() {
try (BufferedReader reader = new BufferedReader(new FileReader("appointments.txt"))) {
String line;
while ((line = reader.readLine()) != null) {
System.out.println(line);
            }
        } catch (IOException e) {
System.out.println("Unable to open file.");
e.printStackTrace();
        }
    }
}

public class MedPlusAppointment {
public static void main(String[] args) {
Appointment appointment = new Appointment();
try (Scanner scanner = new Scanner(System.in)) {
int choice;

do {
System.out.println("\n--- Appointment Menu ---");
System.out.println("1. Book Appointment");
System.out.println("2. Cancel All Appointments");
System.out.println("3. Display Appointments to Doctor");
System.out.println("0. Exit");
System.out.print("Enter your choice: ");
choice = scanner.nextInt();
scanner.nextLine(); // consume the newline

switch (choice) {
case 1:
appointment.bookAppointment(scanner);
break;
case 2:
Appointment.cancelAllAppointments(scanner);
break;
case 3:
Appointment.displayToDoctor();
break;
case 0:
System.out.println("Exiting...");
break;
default:
System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    } // Scanner will be closed here automatically
    }
}
