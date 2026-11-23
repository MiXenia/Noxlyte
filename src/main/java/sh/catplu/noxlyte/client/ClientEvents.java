package sh.catplu.noxlyte.client;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import sh.catplu.noxlyte.Noxlyte;

@EventBusSubscriber(modid = Noxlyte.MODID, value = Dist.CLIENT)
public class ClientEvents {
    // Remove "Dyed" //
    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event) {
        String namespace = BuiltInRegistries.ITEM.getKey(event.getItemStack().getItem()).getNamespace();
        if (!namespace.equals(Noxlyte.MODID)) return;
        event.getToolTip().removeIf(line -> line.getContents() instanceof TranslatableContents t && t.getKey().equals("item.dyed"));
    }
}
