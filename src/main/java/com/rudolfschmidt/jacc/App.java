package com.rudolfschmidt.jacc;

import com.rudolfschmidt.jacc.commands.Printer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

	public static void main(String[] args) throws IOException {
		readDirectory(Paths.get(args[0]));
	}

	private static void readDirectory(final Path path) throws IOException {
		try (final var stream = Files.newDirectoryStream(path)) {
			for (final var file : stream) {
				if (Files.isDirectory(file)) {
					readDirectory(file);
				} else if (Files.isRegularFile(file) && isLedgerFile(file)) {
					final var fileName = file.getFileName().toString();
					if (fileName.endsWith(".ledger")) {
						readFile(file);
					}
				}
			}
		}
	}

	private static boolean isLedgerFile(final Path file) {
		return file.getFileName().toString().endsWith(".ledger");
	}

	private static void readFile(final Path file) throws IOException {
		final var ledger = new Ledger();
		for (final var line : Files.readAllLines(file)) {
			ledger.parseLine(line);
		}
		final var printer = new Printer();
		printer.print(ledger.getTransactions());
	}
}
