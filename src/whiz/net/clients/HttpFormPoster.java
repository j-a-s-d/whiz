/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.constants.STRINGS;
import ace.time.Chronometer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import whiz.net.HttpMethod;
import whiz.net.NetworkConnection;
import whiz.net.URISchemes;
import whiz.net.interfaces.HttpConnectionInfo;

/**
 * Useful HTTP form poster class.
 */
public class HttpFormPoster extends NetworkConnection implements HttpConnectionInfo {

	/**
	 * The character set used for the form post.
	 */
	public static String CHARSET = "utf-8";

	/**
	 * The buffer size used for the form post.
	 */
	public static int BUFFER_SIZE = 4096;

	private static final String BOUNDARY_BEGIN = "------";
	private static final String BOUNDARY_END = "--";

	protected final String _boundaryId;
	protected String _userAgent;
	protected String _contentType;
	private HttpURLConnection _connection;
	private OutputStream _outputStream;
	private PrintWriter _writer;
	protected Chronometer _chrono;
	protected HttpMethod _requestMethod;
	protected String _requestURL;
	protected int _responseCode;
	protected String _responseData;
	protected long _responseTime;

	/**
	 * Constructor accepting the destination URL as a string.
	 * 
	 * @param requestURL 
	 */
	public HttpFormPoster(final String requestURL) {
		super(HttpFormPoster.class);
		_userAgent = STRINGS.EMPTY;
		_boundaryId = _userAgent + "Boundary" + Long.toHexString(System.currentTimeMillis()).toUpperCase();
		_contentType = "multipart/form-data; boundary=" + _boundaryId;
		_requestURL = requestURL;
		_requestMethod = HttpMethod.POST;
		_chrono = new Chronometer();
	}

	/**
	 * Opens the connection to the destination in a silent mode (catching exceptions).
	 */
	public void open() {
		try {
			final URL requestURL = new URL(_requestURL);
			if (URISchemes.HTTP.equals(requestURL.getProtocol())) {
				_connection = (HttpURLConnection) requestURL.openConnection();
			} else if (URISchemes.HTTPS.equals(requestURL.getProtocol())) {
				_connection = (HttpsURLConnection) requestURL.openConnection();
			} else {
				throw new Exception("Invalid protocol.");
			}
			_connection.setRequestMethod(_requestMethod.name());
			_connection.setDoOutput(true);
			_connection.setDoInput(true);
			_connection.setUseCaches(false);
			_connection.setRequestProperty("Content-Type", _contentType);
			_connection.setRequestProperty("User-Agent", _userAgent);
			_outputStream = _connection.getOutputStream();
			_writer = new PrintWriter(new OutputStreamWriter(_outputStream, CHARSET), true);
			_chrono.start();
		} catch (final Exception e) {
			setLastException(e);
		}
	}

	private void writeLine(final String line) {
		_writer.append(line).append(STRINGS.CR + STRINGS.LF);
	}

	/**
	 * Adds the specified request header with the specified value to the form post.
	 * 
	 * @param name
	 * @param value 
	 */
	public void addHeader(final String name, final String value) {
		writeLine(name + ": " + value);
	}

	/**
	 * Adds the specified field with the specified value to the form post.
	 * 
	 * @param fieldName
	 * @param fieldValue 
	 */
	public void addFieldPart(final String fieldName, final String fieldValue) {
		writeLine(BOUNDARY_BEGIN + _boundaryId);
		writeLine("Content-Disposition: form-data; name=\"" + fieldName + "\"");
		writeLine("Content-Type: text/plain; charset=" + CHARSET);
		writeLine(STRINGS.EMPTY);
		writeLine(fieldValue);
		_writer.flush();
	}

	/**
	 * Adds the specified field with the specified file to the form post.
	 * 
	 * @param fieldName
	 * @param uploadFile
	 */
	public void addFilePart(final String fieldName, final File uploadFile) {
		final String fileName = uploadFile.getName();
		writeLine(BOUNDARY_BEGIN + _boundaryId);
		writeLine("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"");
		writeLine("Content-Type: " + URLConnection.guessContentTypeFromName(fileName));
		writeLine("Content-Transfer-Encoding: binary");
		writeLine(STRINGS.EMPTY);
		_writer.flush();
		try {
			final FileInputStream inputStream = new FileInputStream(uploadFile);
			final byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				_outputStream.write(buffer, 0, bytesRead);
			}
			_outputStream.flush();
			inputStream.close();
		} catch (final Exception e) {
			setLastException(e);
		}
		_writer.flush();
	}

	private void readResponse() {
		try {
			_responseCode = _connection.getResponseCode();
			if (_responseCode == HttpURLConnection.HTTP_OK) {
				final BufferedReader reader = new BufferedReader(new InputStreamReader(_connection.getInputStream()));
				final StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line).append(STRINGS.EOL);
				}
				reader.close();
				_connection.disconnect();
				_responseData = response.toString();
			}
		} catch (final Exception e) {
			setLastException(e);
		}
	}

	/**
	 * Closes the connection to the destination.
	 * 
	 * @return the resulting HTTP connection info
	 */
	public HttpConnectionInfo close() {
		writeLine(STRINGS.EMPTY);
		writeLine(BOUNDARY_BEGIN + _boundaryId + BOUNDARY_END);
		_writer.flush();
		_writer.close();
		readResponse();
		_responseTime = _chrono.stop();
		return this;
	}

	/**
	 * Gets the request method.
	 * 
	 * @return the request method
	 */
	@Override public String getRequestMethod() {
		return _requestMethod.name();
	}

	/**
	 * Gets the request URL string.
	 * 
	 * @return the request URL string
	 */
	@Override public String getRequestURL() {
		return _requestURL;
	}

	/**
	 * Gets the response code.
	 * 
	 * @return the response code
	 */
	@Override public int getResponseCode() {
		return _responseCode;
	}

	/**
	 * Gets the response data.
	 * 
	 * @return the response data
	 */
	@Override public String getResponseData() {
		return _responseData;
	}

	/**
	 * Gets the response time.
	 * 
	 * @return the response time
	 */
	@Override public long getResponseTime() {
		return _responseTime;
	}

	// NOTE: the following methods return null due to the class nature

	/**
	 * Gets the request data.
	 * 
	 * @return the request data
	 */
	@Override public String getRequestData() {
		return null;
	}

	/**
	 * Gets the request cookie list.
	 * 
	 * @return the request cookie list
	 */
	@Override public List<HttpCookie> getRequestCookies() {
		return null;
	}

	/**
	 * Gets the response cookie list.
	 * 
	 * @return the response cookie list
	 */
	@Override public List<HttpCookie> getResponseCookies() {
		return null;
	}

}
