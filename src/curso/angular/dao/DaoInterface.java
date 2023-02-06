package curso.angular.dao;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface destinada ao metodos padr�o de opera��es no banco
 * @author alex
 * @param <T>
 */
@Transactional(noRollbackFor = Exception.class)
@Service
public interface DaoInterface<T> {

	void salvar (T objeto) throws Exception;
	
	void deletar (T objeto) throws Exception;
	
	void atualizar (T objeto) throws Exception;
	
	void salvarOuAtualizar (T objeto) throws Exception;
	
	T merge(T objeto) throws Exception;
	
	List<T> lista() throws Exception;
	
	T loadObjeto(Long codigo) throws Exception;
	
	int quantidadePagina() throws Exception;
	
	List<T> consultaPaginada(String numeroPagina) throws Exception;

}
