package util;

import java.time.LocalTime;
import model.Time;

public class ProcessTimeUtil {

  public static Time of(int startTime, int endTIme) {
    return (startTime > endTIme || startTime > 60 || endTIme > 60 || startTime == endTIme)
        ? null : new Time(LocalTime.of(startTime, 0), LocalTime.of(endTIme, 0));
  }
}