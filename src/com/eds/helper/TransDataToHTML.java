package com.eds.helper;

public class TransDataToHTML {

    public String transDataToHTML(String map) {

	map = map.replace("<jsection", "<section");
	map = map.replace("<jsection", "</section");
	map = map.replace("<highlight", "<span class=highlight");
	map = map.replace("</highlight>", "</span>");
	map = map.replace("<text", "<div");
	map = map.replace("</text", "</div");
	map = map.replace("<title", "<h2");
	map = map.replace("</title", "</h2");
	map = map.replace("<anid", "<p");
	map = map.replace("</anid", "</p");
	map = map.replace("<aug", "<strong");
	map = map.replace("</aug", "</strong");
	map = map.replace("<hd", "<h3");
	map = map.replace("</hd", "</h3");
	map = map.replace("<linebr", "<br");
	map = map.replace("</linebr", "");
	map = map.replace("<olist", "<ol");
	map = map.replace("</olistt", "</ol");
	map = map.replace("<reflink", "<a");
	map = map.replace("</reflink", "</a");
	map = map.replace("<blist", "<p class=blist");
	map = map.replace("</blist", "<a");
	map = map.replace("<bibl", "</section");
	map = map.replace("</bibl", "</a");
	map = map.replace("<bibtext", "<span");
	map = map.replace("</bibtext", "</span");
	map = map.replace("<ref", "<div class=ref");
	map = map.replace("</ref", "</div");
	map = map.replace("<relatesTo", "<sup");
	map = map.replace("</relatesTo", "</sup");
	map = map.replace("<superscript", "<sup");
	map = map.replace("</superscript", "</sup");
	map = map.replace("<script", "");
	map = map.replace("</script", "");

	return map;
    }

}
