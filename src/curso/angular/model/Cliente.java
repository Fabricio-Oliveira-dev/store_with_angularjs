package curso.angular.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

/**
 * Modelo que representa a tabel de Cliente do banco
 * @author alex
 *
 */
@Entity
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;

	private String endereco;

	private String telefone;

	private String sexo; 
	
	private Boolean ativo;
	
	private String interesse;
	
	private String cpf;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@ForeignKey(name="estados_fk")
	private Estados estados = new Estados();
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@ForeignKey(name="cidades_fk")
	private Cidades cidades = new Cidades();
	
	@Column(columnDefinition="text")
	private String foto;
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public String getFoto() {
		return foto;
	}
	
	public void setCidades(Cidades cidades) {
		this.cidades = cidades;
	}
	
	
	public Cidades getCidades() {
		return cidades;
	}
	
	
	public void setEstados(Estados estados) {
		this.estados = estados;
	}
	
	public Estados getEstados() {
		return estados;
	}
	
	public void setInteresse(String interesse) {
		this.interesse = interesse;
	}
	
	public String getInteresse() {
		return interesse;
	}
	
	public void setAtivo(Boolean ativo) {
		if (ativo == null) 
			this.ativo = false;
		
		this.ativo = ativo;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSexo() {
		return sexo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
