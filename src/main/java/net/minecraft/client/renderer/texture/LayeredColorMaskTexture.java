package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.IOException;
import java.util.List;
import net.minecraft.item.DyeColor;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class LayeredColorMaskTexture extends Texture {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ResourceLocation textureLocation;
   private final List<String> listTextures;
   private final List<DyeColor> listDyeColors;

   public LayeredColorMaskTexture(ResourceLocation textureLocationIn, List<String> p_i46101_2_, List<DyeColor> p_i46101_3_) {
      this.textureLocation = textureLocationIn;
      this.listTextures = p_i46101_2_;
      this.listDyeColors = p_i46101_3_;
   }

   public void loadTexture(IResourceManager manager) throws IOException {
      try (
         IResource iresource = manager.getResource(this.textureLocation);
         NativeImage nativeimage = NativeImage.read(iresource.getInputStream());
         NativeImage nativeimage1 = new NativeImage(nativeimage.getWidth(), nativeimage.getHeight(), false);
      ) {
         nativeimage1.copyImageData(nativeimage);

         for(int i = 0; i < 17 && i < this.listTextures.size() && i < this.listDyeColors.size(); ++i) {
            String s = this.listTextures.get(i);
            if (s != null) {
               try (
                  IResource iresource1 = manager.getResource(new ResourceLocation(s));
                  NativeImage nativeimage2 = NativeImage.read(iresource1.getInputStream());
               ) {
                  int j = this.listDyeColors.get(i).func_196057_c();
                  if (nativeimage2.getWidth() == nativeimage1.getWidth() && nativeimage2.getHeight() == nativeimage1.getHeight()) {
                     for(int k = 0; k < nativeimage2.getHeight(); ++k) {
                        for(int l = 0; l < nativeimage2.getWidth(); ++l) {
                           int i1 = nativeimage2.getPixelRGBA(l, k);
                           if ((i1 & -16777216) != 0) {
                              int j1 = (i1 & 255) << 24 & -16777216;
                              int k1 = nativeimage.getPixelRGBA(l, k);
                              int l1 = MathHelper.multiplyColor(k1, j) & 16777215;
                              nativeimage1.blendPixel(l, k, j1 | l1);
                           }
                        }
                     }
                  }
               }
            }
         }

         TextureUtil.prepareImage(this.getGlTextureId(), nativeimage1.getWidth(), nativeimage1.getHeight());
         GlStateManager.pixelTransfer(3357, Float.MAX_VALUE);
         nativeimage1.uploadTextureSub(0, 0, 0, false);
         GlStateManager.pixelTransfer(3357, 0.0F);
      } catch (IOException ioexception) {
         LOGGER.error("Couldn't load layered color mask image", (Throwable)ioexception);
      }

   }
}
