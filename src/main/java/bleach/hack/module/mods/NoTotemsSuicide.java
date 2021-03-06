package bleach.hack.module.mods;

import bleach.hack.event.events.EventTick;
import bleach.hack.module.Category;
import bleach.hack.module.Module;
import bleach.hack.setting.base.SettingMode;
import bleach.hack.utils.Finder;
import com.google.common.eventbus.Subscribe;
import net.minecraft.item.Items;

public class NoTotemsSuicide extends Module {

    boolean kill = false;

    public NoTotemsSuicide() {
        super("NoTotemsSuicide", KEY_UNBOUND, Category.COMBAT, "Automaticslly executes kill/suicide command",
                new SettingMode("Command", "/kill", "/suicide"));
    }

    @Subscribe
    public void onTick(EventTick event) {
        if (mc.getServer() == null && kill) {
            kill = false;
            return;
        }

        String cmd = getSetting(0).asMode().mode == 0 ? "/kill" : "/suicide";
        Integer totem = Finder.find(Items.TOTEM_OF_UNDYING, false);

        if (totem != null && !kill) kill = true;
        if (totem == null && kill) {
            mc.player.sendChatMessage(cmd);
            kill = false;
        }
    }
}
