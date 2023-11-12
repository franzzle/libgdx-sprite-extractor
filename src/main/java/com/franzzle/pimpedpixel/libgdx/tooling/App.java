package com.franzzle.pimpedpixel.libgdx.tooling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.mockito.Mockito;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        final String localFile = String.format("%s%s.txt", "borisjohnson", "Animations");
        final String file = App.class.getClassLoader().getResource(localFile).getFile();

        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

        final SpriteExtractor spriteExtractor = new SpriteExtractor(() -> System.exit(0));
        new HeadlessApplication(spriteExtractor, config);

        Gdx.gl = Mockito.mock(GL20.class);

        final FileHandle textureAtlasFileHandle = Gdx.files.absolute(file);
        if(textureAtlasFileHandle.exists()){
            final TextureAtlas textureAtlas = new TextureAtlas(textureAtlasFileHandle);
            if(textureAtlas != null){
                System.out.println("YippkajeeMF");
                spriteExtractor.dumpSprites(textureAtlas);
            }
        }
    }
}
