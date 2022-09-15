package rs117.hd.model;

import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.LinkedHashMap;

class IntBufferCache extends LinkedHashMap<Integer, IntBuffer> {
    private final long byteCapacity;
    private long bytesConsumed;

    private final BufferPool bufferPool;

    public IntBufferCache(long byteCapacity, BufferPool bufferPool) {
        super(512, 0.7f, true);
        this.byteCapacity = byteCapacity;
        this.bytesConsumed = 0;
        this.bufferPool = bufferPool;
    }

    public long getBytesConsumed() {
        return this.bytesConsumed;
    }

    public void put(int key, IntBuffer value) {
        long bytes = value.capacity() * 4L;
        if (this.bytesConsumed + bytes > this.byteCapacity) {
            makeRoom(bytes);
        }

        this.bytesConsumed += bytes;
        super.put(key, value);
    }

    public void makeRoom(long size) {
        Iterator<IntBuffer> iterator = values().iterator();

        long releasedSized = 0;
        while (iterator.hasNext() && releasedSized < size) {
            IntBuffer buffer = iterator.next();
            long releasedBytes = buffer.capacity() * 4L;
            releasedSized += releasedBytes;
            this.bytesConsumed -= releasedBytes;
            this.bufferPool.putIntBuffer(buffer);

            iterator.remove();
        }
    }

    @Override
    public void clear() {
        this.bytesConsumed = 0;
        super.clear();
    }
}
