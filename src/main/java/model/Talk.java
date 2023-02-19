package model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Talk {
  private String message;
  private LocalTime time;
  private boolean isInvoke;
  private int times;
}
