package sh.catplu.noxlyte.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import sh.catplu.noxlyte.Noxlyte;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Noxlyte.MODID);

    public static final DeferredItem<Item> SHATTERGLASS = ITEMS.registerSimpleItem("shatterglass");
    //ITEMS.register("shatterglass", () -> new ShatterglassItem(new Item.Properties()));

    public static final DeferredItem<Item> SCINTILLA = ITEMS.registerSimpleItem("scintilla",
        new Item.Properties()
        .stacksTo(1)
        .durability(9)
        .food(
                new FoodProperties.Builder()
                .alwaysEdible()
                .nutrition(2)
                .saturationModifier(0.4F)
                .build()
        )
    );
//     ITEMS.register("scintilla",
//     () -> new ScintillaItem(new Item.Properties().stacksTo(1).durability(9)
//     .food(new FoodProperties.Builder()
//     .alwaysEat()
                        //     .nutrition(2)
                        //     .saturationMod(0.4F)
                        //     .build())));

    public static final DeferredItem<BuildyItem> DIAMOND_DUST = dust("diamond_dust","t1nutrition",Rarity.UNCOMMON);
    public static final DeferredItem<BuildyItem> QUARTZ_DUST = dust("quartz_dust","t2nutrition",Rarity.UNCOMMON);
    public static final DeferredItem<BuildyItem> ECHO_DUST = dust("echo_dust","t3nutrition",Rarity.RARE);
    public static final DeferredItem<BuildyItem> STAR_DUST = dust("star_dust","t4nutrition",new Item.Properties().rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE,true));

    public static final DeferredItem<BuildyItem> AMETHYST_DUST = dust("amethyst_dust","t1saturation", Rarity.UNCOMMON);
    public static final DeferredItem<BuildyItem> BLAZE_DUST = dust("blaze_dust","t2saturation",Rarity.UNCOMMON);
    public static final DeferredItem<BuildyItem> HOTS_DUST = dust("hots_dust","t3saturation",Rarity.RARE);
    public static final DeferredItem<BuildyItem> PRISMARINE_DUST = dust("prismarine_dust","t4saturation",Rarity.EPIC);

    public static final DeferredItem<BuildyItem> GLASS_DUST = dust("glass_dust","t0durability",Rarity.COMMON);
    public static final DeferredItem<BuildyItem> EMERALD_DUST = dust("emerald_dust","t1durability",Rarity.UNCOMMON);
    public static final DeferredItem<BuildyItem> PEARL_DUST = dust("pearl_dust","t2durability",Rarity.UNCOMMON);
    public static final DeferredItem<BuildyItem> EYE_DUST = dust("eye_dust","t3durability",Rarity.RARE);
    public static final DeferredItem<BuildyItem> CRYSTAL_DUST = dust("crystal_dust","t4durability",Rarity.RARE);
    public static final DeferredItem<BuildyItem> END_DUST = dust("end_dust","t5durability",Rarity.EPIC);

    public static final DeferredItem<Item> DIAMOND_GLASS_BOTTLE = ITEMS.registerSimpleItem("diamond_glass_bottle");
    public static final DeferredItem<Item> DUSTY_GLASS_BOTTLE = ITEMS.registerSimpleItem("dusty_glass_bottle");
    public static final DeferredItem<Item> HEART_GLASS_BOTTLE = ITEMS.registerSimpleItem("heart_glass_bottle");
    public static final DeferredItem<Item> HOLY_GLASS_BOTTLE = ITEMS.registerSimpleItem("holy_glass_bottle");
    public static final DeferredItem<Item> JAR_GLASS_BOTTLE = ITEMS.registerSimpleItem("jar_glass_bottle");
    public static final DeferredItem<Item> STAR_GLASS_BOTTLE = ITEMS.registerSimpleItem("star_glass_bottle");
    public static final DeferredItem<Item> VANILLA_GLASS_BOTTLE = ITEMS.registerSimpleItem("vanilla_glass_bottle");
    public static final DeferredItem<Item> VIAL_GLASS_BOTTLE = ITEMS.registerSimpleItem("vial_glass_bottle");

    public static final DeferredItem<Item> DIAMOND_EMERALD_BOTTLE = ITEMS.registerSimpleItem("diamond_emerald_bottle");
    public static final DeferredItem<Item> DUSTY_EMERALD_BOTTLE = ITEMS.registerSimpleItem("dusty_emerald_bottle");
    public static final DeferredItem<Item> HEART_EMERALD_BOTTLE = ITEMS.registerSimpleItem("heart_emerald_bottle");
    public static final DeferredItem<Item> HOLY_EMERALD_BOTTLE = ITEMS.registerSimpleItem("holy_emerald_bottle");
    public static final DeferredItem<Item> JAR_EMERALD_BOTTLE = ITEMS.registerSimpleItem("jar_emerald_bottle");
    public static final DeferredItem<Item> STAR_EMERALD_BOTTLE = ITEMS.registerSimpleItem("star_emerald_bottle");
    public static final DeferredItem<Item> VANILLA_EMERALD_BOTTLE = ITEMS.registerSimpleItem("vanilla_emerald_bottle");
    public static final DeferredItem<Item> VIAL_EMERALD_BOTTLE = ITEMS.registerSimpleItem("vial_emerald_bottle");

    public static final DeferredItem<Item> DIAMOND_PEARL_BOTTLE = ITEMS.registerSimpleItem("diamond_pearl_bottle");
    public static final DeferredItem<Item> DUSTY_PEARL_BOTTLE = ITEMS.registerSimpleItem("dusty_pearl_bottle");
    public static final DeferredItem<Item> HEART_PEARL_BOTTLE = ITEMS.registerSimpleItem("heart_pearl_bottle");
    public static final DeferredItem<Item> HOLY_PEARL_BOTTLE = ITEMS.registerSimpleItem("holy_pearl_bottle");
    public static final DeferredItem<Item> JAR_PEARL_BOTTLE = ITEMS.registerSimpleItem("jar_pearl_bottle");
    public static final DeferredItem<Item> STAR_PEARL_BOTTLE = ITEMS.registerSimpleItem("star_pearl_bottle");
    public static final DeferredItem<Item> VANILLA_PEARL_BOTTLE = ITEMS.registerSimpleItem("vanilla_pearl_bottle");
    public static final DeferredItem<Item> VIAL_PEARL_BOTTLE = ITEMS.registerSimpleItem("vial_pearl_bottle");

    public static final DeferredItem<Item> DIAMOND_EYE_BOTTLE = ITEMS.registerSimpleItem("diamond_eye_bottle");
    public static final DeferredItem<Item> DUSTY_EYE_BOTTLE = ITEMS.registerSimpleItem("dusty_eye_bottle");
    public static final DeferredItem<Item> HEART_EYE_BOTTLE = ITEMS.registerSimpleItem("heart_eye_bottle");
    public static final DeferredItem<Item> HOLY_EYE_BOTTLE = ITEMS.registerSimpleItem("holy_eye_bottle");
    public static final DeferredItem<Item> JAR_EYE_BOTTLE = ITEMS.registerSimpleItem("jar_eye_bottle");
    public static final DeferredItem<Item> STAR_EYE_BOTTLE = ITEMS.registerSimpleItem("star_eye_bottle");
    public static final DeferredItem<Item> VANILLA_EYE_BOTTLE = ITEMS.registerSimpleItem("vanilla_eye_bottle");
    public static final DeferredItem<Item> VIAL_EYE_BOTTLE = ITEMS.registerSimpleItem("vial_eye_bottle");

    public static final DeferredItem<Item> DIAMOND_CRYSTAL_BOTTLE = ITEMS.registerSimpleItem("diamond_crystal_bottle");
    public static final DeferredItem<Item> DUSTY_CRYSTAL_BOTTLE = ITEMS.registerSimpleItem("dusty_crystal_bottle");
    public static final DeferredItem<Item> HEART_CRYSTAL_BOTTLE = ITEMS.registerSimpleItem("heart_crystal_bottle");
    public static final DeferredItem<Item> HOLY_CRYSTAL_BOTTLE = ITEMS.registerSimpleItem("holy_crystal_bottle");
    public static final DeferredItem<Item> JAR_CRYSTAL_BOTTLE = ITEMS.registerSimpleItem("jar_crystal_bottle");
    public static final DeferredItem<Item> STAR_CRYSTAL_BOTTLE = ITEMS.registerSimpleItem("star_crystal_bottle");
    public static final DeferredItem<Item> VANILLA_CRYSTAL_BOTTLE = ITEMS.registerSimpleItem("vanilla_crystal_bottle");
    public static final DeferredItem<Item> VIAL_CRYSTAL_BOTTLE = ITEMS.registerSimpleItem("vial_crystal_bottle");

    public static final DeferredItem<Item> DIAMOND_END_BOTTLE = ITEMS.registerSimpleItem("diamond_end_bottle");
    public static final DeferredItem<Item> DUSTY_END_BOTTLE = ITEMS.registerSimpleItem("dusty_end_bottle");
    public static final DeferredItem<Item> HEART_END_BOTTLE = ITEMS.registerSimpleItem("heart_end_bottle");
    public static final DeferredItem<Item> HOLY_END_BOTTLE = ITEMS.registerSimpleItem("holy_end_bottle");
    public static final DeferredItem<Item> JAR_END_BOTTLE = ITEMS.registerSimpleItem("jar_end_bottle");
    public static final DeferredItem<Item> STAR_END_BOTTLE = ITEMS.registerSimpleItem("star_end_bottle");
    public static final DeferredItem<Item> VANILLA_END_BOTTLE = ITEMS.registerSimpleItem("vanilla_end_bottle");
    public static final DeferredItem<Item> VIAL_END_BOTTLE = ITEMS.registerSimpleItem("vial_end_bottle");

    private static DeferredItem<BuildyItem> dust(String name, String loreID, Rarity rarity) {
        return dust(name, loreID, new Item.Properties().rarity(rarity));
    }

    private static DeferredItem<BuildyItem> dust(String name, String loreID, Item.Properties properties) {
        return ITEMS.registerItem(name,props -> new BuildyItem(props,loreID),properties);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}





