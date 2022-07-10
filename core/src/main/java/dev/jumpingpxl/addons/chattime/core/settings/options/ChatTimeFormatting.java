package dev.jumpingpxl.addons.chattime.core.settings.options;

import java.text.SimpleDateFormat;

public class ChatTimeFormatting extends ChatTimeOption<SimpleDateFormat> {

  protected ChatTimeFormatting(String defaultValue) {
    super(defaultValue);
  }

  public static ChatTimeFormatting of(String defaultValue) {
    return new ChatTimeFormatting(defaultValue);
  }

  @Override
  protected SimpleDateFormat updateValue(String rawValue) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(rawValue);
    try {
      dateFormat.format(System.currentTimeMillis());
      return dateFormat;
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}
