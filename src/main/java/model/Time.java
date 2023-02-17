package model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Time {

  private LocalTime startTime;
  private LocalTime endTime;

  public Time(LocalTime startTime, LocalTime endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

}

