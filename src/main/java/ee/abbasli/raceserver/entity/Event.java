package ee.abbasli.raceserver.entity;

import java.time.LocalDateTime;

public class Event {

  private long rfid;
  private long readerId;
  private LocalDateTime time;

  public Event(long rfid, long readerId, LocalDateTime time) {
    this.rfid = rfid;
    this.readerId = readerId;
    this.time = time;
  }

  public long getRfid() {
    return rfid;
  }

  public long getReaderId() {
    return readerId;
  }

  public LocalDateTime getTime() {
    return time;
  }
}
