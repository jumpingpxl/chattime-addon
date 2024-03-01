package dev.jumpingpxl.addons.chattime.core.listener;

import dev.jumpingpxl.addons.chattime.core.ChatTime;
import dev.jumpingpxl.addons.chattime.core.ChatTimeConfiguration;
import java.text.SimpleDateFormat;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.loader.MinecraftVersions;

public class ChatReceiveListener {

  // also declared in net.labymod.core.client.chat.advanced.ChatDuplicateMessageHandler
  private static final String IGNORE_CHANGES_KEY = "ChatDuplicateMessages-IgnoreChanges";

  private final ChatTime addon;

  public ChatReceiveListener(ChatTime addon) {
    this.addon = addon;
  }

  @Subscribe(125)
  public void onChatReceive(ChatReceiveEvent event) {
    if (event.isCancelled()) {
      return;
    }

    ChatTimeConfiguration configuration = this.addon.configuration();
    String style = configuration.style().computedValue();
    SimpleDateFormat format = configuration.formatting().computedValue();

    Component message = event.message();
    if (MinecraftVersions.V1_12_2.orOlder()) {
      message = message.copy().colorIfAbsent(NamedTextColor.WHITE);
    }

    style = style.replace("%time%", format.format(System.currentTimeMillis()));
    event.setMessage(Component.empty()
        .append(Component.text(style))
        .append(message)
    );

    event.chatMessage().metadata().set(IGNORE_CHANGES_KEY, true);
  }
}
