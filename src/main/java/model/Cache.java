package model;

import lombok.Data;

import java.util.List;

@Data
public class Cache {

  private List<Talk> Already;
  private List<Talk> unfinished;

  public Cache(List<Talk> plans) {
    this.unfinished = plans;
  }
}
