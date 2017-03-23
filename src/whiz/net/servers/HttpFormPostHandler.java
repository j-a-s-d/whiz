/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.arrays.ByteArrays;
import ace.files.FilenameValidator;
import ace.interfaces.Treater;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import whiz.net.HttpMethod;

public abstract class HttpFormPostHandler extends HttpBinaryHandler implements HttpFileOperator {

	private final List<String> _explicitFieldSortList = new ArrayList();
	private final FilenameValidator _filter;

	public HttpFormPostHandler(final Class<?> clazz, final String route) {
		this(clazz, route, null, null, FilenameValidator.makeAllFilesValidator());
	}

	public HttpFormPostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		this(clazz, route, readingAdapter, writingAdapter, FilenameValidator.makeAllFilesValidator());
	}

	public HttpFormPostHandler(final Class<?> clazz, final String route, final String filter) {
		this(clazz, route, null, null, new FilenameValidator(filter));
	}

	public HttpFormPostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String filter) {
		this(clazz, route, readingAdapter, writingAdapter, new FilenameValidator(filter));
	}

	public HttpFormPostHandler(final Class<?> clazz, final String route, final FilenameValidator filter) {
		this(clazz, route, null, null, filter);
	}

	public HttpFormPostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final FilenameValidator filter) {
		super(clazz, new HttpMethod[] { HttpMethod.POST }, route, readingAdapter, writingAdapter);
		_filter = filter;
	}

	public synchronized void setExplicitFieldSortList(final List<String> list) {
		_explicitFieldSortList.clear();
		_explicitFieldSortList.addAll(list);
	}

	public synchronized List<String> getExplicitFieldSortList() {
		return _explicitFieldSortList;
	}

	final List<MIMEMultipartFormDataPartInfo> performExplicitFieldSorting(final List<MIMEMultipartFormDataPartInfo> list) {
		Collections.sort(list, new Comparator<MIMEMultipartFormDataPartInfo>() {
			int compareIntegers(final int x, final int y) {
				return (x < y) ? -1 : ((x == y) ? 0 : 1);
			}

			@Override public int compare(final MIMEMultipartFormDataPartInfo left, final MIMEMultipartFormDataPartInfo right) {
				return compareIntegers(
					_explicitFieldSortList.indexOf(left.getFieldName()), _explicitFieldSortList.indexOf(right.getFieldName())
				);
			}
		});
		return list;
	}

	@Override public byte[] transact(final HttpRequest request, final byte[] body) {
		final MIMEMultipartFormDataInfo info = new MIMEMultipartFormDataParser().parse(body);
		if (assigned(info)) {
			onStartProcessingParts(request);
			for (final MIMEMultipartFormDataPartInfo partInfo : performExplicitFieldSorting(new ArrayList<MIMEMultipartFormDataPartInfo>(info.getPartsInfos()))) {
				if (partInfo.isFormData()) {
					final byte[] partBody = ByteArrays.copy(body, partInfo.getDataStartOffset(), partInfo.getDataLength());
					final String fileName = partInfo.getFileName();
					if (assigned(fileName)) {
						if (fileName.isEmpty()) {
							onMissingFilename(request);
							continue;
						}
						if (!_filter.validate(fileName)) {
							onForbiddenFilename(request, fileName);
							continue;
						}
						if (!validate(request, fileName)) {
							continue;
						}
						onProcessPartFile(request, partInfo.getFieldName(), fileName, partBody);
					} else {
						onProcessPartField(request, partInfo.getFieldName(), partBody);
					}
				}
			}
			onFinishProcessingParts(request);
		}
		return onGetResponse(request);
	}

	protected abstract void onMissingFilename(final HttpRequest request);

	protected abstract void onForbiddenFilename(final HttpRequest request, final String fileName);

	protected abstract void onProcessPartField(final HttpRequest request, final String fieldName, final byte[] partBody);

	protected abstract void onProcessPartFile(final HttpRequest request, final String fieldName, final String fileName, final byte[] partBody);

	protected abstract void onStartProcessingParts(final HttpRequest request);

	protected abstract void onFinishProcessingParts(final HttpRequest request);

	protected abstract byte[] onGetResponse(final HttpRequest request);

}
