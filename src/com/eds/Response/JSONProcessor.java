package com.eds.Response;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;

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

public class JSONProcessor implements IMessageProcessor {

    public static final String HTTP_Bad_Request = "400";

    public String GetContentType() {
	return "application/json";
    }

    public SessionToken buildSessionToken(BufferedReader reader) {
	JSONObject object = null;
	SessionToken sessionToken = new SessionToken();
	try {

	    object = (JSONObject) new JSONTokener(reader).nextValue();
	    sessionToken.setSessionToken(object.getString("SessionToken"));
	} catch (JSONException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return sessionToken;

    }

    public ResultsList buildResultsList(Response response) {

	JSONObject object = null;
	ResultsList resultsList = new ResultsList();
	BufferedReader reader = null;
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

		if (errorNumber.equals(HTTP_Bad_Request)) {

		    object = (JSONObject) new JSONTokener(errorreader)
			    .nextValue();
		    String DetailedErrorDescription = object
			    .getString("DetailedErrorDescription");
		    String ErrorDescription = object
			    .getString("ErrorDescription");
		    String ErrorNumber = object.getString("ErrorNumber");
		    apierrormessage
			    .setDetailedErrorDescription(DetailedErrorDescription);
		    apierrormessage.setErrorDescription(ErrorDescription);
		    apierrormessage.setErrorNumber(ErrorNumber);

		} else {

		    String DetailedErrorDescription = response.getErrorStream();
		    String ErrorNumber = errorNumber;
		    String ErrorDescription = response.getErrorStream();
		    apierrormessage
			    .setDetailedErrorDescription(DetailedErrorDescription);
		    apierrormessage.setErrorDescription(ErrorDescription);
		    apierrormessage.setErrorNumber(ErrorNumber);

		}

		resultsList.setApierrormessage(apierrormessage);

	    } catch (JSONException e) {

		e.printStackTrace();
	    }

	}

