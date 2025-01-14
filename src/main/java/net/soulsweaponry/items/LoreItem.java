package net.soulsweaponry.items;

import java.util.List;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class LoreItem extends Item {

    private final String name;
    private final int linesOfLore;

    public LoreItem(Settings settings, String name, int linesOfLore) {
        super(settings);
        this.name = name;
        this.linesOfLore = linesOfLore;
    }

    public String getIdName() {
        return this.name;
    }
    
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (Screen.hasControlDown()) {
            for (int i = 1; i < linesOfLore + 1; i++) {
                tooltip.add(Text.translatable("tooltip.soulsweapons." + this.name + ".part_" + i).formatted(Formatting.DARK_GRAY));
            }
        } else {
            tooltip.add(Text.translatable("tooltip.soulsweapons.control"));
        }
    }
}
