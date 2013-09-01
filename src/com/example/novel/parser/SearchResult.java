package com.example.novel.parser;

import java.util.ArrayList;

/*
 * SearchResult is a data holder class.
 */
public class SearchResult {
	public int totalPages = 0; // The number of the total search result pages.
	public int currentPage = 0; // The number of the current page.
	public String nextPageUrl; // The url of the next result page.

	public ArrayList<String> state = new ArrayList<String>(); // State of the novel
	public ArrayList<String> name = new ArrayList<String>(); // Name of the novel
	public ArrayList<String> nameUrl = new ArrayList<String>(); // Link to the novel
	public ArrayList<String> writer = new ArrayList<String>(); // Name of the writer
	public ArrayList<String> writerUrl = new ArrayList<String>(); // Link to the writer
	public ArrayList<String> serial = new ArrayList<String>(); // Is the novel still publishing
	public ArrayList<String> update = new ArrayList<String>(); // Last update time
	public ArrayList<String> monthlyPopularity = new ArrayList<String>(); // Monthly popularity
	public ArrayList<String> popularity = new ArrayList<String>(); // Total popularity
	public ArrayList<String> monthlyVote = new ArrayList<String>(); // Monthly vote
	public ArrayList<String> vote = new ArrayList<String>(); // Total vote
	public ArrayList<String> voteUrl = new ArrayList<String>(); // Link to vote this novel
	public ArrayList<String> commentUrl = new ArrayList<String>(); // Link to comment this novel

	/*
	 * Insert the given result in front of the current result.
	 * @param previous is the previous search result.
	 */
	public void insertFront(SearchResult previous) {
		state.addAll(0, previous.state);
		name.addAll(0, previous.name);
		nameUrl.addAll(0, previous.nameUrl);
		writer.addAll(0, previous.writer);
		writerUrl.addAll(0, previous.writerUrl);
		serial.addAll(0, previous.serial);
		update.addAll(0, previous.update);
		monthlyPopularity.addAll(0, previous.monthlyPopularity);
		popularity.addAll(0, previous.popularity);
		monthlyVote.addAll(0, previous.monthlyVote);
		vote.addAll(0, previous.vote);
		voteUrl.addAll(0, previous.voteUrl);
		commentUrl.addAll(0, previous.commentUrl);
	}
}
