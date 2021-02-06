package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SChangeBlockPacket implements IPacket<IClientPlayNetHandler> {
   private BlockPos pos;
   private BlockState state;

   public SChangeBlockPacket() {
   }

   public SChangeBlockPacket(IBlockReader p_i48982_1_, BlockPos pos) {
      this.pos = pos;
      this.state = p_i48982_1_.getBlockState(pos);
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.pos = buf.readBlockPos();
      this.state = Block.BLOCK_STATE_IDS.getByValue(buf.readVarInt());
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeBlockPos(this.pos);
      buf.writeVarInt(Block.getStateId(this.state));
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleBlockChange(this);
   }

   @OnlyIn(Dist.CLIENT)
   public BlockState getState() {
      return this.state;
   }

   @OnlyIn(Dist.CLIENT)
   public BlockPos getPos() {
      return this.pos;
   }
}
