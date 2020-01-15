package ee.abbasli.raceserver.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ee.abbasli.raceserver.entity.Event;

@Component
public class EventDao {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public List<Event> search() {
    return jdbcTemplate.query("SELECT rfid, reader_id, time FROM event e JOIN participant p on e.participant_id = p.id ",
        (rs, num) -> new Event(rs.getLong("rfid"), rs.getLong("reader_id"), rs.getObject("time", LocalDateTime.class)));
  }

  public boolean exists(long rfid, long readerId) {
    return jdbcTemplate.queryForObject("SELECT EXISTS " +
        "(SELECT 1 FROM event " +
        "WHERE reader_id = ? AND participant_id IN " +
        "(SELECT id FROM participant p WHERE p.rfid = ?))", Boolean.class, readerId, rfid);
  }

  public void create(long rfid, long readerId, LocalDateTime time) {
    jdbcTemplate.update("INSERT INTO event (participant_id, reader_id, \"time\") VALUES (" +
        "(SELECT id FROM participant p WHERE p.rfid = ?), ?, ?)",
        rfid, readerId, time);
  }

  public void update(long rfid, long readerId, LocalDateTime time) {
    jdbcTemplate.update("UPDATE event SET \"time\" = ? " +
            "WHERE reader_id = ? AND participant_id IN " +
            "(SELECT id FROM participant p WHERE p.rfid = ?)",
        time, readerId, rfid);
  }
}
