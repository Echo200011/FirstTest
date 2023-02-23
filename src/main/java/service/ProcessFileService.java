package service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Talk;
import org.apache.commons.io.FileUtils;

public class ProcessFileService {

  public List<Talk> processData(String path) {
    List<String> message = getMessage(path);
    return (message == null) ? null : initTalkList(message);
  }


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

  private List<Talk> initTalkList(List<String> message) {
    List<Talk> talkList = new ArrayList<>();
    Pattern pattern = Pattern.compile("[^0-9]");
    message.stream().filter(m -> !m.equals("")).forEach(m -> talkList.add(initTalk(m, pattern)));
    return talkList;
  }
}
