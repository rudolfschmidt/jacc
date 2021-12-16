package com.rudolfschmidt.jacc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;

@RequiredArgsConstructor
@Getter
@Setter
public class Posting {
	private final String account;
	private String expression;
	private String currency;
	private BigDecimal amount;
	private Deque<Comment> comments = new LinkedList<>();
}
