package syneren.sietech.hht;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

public class ImageExportResource extends Resource {
	// Public Constants
		public static final String CONTENT_TYPE = "application/pdf";
		public static final String RESOURCE_NAME = "export";
		private String requestPath;
		
	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public String getRequestPath() {
		// TODO Auto-generated method stub
		if (requestPath == null) {
			StringBuilder buf = new StringBuilder();
			buf.append(ResourceHandler.RESOURCE_IDENTIFIER);
			buf.append("/");
			buf.append(getResourceName());
			buf.append("?ln=");
			buf.append(getLibraryName());
			//buf.append("&");
			//buf.append(PARAM_NAME_CUSTOMER_ID);
			//buf.append("=");
			//buf.append(getCustomer().getCustomerId());
			requestPath = buf.toString();
		}

		return requestPath;
	}

	@Override
	public Map<String, String> getResponseHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
