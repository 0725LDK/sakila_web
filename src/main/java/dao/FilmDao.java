package dao;

import java.util.*;
import java.sql.*;
import vo.*;

public class FilmDao {
	
	//release_year의 최소값
	public int selectMinReleaseYear()
	{
		int minYear = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila","root","java1234");
			String sql = "SELECT MIN(release_year) y FROM film";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next())
			{
				minYear = rs.getInt("y"); 
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				rs.close();
				stmt.close();
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return minYear;
	}
	
	//rating : String[] 여러개의 등급
	public ArrayList<Film> selectFilmList2(int releaseYear, int fromMinute, int toMinute, String[] rating,String searchTitle )
	{
		ArrayList<Film> list = new ArrayList<Film>();
		String sql = "";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila","root","java1234");
			
			if(releaseYear != 0) //releaseYear가 선택되었을때
			{
				if(toMinute > fromMinute) //상영시간 설정 되었을때
				{//between...and...필요
					if(rating == null || rating.length == 5) //등급 전부다 선택+검색어 입력
					{
						sql = "SELECT * FROM film WHERE (title LIKE ?) AND (length BETWEEN ? AND ?) AND (release_year = ?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setInt(2, fromMinute);
						stmt.setInt(3, toMinute);
						stmt.setInt(4, releaseYear);
					}
					else if(rating.length == 4)//등급 4개 선택+검색어 입력
					{
						sql = "SELECT * FROM film WHERE  title LIKE ? AND  rating IN(?,?,?,?) AND length BETWEEN ? AND ? AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setString(5, rating[3]);
						stmt.setInt(6, fromMinute);
						stmt.setInt(7, toMinute);
						stmt.setInt(8, releaseYear);
					}
					else if(rating.length == 3)//등급 3개 선택+검색어 입력
					{
						sql = "SELECT * FROM film WHERE  title LIKE ? AND rating IN(?,?,?)  AND length BETWEEN ? AND ? AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setInt(5, fromMinute);
						stmt.setInt(6, toMinute);
						stmt.setInt(7, releaseYear);
						
					}
					else if(rating.length == 2)//등급 2개 선택+검색어 입력
					{
						sql = "SELECT * FROM film WHERE title LIKE ? AND  rating IN(?,?)  AND length BETWEEN ? AND ? AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setInt(4, fromMinute);
						stmt.setInt(5, toMinute);
						stmt.setInt(6, releaseYear);
					}
					else if(rating.length == 1)//등급 1개 선택+검색어 입력
					{
						sql = "SELECT * FROM film WHERE title LIKE ? AND rating IN(?) AND length BETWEEN ? AND ? AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setInt(3, fromMinute);
						stmt.setInt(4, toMinute);
						stmt.setInt(5, releaseYear);
					}
				}
				//출시연도 선택했으나 상영시간 선택 X
				else if(fromMinute == 0 || toMinute ==0 ) //상영시간 설정 X
				{//between...and...불필요
					if(rating == null || rating.length == 5 ) //영화등급 전체선택 OR 미선택
					{
						sql = "SELECT * FROM film WHERE release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setInt(1, releaseYear);
					}
					else if(rating.length == 4)//영화 등급 4개 선택
					{
						sql = "SELECT * FROM film WHERE rating IN(?,?,?,?) AND release_year = ? ";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, rating[0]);
						stmt.setString(2, rating[1]);
						stmt.setString(3, rating[2]);
						stmt.setString(4, rating[3]);
						stmt.setInt(5, releaseYear);
					}
					else if(rating.length == 3)//영화 등급 3개 선택
					{
						sql = "SELECT * FROM film WHERE rating IN(?,?,?) AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, rating[0]);
						stmt.setString(2, rating[1]);
						stmt.setString(3, rating[2]);
						stmt.setInt(4, releaseYear);
					}
					else if(rating.length == 2)//영화 등급 2개 선택
					{
						sql = "SELECT * FROM film WHERE rating IN(?,?) AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, rating[0]);
						stmt.setString(2, rating[1]);
						stmt.setInt(3, releaseYear);
					}
					else if(rating.length == 1)//영화 등급 1개 선택
					{
						sql = "SELECT * FROM film WHERE rating IN(?) AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, rating[0]);
						stmt.setInt(2, releaseYear);
					}
				}
			}
			else if(releaseYear ==0) //출시연도 선택 X
			{
				if(toMinute > fromMinute) //영화시간 설정
				{//between...and...필요
					if(rating == null || rating.length == 5 )//영화등급 전체선택 OR 미선택
					{
						sql = "SELECT * FROM film WHERE title LIKE ? AND length BETWEEN ? AND ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setInt(2, fromMinute);
						stmt.setInt(3, toMinute);
					}
					else if(rating.length == 4)//영화 등급 4개 선택
					{
						sql = "SELECT * FROM film WHERE  title LIKE ? AND  rating IN(?,?,?,?) AND length BETWEEN ? AND ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setString(5, rating[3]);
						stmt.setInt(6, fromMinute);
						stmt.setInt(7, toMinute);
					}
					else if(rating.length == 3)//영화 등급 3개 선택
					{
						sql = "SELECT * FROM film WHERE  title LIKE ? AND rating IN(?,?,?)  AND length BETWEEN ? AND ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setInt(5, fromMinute);
						stmt.setInt(6, toMinute);
						
					}
					else if(rating.length == 2)//영화 등급 2개 선택
					{
						sql = "SELECT * FROM film WHERE title LIKE ? AND  rating IN(?,?)  AND length BETWEEN ? AND ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setInt(4, fromMinute);
						stmt.setInt(5, toMinute);
					}
					else if(rating.length == 1)//영화 등급 1개 선택
					{
						sql = "SELECT * FROM film WHERE title LIKE ? AND rating IN(?) AND length BETWEEN ? AND ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setInt(3, fromMinute);
						stmt.setInt(4, toMinute);
					}
				}
				//출시연도 선택 X , 시간설정 X
				else if(fromMinute == 0 && toMinute ==0)
				{//between...and...불필요
					if(rating == null || rating.length == 5 )//영화등급 전체선택 OR 미선택
					{
						sql = "SELECT * FROM film";
						stmt = conn.prepareStatement(sql);
					}
					else if(rating.length == 4)//영화 등급 4개 선택
					{
						sql = "SELECT * FROM film WHERE rating IN(?,?,?,?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, rating[0]);
						stmt.setString(2, rating[1]);
						stmt.setString(3, rating[2]);
						stmt.setString(4, rating[3]);
					}
					else if(rating.length == 3)//영화 등급 3개 선택
					{
						sql = "SELECT * FROM film WHERE rating IN(?,?,?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, rating[0]);
						stmt.setString(2, rating[1]);
						stmt.setString(3, rating[2]);
					}
					else if(rating.length == 2)//영화 등급 2개 선택
					{
						sql = "SELECT * FROM film WHERE rating IN(?,?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, rating[0]);
						stmt.setString(2, rating[1]);
					}
					else if(rating.length == 1)//영화 등급 1개 선택
					{
						sql = "SELECT * FROM film WHERE rating IN(?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, rating[0]);
					}
				}
				
			}
			/*
			if(rating != null)
			{
				for(int i=1; i<=rating.length; i+=1)
				{
					stmt.setString(i, rating[i-1]);
					
				}
			}*/
			rs = stmt.executeQuery();
			while(rs.next())
			{
				Film f = new Film();
				f.setFilmId(rs.getInt("film_id"));
				f.setReleaseYear(rs.getString("release_year"));
				f.setTitle(rs.getString("title"));
				f.setRating(rs.getString("rating"));
				f.setLength(rs.getInt("length"));
				list.add(f);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				rs.close();
				stmt.close();
				conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return list;
	}
	
