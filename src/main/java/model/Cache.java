package model;

import lombok.Data;

import java.util.List;

@Data
public class Cache {

  private List<Talk> already;
  private List<Talk> unfinished;

 public Cache(List<Talk> talkList){
   this.unfinished=talkList;
 }
}
