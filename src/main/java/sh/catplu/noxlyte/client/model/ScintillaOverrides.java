package sh.catplu.noxlyte.client.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ScintillaOverrides extends ItemOverrides{
    private final Map<ScintillaLook, BakedModel> cache = new ConcurrentHashMap<>();
    private final Function<Material, TextureAtlasSprite> sprites;
    private final ModelState state;
    private final ItemTransforms transforms;

    public ScintillaOverrides(Function<Material, TextureAtlasSprite> sprites, ModelState state, ItemTransforms transforms) {
        this.sprites = sprites;
        this.state = state;
        this.transforms = transforms;
    }

    public BakedModel get(ScintillaLook look) {
        return cache.computeIfAbsent(look, l -> new ScintillaBakedModel(l,sprites,state,transforms,this));
    }

    @Override 
    public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        return get(ScintillaLook.of(stack));
    }
}
