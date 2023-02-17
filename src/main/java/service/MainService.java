package service;

import model.*;
import util.ProcessTimeUtil;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class MainService {


  public Cache processingPlan(List<Plan> plans, Time time, Cache cache) {
    cache.setAlready(null);
    cache.getUnfinished()
        .stream()
        .filter(plan -> Duration.between(time.getStartTime(), time.getEndTime()).toMinutes()
            > plan.getTimes())
        .forEach(plan -> ProcessTimeUtil.processTime(plan, time));
    cache.setAlready(
        cache.getUnfinished().stream().filter(Plan::isInvoke).collect(Collectors.toList()));
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
