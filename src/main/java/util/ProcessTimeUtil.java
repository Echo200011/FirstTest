package util;

import model.Plan;
import model.Time;

public class ProcessTimeUtil {

  public static void processTime(Plan plan, Time time) {
    plan.setTime(time.getStartTime());
    if (plan.getTimes() % 5 == 0) {
      time.setStartTime(time.getStartTime().plusMinutes(plan.getTimes()));
    } else {
      time.setStartTime(time.getStartTime().plusMinutes(plan.getTimes() - plan.getTimes() % 5 + 5));
    }
    plan.setInvoke(true);
  }
}
