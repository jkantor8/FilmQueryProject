package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//		app.test();
    app.launch();
	}

//  private void test() {
//    Film film = db.findFilmById(1);
//    System.out.println(film);
//    
//  }

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		while (true) {
		System.out.println("USER MENU - Make a selection");
		System.out.println("1. Film lookup by ID");
		System.out.println("2. Film lookup by keyword");
		System.out.println("3. Exit app");

		int userChoice = input.nextInt();

		
			switch (userChoice) {
		case 1:
			byFilmId(input);
			break;
		case 2:
			byKeyword(input);	
			break;
		case 3:
			System.out.println("Exiting app...GOODBYE!!!");
			return;

		default:
			System.out.println("Invalid menu option");;
		}
	}
	
}
	
	private void byFilmId(Scanner input) {
		System.out.println("Enter a film ID");
		try {
			int filmId = input.nextInt();
			Film film = db.findFilmById(filmId);
			if (film != null) {
				System.out.println(film);
			} else {
				System.out.println("Invalid Film ID");
			}
		} catch (Exception e) {
			System.err.println("Invalid input type");
			input.next();
			startUserInterface(input);
		}
	}
	
	private void byKeyword(Scanner input) {
		System.out.println("Enter a keyword");
		try {
			String keyword = input.next();
			List<Film> films = db.findFilmsByKeyword(keyword);
			if (films.size() > 0) {
				for (Film film : films) {
					System.out.println(film);
				}
			} else {
				System.out.println("No match found!");
			}
		} catch (Exception e) {
			System.err.println("Invalid input type");
			input.next();
			startUserInterface(input);
		}
	}
}