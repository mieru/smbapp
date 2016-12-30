package dbmenager.commodityimage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.CommoditiesImage;

@Stateless
@LocalBean
public class CommodityImageDAO extends AbstractDAO<CommoditiesImage> {
	
	public CommodityImageDAO(){
		this.dbEntity = CommoditiesImage.class;
	}
}
