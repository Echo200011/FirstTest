package service;

import java.time.LocalTime;
import jdk.nashorn.internal.runtime.ECMAException;
import model.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ConferenceManagementService {

  public Cache processTalk(List<Talk> talks, Time time, Cache cache) {
    try {
      cache.getUnfinished().stream()
          .filter(talk -> compareRemainingTime(time, talk))
          .forEach(talk -> setMessage(talk, time));
      return initCache(cache, talks);
    } catch (NullPointerException exception) {
      //打印日志
      return null;
    }
  }

  public Session processSession(Cache cache, Time time) {
    try {
      return (cache.getUnfinished().size() == 0 && cache.getAlready().size()==0)
          ? null : (time.getEndTime().getHour() == 12)
          ? new Session(true, cache.getAlready()) : new Session(false, cache.getAlready());
    } catch (NullPointerException exception) {
      //日志打印
      return null;
    }
  }

  public Track processTrack(Session morningSession, Session afternoonSession) {
    try {
      return (morningSession.getTalkList().size() == 0)
          ? null : (morningSession.isMorning())
          ? new Track(morningSession, afternoonSession) : new Track(afternoonSession, morningSession);
    } catch (NullPointerException exception) {
      //打印日志
      return null;
    }
  }

  private boolean compareRemainingTime(Time time, Talk talk) {
    return Duration.between(time.getStartTime(), time.getEndTime()).toMinutes() > talk.getTimes();
  }

  private void setMessage(Talk talk, Time time) {
    talk.setMessage(time.getStartTime() + " " + talk.getMessage());
    time.setStartTime(computeStartTime(talk.getTime(),time.getStartTime()));
    talk.setInvoke(true);
  }

  private Cache initCache(Cache cache, List<Talk> talks) {
    cache.setAlready(cache.getUnfinished().stream().filter(Talk::isInvoke).collect(Collectors.toList()));
    cache.setUnfinished(talks.stream().filter(talk -> !talk.isInvoke()).collect(Collectors.toList()));
    return cache;
  }

  private LocalTime computeStartTime(LocalTime talkTime,LocalTime startTime){
   return startTime.plusMinutes(talkTime.getMinute()).plusHours(talkTime.getHour());
  }

}
