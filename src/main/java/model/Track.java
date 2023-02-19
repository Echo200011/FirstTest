package model;

import lombok.Data;

@Data
public class Track {

  private Integer id;
  private Session morningSession;

  private Session afternoonSession;

  public Track(Session morningSession, Session afternoonSession) {
    this.morningSession = morningSession;
    this.afternoonSession = afternoonSession;
  }

}
