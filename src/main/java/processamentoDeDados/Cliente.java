package processamentoDeDados;

import java.time.LocalDate;

import dao.DaoCliente;

public class Cliente {
	private long id;
	private String nome;
	private String email;
	private String telefone;
	private String datanascimento;
	private String profissao;
	private String documento;
	private String tipopessoa;
	private String endereco;
	
	public Cliente(long id, String nome, String email, String telefone, String datanascimento, String profissao,
			String documento, String tipopessoa, String endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.datanascimento = datanascimento;
		this.profissao = profissao;
		this.documento = documento;
		this.tipopessoa = tipopessoa;
		this.endereco = endereco;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(String datanascimento) {
		this.datanascimento = datanascimento;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTipopessoa() {
		return tipopessoa;
	}

	public void setTipopessoa(String tipopessoa) {
		this.tipopessoa = tipopessoa;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public boolean salvaCadastro() {
	    boolean enviadoComSucesso = false;
	    try {
	    	DaoCliente dao = new DaoCliente();
	        dao.salvar(this);
	        enviadoComSucesso = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return enviadoComSucesso;
	}
	public boolean salvaCadastroEditado() {
	    boolean enviadoComSucesso = false;
	    try {
	    	
	    	DaoCliente dao = new DaoCliente();
	    	Cliente usuario = dao.buscarPorId(this.getId());
	        if (usuario != null) {
	        	usuario.getId();
	            usuario.setNome(this.getNome());
	            usuario.setEmail(this.getEmail());
	            usuario.setTelefone(this.getTelefone());
	            usuario.setDatanascimento(this.getDatanascimento());
	            usuario.setProfissao(this.getProfissao());
	            usuario.setDocumento(this.getDocumento());
	            usuario.setTipopessoa(this.getTipopessoa());
	            usuario.setEndereco(this.getEndereco());
	            dao.update(usuario);
	            enviadoComSucesso = true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return enviadoComSucesso;
	}

}
