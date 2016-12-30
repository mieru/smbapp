package dbmenager.companyinfo;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.CompanyInfo;



@Stateless
@LocalBean
public class CompanyInfoDAO  extends AbstractDAO<CompanyInfo> {

	public CompanyInfoDAO(){
		this.dbEntity = CompanyInfo.class;
	}
}
