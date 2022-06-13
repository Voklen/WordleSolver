import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

public class RandomAccessFileReader {
	public static final int wordLength = 5;
	private static final long PAGE_SIZE = Integer.MAX_VALUE;
	private List<MappedByteBuffer> buffers = new ArrayList<MappedByteBuffer>();

	RandomAccessFileReader(FileChannel channel) throws IOException {
		long start = 0, length = 0;
		for (long index = 0; start + length < channel.size(); index++) {
			if ((channel.size() / PAGE_SIZE) == index)
				length = (channel.size() - index * PAGE_SIZE);
			else
				length = PAGE_SIZE;
			start = index * PAGE_SIZE;
			buffers.add((int) index, channel.map(MapMode.READ_ONLY, start, length));
		}
	}

	public Character getChar(long bytePosition) {
		int page = (int) (bytePosition / PAGE_SIZE);
		int index = (int) (bytePosition % PAGE_SIZE);
		byte raw = buffers.get(page).get(index);
		return (char) raw;
	}
}