package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class JDBCListDAOImpl implements ListDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCListDAOImpl.class.getName());

	@Override
	public List<model.List> getAll() {

		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<model.List> lists = new ArrayList<model.List>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM List");

			while ( rs.next() ) {
				model.List list = new model.List();
				list.setId(rs.getLong("id"));
				list.setName(rs.getString("name"));
				list.setBoard(rs.getLong("board"));
				lists.add(list);
				logger.info("fetching List: "+list.getId()+" "+list.getName()+" "+list.getBoard());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lists;
	}

	@Override
	public List<model.List> getAllByBoard(long boardid) {
		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<model.List> lists = new ArrayList<model.List>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM List WHERE board="+boardid);

			while ( rs.next() ) {
				model.List list = new model.List();
				list.setId(rs.getLong("id"));
				list.setName(rs.getString("name"));
				list.setBoard(rs.getLong("board"));
				lists.add(list);
				logger.info("fetching List: "+list.getId()+" "+list.getName()+" "+list.getBoard());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lists;
	}
	
	
	@Override
	public model.List get(long id) {
		if (conn == null) return null;
		
		model.List list = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM List WHERE id ="+id);			 
			if (!rs.next()) return null; 
			list  = new model.List();	 
			list.setId(rs.getLong("id"));
			list.setName(rs.getString("name"));
			list.setBoard(rs.getLong("board"));
			logger.info("fetching List: "+list.getId()+" "+list.getName()+" "+list.getBoard());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	

	@Override
	public long add(model.List list) {
		long id=-1;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO List (name,board) VALUES('"+list.getName()+"','"+list.getBoard()+"')",Statement.RETURN_GENERATED_KEYS);
				
				ResultSet genKeys = stmt.getGeneratedKeys();
				
				if (genKeys.next())
				    id = genKeys.getInt(1);
				
				logger.info("creating List: "+list.getName()+" - owner "+list.getBoard());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public void save(model.List list) {
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE List SET name='"+list.getName()+"', board='"+list.getBoard()+"' WHERE id = "+list.getId());
				logger.info("updating List: "+list.getId()+" "+list.getName()+" "+list.getBoard());
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
				stmt.executeUpdate("DELETE FROM List WHERE id ="+id);
				logger.info("updating List: "+id);
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
