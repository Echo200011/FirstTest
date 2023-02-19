package util;

import java.time.LocalTime;
import model.Time;

public class ProcessTimeUtil {
  public static Time of(int startTime, int endTIme) {
    LocalTime time1 = LocalTime.of(startTime, 0);
    LocalTime time2 = LocalTime.of(endTIme, 0);
    return new Time(time1, time2);
  }
}