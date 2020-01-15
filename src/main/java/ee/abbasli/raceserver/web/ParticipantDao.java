package ee.abbasli.raceserver.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ee.abbasli.raceserver.entity.Participant;

@Component
public class ParticipantDao {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public Optional<Participant> tryFind(long rfid) {
    List<Participant> participants =  jdbcTemplate.query("SELECT rfid, start_number, first_name, last_name FROM participant p WHERE rfid = ? ",
        (rs, num) -> new Participant(rs.getLong("rfid"), rs.getLong("start_number"), rs.getString("first_name"), rs.getString("last_name")),
        rfid);

    if (participants.size() == 1) {
      return Optional.of(participants.get(0));
    }

    return Optional.empty();
  }
}
