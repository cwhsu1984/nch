package com.example.novel.parser;

import java.util.ArrayList;

/*
 * SearchResult is a data holder class.
 */
public class SearchResult {
	public int totalPages = 0; // The number of the total search result pages
	public String nextPageUrl;
	public ArrayList<Novel> novels = new ArrayList<Novel>();	// The novel data of the current page
}
