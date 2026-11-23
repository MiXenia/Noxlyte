package sh.catplu.noxlyte.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import sh.catplu.noxlyte.item.ScintillaItem;

public record ScintillaRarityPredicate(Rarity min) implements ItemSubPredicate {
    public static final Codec<ScintillaRarityPredicate> CODEC = RecordCodecBuilder.create(
        inst -> inst.group(Rarity.CODEC.fieldOf("min").forGetter(ScintillaRarityPredicate::min)
    ).apply(inst, ScintillaRarityPredicate::new));

    @Override 
    public boolean matches(ItemStack stack) {
        return stack.getItem() instanceof ScintillaItem && ScintillaItem.rarityOf(stack).ordinal() >= min.ordinal();
    }
}
