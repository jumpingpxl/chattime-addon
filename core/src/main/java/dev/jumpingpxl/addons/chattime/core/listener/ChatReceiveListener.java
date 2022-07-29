package dev.jumpingpxl.addons.chattime.core.listener;

import com.google.inject.Inject;
import dev.jumpingpxl.addons.chattime.core.ChatTime;
import dev.jumpingpxl.addons.chattime.core.ChatTimeConfiguration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.labymod.api.event.Priority;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.text.SimpleDateFormat;

public class ChatReceiveListener {

  private final ChatTime addon;

  @Inject
  private ChatReceiveListener(ChatTime addon) {
    this.addon = addon;
  }

  @Subscribe(Priority.LATE)
  public void onChatReceive(ChatReceiveEvent event) {
    ChatTimeConfiguration configuration = this.addon.configuration();
    if (event.isCancelled() || !configuration.enabled().get()) {
      return;
    }

    String style = configuration.style().computedValue();
    SimpleDateFormat format = configuration.formatting().computedValue();

    style = style.replace("%time%", format.format(System.currentTimeMillis()));
    Component message = event.message();
    if (style.endsWith("&r")) {
      message = message.colorIfAbsent(NamedTextColor.WHITE);
    }

    event.setMessage((Component.empty().append(Component.text(style))).append(message));
  }
}
