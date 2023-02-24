package service;

import java.time.LocalTime;
import java.util.ArrayList;
import model.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ConferenceManagementService {


  public List<Talk> processTalk(List<Talk> talkList, Time time) {
    if (talkList == null || talkList.size() == 0 || time == null || time.getStartTime() == null || time.getEndTime() == null) {
      return new ArrayList<>();
    }
    Cache cache = new Cache(talkListalkListNotInvoke(talkList));
    cache.getUnfinished().stream()
        .filter(talk -> needTimeCompare(time, talk))
        .forEach(talk -> setMessage(talk, time));
    return initCache(cache, talkList).getAlready();
  }

  public Session processSession(List<Talk> talkList, Time time) {
    if (talkList == null || talkList.size() == 0 || time == null || time.getStartTime() == null || time.getEndTime() == null) {
      return new Session(false, new ArrayList<>());
    }
    return (time.getEndTime().getHour() == 12) ? new Session(true, talkList) : new Session(false, talkList);
  }

  public Track processTrack(Session morningSession, Session afternoonSession) {
    if (morningSession == null || afternoonSession == null) {
      return new Track(new Session(false, new ArrayList<>()), new Session(false, new ArrayList<>()));
    }
    return (afternoonSession.getTalkList().size() == 0)
        ? new Track(morningSession, new Session(false, new ArrayList<>())) : (morningSession.isMorning())
        ? new Track(morningSession, afternoonSession) : new Track(afternoonSession, morningSession);
  }

  private boolean needTimeCompare(Time time, Talk talk) {
    return Duration.between(time.getStartTime(), time.getEndTime()).toMinutes() > talk.getTimes();
  }

  private void setMessage(Talk talk, Time time) {
    talk.setMessage(time.getStartTime() + " " + talk.getMessage());
    time.setStartTime(computeStartTime(talk.getTime(), time.getStartTime()));
    talk.setInvoke(true);
  }

  private Cache initCache(Cache cache, List<Talk> talkList) {
    cache.setAlready(talkListalkListisInvoke(cache));
    cache.setUnfinished(talkListalkListNotInvoke(talkList));
    return cache;
  }

  private LocalTime computeStartTime(LocalTime talkTime, LocalTime startTime) {
    return startTime.plusMinutes(talkTime.getMinute()).plusHours(talkTime.getHour());
  }

  private List<Talk> talkListalkListisInvoke(Cache cache) {
    return cache.getUnfinished().stream().filter(Talk::isInvoke).collect(Collectors.toList());
  }

  private List<Talk> talkListalkListNotInvoke(List<Talk> talkList) {
    return talkList.stream().filter(talk -> !talk.isInvoke()).collect(Collectors.toList());
  }

}
