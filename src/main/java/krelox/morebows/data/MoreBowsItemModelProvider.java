package krelox.morebows.data;

import krelox.morebows.MoreBows;
import krelox.morebows.item.CustomBowItem;
import krelox.morebows.item.MoreBowsItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class MoreBowsItemModelProvider extends ItemModelProvider {
    public MoreBowsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MoreBows.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        MoreBowsItems.ITEMS.getEntries().forEach(item -> {
            for (int i = 0; i < 3; i++) {
                bowPulling(item, i);
            }
            bowItem(item);
        });
    }

    private ItemModelBuilder bowPulling(RegistryObject<Item> item, int i) {
        return withExistingParent(item.getId().getPath() + "_pulling_" + i, this.mcLoc("item/bow")).texture("layer0",
                new ResourceLocation(MoreBows.MODID, "item/" + item.getId().getPath() + "_pulling_" + i));
    }

    private ItemModelBuilder.OverrideBuilder bowItem(RegistryObject<Item> item) {
        float maxPull = ((CustomBowItem) item.get()).bowType.drawDuration / 20 / 100;
        return withExistingParent(item.getId().getPath(), this.mcLoc("item/bow"))
                .texture("layer0", new ResourceLocation(MoreBows.MODID, "item/" + item.getId().getPath()))
                .override().predicate(new ResourceLocation("pulling"), 1).model(bowPulling(item, 0)).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), maxPull * 65).model(bowPulling(item, 1)).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), maxPull * 90).model(bowPulling(item, 2));
    }
}
