package main;

import static util.ProcessTimeUtil.of;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import model.*;
import service.MainService;
import java.util.List;
import service.ReceiveService;

public class Main {

  public List<Track> main(String path, int num) throws IOException {
    MainService mainService = new MainService();
    ReceiveService receiveService = new ReceiveService();
    List<Track> tracks = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      List<Talk> talkList = receiveService.processData(path);
      Cache cache = new Cache(talkList);
      Collections.shuffle(talkList, new Random());
      Session morningSession = mainService.processingSession(
          mainService.processingPlan(talkList, of(9, 12), cache), "morning");
      Session afternoonSession = mainService.processingSession(
          mainService.processingPlan(talkList, of(13, 17), cache), "afternoon");
      Track track = mainService.processingTrack(morningSession, afternoonSession);
      track.setId(i);
      tracks.add(track);
    }
    return tracks;
  }

}
