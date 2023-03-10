package pacoteDados;

import dao.DaoCliente;

public class Cliente {
	private int id;
	private String nome;
	private String email;
	private String telefone;
	private String datanascimento;
	private String profissao;
	private String documento;
	private String tipopessoa;
	private String endereco;
	
	public Cliente(int id, String nome, String email, String telefone, String datanascimento, String profissao,
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone
				+ ", datanascimento=" + datanascimento + ", profissao=" + profissao + ", documento=" + documento
				+ ", tipopessoa=" + tipopessoa + ", endereco=" + endereco + "]";
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
}
