package model;

import java.util.stream.Collectors;
import lombok.Data;

import java.util.List;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cache {

  private List<Talk> already;
  private List<Talk> unfinished;

  public Cache(List<Talk> talkList) {
    this.unfinished = talkListNotInvoke(talkList);
    this.already = talkListIsInvoke(this);
  }

  public Cache initCache(Cache cache, List<Talk> talkList) {
    this.already = talkListIsInvoke(this);
    this.unfinished = talkListNotInvoke(talkList);
    return cache;
  }

  private List<Talk> talkListIsInvoke(Cache cache) {
    return cache.getUnfinished().stream().filter(Talk::isInvoke).collect(Collectors.toList());
  }

  private List<Talk> talkListNotInvoke(List<Talk> talkList) {
    return talkList.stream().filter(talk -> !talk.isInvoke()).collect(Collectors.toList());
  }
}
