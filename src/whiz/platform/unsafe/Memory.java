/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.platform.unsafe;

import ace.constants.BYTES;
import whiz.Whiz;

/**
 * Utility class for working with unsafe memory methods.
 */
public class Memory extends Whiz {

	/**
	 * Gets the address size.
	 * 
	 * @return the address size
	 */
	public static int getAddressSize() {
		return Unsafe.get().addressSize();
	}

	/**
	 * Gets the page size.
	 * 
	 * @return the page size
	 */
	public static int getPageSize() {
		return Unsafe.get().pageSize();
	}

	/**
	 * Allocates a block of memory of the specified size.
	 * 
	 * @param size
	 * @return the address of the allocated block
	 */
	public static long allocate(final long size) {
		return Unsafe.get().allocateMemory(size);
	}

	/**
	 * Reallocates the specified block with the specified size.
	 * 
	 * @param address
	 * @param bytes
	 * @return the address of the reallocated block
	 */
	public static long reallocate(final long address, final long bytes) {
		return Unsafe.get().reallocateMemory(address, bytes);
	}

	/**
	 * Copies from the specified origin address to the specified destination address the specified amount of bytes.
	 * 
	 * @param originAddress
	 * @param destinationAddress
	 * @param size 
	 */
	public static void copy(final long originAddress, final long destinationAddress, final long size) {
		Unsafe.get().copyMemory(originAddress, destinationAddress, size);
	}

	/**
	 * Writes at the specified memory address the specified amount of bytes with the specified byte value.
	 * 
	 * @param address
	 * @param size
	 * @param value 
	 */
	public static void writeWithByte(final long address, final long size, final byte value) {
		Unsafe.get().setMemory(address, size, value);
	}

	/**
	 * Writes at the specified memory address the specified amount of bytes with a zero byte value.
	 * 
	 * @param address
	 * @param size 
	 */
	public static void zero(final long address, final long size) {
		writeWithByte(address, size, BYTES.X00);
	}

	/**
	 * Reads the byte located at the specified address.
	 * 
	 * @param address
	 * @return the read byte
	 */
	public static byte readByte(final long address) {
		return Unsafe.get().getByte(address);
	}

	/**
	 * Frees the memory block at the specified address.
	 * 
	 * @param address 
	 */
	public static void free(final long address) {
		Unsafe.get().freeMemory(address);
	}

}
