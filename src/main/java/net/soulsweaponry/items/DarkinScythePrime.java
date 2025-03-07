package net.soulsweaponry.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.MathHelper;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.util.WeaponUtil;

public class DarkinScythePrime extends UmbralTrespassItem {

    public DarkinScythePrime(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, ConfigConstructor.darkin_scythe_damage + ConfigConstructor.darkin_scythe_bonus_damage, ConfigConstructor.darkin_scythe_prime_attack_speed, settings, ConfigConstructor.darkin_scythe_prime_ticks_before_dismount);
        this.addTooltipAbility(WeaponUtil.TooltipAbilities.OMNIVAMP);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (this.isDisabled(stack)) {
            return super.postHit(stack, target, attacker);
        }
        if (attacker instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(stack.getItem()) && !(player.getHealth() >= player.getMaxHealth())) {
                this.applyItemCooldown(player, Math.max(ConfigConstructor.lifesteal_item_min_cooldown, ConfigConstructor.lifesteal_item_cooldown - this.getReduceLifeStealCooldownEnchantLevel(stack) * 6));
                float healing = ConfigConstructor.lifesteal_item_base_healing;
                if (ConfigConstructor.lifesteal_item_heal_scales) {
                    healing += MathHelper.ceil(((float) WeaponUtil.getEnchantDamageBonus(stack))/2);
                }
                attacker.heal(healing);
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public boolean isFireproof() {
        return ConfigConstructor.is_fireproof_darkin_scythe_prime;
    }

    @Override
    public boolean isDisabled(ItemStack stack) {
        return ConfigConstructor.disable_use_darkin_scythe_prime;
    }

    @Override
    public float getAbilityDamage() {
        return ConfigConstructor.darkin_scythe_prime_ability_damage;
    }

    @Override
    public int getAbilityCooldown(ItemStack stack) {
        return Math.max(ConfigConstructor.darkin_scythe_prime_ability_min_cooldown, ConfigConstructor.darkin_scythe_prime_ability_cooldown
                - this.getReduceCooldownEnchantLevel(stack) * 25);
    }

    @Override
    public boolean shouldAbilityHeal() {
        return true;
    }

    @Override
    public boolean canEnchantReduceCooldown(ItemStack stack) {
        return ConfigConstructor.darkin_scythe_prime_ability_enchant_reduces_cooldown;
    }

    @Override
    public String getReduceCooldownEnchantId(ItemStack stack) {
        return ConfigConstructor.darkin_scythe_prime_ability_enchant_reduces_cooldown_id;
    }
}