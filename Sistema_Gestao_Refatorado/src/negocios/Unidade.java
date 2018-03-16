package negocios;
import java.util.ArrayList;
import java.util.Scanner;
import util.*;

public class Unidade {
	
	private ArrayList<Usuario> users = new ArrayList<>();
	private ArrayList<Recurso> resources = new ArrayList<>();
	private ArrayList<Atividade> activities = new ArrayList<>();
	private ArrayList<Alocacao> allocations = new ArrayList<>();
	
	private Usuario user_logged = null;
	private ArrayList<Integer> id_alloc_in_process = new ArrayList<>();
	private ArrayList<Integer> id_allocated = new ArrayList<>();
	private ArrayList<Integer> id_in_progress = new ArrayList<>();
	
	private final static Scanner input = new Scanner(System.in);
	
	public Unidade() {
		
		Usuario user = new Usuario("ADM", "adm@ic.ufal.br", "adm", "adm", TipoUsuario.ADM);
		users.add(user);
		
		Recurso resource = new Recurso("aud1", TipoRes.AUDITORIO, user); // Responsible = ADM
		resources.add(resource);
		
		user = new Usuario("Maria", "maria@ic.ufal.com", "maria", "m123", TipoUsuario.PROFESSOR);
		users.add(user);
		user = new Usuario("João", "joao@ic.ufal.com", "joao", "j123", TipoUsuario.PESQUISADOR);
		users.add(user);
		
		resource = new Recurso("lab1", TipoRes.LABORATORIO, user); // Responsible = Joao
		resources.add(resource);
		
	}
	
	public void login() {
		
		String login, passwd;
		Usuario user = null;
		
		while( true ) { 
			System.out.print("Login: ");
			login = input.nextLine();
			System.out.print("Password: ");
			passwd = input.nextLine();
			
			int index = searchUser(login);
			if(index != -1) {
				user = users.get(index);
			}
			if(user != null && passwd.equals(user.getSenha())) {
				user_logged = user;
				displayMenu();
			} else {
				System.out.println("\nLogin ou senha incorretos\n");
			}
			
		}
		
	}
	
	private void displayMenu() {
		
		ArrayList<Integer> resourcesToConfirm = getResourcesToConfirm();
		boolean isResponsible = false;
		
		if(resourcesToConfirm.size() > 0)
			isResponsible = true;
		
		int option = 0;
		do {
			
			System.out.println("\n	Menu:\n\n"
							 + "[1] Solicitar alocação de recurso\n"
							 + "[2] Consultar por usuário\n"
							 + "[3] Consultar por recurso");
			
			if(isResponsible)
				System.out.println("[4] Confirmar alocação de recurso");
				
			
			if(user_logged.getTipo() == TipoUsuario.ADM) {
				
				if(id_alloc_in_process.size() > 0)
					System.out.println("[5] Confirmar processo de alocação");
				if(id_in_progress.size() > 0)
					System.out.println("[6] Concluir processo de alocação");
				
				System.out.println("[7] Criar usuário\n"
								 + "[8] Excluir usuário\n"
								 + "[9] Criar recurso\n"
								 + "[10] Excluir recurso\n"
								 + "[11] Gerar relatório");
				
			}
			
			System.out.println("[0] Sair");
			
			String optionS = input.nextLine();
			
			if(!isValid(optionS))
				continue;
			
			option = Integer.valueOf(optionS);
			if(option == 1)
				allocateResource();
			else if(option == 2)
				showUser();
			else if(option == 3)
				showResource();
			else if(option == 4 && isResponsible) {
				
				confirmAllocation(resourcesToConfirm);
				resourcesToConfirm = getResourcesToConfirm();
				if(resourcesToConfirm.size() == 0)
					isResponsible = false;
			
			}
			else if (user_logged.getTipo() == TipoUsuario.ADM) {
				if(option == 5 && id_alloc_in_process.size() > 0) {
					
					confirmProcess();
					resourcesToConfirm = getResourcesToConfirm();
					if(resourcesToConfirm.size() > 0)
						isResponsible = true;
					
				}
				else if(option == 6 && id_in_progress.size() > 0)
					concludeProcess();
				else if(option == 7)
					createUser();
				else if(option == 8)
					deleteUser();
				else if(option == 9)
					createResource();
				else if(option == 10)
					deleteResource();
				else if(option == 11)
					generateReport();
			}
			
		}while( option != 0 );
		
		user_logged = null;			
		
	}
	
