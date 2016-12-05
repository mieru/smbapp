package smbemplsrv.query.ejbcontrol.abstr;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public abstract class AbstractEjbQueryController<T> {
	@PersistenceContext(unitName = "smbemplsrv")
	protected EntityManager entityManager;
	protected Class<T> dbEntity;

	
	public List<T> findEntity(T value) {
		return findEntity(value, null);
	}
	
	public List<T> findEntity(T value, StringBuilder additonalCondition) {
		try {
			StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT " + value.getClass().getSimpleName() + " FROM "
					+ value.getClass().getSimpleName() + " " + value.getClass().getSimpleName() + " WHERE 1=1");
			for (Field field : value.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.get(value) != null && !Modifier.isStatic(field.getModifiers())
						&& !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
					if (field.getType().isAssignableFrom(String.class) || field.getType().isAssignableFrom(Date.class)
							|| field.getType().isAssignableFrom(BigInteger.class)) {
						queryString.append(" AND " + value.getClass().getSimpleName() + "." + field.getName() + " = '"
								+ field.get(value) + "'");
					} else {
						queryString.append(" AND " + value.getClass().getSimpleName() + "." + field.getName() + " = "
								+ field.get(value));
					}
				}
			}
			if(additonalCondition != null)
				queryString.append(additonalCondition.toString());
			System.out.println(queryString.toString());
			List<T> queryResult = entityManager.createQuery(queryString.toString()).getResultList();
			return queryResult;
		} catch (Exception e) {
			System.out.println("factory.abstr.AbstractFactory.findEntity() - Blad w generowaniu zapytania");
			return null;
		}
	}

	public T findEntityByID(Integer primaryKey) {
		String sql = "SELECT " + dbEntity.getSimpleName() + " FROM " + dbEntity.getSimpleName() + " "
				+ dbEntity.getSimpleName() + " WHERE " + dbEntity.getSimpleName() + "."
				+ dbEntity.getDeclaredFields()[1].getName() + " = " + primaryKey;
		System.out.println("LOG[sql findEntityByID]: " + sql);
		T queryResault = (T) entityManager.createQuery(sql).getSingleResult();
		return queryResault;
	}

	public List<T> findAll() {
		TypedQuery<T> queryResault = entityManager.createNamedQuery(dbEntity.getSimpleName() + ".findAll", dbEntity);
		return queryResault.getResultList();
	}

}
