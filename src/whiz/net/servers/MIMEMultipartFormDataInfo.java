/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.containers.Lists;
import java.util.List;

/**
 * MIME multipart form data information class.
 */
public class MIMEMultipartFormDataInfo {

	private String _boundaryId;
	private final List<MIMEMultipartFormDataPartInfo> _partsInfos;

	/**
	 * Default constructor.
	 */
	public MIMEMultipartFormDataInfo() {
		_partsInfos = Lists.make();
	}

	/**
	 * Gets the boundary id.
	 * 
	 * @return the boundary id
	 */
	public String getBoundaryId() {
		return _boundaryId;
	}

	/**
	 * Sets the boundary id to the specified string value
	 * 
	 * @param value 
	 */
	public void setBoundaryId(final String value) {
		_boundaryId = value;
	}

	void addPartInfo(final MIMEMultipartFormDataPartInfo partInfo) {
		_partsInfos.add(partInfo);
	}

	/**
	 * Gets the parts information object instances as a list.
	 * 
	 * @return the resulting list
	 */
	public List<MIMEMultipartFormDataPartInfo> getPartsInfos() {
		return _partsInfos;
	}

}
