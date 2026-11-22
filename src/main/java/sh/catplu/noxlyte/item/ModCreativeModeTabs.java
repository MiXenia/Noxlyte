package sh.catplu.noxlyte.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sh.catplu.noxlyte.Noxlyte;
import sh.catplu.noxlyte.component.ModComponents;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, Noxlyte.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NOXLYTE_TAB = CREATIVE_MODE_TABS.register("noxlyte_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> scintilla("heart", "end", "snow", "diamond", 0xFFAADD, 257, 10, 20.0f))
                    .title(Component.translatable("creativetab.noxlyte_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(scintilla("vanilla","glass",  "sugar",   "snow",      0x324BB0,9,  2, 0.4f ));
                        pOutput.accept(scintilla("dusty",  "emerald","diamond", "amethyst",  0xB04BD5,17, 4, 1.2f ));
                        pOutput.accept(scintilla("jar",    "pearl",  "quartz",  "blaze",     0x973232,33, 6, 6.0f ));
                        pOutput.accept(scintilla("holy",   "eye",    "echo",    "hots",      0x4B7D97,65, 8, 12.8f));
                        pOutput.accept(scintilla("star",   "crystal","star",    "prismarine",0x7DCA19,129,10,20.0f));
                        pOutput.accept(scintilla("heart",  "end",    "snow",    "diamond",   0xFFAADD,257,10,20.0f));
                        pOutput.accept(scintilla("dusty",  "glass",  "redstone","glowstone", 0xFFFFFF,257,10,20.0f));
                        pOutput.accept(scintilla("vial",   "crystal","crystal", "crystal",   0x954DBB,257,10,20.0f));
                        pOutput.accept(scintilla("diamond","end",    "glass",   "glass",     0xC4A2A8,257,10,20.0f));

                        pOutput.accept(ModItems.SHATTERGLASS);

                        pOutput.accept(ModItems.DIAMOND_DUST);
                        pOutput.accept(ModItems.QUARTZ_DUST);
                        pOutput.accept(ModItems.ECHO_DUST);
                        pOutput.accept(ModItems.STAR_DUST);

                        pOutput.accept(ModItems.AMETHYST_DUST);
                        pOutput.accept(ModItems.BLAZE_DUST);
                        pOutput.accept(ModItems.HOTS_DUST);
                        pOutput.accept(ModItems.PRISMARINE_DUST);

                        pOutput.accept(ModItems.GLASS_DUST);
                        pOutput.accept(ModItems.DIAMOND_GLASS_BOTTLE);
                        pOutput.accept(ModItems.DUSTY_GLASS_BOTTLE);
                        pOutput.accept(ModItems.HEART_GLASS_BOTTLE);
                        pOutput.accept(ModItems.HOLY_GLASS_BOTTLE);
                        pOutput.accept(ModItems.JAR_GLASS_BOTTLE);
                        pOutput.accept(ModItems.STAR_GLASS_BOTTLE);
                        pOutput.accept(ModItems.VANILLA_GLASS_BOTTLE);
                        pOutput.accept(ModItems.VIAL_GLASS_BOTTLE);

                        pOutput.accept(ModItems.EMERALD_DUST);
                        pOutput.accept(ModItems.DIAMOND_EMERALD_BOTTLE);
                        pOutput.accept(ModItems.DUSTY_EMERALD_BOTTLE);
                        pOutput.accept(ModItems.HEART_EMERALD_BOTTLE);
                        pOutput.accept(ModItems.HOLY_EMERALD_BOTTLE);
                        pOutput.accept(ModItems.JAR_EMERALD_BOTTLE);
                        pOutput.accept(ModItems.STAR_EMERALD_BOTTLE);
                        pOutput.accept(ModItems.VANILLA_EMERALD_BOTTLE);
                        pOutput.accept(ModItems.VIAL_EMERALD_BOTTLE);

                        pOutput.accept(ModItems.PEARL_DUST);
                        pOutput.accept(ModItems.DIAMOND_PEARL_BOTTLE);
                        pOutput.accept(ModItems.DUSTY_PEARL_BOTTLE);
                        pOutput.accept(ModItems.HEART_PEARL_BOTTLE);
                        pOutput.accept(ModItems.HOLY_PEARL_BOTTLE);
                        pOutput.accept(ModItems.JAR_PEARL_BOTTLE);
                        pOutput.accept(ModItems.STAR_PEARL_BOTTLE);
                        pOutput.accept(ModItems.VANILLA_PEARL_BOTTLE);
                        pOutput.accept(ModItems.VIAL_PEARL_BOTTLE);

                        pOutput.accept(ModItems.EYE_DUST);
                        pOutput.accept(ModItems.DIAMOND_EYE_BOTTLE);
                        pOutput.accept(ModItems.DUSTY_EYE_BOTTLE);
                        pOutput.accept(ModItems.HEART_EYE_BOTTLE);
                        pOutput.accept(ModItems.HOLY_EYE_BOTTLE);
                        pOutput.accept(ModItems.JAR_EYE_BOTTLE);
                        pOutput.accept(ModItems.STAR_EYE_BOTTLE);
                        pOutput.accept(ModItems.VANILLA_EYE_BOTTLE);
                        pOutput.accept(ModItems.VIAL_EYE_BOTTLE);

                        pOutput.accept(ModItems.CRYSTAL_DUST);
                        pOutput.accept(ModItems.DIAMOND_CRYSTAL_BOTTLE);
                        pOutput.accept(ModItems.DUSTY_CRYSTAL_BOTTLE);
                        pOutput.accept(ModItems.HEART_CRYSTAL_BOTTLE);
                        pOutput.accept(ModItems.HOLY_CRYSTAL_BOTTLE);
                        pOutput.accept(ModItems.JAR_CRYSTAL_BOTTLE);
                        pOutput.accept(ModItems.STAR_CRYSTAL_BOTTLE);
                        pOutput.accept(ModItems.VANILLA_CRYSTAL_BOTTLE);
                        pOutput.accept(ModItems.VIAL_CRYSTAL_BOTTLE);

                        pOutput.accept(ModItems.END_DUST);
                        pOutput.accept(ModItems.DIAMOND_END_BOTTLE);
                        pOutput.accept(ModItems.DUSTY_END_BOTTLE);
                        pOutput.accept(ModItems.HEART_END_BOTTLE);
                        pOutput.accept(ModItems.HOLY_END_BOTTLE);
                        pOutput.accept(ModItems.JAR_END_BOTTLE);
                        pOutput.accept(ModItems.STAR_END_BOTTLE);
                        pOutput.accept(ModItems.VANILLA_END_BOTTLE);
                        pOutput.accept(ModItems.VIAL_END_BOTTLE);

                        // pOutput.accept(ModBlocks.FRAILILY);
                        // pOutput.accept(ModBlocks.POTTED_FRAILILY);

                    })
                    .build());

    private static ItemStack scintilla(String style, String material, String left, String right, int color, int maxDamage, int nutrition, float saturation) {
        ItemStack stack = ModItems.SCINTILLA.toStack();
        stack.set(ModComponents.BOTTLE_STYLE,     style);
        stack.set(ModComponents.BOTTLE_MATERIAL,  material);
        stack.set(ModComponents.LEFT_DUST,        left);
        stack.set(ModComponents.RIGHT_DUST,       right);
        stack.set(DataComponents.DYED_COLOR,      new DyedItemColor(color, false));
        stack.set(DataComponents.MAX_DAMAGE,      maxDamage);
        stack.set(ModComponents.NUTRITION,        nutrition);
        stack.set(ModComponents.SATURATION,       saturation);
        return stack;
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