	private ArrayList<Integer> getResourcesToConfirm() {
		
		ArrayList<Integer> resourcesToConfirm = new ArrayList<>();
		
		for(int i : id_allocated) {
			if(resources.get(i).getResponsavel().getLogin().equals(user_logged.getLogin())) {
				resourcesToConfirm.add(i);
			}
		}
		
		return resourcesToConfirm;
		
	}
	
	private void concludeProcess() {
		changeStatus(id_in_progress, Status.CONCLUIDO);
			
	}

	private void confirmProcess() {
		int cod = changeStatus(id_alloc_in_process, Status.ALOCADO);
		if(cod != -1)
			id_allocated.add(cod);
	}
	
	private int changeStatus(ArrayList<Integer> list, Status status ) {
		System.out.println("Alocações de recursos pendentes: \n\n"
						 + "Cod - Id do recurso - Nome do usuário");
		System.out.println("--------------------------------------");
		Alocacao alloc;
		for(int i=0; i<list.size(); i++) {
			alloc = allocations.get( list.get(i) );
			System.out.println((list.get(i))+" - "+alloc.getData());
		}
		
		System.out.println("\nDigite o código da alocação (Cod)");
		String number = input.nextLine();
		int cod = -1;
		if(isValid(number))
				cod = Integer.valueOf( number );
		
		if(cod < 0 || cod > list.size()) {
			System.out.println("Cod inválido! Operação cancelada.");
			return -1;
		}
		
		allocations.get(cod).setStatus(status);
		list.remove(cod);
		
		System.out.println("Feito!\n");
		
		return cod;
		
	}
	
	public boolean isValid(String number) {
		
		for(int i=0; i<number.length(); i++) {
			if(number.charAt(i) < 48 || number.charAt(i) > 57)
				return false;
		}
		return true;
		
	}

	private void showResource() {
		System.out.println("Digite o id do Recurso: ");
		int index = searchResource( input.nextLine() );
		if( index == -1 ) {
			System.out.println("Recurso não encontrado");
			return;
		}
		
		Recurso resource = resources.get(index);
		System.out.println("\n----------------------------\n");
		System.out.println( resource.toString() );
		
		
		System.out.println("Alocações:\n");
		boolean hasAllocations = false;
		
		for(Alocacao alloc : allocations) {
			if(alloc.getRecurso().getId().equals(resource.getId())) {
				
				System.out.println( alloc.toString() );
				System.out.println("\n----------------------------\n");
				hasAllocations = true;
					
			}
		}
		if(!hasAllocations)
			System.out.println("[Sem alocações]\n");
			
		
	}

	private void showUser() {
		System.out.println("Digite o login do usuário: \n");
		int index = searchUser( input.nextLine() );
		if(index == -1) {
			System.out.println("Usuário não encontrado\n");
			return;
		}
		
		Usuario user = users.get(index);
		
		System.out.printf( user.toString() );
		
		System.out.println("Alocações:\n");
		boolean hasAllocations = false;
		
		for(Alocacao alloc : allocations) {
			if(alloc.getSolicitante().getLogin().equals(user.getLogin())) {
				
				System.out.println( alloc.toString() );
				
				System.out.println( alloc.getStringStatus() );
				hasAllocations = true;
					
			}
		}
		if(!hasAllocations)
			System.out.println("[Sem alocações]\n");
		
		System.out.println("\n----------------------------\n");
		
	}

	private void generateReport() {
		
		System.out.println("--------------------RELATÓRIO----------------------\n\n" 
						  + "Número de usuários: "+users.size());
		
		int in_process_size = id_alloc_in_process.size();
		int allocated_size = id_allocated.size();
		int in_progress_size = id_in_progress.size();
		int alloc_size = allocations.size();
		
		int num_class = 0;
		int num_lab = 0;
		int num_pres = 0;
		for(Atividade act: activities) {
			
			if(act.getTipo() == TipoAtiv.AULA)
				num_class++;
			else if(act.getTipo() == TipoAtiv.APRESENTACAO)
				num_pres++;
			else
				num_lab++;
		}
		
		System.out.println("\nNúmero de recursos em processo de alocação: "+in_process_size
						  +"\nNúmero de recursos alocados: "+allocated_size
						  +"\nNúmero de recursos 'em andamento': "+in_progress_size
						  +"\nNúmero de recursos 'concluídos': "+(alloc_size - in_process_size - in_progress_size - allocated_size)
						  +"\nNúmero total de alocações: "+alloc_size
						  +"\n\nNúmero de atividades por tipo:\n"
						  +"\n- Aulas tradicionais: "+num_class
						  +"\n- Laboratórios: "+num_lab
						  +"\n- Apresentações: "+num_pres);
		System.out.println("---------------------------------------------------\n");
		
		
	}

