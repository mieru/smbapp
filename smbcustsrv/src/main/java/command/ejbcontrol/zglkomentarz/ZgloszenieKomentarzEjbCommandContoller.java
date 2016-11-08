package command.ejbcontrol.zglkomentarz;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import command.ejbcontrol.abst.AbstractEjbCommandController;
import dbmodel.ZgloszenieKomentarz;

@Stateless
@LocalBean
public class ZgloszenieKomentarzEjbCommandContoller extends AbstractEjbCommandController<ZgloszenieKomentarz> {

}