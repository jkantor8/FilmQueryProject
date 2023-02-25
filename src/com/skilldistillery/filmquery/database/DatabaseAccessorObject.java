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
		String sql = "SELECT * FROM film WHERE id = ?";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		
		if (filmResult.next()) {
		film = new Film(filmResult.getInt("id"), filmResult.getString("title"), filmResult.getString("description"),
		filmResult.getInt("release_Year"), filmResult.getInt("language_Id"), filmResult.getString("length"),
		filmResult.getDouble("replacement_Cost"), filmResult.getString("rating"),
		filmResult.getString("special_Features"));
		}
		}catch(Exception e) {
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
			actor.setId(actorResult.getInt(1));
			actor.setFirstName(actorResult.getString(2));
			actor.setLastName(actorResult.getString(3));
		}
		// ...
		return actor;
	}



	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
