package command.ejbcontrol.uzytkownik;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import command.ejbcontrol.abst.AbstractEjbCommandController;
import dbmodel.Uzytkownik;

@Stateless
@LocalBean
public class UzytkownikEjbCommandController extends AbstractEjbCommandController<Uzytkownik> {

}
