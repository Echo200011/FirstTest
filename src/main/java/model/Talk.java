package model;

import java.time.Duration;
import lombok.Data;

import java.time.LocalTime;

@Data
public class Talk {

  private String message;
  private LocalTime time;
  private boolean isInvoke;
  private int times;

  public Talk(String message, int times) {
    Duration duration;
    this.message = message;
    this.times = times;
    if (times % 5 == 0) {
      duration = Duration.ofMinutes(times);
    } else {
      duration = Duration.ofMinutes((times - times % 5 + 5));
    }
    this.time = LocalTime.MIDNIGHT.plus(duration);
  }
}