	private void deleteResource() {
		
		System.out.println("Digite o id do recurso: ");
		int index = searchResource( input.nextLine() );
		
		if(index == -1)
			System.out.println("Recurso não encontrado!");
		else {
			resources.remove(index);
			System.out.println("Feito!\n");
		}
		
	}

	private void createResource() {
		
		Recurso resource = new Recurso();
		
		String id = "";
		do {
			
			if(!id.equals(""))
				System.out.println("ID já existente!");
			
			System.out.println("Digite a identificação (id): ");
			id = input.nextLine();
			
		}while( searchResource(id) != -1 );
		
		resource.setId(id);
		
		System.out.println("Digite o tipo: \n"
						 + "1 - Laboratório\n"
						 + "2 - Auditório\n"
						 + "3 - Sala de aula\n"
						 + "4 - Projetor");
		int type = Integer.valueOf( input.nextLine() );
		
		switch(type) {
		case 1:
			resource.setTipo(TipoRes.LABORATORIO);
			break;
		case 2:
			resource.setTipo(TipoRes.AUDITORIO);
			break;
		case 3:
			resource.setTipo(TipoRes.SALA);
			break;
		case 4:
			resource.setTipo(TipoRes.PROJETOR);
			break;
		}
		
		Usuario responsible = null;
		boolean unable = true;
		System.out.println("Digite o login do usuário responsável pelo recurso");
		while(responsible == null || unable) {
			int index = searchUser( input.nextLine() );
			
			if(index == -1)
				System.out.println("Usuário não encontrado! Tente novamente.");
			else {
				responsible = users.get(index);
				if(responsible.getTipo() != TipoUsuario.ADM && responsible.getTipo() != TipoUsuario.PROFESSOR && responsible.getTipo() != TipoUsuario.PESQUISADOR ) {
					System.out.println("Somente são permitidos serem responsáveis pelo recurso: um professor, um pesquisador ou um adminstrador do sistema. Tente novamente.");
				} else {
					unable = false;
				}
			}

			
		}
		
		resource.setResponsavel(responsible);
		
		resources.add(resource);
		
		System.out.println("Feito!\n");
		
	}

	private void deleteUser() {
		
		System.out.println("Digite o login do usuário: ");
		int index = searchUser( input.nextLine() );
		
		if(index == -1)
			System.out.println("Usuário não encontrado!");
		else {
			users.remove(index);
			System.out.println("Feito!\n");
		}
		
	}

	private void createUser() {
		
		Usuario user = new Usuario();
		System.out.println("Digite o nome: ");
		user.setNome( input.nextLine() );
		System.out.println("Digite o email: ");
		user.setEmail( input.nextLine() );
		
		String login = "";
		do {
			
			if(!login.equals(""))
				System.out.println("Login já existente!");
			
			System.out.println("Digite o login: ");
			login = input.nextLine();
			
		}while( searchUser(login) != -1 );
		
		System.out.println("Digite a senha: ");
		user.setSenha( input.nextLine() );
		System.out.println("Digite o tipo: \n"
						 + "1 - Administrador\n"
						 + "2 - Professor\n"
						 + "3 - Pesquisador\n"
						 + "4 - Aluno de doutorado\n"
						 + "5 - Aluno de mestrado\n"
						 + "6 - Aluno de graduação");
		int type = Integer.valueOf( input.nextLine() );
		
		switch(type) {
			case 1:
				user.setTipo(TipoUsuario.ADM);
				break;
			case 2:
				user.setTipo(TipoUsuario.PROFESSOR);
				break;
			case 3:
				user.setTipo(TipoUsuario.PESQUISADOR);
				break;
			case 4:
				user.setTipo(TipoUsuario.DOUTORANDO);
				break;
			case 5:
				user.setTipo(TipoUsuario.MESTRANDO);
				break;
			case 6:
				user.setTipo(TipoUsuario.GRADUANDO);
				break;
		}
		
		users.add(user);
		
		System.out.println("Feito!\n");
		
	}

	private void confirmAllocation(ArrayList<Integer> resourcesToConfirm) {
		
		int cod = changeStatus(resourcesToConfirm, Status.EM_ANDAMENTO);
		
		if( cod != -1) {
			id_in_progress.add(cod);
			id_allocated.remove(cod);
		}
		
	}

