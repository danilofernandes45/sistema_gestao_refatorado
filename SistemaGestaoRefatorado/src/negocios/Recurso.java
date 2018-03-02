package negocios;

import util.*;

public class Recurso {
	
	private String id;
	private TipoRes tipo;
	private Usuario responsavel;
	
	public Recurso() {}
	
	public Recurso(String id, TipoRes tipo, Usuario responsavel) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.responsavel = responsavel;
	}
	public Usuario getResponsavel() {
		return responsavel;
	}
	public void setResponsavel (Usuario responsible) {
		this.responsavel = responsible;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TipoRes getTipo() {
		return tipo;
	}
	public void setTipo(TipoRes tipo) {
		this.tipo = tipo;
	}
	
	public String toString() {
		
		String type = "";
		switch( tipo ) {
		
			case LABORATORIO:
				type = "Laboratório\n";
				break;
			case AUDITORIO:
				type = "Auditório\n";
				break;
			case SALA:
				type = "Sala de aula\n";
				break;
			case PROJETOR:
				type = "Projetor\n";
				break;
		
		}
		
		return ("Responsável : "+this.responsavel.getNome()+"\n"
				 + "Tipo de recurso: "+type);
	}
	
}
