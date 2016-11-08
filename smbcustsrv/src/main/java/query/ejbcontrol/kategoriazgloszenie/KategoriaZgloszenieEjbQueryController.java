package query.ejbcontrol.kategoriazgloszenie;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.KategoiaZgloszenia;
import query.ejbcontrol.abst.AbstractEjbQueryController;


@Stateless
@LocalBean
public class KategoriaZgloszenieEjbQueryController  extends AbstractEjbQueryController<KategoiaZgloszenia> {

	public KategoriaZgloszenieEjbQueryController(){
		this.dbEntity = KategoiaZgloszenia.class;
	}
}
