package com.example.novel.parser;

public class Novel {	
	public String state;
	public String name;
	public String nameUrl;
	public String author;
	public String authorUrl;
	public String serial;
	public String update;
	public String monthlyPopularity;
	public String popularity;
	public String monthlyVote;
	public String vote;
	public String voteUrl;
	public String commentUrl;
	
	public String toString() {
		return "state: " + state + "\n" +
				"name: " + name + "\n" +
				"nameUrl: " + nameUrl + "\n" +
				"author: " + author + "\n" +
				"authorUrl: " + authorUrl + "\n" +
				"searial: " + serial + "\n" +
				"update: " + update + "\n" +
				"monthlyPopularity: " + monthlyPopularity + "\n" +
				"popularity: " + popularity + "\n" +
				"monthlyVote: " + monthlyVote + "\n" +
				"vote: " + vote + "\n" +
				"voteUrl: " + voteUrl + "\n" +
				"commentUrl " + commentUrl + "\n";
	}
}
