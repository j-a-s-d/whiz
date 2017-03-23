/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.arrays.ByteArrays;
import ace.constants.STRINGS;
import ace.text.Strings;
import java.util.List;
import whiz.WhizObject;

public class MIMEMultipartFormDataParser extends WhizObject {

	private static final byte[] RAW_CRLF = (STRINGS.CR + STRINGS.LF).getBytes();
	private static final int RAW_CRLF_LENGTH = RAW_CRLF.length;
	private static final byte[] RAW_DOUBLE_CRLF = new byte[] { 13, 10, 13, 10 };
	private static final int DOUBLE_CRLF_LENGTH = RAW_DOUBLE_CRLF.length;
	private static final String MIME_BOUNDARY_MASK = "--";

	private MIMEMultipartFormDataInfo _info;
	private byte[] _body;

	private void crackPartHeaders(final MIMEMultipartFormDataPartInfo partInfo, final List<String> headers) {
		for (final String header : headers) {
			final String[] parts = header.split(":");
			final String ct = parts[0].trim();
			if ("Content-Disposition".equals(ct)) {
				final String[] fields = parts[1].split(";");
				for (final String s : fields) {
					if ("form-data".equals(s.trim().toLowerCase())) {
						partInfo.markAsFormData();
					} else {
						final String[] field = s.split("=");
						final String fn = field[0].trim().toLowerCase();
						if ("name".equals(fn)) {
							partInfo.setFieldName(Strings.stripBoth(field[1].trim(), '"'));
						} else if ("filename".equals(fn)) {
							partInfo.setFileName(Strings.stripBoth(field[1].trim(), '"'));
						}
					}
				}
			} else if ("Content-Type".equals(ct)) {
				partInfo.setContentType(parts[1].trim());
			}
		}
	}

	private MIMEMultipartFormDataPartInfo parsePart(final int partOffset, final int nextPartOffset) {
		final int l = ByteArrays.indexOf(_body, partOffset, RAW_DOUBLE_CRLF);
		final String partMimeHeader = Strings.ensure(Strings.fromByteArrayRange(_body, partOffset, l));
		final List<String> headers = Strings.splitLines(partMimeHeader);
		headers.remove(0);
		final MIMEMultipartFormDataPartInfo partInfo = new MIMEMultipartFormDataPartInfo();
		crackPartHeaders(partInfo, headers);
		partInfo.setPartOffset(partOffset);
		partInfo.setDataStartOffset(l + DOUBLE_CRLF_LENGTH);
		partInfo.setDataLength(nextPartOffset - partInfo.getDataStartOffset() - RAW_CRLF_LENGTH);
		return partInfo;
	}

	private void parseParts(final String boundaryMark) {
		_info.setBoundaryId(Strings.stripBoth(boundaryMark, '-'));
		final int[] partsOffsets = ByteArrays.indexesOf(_body, boundaryMark.getBytes());
		for (int i = 0; i < partsOffsets.length - 1; i++) {
			_info.addPartInfo(parsePart(partsOffsets[i], partsOffsets[i + 1]));
		}
	}

	private MIMEMultipartFormDataInfo parseBody() {
		_info = new MIMEMultipartFormDataInfo();
		final String mimeHeader = Strings.fromByteArray(_body, 0, ByteArrays.indexOf(_body, RAW_CRLF));
		if (assigned(mimeHeader) && mimeHeader.startsWith(MIME_BOUNDARY_MASK)) {
			parseParts(mimeHeader);
			return _info;
		}
		return null;
	}

	public MIMEMultipartFormDataInfo parse(final byte[] body) {
		_body = body;
		return parseBody();
	}

}
