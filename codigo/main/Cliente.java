package main;

import java.io.IOException;

/*
 * Classe criada 04/10/2023
 * 
 * Projeto3 regra aplicada por Matheus Vinicius
 */

import java.util.ArrayList;
import interfaces.ICategoriaCliente;

public class Cliente implements ICategoriaCliente {

	private String nome;
	private Integer id;
	private ICategoriaCliente categoria;
	private ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();

	/**
	 * Contrutores da classe Cliente
	 * Define um novo cliente com nome, ID e categoria
	 * @param nome
	 * @param id
	 * @param categoria
	 */
	public Cliente(String nome, Integer id, ICategoriaCliente categoria) {
		this.categoria = categoria != null ? categoria : new Horista(this);
		this.nome = nome;
		this.id = id;
	}

	/**
	 * Define um novo cliente apenas com o ID e categoria, deixando o
	 * nome como anônimo
	 * @param id
	 * @param categoriaCliente
	 */
	public Cliente(Integer id,ICategoriaCliente categoriaCliente) {
		this.categoria = categoriaCliente !=null ? categoria: new Horista(this);
		this.nome = "anônimo";
		this.id = id;
	}

	/**
	 * Adiciona um novo veiculo ao vetor de veiculos
	 * @param veiculo
	 */
	public void addVeiculo(Veiculo veiculo) {
		if(!this.possuiVeiculo(veiculo.getPlaca())){
			veiculo.atribuirDono();
			veiculos.add(veiculo);
			try {
				Dao.salvarClienteVeiculo(this, veiculo);
			} catch (IOException e) {
				e.getLocalizedMessage();
			}
		}
	}

	/**
	 * Retorna se o cliente possui veiculo se baseando na placa
	 * @param placa
	 */
	public boolean possuiVeiculo(String placa) {
		boolean status = false;
		for(Veiculo veiculo : veiculos){
			if(veiculo.getPlaca() == placa){ status = true; }// getPlaca vai ser criado no veiculo
		}
		return status;
	}

	/**
	 * Percorre todo o vetor de veículos do cliente e faz a soma
	 * da quantidade de vagas que os veículos utilizaram
	 */
	public int totalDeUsos() {
		int total = 0;
		//Percorrendo o vetor de Veiculos do cliente
		for(Veiculo v : veiculos){ 
			total += v.getUsosCount();
		}
		return total;
	}

	/**
	 * Retorna a soma dos valores das vagas utilizadas por um veículo
	 * do cliente
	 * @param placa
	 */
	public double arrecadadoPorVeiculo(String placa) {
		double total = 0.0;
		for(Veiculo v : veiculos){ 
			if(v.getPlaca() == placa){ total = v.totalArrecadado(); }
		}
		return total;
	}

	/**
	 * Retorna a soma dos valores das vagas utilizadas pelos veículos
	 * do cliente
	 */
	public double arrecadadoTotal() {
		double total = 0.0;
		for(Veiculo v : veiculos){ 
			total += v.totalArrecadado(); 
		}
		return total;
	}

	//RELATORIOS
	/**
	 * Retorna uma string com todos os usos de vagas dos veiculos do cliente
	 * @param estacionamento
	 */
	public String usoDeEstacionamento(String estacionamento){
		StringBuilder b = new StringBuilder();

		for(Veiculo v : veiculos){ 
			for(UsoDeVaga u : v.getUsos()){
				if(v.getUsosCount() > 0 && u.getVaga().getNomeEstacionamento() == estacionamento && u.getStatus() == true){
					String valorPago = String.format("%.2f", u.getValorPago());
					b.append(u.getVaga().getNomeEstacionamento() 
						+ " - " + u.getData() 
						+ " - R$" + valorPago 
						+ " - " + v.getPlaca() 
						+ " - " + u.getServicos() + "\n");
				}
			}
		}
		return b.toString();
	}

	/**
	 * Retorna o valor arrecadado durante o mês especificado
	 * @param mes
	 */
	public double arrecadadoNoMes(int mes) {
		double total = 0.0;
		for(Veiculo v : veiculos){ 
			for(UsoDeVaga u : v.getUsos()){
				if(u.getMesEntrada() == mes){ total += u.calcularValor();  }
			}
		}
		return total;
	}

	/**
	 * Retorna a quantidade de veiculos do cliente
	 * 
	 */
	public int getVeiculosCount(){
		return this.veiculos.size();
	}

	/**
	 * Imprime os veiculo disponiveis no padrão posiçao-placa
	 * @return String
	 */
	public String imprimirVeiculosDisponiveis(){
		StringBuilder sb = new StringBuilder();
		int cont = 0;
		for (Veiculo veiculo : veiculos) {
			sb.append(cont + " - " + veiculo.getPlaca() + "\n");
			cont++;
		}
		return sb.toString();
	}

	/**
	 * Imprime os veiculo estacionados no padrão posiçao-placa
	 * @return String
	 */
	public String imprimirVeiculosEstacionados(){
		StringBuilder sb = new StringBuilder();
		int cont = 0;
		for (Veiculo veiculo : veiculos) {
			if(veiculo.getEstacionado()){
				sb.append(cont + " - " + veiculo.getPlaca() + "\n");
			}
			cont++;
		}
		return sb.toString();
	}

	/**
	 * Retorna um veiculo baseado na placa do mesmo
	 * @param placa
	 */
	public Veiculo getVeiculo(String placa){
		Veiculo ve = null;
		for(Veiculo v: veiculos){
			if(v.getPlaca().equals(placa)){ 
				ve = v;
				return ve;
			 }
		}
		return ve;
	}

	/**
	 * Retorna um veiculo baseado no índice
	 * @param posicao
	 */
	public Veiculo getVeiculo(int posicao){
		return veiculos.get(posicao);
	}

	/**
	 * Método utiliza a função calcularPagamento em cada uma das classes que
	 * implementam a interface ICategoriaCliente
	 */
	public double calcularPagamento(){
		return categoria.calcularPagamento();
	}

	@Override
	public String toString(){
		return this.nome + " - " + this.id; 
	}

	//#region Getters e setters
	public Integer getId(){
		return this.id;
	}

	public void setCategoria(ICategoriaCliente categoria){
		this.categoria = categoria;
	}

	public ArrayList<Veiculo> getVeiculos() {
		return this.veiculos;
	}

	public ICategoriaCliente getCategoria(){
		return this.categoria;
	}
	//#endregion
}
