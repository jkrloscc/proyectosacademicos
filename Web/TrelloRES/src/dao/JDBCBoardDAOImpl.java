package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Board;

public class JDBCBoardDAOImpl implements BoardDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCBoardDAOImpl.class.getName());

	@Override
	public List<Board> getAll() {

		logger.info("starting");
		if (conn == null) return null;

		ArrayList<Board> boards = new ArrayList<Board>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.Board");

			while ( rs.next() ) {
				Board board = new Board();
				board.setId(rs.getLong("id"));
				board.setName(rs.getString("name"));
				board.setOwner(rs.getLong("owner"));
				boards.add(board);
				logger.info("fetching Board: "+board.getId()+" "+board.getName()+" "+board.getOwner());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return boards;
	}

	@Override
	public List<Board> getAllByUser(long userid) {
		logger.info("starting");
		if (conn == null) return null;

		ArrayList<Board> boards = new ArrayList<Board>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.Board WHERE owner="+userid);

			while ( rs.next() ) {
				Board board = new Board();
				board.setId(rs.getLong("id"));
				board.setName(rs.getString("name"));
				board.setOwner(rs.getLong("owner"));
				boards.add(board);
				logger.info("fetching Board: "+board.getId()+" "+board.getName()+" "+board.getOwner());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return boards;
	}

	@Override
	public Board get(long id) {
		if (conn == null) return null;

		Board board = null;		

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.Board WHERE id ="+id);			 
			if (!rs.next()) return null; 
			board  = new Board();	 
			board.setId(rs.getLong("id"));
			board.setName(rs.getString("name"));
			board.setOwner(rs.getLong("owner"));
			logger.info("fetching Board: "+board.getId()+" "+board.getName()+" "+board.getOwner());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return board;
	}
	
	
	@Override
	public Board get(String name) {
		if (conn == null) return null;

		Board board = null;		

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.Board WHERE name LIKE"+"'"+name+"'");			 
			if (!rs.next()) return null; 
			board  = new Board();	 
			board.setId(rs.getLong("id"));
			board.setName(rs.getString("name"));
			board.setOwner(rs.getLong("owner"));
			logger.info("fetching Board: "+board.getId()+" "+board.getName()+" "+board.getOwner());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return board;
	}



	@Override
	public long add(Board board) {
		long id=-1;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Board (name,owner) VALUES('"+board.getName()+"','"+board.getOwner()+"')",Statement.RETURN_GENERATED_KEYS);
				
				ResultSet genKeys = stmt.getGeneratedKeys();
				
				if (genKeys.next())
				    id = genKeys.getInt(1);
				
				logger.info("creating Board: "+board.getName()+" - owner "+board.getOwner());
			} catch (SQLException e) {e.printStackTrace();}
		}
		return id;
	}
	
	
	@Override
	public void addDefaultLists(Board board) {
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();

				stmt.executeUpdate("INSERT INTO List (name,board) VALUES('"+"To Do"+"','"+board.getId()+"')");
				stmt.executeUpdate("INSERT INTO List (name,board) VALUES('"+"Doing"+"','"+board.getId()+"')");
				stmt.executeUpdate("INSERT INTO List (name,board) VALUES('"+"Done"+"','"+board.getId()+"')");

				logger.info("creating Default Lists: ");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void save(Board board) {
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE Board SET name='"+board.getName()+"', owner='"+board.getOwner()+"' WHERE id = "+board.getId());
				logger.info("updating Board: "+board.getId()+" "+board.getName()+" "+board.getOwner());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void delete(long id) {
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Board WHERE id ="+id);
				logger.info("updating Board: "+id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}


}
