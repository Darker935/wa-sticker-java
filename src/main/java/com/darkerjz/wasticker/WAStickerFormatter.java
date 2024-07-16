package com.darkerjz.wasticker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

public class WAStickerFormatter {
    private final BufferedImage src;
    private final WAStickerOptions options;

    public WAStickerFormatter(BufferedImage src, WAStickerOptions options) {
        this.src = src;
        this.options = options;

    }

    public Optional<WAStickerData> convertImageToWebp() {
        try {
            var outputStream = new ByteArrayOutputStream();
            var outputImage = (BufferedImage) switch (options.scaleType()) {
                case MATRIX -> matrix();
                case FIT_XY -> fitXY();
                case FIT_END -> fitEnd();
                case FIT_START -> fitStart();
                case FIT_CENTER -> fitCenter();
                case CENTER, CENTER_CROP -> centerCrop();
                case CENTER_INSIDE -> centerInside();
            };
            if (outputImage != null) {
                // Writing the new data to the output stream
                ImageIO.write(outputImage, "webp", outputStream);
                var stickerData = new WAStickerData(
                        outputStream.toByteArray(),
                        outputImage.getWidth(),
                        outputImage.getHeight(),
                        "image/webp"
                );
                return Optional.of(stickerData);
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private BufferedImage fitCenter() {
        var background = getBackgroundARGB();
        var g2d = background.createGraphics();
        g2d.drawImage(src, (background.getWidth() / 2) - (src.getWidth() / 2), (background.getHeight() / 2) - (src.getHeight() / 2), null);
        g2d.dispose();
        return background;
    }

    private BufferedImage fitXY() {
        return src;
    }

    private BufferedImage fitEnd() {
        var background = getBackgroundARGB();
        var g2d = background.createGraphics();
        g2d.drawImage(src, background.getWidth() - src.getWidth(), background.getHeight() - src.getHeight(), null);
        g2d.dispose();
        return background;
    }

    private BufferedImage fitStart() {
        var background = getBackgroundARGB();
        var g2d = background.createGraphics();
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();
        return background;
    }

    private BufferedImage getBackgroundARGB() {
        var maxDimension = Math.max(src.getWidth(), src.getHeight());
        return new BufferedImage(maxDimension, maxDimension, BufferedImage.TYPE_INT_ARGB);
    }

    private BufferedImage centerInside() {
        var background = getBackgroundARGB();
        var g2d = background.createGraphics();
        g2d.drawImage(src, (background.getWidth() / 2) - (src.getWidth() / 2), (background.getHeight() / 2) - (src.getHeight() / 2), null);
        g2d.dispose();
        return background;
    }

    private BufferedImage centerCrop() {
        var minimumDimension = Math.min(src.getWidth(), src.getHeight());
        var maxDimension = Math.max(src.getWidth(), src.getHeight());
        var width = src.getWidth() > src.getHeight() ? Math.negateExact(src.getWidth() / 2) : 0;
        var height = src.getHeight() > src.getWidth() ? Math.negateExact(src.getHeight() / 2) : 0;

        var background = new BufferedImage(minimumDimension, minimumDimension, BufferedImage.TYPE_INT_ARGB);
        var g2d = background.createGraphics();
        g2d.drawImage(src, width, height, null);
        g2d.dispose();
        return background;
    }

    private BufferedImage matrix() {
        var minimumDimension = Math.min(src.getWidth(), src.getHeight());
        var background = new BufferedImage(minimumDimension, minimumDimension, BufferedImage.TYPE_INT_ARGB);
        var g2d = background.createGraphics();
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();
        return background;
    }
}
