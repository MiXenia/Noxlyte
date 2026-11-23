package sh.catplu.noxlyte.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import sh.catplu.noxlyte.Noxlyte;
import sh.catplu.noxlyte.item.ModItems;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper helper) {
        super(
            output,
            lookup,
            CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()),
            Noxlyte.MODID,
            helper
        );
    }

    @Override 
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.DYEABLE).add(
            ModItems.SHATTERGLASS.get(),
            ModItems.SCINTILLA.get()
        );
    }
}
