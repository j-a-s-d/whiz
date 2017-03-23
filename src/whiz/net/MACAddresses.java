/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import ace.constants.STRINGS;
import ace.containers.Lists;
import ace.text.Strings;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import whiz.Whiz;

/**
 * Utility class for working with MAC addresses.
 */
public class MACAddresses extends Whiz {

	public static final String format(final byte[] address) {
		if (address == null) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < address.length; i++) {
			sb.append(String.format("%02X%s", address[i], (i < address.length - 1) ? STRINGS.MINUS : STRINGS.EMPTY));
		}
		return sb.toString();
	}

	public static final boolean isLocal(final String address) {
		try {
			for (final Enumeration<NetworkInterface> item = NetworkInterface.getNetworkInterfaces(); item.hasMoreElements();) {
				if (Strings.sameContent(address, format(item.nextElement().getHardwareAddress()))) {
					return true;
				}
			}
		} catch (final Exception e) {
			GEH.setLastException(e);
		}
		return false;
	}

	public static final List<String> list() {
		try {
			final List<String> result = Lists.make();
			for (final Enumeration<NetworkInterface> item = NetworkInterface.getNetworkInterfaces(); item.hasMoreElements();) {
				final String s = format(item.nextElement().getHardwareAddress());
				if (assigned(s)) {
					result.add(s);
				}
			}
			return result;
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

}
