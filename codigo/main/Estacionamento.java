package main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Estacionamento{

	private Integer id;
	private String nome;
	private Map<Integer, Cliente> clientes = new HashMap<Integer, Cliente>(200);
	private Map<Integer, Vaga> vagas = new HashMap<Integer, Vaga>(200);;
	private int quantFileiras;
	private int vagasPorFileira;
    private int quantVagas;

	/**
	 * Construtor da classe Estacionamento
	 * @param id identificação do estacionamento, nunca repete
	 * @param nome nome do estacionamento
	 * @param fileiras quantidade de fileira no estacionamento
	 * @param vagasPorFila quantidade de vagas por fileira
	 */
	public Estacionamento(Integer id, String nome, int fileiras, int vagasPorFila) {
		this.id = id;
		this.nome=nome;
		this.quantFileiras=fileiras;
		this.vagasPorFileira=vagasPorFila;
		this.quantVagas = quantFileiras*vagasPorFileira;	
		gerarVagas();
	}

	/**
	 * Adiciona um novo cliente ao estacionamento
	 * @param cliente cliente a ser adicionado
	 */
	public void addCliente(Cliente cliente) {
		clientes.put(cliente.getId(), cliente);
	}

	/**
	 * Envia o comando para a Dao salvar o novo cliente no arquivo
	 * @param cliente cliente a ser salvo no arquivo
	 * @throws IOException
	 */
	public void salvarCliente(Cliente cliente) throws IOException{
		Dao.salvarClienteEstacionamento(cliente, this);
	}

	/**
	 * Retorna um cliente especifico pelo identificador dele
	 * @param id identificação do cliente buscado
	 * @return Cliente
	 */
	public Cliente getById(Integer id){
		Cliente c = null;
		try{
			c = clientes.get(id);
		}catch(NullPointerException e){
			throw new RuntimeException();
		}
		return c;
	}

	/**
	 * Gera aleatoriamente vagas para o estacionamento
	 */
	private void gerarVagas() {
		for(int i=0; i < quantVagas; i++){
			Random ale= new Random();
			int numeroVaga=ale.nextInt(quantVagas);
			char letraVaga=(char)('a'+ale.nextInt(26));
			Vaga nova= new Vaga(letraVaga,numeroVaga, this.nome);
			vagas.put(hashCode(), nova);
		}
	}

	/**
	 * Retorna um vaga selecionada
	 * @return
	 */
	public Vaga getVagaAleatoria(){
		Vaga valueVagaDisp = null;
		for (var entryVagas : vagas.entrySet()) {
			if(entryVagas.getValue().disponivel()){
				valueVagaDisp = entryVagas.getValue();
			}
		}
		return valueVagaDisp;
	}

	/**
	 * Estaciona um veiculo dentro do estacionamento
	 * @param placa placa do veiculo a ser estacionado, é necessario para busca
	 * @param serv servico selecionado pelo cliente
	 */
	public void estacionar(String placa, Servicos serv) {
		try{
			Cliente cliente = clientes.entrySet().stream().filter(x -> x.getValue().possuiVeiculo(placa)).findFirst().get().getValue(); 
			Vaga vaga = vagas.entrySet().stream().filter(v -> v.getValue().disponivel()).findFirst().get().getValue();
			cliente.getVeiculo(placa).estacionar(vaga, serv);	
		}catch(NullPointerException npe){
			npe.notify();
		}
	}

	/**
	 * @return Um mapa dos clientes do estacionamento tendo o id como chave
	 */
	public Map<Integer, Cliente> getMapCliente(){
		return this.clientes;
	}


	/**
	 * Retira um veiculo do estacionamento
	 * @param placa placa do veiculo a ser retirado
	 * @return valor pago pelo uso de vaga
	 * @throws IOException
	 */
	public double sair(String placa) throws IOException {
		Cliente c = clientes.entrySet().stream().filter(x -> x.getValue().possuiVeiculo(placa)).findFirst().get().getValue();
		ArrayList<UsoDeVaga> usos = clientes.entrySet().stream().filter(x -> x.getValue().possuiVeiculo(placa)).findFirst().get().getValue().getVeiculo(placa).getUsos(); 
		Optional<UsoDeVaga> usosOpt = usos.stream().filter(u -> !u.getStatus()).findAny();

		double valor = 0d;

		if(usosOpt.isPresent()){
			UsoDeVaga usoReal = (UsoDeVaga)usosOpt.get();
			valor = usoReal.sair();
			if(valor == -1d){ 
				System.out.println("Veículo não permaneceu tempo suficiente");
				valor = 0d;
			 }else{
				Dao.salvarUsoDeVaga(c, c.getVeiculo(placa), this, usoReal);
			 }
		}
		return valor;
	}

	//#region Relatórios
	/**
	 * @return Total arrecadado pelo estacionamento
	 */
	public double totalArrecadado() {
        double totalCliente = 0;

        for(var entryClientes : clientes.entrySet()){
            totalCliente += entryClientes.getValue().arrecadadoTotal();
        }
        
        return totalCliente;
        
	}

	/**
	 * Valor arrecadado em um mês pelo estacionamento
	 * @param mes
	 * @return Valor arrecadado em um mês pelo estacionamento
	 */
	public double arrecadacaoNoMes(int mes) {
        double total = clientes.entrySet().stream().mapToDouble(x -> x.getValue().arrecadadoNoMes(mes)).sum();
        return total;
    }

	/**
	 * Retorna o valor médio dos usos de vagas do estacionamento
	 * @return Valor médio dos usos de vagas do estacionamento
	 */
	public double valorMedioPorUso() {
        double mediaPorUso = 0;
        try{
            mediaPorUso = totalArrecadado()/clientes.size();
        }catch(Exception e){
            mediaPorUso = 0;
        }
		return mediaPorUso;
	}

	//#endregion

	/**
	 * Override toString
	 */
	@Override
	public String toString(){
		return this.nome;
	}

	/**
	 * Lista o cliente do estacionamento
	 * @return string builder dos clientes
	 */
    public String toStringClientes(){
        StringBuilder b = new StringBuilder();

        for(var entryClientes : clientes.entrySet()){
            b.append(entryClientes.getValue().toString());
        }
        return b.toString();
    }

	/**
	 * Valor médio dos usos de vagas pr clientes horistas
	 * @return Valor médio dos usos de vagas pr clientes horistas
	 */
	public double valorMedio(){
		double total = 0d;
		int horistas = 0;
		for(Cliente c: this.getClientes()){
			if(c.getCategoria().getClass().getName().contains("main.Horista")){
				total += c.arrecadadoNoMes(LocalDate.now().getMonthValue());
				horistas+=1;
			}
			
		}
		return total/horistas;
	}

	//#region getter e setters
	/**
	 * Getters and Setters
	 */
	public Integer getId(){
		return this.id;
	}

	/**
	 * Um mapa com as vagas do estacionamento
	 * @return Um mapa com as vagas do estacionamento
	 */
	public Map<Integer, Vaga> getVagas(){
		return this.vagas;
	}

	/**
	 * Uma lista com todos os cliente do estacionamento
	 * @return Uma lista com todos os cliente do estacionamento
	 */
	public List<Cliente> getClientes(){
        List<Cliente> listaCliente = new ArrayList<Cliente>(this.clientes.values());
		return listaCliente;
	}
	//#endregion
}