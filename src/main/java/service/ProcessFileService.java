package service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import model.Talk;
import model.Track;
import org.apache.commons.io.FileUtils;

public class ProcessFileService {

  private final ConferenceManagementService service = new ConferenceManagementService();

  public Track processData(File file) {
    return service.processTrack(initTalkList(getConferenceInformation(file)));
  }

  private List<String> getConferenceInformation(File file) {
    try {
      return FileUtils.readLines(file, "UTF-8");
    } catch (Exception e) {
      throw new IllegalArgumentException("读取文件失败", e);
    }
  }

  private List<Talk> initTalkList(List<String> conferenceInformation) {
    return conferenceInformation.stream()
        .filter(m -> !m.equals(""))
        .map(Talk::initTalk)
        .collect(Collectors.toList());
  }

}
