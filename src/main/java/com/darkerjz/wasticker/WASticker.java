package com.darkerjz.wasticker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class WASticker {
    private final WAStickerOptions options;
    private final BufferedImage bufferedImage;

    /**
     * Constructs a new WASticker with the provided parameters. You can use the {@link WAStickerOptions#DEFAULT} for default options.
     * @param bufferedImage The image to be converted to a sticker.
     * @param options The options to be used when converting the image to a sticker.
     */
    public WASticker(BufferedImage bufferedImage, WAStickerOptions options) {
        this.bufferedImage = bufferedImage;
        this.options = options;
    }

    public WASticker of(byte[] src) {
        try {
            var bufferedImage = ImageIO.read(new ByteArrayInputStream(src));
            return new WASticker(bufferedImage, WAStickerOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WASticker of(ByteArrayInputStream src) {
        try {
            var bufferedImage = ImageIO.read(src);
            return new WASticker(bufferedImage, WAStickerOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WASticker of(BufferedImage src) {
        return new WASticker(src, WAStickerOptions.DEFAULT);
    }

    public byte[] toByteArray() {
        var stickerData = new WAStickerFormatter(bufferedImage, options).convertImageToWebp();
        return stickerData.map(WAStickerData::data).orElseThrow(() -> new RuntimeException("Failed to convert the image to webp"));
    }
}
