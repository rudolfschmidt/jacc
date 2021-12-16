package com.rudolfschmidt.jacc.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Deque;
import java.util.LinkedList;

@Builder
@Getter
public class Transaction {
	private final LocalDate date;
	private String state;
	private String description;
	private final Deque<Comment> comments = new LinkedList<>();
	private final Deque<Posting> postings = new LinkedList<>();
}


