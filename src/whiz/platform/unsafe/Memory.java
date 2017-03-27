/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.platform.unsafe;

import ace.constants.BYTES;
import whiz.Whiz;

/**
 * Utility class for working with unsafe memory methods.
 */
public class Memory extends Whiz {

	public static int getAddressSize() {
		return Unsafe.get().addressSize();
	}

	public static int getPageSize() {
		return Unsafe.get().pageSize();
	}

	public static long allocate(final long size) {
		return Unsafe.get().allocateMemory(size);
	}

	public static long reallocate(final long address, final long bytes) {
		return Unsafe.get().reallocateMemory(address, bytes);
	}

	public static void copy(final long originAddress, final long destinationAddress, final long size) {
		Unsafe.get().copyMemory(originAddress, destinationAddress, size);
	}

	public static void writeWithByte(final long address, final long size, final byte value) {
		Unsafe.get().setMemory(address, size, value);
	}

	public static void zero(final long address, final long size) {
		writeWithByte(address, size, BYTES.X00);
	}

	public static byte readByte(final long address) {
		return Unsafe.get().getByte(address);
	}

	public static void free(final long address) {
		Unsafe.get().freeMemory(address);
	}

}
