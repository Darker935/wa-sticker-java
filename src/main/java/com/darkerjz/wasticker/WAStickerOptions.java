package com.darkerjz.wasticker;

import java.awt.*;

public record WAStickerOptions(com.darkerjz.wasticker.WAStickerOptions.ScaleType scaleType, int width, int height,
                               float quality, Color backgroundColor) {
    public static final WAStickerOptions DEFAULT = new WAStickerOptions.Builder().build();

    public enum ScaleType {
        /**
         * Draws the image starting from the top-left corner of the view, cropping the image if the image is larger than the view.
         */
        MATRIX,
        /**
         * Center the image in the view, but perform no scaling.
         */
        CENTER,
        /**
         * Center the image in the view, but perform no scaling.
         */
        CENTER_CROP,
        /**
         * Scale the image uniformly (maintain the image's aspect ratio) so that both dimensions (width and height) of the image will be equal to or larger than the corresponding dimension of the view (minus padding).
         */
        CENTER_INSIDE,
        /**
         * Scale the image uniformly, as the same way the CENTER_INSIDE.
         */
        FIT_CENTER,
        /**
         * Scale the image uniformly, and align the image to the top-parent view.
         */
        FIT_START,
        /**
         * Scale the image uniformly, and align the image to the bottom-parent view.
         */
        FIT_END,
        /**
         * Scale the image to fill the view, resizing the image's width and height to match the view's size.
         */
        FIT_XY
    }

    public static class Builder {
        private ScaleType scaleType = ScaleType.CENTER;
        private int width = 512;
        private int height = 512;
        private float quality = 1.0f;
        private Color backgroundColor = new Color(0, 0, 0, 0);

        public Builder scaleType(ScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder quality(float quality) {
            this.quality = Math.max(0.0f, Math.min(1.0f, quality));
            return this;
        }

        public Builder backgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public WAStickerOptions build() {
            return new WAStickerOptions(scaleType, width, height, quality, backgroundColor);
        }
    }
}
