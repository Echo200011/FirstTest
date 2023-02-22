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
    this.message = message;
    this.times = times;
    this.time = LocalTime.MIDNIGHT.plus((times % 5 == 0) ? Duration.ofMinutes(times) : Duration.ofMinutes((times - times % 5 + 5)));
  }
}
