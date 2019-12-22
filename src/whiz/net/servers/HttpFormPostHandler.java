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

/**
 * Useful HTTP form post handler class.
 */
public abstract class HttpFormPostHandler extends HttpBinaryHandler implements HttpFileOperator {

	private final List<String> _explicitFieldSortList = new ArrayList();
	private final FilenameValidator _filter;

	/**
	 * Constructor accepting a class instance and a route.
	 * 
	 * @param clazz
	 * @param route
	 */
	public HttpFormPostHandler(final Class<?> clazz, final String route) {
		this(clazz, route, null, null, FilenameValidator.makeAllFilesValidator());
	}

	/**
	 * Constructor accepting a class instance, a route and adapters for reading and writing.
	 * 
	 * @param clazz
	 * @param route
	 * @param readingAdapter
	 * @param writingAdapter
	 */
	public HttpFormPostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		this(clazz, route, readingAdapter, writingAdapter, FilenameValidator.makeAllFilesValidator());
	}

	/**
	 * Constructor accepting a class instance, a route and a file name filter.
	 * 
	 * @param clazz
	 * @param route
	 * @param filter 
	 */
	public HttpFormPostHandler(final Class<?> clazz, final String route, final String filter) {
		this(clazz, route, null, null, new FilenameValidator(filter));
	}

	/**
	 * Constructor accepting a class instance, a route, adapters for reading and writing and a file name filter.
	 * 
	 * @param clazz
	 * @param route
	 * @param readingAdapter
	 * @param writingAdapter
	 * @param filter 
	 */
	public HttpFormPostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String filter) {
		this(clazz, route, readingAdapter, writingAdapter, new FilenameValidator(filter));
	}

	/**
	 * Constructor accepting a class instance, a route and a file name validator.
	 * 
	 * @param clazz
	 * @param route
	 * @param validator 
	 */
	public HttpFormPostHandler(final Class<?> clazz, final String route, final FilenameValidator validator) {
		this(clazz, route, null, null, validator);
	}

	/**
	 * Constructor accepting a class instance, a route, adapters for reading and writing and a file name validator.
	 * 
	 * @param clazz
	 * @param route
	 * @param readingAdapter
	 * @param writingAdapter
	 * @param validator 
	 */
	public HttpFormPostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final FilenameValidator validator) {
		super(clazz, new HttpMethod[] { HttpMethod.POST }, route, readingAdapter, writingAdapter);
		_filter = validator;
	}

	/**
	 * Sets the list used for sorting the form fields.
	 * 
	 * @param list 
	 */
	public synchronized void setExplicitFieldSortList(final List<String> list) {
		_explicitFieldSortList.clear();
		_explicitFieldSortList.addAll(list);
	}

	/**
	 * Gets the list used for sorting the form fields.
	 * 
	 * @return the list
	 */
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

	/**
	 * On missing file name event handler accepting a HTTP request.
	 * 
	 * @param request 
	 */
	protected abstract void onMissingFilename(final HttpRequest request);

	/**
	 * On forbidden file name event handler accepting a HTTP request and the forbidden file name.
	 * 
	 * @param request 
	 * @param fileName 
	 */
	protected abstract void onForbiddenFilename(final HttpRequest request, final String fileName);

	/**
	 * On process part field event handler accepting a HTTP request, a field name and a part body byte array.
	 * 
	 * @param request
	 * @param fieldName
	 * @param partBody 
	 */
	protected abstract void onProcessPartField(final HttpRequest request, final String fieldName, final byte[] partBody);

	/**
	 * On process part file event handler accepting a HTTP request, a field name, a file name and a part body byte array.
	 * 
	 * @param request
	 * @param fieldName
	 * @param fileName
	 * @param partBody 
	 */
	protected abstract void onProcessPartFile(final HttpRequest request, final String fieldName, final String fileName, final byte[] partBody);

	/**
	 * On start processing parts event handler accepting a HTTP request.
	 * 
	 * @param request 
	 */
	protected abstract void onStartProcessingParts(final HttpRequest request);

	/**
	 * On finish processing parts event handler accepting a HTTP request.
	 * 
	 * @param request 
	 */
	protected abstract void onFinishProcessingParts(final HttpRequest request);

	/**
	 * On get response event handler accepting a HTTP request.
	 * 
	 * @param request
	 * @return the byte array of the response
	 */
	protected abstract byte[] onGetResponse(final HttpRequest request);

}
