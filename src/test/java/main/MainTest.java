package main;

import java.util.ArrayList;
import java.util.List;
import model.Talk;
import org.junit.jupiter.api.Test;

class MainTest {

  @Test
  void main() {
    List<Talk> plans =new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      Talk plan =new Talk("dsasd"+i,(int)(Math.random()*59+1));
      plans.add(plan);
    }
    Main main =new Main();
    main.main(plans);
  }

  @Test
  void of() {
  }
}