package sh.catplu.noxlyte.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sh.catplu.noxlyte.Noxlyte;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER,Noxlyte.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, ColorTransfer.Serializer> COLOR = SERIALIZERS.register("color",ColorTransfer.Serializer::new);
    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
