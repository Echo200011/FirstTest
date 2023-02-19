package main;

import java.io.IOException;
import java.util.List;
import model.Track;
import org.junit.jupiter.api.Test;

class MainTest {

  @Test
  void main() throws IOException {
    Main main = new Main();
    List<Track> tracks = main.main("D:\\IoTestFile\\Test.txt",2);
    tracks.forEach(track -> {
      System.out.println(track.getId());
      track.getMorningSession().getTalkList().forEach(talk -> System.out.println(talk.getMessage()));
      track.getAfternoonSession().getTalkList().forEach(talk -> System.out.println(talk.getMessage()));
    });
  }
}