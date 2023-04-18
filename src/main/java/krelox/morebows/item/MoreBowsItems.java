package krelox.morebows.item;

import krelox.morebows.MoreBows;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumSet;

public class MoreBowsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreBows.MODID);

    public static final RegistryObject<CustomBowItem> STONE_BOW = ITEMS.register("stone_bow", () ->
            new CustomBowItem(CustomBowItem.BowType.STONE, new Item.Properties().durability(CustomBowItem.BowType.STONE.durability)));
    public static final RegistryObject<CustomBowItem> IRON_BOW = ITEMS.register("iron_bow", () ->
            new CustomBowItem(CustomBowItem.BowType.IRON, new Item.Properties().durability(CustomBowItem.BowType.IRON.durability)));
    public static final RegistryObject<CustomBowItem> GOLD_BOW = ITEMS.register("gold_bow", () ->
            new CustomBowItem(CustomBowItem.BowType.GOLD, new Item.Properties().durability(CustomBowItem.BowType.GOLD.durability)));
    public static final RegistryObject<CustomBowItem> DIAMOND_BOW = ITEMS.register("diamond_bow", () ->
            new CustomBowItem(CustomBowItem.BowType.DIAMOND, new Item.Properties().durability(CustomBowItem.BowType.DIAMOND.durability)));
    public static final RegistryObject<CustomBowItem> ENDER_BOW = ITEMS.register("ender_bow", () ->
            new CustomBowItem(CustomBowItem.BowType.ENDER, new Item.Properties().durability(CustomBowItem.BowType.ENDER.durability)));
    public static final RegistryObject<CustomBowItem> FLAME_BOW = ITEMS.register("flame_bow", () ->
            new CustomBowItem(CustomBowItem.BowType.FLAME, new Item.Properties().durability(CustomBowItem.BowType.FLAME.durability)));
    public static final RegistryObject<CustomBowItem> FROST_BOW = ITEMS.register("frost_bow", () ->
            new CustomBowItem(CustomBowItem.BowType.FROST, new Item.Properties().durability(CustomBowItem.BowType.FROST.durability)));
    public static final RegistryObject<CustomBowItem> MULTI_BOW = ITEMS.register("multi_bow", () ->
            new CustomBowItem(CustomBowItem.BowType.MULTI, new Item.Properties().durability(CustomBowItem.BowType.MULTI.durability)));
}