	// col : 컬럼명
	// sort : asc/desc
	public ArrayList<Film> selectFilmListBySearch(String searchCol, String searchWord, String col, String sort)
	{
		ArrayList<Film> list = new ArrayList<Film>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try
		{
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila","root","java1234");
	
			if(searchCol == null || searchWord == null)
			{
				
				sql = "SELECT"
						+ "	film_id filmId,title,description,release_year releaseYear"
						+ "	,language_id languageId,original_language_id originalLanguageId"
						+ "	,rental_duration rentalDuration,rental_rate rentalRate"
						+ "	,LENGTH,replacement_cost replacementCost,rating"
						+ "	,special_features specialFeatures,last_update lastUpdate"
						+ " FROM film"
						+ " ORDER BY " + col +" "+ sort;
				stmt = conn.prepareStatement(sql);
			}
			else
			{
				String whereCol = ""; //검색항목 저장변수
				if(searchCol.equals("titleAndDescription"))
				{
					whereCol = "CONCAT(title,' ',description)";
				}
				else
				{
					whereCol = searchCol;
				}
				sql = "SELECT"
						+ "	film_id filmId,title,description,release_year releaseYear"
						+ "	,language_id languageId,original_language_id originalLanguageId"
						+ "	,rental_duration rentalDuration,rental_rate rentalRate"
						+ "	,LENGTH,replacement_cost replacementCost,rating"
						+ "	,special_features specialFeatures,last_update lastUpdate"
						+ " FROM film"
						+ " WHERE "+whereCol+" LIKE ?"
						+ " ORDER BY " + col +" "+ sort;
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+searchWord+"%");	
			}
			
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Film f  = new Film();
				f.setFilmId(rs.getInt("filmId"));
				f.setTitle(rs.getString("title"));
				f.setDescription(rs.getString("description"));
				f.setReleaseYear(rs.getString("releaseYear"));
				f.setLanguageId(rs.getInt("languageId"));
				f.setOriginalLanguageId(rs.getInt("originalLanguageId"));
				f.setRentalDuration(rs.getInt("rentalDuration"));
				f.setRentalRate(rs.getDouble("rentalRate"));
				f.setLength(rs.getInt("length"));
				f.setReplacementCost(rs.getDouble("replacementCost"));
				f.setRating(rs.getString("rating"));
				f.setSpecialFeatures(rs.getString("specialFeatures"));
				f.setLastUpdate(rs.getString("lastUpdate"));
				list.add(f);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				rs.close();
				stmt.close();
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	//상영시간 설정해서 영화 리스트 구하기
	public ArrayList<Film> runningTime(int startMin, int endMin, String col, String sort)
	{
		ArrayList<Film> list = new ArrayList<Film>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try
		{
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila","root","java1234");
				
				sql = "SELECT"
						+ "	film_id filmId,title,description,release_year releaseYear"
						+ "	,language_id languageId,original_language_id originalLanguageId"
						+ "	,rental_duration rentalDuration,rental_rate rentalRate"
						+ "	,LENGTH,replacement_cost replacementCost,rating"
						+ "	,special_features specialFeatures,last_update lastUpdate"
						+ " FROM film"
						+ "	WHERE LENGTH>" +startMin+ " AND LENGTH<"+endMin+" "
						+ " ORDER BY " + col +" "+ sort;
				stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
					System.out.println("성공");
			
			while(rs.next())
			{
				Film f  = new Film();
				f.setFilmId(rs.getInt("filmId"));
				f.setTitle(rs.getString("title"));
				f.setDescription(rs.getString("description"));
				f.setReleaseYear(rs.getString("releaseYear"));
				f.setLanguageId(rs.getInt("languageId"));
				f.setOriginalLanguageId(rs.getInt("originalLanguageId"));
				f.setRentalDuration(rs.getInt("rentalDuration"));
				f.setRentalRate(rs.getDouble("rentalRate"));
				f.setLength(rs.getInt("length"));
				f.setReplacementCost(rs.getDouble("replacementCost"));
				f.setRating(rs.getString("rating"));
				f.setSpecialFeatures(rs.getString("specialFeatures"));
				f.setLastUpdate(rs.getString("lastUpdate"));
				list.add(f);
				System.out.println("성공");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				rs.close();
				stmt.close();
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return list;
	}
}