package main;

import java.util.ArrayList;
import java.util.List;
import model.Talk;
import org.junit.jupiter.api.Test;

class MainTest {

  @Test
  void main() {
    List<Talk> plans =new ArrayList<>();
    plans.stream().forEach(plan -> plan.setMessage("sdasdsadasdsad"));
    for (int i = 0; i < 20; i++) {
      Talk plan =new Talk();
      plan.setTimes((int)(Math.random()*59+1));
      plans.add(plan);
    }
    Main main =new Main();
    main.main(plans);
  }

  @Test
  void of() {
  }
}