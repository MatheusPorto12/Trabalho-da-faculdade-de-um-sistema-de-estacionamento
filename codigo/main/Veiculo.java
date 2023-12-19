package main;
/*
 * Classe criada em 04/10/2023
 * por Gabriel Henrique
 * */
import java.util.ArrayList;

public class Veiculo {

	private int id;
	private String placa;
	private boolean temDono;
	private boolean estacionado;
	private ArrayList<UsoDeVaga> usos = new ArrayList<UsoDeVaga>(); //Mudar para arrayList

	public Veiculo(){
		this.estacionado = false;
	}

	/**
	 * Define um veiculo com id e placa
	 * @param id
	 * @param placa
	 */
	public Veiculo(int id, String placa) {
		this.id = id;
		this.placa = placa;
		this.estacionado = false;
	}

	/**
	 * Estaciona um veiculo com a utilização de um serviço
	 * @param vaga
	 * @param serv
	 * @return boolean
	 */
	public boolean estacionar(Vaga vaga, Servicos serv) {
		boolean res = true;
		if(vaga.disponivel()) {
			try {
				vaga.estacionar();
				UsoDeVaga usoVaga = new UsoDeVaga(vaga, serv);
				this.usos.add(usoVaga);
				this.setEstacionado(true);
			} catch(Exception e) {
				res = false;
			}
		} else {
			res = false;
		}
		return res;
	}
	
	/**
	 * Quando sair da vaga, invoca o método sair da classe Vaga
  	 * e disponibiliza como disponivel=true;
	 * @param vaga
	 * */
	public boolean sair(Vaga vaga) {
		boolean res = true;
		try {
			vaga.sair();
			this.setEstacionado(true); 
		} catch(Exception e) {
			res = false;
		}
		return res;
	}

	/**
	 * Retorna o total arrecada pelo veiculo
	 * @return double
	 */
	public double totalArrecadado() {
		double total = 0.0;
		for(UsoDeVaga uv : usos) {
			total += uv.getValorPago();
		}
		return total;
	}

	/**
	 * Adiciona um novo uso de vaga para o veiculo
	 * @param u
	 */
	public void adicionarUso(UsoDeVaga u){
		this.getUsos().add(u);
	}
	
	/**
	 * Inclui no uso de vaga, a vaga utilizada.
	 * @param vaga
	 * @return int
	 * */
	public int totalDeUsos(Vaga vaga) {
		int total = 0;
		for(UsoDeVaga uv: usos){
			if(uv.getVaga().equals(vaga)){
				total++;
			}
		}
		return total;
	}

	/**
	 * Getters and Setters
	 * */
	public String getPlaca() {
		return this.placa;
	}

	/**
	 * Getters and Setters
	 * */
	public int getUsosCount(){
		return this.usos.size();
	}

	/**
	 * Getters and Setters
	 * */
	public ArrayList<UsoDeVaga> getUsos(){
		return this.usos;
	}

	/**
	 * Override toString
	 * */
	@Override
	public String toString(){
		return this.placa;
	}

	/**
	 * Getters and Setters
	 * */
	public boolean getTemDono(){
		return this.temDono;
	}

	/**
	 * Getters and Setters
	 * */
	public void atribuirDono(){
		this.temDono = true;
	}

	/**
	 * Getters and Setters
	 * */
	public boolean getEstacionado(){
		return this.estacionado;
	}

	/**
	 * Getters and Setters
	 * */
	public void setEstacionado(boolean status){
		this.estacionado = status;
	}

	/**
	 * Getters and Setters
	 * */
	public int getId(){
		return this.id;
	}

	/**
	 * Getters and Setters
	 * */
	public void setId(int id){
		this.id = id;
	}

	/**
	 * Getters and Setters
	 * */
	public void setPlaca(String placa){
		this.placa = placa;
	}
}
