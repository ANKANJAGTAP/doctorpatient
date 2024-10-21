public class tempCodeRunnerFile {
    public static void main(String[] args) {
        Patient patient = new Patient(); // Create a Patient object (singular)

        // Example usage of the Patient methods
        patient.setID(patient.generateUniqueID()); // Set and generate a unique ID for the patient
        patient.getID();  // Display the patient's unique ID
        patient.addRecord("Cold", "20-10-2024"); // Add a medical record for the patient
        patient.printRecords(); // Print the patient's medical records

        // Appointments appointment = new Appointments(); // Uncomment if using Appointments class
        // appointment.bookAppointment(); // Call appointment-related methods
    }
}
