package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Card;

public class JDBCCardDAOImpl implements CardDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCCardDAOImpl.class.getName());

	@Override
	public List<Card> getAll() {

		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<Card> cards = new ArrayList<Card>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.Card");

			while ( rs.next() ) {
				Card card = new Card();
				card.setId(rs.getLong("id"));
				card.setName(rs.getString("name"));
				card.setDescription(rs.getString("description"));
				card.setList(rs.getLong("list"));
				cards.add(card);
				logger.info("fetching Card: "+card.getId()+" "+card.getName()+" "+card.getList()+" "+card.getDescription());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cards;
	}

	@Override
	public List<Card> getAllByList(long listid) {
		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<Card> cards = new ArrayList<Card>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.Card WHERE list="+listid);

			while ( rs.next() ) {
				Card card = new Card();
				card.setId(rs.getLong("id"));
				card.setName(rs.getString("name"));
				card.setDescription(rs.getString("description"));
				card.setList(rs.getLong("list"));
				cards.add(card);
				logger.info("fetching Card: "+card.getId()+" "+card.getName()+" "+card.getList());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cards;
	}
	
	@Override
	public Card get(long id) {
		if (conn == null) return null;
		
		Card card = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.Card WHERE id ="+id);			 
			if (!rs.next()) return null; 
			card  = new Card();	 
			card.setId(rs.getLong("id"));
			card.setName(rs.getString("name"));
			card.setDescription(rs.getString("description"));
			card.setList(rs.getLong("list"));
			logger.info("fetching Card: "+card.getId()+" "+card.getName()+" "+card.getList()+" "+card.getDescription());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return card;
	}
	
	

	@Override
	public long add(Card card) {
		long id=-1;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Card (name,list) VALUES('"+card.getName()+"','"+card.getList()+"')",Statement.RETURN_GENERATED_KEYS);
				
				
				ResultSet genKeys = stmt.getGeneratedKeys();
				if (genKeys.next())
				    id = genKeys.getInt(1);
								
				logger.info("creating Card: "+card.getName()+" - list "+card.getList());
			} catch (SQLException e) {
			  e.printStackTrace();
			}
		}
	return id;
	}

	@Override
	public void save(Card card) {
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE Card SET description='"+card.getDescription()+"', name='"+card.getName()+"', list='"+card.getList()+"' WHERE id = "+card.getId());
				logger.info("updating Card: "+card.getId()+" "+card.getName()+" "+card.getList());
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
				stmt.executeUpdate("DELETE FROM Card WHERE id ="+id);
				logger.info("updating Card: "+id);
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
