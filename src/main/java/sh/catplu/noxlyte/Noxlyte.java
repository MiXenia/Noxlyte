package sh.catplu.noxlyte;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import sh.catplu.noxlyte.block.ModBlocks;
import sh.catplu.noxlyte.item.ModCreativeModeTabs;
import sh.catplu.noxlyte.item.ModItems;

@Mod(Noxlyte.MODID)
public class Noxlyte {
    public static final String MODID = "noxlyte";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Noxlyte(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Calling on Lovestitch...");
        ModItems.register(modEventBus);
        LOGGER.info("Heartstring Tied!");
        ModBlocks.register(modEventBus);
        LOGGER.info("Fate Established!");
        ModCreativeModeTabs.register(modEventBus);
        LOGGER.info("Sending Gifts to 'Minecraft NeoForge'...");
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() ->
            ((FlowerPotBlock) Blocks.FLOWER_POT)
            .addPlant(ModBlocks.FRAILILY.getId(), ModBlocks.POTTED_FRAILILY)
        );
    }
}