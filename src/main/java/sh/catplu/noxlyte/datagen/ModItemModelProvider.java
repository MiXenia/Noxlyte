package sh.catplu.noxlyte.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import sh.catplu.noxlyte.Noxlyte;
import sh.catplu.noxlyte.block.ModBlocks;
import sh.catplu.noxlyte.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Noxlyte.MODID, helper);
    }

    @Override 
    protected void registerModels() {
        basicItem(ModItems.SHATTERGLASS.get());

        ModItems.ITEMS.getEntries().forEach(holder -> {
            String name = holder.getId().getPath();
            if (name.endsWith("_dust")) {
                flat(name, "d_" + name.replace("_dust", ""));
            }
        });

        ModItems.BOTTLES.forEach((key, bottle) -> 
            flat(bottle.getId().getPath(), "b_" + key)
        );

        flat(ModBlocks.FRAILILY.getId().getPath(),"frailily").renderType("minecraft:translucent");
    }

    private ItemModelBuilder flat(String name, String texture) {
        return withExistingParent(name, mcLoc("item/generated"))
            .texture("layer0", modLoc("item/" + texture));
    }
}
