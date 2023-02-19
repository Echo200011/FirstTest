package util;

import java.time.LocalTime;
import model.Talk;
import model.Time;

public class ProcessTimeUtil {

  public static void processTime(Talk plan, Time time) {
    plan.setTime(time.getStartTime());
    if (plan.getTimes() % 5 == 0) {
      time.setStartTime(time.getStartTime().plusMinutes(plan.getTimes()));
    } else {
      time.setStartTime(time.getStartTime().plusMinutes(plan.getTimes() - plan.getTimes() % 5 + 5));
    }
    plan.setInvoke(true);
  }

  public static Time of(int startTime, int endTIme) {
    LocalTime time1 = LocalTime.of(startTime, 0);
    LocalTime time2 = LocalTime.of(endTIme, 0);
    return new Time(time1, time2);
  }
}