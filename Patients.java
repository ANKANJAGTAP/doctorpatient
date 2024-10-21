import java.io.*;
import java.util.*;

class Patient {
    private static class MedicalRecord {
        String illness;
        String date;

        MedicalRecord(String illness, String date) {
            this.illness = illness;
            this.date = date;
        }
    }

    private List<MedicalRecord> records = new ArrayList<>();  // To store multiple medical records
    private String uniqueID;

    // Method to set unique ID
    public void setID(String id) {
        this.uniqueID = id;
    }

    // Method to get the unique ID
    public void getID() {
        if (uniqueID == null || uniqueID.isEmpty()) {
            System.out.println("\n\t\t\tUnique ID is not generated\n");
        } else {
            System.out.println("Your Unique ID is: " + uniqueID);
        }
    }

    // Method to generate a 6-digit unique ID
    public String generateUniqueID() {
        StringBuilder id = new StringBuilder();
        Random random = new Random(); // Correctly initialize random here
        for (int i = 0; i < 6; ++i) {
            id.append(random.nextInt(10));  // Append random digits (0-9)
        }
        return id.toString();
    }

    // Method to save unique ID to a file
    public void saveIDToFile() {
        try (BufferedWriter file = new BufferedWriter(new FileWriter("uniqueid.txt"))) {
            file.write(uniqueID);
            System.out.println("Unique ID saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to open file.");
        }
    }

    // Method to add a medical record
    public void addRecord(String illness, String date) {
        records.add(new MedicalRecord(illness, date));
    }

    // Method to print all medical records
    public void printRecords() {
        for (MedicalRecord record : records) {
            System.out.println("Illness: " + record.illness + ", Date: " + record.date);
        }
    }

    // Method to save medical records to a file
    public void saveRecordsToFile(String filename) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter(filename))) {
            for (MedicalRecord record : records) {
                file.write("Illness: " + record.illness + ", Date: " + record.date + "\n");
            }
            System.out.println("Records saved successfully.");
        } catch (IOException e) {
            System.out.println("Unable to open file for writing.");
        }
    }

    // Method to collect medical records interactively (you can call this from main)
    public void collectRecord() {
        try (Scanner scanner = new Scanner(System.in)) { // Ensure scanner is closed after use
            System.out.println("Enter illness: ");
            String illness = scanner.nextLine();
            System.out.println("Enter date (e.g., DD-MM-YYYY): ");
            String date = scanner.nextLine();

            addRecord(illness, date);
        }
    }
}

public class Patients {
    public static void main(String[] args) {
        Patient patient = new Patient();
        // Generate and set a unique ID
        String generatedID = patient.generateUniqueID();
        patient.setID(generatedID);
        patient.getID();  // Show the generated ID

        // Save unique ID to file
        patient.saveIDToFile();

        // Add medical records
        patient.addRecord("Flu", "12-10-2023");
        patient.addRecord("Fever", "15-10-2023");

        // Print and save medical records to a file
        patient.printRecords();
        patient.saveRecordsToFile("medical_records.txt");
    }
}
