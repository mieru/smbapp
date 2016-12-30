package dbmenager.warehouse;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.Warehouse;

@Stateless
@LocalBean
public class WarehouseDAO extends AbstractDAO<Warehouse> {
	
	public WarehouseDAO(){
		this.dbEntity = Warehouse.class;
	}
}
