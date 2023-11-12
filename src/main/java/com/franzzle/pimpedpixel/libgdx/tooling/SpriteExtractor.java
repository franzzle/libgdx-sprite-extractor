package com.franzzle.pimpedpixel.libgdx.tooling;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class SpriteExtractor extends ApplicationAdapter {
    private final Runnable runnable;
    public SpriteExtractor(Runnable runnable){
        this.runnable = runnable;
    }

    @Override
    public void create() {
        // Your Libgdx initialization code, if needed
    }

    public void dumpSprites(TextureAtlas textureAtlas) {
        final Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();

        for (TextureAtlas.AtlasRegion textureRegion : regions) {
            final String regionName = textureRegion.name;

            // Ensure the texture is loaded for each region
            Texture texture = textureRegion.getTexture();
            if (!texture.getTextureData().isPrepared()) {
                texture.getTextureData().prepare();
            }

            System.out.println(String.format("x : %d, y: %d,region width : %d, region height : %d",
                    textureRegion.getRegionX(),
                    textureRegion.getRegionY(),
                    textureRegion.getRegionWidth(),
                    textureRegion.getRegionHeight()
            ));
            // Create a Pixmap from the texture region
            Pixmap pixmap = new Pixmap(
                    textureRegion.getRegionWidth(),
                    textureRegion.getRegionHeight(),
                    Pixmap.Format.RGBA8888 // Use RGBA8888 format for color images
            );

            // Draw the texture region onto the Pixmap
            final Pixmap consumedPixmap = texture.getTextureData().consumePixmap();
            pixmap.drawPixmap(
                    consumedPixmap,
                    0,
                    0,
                    textureRegion.getRegionX(), // Source coordinates (entire pixmap)
                    textureRegion.getRegionY(),
                    textureRegion.getRegionWidth(),
                    textureRegion.getRegionHeight() // Width and height of the region
            );

            String fileName = String.format("dump/%s.png", regionName);
            PixmapIO.writePNG(Gdx.files.local(fileName), pixmap);

            // Dispose of the Pixmap to avoid memory leaks
            pixmap.dispose();
            consumedPixmap.dispose();
        }
        runnable.run();
    }
}
