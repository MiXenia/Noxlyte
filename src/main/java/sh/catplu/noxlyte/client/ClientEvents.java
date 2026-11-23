package sh.catplu.noxlyte.client;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import sh.catplu.noxlyte.Noxlyte;
import sh.catplu.noxlyte.client.model.ScintillaGeometry;
import sh.catplu.noxlyte.item.ModItems;

@EventBusSubscriber(modid = Noxlyte.MODID, value = Dist.CLIENT)
public class ClientEvents {
    // Tint Shatterglass //
    @SubscribeEvent 
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
        (stack, tintIndex) -> tintIndex == 0 ? DyedItemColor.getOrDefault(stack, 0xFFFFFFFF) : 0xFFFFFFFF,
        ModItems.SHATTERGLASS,
        ModItems.SCINTILLA
        );
    }
    
    // Remove "Dyed" //
    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event) {
        String namespace = BuiltInRegistries.ITEM.getKey(event.getItemStack().getItem()).getNamespace();
        if (!namespace.equals(Noxlyte.MODID)) return;
        event.getToolTip().removeIf(line -> line.getContents() instanceof TranslatableContents t && t.getKey().equals("item.dyed"));
    }

    // Render Scintilla //
    @SubscribeEvent 
    public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register(
            ResourceLocation.fromNamespaceAndPath(Noxlyte.MODID, "scintilla_model"),
            ScintillaGeometry.LOADER
        );
    }
}
