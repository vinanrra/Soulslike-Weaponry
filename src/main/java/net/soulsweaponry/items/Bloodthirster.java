package net.soulsweaponry.items;

import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.IItemRenderProperties;
import net.soulsweaponry.client.renderer.item.BloodthirsterRenderer;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.util.WeaponUtil;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class Bloodthirster extends ModdedSword implements IAnimatable {

    public AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Bloodthirster(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, ConfigConstructor.bloodthirster_damage, ConfigConstructor.bloodthirster_attack_speed, settings);
        this.addTooltipAbility(WeaponUtil.TooltipAbilities.LIFE_STEAL, WeaponUtil.TooltipAbilities.OVERHEAL);
    }

    @Override
    public boolean isDisabled(ItemStack stack) {
        return ConfigConstructor.disable_use_bloodthirster;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (this.isDisabled(stack)) {
            return super.postHit(stack, target, attacker);
        }
        if (attacker instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                this.applyCooldown(player, Math.max(ConfigConstructor.lifesteal_item_min_cooldown, ConfigConstructor.lifesteal_item_cooldown - this.getReduceCooldownEnchantLevel(stack) * 6));
                float healing = ConfigConstructor.lifesteal_item_base_healing;
                if (ConfigConstructor.lifesteal_item_heal_scales) {
                    healing += MathHelper.ceil(((float)WeaponUtil.getEnchantDamageBonus(stack))/2);
                }
                if (player.getHealth() == player.getMaxHealth() && ConfigConstructor.bloodthirster_overshields) {
                    healing = healing - 4 > 0 ? healing - 4 : 0;
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 60 + MathHelper.floor(healing*10), MathHelper.floor(healing)));
                } else if (player.getHealth() < player.getMaxHealth()) {
                    attacker.heal(healing);
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public boolean isFireproof() {
        return ConfigConstructor.is_fireproof_bloodthirster;
    }

    @Override
    public int getReduceCooldownEnchantLevel(ItemStack stack) {
        if (ConfigConstructor.lifesteal_item_damage_enchant_reduces_cooldown) {
            return WeaponUtil.getEnchantDamageBonus(stack);
        }
        return 0;
    }

    @Override
    public void registerControllers(AnimationData data) {
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            private BloodthirsterRenderer renderer = null;
            // Don't instantiate until ready. This prevents race conditions breaking things
            @Override public BuiltinModelItemRenderer getItemStackRenderer() {
                if (this.renderer == null)
                    this.renderer = new BloodthirsterRenderer();

                return renderer;
            }
        });
    }
}
