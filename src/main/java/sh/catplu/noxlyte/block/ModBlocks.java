package sh.catplu.noxlyte.block;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import sh.catplu.noxlyte.Noxlyte;
import sh.catplu.noxlyte.item.ModItems;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Noxlyte.MODID);

    public static final DeferredBlock<FlowerBlock> FRAILILY = registerBlock("frailily",
        () -> new FlowerBlock(MobEffects.LUCK, Integer.MAX_VALUE, BlockBehaviour.Properties
            .ofFullCopy(Blocks.ALLIUM)
            .sound(SoundType.MEDIUM_AMETHYST_BUD)
            .noOcclusion()
            .noCollission()),
            "frailily_lore"
    );

    public static final DeferredBlock<FlowerPotBlock> POTTED_FRAILILY = BLOCKS.register("potted_frailily",
        () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FRAILILY, BlockBehaviour.Properties
            .ofFullCopy(Blocks.POTTED_ALLIUM)
            .sound(SoundType.LARGE_AMETHYST_BUD)
            .noOcclusion()
        )
    );

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, @Nullable String loreID) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()) {
            @Override
            public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                if (loreID != null) {
                    tooltip.add(Component.translatable("tooltip.scintilla."+loreID+".1")
                    .withStyle(ChatFormatting.DARK_GRAY,ChatFormatting.ITALIC));
                    tooltip.add(Component.translatable("tooltip.scintilla."+loreID+".2")
                    .withStyle(ChatFormatting.DARK_GRAY,ChatFormatting.ITALIC));
                    tooltip.add(Component.translatable("tooltip.scintilla."+loreID+".3")
                    .withStyle(ChatFormatting.GRAY,ChatFormatting.ITALIC));
                }
                super.appendHoverText(stack,context,tooltip,flag);
            }
        });
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name,block,null);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block, @Nullable String loreID) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn,loreID);
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
