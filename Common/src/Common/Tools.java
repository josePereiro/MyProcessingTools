package Common;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Tools {

    /**
     * Cut {@code text}, if necessary, to make sure the text fit in the given width.
     * It can even returns "" if any char fit.
     *
     * @param text
     * @param desiredWidth
     * @param context
     * @return
     */
    public static String fixTextLength(String text, float desiredWidth, PApplet context) {
        float textWidth = context.textWidth(text);

        if (textWidth > desiredWidth) {

            //First approach
            int lastCharIndex = (int) (text.length() * desiredWidth / textWidth);

            if (lastCharIndex > 0) {
                //Enhance
                textWidth = context.textWidth(text.substring(0, lastCharIndex));

                if (textWidth < desiredWidth) {
                    while (true) {
                        lastCharIndex++;
                        textWidth = context.textWidth(text.substring(0, lastCharIndex));
                        if (textWidth > desiredWidth) {
                            lastCharIndex--;
                            break;
                        }
                    }
                } else {
                    do {
                        lastCharIndex--;
                        textWidth = context.textWidth(text.substring(0, lastCharIndex));
                    } while (textWidth > desiredWidth);
                }
                return text.substring(0, lastCharIndex);

            } else {
                return "";
            }
        } else {
            return text;
        }
    }

    public static abstract class TimeOscillator {

        private long lastOscillationTime;
        private int interval;
        private int count;
        private boolean oscillationJustHappened;
        private boolean oscillationState;
        private boolean running;

        public TimeOscillator(int period) {
            this.interval = period;
            running = false;
        }

        public boolean isRunning() {
            return running;
        }

        public final void run(boolean startRightNow) {
            if (startRightNow) {
                onOscillationHappened();
                lastOscillationTime = System.currentTimeMillis();
                oscillationJustHappened = true;
                count++;
            } else {
                count = 0;
                this.lastOscillationTime = System.currentTimeMillis();
                oscillationJustHappened = false;
            }
            running = true;
        }

        public final void stop() {
            running = false;
        }

        public final void step() {
            if (System.currentTimeMillis() - lastOscillationTime > interval) {
                oscillationState = !oscillationState;
                onOscillationHappened();
                lastOscillationTime = System.currentTimeMillis();
                oscillationJustHappened = true;
                count++;
            } else {
                oscillationJustHappened = false;
                onOscillationDidNotHappen();
            }
        }

        public abstract void onOscillationHappened();

        public abstract void onOscillationDidNotHappen();

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public long getLastOscillationTime() {
            return lastOscillationTime;
        }

        public int getCount() {
            return count;
        }

        public boolean getOscillationState() {
            return oscillationState;
        }

        public boolean isOscillationJustHappened() {
            return oscillationJustHappened;
        }
    }

    public static class KeyCodes {


            public static final int RIGHT_ARROW = 37;
            public static final int LEFT_ARROW = 39;
            public static final int DOWN_ARROW = 40;
            public static final int CAPS_LOCK = 20;
            public static final int UP_ARROW = 38;
            public static final int COMMAND = 157;
            public static final int CONTROL = 17;
            public static final int OPTION = 18;
            public static final int FUNTION = 0;
            public static final int RETURN = 10;
            public static final int SHIFT = 16;
            public static final int SPACE = 32;
            public static final int DELETE = 8;
            public static final int F12 = 123;
            public static final int F11 = 122;
            public static final int F10 = 121;
            public static final int F1 = 112;
            public static final int F2 = 113;
            public static final int F3 = 114;
            public static final int F4 = 115;
            public static final int F5 = 116;
            public static final int F6 = 117;
            public static final int F7 = 118;
            public static final int F8 = 119;
            public static final int F9 = 120;
            public static final int ESC = 27;
            public static final int TAB = 9;

    }

    public static class KeyScanner {

        private String lastLine = "";
        private final StringBuilder bufferLine;
        private String validCharsRegex = "[0-9a-zA-Z ():;,_./<>!@#$%^&*-=+~`?]";
        private String lastValidChar;
        private boolean newLine;

        public KeyScanner() {
            bufferLine = new StringBuilder();
            newLine = false;
        }

        /**
         * Scan a given char. The scanner use {@link String#matches(java.lang.String validCharRegex)}
         * to check if the given char is valid. If it is not, it will be ignored. {@link PConstants#ENTER} or
         * {@link PConstants#RETURN} are scanned as new line chars and {@link PConstants#BACKSPACE} delete the
         * last valid char scanned.
         *
         * @param key the key to scan
         */
        public boolean scan(char key) {
            if (isLineSeparator(key)) {
                lastLine = bufferLine.toString();
                bufferLine.delete(0, bufferLine.length());
                lastValidChar = "";
                newLine = true;
                return true;
            } else if (checkValidChar(key)) {
                bufferLine.append(key);
                lastValidChar = String.valueOf(key);
                newLine = false;
                return true;
            } else if (isDeleteChar(key)) {
                if (bufferLine.length() > 0) {
                    bufferLine.deleteCharAt(bufferLine.length() - 1);
                }
                lastValidChar = "";
                newLine = false;
                return true;
            } else {
                newLine = false;
                return false;
            }

        }

        private boolean checkValidChar(char key) {
            return String.valueOf(key).matches(validCharsRegex);
        }

        private boolean isLineSeparator(char key) {
            return key == PApplet.ENTER || key == PApplet.RETURN;
        }

        private boolean isDeleteChar(char key) {
            return key == PApplet.BACKSPACE;
        }

        /**
         * Returns the last scanned line. Valid chars are stored in the {@code bufferLine}
         * till a {@link PConstants#RETURN} or a {@link PConstants#ENTER} if scanned.
         *
         * @return
         */
        public String getLastLine() {
            return lastLine;
        }

        /**
         * Returns the current string stored in the scanner buffer. Valid chars are stored in the {@code bufferLine}
         * till a {@link PConstants#RETURN} or a {@link PConstants#ENTER} if scanned. After that, the {@code bufferLine}
         * is reset to {@code ""}.
         *
         * @return
         */
        public String getBufferedLine() {
            return bufferLine.toString();
        }

        /**
         * The scanner use {@link String#matches(java.lang.String validCharRegex)} to check if a scanned char is valid.
         * This setter allows you to change the default {@code validCharRegex}.
         *
         * @param validCharsRegex
         */
        public void setValidCharsRegex(String validCharsRegex) {
            this.validCharsRegex = validCharsRegex;
        }

        /**
         * Returns true if the las scanned char was considered as a new LineWrapper char.
         * Ignored char do not affect this value
         *
         * @return
         */
        public boolean newLineJustHappen() {
            return newLine;
        }

        public String getLastValidChar() {
            return lastValidChar;
        }

        public void clearAll() {
            clearBuffer();
            clearHistory();
        }

        public void clearHistory() {
            lastValidChar = "";
            lastLine = "";
        }

        public void clearBuffer() {
            bufferLine.delete(0, bufferLine.length());
        }
    }

    public static class Zoom extends PObject {

        private float factor = 2.0F;
        private PImage amplifiedImage;
        private int factorizedWidth;
        private int width;
        private int factorizedHeight;
        private int height;
        private float maxFactor = 25;
        private float minFactor = 0.3F;

        public Zoom(int width, int height, PApplet context) {
            super(context);
            this.width = width;
            this.height = height;
            factorizedWidth = Math.round(width / factor);
            factorizedHeight = Math.round(height / factor);
        }


        /**
         * Amplify a area with the center in the given coordinates.
         * The magnified area is a rectangle with dimensions {@code width / factor} x {@code height / factor}.
         *
         * @param centerX the x coordinate of the center
         * @param centerY the y coordinate of the center
         */
        public void magnifyCenteredArea(int centerX, int centerY) {
            magnifyArea(centerX - factorizedWidth / 2, centerY - factorizedHeight / 2);
        }

        /**
         * Amplify a area with the top left corner in the given coordinates.
         * The magnified area is a rectangle with dimensions {@code width / factor} x {@code height / factor}.
         *
         * @param x the x coordinate of the top left corner
         * @param y the y coordinate of the top left corner
         */
        public void magnifyArea(int x, int y) {
            amplifiedImage = context.get(x, y,
                    factorizedWidth, factorizedHeight);
            amplifiedImage.resize(width, height);
        }

        /**
         * Get the current factor of magnification of the zoom
         *
         * @return
         */
        public float getFactor() {
            return factor;
        }

        /**
         * Set the magnification factor of the zoom
         *
         * @param factor
         */
        public void setFactor(float factor) {
            if (factor <= minFactor || factor > maxFactor) {
                return;
            }
            this.factor = factor;
            factorizedWidth = Math.round(width / factor);
            factorizedHeight = Math.round(height / factor);
        }

        public void setWidth(int width) {
            this.width = width;
            factorizedWidth = Math.round(width / factor);
        }

        public void setHeight(int height) {
            this.height = height;
            factorizedHeight = Math.round(height / factor);
        }

        public int getMagnifiedAreaWidth() {
            return factorizedWidth;
        }

        public int getMagnifiedAreaHeight() {
            return factorizedHeight;
        }

        public float getMaxFactor() {
            return maxFactor;
        }

        public void setMaxFactor(float maxFactor) {
            this.maxFactor = maxFactor;
        }

        public float getMinFactor() {
            return minFactor;
        }

        public void setMinFactor(float minFactor) {
            this.minFactor = minFactor;
        }

        public PImage getAmplifiedImage() {
            return amplifiedImage;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
