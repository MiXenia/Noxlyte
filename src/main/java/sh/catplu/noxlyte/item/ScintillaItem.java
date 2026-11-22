package sh.catplu.noxlyte.item;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import sh.catplu.noxlyte.Noxlyte;
import sh.catplu.noxlyte.component.ModComponents;

public class ScintillaItem extends Item{
    public static final FoodProperties DEFAULT_FOOD = new FoodProperties(2, 0.4f, true, 1.1f, Optional.empty(), List.of());

    public ScintillaItem(Properties properties) {
        super(properties);
    }

    // food part //

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        int nutrition = stack.getOrDefault(ModComponents.NUTRITION,DEFAULT_FOOD.nutrition());
        float saturation = stack.getOrDefault(ModComponents.SATURATION,DEFAULT_FOOD.saturation());
        return new FoodProperties(nutrition, saturation, true, DEFAULT_FOOD.eatSeconds(), Optional.empty(), List.of());
    }

    @Override 
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player
            && player.getFoodData().needsFood()
            && stack.getDamageValue() < stack.getMaxDamage() - 1) {
                FoodProperties food = stack.getFoodProperties(player);
                if (food != null) {
                    player.getFoodData().eat(food);
                }
                stack.setDamageValue(stack.getDamageValue() + 1);
            }
        return stack;
    }

    @Override 
    public SoundEvent getEatingSound() {
        return SoundEvents.AMETHYST_BLOCK_BREAK;
    }

    // durability bar //

    @Override
    public int getBarColor(ItemStack stack) {
        float used = (float) stack.getDamageValue() / stack.getMaxDamage();
        int full = DyedItemColor.getOrDefault(stack, 0xFFAADD);
        int empy = FastColor.ARGB32.lerp(0.6f,full,0x000000);
        return FastColor.ARGB32.lerp(used,full,empy);
    }

    @Override 
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override 
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.getItem() instanceof ShatterglassItem;
    }

    // rarity //

    public static Rarity rarityOf(ItemStack stack) {
        int   h = stack.getOrDefault(ModComponents.NUTRITION,DEFAULT_FOOD.nutrition());
        float s = stack.getOrDefault(ModComponents.SATURATION,DEFAULT_FOOD.saturation());
        int   d = stack.getMaxDamage();
        if (h >= 9 && s >= 19.9f && d >= 256) return Rarity.EPIC;
        if (h >= 7 && s >= 12.7f && d >=  64) return Rarity.RARE;
        if (h >= 3 && s >=  1.1f && d >=  16) return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }

    @Override 
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(rarityOf(stack).getStyleModifier());
    }

    // tooltip //

    @Override 
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        String left     = stack.get(ModComponents.LEFT_DUST);
        String right    = stack.get(ModComponents.RIGHT_DUST);
        String style    = stack.get(ModComponents.BOTTLE_STYLE);
        String material = stack.get(ModComponents.BOTTLE_MATERIAL);
        if (left  != null) tooltip.add(line("left_dust" ,Component.translatable(itemKey(left +"_dust"))));
        if (right != null) tooltip.add(line("right_dust",Component.translatable(itemKey(right+"_dust"))));
        if (style != null && material != null) tooltip.add(line("bottle",Component.translatable(itemKey(style+"_"+material+"_bottle"))));
        if (Screen.hasShiftDown()) {
            FoodProperties food = getFoodProperties(stack,null);
            tooltip.add(line("nutrition",     Component.literal(String.valueOf(food.nutrition()))));
            tooltip.add(line("saturation",    Component.literal(String.format("%.2f",food.saturation()))));
            tooltip.add(line("max_durability",Component.literal(String.valueOf(stack.getMaxDamage()-1))));
        } else {
            tooltip.add(Component.translatable("tooltip."+Noxlyte.MODID+".expandable").withStyle(ChatFormatting.GRAY));
        }
    }

    private static String itemKey(String id) {
        return "item." + Noxlyte.MODID + "." + id;
    }

    private static Component line(String key, Component value) {
        return Component.translatable("tooltip." + Noxlyte.MODID + "." + key)
            .append(value)
            .withStyle(ChatFormatting.DARK_GRAY);
    }
}
