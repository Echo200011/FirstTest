package service;

import java.util.Collections;
import model.*;
import java.time.Duration;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

public class ConferenceManagementService {


  public Track processTrack(List<Talk> talkList) {
    if (CollectionUtils.isEmpty(talkList)) {
      return new Track();
    }
    model.Time morningTime = Time.of(9, 12);
    model.Time afternoonTime = Time.of(13, 17);
    List<Talk> morningTalkList = processTalk(talkList, morningTime);
    List<Talk> afternoonTalkList = processTalk(talkList, afternoonTime);
    Session morningSession = processSession(morningTalkList, morningTime);
    Session afternoonSession = processSession(afternoonTalkList, afternoonTime);
    return new Track(morningSession, afternoonSession);
  }

  private List<Talk> processTalk(List<Talk> talkList, Time time) {
    if (ObjectUtils.isEmpty(time)) {
      return Collections.emptyList();
    }
    Cache cache = new Cache(talkList);
    cache.getUnfinished().stream()
        .filter(talk -> isEnoughTime(time, talk))
        .forEach(talk -> setMessage(talk, time));
    return cache.initCache(cache, talkList).getAlready();
  }

  private Session processSession(List<Talk> talkList, Time time) {
    return (ObjectUtils.isEmpty(time)) ? new Session() : new Session(time, talkList);
  }

  private boolean isEnoughTime(Time time, Talk talk) {
    return Duration.between(time.getStartTime(), time.getEndTime()).toMinutes() > talk.getActualTime();
  }

  private void setMessage(Talk talk, Time time) {
    talk.setTalkDetails(time.getStartTime() + " " + talk.getTitle()+" "+talk.getActualTime());
    time.setStartTime(time.computeStartTime(talk.getExecutionTime()));
    talk.setInvoke(true);
  }

}
