package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Session {

  private boolean isMorning;
  private List<Talk> talkList;

  public Session(Time time, List<Talk> talkList) {
    this.isMorning = time.getEndTime().getHour() == 12;
    this.talkList = talkList;
  }

}
