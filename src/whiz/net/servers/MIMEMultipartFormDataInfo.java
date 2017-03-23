/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.containers.Lists;
import java.util.List;

public class MIMEMultipartFormDataInfo {

	private String _boundaryId;
	private final List<MIMEMultipartFormDataPartInfo> _partsInfos;

	public MIMEMultipartFormDataInfo() {
		_partsInfos = Lists.make();
	}

	public String getBoundaryId() {
		return _boundaryId;
	}

	public void setBoundaryId(final String value) {
		_boundaryId = value;
	}

	void addPartInfo(final MIMEMultipartFormDataPartInfo partInfo) {
		_partsInfos.add(partInfo);
	}

	public List<MIMEMultipartFormDataPartInfo> getPartsInfos() {
		return _partsInfos;
	}

}
