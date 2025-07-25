package net.ashpapi.cool_offhand;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OffhandConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BLOCKED_ITEMS;

    static {
        BUILDER.push("offhand");

        BLOCKED_ITEMS = BUILDER
                .comment("List of blocked items in offhand (format: 'minecraft:torch')")
                .defineList(
                        "blockedItems",
                        List.of(
                                "minecraft:torch",
                                "minecraft:soul_torch",
                                "minecraft:lantern",
                                "minecraft:soul_lantern"
                        ),

                        obj -> obj instanceof String
                );
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static boolean isBlocked(Item item) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
        if (itemId == null) return false;
        Set<String> blockedSet = BLOCKED_ITEMS.get().stream().collect(Collectors.toSet());
        return blockedSet.contains(itemId.toString());
    }

    public static void addBlockedItem(Item item) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
        if (itemId == null) return;
        List<String> list = List.copyOf(BLOCKED_ITEMS.get());
        if (!list.contains(itemId.toString())) {
            var newList = new java.util.ArrayList<>(list);
            newList.add(itemId.toString());
            BLOCKED_ITEMS.set(newList);
        }
    }

    public static void removeBlockedItem(Item item) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
        if (itemId == null) return;
        List<String> list = List.copyOf(BLOCKED_ITEMS.get());
        if (list.contains(itemId.toString())) {
            var newList = new java.util.ArrayList<>(list);
            newList.remove(itemId.toString());
            BLOCKED_ITEMS.set(newList);
        }
    }
}
