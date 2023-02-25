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
    if (CollectionUtils.isEmpty(talkList)||ObjectUtils.isEmpty(time)) {
      return new ArrayList<>();
    }
    Cache cache = new Cache(talkListNotInvoke(talkList));
    cache.getUnfinished().stream()
        .filter(talk -> isEnoughTime(time, talk))
        .forEach(talk -> setMessage(talk, time));
    return initCache(cache, talkList).getAlready();
  }

  public Session processSession(List<Talk> talkList, Time time) {
    if (CollectionUtils.isEmpty(talkList)|| ObjectUtils.isEmpty(time)) {
      return new Session(false, Collections.emptyList());
    }
    return new Session(time, talkList);
  }

  public Track processTrack(Session morningSession, Session afternoonSession) {
    if (ObjectUtils.isEmpty(morningSession) || ObjectUtils.isEmpty(afternoonSession)) {
      return new Track(new Session(false, new ArrayList<>()), new Session(false, new ArrayList<>()));
    }
    return new Track(morningSession, afternoonSession);
  }

  private boolean isEnoughTime(Time time, Talk talk) {
    return Duration.between(time.getStartTime(), time.getEndTime()).toMinutes() > talk.getTimes();
  }

  private void setMessage(Talk talk, Time time) {
    talk.setMessage(time.getStartTime() + " " + talk.getMessage());
    time.setStartTime(computeStartTime(talk.getTime(), time.getStartTime()));
    talk.setInvoke(true);
  }

  private Cache initCache(Cache cache, List<Talk> talkList) {
    cache.setAlready(talkListIsInvoke(cache));
    cache.setUnfinished(talkListNotInvoke(talkList));
    return cache;
  }

  private LocalTime computeStartTime(LocalTime talkTime, LocalTime startTime) {
    return startTime.plusMinutes(talkTime.getMinute()).plusHours(talkTime.getHour());
  }

  private List<Talk> talkListIsInvoke(Cache cache) {
    return cache.getUnfinished().stream().filter(Talk::isInvoke).collect(Collectors.toList());
  }

  private List<Talk> talkListNotInvoke(List<Talk> talkList) {
    return talkList.stream().filter(talk -> !talk.isInvoke()).collect(Collectors.toList());
  }

}
