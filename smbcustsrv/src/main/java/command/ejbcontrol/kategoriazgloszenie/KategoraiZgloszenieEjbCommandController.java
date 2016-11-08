package command.ejbcontrol.kategoriazgloszenie;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import command.ejbcontrol.abst.AbstractEjbCommandController;
import dbmodel.KategoiaZgloszenia;

@Stateless
@LocalBean
public class KategoraiZgloszenieEjbCommandController extends AbstractEjbCommandController<KategoiaZgloszenia> {

}
