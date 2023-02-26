package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Time {

  private LocalTime startTime;
  private LocalTime endTime;

  public static Time of(int startTime, int endTIme) {
    return (startTime > endTIme || startTime > 60 || endTIme > 60 || startTime == endTIme)
        ? null : new Time(LocalTime.of(startTime, 0), LocalTime.of(endTIme, 0));
  }
}

