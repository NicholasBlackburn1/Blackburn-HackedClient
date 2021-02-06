package net.minecraft.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFrameItem extends HangingEntityItem {
   public ItemFrameItem(Item.Properties builder) {
      super(EntityType.ITEM_FRAME, builder);
   }

   protected boolean canPlace(PlayerEntity p_200127_1_, Direction p_200127_2_, ItemStack p_200127_3_, BlockPos p_200127_4_) {
      return !World.isOutsideBuildHeight(p_200127_4_) && p_200127_1_.canPlayerEdit(p_200127_4_, p_200127_2_, p_200127_3_);
   }
}
