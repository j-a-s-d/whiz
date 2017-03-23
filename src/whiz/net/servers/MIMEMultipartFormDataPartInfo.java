/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import whiz.WhizObject;

public class MIMEMultipartFormDataPartInfo extends WhizObject {

	private boolean _isFormData = false;
	private int _partOffset = -1;
	private String _fieldName = null;
	private String _fileName = null;
	private String _contentType = null;
	private int _dataStartOffset = -1;
	private int _dataLength = -1;

	public boolean isFormData() {
		return _isFormData;
	}

	public int getPartOffset() {
		return _partOffset;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getContentType() {
		return _contentType;
	}

	public int getDataStartOffset() {
		return _dataStartOffset;
	}

	public int getDataLength() {
		return _dataLength;
	}

	public void setPartOffset(final int value) {
		_partOffset = value;
	}

	public void setFieldName(final String value) {
		_fieldName = value;
	}

	public void setFileName(final String value) {
		_fileName = value;
	}

	public void setContentType(final String value) {
		_contentType = value;
	}

	public void setDataStartOffset(final int value) {
		_dataStartOffset = value;
	}

	public void setDataLength(final int value) {
		_dataLength = value;
	}

	public void markAsFormData() {
		_isFormData = true;
	}

}
