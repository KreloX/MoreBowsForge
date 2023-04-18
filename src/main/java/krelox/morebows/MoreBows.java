package krelox.morebows;

import krelox.morebows.client.render.EmptyEntityRenderer;
import krelox.morebows.config.MoreBowsConfig;
import krelox.morebows.entity.ArrowSpawner;
import krelox.morebows.item.CustomBowItem;
import krelox.morebows.item.MoreBowsItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(MoreBows.MODID)
public class MoreBows {
    public static final String MODID = "morebows";

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<Entity>> ARROW_SPAWNER = ENTITY_TYPES.register("arrow_spawner", () ->
            EntityType.Builder.of(ArrowSpawner::new, MobCategory.MISC)
                    .sized(0.0F, 0.0F)
                    .build(new ResourceLocation(MODID, "arrow_spawner").toString()));

    public MoreBows() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ENTITY_TYPES.register(bus);
        MoreBowsItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MoreBowsConfig.SPEC, "morebows.toml");

        bus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.COMBAT) {
            MoreBowsItems.ITEMS.getEntries().forEach(item -> event.accept(item));
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ARROW_SPAWNER.get(), EmptyEntityRenderer::new);

            MoreBowsItems.ITEMS.getEntries().forEach(item -> {
                ItemProperties.register(item.get(), new ResourceLocation("pull"), (stack, level, entity, p_174638_) -> {
                    if (entity == null) {
                        return 0.0F;
                    } else {
                        return entity.getUseItem() != stack ? 0.0F
                                : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / ((CustomBowItem) entity.getUseItem().getItem()).bowType.drawDuration;
                    }
                });
                ItemProperties.register(item.get(), new ResourceLocation("pulling"), (stack, level, entity, p_174633_) ->
                        entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
            });
        }
    }
}
