package model;

import java.time.Duration;
import lombok.Data;
import java.time.LocalTime;
import org.apache.commons.lang3.StringUtils;

@Data
public class Talk {

  private String title;

  private String talkDetails;
  private final LocalTime executionTime;
  private boolean isInvoke;
  private final int actualTime;


  public Talk(String title, int actualTime) {
    this.title = title;
    this.actualTime = actualTime;
    this.executionTime = LocalTime.MIDNIGHT.plus(
        (actualTime % 5 == 0) ? Duration.ofMinutes(actualTime) : Duration.ofMinutes((actualTime - actualTime % 5 + 5)));
  }


  public static Talk initTalk(String conferenceInformation) {
    String title = StringUtils.substringBeforeLast(conferenceInformation, "");
    String minutes = StringUtils.substringAfterLast(conferenceInformation, " ");
    minutes = StringUtils.substringBefore(minutes, "m");
    try {
      int actualTime = minutes.equals("lightning") ? 5 : Integer.parseInt(minutes);
      return new Talk(title, actualTime);
    } catch (Exception e) {
      throw new IllegalArgumentException("Talk初始化错误：时间转换错误", e);
    }
  }
}
