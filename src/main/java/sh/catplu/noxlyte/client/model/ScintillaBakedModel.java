package sh.catplu.noxlyte.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.geometry.UnbakedGeometryHelper;

public class ScintillaBakedModel implements BakedModel {
    private final List<BakedQuad> quads;
    private final TextureAtlasSprite particle;
    private final ItemTransforms transforms;
    private final ItemOverrides overrides;

    public ScintillaBakedModel(
        ScintillaLook look,
        Function<Material, TextureAtlasSprite> sprites,
        ModelState state,
        ItemTransforms transforms,
        ItemOverrides overrides
    ) {
        List<BakedQuad> baked = new ArrayList<>();
        List<ScintillaLook.Layer> layers = look.layers();
        for (ScintillaLook.Layer layer : layers) {
            TextureAtlasSprite sprite = sprites.apply(new Material(InventoryMenu.BLOCK_ATLAS, layer.texture()));
            List<BlockElement> elements = UnbakedGeometryHelper.createUnbakedItemElements(layer.tint(), sprite);
            baked.addAll(UnbakedGeometryHelper.bakeElements(elements, material -> sprite, state));
        }
        this.quads = List.copyOf(baked);
        this.particle = sprites.apply(new Material(InventoryMenu.BLOCK_ATLAS, layers.get(1).texture()));
        this.transforms = transforms;
        this.overrides = overrides;
    }

    @Override 
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource random) {
        return side == null ? quads : List.of();
    }

    @Override public boolean            useAmbientOcclusion() { return false;      }
    @Override public boolean            isGui3d()             { return false;      }
    @Override public boolean            usesBlockLight()      { return false;      }
    @Override public boolean            isCustomRenderer()    { return false;      }
    @Override public TextureAtlasSprite getParticleIcon()     { return particle;   }
    @Override public ItemTransforms     getTransforms()       { return transforms; }
    @Override public ItemOverrides      getOverrides()        { return overrides;  }
}
