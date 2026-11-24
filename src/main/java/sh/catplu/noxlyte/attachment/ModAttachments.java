package sh.catplu.noxlyte.attachment;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import sh.catplu.noxlyte.Noxlyte;

public class ModAttachments {
    private ModAttachments() {}

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Noxlyte.MODID);

    // Tied to you no matter where you are,         //
    // Lovestitch connects you to those around you. //
    // It is the power to bond with loved ones.     //
    // It is the power to call for help.            //
    // And no matter what...                        //
    // It will never ever leave you.                //
    public static final Supplier<AttachmentType<List<ItemStack>>> SEWN = 
        ATTACHMENT_TYPES.register(
            "sewn",
            () -> AttachmentType.<List<ItemStack>>builder(() -> List.of())
                .serialize(ItemStack.CODEC.listOf(), list -> !list.isEmpty())
                .build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
