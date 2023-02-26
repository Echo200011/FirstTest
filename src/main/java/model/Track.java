package model;

import lombok.Data;

@Data
public class Track {

  private Session morningSession;

  private Session afternoonSession;

  public Track(Session morningSession, Session afternoonSession) {
    this.morningSession = morningSession;
    this.afternoonSession = afternoonSession;
  }

  public Track() {
    this.morningSession = new Session();
    this.afternoonSession = new Session();
  }

}
