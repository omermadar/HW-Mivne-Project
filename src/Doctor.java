public class Doctor {
    private String ID;
    private Queue<Patient> WaitingRoom;

    public Doctor(String ID) {
        this.ID = ID;
        this.WaitingRoom = new Queue<Patient>();
    }

    public int getSize() {
        return this.WaitingRoom.getSize();
    }
    public Queue<Patient> getQueue() {
        return this.WaitingRoom;
    }
    public String getID() {
        return this.ID;
    }

    public void Came_To_Doctor(Patient p){
        this.WaitingRoom.Enqueue(p);
    }

    public Patient Remove_From_WaitingRoom(){
        return this.WaitingRoom.Dequeue();
    }

    public Node<Patient> Last_In_Line(){
        return this.WaitingRoom.Last_In_Line();
    }
}
