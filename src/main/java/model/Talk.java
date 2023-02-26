package model;

import java.time.Duration;
import lombok.Data;
import java.time.LocalTime;
import org.apache.commons.lang3.StringUtils;

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

  public static Talk initTalk(String message) {
    String minutes = StringUtils.substringAfterLast(message, " ");
    minutes = StringUtils.substringBefore(minutes, "m");
    return (minutes.equals("lightning")) ? new Talk(message, 5) : new Talk(message, Integer.parseInt(minutes));
  }
}
