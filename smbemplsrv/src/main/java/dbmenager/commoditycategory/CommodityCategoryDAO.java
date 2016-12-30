package dbmenager.commoditycategory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.CommodityCategory;



@Stateless
@LocalBean
public class CommodityCategoryDAO  extends AbstractDAO<CommodityCategory> {

	public CommodityCategoryDAO(){
		this.dbEntity = CommodityCategory.class;
	}
}
