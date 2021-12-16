package com.rudolfschmidt.jacc.commands;

import com.rudolfschmidt.jacc.model.Transaction;

import java.util.Deque;
import java.util.Optional;

public class Printer {
	public void print(final Deque<Transaction> transactions) {
		for (final var transaction : transactions) {
			print(transaction);
		}
	}
	public void print(final Transaction transaction) {
		final var output = new StringBuilder();
		Optional.ofNullable(transaction.getDate()).ifPresent(output::append);
		Optional.ofNullable(transaction.getState()).ifPresent(state -> output.append(" ").append(state));
		Optional.ofNullable(transaction.getDescription()).ifPresent(desc -> output.append(" ").append(desc));
		for (final var comment : transaction.getComments()) {
			output.append(String.format("\n\t; %s",comment.getComment()));
		}
		for (final var posting : transaction.getPostings()) {
			if(Optional.ofNullable(posting.getExpression()).isPresent()){
				output.append(String.format("\n\t%-90s%20s",posting.getAccount(),posting.getExpression()));
			}else{
				output.append(String.format("\n\t%s",posting.getAccount()));
			}
			for (final var comment : posting.getComments()) {
				output.append(String.format("\n\t; %s",comment.getComment()));
			}
		}
		output.append("\n");
		System.out.println(output);
	}
}
