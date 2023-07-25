package com.paneedah.mwc.renderer;

import com.paneedah.mwc.capabilities.EquipmentCapability;
import com.paneedah.mwc.equipment.inventory.EquipmentInventory;
import com.paneedah.mwc.items.equipment.carryable.ItemCarryable;
import com.paneedah.weaponlib.ItemVest;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

import static com.paneedah.mwc.proxies.ClientProxy.MC;
import static com.paneedah.mwc.utils.ModReference.ID;

@SideOnly(Side.CLIENT)
public final class EquipmentRenderer implements LayerRenderer<EntityPlayer> {

    private final RenderLivingBase<?> renderer;

    public EquipmentRenderer(RenderLivingBase<?> rendererIn) {
        this.renderer = rendererIn;
    }

    @Override
    public void doRenderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        EquipmentInventory equipmentInventory = EquipmentCapability.getInventory(player);

        if (equipmentInventory == null)
            return;

        ItemStack backpackSlot = equipmentInventory.getStackInSlot(0);
        ItemStack beltSlot = equipmentInventory.getStackInSlot(1);
        ItemStack vestSlot = equipmentInventory.getStackInSlot(2);

        if (!backpackSlot.isEmpty()) {
            final ItemCarryable itemCarryable = (ItemCarryable) backpackSlot.getItem();
            final ModelBiped model = EquipmentModelPool.get(itemCarryable.modelName);


            doEquipmentRender(model, player, new ResourceLocation(ID + ":textures/models/" + itemCarryable.textureName), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }

        if (!beltSlot.isEmpty()) {
            /*final ItemBelt itemBelt = (ItemBelt) beltSlot.getItem();
            final ModelBiped model = EquipmentModelPool.get(itemBelt.model.getClass().getName());

            doEquipmentRender(model, player, new ResourceLocation(ID + ":textures/models/" + itemBelt.textureName), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);*/
        }

        if (!vestSlot.isEmpty()) {
            final ItemVest itemVest = (ItemVest) vestSlot.getItem();
            final ModelBiped model = EquipmentModelPool.get(itemVest.model.getClass().getName());

            doEquipmentRender(model, player, new ResourceLocation(ID + ":textures/models/" + itemVest.textureName), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public void doEquipmentRender(ModelBiped model, EntityPlayer player, ResourceLocation texture, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        final float swingProgress = model.swingProgress;
        final boolean isRiding = model.isRiding;
        final boolean isChild = model.isChild;

        final ModelBiped.ArmPose leftArmPose = model.leftArmPose;
        final ModelBiped.ArmPose rightArmPose = model.rightArmPose;
        final boolean isSneak = model.isSneak;

        MC.getTextureManager().bindTexture(texture);

        model.setModelAttributes(this.renderer.getMainModel());

        model.render(MC.player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        model.swingProgress = swingProgress;
        model.isRiding = isRiding;
        model.isChild = isChild;

        model.leftArmPose = leftArmPose;
        model.rightArmPose = rightArmPose;
        model.isSneak = isSneak;
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}