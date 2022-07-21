package dev.jumpingpxl.addons.chattime.core;

import com.google.inject.Singleton;
import dev.jumpingpxl.addons.chattime.core.listener.ChatReceiveListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonListener;

@Singleton
@AddonListener
public class ChatTime extends LabyAddon<ChatTimeConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.registerListener(ChatReceiveListener.class);

    this.labyAPI().lssInjector().register(this, "settings-injector.lss", "activity/settings.lss");
  }

  @Override
  protected Class<ChatTimeConfiguration> configurationClass() {
    return ChatTimeConfiguration.class;
  }
}
