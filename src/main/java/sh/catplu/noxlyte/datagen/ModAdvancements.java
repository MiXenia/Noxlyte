package sh.catplu.noxlyte.datagen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import sh.catplu.noxlyte.Noxlyte;
import sh.catplu.noxlyte.advancement.ModPredicates;
import sh.catplu.noxlyte.advancement.ScintillaRarityPredicate;
import sh.catplu.noxlyte.component.ModComponents;
import sh.catplu.noxlyte.item.ModItems;
import sh.catplu.noxlyte.item.Parts;

public class ModAdvancements implements AdvancementProvider.AdvancementGenerator {
    private static final int ICON_COLOR = 0xEF7DA3;

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper ExistingFileHelper) {
        // scintilla root //
        AdvancementHolder root = new Advancement.Builder().display(
            scintillaIcon("heart","end","snow","diamond"),
            title("root"),
            description("root"),
            ResourceLocation.withDefaultNamespace("textures/block/pink_concrete_powder.png"),
            AdvancementType.TASK,
            true,
            false,
            false
        ).addCriterion("starting_scintilla", PlayerTrigger.TriggerInstance.tick()).save(saver,id("root"));

        // smash glass//
        AdvancementHolder smashGlass = node(root, "smash_glass", new ItemStack(Items.BRICK))
            .addCriterion("has_shatterglass",has(ModItems.SHATTERGLASS))
            .rewards(AdvancementRewards.Builder.recipe(rl("scintilla")))
            .save(saver,id("smash_glass"));
        
        // hand over faucet //
        Advancement.Builder secret = node(root, "secret_glass", shatterglassIcon(0xFFAADD), AdvancementType.CHALLENGE, false, true)
            .requirements(AdvancementRequirements.Strategy.OR)
            .rewards(AdvancementRewards.Builder.loot(lootTable("kickstart")));
        ModLootModifierProvider.SECRET_SMASHES.forEach(smash -> 
            secret.addCriterion(
                smash.name(),
                has(
                    ModItems.SHATTERGLASS,
                    is(
                        DataComponents.DYED_COLOR,
                        new DyedItemColor(
                            smash.color(),
                            true
                        )
                    )
                )
            )
        );
        secret.save(saver,id("secret_glass"));

        // first scintilla //
        AdvancementHolder craftFirst = node(smashGlass, "craft_first_scintilla", scintillaIcon("vanilla","glass","sugar","snow"), AdvancementType.GOAL, true,false)
            .addCriterion("has_scintilla", has(ModItems.SCINTILLA))
            .rewards(firstUnlocks())
            .save(saver, id("craft_first_scintilla"));

        // tier chains //
        List<AdvancementHolder> cap = chain(
            saver,
            craftFirst,
            "cap_up",
            Parts.MATERIALS,
            material -> has(
                ModItems.SCINTILLA,
                is(
                    DataComponents.MAX_DAMAGE,
                    Parts.CAPACITY.get(material)
                )
            ),
            material -> recipes(materialRecipes(material))
        );

        // nutrition chain //
        chain(
            saver,
            craftFirst,
            "nut_up",
            List.copyOf(Parts.NUTRITION.keySet()),
            dust -> has(
                ModItems.SCINTILLA,
                is(
                    ModComponents.NUTRITION.get(),
                    Parts.NUTRITION.get(dust)
                )
            ),
            dust -> recipes(List.of("upgrade/dust/" + dust))
        );

        // saturation chain //
        chain(
            saver,
            craftFirst,
            "sat_up",
            List.copyOf(Parts.SATURATION.keySet()),
            dust -> has(
                ModItems.SCINTILLA,
                is(
                    ModComponents.SATURATION.get(),
                    Parts.SATURATION.get(dust)
                )
            ),
            dust -> recipes(List.of("upgrade/dust/" + dust))
        );

        // Checkpoints //
        rarity(saver,cap.get(0),"uncommon",AdvancementType.GOAL,     Rarity.UNCOMMON,scintillaIcon("star", "emerald","diamond","amethyst"),  null);
        rarity(saver,cap.get(2),"rare",    AdvancementType.GOAL,     Rarity.RARE,    scintillaIcon("holy", "eye",    "echo",   "hots"),      null);
        // Victory //
        rarity(saver,cap.get(4),"epic",    AdvancementType.CHALLENGE,Rarity.EPIC,    scintillaIcon("heart","end",    "star",   "prismarine"),lootTable("victory"));

        // Glam Guide //
        Advancement.Builder glamour = node(
            craftFirst,
            "glamour",
            scintillaIcon("dusty","glass","redstone","glowstone"),
            AdvancementType.GOAL,
            true,
            false
        ).requirements(AdvancementRequirements.Strategy.OR);
        for (String dust : List.of("redstone", "glowstone","gunpowder")) {
            glamour.addCriterion(dust + "_left" , has(ModItems.SCINTILLA, is(ModComponents.LEFT_DUST.get(),  dust)));
            glamour.addCriterion(dust + "_right", has(ModItems.SCINTILLA, is(ModComponents.RIGHT_DUST.get(), dust)));
        }
        glamour.save(saver,id("glamour"));
    }

    // Growing advancements like a flower~! //
    private static List<AdvancementHolder> chain(
        Consumer<AdvancementHolder> saver,
        AdvancementHolder parent,
        String prefix,
        List<String> tiers,
        Function<String, Criterion<?>> reached,
        Function<String, AdvancementRewards.Builder> unlocks
    ) {
        List<AdvancementHolder> out = new ArrayList<>();
        for (int i = 1; i < tiers.size(); i++) {
            String tier = tiers.get(i);
            String name = prefix + "_" + i;
            Advancement.Builder builder = node(
                parent,
                name,
                new ItemStack(ModRecipeProvider.dustItem(tier))
            ).addCriterion("reached", reached.apply(tier));
            if (i + 1 < tiers.size()) builder.rewards(unlocks.apply(tiers.get(i + 1)));
            parent = builder.save(saver, id(name));
            out.add(parent);
        }
        return out;
    }

    // darling, you simply MUST try this... beverage? //
    private static void rarity(
        Consumer<AdvancementHolder> saver,
        AdvancementHolder parent,
        String name,
        AdvancementType type,
        Rarity min,
        ItemStack icon,
        @Nullable ResourceKey<LootTable> loot
    ) {
        Advancement.Builder builder = node(parent, name, icon, type, true, true)
            .addCriterion("rarity", InventoryChangeTrigger.TriggerInstance.hasItems(
                ItemPredicate.Builder.item()
                    .of(ModItems.SCINTILLA)
                    .withSubPredicate(
                        ModPredicates.SCINTILLA_RARITY.get(),
                        new ScintillaRarityPredicate(min)
                    )
            )
        );
        if (loot != null) builder.rewards(AdvancementRewards.Builder.loot(loot));
        builder.save(saver, id(name));
    }

    // Node after Node, mew can reach the end! //
    private static Advancement.Builder node(AdvancementHolder parent, String name, ItemStack icon) {
        return node(parent, name, icon, AdvancementType.TASK, true, false);
    }

    private static Advancement.Builder node(AdvancementHolder parent, String name, ItemStack icon, AdvancementType type, boolean announce, boolean hidden) {
        return new Advancement.Builder()
            .parent(parent)
            .display(icon, title(name), description(name),null,type,true,announce,hidden);
    }

    // i has! Do you has? Cus' i has! //
    private static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    // i can haz scintilla? //
    private static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item, DataComponentPredicate components) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(item).hasComponents(components));
    }

    private static <T> DataComponentPredicate is(DataComponentType<T> type, T value) {
        return DataComponentPredicate.builder().expect(type, value).build();
    }

    // rewards //
    private static AdvancementRewards.Builder recipes(List<String> paths) {
        AdvancementRewards.Builder rewards = new AdvancementRewards.Builder();
        paths.forEach(path -> rewards.addRecipe(rl(path)));
        return rewards;
    }

    private static List<String> materialRecipes(String material) {
        List<String> out = new ArrayList<>();
        for (String shape : Parts.SHAPES) {
            out.add(material + "_bottles/" + shape + "_bottle");
            out.add("upgrade/bottle/" + material + "/" + shape);
        }
        return(out);
    }

    // get-go resippies //
    private static AdvancementRewards.Builder firstUnlocks() {
        List<String> paths = new ArrayList<>();
        // stonecutter resippies //
        for (String dust : Parts.DUSTS) {
            if (ModRecipeProvider.GRIND_CORE.containsKey(dust)) paths.add("dust_grinding/" + dust + "_dust");
        }
        // two tiers of bottle resippies //
        paths.addAll(materialRecipes(Parts.MATERIALS.get(0)));
        paths.addAll(materialRecipes(Parts.MATERIALS.get(1)));
        // two tiers of dust resippies //
        for (String dust : List.copyOf(Parts.NUTRITION.keySet()).subList(0,2)) paths.add("upgrade/dust/" + dust);
        for (String dust : List.copyOf(Parts.SATURATION.keySet()).subList(0,2)) paths.add("upgrade/dust/" + dust);
        // all glam resippies //
        for (String material : Parts.MATERIALS) {
            for (String shape : Parts.SHAPES) paths.add("glamour/bottle/" + material + "/" + shape);
        }
        for (String side : List.of("left", "right")) {
            for (String dust : Parts.DUSTS) paths.add("glamour/dust/" + side + "/" + dust);
        }
        return recipes(paths);
    }

    // A gallery of Flavors to choose from //
    private static ItemStack scintillaIcon(String style, String material, String left, String right) {
        ItemStack stack = ModItems.SCINTILLA.toStack();
        stack.set(ModComponents.BOTTLE_STYLE,    style);
        stack.set(ModComponents.BOTTLE_MATERIAL, material);
        stack.set(ModComponents.LEFT_DUST,       left);
        stack.set(ModComponents.RIGHT_DUST,      right);
        stack.set(DataComponents.DYED_COLOR,     new DyedItemColor(ICON_COLOR, false));
        return stack;
    }

    // What's wrong? It's just snow! //
    private static ItemStack shatterglassIcon(int rgb) {
        ItemStack stack = ModItems.SHATTERGLASS.toStack();
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(rgb, false));
        return stack;
    }

    // String Chease //
    private static String id(String name) {
        return Noxlyte.MODID + ":" + name;
    }

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(Noxlyte.MODID, path);
    }

    private static ResourceKey<LootTable> lootTable(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, rl(path));
    }

    private static Component title(String name) {
        return Component.translatable("noxlyte.advancements." + name + ".title");
    }

    private static Component description(String name) {
        return Component.translatable("noxlyte.advancements." + name + ".description");
    }
}