package command.ejbcontrol.zamowieniesprzedaz;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import command.ejbcontrol.abst.AbstractEjbCommandController;
import dbmodel.ZamowienieSprzedaz;

@Stateless
@LocalBean
public class ZamowienieSprzedEjbCommandContoller extends AbstractEjbCommandController<ZamowienieSprzedaz> {

}
