package sh.catplu.noxlyte.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import sh.catplu.noxlyte.item.ModItems;

public class SmashingModifier extends LootModifier {
    public static final MapCodec<SmashingModifier> CODEC = RecordCodecBuilder.mapCodec(
        inst -> codecStart(inst)
            .and(
                Codec.INT.fieldOf("color").forGetter(m -> m.color)
            ).apply(
                inst,
                SmashingModifier::new
            )
    );

    private final int color;

    public SmashingModifier(LootItemCondition[] conditions, int color) {
        super(conditions);
        this.color = color;
    }

    @Override 
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ItemStack glass = new ItemStack(ModItems.SHATTERGLASS.get());
        glass.set(DataComponents.DYED_COLOR, new DyedItemColor(color,true));
        generatedLoot.add(glass);
        return generatedLoot;
    }

    @Override 
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
