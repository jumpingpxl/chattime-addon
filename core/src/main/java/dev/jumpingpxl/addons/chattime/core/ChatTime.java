package dev.jumpingpxl.addons.chattime.core;

import dev.jumpingpxl.addons.chattime.core.listener.ChatReceiveListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class ChatTime extends LabyAddon<ChatTimeConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();
    this.registerListener(new ChatReceiveListener(this));
  }

  @Override
  protected Class<ChatTimeConfiguration> configurationClass() {
    return ChatTimeConfiguration.class;
  }
}
