package com.eds.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildSearchLink {

    public String buildSearchLink(String input) {

	Pattern p = Pattern
		.compile("<searchLink fieldCode=\"([^\"]*)\" term=\"([^\"]*)\">");
	Matcher m = p.matcher(input);

	while (m.find()) {

	    String link_value = "<searchLink fieldCode=\"([^\"]*)\" term=\"([^\"]*)\">";
	    String link_html = "<a href=Search?lookfor=" + m.group(2)
		    + "&type=" + m.group(1) + ">";
	    input = input.replaceFirst(link_value, link_html);
	    input = input.replace("</searchLink>", "</a>");

	}

	return input;

    }

}
