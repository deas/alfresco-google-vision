package es.keensoft.alfresco.google;

import org.alfresco.service.cmr.remoteconnector.RemoteConnectorRequest;
import org.alfresco.service.cmr.remoteconnector.RemoteConnectorService;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GoogleVisionWorker {
	private static final Logger logger = LoggerFactory.getLogger(GoogleVisionWorker.class);
	/*
    private String applicationName;
    private String credentialsJsonPath;
    private Integer maxResults;
    private String translateLanguage;
    private String translateApiKey;
    */
	private RemoteConnectorService remoteConnectorService;
	private ContentService contentService;
	private String endpoint;

    public GoogleVisionBean execute(ContentReader reader) throws Exception {
    	
    	if (isImage(reader)) {
			RemoteConnectorRequest request = remoteConnectorService.buildRequest(endpoint, "POST");
			request.setRequestBody(reader.getContentInputStream());
			JSONObject response = remoteConnectorService.executeJSONRequest(request);
			GoogleVisionBean gvb = new GoogleVisionBean();
			if (response.containsKey("labels")) {
				gvb.setLabels((List<String>) response.get("labels"));
			}
			if (response.containsKey("landmark")) {
				gvb.setLandmark((String) response.get("landmark"));
			}
			if (response.containsKey("logo")) {
				gvb.setLogo((String) response.get("logo"));
			}
			if (response.containsKey("text")) {
				gvb.setText((List<String>) response.get("text"));
			}
    		return gvb;
    	} else {
	        return null;
    	}
	    
    }

    public boolean isImage(ContentReader reader) {
		 return reader.getMimetype().startsWith("image/");
	}
	/*
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setCredentialsJsonPath(String credentialsJsonPath) {
		this.credentialsJsonPath = credentialsJsonPath;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public void setTranslateLanguage(String translateLanguage) {
		this.translateLanguage = translateLanguage;
	}

	public void setTranslateApiKey(String translateApiKey) {
		this.translateApiKey = translateApiKey;
	}
	*/
	public void setRemoteConnectorService(RemoteConnectorService remoteConnectorService) {
		this.remoteConnectorService = remoteConnectorService;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}