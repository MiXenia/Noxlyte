package sh.catplu.noxlyte.client.model;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import sh.catplu.noxlyte.Noxlyte;
import sh.catplu.noxlyte.component.ModComponents;

public record ScintillaLook(String style, String material, String left, String right) {
    public static final ScintillaLook DEFAULT = new ScintillaLook("vanilla","glass","sugar","snow");
    
    public record Layer(ResourceLocation texture, int tint) {}
    public static final int TINTED =  0;
    public static final int PLAIN  = -1;

    public static ScintillaLook of(ItemStack stack) {
        return new ScintillaLook(
            stack.getOrDefault(ModComponents.BOTTLE_STYLE,    DEFAULT.style()   ),
            stack.getOrDefault(ModComponents.BOTTLE_MATERIAL, DEFAULT.material()),
            stack.getOrDefault(ModComponents.LEFT_DUST,       DEFAULT.left()    ),
            stack.getOrDefault(ModComponents.RIGHT_DUST,      DEFAULT.right()   )
        );
    }

    public List<Layer> layers() {
        return List.of(
            new Layer(texture("b_" + style + "_" +  material), PLAIN ),
            new Layer(texture("s_" + style + "_shatterglass"), TINTED),
            new Layer(texture("l_" + style + "_" +  left    ), PLAIN ),
            new Layer(texture("r_" + style + "_" +  right   ), PLAIN )
        );
    }

    private static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath(Noxlyte.MODID, "item/" + name);
    }
}
