package dbmenager.mailconfiguration;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.SysEmailConfiguration;


@Stateless
@LocalBean
public class SystemMailCongifurationDAO  extends AbstractDAO<SysEmailConfiguration> {

	public SystemMailCongifurationDAO(){
		this.dbEntity = SysEmailConfiguration.class;
	}
}
