/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

/**
 * MIME multipart form data part information class.
 */
public class MIMEMultipartFormDataPartInfo {

	private boolean _isFormData = false;
	private int _partOffset = -1;
	private String _fieldName = null;
	private String _fileName = null;
	private String _contentType = null;
	private int _dataStartOffset = -1;
	private int _dataLength = -1;

	/**
	 * Determines if this part is the form data.
	 * 
	 * @return <tt>true</tt> if this part is the form data, <tt>false</tt> otherwise
	 */
	public boolean isFormData() {
		return _isFormData;
	}

	/**
	 * Gets the part offset.
	 * 
	 * @return the part offset
	 */
	public int getPartOffset() {
		return _partOffset;
	}

	/**
	 * Gets the field name.
	 * 
	 * @return the field name
	 */
	public String getFieldName() {
		return _fieldName;
	}

	/**
	 * Gets the file name.
	 * 
	 * @return the file name
	 */
	public String getFileName() {
		return _fileName;
	}

	/**
	 * Gets the content type.
	 * 
	 * @return the content type
	 */
	public String getContentType() {
		return _contentType;
	}

	/**
	 * Gets the data start offset.
	 * 
	 * @return the data start offset
	 */
	public int getDataStartOffset() {
		return _dataStartOffset;
	}

	/**
	 * Gets the data length.
	 * 
	 * @return the data length
	 */
	public int getDataLength() {
		return _dataLength;
	}

	/**
	 * Sets the part offset to the specified value.
	 * 
	 * @param value 
	 */
	public void setPartOffset(final int value) {
		_partOffset = value;
	}

	/**
	 * Sets the field name to the specified value.
	 * 
	 * @param value 
	 */
	public void setFieldName(final String value) {
		_fieldName = value;
	}

	/**
	 * Sets the file name to the specified value.
	 * 
	 * @param value 
	 */
	public void setFileName(final String value) {
		_fileName = value;
	}

	/**
	 * Sets the content type to the specified value.
	 * 
	 * @param value 
	 */
	public void setContentType(final String value) {
		_contentType = value;
	}

	/**
	 * Sets the data start offset to the specified value.
	 * 
	 * @param value 
	 */
	public void setDataStartOffset(final int value) {
		_dataStartOffset = value;
	}

	/**
	 * Sets the data length to the specified value.
	 * 
	 * @param value 
	 */
	public void setDataLength(final int value) {
		_dataLength = value;
	}

	/**
	 * Marks this part as form data.
	 */
	public void markAsFormData() {
		_isFormData = true;
	}

}
