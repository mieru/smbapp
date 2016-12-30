package dbmenager.commodity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.Commodity;

@Stateless
@LocalBean
public class CommodityDAO extends AbstractDAO<Commodity> {
	
	public CommodityDAO(){
		this.dbEntity = Commodity.class;
	}
}
