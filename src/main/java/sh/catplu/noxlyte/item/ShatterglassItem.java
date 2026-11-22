package sh.catplu.noxlyte.item;

import java.util.List;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import sh.catplu.noxlyte.Noxlyte;

public class ShatterglassItem extends Item {
    public ShatterglassItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (!stack.has(DataComponents.DYED_COLOR)) {
            tooltip.add(Component.translatable("tooltip." + Noxlyte.MODID + ".shatterglass_guide"));
        }
    }
}
