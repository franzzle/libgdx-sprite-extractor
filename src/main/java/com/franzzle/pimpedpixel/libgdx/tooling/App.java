package com.franzzle.pimpedpixel.libgdx.tooling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        if(args.length > 0){
            final String spritesheetName = args[0];
            final String libgdxSpritesheetFileTxtPath = String.format("%s.txt", spritesheetName);
            final String libgdxSpritesheetFilePngPath = String.format("%s.png", args[0]);

            Path pathToTextureAtlasFile = Path.of(libgdxSpritesheetFileTxtPath);
            if(!pathToTextureAtlasFile.toFile().exists()){
                System.err.println("Usage: java App animationname without extension, e.g. borisjohnsonAnimations");
                System.exit(1);
            }
            if(!Path.of(libgdxSpritesheetFilePngPath).toFile().exists()){
                System.err.println("Usage: png with prefix 'animationName' is not present");
                System.exit(1);
            }

            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            final SpriteExtractor spriteExtractor = new SpriteExtractor(() -> System.exit(0));
            new HeadlessApplication(spriteExtractor, config);

            Gdx.gl = new MockGL20();

            final String absolutePath = pathToTextureAtlasFile.toAbsolutePath().toString();
            final FileHandle textureAtlasFileHandle = Gdx.files.absolute(absolutePath);
            final TextureAtlas textureAtlas = new TextureAtlas(textureAtlasFileHandle);
            System.out.println("Dumping sprites...");
            spriteExtractor.dumpSprites(spritesheetName, textureAtlas);
        }
        System.out.println("Usage: 'java -jar libgdx-sprite-extractor.jar borisjohnsonAnimations' ");
    }
}
