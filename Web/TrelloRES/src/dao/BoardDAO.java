package dao;

import java.sql.Connection;
import java.util.List;

import model.Board;

public interface BoardDAO {
	
	public List<Board> getAll();
	public List<Board>  getAllByUser(long userid);
	public Board get(long id);
	public Board get(String name);
	public long add(Board Board);
	public void save(Board Board);
	public void delete(long id);
	
	public void setConnection(Connection conn);
	void addDefaultLists(Board board);
}
