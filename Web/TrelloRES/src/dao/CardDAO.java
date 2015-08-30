package dao;

import java.sql.Connection;
import java.util.List;

import model.Card;

public interface CardDAO {
	
	public List<Card> getAll();
	public List<Card>  getAllByList(long listid);
	public Card get(long id);	
	public long add(Card Card);
	public void save(Card Card);
	public void delete(long id);
	
	public void setConnection(Connection conn);
}
