public class Patient {
    private String ID;
    private Doctor doc;
    private Node<Patient> reference;

    public Patient(String ID, Doctor doc) {
        this.ID = ID;
        this.doc = doc;
        this.reference = null;
    }

    public void Set_Location(Node<Patient> reference){
        this.reference = reference;
    }

    public Doctor getDoctor(){return this.doc;}
    public String getID(){return this.ID;}
    public Node<Patient> getReference(){return this.reference;}
}
