package model;

import lombok.Data;

import java.util.List;

@Data
public class Cache {

  private List<Plan> Already;
  private List<Plan> unfinished;

  public Cache(List<Plan> plans) {
    this.unfinished = plans;
  }
}
