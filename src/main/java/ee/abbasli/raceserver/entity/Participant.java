package ee.abbasli.raceserver.entity;

public class Participant {

  private long rfid;
  private long startNumber;
  private String firstName;
  private String lastName;

  public Participant(long rfid, long startNumber, String firstName, String lastName) {
    this.rfid = rfid;
    this.startNumber = startNumber;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public long getRfid() {
    return rfid;
  }

  public long getStartNumber() {
    return startNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
