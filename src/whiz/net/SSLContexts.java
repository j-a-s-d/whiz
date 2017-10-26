/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import ace.text.Strings;
import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import whiz.Whiz;

/**
 * Utility class for working with SSL contexts.
 */
public class SSLContexts extends Whiz {

	public static SSLContext makeTLSContext(final String keystoreFilename, final String password) {
		return makeTLSContext(keystoreFilename, password, null, null, null);
	}

	// sample usage: makeTLSContext("./my.keystore", "mypass", "JKS", "SunX509", "SunX509");
	public static SSLContext makeTLSContext(final String keystoreFilename, final String password, final String keystoreType, final String keymanagerAlgorithm, final String trustmanagerAlgorithm) {
		try {
			final char[] passphrase = password.toCharArray();
			final FileInputStream fis = new FileInputStream(keystoreFilename);
            final KeyStore ks = KeyStore.getInstance(Strings.hasText(keystoreType) ? keystoreType : KeyStore.getDefaultType());
			ks.load(fis, passphrase);
            final KeyManagerFactory kmf = KeyManagerFactory.getInstance(Strings.hasText(keymanagerAlgorithm) ? keymanagerAlgorithm : KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, passphrase);
            final TrustManagerFactory tmf = TrustManagerFactory.getInstance(Strings.hasText(trustmanagerAlgorithm) ? trustmanagerAlgorithm : TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);
            final SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			return sc;
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

}
