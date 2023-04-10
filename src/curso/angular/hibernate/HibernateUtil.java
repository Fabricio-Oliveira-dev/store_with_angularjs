package curso.angular.hibernate;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Responsável por estabelecer a conexão do hibernate
public class HibernateUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private static SessionFactory sessionFactory = buildSessionFactory();

	/**
	 * Responsável por ler o arquivo de configuração hibernate.cfg.xml
	 * @return SessionFactory
	 */
	private static SessionFactory buildSessionFactory() {
		try {
			if (sessionFactory == null) {
				sessionFactory = (new Configuration()).configure().buildSessionFactory();
			}
			return sessionFactory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("Erro ao criar conexão SessionFactory");
		}
	}

	//Retorna o sessionFactory corrente.
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
