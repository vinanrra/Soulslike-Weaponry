package net.soulsweaponry.items;

import net.fabric_extras.ranged_weapon.api.RangedConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.entity.projectile.MoonlightArrow;
import net.soulsweaponry.entity.projectile.invisible.ArrowStormEntity;
import net.soulsweaponry.registry.EntityRegistry;
import net.soulsweaponry.util.IKeybindAbility;
import net.soulsweaponry.util.WeaponUtil;

import java.util.function.Supplier;

public class DarkmoonLongbow extends ModdedBow implements IKeybindAbility {

    public DarkmoonLongbow(Settings settings, Supplier<Ingredient> repairIngredientSupplier) {
        super(settings, repairIngredientSupplier);
        this.addTooltipAbility( WeaponUtil.TooltipAbilities.SLOW_PULL, WeaponUtil.TooltipAbilities.MOONLIGHT_ARROW, WeaponUtil.TooltipAbilities.ARROW_STORM);
        this.configure(new RangedConfig(ConfigConstructor.darkmoon_longbow_pull_time_ticks, ConfigConstructor.darkmoon_longbow_damage, ConfigConstructor.darkmoon_longbow_max_velocity));
    }

    @Override
    public boolean isFireproof() {
        return ConfigConstructor.is_fireproof_darkmoon_longbow;
    }

    @Override
    public PersistentProjectileEntity getModifiedProjectile(World world, ItemStack bowStack, ItemStack arrowStack, LivingEntity shooter, PersistentProjectileEntity originalArrow) {
        MoonlightArrow projectile = new MoonlightArrow(world, shooter);
        projectile.setPierceLevel((byte) 4);
        projectile.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        return projectile;
    }

    @Override
    public void useKeybindAbilityServer(ServerWorld world, ItemStack stack, PlayerEntity player) {
        if (!player.getItemCooldownManager().isCoolingDown(this)) {
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.PLAYERS, 1f, 1f);
            ArrowStormEntity entity = new ArrowStormEntity(EntityRegistry.ARROW_STORM_ENTITY, world);
            entity.setPos(player.getX(), player.getY() + 4.5F, player.getZ());
            entity.setVelocity(player, 0, player.getYaw(), 0.0F, 1f, 1.0F);
            entity.setOwner(player);
            double power = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
            entity.setDamage(ConfigConstructor.darkmoon_longbow_ability_damage / 2.6f + power * 1.25f);
            entity.setMaxArrowAge(40);
            world.spawnEntity(entity);
            this.applyItemCooldown(player, Math.max(ConfigConstructor.darkmoon_longbow_ability_min_cooldown_ticks,
                    ConfigConstructor.darkmoon_longbow_ability_cooldown_ticks - this.getReduceCooldownEnchantLevel(stack) * 30));
            stack.damage(3, player, (p_220045_0_) -> p_220045_0_.sendToolBreakStatus(player.getActiveHand()));
        }
    }

    @Override
    public void useKeybindAbilityClient(ClientWorld world, ItemStack stack, ClientPlayerEntity player) {
    }

    @Override
    public boolean isDisabled(ItemStack stack) {
        return ConfigConstructor.disable_use_darkmoon_longbow;
    }

    @Override
    public boolean canEnchantReduceCooldown(ItemStack stack) {
        return ConfigConstructor.darkmoon_longbow_enchant_reduces_cooldown;
    }

    @Override
    public String getReduceCooldownEnchantId(ItemStack stack) {
        return ConfigConstructor.darkmoon_longbow_enchant_reduces_cooldown_id;
    }
}