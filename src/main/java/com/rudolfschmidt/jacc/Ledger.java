package com.rudolfschmidt.jacc;

import com.rudolfschmidt.jacc.model.Comment;
import com.rudolfschmidt.jacc.model.Posting;
import com.rudolfschmidt.jacc.model.Transaction;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Ledger {

	private final Deque<Transaction> transactions = new LinkedList<>();

	public void parseLine(String line) {
		Matcher matcher;
		if ((matcher = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}").matcher(line)).find()) {
			String date = line.substring(matcher.start(), matcher.end());
			String state = null;
			String description = null;
			line = line.substring(matcher.end());
			if ((matcher = Pattern.compile("^\s[*!]").matcher(line)).find()) {
				state = line.substring(matcher.start(), matcher.end()).substring(1);
				line = line.substring(matcher.end());
			}
			if ((matcher = Pattern.compile("^\s.*").matcher(line)).find()) {
				description = line.substring(matcher.start(), matcher.end()).substring(1);
			}
			final var transaction = Transaction.builder()
					.date(LocalDate.parse(date))
					.state(state)
					.description(description)
					.build();
			transactions.add(transaction);
		}
		if ((matcher = Pattern.compile("^\s\s+|\t+").matcher(line)).find()) {
			line = line.substring(matcher.end());
			if ((matcher = Pattern.compile("^; ").matcher(line)).find()) {
				line = line.substring(matcher.end());
				if (transactions.getLast().getPostings().isEmpty()) {
					transactions.getLast().getComments().add(new Comment(line));
				} else {
					transactions.getLast().getPostings().getLast().getComments().add(new Comment(line));
				}
			} else if ((matcher = Pattern.compile("^\\S+").matcher(line)).find()) {
				final var posting = new Posting(line.substring(matcher.start(), matcher.end()));
				transactions.getLast().getPostings().add(posting);
				line = line.substring(matcher.end());
				if ((matcher = Pattern.compile("^\s\s+|\t+").matcher(line)).find()) {
					line = line.substring(matcher.end());
					transactions.getLast().getPostings().getLast().setExpression(line);
				}
			}
		}
	}
}
