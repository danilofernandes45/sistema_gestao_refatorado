package negocios;
import util.TipoUsuario;

public class Usuario {
	
	private String login;
	private String senha;
	private String nome;
	private String email;
	private TipoUsuario tipo;
	
	public Usuario() {}
	
	public Usuario(String nome, String email, String login, String senha, TipoUsuario tipo) {
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.tipo = tipo;
	}
	
	public String getNome(){
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public TipoUsuario getTipo() {
		return tipo;
	}
	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
	
	public String toString() {
		
		String type = "";
		switch( this.tipo ) {
		
			case ADM:
				type = "Administrador\n";
				break;
			case GRADUANDO:
				type = "Aluno de graduação\n";
				break;
			case MESTRANDO:
				type = "Aluno de mestrado\n";
				break;
			case DOUTORANDO:
				type = "Aluno de doutorado\n";
				break;
			case PROFESSOR:
				type = "Professor\n";
				break;
			case PESQUISADOR:
				type = "Pesquisador\n";
				break;
		
		}
		
		return "Nome: "+this.nome+"\n"
				 + "Email: "+this.email+"\n"
				 + "Tipo de usuário: "+type;
		
	}

}