	private int searchResource(String id) {
		
		int size = resources.size();
		Recurso res;
		for(int i=0; i<size; i++) {
			res = resources.get(i);
			if(res.getId().equals(id)) {
				return i;
			}
		}
		
		return -1;
	}

	private int searchUser(String userLogin) {
		
		int size = users.size();
		Usuario user;
		for(int i=0; i<size; i++) {
			user = users.get(i);
			if(user.getLogin().equals(userLogin))
				return i;
		}
		
		return -1;
		
	}

	public void allocateResource() {
		
		Alocacao newAlloc = new Alocacao();
				
		newAlloc.setStatus(Status.EM_PROCESSO);
		newAlloc.setSolicitante(user_logged);
		
		System.out.println("Digite o id do recurso");
		String id = input.nextLine();
		int index = searchResource(id);
		
		if(index == -1) {
			System.out.println("Recurso não encontrado!\n");
			return;
		}
		
		Recurso resource = resources.get(index);
			
		newAlloc.setRecurso(resource);
		
		System.out.println("Digite a data de início [formato dd/mm/aaaa]: ");
		
		Date date = new Date();
		while( !date.setDate( input.nextLine() ) ) {
			System.out.println("Data inválida! Digite novamente [formato dd/mm/aaaa]: ");
		}
		
		newAlloc.setDataInicio( date );
			
		System.out.println("Digite a hora de início [formato hh:mm]: ");
		
		Time time = new Time();
		while( !time.setTime( input.nextLine() ) ) {
			System.out.println("Hora inválida! Digite novamente [formato hh:mm]: ");
		}
		
		newAlloc.setHoraInicio( time );
			
		System.out.println("Digite a data de término [formato dd/mm/aaaa]: ");
		
		date = new Date();
		while( !date.setDate( input.nextLine() ) ) {
			System.out.println("Data inválida! Digite novamente [formato dd/mm/aaaa]: ");
		}
		
		newAlloc.setDataFim( date );
			
		System.out.println("Digite a hora de término [formato hh:mm]: ");
		
		time = new Time();
		while( !time.setTime( input.nextLine() ) ) {
			System.out.println("Hora inválida! Digite novamente [formato hh:mm]: ");
		}
		
		newAlloc.setHoraInicio( time );
			
		Atividade activity = new Atividade();
		System.out.println("Digite o título da atividade a ser realizada: ");
		activity.setTitulo( input.nextLine() );
			
		System.out.println("Digite a descrição da atividade: ");
		activity.setDescricao( input.nextLine() );
			
		System.out.println("Digite qual o material que será usado na atividade: ");
		activity.setMaterial( input.nextLine() );
			
		System.out.println("Digite quantos participantes terá a atividade");
		int num_part = Integer.valueOf(input.nextLine() );
			
		String userLogin;
		for(int i=0; i<num_part; i++) {
			System.out.println("Digite o login do "+(i+1)+"º participante");
			userLogin = input.nextLine();
			index = searchUser(userLogin);
			
			if(index == -1) {
				System.out.println("Usuário não encontrado\n");
				i--;
			} else {
				activity.adicionarParticipantes(users.get(index));
			}
		}
		
		boolean invalid;
		do {
			invalid = false;
			System.out.println("Digite a tipo da atividade: \n"
							 + "1 - Aula tradicional\n"
							 + "2 - Apresentação\n"
							 + "3 - Laboratório");
			int type = Integer.valueOf( input.nextLine() );
				
			if(type == 1 && user_logged.getTipo() == TipoUsuario.PROFESSOR) 
				activity.setTipo(TipoAtiv.AULA);
			else if(type == 2) 
				activity.setTipo(TipoAtiv.APRESENTACAO);
			else if(type == 3 && user_logged.getTipo() == TipoUsuario.PROFESSOR)
				activity.setTipo(TipoAtiv.LABORATORIO);
			
			else {
				System.out.println("Atividade não permitida!\n");
				invalid = true;
			}
		
		}while(invalid);
		
		activities.add(activity);
		newAlloc.setAtividade(activity);
		allocations.add(newAlloc);
		index = allocations.size() - 1;
		
		if(user_logged.getTipo() == TipoUsuario.ADM)
			id_allocated.add(index);
		else
			id_alloc_in_process.add(index);
		
		System.out.println("\nFeito!\n");
		
		
	}
	
}
