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


            public static final int RIGHT_ARROW = 39;
            public static final int LEFT_ARROW = 37;
            public static final int DOWN_ARROW = 40;
            public static final int CAPS_LOCK = 20;
            public static final int UP_ARROW = 38;
            public static final int COMMAND = 157;
            public static final int CONTROL = 17;
            public static final int OPTION = 18;
            public static final int FUNCTION = 0;
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
            public static final int NUMBER_0 = 48;
            public static final int NUMBER_1 = 49;
            public static final int NUMBER_2 = 50;
            public static final int NUMBER_3 = 51;
            public static final int NUMBER_4 = 52;
            public static final int NUMBER_5 = 53;
            public static final int NUMBER_6 = 54;
            public static final int NUMBER_7 = 55;
            public static final int NUMBER_8 = 56;
            public static final int NUMBER_9 = 57;
            public static final int LETTER_A = 65;
            public static final int LETTER_B = 66;
            public static final int LETTER_C = 67;
            public static final int LETTER_D = 68;
            public static final int LETTER_E = 69;
            public static final int LETTER_F = 70;
            public static final int LETTER_G = 71;
            public static final int LETTER_H = 72;
            public static final int LETTER_I = 73;
            public static final int LETTER_J = 74;
            public static final int LETTER_K = 75;
            public static final int LETTER_L = 76;
            public static final int LETTER_M = 77;
            public static final int LETTER_N = 78;
            public static final int LETTER_O = 79;
            public static final int LETTER_P = 80;
            public static final int LETTER_Q = 81;
            public static final int LETTER_R = 82;
            public static final int LETTER_S = 83;
            public static final int LETTER_T = 84;
            public static final int LETTER_U = 85;
            public static final int LETTER_V = 86;
            public static final int LETTER_W = 87;
            public static final int LETTER_X = 88;
            public static final int LETTER_Y = 89;
            public static final int LETTER_Z = 90;


        public static String toString(int keyCode){

            switch (keyCode) {

                case RIGHT_ARROW:
                    return "RIGHT_ARROW";

                case LEFT_ARROW:
                    return "LEFT_ARROW";

                case DOWN_ARROW:
                    return "DOWN_ARROW";

                case CAPS_LOCK:
                    return "CAPS_LOCK";

                case UP_ARROW:
                    return "UP_ARROW";

                case COMMAND:
                    return "COMMAND";

                case CONTROL:
                    return "CONTROL";

                case OPTION:
                    return "OPTION";

                case FUNCTION:
                    return "FUNCTION";

                case RETURN:
                    return "RETURN";

                case SHIFT:
                    return "SHIFT";

                case SPACE:
                    return "SPACE";

                case DELETE:
                    return "DELETE";

                case F12:
                    return "F12";

                case F11:
                    return "F11";

                case F10:
                    return "F10";

                case F1:
                    return "F1";

                case F2:
                    return "F2";

                case F3:
                    return "F3";

                case F4:
                    return "F4";

                case F5:
                    return "F5";

                case F6:
                    return "F6";

                case F7:
                    return "F7";

                case F8:
                    return "F8";

                case F9:
                    return "F9";

                case ESC:
                    return "ESC";

                case TAB:
                    return "TAB";

                case NUMBER_0:
                    return "NUMBER_0";

                case NUMBER_1:
                    return "NUMBER_1";

                case NUMBER_2:
                    return "NUMBER_2";

                case NUMBER_3:
                    return "NUMBER_3";

                case NUMBER_4:
                    return "NUMBER_4";

                case NUMBER_5:
                    return "NUMBER_5";

                case NUMBER_6:
                    return "NUMBER_6";

                case NUMBER_7:
                    return "NUMBER_7";

                case NUMBER_8:
                    return "NUMBER_8";

                case NUMBER_9:
                    return "NUMBER_9";

                case LETTER_A:
                    return "LETTER_A";

                case LETTER_B:
                    return "LETTER_B";

                case LETTER_C:
                    return "LETTER_C";

                case LETTER_D:
                    return "LETTER_D";

                case LETTER_E:
                    return "LETTER_E";

                case LETTER_F:
                    return "LETTER_F";

                case LETTER_G:
                    return "LETTER_G";

                case LETTER_H:
                    return "LETTER_H";

                case LETTER_I:
                    return "LETTER_I";

                case LETTER_J:
                    return "LETTER_J";

                case LETTER_K:
                    return "LETTER_K";

                case LETTER_L:
                    return "LETTER_L";

                case LETTER_M:
                    return "LETTER_M";

                case LETTER_N:
                    return "LETTER_N";

                case LETTER_O:
                    return "LETTER_O";

                case LETTER_P:
                    return "LETTER_P";

                case LETTER_Q:
                    return "LETTER_Q";

                case LETTER_R:
                    return "LETTER_R";

                case LETTER_S:
                    return "LETTER_S";

                case LETTER_T:
                    return "LETTER_T";

                case LETTER_U:
                    return "LETTER_U";

                case LETTER_V:
                    return "LETTER_V";

                case LETTER_W:
                    return "LETTER_W";

                case LETTER_X:
                    return "LETTER_X";

                case LETTER_Y:
                    return "LETTER_Y";

                case LETTER_Z:
                return "LETTER_Z";



            }

            return "";


        }

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
