package sh.catplu.noxlyte.component;

import com.mojang.serialization.Codec;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sh.catplu.noxlyte.Noxlyte;

public class ModComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE,Noxlyte.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> BOTTLE_STYLE = string("bottle_style");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> BOTTLE_MATERIAL = string("bottle_material");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> LEFT_DUST = string("left_dust");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> RIGHT_DUST = string("right_dust");

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> NUTRITION = COMPONENTS.registerComponentType("nutrition", builder -> builder
        .persistent(ExtraCodecs.NON_NEGATIVE_INT)
        .networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> SATURATION = COMPONENTS.registerComponentType("saturation", builder -> builder
        .persistent(Codec.FLOAT)
        .networkSynchronized(ByteBufCodecs.FLOAT)
    );

    private static DeferredHolder<DataComponentType<?>, DataComponentType<String>> string(String name) {
        return COMPONENTS.registerComponentType(name, builder -> builder
            .persistent(Codec.STRING)
            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
        );
    }

    public static void register(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }
}
