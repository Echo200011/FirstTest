package model;

import lombok.Data;

import java.util.List;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cache {

  private List<Talk> already;
  private List<Talk> unfinished;

 public Cache(List<Talk> talkList){
   this.unfinished=talkList;
 }
}
