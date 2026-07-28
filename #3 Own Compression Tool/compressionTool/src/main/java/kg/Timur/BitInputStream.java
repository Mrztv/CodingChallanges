package kg.Timur;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream implements AutoCloseable{
    private final InputStream in;
    private int currentByte;
    private int numBits;

    public BitInputStream(InputStream in) {
        this.in = in;
        currentByte = 0;
        numBits = 0;
    }

    public int read() throws IOException {
        if (numBits == 0){
            currentByte = in.read();
            numBits = 8;
        }
        int bit = (currentByte>>(numBits-1)) & 1;
        numBits--;
        return bit;
    }

    @Override
    public void close() throws Exception {
        currentByte = 0;
        numBits = 0;
        in.close();
    }
}