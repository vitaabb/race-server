package ee.abbasli.raceserver.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("race")
public class RaceController {

  @Autowired
  private RaceService service;

  @Autowired
  private EventDao dao;

  @GetMapping("events")
  public EventList search() {
    return new EventList(dao.search());
  }

  @ResponseBody
  @PostMapping("event")
  public void save(@Valid @RequestBody EventRequest request) {
    service.createOrUpdate(request.getRfid(), request.getReaderId(), request.getTime());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody String handleException(IllegalArgumentException e) {
    return "Invalid inputs";
  }
}
