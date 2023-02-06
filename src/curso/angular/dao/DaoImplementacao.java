package curso.angular.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.angular.hibernate.HibernateUtil;
/**
 * Implementa��o dos metodos padr�es da interface de acesso ao banco e opera��es
 * @author alex
 * @param <T>
 */
@Transactional(noRollbackFor = Exception.class)
@Service
public abstract class DaoImplementacao<T> implements DaoInterface<T> {

	private Class<T> persistenceClass;

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public DaoImplementacao(Class<T> persistenceClass) {
		super();
		this.persistenceClass = persistenceClass;
	}

	@Override
	public void salvar(T objeto) throws Exception {
		sessionFactory.getCurrentSession().save(objeto);
		 sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void deletar(T objeto) throws Exception {
		sessionFactory.getCurrentSession().delete(objeto);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void atualizar(T objeto) throws Exception {
		sessionFactory.getCurrentSession().update(objeto);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void salvarOuAtualizar(T objeto) throws Exception {
		sessionFactory.getCurrentSession().saveOrUpdate(objeto);
		sessionFactory.getCurrentSession().flush();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T merge(T objeto) throws Exception {
		 T t =  (T) sessionFactory.getCurrentSession().merge(objeto);
		 sessionFactory.getCurrentSession().flush();
		 return t;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> lista() throws Exception {
		Criteria criteria =sessionFactory.getCurrentSession().createCriteria(persistenceClass);
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}
	
	@Override
	public T loadObjeto(Long codigo) throws Exception { 
		return (T) sessionFactory.getCurrentSession().get(persistenceClass, codigo);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Class<T> getPersistenceClass() {
		return persistenceClass;
	}
	
}
