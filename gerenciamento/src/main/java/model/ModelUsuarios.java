package model;

public class ModelUsuarios {
	
	private String login;
	private String senha;
	private int id;
	private String nome;
	private String email;
	
	public ModelUsuarios() {
		
	}
	
	public ModelUsuarios(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	public ModelUsuarios(String login, int id) {
		this.login = login;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
}
