package krelox.morebows.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MoreBowsConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> ENDER_BOW_DELAY;

    static {
        BUILDER.push("More Bows Config");

        ENDER_BOW_DELAY = BUILDER.comment("Delay between the ender bow's first shot and the rest in ticks (20 ticks equals 1 second) [default: 40]")
                .define("Ender Bow Delay", 40);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
