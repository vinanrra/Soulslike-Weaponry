package net.soulsweaponry.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.soulsweaponry.entity.AreaEffectSphere;
import net.soulsweaponry.registry.EffectRegistry;
import net.soulsweaponry.registry.ParticleRegistry;
import net.soulsweaponry.registry.SoundRegistry;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class NightSkull extends NonArrowProjectile implements IAnimatable {

    public AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public NightSkull(EntityType<? extends NightSkull> entityType, World world) {
        super(entityType, world);
        this.setDamage(10D);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (entityHitResult.getEntity() instanceof LivingEntity living) {
            living.addStatusEffect(new StatusEffectInstance(EffectRegistry.DECAY.get(), 60, 0));
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 80, 0));
        }
        this.detonate();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.detonate();
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundRegistry.NIGHT_SKULL_DIE.get();
    }

    @Override
    protected boolean canHit(Entity entity) {
        if (entity instanceof ProjectileEntity || this.isOwner(entity)) {
            return false;
        }
        return super.canHit(entity);
    }

    private void detonate() {
        if (!this.getWorld().isClient) {
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.0f, false, destructionType);
            List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(4.0, 2.0, 4.0));
            AreaEffectSphere areaEffectCloudEntity = new AreaEffectSphere(this.getWorld(), this.getX(), this.getY(), this.getZ());
            Entity entity = this.getOwner();
            if (entity instanceof LivingEntity) {
                areaEffectCloudEntity.setOwner((LivingEntity)entity);
            }
            areaEffectCloudEntity.setParticleType(ParticleRegistry.DARK_STAR.get());
            areaEffectCloudEntity.setRadius(0.5f);
            areaEffectCloudEntity.setDuration(80);
            areaEffectCloudEntity.setRadiusGrowth((2.5f - areaEffectCloudEntity.getRadius()) / (float)areaEffectCloudEntity.getDuration());
            areaEffectCloudEntity.addEffect(new StatusEffectInstance(EffectRegistry.DECAY.get(), 60, 0));
            areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.WITHER, 80, 0));
            if (!list.isEmpty()) {
                for (LivingEntity livingEntity : list) {
                    double d = this.squaredDistanceTo(livingEntity);
                    if (!(d < 16.0)) continue;
                    areaEffectCloudEntity.setPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    break;
                }
            }
            this.getWorld().spawnEntity(areaEffectCloudEntity);
            this.discard();
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age > 100) {
            this.detonate();
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    private <E extends IAnimatable> PlayState idle(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "idle", 0, this::idle));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected ItemStack asItemStack() {
        return Items.WITHER_SKELETON_SKULL.getDefaultStack();
    }
}