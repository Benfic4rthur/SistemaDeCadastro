package model;

public class DadosLogin {
	private long id;
	private String login;
	private String senha;
	
	public void salvaCadastro(long id, String login, String senha) {
		this.id = id;
		this.login = login;
		this.senha = senha;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "MinhaUserPosJava [id=" + id + ", login=" + login + ", senha=" + senha + "]";
	}

}