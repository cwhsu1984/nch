package com.example.novel.parser;

import java.util.ArrayList;

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
		ArrayList<Novel> novels = result.novels;

		System.out.println("table size " + tables.size());
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

				Novel novel = new Novel();
				novel.state = td.get(STATE).text();
				novel.name = td.get(NAME).text();
				novel.author = td.get(AUTHOR).text();
				novel.serial = td.get(SERIAL).text();
				novel.update = td.get(UPDATE).text();
				novel.popularity = td.get(POPULARITY).text();
				novel.vote = td.get(VOTE).text();
				novel.commentUrl = td.get(COMMENT).text();


				Elements href = t.select("td").select("a[href]");
				novel.nameUrl = NCH + href.get(NAME_URL).attr("href");
				novel.authorUrl = NCH + href.get(AUTHOR_URL).attr("href");
				novel.voteUrl = NCH + href.get(VOTE_URL).attr("href");
				novel.commentUrl = NCH + href.get(COMMENT_URL).attr("href");
				//System.out.println(novel);
				novels.add(novel);
			}
		}

		// Select total data count, total page, current page by background color
		Elements info = doc.select("table").select("[bgcolor=#3366cc]");
		if (info.size() != 0) {
			result.totalPages = Integer.valueOf(info.get(1).text());

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
