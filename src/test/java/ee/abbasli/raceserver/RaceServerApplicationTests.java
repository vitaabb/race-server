package ee.abbasli.raceserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import ee.abbasli.raceserver.entity.Event;
import ee.abbasli.raceserver.web.EventList;
import ee.abbasli.raceserver.web.EventRequest;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RaceServerApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}

	//@Sql({ "schema.sql", "data.sql" })
	@Test
	public void canCreate() {
		EventRequest requestBody = new EventRequest(1L, 111L, LocalDateTime.of(2020,1,12,1,1,1,0));
		ResponseEntity<String> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/race/event", requestBody, String.class);
		assertEquals(200, responseEntity.getStatusCodeValue());

		EventList response = restTemplate.getForObject("http://localhost:" + port + "/race/events", EventList.class);
		List<Event> events = response.getList();
		assertEquals(events.size(), 1);
		assertTrue(events.stream()
				.filter(event -> event.getReaderId() == 111L)
				.filter(event -> event.getRfid() == 1L)
				.anyMatch(event -> event.getTime().equals(LocalDateTime.of(2020,1,12,1,1,1,0))));
	}

	@Test
	public void canCreateMultiple() {
		EventRequest request1Body = new EventRequest(1L, 111L, LocalDateTime.of(2020,1,12,1,1,1,0));
		ResponseEntity<String> response1Entity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/race/event", request1Body, String.class);
		assertEquals(200, response1Entity.getStatusCodeValue());
		EventRequest request2Body = new EventRequest(1L, 222L, LocalDateTime.of(2020,1,12,10,12,1,0));
		ResponseEntity<String> response2Entity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/race/event", request2Body, String.class);
		assertEquals(200, response2Entity.getStatusCodeValue());

		EventList response = restTemplate.getForObject("http://localhost:" + port + "/race/events", EventList.class);
		List<Event> events = response.getList();
		assertEquals(events.size(), 2);
		assertTrue(events.stream()
				.filter(event -> event.getReaderId() == 111L)
				.filter(event -> event.getRfid() == 1L)
				.anyMatch(event -> event.getTime().equals(LocalDateTime.of(2020,1,12,1,1,1,0))));
		assertTrue(events.stream()
				.filter(event -> event.getReaderId() == 222L)
				.filter(event -> event.getRfid() == 1L)
				.anyMatch(event -> event.getTime().equals(LocalDateTime.of(2020,1,12,10,12,1,0))));

	}

	@Test
	public void canUpdate() {
		EventRequest requestBody = new EventRequest(1L, 111L, LocalDateTime.of(2020,1,12,1,1,1,0));
		ResponseEntity<String> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/race/event", requestBody, String.class);
		assertEquals(200, responseEntity.getStatusCodeValue());

		EventRequest request2Body = new EventRequest(1L, 111L, LocalDateTime.of(2020,1,12,9,10,11,0));
		ResponseEntity<String> response2Entity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/race/event", request2Body, String.class);
		assertEquals(200, response2Entity.getStatusCodeValue());

		EventList response = restTemplate.getForObject("http://localhost:" + port + "/race/events", EventList.class);
		List<Event> events = response.getList();
		assertEquals(events.size(), 1);
		assertTrue(events.stream()
				.filter(event -> event.getReaderId() == 111L)
				.filter(event -> event.getRfid() == 1L)
				.anyMatch(event -> event.getTime().equals(LocalDateTime.of(2020,1,12,9,10,11,0))));
	}

	@Test
	public void failsToCreateIfRfidFromUnknownUser() {
		EventRequest requestBody = new EventRequest(999L, 111L, LocalDateTime.of(2020,1,12,1,1,1,0));
		ResponseEntity<String> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/race/event", requestBody, String.class);
		assertEquals(400, responseEntity.getStatusCodeValue());

		EventList response = restTemplate.getForObject("http://localhost:" + port + "/race/events", EventList.class);
		List<Event> events = response.getList();
		assertTrue(events.isEmpty());
	}

	@AfterEach
	public void cleanup() {
		this.jdbcTemplate.execute("DELETE FROM event");
	}
}
