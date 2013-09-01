package com.example.novel.parser;

import java.util.ArrayList;

/*
 * SearchResult is a data holder class.
 */
public class SearchResult {
	public int totalPages = 0; // The number of the total search result pages
	public int currentPage = 0; // The number of the current page.
	public String nextPageUrl;
	//public ArrayList<Novel> novels = new ArrayList<Novel>();	// The novel data of the current page


	public ArrayList<String> state = new ArrayList<String>();
	public ArrayList<String> name = new ArrayList<String>();
	public ArrayList<String> nameUrl = new ArrayList<String>();
	public ArrayList<String> author = new ArrayList<String>();
	public ArrayList<String> authorUrl = new ArrayList<String>();
	public ArrayList<String> serial = new ArrayList<String>();
	public ArrayList<String> update = new ArrayList<String>();
	public ArrayList<String> monthlyPopularity = new ArrayList<String>();
	public ArrayList<String> popularity = new ArrayList<String>();
	public ArrayList<String> monthlyVote = new ArrayList<String>();
	public ArrayList<String> vote = new ArrayList<String>();
	public ArrayList<String> voteUrl = new ArrayList<String>();
	public ArrayList<String> commentUrl = new ArrayList<String>();

	public void insertFront(SearchResult result) {
		state.addAll(0, result.state);
		name.addAll(0, result.name);
		nameUrl.addAll(0, result.nameUrl);
		author.addAll(0, result.author);
		authorUrl.addAll(0, result.authorUrl);
		serial.addAll(0, result.serial);
		update.addAll(0, result.update);
		monthlyPopularity.addAll(0, result.monthlyPopularity);
		popularity.addAll(0, result.popularity);
		monthlyVote.addAll(0, result.monthlyVote);
		vote.addAll(0, result.vote);
		voteUrl.addAll(0, result.voteUrl);
		commentUrl.addAll(0, result.commentUrl);
	}
}
