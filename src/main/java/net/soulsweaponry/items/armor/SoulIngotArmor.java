package net.soulsweaponry.items.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.soulsweaponry.config.CommonConfig;
import net.soulsweaponry.registry.ArmorRegistry;

public class SoulIngotArmor extends SetBonusArmor {

    public SoulIngotArmor(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    protected void tickAdditionalSetEffects(ItemStack stack, PlayerEntity player) {}

    @Override
    protected Item getMatchingBoots() {
        return ArmorRegistry.SOUL_INGOT_BOOTS.get();
    }

    @Override
    protected Item getMatchingLegs() {
        return ArmorRegistry.SOUL_INGOT_LEGGINGS.get();
    }

    @Override
    protected Item getMatchingChest() {
        return ArmorRegistry.SOUL_INGOT_CHESTPLATE.get();
    }

    @Override
    protected Item getMatchingHead() {
        return ArmorRegistry.SOUL_INGOT_HELMET.get();
    }

    @Override
    protected StatusEffectInstance[] getFullSetEffects() {
        return new StatusEffectInstance[] {
                new StatusEffectInstance(StatusEffects.RESISTANCE, 40, 0)
        };
    }

    @Override
    protected Text[] getCustomTooltips() {
        return new Text[0];
    }

    @Override
    public boolean isDisabled(ItemStack stack) {
        return CommonConfig.DISABLE_USE_SOUL_INGOT_ARMOR.get();
    }
}
