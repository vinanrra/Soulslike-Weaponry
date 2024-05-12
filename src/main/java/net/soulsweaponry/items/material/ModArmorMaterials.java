package net.soulsweaponry.items.material;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.soulsweaponry.config.CommonConfig;
import net.soulsweaponry.registry.ItemRegistry;

public enum ModArmorMaterials implements ArmorMaterial {
    //Boots are on the left
    CHAOS_ARMOR("chaos_armor", new int[]{500, 600, 700, 500}, CommonConfig.CHAOS_ARMOR_ARMOR_POINTS.get().stream().mapToInt(Integer::intValue).toArray(), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0f, 0.1f, Ingredient.ofItems(ItemRegistry.MOONSTONE.get(), Items.NETHERITE_INGOT)),
    CHAOS_SET("chaos_set", new int[]{400, 500, 600, 400}, CommonConfig.CHAOS_SET_ARMOR_POINTS.get().stream().mapToInt(Integer::intValue).toArray(), 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0f, 0f, Ingredient.ofItems(ItemRegistry.MOONSTONE.get())),
    SOUL_INGOT("soul_ingot", new int[]{350, 410, 480, 380}, CommonConfig.SOUL_INGOT_ARMOR_POINTS.get().stream().mapToInt(Integer::intValue).toArray(), 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f, 0f, Ingredient.ofItems(ItemRegistry.SOUL_INGOT.get())),
    SOUL_ROBES("soul_robes", new int[]{150, 210, 280, 180}, CommonConfig.SOUL_ROBES_ARMOR_POINTS.get().stream().mapToInt(Integer::intValue).toArray(), 30, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0f, 0f, Ingredient.ofItems(ItemRegistry.SOUL_INGOT.get())),
    FORLORN_ARMOR("forlorn", new int[]{380, 440, 510, 320}, CommonConfig.FORLORN_ARMOR_POINTS.get().stream().mapToInt(Integer::intValue).toArray(), 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f, 0f, Ingredient.ofItems(ItemRegistry.SOUL_INGOT.get()));

    private final String name;
    private final int[] baseDurability;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredientSupplier;

    ModArmorMaterials(String name, int[] baseDurability, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Ingredient repairIngredientSupplier) {
        this.name = name;
        this.baseDurability = baseDurability;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = repairIngredientSupplier;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return this.baseDurability[slot.getEntitySlotId()];
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return this.protectionAmounts[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
