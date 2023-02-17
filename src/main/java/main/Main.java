package main;

import model.*;
import org.apache.commons.io.FileUtils;
import service.MainService;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Main {

  public static void main(String[] args) throws IOException {
       /* String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        List<String> message= new ArrayList<>();
        List<Integer> time = new ArrayList<>();
        File file = new File("D:\\IoTestFile\\Test.txt");
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        Random rand = new Random();
        Collections.shuffle(lines,rand);

        lines.forEach(l-> {
            time.add(Integer.valueOf(p.matcher(l).replaceAll("").trim()));

        });
        time.stream().forEach(System.out::println);*/

    List<Plan> plans = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      Plan plan = new Plan();
      plan.setMessage("第" + i);
      plan.setTimes((int) (Math.random() * 69 + 1));
      plans.add(plan);
    }

    MainService mainService = new MainService();
    Cache cache = new Cache(plans);
    Session morningSession = mainService.processingSession(
        mainService.processingPlan(plans, of(9, 12), cache), "morning");
    Session afternoonSession = mainService.processingSession(
        mainService.processingPlan(plans, of(13, 17), cache), "afternoon");
    Track track = mainService.processingTrack(morningSession, afternoonSession);
    track.getMorningSession().getPlanList().forEach(System.out::println);
    track.getAfternoonSession().getPlanList().forEach(System.out::println);
  }

  public static Time of(int startTime, int endTIme) {
    LocalTime time1 = LocalTime.of(startTime, 0);
    LocalTime time2 = LocalTime.of(endTIme, 0);
    return new Time(time1, time2);
  }
}
