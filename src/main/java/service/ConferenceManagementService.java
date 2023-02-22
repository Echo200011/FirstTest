package service;

import model.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ConferenceManagementService {

  public Cache processTalk(List<Talk> talks, Time time, Cache cache) {
    if (talks==null || time==null || cache.getUnfinished().size()==0)
      return null;
    cache.getUnfinished().stream().filter(talk ->  compareRemainingTime(time, talk)).forEach(talk -> setMessage(talk, time));
    return initCache(cache, talks);
  }

  public Session processSession(Cache cache) {
    return new Session(cache.getAlready());
  }

  public Track processTrack(Session morningSession, Session afternoonSession) {
    return new Track(morningSession, afternoonSession);
  }

  private boolean compareRemainingTime(Time time, Talk talk) {
    return Duration.between(time.getStartTime(), time.getEndTime()).toMinutes() > talk.getTimes();
  }

  private void setMessage(Talk talk, Time time) {
    talk.setMessage(time.getStartTime() + " " + talk.getMessage());
    time.setStartTime(time.getStartTime().plusMinutes(talk.getTime().getMinute()).plusHours(talk.getTime().getHour()));
    talk.setInvoke(true);
  }

  private Cache initCache(Cache cache, List<Talk> talks) {
    cache.setAlready(cache.getUnfinished().stream().filter(Talk::isInvoke).collect(Collectors.toList()));
    cache.setUnfinished(talks.stream().filter(talk -> !talk.isInvoke()).collect(Collectors.toList()));
    return cache;
  }

}
