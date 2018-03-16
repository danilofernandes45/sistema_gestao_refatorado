package negocios;

import util.Date;
import util.Time;
import util.Status;

public class Alocacao {

	private Status status;
	private Date dataInicio;
	private Time horaInicio;
	private Date dataFim;
	private Time horaFim;
	private Usuario solicitante;
	private Recurso recurso;
	private Atividade atividade;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Time getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Time getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(Time horaFim) {
		this.horaFim = horaFim;
	}
	public Usuario getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}
	public Recurso getRecurso() {
		return recurso;
	}
	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}
	public Atividade getAtividade() {
		return atividade;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	
	public String getStringStatus() {
		
		String stringStatus = "";
		switch ( this.status ) {
		
			case EM_PROCESSO:
				stringStatus = "Status: Em processo";
				break;
			case ALOCADO:
				stringStatus = "Status: Alocado";
				break;
			case EM_ANDAMENTO:
				stringStatus = "Status: Em andamento";
				break;
			case CONCLUIDO:
				stringStatus = "Status: Concluído";
				break;
		}
		
		return stringStatus;
	}
	
	public String toString() {
		
		return "Usuário: "+this.solicitante.getNome()+"\n"
				 + "Atividade: "+this.atividade.getTitulo()+"\n"
				 + getStringStatus();
		
	}
	
	public String getData() {
		return this.recurso.getId()+" - "+this.solicitante.getNome();
	}

}
