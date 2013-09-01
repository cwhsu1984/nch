package com.example.novel.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchResultParser {

	static final String NCH = "http://www.nch.com.tw/";
	static final int STATE = 1;
	static final int NAME = 2;
	static final int AUTHOR = 3;
	static final int SERIAL = 4;
	static final int UPDATE = 5;
	static final int POPULARITY = 6;
	static final int VOTE = 7;
	static final int COMMENT = 8;
	static final int COLUMN_SIZE = 9;

	static final int NAME_URL = 0;
	static final int AUTHOR_URL = 1;
	static final int VOTE_URL = 2;
	static final int COMMENT_URL = 3;

	public static SearchResult parse(String html) {
		Document doc = Jsoup.parse(html);
		// select novels by table color
		Elements tables = doc.select("tr[bgcolor=#9BC2E3], tr[bgcolor=#E1E1E1]");
		SearchResult result = new SearchResult();

		//System.out.println("table size " + tables.size());
		for (Element t : tables) {
			Elements td = t.select("td").select("font");

			/*
			int i = 0;
			System.out.println("column size " + td.size());
			for (Element c : td) {
				System.out.println(i++ + " " + c.select("font").text());
			}

			for (Element a : t.select("td").select("a[href]")) {
				System.out.println(a.text());
				System.out.println(a.attr("href"));
			}
			 */
			// Prevent index out of bounds by checking column size
			if (td.size() >= COLUMN_SIZE) {

				result.state.add(td.get(STATE).text());
				result.name.add(td.get(NAME).text());
				result.author.add(td.get(AUTHOR).text());
				result.serial.add(td.get(SERIAL).text());
				result.update.add(td.get(UPDATE).text());
				result.popularity.add(td.get(POPULARITY).text());
				result.vote.add(td.get(VOTE).text());
				result.commentUrl.add(td.get(COMMENT).text());


				Elements href = t.select("td").select("a[href]");
				result.nameUrl.add(NCH + href.get(NAME_URL).attr("href"));
				result.authorUrl.add(NCH + href.get(AUTHOR_URL).attr("href"));
				result.voteUrl.add(NCH + href.get(VOTE_URL).attr("href"));
				result.commentUrl.add(NCH + href.get(COMMENT_URL).attr("href"));
				//System.out.println(novel);
			}
		}

		// Select total data count, total page, current page by background color
		Elements info = doc.select("table").select("[bgcolor=#3366cc]");
		if (info.size() != 0) {
			result.totalPages = Integer.valueOf(info.get(1).text());
			result.currentPage = Integer.valueOf(info.get(2).text());

			// Select the url of the next page.
			Elements url = doc.select("table").select("[bgcolor=#c0c0c0]").select("a[href]");
			result.nextPageUrl = NCH.substring(0, NCH.length() - 1) + url.get(1).attr("href"); // trim the extra "/" of NCH
			//System.out.println(result.nextPageUrl);

			/*
			System.out.println("url size: " + url.size());
			for (Element u : url) {
				System.out.println(u.toString());
			}
			 */
		}
		//System.out.println("totalPage: " + result.totalPages);
		/*
		System.out.println("info size: " + info.size());
		for (Element i : info) {
			System.out.println(i.text());
		}
		 */
		return result;
	}
}
