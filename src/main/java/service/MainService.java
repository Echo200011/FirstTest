package service;

import model.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class MainService {


  public Cache processingPlan(List<Talk> plans, Time time, Cache cache) {
    cache.setAlready(null);
    cache.getUnfinished().forEach(p -> {
          if (Duration.between(time.getStartTime(), time.getEndTime()).toMinutes() > p.getTimes()) {
            time.setStartTime(time.getStartTime().plusMinutes(p.getTime().getMinute())
                .plusHours(p.getTime().getHour()));
            p.setInvoke(true);
          }
        });

    cache.setAlready(
        cache.getUnfinished().stream().filter(Talk::isInvoke).collect(Collectors.toList()));
    cache.setUnfinished(
        plans.stream().filter(plan -> !plan.isInvoke()).collect(Collectors.toList()));
    return cache;
  }

  public Session processingSession(Cache cache, String timeFrame) {
    return new Session(cache.getAlready(), timeFrame);
  }

  public Track processingTrack(Session morningSession, Session afternoonSession) {
    return new Track(morningSession, afternoonSession);
  }
}
