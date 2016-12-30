package dbmenager.order;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.Order;

@Stateless
@LocalBean
public class OrderDAO extends AbstractDAO<Order> {

	public OrderDAO() {
		this.dbEntity = Order.class;
	}

	public String generujNumerZgloszenia() {
		// TODO Auto-generated method stub
		return null;
	}
}
