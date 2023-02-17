package model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Plan {

  private String message;
  private LocalTime time;
  private boolean isInvoke;
  private int times;
}
