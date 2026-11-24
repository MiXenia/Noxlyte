package sh.catplu.noxlyte.item;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public final class Parts {
    private Parts() {}

    public static final List<String> SHAPES = List.of(
        "bulb",
        "diamond",
        "duo",
        "dusty",
        "heart",
        "holy",
        "jar",
        "kit",
        "star",
        "vanilla",
        "vial"
    );

    public static final Map<String,Integer> CAPACITY = ImmutableMap.of(
        "glass",   9,
        "emerald", 17,
        "pearl",   33,
        "eye",     65,
        "crystal", 129,
        "end",     257
    );

    public static final List<String> MATERIALS = List.copyOf(CAPACITY.keySet());

    public static final List<String> DUSTS = List.of(
        "amethyst",
        "blaze",
        "crystal",
        "diamond",
        "echo",
        "emerald",
        "end",
        "eye",
        "glass",
        "glowstone",
        "gunpowder",
        "hots",
        "pearl",
        "prismarine",
        "quartz",
        "redstone",
        "snow",
        "star",
        "sugar"
    );

    public static final Map<String, Integer> NUTRITION = ImmutableMap.of(
        "sugar",   2,
        "diamond", 4,
        "quartz",  6,
        "echo",    8,
        "star",    10
    );

    public static final Map<String, Float> SATURATION = ImmutableMap.of(
        "snow",       0.4f,
        "amethyst",   1.2f,
        "blaze",      6.0f,
        "hots",       12.8f,
        "prismarine", 20.0f
    );
}
