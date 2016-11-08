package command.ejbcontrol.wiadomosczamsprz;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import command.ejbcontrol.abst.AbstractEjbCommandController;
import dbmodel.WiadomoscZamSprzedaz;

@Stateless
@LocalBean
public class WiadZamSprzedEjbCommandController extends AbstractEjbCommandController<WiadomoscZamSprzedaz>{

}
