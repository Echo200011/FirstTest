package service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import model.*;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

public class ConferenceManagementService {


  public List<Talk> processTalk(List<Talk> talkList, Time time) {
    if (CollectionUtils.isEmpty(talkList) || ObjectUtils.isEmpty(time)) {
      return new ArrayList<>();
    }
    Cache cache = new Cache(talkList);
    cache.getUnfinished().stream()
        .filter(talk -> isEnoughTime(time, talk))
        .forEach(talk -> setMessage(talk, time));
    return cache.initCache(cache, talkList).getAlready();
  }

  public Session processSession(List<Talk> talkList, Time time) {
    if (CollectionUtils.isEmpty(talkList) || ObjectUtils.isEmpty(time)) {
      return new Session();
    }
    return new Session(time, talkList);
  }

  public Track processTrack(Session morningSession, Session afternoonSession) {
    if (ObjectUtils.isEmpty(morningSession) || ObjectUtils.isEmpty(afternoonSession)) {
      return new Track();
    }
    return new Track(morningSession, afternoonSession);
  }

  private boolean isEnoughTime(Time time, Talk talk) {
    return Duration.between(time.getStartTime(), time.getEndTime()).toMinutes() > talk.getTimes();
  }

  private void setMessage(Talk talk, Time time) {
    talk.setMessage(time.getStartTime() + " " + talk.getMessage());
    time.setStartTime(time.computeStartTime(talk.getTime()));
    talk.setInvoke(true);
  }

}
