public class ClinicManager {
    public static final String MIN_ID = "";
    public static final String MAX_ID = "\uFFFF\uFFFF\uFFFF\uFFFF";

    private Two_Three_Tree<String> Patients;
    private Two_Three_Tree<String> Doctors;
    private Two_Three_Tree_Multiple<Integer> Amount_Of_Patients;


    public ClinicManager() {
        this.Patients = new Two_Three_Tree<String>(MIN_ID, MAX_ID);
        this.Doctors = new Two_Three_Tree<String>(MIN_ID, MAX_ID);
        this.Amount_Of_Patients = new Two_Three_Tree_Multiple<Integer>(-1, Integer.MAX_VALUE);
    }

    public void doctorEnter(String doctorId) {
        Doctor new_doc = new Doctor(doctorId);
        // If already in the tree
        if(this.Doctors.Search(this.Doctors.getRoot(), doctorId) != null) throw new IllegalArgumentException();

        TT_Node<String> new_node = new TT_Node<String>(null, null, null, null, doctorId, new_doc);
        this.Doctors.Insert(this.Doctors, new_node);

        // Keep track of amount of patients
        TT_Node<Integer> temp = new TT_Node<Integer>(null, null, null,
                null, 0, 0, 1);
        this.Amount_Of_Patients.Insert(this.Amount_Of_Patients, temp);
    }

    public void doctorLeave(String doctorId) {
        TT_Node<String> found = this.Doctors.Search(this.Doctors.getRoot(), doctorId);
        if(found == null) throw new IllegalArgumentException();
        Doctor found_doc = (Doctor)found.getPointer();
        if(found_doc.getSize() != 0) throw new IllegalArgumentException();

        this.Doctors.Delete(this.Doctors, found);
        // decrease 1 of the 0's in the tree
        TT_Node<Integer> temp_node = this.Amount_Of_Patients.Search(this.Amount_Of_Patients.getRoot(), 0);
        this.Amount_Of_Patients.Delete(this.Amount_Of_Patients, temp_node);
    }

    public void patientEnter(String doctorId, String patientId) {
        // check that patient does not exist yet and doctor exists
        TT_Node<String> found = this.Doctors.Search(this.Doctors.getRoot(), doctorId);
        if(found == null) throw new IllegalArgumentException();
        Doctor found_doc = (Doctor)found.getPointer();
        if(this.Patients.Search(this.Patients.getRoot(), patientId) != null) throw new IllegalArgumentException();

        Patient newPatient = new Patient(patientId, found_doc); // create patient
        TT_Node<String> NodeNewPatient =
                new TT_Node<String>(null,null,null,null,patientId,newPatient);
        this.Patients.Insert(this.Patients, NodeNewPatient);
        int doctor_patients_inline = found_doc.getSize();
        found_doc.Came_To_Doctor(newPatient); // get into queue
        newPatient.Set_Location(found_doc.Last_In_Line()); // location in doc's line (refference pointer)

        //update amounts
        TT_Node<Integer> decrease_node =
                this.Amount_Of_Patients.Search(this.Amount_Of_Patients.getRoot(), doctor_patients_inline);
        TT_Node<Integer> increase_node =
                new TT_Node<Integer>(null,null,null,null,
                        doctor_patients_inline+1,doctor_patients_inline+1,1);
        this.Amount_Of_Patients.Delete(this.Amount_Of_Patients, decrease_node);
        this.Amount_Of_Patients.Insert(this.Amount_Of_Patients, increase_node);
    }

