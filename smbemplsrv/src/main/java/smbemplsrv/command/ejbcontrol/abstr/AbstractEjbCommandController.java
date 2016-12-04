package smbemplsrv.command.ejbcontrol.abstr;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractEjbCommandController<T> {
	@PersistenceContext(unitName = "smbemplsrv")
	protected EntityManager entityManager;

	public T update(T value) {
		try {
			entityManager.merge(value);
			return value;
		} catch (Exception e) {
			System.out.println(e.getMessage()); // FIXME log to file
			return null;
		}
	}

	public T insert(T value) {
		try {
			entityManager.persist(value);
			return value;
		} catch (Exception e) {
			System.out.println(e.getMessage());// FIXME log to file
			return null;
		}
	}

	public boolean delete(T value) {
		try {
			T toBeRemoved = entityManager.merge(value);
			entityManager.remove(toBeRemoved);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());// FIXME log to file
			return false;
		}
	}

}
