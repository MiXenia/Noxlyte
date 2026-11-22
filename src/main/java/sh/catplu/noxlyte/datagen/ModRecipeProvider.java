package sh.catplu.noxlyte.datagen;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.neoforged.neoforge.common.Tags;
import sh.catplu.noxlyte.Noxlyte;
import sh.catplu.noxlyte.component.ModComponents;
import sh.catplu.noxlyte.item.ModItems;
import sh.catplu.noxlyte.item.Parts;
import sh.catplu.noxlyte.recipe.ColorTransfer;

public class ModRecipeProvider extends RecipeProvider{
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output,registries);
    }

    // tables //

    private static final Map<String, List<String>> BOTTLE_PATTERNS = Map.ofEntries(
        // Map.entry(
        //     "bulb",
        //     List.of(
        //         " G ",
        //         "G G",
        //         " C "
        //     )
        // ),
        Map.entry(
            "diamond",
            List.of(
                " C ",
                "GGG",
                " G "
            )
        ),
        // Map.entry(
        //     "duo",
        //     List.of(
        //         " GC",
        //         "G G",
        //         " G "
        //     )
        // ),
        Map.entry(
            "dusty",
            List.of(
                " C ",
                " G ",
                "GGG"
            )
        ),
        Map.entry(
            "heart",
            List.of(
                "GCG",
                "GGG",
                " G "
            )
        ),
        Map.entry(
            "holy",
            List.of(
                "GCG",
                "G G",
                "GGG"
            )
        ),
        Map.entry(
            "jar",
            List.of(
                " C ",
                "G G",
                "GGG"
            )
        ),
        // Map.entry(
        //     "kit",
        //     List.of(
        //         " G ",
        //         "GCG",
        //         " G "
        //     )
        // ),
        Map.entry(
            "star",
            List.of(
                "GCG",
                " G ",
                "G G"
            )
        ),
        Map.entry(
            "vanilla",
            List.of(
                " C ",
                "G G",
                " G "
            )
        ),
        Map.entry(
            "vial",
            List.of(
                " C ",
                " G ",
                " G "
            )
        )
    );

    private static final Map<String, Item> VANILLA_DUSTS = Map.of(
        "sugar", Items.SUGAR,
        "snow", Items.SNOWBALL,
        "glowstone", Items.GLOWSTONE_DUST,
        "gunpowder", Items.GUNPOWDER,
        "redstone", Items.REDSTONE
    );

    // stonecutter //
    private record Grind(Item from, int count) {}

    private static Grind grinder(Item from) {
        return grinder(from, 1);
    }
    private static Grind grinder(Item from, int count) {
        return new Grind(from,count);
    }

    private static final Map<String, Grind> GRIND_CORE = Map.ofEntries(
        Map.entry("amethyst",   grinder(Items.AMETHYST_SHARD)),
        Map.entry("blaze",      grinder(Items.BLAZE_ROD)),
        Map.entry("crystal",    grinder(Items.END_CRYSTAL)),
        Map.entry("diamond",    grinder(Items.DIAMOND)),
        Map.entry("echo",       grinder(Items.ECHO_SHARD)),
        Map.entry("emerald",    grinder(Items.EMERALD)),
        Map.entry("end",        grinder(Items.END_ROD)),
        Map.entry("eye",        grinder(Items.ENDER_EYE)),
        Map.entry("glass",      grinder(Items.GLASS)),
        Map.entry("hots",       grinder(Items.HEART_OF_THE_SEA,4)),
        Map.entry("pearl",      grinder(Items.ENDER_PEARL)),
        Map.entry("prismarine", grinder(Items.PRISMARINE_SHARD)),
        Map.entry("quartz",     grinder(Items.QUARTZ_BLOCK)),
        Map.entry("star",       grinder(Items.NETHER_STAR))
    );

    // bus stop //
    @Override 
    protected void buildRecipes(RecipeOutput out) {
        scintillaBase(out);
        dustGrinding(out);
        dustUpgrades(out);
        dustGlam(out);
        bottles(out);
        bottleUpgrades(out);
        bottleGlam(out);
    }

    //    Gift from Wyyv     //
    // Delicacy of Harlequin //
    //       Scintilla       //
    private void scintillaBase(RecipeOutput out) {
        ItemStack result = ModItems.SCINTILLA.toStack();
        result.set(ModComponents.BOTTLE_STYLE, "vanilla");
        result.set(ModComponents.BOTTLE_MATERIAL, "glass");
        result.set(ModComponents.LEFT_DUST, "sugar");
        result.set(ModComponents.RIGHT_DUST, "snow");
        result.set(DataComponents.MAX_DAMAGE, Parts.CAPACITY.get("glass"));
        result.set(ModComponents.NUTRITION, Parts.NUTRITION.get("sugar"));
        result.set(ModComponents.SATURATION, Parts.SATURATION.get("snow"));

        color(out, "scintilla", "", 4, result, Map.of(
            'U', Ingredient.of(Items.POWDER_SNOW_BUCKET),
            'O', Ingredient.of(Items.SNOWBALL),
            '^', Ingredient.of(Items.SUGAR),
            'X', Ingredient.of(ModItems.SHATTERGLASS)),
            "UUU",
            "OXO",
            "^^^"
        );
    }

    // dust grinding //
    private void dustGrinding(RecipeOutput out) {
        GRIND_CORE.forEach((dust, grind) -> out.accept(
            id("dust_grinding/"+dust+"_dust"),
            new StonecutterRecipe(
                "",
                Ingredient.of(grind.from()),
                new ItemStack(
                    dustItem(dust),
                    grind.count()
                )
            ),
            null
        ));
    }

    // shaped bottles //
    private void bottles(RecipeOutput out) {
        for (String material : Parts.MATERIALS) {
            for (String shape : Parts.SHAPES) {
                ShapedRecipePattern pattern = ShapedRecipePattern.of(
                    Map.of(
                        'C', Ingredient.of(ItemTags.PLANKS),
                        'G', Ingredient.of(dustItem(material))
                    ),
                    bottlePattern(shape)
                );
                out.accept(
                    id(material + "_bottles/" + shape + "_bottle"),
                    new ShapedRecipe(
                        material + "_bottles",
                        CraftingBookCategory.MISC,
                        pattern,
                        ModItems.bottle(
                            shape,
                            material
                        ).toStack()
                    ),
                    null
                );
            }
        }
    }

    // bottle upgrades //
    private void bottleUpgrades(RecipeOutput out) {
        for (String material : Parts.MATERIALS) {
            for (String shape : Parts.SHAPES) {
                ItemStack result = ModItems.SCINTILLA.toStack();

                result.set(ModComponents.BOTTLE_STYLE,shape);
                result.set(ModComponents.BOTTLE_MATERIAL,material);
                result.set(DataComponents.MAX_DAMAGE, Parts.CAPACITY.get(material));

                color(
                    out,
                    "upgrade/bottle/" + material + "/" + shape,
                    "noxlyte_upgrade_bottle",
                    0,
                    result,
                    Map.of(
                        'X', Ingredient.of(ModItems.SCINTILLA),
                        'U', Ingredient.of(ModItems.bottle(shape,material))
                    ),
                    "X",
                    "U"
                );
            }
        }
    }

    // bottle glams //
    private void bottleGlam(RecipeOutput out) {
        for (String material : Parts.MATERIALS) {
            for (String shape : Parts.SHAPES) {
                ItemStack result = ModItems.SCINTILLA.toStack();
                result.set(ModComponents.BOTTLE_STYLE, shape);
                result.set(ModComponents.BOTTLE_MATERIAL, material);

                color(
                    out,
                    "glamour/bottle/" + material + "/" + shape,
                    "noxlyte_glamour_bottle",
                    1,
                    result,
                    Map.of(
                        'O', Ingredient.of(Tags.Items.DYES),
                        'X', Ingredient.of(ModItems.SCINTILLA),
                        'U', Ingredient.of(ModItems.bottle(shape,material))),
                        "O",
                        "X",
                        "U"
                );
            }
        }
    }

    // dust upgrades //
    private void dustUpgrades(RecipeOutput out) {
        // nutrition //
        // left dust //
        Parts.NUTRITION.forEach((dust, nutrition) -> {
            ItemStack result = ModItems.SCINTILLA.toStack();
            result.set(ModComponents.LEFT_DUST, dust);
            result.set(ModComponents.NUTRITION, nutrition);
            dustUpgrade(
                out,
                dust,
                "noxlyte_upgrade_dust_left",
                result
            );
        });
        // saturation //
        // right dust //
        Parts.SATURATION.forEach((dust, saturation) -> {
            ItemStack result = ModItems.SCINTILLA.toStack();
            result.set(ModComponents.RIGHT_DUST, dust);
            result.set(ModComponents.SATURATION, saturation);
            dustUpgrade(
                out,
                dust,
                "noxlyte_upgrade_dust_right",
                result
            );
        });
    }

    // dust upgrade base //
    private void dustUpgrade(
        RecipeOutput out,
        String dust,
        String group,
        ItemStack result
    ) {
        color(
            out,
            "upgrade/dust/" + dust,
            group,
            4,
            result,
            Map.of(
                'U', Ingredient.of(dustItem(dust)),
                'X', Ingredient.of(ModItems.SCINTILLA)
            ),
            "UUU",
            "UXU",
            "UUU"
        );
    }

    // dust glams //
    private void dustGlam(RecipeOutput out) {
        // base //
        for (String dust : Parts.DUSTS) {
            Map<Character, Ingredient> key = Map.of(
                'O', Ingredient.of(Tags.Items.DYES),
                'U', Ingredient.of(dustItem(dust)),
                'X', Ingredient.of(ModItems.SCINTILLA)
            );
            // left glam //
            ItemStack left = ModItems.SCINTILLA.toStack();
            left.set(ModComponents.LEFT_DUST, dust);
            color(
                out,
                "glamour/dust/left/" + dust,
                "noxlyte_glamour_left_dust",
                3,
                left,
                key,
                " O",
                "UX"
            );
            // right glam //
            ItemStack right = ModItems.SCINTILLA.toStack();
            right.set(ModComponents.RIGHT_DUST, dust);
            color(
                out,
                "glamour/dust/right/" + dust,
                "noxlyte_glamour_right_dust",
                2,
                right,
                key,
                "O ",
                "XU"
            );
        }
    }

    // ColorTransfer builder //
    private static void color(
        RecipeOutput out,
        String path,
        String group,
        int colorSlot,
        ItemStack result,
        Map<Character, Ingredient> key,
        String... pattern) {
        ShapedRecipe base = new ShapedRecipe(
            group,
            CraftingBookCategory.MISC,
            ShapedRecipePattern.of(key, pattern),
            result
        );
        out.accept(
            id(path),
            new ColorTransfer(base, colorSlot),
            null
        );
    }

    // location builder //
    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(Noxlyte.MODID, path);
    }

    // dust accumulator //
    private static Item dustItem(String dust) {
        Item vanilla = VANILLA_DUSTS.get(dust);
        if (vanilla != null) return vanilla;
        return BuiltInRegistries.ITEM.getOptional(id(dust + "_dust")).orElseThrow(() -> new IllegalStateException("No item for dust '" + dust + "'."));
    }

    // bottle collector //
    private static List<String> bottlePattern(String shape) {
        List<String> pattern = BOTTLE_PATTERNS.get(shape);
        if (pattern == null) throw new IllegalStateException("No bottle pattern for shape '" + shape + "'.");
        return pattern;
    }
}
