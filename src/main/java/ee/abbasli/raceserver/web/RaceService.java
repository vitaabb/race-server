package ee.abbasli.raceserver.web;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RaceService {

  @Autowired
  EventDao eventDao;

  @Autowired
  ParticipantDao participantDao;

  public void createOrUpdate(long rfid, long readerId, LocalDateTime time) {
    validate(rfid, readerId, time);
    if (eventDao.exists(rfid, readerId)) {
      eventDao.update(rfid, readerId, time);
    }
    else {
      eventDao.create(rfid, readerId, time);
    }
  }

  public void validate(long rfid, long readerId, LocalDateTime time) {
    participantDao.tryFind(rfid)
        .orElseThrow(IllegalArgumentException::new);
  }
}
