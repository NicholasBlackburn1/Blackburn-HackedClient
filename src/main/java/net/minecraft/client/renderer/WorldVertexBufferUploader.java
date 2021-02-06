package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.ByteBuffer;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WorldVertexBufferUploader {
   public void draw(BufferBuilder bufferBuilderIn) {
      if (bufferBuilderIn.getVertexCount() > 0) {
         VertexFormat vertexformat = bufferBuilderIn.getVertexFormat();
         int i = vertexformat.getSize();
         ByteBuffer bytebuffer = bufferBuilderIn.getByteBuffer();
         List<VertexFormatElement> list = vertexformat.getElements();

         for(int j = 0; j < list.size(); ++j) {
            VertexFormatElement vertexformatelement = list.get(j);
            VertexFormatElement.Usage vertexformatelement$usage = vertexformatelement.getUsage();
            int k = vertexformatelement.getType().getGlConstant();
            int l = vertexformatelement.getIndex();
            bytebuffer.position(vertexformat.getOffset(j));
            switch(vertexformatelement$usage) {
            case POSITION:
               GlStateManager.vertexPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
               GlStateManager.enableClientState(32884);
               break;
            case UV:
               GLX.glClientActiveTexture(GLX.GL_TEXTURE0 + l);
               GlStateManager.texCoordPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
               GlStateManager.enableClientState(32888);
               GLX.glClientActiveTexture(GLX.GL_TEXTURE0);
               break;
            case COLOR:
               GlStateManager.colorPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
               GlStateManager.enableClientState(32886);
               break;
            case NORMAL:
               GlStateManager.normalPointer(k, i, bytebuffer);
               GlStateManager.enableClientState(32885);
            }
         }

         GlStateManager.drawArrays(bufferBuilderIn.getDrawMode(), 0, bufferBuilderIn.getVertexCount());
         int i1 = 0;

         for(int j1 = list.size(); i1 < j1; ++i1) {
            VertexFormatElement vertexformatelement1 = list.get(i1);
            VertexFormatElement.Usage vertexformatelement$usage1 = vertexformatelement1.getUsage();
            int k1 = vertexformatelement1.getIndex();
            switch(vertexformatelement$usage1) {
            case POSITION:
               GlStateManager.disableClientState(32884);
               break;
            case UV:
               GLX.glClientActiveTexture(GLX.GL_TEXTURE0 + k1);
               GlStateManager.disableClientState(32888);
               GLX.glClientActiveTexture(GLX.GL_TEXTURE0);
               break;
            case COLOR:
               GlStateManager.disableClientState(32886);
               GlStateManager.clearCurrentColor();
               break;
            case NORMAL:
               GlStateManager.disableClientState(32885);
            }
         }
      }

      bufferBuilderIn.reset();
   }
}
