package net.ashpapi.cool_offhand;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class KeybindHandler {
    public static final KeyMapping BLOCK_ITEM_KEY = new KeyMapping(
            "key.offhandblocker.block",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            "key.categories.offhandblocker"
    );

    public static final KeyMapping UNBLOCK_ITEM_KEY = new KeyMapping(
            "key.offhandblocker.unblock",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key.categories.offhandblocker"
    );

    public static void registerKeybinds() {
        MinecraftForge.EVENT_BUS.register(KeybindHandler.class);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;

        ItemStack offhandItem = mc.player.getOffhandItem();
        if (offhandItem.isEmpty()) return;

        while (BLOCK_ITEM_KEY.consumeClick()) {
            OffhandConfig.addBlockedItem(offhandItem.getItem());
            mc.player.displayClientMessage(Component.translatable("message.offhandblocker.added"), true);
        }

        while (UNBLOCK_ITEM_KEY.consumeClick()) {
            OffhandConfig.removeBlockedItem(offhandItem.getItem());
            mc.player.displayClientMessage(Component.translatable("message.offhandblocker.removed"), true);
        }
    }

    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(BLOCK_ITEM_KEY);
        event.register(UNBLOCK_ITEM_KEY);
    }
}