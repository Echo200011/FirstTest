package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Time {

  private LocalTime startTime;
  private LocalTime endTime;
}

