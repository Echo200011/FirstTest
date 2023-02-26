package service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import model.Talk;
import org.apache.commons.io.FileUtils;

public class ProcessFileService {

  public List<Talk> processData(File file) {
    return initTalkList(getMessage(file));
  }

  private List<String> getMessage(File file) {
    try {
      return FileUtils.readLines(file, "UTF-8");
    } catch (Exception e) {
      throw new IllegalArgumentException("读取文件失败", e);
    }
  }

  private List<Talk> initTalkList(List<String> message) {
    return message.stream()
        .filter(m -> !m.equals(""))
        .map(Talk::initTalk)
        .collect(Collectors.toList());
  }

}
