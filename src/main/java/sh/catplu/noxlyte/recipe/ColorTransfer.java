package sh.catplu.noxlyte.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class ColorTransfer extends ShapedRecipe{
    final int colorSlot;

    public ColorTransfer(ShapedRecipe base, int colorSlot) {
        super(base.getGroup(), base.category(), base.pattern, base.getResultItem(null),base.showNotification());
        this.colorSlot = colorSlot;
    }

    @Override 
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.COLOR.get();
    }

    private boolean matchesUnmirrored(CraftingInput input) {
        if (input.width() != getWidth() || input.height() != getHeight()) return false;
        for (int i = 0; i < input.size(); i++) {
            if (!getIngredients().get(i).test(input.getItem(i))) return false;
        }
        return true;
    }

    @Override 
    public boolean matches(CraftingInput input, Level level) {
        return matchesUnmirrored(input) && !input.getItem(colorSlot).getComponentsPatch().isEmpty();
    }

    @Override 
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack donor  = input.getItem(colorSlot);
        ItemStack result = getResultItem(registries);
        ItemStack out    = donor.transmuteCopy(result.getItem(), result.getCount());
        out.applyComponents(result.getComponentsPatch());
        return out;
    }

    public static class Serializer implements RecipeSerializer<ColorTransfer> {
        public static final MapCodec<ColorTransfer> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ShapedRecipe.Serializer.CODEC.forGetter(recipe -> recipe),
            Codec.INT.optionalFieldOf("color_slot",0).forGetter(recipe-> recipe.colorSlot)
        ).apply(inst,ColorTransfer::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ColorTransfer> STREAM_CODEC = StreamCodec.composite(
            ShapedRecipe.Serializer.STREAM_CODEC,
            recipe -> recipe,
            ByteBufCodecs.VAR_INT,
            recipe -> recipe.colorSlot,
            ColorTransfer::new
        );

        @Override 
        public MapCodec<ColorTransfer> codec() {
            return CODEC;
        }

        @Override 
        public StreamCodec<RegistryFriendlyByteBuf, ColorTransfer> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
