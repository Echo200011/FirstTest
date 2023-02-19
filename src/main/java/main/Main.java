package main;

import static util.ProcessTimeUtil.of;

import model.*;
import service.MainService;
import java.util.List;

public class Main {

  public void main(List<Talk> plans) {
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

}
