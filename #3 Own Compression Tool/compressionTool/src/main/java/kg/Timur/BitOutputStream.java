package kg.Timur;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream implements AutoCloseable {

    private final OutputStream out;
    private int currentByte;
    private int numBits;

    public BitOutputStream(OutputStream out) {
        this.out = out;
        currentByte = 0;
        numBits = 0;
    }

    public void writeBit(int bit) throws IOException {
        bit &= 1;
        currentByte = (currentByte<<1) | bit;
        numBits++;

        if (numBits == 8) {
            out.write(currentByte);
            currentByte = 0;
            numBits = 0;
        }
    }

    public void flush() throws IOException{
        if (numBits > 0) {
            int shift = 8 - numBits;
            currentByte = currentByte << shift;
            out.write(currentByte);
            numBits = 0;
            currentByte = 0;
        }
        out.flush();
    }

    @Override
    public void close() throws Exception {
        flush();
        out.close();
    }
}