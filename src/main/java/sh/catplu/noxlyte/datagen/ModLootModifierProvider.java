package sh.catplu.noxlyte.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import sh.catplu.noxlyte.Noxlyte;
import sh.catplu.noxlyte.loot.SmashingModifier;

public class ModLootModifierProvider extends GlobalLootModifierProvider{
    record Smash(String name, Block glass, Item tool, int color) {}

    private static final List<Smash> COMMON_SMASHES = List.of(
        brick("white",      Blocks.WHITE_STAINED_GLASS,      0xFFFFFF),
        brick("orange",     Blocks.ORANGE_STAINED_GLASS,     0xD57D32),
        brick("magenta",    Blocks.MAGENTA_STAINED_GLASS,    0xB04BD5),
        brick("light_blue", Blocks.LIGHT_BLUE_STAINED_GLASS, 0x6597D5),
        brick("yellow",     Blocks.YELLOW_STAINED_GLASS,     0xE2E232),
        brick("lime",       Blocks.LIME_STAINED_GLASS,       0x7DCA19),
        brick("pink",       Blocks.PINK_STAINED_GLASS,       0xEF7DA3),
        brick("gray",       Blocks.GRAY_STAINED_GLASS,       0x4B4B4B),
        brick("light_gray", Blocks.LIGHT_GRAY_STAINED_GLASS, 0x979797),
        brick("cyan",       Blocks.CYAN_STAINED_GLASS,       0x4B7D97),
        brick("purple",     Blocks.PURPLE_STAINED_GLASS,     0x7D3EB0),
        brick("blue",       Blocks.BLUE_STAINED_GLASS,       0x324BB0),
        brick("brown",      Blocks.BROWN_STAINED_GLASS,      0x654B32),
        brick("green",      Blocks.GREEN_STAINED_GLASS,      0x657D32),
        brick("red",        Blocks.RED_STAINED_GLASS,        0x973232),
        brick("black",      Blocks.BLACK_STAINED_GLASS,      0x000000)
    );

    static final List<Smash> SECRET_SMASHES = List.of(
        new Smash("weaver", Blocks.PINK_STAINED_GLASS, Items.SUGAR, 0xFFAADD),
        new Smash("catbird", Blocks.BLUE_STAINED_GLASS, Items.LAPIS_LAZULI, 0x0F00B3),
        new Smash("priestess", Blocks.BLACK_STAINED_GLASS, Items.ENDER_PEARL, 0xA06666),
        new Smash("worldmoth", Blocks.TINTED_GLASS, Items.ECHO_SHARD, 0x5F8787),
        new Smash("skyeater", Blocks.WHITE_STAINED_GLASS, Items.DIAMOND, 0x00FFFB)
    );

    public ModLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Noxlyte.MODID);
    }

    @Override 
    protected void start() {
        COMMON_SMASHES.forEach(this::addSmash);
        SECRET_SMASHES.forEach(this::addSmash);
    }

    private void addSmash(Smash smash) {
        LootItemCondition[] conditions = {
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(smash.glass()).build(),
            MatchTool.toolMatches(ItemPredicate.Builder.item().of(smash.tool())).build()
        };
        add("smashing/" + smash.name(), new SmashingModifier(conditions, smash.color()));
    }

    private static Smash brick(String name, Block glass, int color) {
        return new Smash(name, glass, Items.BRICK, color);
    }
}