    public String nextPatientLeave(String doctorId) {
        TT_Node<String> found = this.Doctors.Search(this.Doctors.getRoot(), doctorId);
        if(found == null) throw new IllegalArgumentException(); // if doc does not exist
        Doctor found_doc = (Doctor)found.getPointer();
        if(found_doc.getSize() == 0) throw new IllegalArgumentException(); // line empty of doc

        int doctor_patients_inline = found_doc.getSize();
        Patient removed = found_doc.Remove_From_WaitingRoom();

        //update amounts, it's called decrease but we remove from the last amount of people we had in line
        TT_Node<Integer> decrease_node =
                this.Amount_Of_Patients.Search(this.Amount_Of_Patients.getRoot(), doctor_patients_inline);
        TT_Node<Integer> increase_node =
                new TT_Node<Integer>(null,null,null,null,
                        doctor_patients_inline-1,doctor_patients_inline-1,1);
        this.Amount_Of_Patients.Delete(this.Amount_Of_Patients, decrease_node);
        this.Amount_Of_Patients.Insert(this.Amount_Of_Patients, increase_node);

        // remove patient from tree
        TT_Node<String> temp = this.Patients.Search(this.Patients.getRoot(), removed.getID());
        this.Patients.Delete(this.Patients, temp);

        return removed.getID();
    }

    public void patientLeaveEarly(String patientId) {
        TT_Node<String> removed = this.Patients.Search(this.Patients.getRoot(), patientId);
        if(removed == null) throw new IllegalArgumentException();

        Patient p = (Patient)removed.getPointer(); // get pointer to patient
        this.Patients.Delete(this.Patients, removed); // remove from Patients tree
        Doctor d = p.getDoctor(); // get patient's doctor

        int doctor_patients_inline = d.getSize();

        d.getQueue().Remove(p.getReference()); // remove the patient from the line

        //update amounts, it's called decrease but we remove from the last amount of people we had in line
        TT_Node<Integer> decrease_node =
                this.Amount_Of_Patients.Search(this.Amount_Of_Patients.getRoot(), doctor_patients_inline);
        TT_Node<Integer> increase_node =
                new TT_Node<Integer>(null,null,null,null,
                        doctor_patients_inline-1,doctor_patients_inline-1,1);
        this.Amount_Of_Patients.Delete(this.Amount_Of_Patients, decrease_node);
        this.Amount_Of_Patients.Insert(this.Amount_Of_Patients, increase_node);


    }

    public int numPatients(String doctorId) {
        TT_Node<String> found = this.Doctors.Search(this.Doctors.getRoot(), doctorId);
        if(found == null) throw new IllegalArgumentException(); // if doc does not exist

        Doctor d = (Doctor)found.getPointer();
        return d.getQueue().getSize();
    }

    public String nextPatient(String doctorId) {
        TT_Node<String> found = this.Doctors.Search(this.Doctors.getRoot(), doctorId);
        if(found == null) throw new IllegalArgumentException(); // if doc does not exist

        Doctor d = (Doctor)found.getPointer();
        if(d.getSize() == 0) throw new IllegalArgumentException(); // empty queue
        Node<Patient> temp = d.getQueue().First_In_Line();

        Patient p = temp.Get_Data(); // from node take the actual patient object
        return p.getID();
    }

    public String waitingForDoctor(String patientId) {
        TT_Node<String> patient = this.Patients.Search(this.Patients.getRoot(), patientId);
        if(patient == null) throw new IllegalArgumentException();

        Patient p = (Patient) patient.getPointer(); // take patient from the node pointer
        return p.getDoctor().getID();
    }

    public int numDoctorsWithLoadInRange(int low, int high) {
        // minus 1 to get what is left from the low integer
        int count_low = this.Amount_Of_Patients.Count_Search(this.Amount_Of_Patients.getRoot(), low - 1, 0);
        int count_high = this.Amount_Of_Patients.Count_Search(this.Amount_Of_Patients.getRoot(), high, 0);

        return count_high - count_low;
    }

    public int averageLoadWithinRange(int low, int high) {
        // minus 1 to get what is left from the low integer
        int count_low = this.Amount_Of_Patients.Count_Search(this.Amount_Of_Patients.getRoot(), low - 1, 0);
        int count_high = this.Amount_Of_Patients.Count_Search(this.Amount_Of_Patients.getRoot(), high, 0);

        int size_low = this.Amount_Of_Patients.Size_Search(this.Amount_Of_Patients.getRoot(), low - 1, 0);
        int size_high = this.Amount_Of_Patients.Size_Search(this.Amount_Of_Patients.getRoot(), high, 0);

        return (size_high - size_low) / (count_high - count_low);
    }
}