package bleach.hack.module.mods;

import bleach.hack.event.events.EventTick;
import bleach.hack.module.Category;
import bleach.hack.module.Module;
import bleach.hack.utils.BleachLogger;
import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;

public class TotemNotifier extends Module {

    HashMap<String, Boolean> players = new HashMap<>();

    public TotemNotifier() { super("TotemNotifier", KEY_UNBOUND, Category.MISC, "Notifies when other players have/don't have a totem in their hands"); }

    @Subscribe
    public void onTick(EventTick event) {
        if (mc.getServer() == null && !players.isEmpty()) players.clear();
        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof PlayerEntity) || entity == mc.player) continue;
            String displayName = entity.getDisplayName().getString();
            Item mainHandItem = ((PlayerEntity) entity).getMainHandStack().getItem();
            Item offHandItem = ((PlayerEntity) entity).getOffHandStack().getItem();
            boolean totem = false;
            if (mainHandItem == Items.TOTEM_OF_UNDYING || offHandItem == Items.TOTEM_OF_UNDYING) totem = true;
            if (!players.containsKey(displayName)) {
                players.put(displayName, totem);
                BleachLogger.infoMessage(totem ? "\u00a7f" + displayName + " \u00a73has totem in his hand" :
                        "\u00a7f" + displayName + " \u00a73hasn't totem in his hand");
            }
            if (players.get(displayName) != totem) {
                players.put(displayName, totem);
                BleachLogger.infoMessage(totem ? "\u00a7f" + displayName + " \u00a73now has totem in his hand" :
                        "\u00a7f" + displayName + " \u00a73now hasn't totem in his hand");
            }
        }
    }
}
