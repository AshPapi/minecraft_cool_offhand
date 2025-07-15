package net.ashpapi.cool_offhand;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OffhandBlockerMod.MODID)
public class OffhandTorchLanternPlaceBlocker {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        // Проверяем, что рука — offhand
        if (event.getHand() == InteractionHand.OFF_HAND) {
            ItemStack stack = event.getItemStack();
            // Проверяем, что в offhand любой из запрещённых источников света
            if (
                    stack.getItem() == Items.TORCH ||
                            stack.getItem() == Items.SOUL_TORCH ||
                            stack.getItem() == Items.LANTERN ||
                            stack.getItem() == Items.SOUL_LANTERN
            ) {
                event.setCanceled(true);
            }
        }
    }
}
