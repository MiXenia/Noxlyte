package sh.catplu.noxlyte.advancement;

import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sh.catplu.noxlyte.Noxlyte;

public class ModPredicates {
    public static final DeferredRegister<ItemSubPredicate.Type<?>> SUB_PREDICATES = DeferredRegister.create(Registries.ITEM_SUB_PREDICATE_TYPE,Noxlyte.MODID);
    public static final DeferredHolder<ItemSubPredicate.Type<?>, ItemSubPredicate.Type<ScintillaRarityPredicate>> SCINTILLA_RARITY = SUB_PREDICATES.register(
        "scintilla_rarity",
        () -> new ItemSubPredicate.Type<>(ScintillaRarityPredicate.CODEC)
    );
    public static void register(IEventBus eventBus) {
        SUB_PREDICATES.register(eventBus);
    }
}
