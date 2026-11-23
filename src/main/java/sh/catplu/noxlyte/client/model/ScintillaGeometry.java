package sh.catplu.noxlyte.client.model;

import java.util.function.Function;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;

public class ScintillaGeometry implements IUnbakedGeometry<ScintillaGeometry> {
    public static final IGeometryLoader<ScintillaGeometry> LOADER = (json, context) -> new ScintillaGeometry();

    @Override
    public BakedModel bake(
        IGeometryBakingContext context,
        ModelBaker baker,
        Function<Material, TextureAtlasSprite> spriteGetter,
        ModelState modelState,
        ItemOverrides overrides
    ) {
        return new ScintillaOverrides(spriteGetter, modelState, context.getTransforms()).get(ScintillaLook.DEFAULT);
    }
}
