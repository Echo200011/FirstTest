package service;

import java.io.File;
import java.util.List;
import model.Session;
import model.Talk;
import model.Time;
import model.Track;
import util.ProcessTimeUtil;

public class OutputTrackListService {

  private final ProcessFileService processFileService = new ProcessFileService();
  private final ConferenceManagementService conferenceManagementService = new ConferenceManagementService();

  public Track outputTrackList(File file){
    List<Talk> talkList = processFileService.processData(file);
    Time morningTime = ProcessTimeUtil.of(9,12);
    Time afternoonTime = ProcessTimeUtil.of(13,17);
    List<Talk> morningTalkList =  conferenceManagementService.processTalk(talkList,morningTime );
    List<Talk> afternoonTalkList =  conferenceManagementService.processTalk(talkList,afternoonTime);
    Session morningSession = conferenceManagementService.processSession(morningTalkList,morningTime);
    Session afternoonSession = conferenceManagementService.processSession(afternoonTalkList,afternoonTime);
    return conferenceManagementService.processTrack(morningSession,afternoonSession);
  }

}
