package ee.abbasli.raceserver.web;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class EventRequest {

  @NotNull
  private Long rfid;

  @NotNull
  private Long readerId;

  @NotNull
  private LocalDateTime time;

  public EventRequest(@NotNull Long rfid, @NotNull Long readerId, @NotNull LocalDateTime time) {
    this.rfid = rfid;
    this.readerId = readerId;
    this.time = time;
  }

  public Long getRfid() {
    return rfid;
  }

  public Long getReaderId() {
    return readerId;
  }

  public LocalDateTime getTime() {
    return time;
  }
}
