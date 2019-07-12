package utils;

public class ByteToShortLE {
    public static short byteArrayToShortLE(final byte[] b, final int offset) {
        short value = 0;
        for (int i = 0; i < 2; i++) {
            value |= (b[i + offset] & 0x000000FF) << (i * 8);
        }

        return value;
    }
}
