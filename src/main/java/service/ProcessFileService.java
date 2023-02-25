package service;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import model.Talk;
import org.apache.commons.io.FileUtils;

public class ProcessFileService {

  public List<Talk> processData(File file) {
    List<String> message = getMessage(file);
    return initTalkList(message);
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
        .map(m -> initTalk(m, Pattern.compile("[^0-9]")))
        .collect(Collectors.toList());
  }

  private Talk initTalk(String message, Pattern pattern) {
    String[] tokens = message.split(" ");
    Matcher matcher = pattern.matcher(tokens[tokens.length - 1]);
    return new Talk(message, Integer.parseInt(matcher.replaceAll("").trim()));
  }

}
