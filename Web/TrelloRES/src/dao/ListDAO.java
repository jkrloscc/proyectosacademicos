package dao;

import java.sql.Connection;
import java.util.List;



public interface ListDAO {
	
	public List<model.List> getAll();
	public List<model.List>  getAllByBoard(long boardid);
	public model.List get(long id);	
	public long add(model.List list);
	public void save(model.List list);
	public void delete(long id);
	
	public void setConnection(Connection conn);
}
