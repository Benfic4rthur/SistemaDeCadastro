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
	private String cep;
	private String numero;
	private String tipomoradia;
	private String cidade;
	private String estado;

	public Cliente(long id, String nome, String email, String telefone, String datanascimento, String profissao,
			String documento, String tipopessoa, String endereco, String cep, String numero, String tipomoradia,
			String cidade, String estado) {
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
		this.cep = cep;
		this.numero = numero;
		this.tipomoradia = tipomoradia;
		this.cidade = cidade;
		this.estado = estado;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipomoradia() {
		return tipomoradia;
	}

	public void setTipomoradia(String tipomoradia) {
		this.tipomoradia = tipomoradia;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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
				usuario.setCep(this.getCep());
				usuario.setNumero(this.getNumero());
				usuario.setTipomoradia(getTipomoradia());
				usuario.setCidade(getCidade());
				usuario.setEstado(getEstado());
				dao.update(usuario);
				enviadoComSucesso = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enviadoComSucesso;
	}
}