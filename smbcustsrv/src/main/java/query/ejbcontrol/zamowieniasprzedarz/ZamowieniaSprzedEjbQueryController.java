package query.ejbcontrol.zamowieniasprzedarz;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.ZamowienieSprzedaz;
import query.ejbcontrol.abst.AbstractEjbQueryController;

@Stateless
@LocalBean
public class ZamowieniaSprzedEjbQueryController extends AbstractEjbQueryController<ZamowienieSprzedaz> {

	public ZamowieniaSprzedEjbQueryController(){
		this.dbEntity = ZamowienieSprzedaz.class;
	}
}