	else {

	    try {

		reader = response.getRead();
		object = (JSONObject) new JSONTokener(reader).nextValue();
		// Here we set result list's attribute QueryString

		JSONObject SearchRequestGet = object
			.getJSONObject("SearchRequestGet");

		// Here We get QueryString
		String QueryString = SearchRequestGet.getString("QueryString");
		resultsList.setQueryString(QueryString);

		// Here we set result list's attribute TotalHits and
		// TotalSearchTime
		String TotalHits, TotalSearchTime;
		JSONObject searchResult = object.getJSONObject("SearchResult");
		JSONObject Statistics = searchResult
			.getJSONObject("Statistics");

		TotalHits = Statistics.getString("TotalHits");
		TotalSearchTime = Statistics.getString("TotalSearchTime");
		resultsList.setHits(TotalHits);
		resultsList.setSearchTime(TotalSearchTime);

		// Here we set result list's attribute result list;

		ArrayList<Result> resultlist = new ArrayList<Result>();

		JSONObject data = searchResult.getJSONObject("Data");

		if (!data.has("Records")) {
		}

		else {

		    JSONArray Records = data.getJSONArray("Records");

		    for (int i = 0; i < Records.length(); i++) {

			Result result = new Result();

			JSONObject Record = Records.getJSONObject(i);
			JSONObject Header = Record.getJSONObject("Header");
			String Resultid = Record.getString("ResultId");
			String PLink = Record.getString("PLink");
			String DbId = Header.getString("DbId");
			String DbLabel = Header.getString("DbLabel");
			String An = Header.getString("An");
			String PubType = Header.getString("PubType");
			String PubTypeId = Header.getString("PubTypeId");

			result.setAn(An);
			result.setpLink(PLink);
			result.setDbLabel(DbLabel);
			result.setPubType(PubType);
			result.setDbId(DbId);
			result.setResultId(Resultid);
			result.setPubTypeID(PubTypeId);

			// set BookJacket list in the result
			ArrayList<BookJacket> bookJaketList = new ArrayList<BookJacket>();
			if (!Record.has("ImageInfo")) {
			} else {

			    JSONArray Imageinfos = Record
				    .getJSONArray("ImageInfo");
			    for (int j = 0; j < Imageinfos.length(); j++) {

				BookJacket bookJacket = new BookJacket();
				JSONObject Imageinfo = Imageinfos
					.getJSONObject(j);
				String Size = Imageinfo.getString("Size");
				String Target = Imageinfo.getString("Target");
				bookJacket.setSize(Size);
				bookJacket.setTarget(Target);
				bookJaketList.add(bookJacket);
			    }

			    result.setBookJaketList(bookJaketList);
			}

			// set Custom link List in the result

			ArrayList<CustomLink> CustomListList = new ArrayList<CustomLink>();

			if (!Record.has("CustomLinks")) {
			} else {

			    JSONArray CustomLinks = Record
				    .getJSONArray("CustomLinks");
			    for (int j = 0; j < CustomLinks.length(); j++) {

				CustomLink customlink = new CustomLink();
				JSONObject CustomLinkJSON = CustomLinks
					.getJSONObject(j);
				String CustomLinkURl = CustomLinkJSON
					.getString("Url");
				String CustomLinkName = CustomLinkJSON
					.getString("Name");
				String CustomLinkCategory = CustomLinkJSON
					.getString("Category");
				String CustomLinkText = CustomLinkJSON
					.getString("Text");
				String CustomLinkIcon = CustomLinkJSON
					.getString("Icon");
				String MouseOverText = CustomLinkJSON
					.getString("MouseOverText");

				customlink.setCategory(CustomLinkCategory);
				customlink.setIcon(CustomLinkIcon);
				customlink.setMouseOverText(MouseOverText);
				customlink.setName(CustomLinkName);
				customlink.setText(CustomLinkText);
				customlink.setUrl(CustomLinkURl);

				CustomListList.add(customlink);

			    }

			    result.setCustomListList(CustomListList);
			}

			// set Item in the result

			ArrayList<Item> itemList = new ArrayList<Item>();
			TransDataToHTML tdth = new TransDataToHTML();
			if (!Record.has("Items")) {
			} else {
			    JSONArray Items = Record.getJSONArray("Items");

			    for (int j = 0; j < Items.length(); j++) {

				Item item = new Item();
				JSONObject itemJSON = Items.getJSONObject(j);
				String Data = itemJSON.getString("Data");
				String Label = itemJSON.getString("Label");
				String Group = itemJSON.getString("Group");
				Data = Jsoup.parse(Data).text().toString();
				Data = tdth.transDataToHTML(Data);

				item.setData(Data);
				item.setGroup(Group);
				item.setLabel(Label);
				itemList.add(item);

			    }

			    result.setItemList(itemList);
			}

			// Set the PDF and URL in Result List, But There is a
			// question, The Links is a Array.

			JSONObject FullText = Record.getJSONObject("FullText");

			String Type = null, Url = null;

			if (!FullText.has("Text")) {
			} else {

			    // JSONObject Text = FullText.getJSONObject("Text");
			    // JSONObject
			    // Availability=Text.getJSONObject("Availability");

			}

			result.setPDF(Type);
			result.setHTML(Url);
			resultlist.add(result);

		    }

		    resultsList.setResultsList(resultlist);

		}
		// Here we set result list's attribute facetsList
		ArrayList<Facet> facetsList = new ArrayList<Facet>();
		if (!searchResult.has("AvailableFacets")) {
		} else {
		    JSONArray AvailableFacets = searchResult
			    .getJSONArray("AvailableFacets");
		    for (int i = 0; i < AvailableFacets.length(); i++) {
			JSONObject availableFacet = AvailableFacets
				.getJSONObject(i);
			Facet facet = new Facet();
			String Id = availableFacet.getString("Id");
			String Label = availableFacet.getString("Label");

			facet.setId(Id);
			facet.setLabel(Label);
			JSONArray AvailableFacetValues = availableFacet
				.getJSONArray("AvailableFacetValues");
			ArrayList<FacetValue> facetsValueList = new ArrayList<FacetValue>();

			for (int j = 0; j < AvailableFacetValues.length(); j++) {

			    JSONObject availableFacetValue = AvailableFacetValues
				    .getJSONObject(j);
			    FacetValue facetvalue = new FacetValue();

			    String Value = availableFacetValue
				    .getString("Value");
			    String Count = availableFacetValue
				    .getString("Count");
			    String AddAction = availableFacetValue
				    .getString("AddAction");

			    facetvalue.setAddAction(AddAction);
			    facetvalue.setCount(Count);
			    facetvalue.setValue(Value);
			    facetsValueList.add(facetvalue);

			}

			facet.setFacetsValueList(facetsValueList);
			facetsList.add(facet);
		    }
		    resultsList.setFacetsList(facetsList);
		}

	    } catch (JSONException e) {

		e.printStackTrace();
	    }

	}

