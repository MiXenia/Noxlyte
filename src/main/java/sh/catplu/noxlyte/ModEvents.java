package sh.catplu.noxlyte;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import sh.catplu.noxlyte.attachment.ModAttachments;
import sh.catplu.noxlyte.item.ModTags;
import sh.catplu.noxlyte.item.ScintillaItem;
import sh.catplu.noxlyte.item.ShatterglassItem;

@EventBusSubscriber(modid = Noxlyte.MODID)
public class ModEvents {
    private static final int REPAIR_PER_GLASS = 8;

    // scintill repair //
    @SubscribeEvent 
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left  = event.getLeft();
        ItemStack right = event.getRight();
        if (!(left.getItem() instanceof ScintillaItem) || !(right.getItem() instanceof ShatterglassItem)) return;

        int damage = left.getDamageValue();
        if (damage == 0) return;

        int needed = (damage + REPAIR_PER_GLASS - 1) / REPAIR_PER_GLASS;
        int used   = Math.min(right.getCount(), needed);

        ItemStack output = left.copy();
        output.setDamageValue(Math.max(0, damage - used * REPAIR_PER_GLASS));

        String name = event.getName();
        if (StringUtil.isBlank(name)) {
            output.remove(DataComponents.CUSTOM_NAME);
        } else if (!name.equals(left.getHoverName().getString())) {
            output.set(DataComponents.CUSTOM_NAME, Component.literal(name));
        }

        event.setOutput(output);
        event.setMaterialCost(used);
        event.setCost(1);
    }

    // No matter where you are,       //
    // no matter what you're doing... //
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        List<ItemStack> kept = new ArrayList<> (player.getData(ModAttachments.SEWN));
        event.getDrops().removeIf(drop -> {
            ItemStack stack = drop.getItem();
            if (!stack.is(ModTags.SEWN)) return false;
            kept.add(stack.copy());
            return true;
        });
        if (!kept.isEmpty()) player.setData(ModAttachments.SEWN, kept);
    }

    // ...the thread that ties you to safety //
    //     will always be there for you!     //
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        Player player = event.getEntity();
        for (ItemStack stack : event.getOriginal().getData(ModAttachments.SEWN)) {
            if (!player.getInventory().add(stack)) player.drop(stack, false);
        }
    }
}
