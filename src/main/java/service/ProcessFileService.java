package service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import model.Talk;
import org.apache.commons.io.FileUtils;

public class ProcessFileService {

  public List<Talk> processData(String path) {
    return (getMessage(path) == null) ? null : initTalkList(path);
  }

  @SneakyThrows
  private List<String> getMessage(String path) {
    File file = new File(path);
    try {
      return FileUtils.readLines(file, "UTF-8");
    } catch (Exception e) {
      //用日志插件
      return null;
    }
  }

  private Talk initTalk(String message, Pattern pattern) {
    Matcher matcher = pattern.matcher(message);
    return new Talk(message, Integer.parseInt(matcher.replaceAll("").trim()));
  }

  private List<Talk> initTalkList(String path) {
    List<Talk> talkList = new ArrayList<>();
    Pattern pattern = Pattern.compile("[^0-9]");
    Objects.requireNonNull(getMessage(path)).forEach(m -> talkList.add(initTalk(m, pattern)));
    return talkList;
  }
}
