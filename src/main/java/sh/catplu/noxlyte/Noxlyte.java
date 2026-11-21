package sh.catplu.noxlyte;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Noxlyte.MODID)
public class Noxlyte {
    public static final String MODID = "noxlyte";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Noxlyte(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Calling on Lovestitch...");
    }
}