package sh.catplu.noxlyte.item;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class BuildyItem extends Item {
    private final String loreID;

    public BuildyItem(Properties properties, String loreID) {
        super(properties);
        this.loreID = loreID;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.noxlyte." + loreID));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}