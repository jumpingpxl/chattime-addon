package dev.jumpingpxl.addons.chattime.core.listener;

import dev.jumpingpxl.addons.chattime.core.ChatTime;
import dev.jumpingpxl.addons.chattime.core.ChatTimeConfiguration;
import java.text.SimpleDateFormat;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

public class ChatReceiveListener {

  private final ChatTime addon;

  public ChatReceiveListener(ChatTime addon) {
    this.addon = addon;
  }

  @Subscribe(127)
  public void onChatReceive(ChatReceiveEvent event) {
    if (event.isCancelled()) {
      return;
    }

    ChatTimeConfiguration configuration = this.addon.configuration();
    String style = configuration.style().computedValue();
    SimpleDateFormat format = configuration.formatting().computedValue();

    style = style.replace("%time%", format.format(System.currentTimeMillis()));
    event.setMessage(Component.empty()
        .append(Component.text(style))
        .append(event.message())
    );
  }
}
