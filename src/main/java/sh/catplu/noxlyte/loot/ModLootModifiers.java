package sh.catplu.noxlyte.loot;

import java.util.function.Supplier;

import com.mojang.serialization.MapCodec;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import sh.catplu.noxlyte.Noxlyte;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister
        .create(
            NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS,
            Noxlyte.MODID
        );
    
    public static final Supplier<MapCodec<SmashingModifier>> SMASHING = LOOT_MODIFIERS.register("smashing", () -> SmashingModifier.CODEC);
    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS.register(eventBus);
    }
}
