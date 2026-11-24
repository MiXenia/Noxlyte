package sh.catplu.noxlyte.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import sh.catplu.noxlyte.Noxlyte;

public class ModTags {
    private ModTags() {}

    public static final TagKey<Item> SEWN = TagKey
        .create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(Noxlyte.MODID, "sewn")
        );
}
