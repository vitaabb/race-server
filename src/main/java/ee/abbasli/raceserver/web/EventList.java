package ee.abbasli.raceserver.web;

import java.util.ArrayList;
import java.util.List;

import ee.abbasli.raceserver.entity.Event;

public class EventList {
  private List<Event> list;

  public EventList() {
    list = new ArrayList<>();
  }

  public EventList(List<Event> events) {
    this.list = events;
  }

  public List<Event> getList() {
    return list;
  }
}
