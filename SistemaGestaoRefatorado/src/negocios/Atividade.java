package negocios;
import util.TipoAtiv;
import java.util.ArrayList;

public class Atividade {
	
	private String titulo;
	private String descricao;
	private String material;
	private TipoAtiv tipo;
	private ArrayList<Usuario> participantes = new ArrayList<>();
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public TipoAtiv getTipo() {
		return tipo;
	}
	public void setTipo(TipoAtiv tipo) {
		this.tipo = tipo;
	}
	public ArrayList<Usuario> getParticipantes() {
		return participantes;
	}
	public void setParticipantes(ArrayList<Usuario> participantes) {
		this.participantes = participantes;
	}
	
	public void adicionarParticipantes(Usuario user) {
		this.participantes.add(user);
	}
	
	
}
