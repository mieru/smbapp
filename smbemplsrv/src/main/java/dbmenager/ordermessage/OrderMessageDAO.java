package dbmenager.ordermessage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.OrderMessage;



@Stateless
@LocalBean
public class OrderMessageDAO  extends AbstractDAO<OrderMessage> {

	public OrderMessageDAO(){
		this.dbEntity = OrderMessage.class;
	}
}