	return resultsList;
    }

    @Override
    public String buildUIDAuthRequest(String username, String password) {
	return "{\"UserId\":\"" + username + "\",\"Password\":\"" + password
		+ "\"}";
    }

    @Override
    public Record buildRecord(Response response) {

	JSONObject object = null;
	BufferedReader reader = response.getRead();
	Record record = new Record();
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

		if (errorNumber.equals(HTTP_Bad_Request)) {

		    object = (JSONObject) new JSONTokener(errorreader)
			    .nextValue();
		    String DetailedErrorDescription = object
			    .getString("DetailedErrorDescription");
		    String ErrorDescription = object
			    .getString("ErrorDescription");
		    String ErrorNumber = object.getString("ErrorNumber");
		    apierrormessage
			    .setDetailedErrorDescription(DetailedErrorDescription);
		    apierrormessage.setErrorDescription(ErrorDescription);
		    apierrormessage.setErrorNumber(ErrorNumber);

		} else {

		    String DetailedErrorDescription = response.getErrorStream();
		    String ErrorNumber = errorNumber;
		    String ErrorDescription = response.getErrorStream();
		    apierrormessage
			    .setDetailedErrorDescription(DetailedErrorDescription);
		    apierrormessage.setErrorDescription(ErrorDescription);
		    apierrormessage.setErrorNumber(ErrorNumber);

		}

		record.setApierrormessage(apierrormessage);

	    } catch (JSONException e) {

		e.printStackTrace();
	    }

	} else {

	    try {
		object = (JSONObject) new JSONTokener(reader).nextValue();
		// Here we address JSON object form the top layer this is JSON

		if (object.has("Record")) {

		    JSONObject recordJSON = object.getJSONObject("Record");
		    String resultId = recordJSON.getString("ResultId");
		    String PLink = recordJSON.getString("PLink");
		    JSONObject HeaderJSON = recordJSON.getJSONObject("Header");
		    String DbId = HeaderJSON.getString("DbId");
		    String DbLabel = HeaderJSON.getString("DbLabel");
		    String An = HeaderJSON.getString("An");
		    String PubType = HeaderJSON.getString("PubType");

		    record.setResultId(resultId);
		    record.setAn(An);
		    record.setDbId(DbId);
		    record.setDbLabel(DbLabel);
		    record.setPubType(PubType);
		    record.setpLink(PLink);

		    // Print the result's information of Header

		    // ...............Here we address Custom Lists
		    // ........................

		    ArrayList<CustomLink> CustomListList = new ArrayList<CustomLink>();

		    if (!recordJSON.has("CustomLinks")) {
		    } else {

			JSONArray CustomLinks = recordJSON
				.getJSONArray("CustomLinks");
			for (int j = 0; j < CustomLinks.length(); j++) {

			    CustomLink customlink = new CustomLink();
			    JSONObject CustomLinkJSON = CustomLinks
				    .getJSONObject(j);
			    String CustomLinkURl = CustomLinkJSON
				    .getString("Url");
			    String CustomLinkName = CustomLinkJSON
				    .getString("Name");
			    String CustomLinkCategory = CustomLinkJSON
				    .getString("Category");
			    String CustomLinkText = CustomLinkJSON
				    .getString("Text");
			    String CustomLinkIcon = CustomLinkJSON
				    .getString("Icon");
			    String MouseOverText = CustomLinkJSON
				    .getString("MouseOverText");

			    customlink.setCategory(CustomLinkCategory);
			    customlink.setIcon(CustomLinkIcon);
			    customlink.setMouseOverText(MouseOverText);
			    customlink.setName(CustomLinkName);
			    customlink.setText(CustomLinkText);
			    customlink.setUrl(CustomLinkURl);

			    CustomListList.add(customlink);
			}

			record.setCustomListList(CustomListList);
		    }

		    // set BookJacket list in the record
		    ArrayList<BookJacket> bookJaketList = new ArrayList<BookJacket>();

		    if (!recordJSON.has("ImageInfo")) {
		    } else {

			JSONArray Imageinfos = recordJSON
				.getJSONArray("ImageInfo");
			for (int j = 0; j < Imageinfos.length(); j++) {

			    BookJacket bookJacket = new BookJacket();
			    JSONObject Imageinfo = Imageinfos.getJSONObject(j);
			    String Size = Imageinfo.getString("Size");
			    String Target = Imageinfo.getString("Target");
			    bookJacket.setSize(Size);
			    bookJacket.setTarget(Target);
			    bookJaketList.add(bookJacket);
			}

		    }
		    record.setBookJaketList(bookJaketList);

		    // Here we address FullText

		    JSONObject FullText = recordJSON.getJSONObject("FullText");

		    String Type = null, Url = null;
		    if (!FullText.has("Links")) {
		    }

		    else {
			JSONArray Links = FullText.getJSONArray("Links");

			for (int j = 0; j < Links.length(); j++) {

			    JSONObject Link = Links.getJSONObject(j);
			    Type = Link.getString("Type");
			    Url = Link.getString("Url");

			    record.setPDF(Type);
			    record.setHTML(Url);
			}

		    }

		    // Get Text from full text tag which was to be used by
		    // retrieve.
		    // JSONObject Text = FullText.getJSONObject("Text");
		    // There is something I did not address
		    // String Availability = Text.getString("Availability");

		    ArrayList<Item> itemList = new ArrayList<Item>();
		    JSONArray Items = recordJSON.getJSONArray("Items");
		    TransDataToHTML tdth = new TransDataToHTML();

		    for (int j = 0; j < Items.length(); j++) {

			Item item = new Item();
			JSONObject itemJSON = Items.getJSONObject(j);

			String Data = itemJSON.getString("Data");

			Data = Jsoup.parse(Data).text().toString();
			Data = tdth.transDataToHTML(Data);// Here we transfer
							  // data to HTML

			String Label = itemJSON.getString("Label");
			String Group = itemJSON.getString("Group");
			item.setData(Data);
			item.setGroup(Group);
			item.setLabel(Label);
			itemList.add(item);

		    }

		    record.setItemList(itemList);
		}

	    } catch (JSONException e) {

		e.printStackTrace();
	    }

	}

	return record;

    }

}
