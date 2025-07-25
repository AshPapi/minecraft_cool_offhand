package net.ashpapi.cool_offhand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class OffhandBlockerCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("offhandblocker")
                .then(Commands.literal("list")
                        .executes(context -> {
                            var list = OffhandConfig.BLOCKED_ITEMS.get();
                            if (list.isEmpty()) {
                                context.getSource().sendSuccess(() ->
                                        Component.translatable("message.offhandblocker.list.empty"), false);
                            } else {
                                context.getSource().sendSuccess(() ->
                                        Component.translatable("message.offhandblocker.list.title"), false);
                                for (String id : list) {
                                    ResourceLocation loc = ResourceLocation.tryParse(id);
                                    if (loc != null && ForgeRegistries.ITEMS.containsKey(loc)) {
                                        var item = ForgeRegistries.ITEMS.getValue(loc);
                                        assert item != null;
                                        var name = new ItemStack(item).getHoverName().getString();
                                        context.getSource().sendSuccess(() ->
                                                Component.literal("§8- §f" + name + " (§7" + id + "§f)"), false);
                                    } else {
                                        context.getSource().sendSuccess(() ->
                                                Component.translatable("message.offhandblocker.not_found", id), false);
                                    }
                                }
                            }
                            return 1;
                        }))
                .then(Commands.literal("add")
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .executes(context -> {
                                    String name = StringArgumentType.getString(context, "name").toLowerCase();
                                    var match = ForgeRegistries.ITEMS.getValues().stream()
                                            .filter(item -> new ItemStack(item).getHoverName().getString().toLowerCase().equals(name))
                                            .findFirst();
                                    if (match.isPresent()) {
                                        OffhandConfig.addBlockedItem(match.get());
                                        context.getSource().sendSuccess(() ->
                                                Component.translatable("message.offhandblocker.added_name", new ItemStack(match.get()).getHoverName().getString()), true);
                                        return 1;
                                    } else {
                                        context.getSource().sendFailure(Component.translatable("message.offhandblocker.not_found", name));
                                        return 0;
                                    }
                                })))
                .then(Commands.literal("remove")
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .executes(context -> {
                                    String name = StringArgumentType.getString(context, "name").toLowerCase();
                                    var match = ForgeRegistries.ITEMS.getValues().stream()
                                            .filter(item -> new ItemStack(item).getHoverName().getString().toLowerCase().equals(name))
                                            .findFirst();
                                    if (match.isPresent()) {
                                        OffhandConfig.removeBlockedItem(match.get());
                                        context.getSource().sendSuccess(() ->
                                                Component.translatable("message.offhandblocker.removed_name", new ItemStack(match.get()).getHoverName().getString()), true);
                                        return 1;
                                    } else {
                                        context.getSource().sendFailure(Component.translatable("message.offhandblocker.not_found", name));
                                        return 0;
                                    }
                                })))
        );
    }
}
