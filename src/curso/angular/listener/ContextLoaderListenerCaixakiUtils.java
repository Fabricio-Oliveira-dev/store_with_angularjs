package curso.angular.listener;

import java.io.Serializable;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

//Acessa todos os objetos criados pelo contexto do spring
public class ContextLoaderListenerCaixakiUtils extends
		org.springframework.web.context.ContextLoaderListener implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static WebApplicationContext getWac() {
		return WebApplicationContextUtils
				.getWebApplicationContext(getCurrentWebApplicationContext().getServletContext());
	}
	
	/**
	 * Retorna um obejeto do contexto Spring de acordo com seu nome
	 * @return Object
	 */
	public static Object getBean(String idNomeBean) {
		return getWac().getBean(idNomeBean);
	}
	
	/**
	 * Retorna um obejeto do contexto Spring de acordo com sua Class
	 * @return Object
	 */
	public static Object getBean(Class<?> classe) {
		return getWac().getBean(classe);
	}
}
