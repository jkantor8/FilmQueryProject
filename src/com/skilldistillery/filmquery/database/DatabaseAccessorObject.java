package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";

	private static final String user = "student";
	private static final String pass = "student";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

//	@Override
//	public List<Film> findFilmsByActorId(int actorId) {
//		  List<Film> films = new ArrayList<>();
//		  
//		  try {
//		    Connection conn = DriverManager.getConnection(URL, user, pass);
//		    
//		    String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
//		                sql += " rental_rate, length, replacement_cost, rating, special_features "
//		               +  " FROM film JOIN film_actor ON film.id = film_actor.film_id "
//		               + " WHERE actor_id = ?";
//		                
//		    // SQL Injection:
////            String noOKSQL = "SELECT id, title, description, release_year, language_id, rental_duration, ";
////            sql += " rental_rate, length, replacement_cost, rating, special_features "
////            		+  " FROM film JOIN film_actor ON film.id = film_actor.film_id "
////            		+ " WHERE actor_id = " + actorId;  // --" OR 1=1
//            
//		    PreparedStatement stmt = conn.prepareStatement(sql);
//		    
//		    // bind stmt
//		    stmt.setInt(1, actorId);
//		    
//		    ResultSet rs = stmt.executeQuery();
//		    
//			while (rs.next()) {
//				int filmId = rs.getInt(1);
//				String title = rs.getString(2);
//				String desc = rs.getString(3);
//				short releaseYear = rs.getShort(4);
//				int langId = rs.getInt("language_id");
//				int rentDur = rs.getInt("rental_duration");
//				double rate = rs.getDouble(7);
//				int length = rs.getInt(8);
//				double repCost = rs.getDouble(9);
//				String rating = rs.getString(10);
//				String features = rs.getString("rating");
//				
//				Film film = new Film(length, features, features, length, length, features, repCost, features, features); 
//				//filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,features);
//				
//				  films.add(film);
//		    }
//		    rs.close();
//		    stmt.close();
//		    conn.close();
//		  } catch (SQLException e) {
//		    e.printStackTrace();
//		  }
//		  
//		  return films;
//		  
//		}
	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		try {
//			String sql = "SELECT * FROM film WHERE id = ?";
			String sql = "SELECT title, release_year, rating, description FROM film WHERE id = ?";
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();

			if (filmResult.next()) {
				film = new Film();
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getInt("release_Year"));
				film.setLanguageId(filmResult.getInt("language_Id"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_Cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_Features"));
				film.setLanguage(filmResult.getString("language"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {

		Actor actor = null;
		// ...
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, actorId);

		ResultSet actorResult = stmt.executeQuery();

		if (actorResult.next()) {
			actor = new Actor(); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt("id"));
			actor.setFirstName(actorResult.getString("first_name"));
			actor.setLastName(actorResult.getString("last_name"));
		}
		// ...
		actorResult.close();
		conn.close();
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actors = new ArrayList<>();

		String sql = "SELECT actor.id, actor.first_name, actor.last_name FROM film JOIN film_actor ON film.id = film_actor.film_id JOIN actor ON film_actor.actor_id = actor.id WHERE film.id = ?";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, filmId);

		ResultSet actorByFilmIdResult = stmt.executeQuery();

		if (actorByFilmIdResult.next()) {

			int id = actorByFilmIdResult.getInt("id");
			String firstName = actorByFilmIdResult.getString("first_name");
			String lastName = actorByFilmIdResult.getString("last_name");

			Actor actor = new Actor(id, firstName, lastName);
			actors.add(actor);
		}
		// ...
		actorByFilmIdResult.close();
		conn.close();
		return actors;
	}

	@Override
	public List<Film> findFilmsByActorId(int actorId) {
			List<Film> filmList = new ArrayList<>();
		
			try {
//				String sql = "SELECT * FROM film WHERE id = ?";
				String sql = "SELECT film.title FROM actor JOIN film_actor ON actor.id = film_actor.actor_id JOIN film ON film_actor.film_id = film.id WHERE actor.id = ?";
				Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sql);

				stmt.setInt(1, actorId);
				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					
					int filmId = rs.getInt("id");
					String title = rs.getString("title");
					String description = rs.getString("description");
					Integer releaseYear = rs.getInt("release_Year");
					int languageId = rs.getInt("language_Id");
					Integer length = rs.getInt("length");
					double replacementCost = rs.getDouble("replacement_Cost");
					String rating = rs.getString("rating");
					String specialFeatures =rs.getString("special_Features");
					String language = rs.getString("language");
					
					Film film = new Film(filmId, title, description, releaseYear, languageId, length, replacementCost, rating, specialFeatures, language);
					filmList.add(film);
				}
				rs.close();
				stmt.close();
				conn.close();
				
			} catch (Exception e) {
				System.out.println(e);
			}
			return filmList;
		}
	
	public List<Film> findFilmsByKeyword(String keyword) {
		List<Film> filmList = new ArrayList<>();
		List<Actor> actors = new ArrayList<>();
		
		
		try {
//			String sql = "SELECT * FROM film WHERE id = ?";
			String sql = "SELECT title, release_year, rating, description FROM film WHERE id = ?";
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");

			stmt.setString(1, keyword);
			ResultSet filmResult = stmt.executeQuery();

			if (filmResult.next()) {
				Film film = new Film();
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getInt("release_Year"));
				film.setLanguageId(filmResult.getInt("language_Id"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_Cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_Features"));
				film.setLanguage(filmResult.getString("language"));
				
				actors = findActorsByFilmId(film.getId());
				film.setActors(actors);
				filmList.add(film);
				
			}
			filmResult.close();
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return filmList;
	}
}
