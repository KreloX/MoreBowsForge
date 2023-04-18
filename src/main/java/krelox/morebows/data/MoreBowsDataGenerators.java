package krelox.morebows.data;

import krelox.morebows.MoreBows;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreBows.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoreBowsDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // Client
        generator.addProvider(event.includeClient(), new MoreBowsItemModelProvider(packOutput, existingFileHelper));

        // Server
        generator.addProvider(event.includeServer(), new MoreBowsRecipeProvider(packOutput));
    }
}
