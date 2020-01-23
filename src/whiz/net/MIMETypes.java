/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import ace.containers.Maps;
import java.util.HashMap;
import java.util.Map;
import whiz.Whiz;

/**
 * MIME types class.
 */
public final class MIMETypes extends Whiz {

	MIMETypes() {}

	private static final Map<String, String> DEFAULT_MIME_TYPES = new HashMap() {{
		put(".bin", "application/octet-stream");
		put(".bmp", "image/bmp");
		put(".bz2", "application/x-bzip2");
		put(".css", "text/css");
		put(".dtd", "application/xml-dtd");
		put(".doc", "application/msword");
		put(".gif", "image/gif");
		put(".gz", "application/x-gzip");
		put(".htm", "text/html");
		put(".html", "text/html");
		put(".jar", "application/java-archive");
		put(".jpg", "image/jpeg");
		put(".js", "application/javascript");
		put(".json", "application/json");
		put(".pdf", "application/pdf");
		put(".png", "image/png");
		put(".ppt", "application/powerpoint");
		put(".ps", "application/postscript");
		put(".rdf", "application/rdf");
		put(".rtf", "application/rtf");
		put(".sgml", "text/sgml");
		put(".svg", "image/svg+xml");
		put(".swf", "application/x-shockwave-flash");
		put(".tar", "application/x-tar");
		put(".tgz", "application/x-tar");
		put(".tiff", "image/tiff");
		put(".tsv", "text/tab-separated-values");
		put(".txt", "text/plain");
		put(".xls", "application/excel");
		put(".xml", "application/xml");
		put(".zip", "application/zip");
	}};

    /**
     * Gets the default mime types as a hash map.
	 * 
     * @return the resulting hash map
     */
	public static final Map<String, String> getAsHashMap() {
		return Maps.make(DEFAULT_MIME_TYPES);
	}

    /**
     * Gets the mime type for the specified file extension.
	 * 
     * @param extension
     * @return the resulting mime type
     */
	public static final String get(final String extension) {
		return DEFAULT_MIME_TYPES.get(extension);
	}

}
