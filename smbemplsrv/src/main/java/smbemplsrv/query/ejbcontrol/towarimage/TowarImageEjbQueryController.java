package smbemplsrv.query.ejbcontrol.towarimage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.TowarImage;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;

@Stateless
@LocalBean
public class TowarImageEjbQueryController extends AbstractEjbQueryController<TowarImage> {
	
	public TowarImageEjbQueryController(){
		this.dbEntity = TowarImage.class;
	}
}
