package sh.catplu.noxlyte.item;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import sh.catplu.noxlyte.Noxlyte;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Noxlyte.MODID);

    public static final DeferredItem<ShatterglassItem> SHATTERGLASS = ITEMS.registerItem("shatterglass", ShatterglassItem::new);
    public static final DeferredItem<ScintillaItem> SCINTILLA = ITEMS.registerItem("scintilla",ScintillaItem::new,new Item.Properties().durability(9).food(ScintillaItem.DEFAULT_FOOD));

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

    public static final Map<String, DeferredItem<Item>> BOTTLES = new LinkedHashMap<>();
    static {
        for (String material : Parts.MATERIALS) {
            for (String shape : Parts.SHAPES) {
                String key = shape + "_" + material;
                BOTTLES.put(key,ITEMS.registerSimpleItem(key + "_bottle"));
            }
        }
    }

    public static DeferredItem<Item> bottle(String shape, String material) {
        return BOTTLES.get(shape + "_" + material);
    }

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





