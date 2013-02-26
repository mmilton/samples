package com.eds.Response;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jsoup.Jsoup;
import org.xml.sax.InputSource;

import com.eds.bean.ApiErrorMessage;
import com.eds.bean.BookJacket;
import com.eds.bean.CustomLink;
import com.eds.bean.Facet;
import com.eds.bean.FacetValue;
import com.eds.bean.Item;
import com.eds.bean.Record;
import com.eds.bean.Response;
import com.eds.bean.Result;
import com.eds.bean.ResultsList;
import com.eds.bean.SessionToken;
import com.eds.helper.TransDataToHTML;

public class XMLProcessor implements IMessageProcessor {
    public static final String HTTP_BAD_REQUEST = "400";

    public String GetContentType() {
	return "application/XML";
    }

    public SessionToken buildSessionToken(BufferedReader reader) {
	String line = "";
	String sessionTokenXML = "";

	try {
	    while ((line = reader.readLine()) != null) {
		sessionTokenXML += line;
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	SessionToken sessionToken = new SessionToken();
	/*
	 * Parse String to XML and the get the value
	 */
	try {
	    StringReader stringReader = new StringReader(sessionTokenXML);
	    InputSource inputSource = new InputSource(stringReader);
	    Document doc = (new SAXBuilder()).build(inputSource);
	    Element root = doc.getRootElement();
	    Content content = root.getContent().get(0);

	    if (content.getValue() != null) {
		sessionToken.setSessionToken(content.getValue());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return sessionToken;

    }

    public ResultsList buildResultsList(Response response) {

	BufferedReader reader = response.getRead();
	ResultsList resultsList = new ResultsList();
	InputStream errorInputStream = null;
	String errorStream = "";
	BufferedReader errorreader = null;

	if (!response.getErrorStream().equals("")) {

	    errorStream = response.getErrorStream();
	    errorInputStream = new ByteArrayInputStream(errorStream.getBytes());
	    InputStreamReader in = new InputStreamReader(errorInputStream);
	    errorreader = new BufferedReader(in);
	    String errorNumber = response.getErrorNumber();
	    ApiErrorMessage apierrormessage = new ApiErrorMessage();

	    try {

		if (errorNumber.equals(HTTP_BAD_REQUEST)) {
		    String line = "";
		    String ResultListErrorStream = "";

		    try {
			while ((line = errorreader.readLine()) != null) {

			    ResultListErrorStream += line;
			}

		    } catch (IOException e) {

			e.printStackTrace();
		    }

		    try {

			StringReader stringReader = new StringReader(
				ResultListErrorStream);
			InputSource inputSource = new InputSource(stringReader);
			Document doc = (new SAXBuilder()).build(inputSource);

			if (doc.getRootElement().getName() == "APIErrorMessage") {

			    Element root = doc.getRootElement();
			    ApiErrorMessage aem = new ApiErrorMessage();
			    String DetailedErrorDescription = root.getContent()
				    .get(0).getValue();
			    String ErrorDescription = root.getContent().get(1)
				    .getValue();
			    String ErrorNumber = root.getContent().get(2)
				    .getValue();

			    aem.setDetailedErrorDescription(DetailedErrorDescription);
			    aem.setErrorDescription(ErrorDescription);
			    aem.setErrorNumber(ErrorNumber);
			    resultsList.setApierrormessage(aem);

			} else {

			    String DetailedErrorDescription = response
				    .getErrorStream();
			    String ErrorNumber = errorNumber;
			    String ErrorDescription = response.getErrorStream();
			    apierrormessage
				    .setDetailedErrorDescription(DetailedErrorDescription);
			    apierrormessage
				    .setErrorDescription(ErrorDescription);
			    apierrormessage.setErrorNumber(ErrorNumber);
			    resultsList.setApierrormessage(apierrormessage);
			}

		    } catch (Exception e) {

			e.printStackTrace();

		    }

		}

	    } catch (Exception e) {

		e.printStackTrace();

	    }

	}

	else {

	    TransDataToHTML tdth = new TransDataToHTML();
	    String line = "";
	    String resultsListXML = "";
	    try {
		while ((line = reader.readLine()) != null) {
		    resultsListXML += line;
		}
	    } catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }

	    try {

		StringReader stringReader = new StringReader(resultsListXML);
		InputSource inputSource = new InputSource(stringReader);
		Document doc = (new SAXBuilder()).build(inputSource);
		// root element (level 1)

		// ------------------handle resultsList

		Element searchResponseMessageGet = doc.getRootElement();
		// level 2 elements

		Element searchRequestGet = searchResponseMessageGet.getChild(
			"SearchRequestGet",
			searchResponseMessageGet.getNamespace());
		Element searchResult = searchResponseMessageGet
			.getChild("SearchResult",
				searchResponseMessageGet.getNamespace());
		// level 3 elements

		Element queryString = searchRequestGet.getChild("QueryString",
			searchRequestGet.getNamespace());
		Element statics = searchResult.getChild("Statistics",
			searchResult.getNamespace());
		Element data = searchResult.getChild("Data",
			searchResult.getNamespace());
		Element availableFacets = searchResult.getChild(
			"AvailableFacets", searchResult.getNamespace());

		/*
		 * In next steps, elements will be analyzed separately
		 */
		// Get Query String
		String querystring = queryString.getContent(0).getValue();
		resultsList.setQueryString(querystring);

		// Get Total Hits and Total Search Time
		String totalHits = statics.getContent(0).getValue();
		String totalSearchTime = statics.getContent(1).getValue();
		resultsList.setHits(totalHits);
		resultsList.setSearchTime(totalSearchTime);

		if (Integer.parseInt(totalHits) > 0) {
		    // Get Results
		    Element records = data.getChild("Records",
			    data.getNamespace());
		    List<Element> recordsList = records.getChildren();
		    for (int i = 0; i < recordsList.size(); i++) {
			Result result = new Result();
			Element record = (Element) recordsList.get(i);
			// Get Result Id
			String resultId = record.getContent(0).getValue();
			result.setResultId(resultId);

			// Get Header Info
			Element header = record.getChildren().get(1);
			String dbId = header.getContent(0).getValue();
			String dbLabel = header.getContent(1).getValue();
			String an = header.getContent(2).getValue();
			String pubType = header.getContent(4).getValue();
			String pubTypeId = header.getContent(5).getValue();

			result.setDbId(dbId);
			result.setDbLabel(dbLabel);
			result.setPubTypeID(pubTypeId);

			result.setAn(an);
			result.setPubType(pubType);
			// Get PLink
			String pLink = record.getContent(2).getValue();
			result.setpLink(pLink);
			// Get ImageInfo

			Element imageInfo = record.getChild("ImageInfo",
				record.getNamespace());

			if (imageInfo != null) {
			    List<Element> coverArts = imageInfo.getChildren();
			    for (int b = 0; b < coverArts.size(); b++) {
				BookJacket bookJacket = new BookJacket();
				Element coverArt = (Element) coverArts.get(b);

				String size = coverArt.getContent(0).getValue();
				String target = coverArt.getContent(1)
					.getValue();

				bookJacket.setSize(size);
				bookJacket.setTarget(target);
				result.getBookJaketList().add(bookJacket);
			    }
			}

			// Get Custom Links

			Element customLinks = record.getChild("CustomLinks",
				record.getNamespace());

			if (customLinks != null) {
			    List<Element> customLinksList = customLinks
				    .getChildren();
			    for (int c = 0; c < customLinksList.size(); c++) {
				CustomLink customLink = new CustomLink();
				Element cl = (Element) customLinksList.get(c);

				String clurl = cl.getContent(0).getValue();
				String name = cl.getContent(1).getValue();
				String category = cl.getContent(2).getValue();
				String text = cl.getContent(3).getValue();
				String icon = cl.getContent(4).getValue();
				String mouseOverText = cl.getContent(5)
					.getValue();

				customLink.setUrl(clurl);
				customLink.setName(name);
				customLink.setCategory(category);
				customLink.setText(text);
				customLink.setIcon(icon);
				customLink.setMouseOverText(mouseOverText);
				result.getCustomListList().add(customLink);
			    }
			}

			// Get Full Text Info

			Element FullText = record.getChild("FullText",
				record.getNamespace());
			Element Text = FullText.getChild("Text",
				FullText.getNamespace());
			String availability = Text.getChild("Availability",
				FullText.getNamespace()).getValue();

			result.setHTML(availability);
			Element items = record.getChild("Items",
				record.getNamespace());
			List<Element> itemList = items.getChildren();

			for (int j = 0; j < itemList.size(); j++) {

			    Element itemEle = (Element) itemList.get(j);
			    Item item = new Item();

			    String label = itemEle.getChild("Label",
				    itemEle.getNamespace()).getValue();
			    String group = itemEle.getChild("Group",
				    itemEle.getNamespace()).getValue();
			    String itemdata = itemEle.getChild("Data",
				    itemEle.getNamespace()).getValue();

			    itemdata = tdth.transDataToHTML(itemdata);// Here we
								      // transfer
								      // data to
								      // HTML

			    item.setLabel(label);
			    item.setGroup(group);
			    item.setData(itemdata);

			    // // need a toHTML method
			    result.getItemList().add(item);

			}
			resultsList.getResultsList().add(result);

		    }
		    // Get Facets
		    if (!availableFacets.getContent().isEmpty()) {
			List<Element> facetsList = availableFacets
				.getChildren();
			for (int e = 0; e < facetsList.size(); e++) {
			    Facet facet = new Facet();
			    Element availableFacet = (Element) facetsList
				    .get(e);
			    String id = availableFacet.getContent(0).getValue();
			    String label = availableFacet.getContent(1)
				    .getValue();
			    facet.setId(id);
			    facet.setLabel(label);
			    Element availableFacetValues = availableFacet
				    .getChildren().get(2);
			    List<Element> availableFacetValuesList = availableFacetValues
				    .getChildren();
			    for (int f = 0; f < availableFacetValuesList.size(); f++) {
				FacetValue facetValue = new FacetValue();
				Element availableFacetValue = (Element) availableFacetValuesList
					.get(f);
				String value = availableFacetValue
					.getContent(0).getValue();
				String count = availableFacetValue
					.getContent(1).getValue();
				String addAction = availableFacetValue
					.getContent(2).getValue();
				facetValue.setValue(value);
				facetValue.setCount(count);
				facetValue.setAddAction(addAction);
				facet.getFacetsValueList().add(facetValue);
			    }
			    resultsList.getFacetsList().add(facet);

			    // --------end to handle resultsList
			}
		    }

		}

	    } catch (Exception e) {
		e.printStackTrace();
	    }

	}

	return resultsList;

    }

    public String buildUIDAuthRequest(String userName, String password) {
	return "<UIDAuthRequestMessage xmlns=\"http://www.ebscohost.com/services/public/AuthService/Response/2012/06/01\">"
		+ "<UserId>"
		+ userName
		+ "</UserId>"
		+ "<Password>"
		+ password
		+ "</Password>"
		+ "<InterfaceId></InterfaceId>"
		+ "</UIDAuthRequestMessage>";
    }

    public Record buildRecord(Response response) {

	Record record = new Record();
	BufferedReader reader = response.getRead();
	InputStream errorInputStream = null;
	String errorStream = "";
	BufferedReader errorreader = null;

	if (!response.getErrorStream().equals("")) {

	    errorStream = response.getErrorStream();
	    errorInputStream = new ByteArrayInputStream(errorStream.getBytes());
	    InputStreamReader in = new InputStreamReader(errorInputStream);
	    errorreader = new BufferedReader(in);
	    String errorNumber = response.getErrorNumber();
	    ApiErrorMessage apierrormessage = new ApiErrorMessage();

	    try {

		if (errorNumber.equals(HTTP_BAD_REQUEST)) {
		    String line = "";
		    String RecordErrorStream = "";

		    try {
			while ((line = errorreader.readLine()) != null) {

			    RecordErrorStream += line;
			}

		    } catch (IOException e) {

			e.printStackTrace();
		    }

		    try {

			StringReader stringReader = new StringReader(
				RecordErrorStream);
			InputSource inputSource = new InputSource(stringReader);
			Document doc = (new SAXBuilder()).build(inputSource);

			if (doc.getRootElement().getName() == "APIErrorMessage") {

			    Element root = doc.getRootElement();
			    ApiErrorMessage aem = new ApiErrorMessage();
			    String DetailedErrorDescription = root.getContent()
				    .get(0).getValue();
			    String ErrorDescription = root.getContent().get(1)
				    .getValue();
			    String ErrorNumber = root.getContent().get(2)
				    .getValue();

			    aem.setDetailedErrorDescription(DetailedErrorDescription);
			    aem.setErrorDescription(ErrorDescription);
			    aem.setErrorNumber(ErrorNumber);
			    record.setApierrormessage(aem);

			} else {

			    String DetailedErrorDescription = response
				    .getErrorStream();
			    String ErrorNumber = errorNumber;
			    String ErrorDescription = response.getErrorStream();
			    apierrormessage
				    .setDetailedErrorDescription(DetailedErrorDescription);
			    apierrormessage
				    .setErrorDescription(ErrorDescription);
			    apierrormessage.setErrorNumber(ErrorNumber);
			    record.setApierrormessage(apierrormessage);
			}

		    } catch (Exception e) {

			e.printStackTrace();

		    }

		}

	    } catch (Exception e) {

		e.printStackTrace();

	    }

	}

	else {

	    String line = "";
	    String RecordXML = "";
	    try {
		while ((line = reader.readLine()) != null) {
		    RecordXML += line;
		}
	    } catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }

	    try {

		StringReader stringReader = new StringReader(RecordXML);
		InputSource inputSource = new InputSource(stringReader);
		Document doc = (new SAXBuilder()).build(inputSource);

		// ---------------begin to handle record

		// root element (level 1)
		Element RetrieveResponseMessage = doc.getRootElement();
		// level 2 elements
		Element Record = RetrieveResponseMessage.getChild("Record",
			RetrieveResponseMessage.getNamespace());

		// String resultId=recordDetail.getContent(0).getValue();

		// Here we parse Header
		Element Header = Record.getChild("Header",
			Record.getNamespace());

		String dbId = Header.getContent(0).getValue();
		String dbLabel = Header.getContent(1).getValue();
		String an = Header.getContent(2).getValue();
		String pubType = Header.getContent(4).getValue();
		String pubTypeId = Header.getContent(5).getValue();
		record.setAn(an);
		record.setDbId(dbId);
		record.setDbLabel(dbLabel);
		record.setPubType(pubType);
		record.setPubTypeId(pubTypeId);

		// Here we parse PLink
		String pLink = Record.getChild("PLink", Record.getNamespace())
			.getValue();

		record.setpLink(pLink);

		// Here we parse ImageInfo
		Element imageInfo = Record.getChild("ImageInfo",
			Record.getNamespace());

		if (imageInfo != null) {
		    List<Element> coverArts = imageInfo.getChildren();
		    for (int b = 0; b < coverArts.size(); b++) {
			BookJacket bookJacket = new BookJacket();
			Element coverArt = (Element) coverArts.get(b);

			String size = coverArt.getContent(0).getValue();
			String target = coverArt.getContent(1).getValue();

			bookJacket.setSize(size);
			bookJacket.setTarget(target);
			record.getBookJaketList().add(bookJacket);
		    }
		}

		// Here we parse CustomLinks

		Element customLinks = Record.getChild("CustomLinks",
			Record.getNamespace());

		if (customLinks != null) {
		    List<Element> customLinksList = customLinks.getChildren();
		    for (int c = 0; c < customLinksList.size(); c++) {
			CustomLink customLink = new CustomLink();
			Element cl = (Element) customLinksList.get(c);

			String clurl = cl.getContent(0).getValue();
			String name = cl.getContent(1).getValue();
			String category = cl.getContent(2).getValue();
			String text = cl.getContent(3).getValue();
			String icon = cl.getContent(4).getValue();
			String mouseOverText = cl.getContent(5).getValue();

			customLink.setUrl(clurl);
			customLink.setName(name);
			customLink.setCategory(category);
			customLink.setText(text);
			customLink.setIcon(icon);
			customLink.setMouseOverText(mouseOverText);
			record.getCustomListList().add(customLink);
		    }
		}

		// Here we parse FullText

		Element FullText = Record.getChild("FullText",
			Record.getNamespace());
		Element Text = FullText.getChild("Text",
			FullText.getNamespace());
		String availability = Text.getChild("Availability",
			FullText.getNamespace()).getValue();

		record.setHTML(availability);

		// Here we parse Items

		Element items = Record.getChild("Items", Record.getNamespace());
		List<Element> itemList = items.getChildren();
		TransDataToHTML trans = new TransDataToHTML();

		for (int j = 0; j < itemList.size(); j++) {

		    Element itemEle = (Element) itemList.get(j);
		    Item item = new Item();
		    String label = itemEle.getChild("Label",
			    itemEle.getNamespace()).getValue();
		    String group = itemEle.getChild("Group",
			    itemEle.getNamespace()).getValue();
		    String itemdata = itemEle.getChild("Data",
			    itemEle.getNamespace()).getText();

		    itemdata = Jsoup.parse(itemdata).text().toString();
		    itemdata = trans.transDataToHTML(itemdata);// Here we
							       // transfer data
							       // to HTML

		    item.setLabel(label);
		    item.setGroup(group);
		    item.setData(itemdata);// // need a toHTML method
		    record.getItemList().add(item);
		    // ----------end of handling record
		}

	    } catch (Exception e) {

		e.printStackTrace();
	    }
	}

	return record;

    }

}
