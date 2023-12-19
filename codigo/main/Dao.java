package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import interfaces.IFabrica;
import main.Fabricas.FabricaVeiculo;

public class Dao {
    
    static IFabrica<Veiculo> fabricaVeiculo = new FabricaVeiculo();

    static ArrayList<Estacionamento> estacionamentos = new ArrayList<Estacionamento>();
    static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    static ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();

    /**
     * Salva em arquivos os usos de vagas gerados pelo sistema
     * @param cliente Cliente
     * @param veiculo Veiculo
     * @param estacionamento Estacionamento
     * @param usoDeVaga UsoDeVaga
     * @throws IOException
     */
    public static void salvarUsoDeVaga(Cliente cliente, Veiculo veiculo, Estacionamento estacionamento, UsoDeVaga usoDeVaga) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("usoDeVagas.txt", true));
   
        bw.newLine();
        bw.write(cliente.getId()
            +";"+veiculo.getPlaca()
            +";"+estacionamento.getId()
            +";"+usoDeVaga.getHoraEntrada()
            +";"+usoDeVaga.getHoraSaida()
            +";"+usoDeVaga.getServicos().nome());
        bw.close();
    }
    
    /**
     * Salva em arquivos os relacionamentos entre clientes e estacionamentos cadastrados
     * @param cliente Cliente
     * @param estacionamento Estacionamento
     * @throws IOException
     */
    public static void salvarClienteEstacionamento(Cliente cliente, Estacionamento estacionamento) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("estacionamentoClientes.txt", true));
		
		try {
			if(clientes.get(cliente.getId()) != null){
				bw.newLine();
				bw.write(estacionamento.getId()+";"+cliente.getId());
                bw.close();
			}else{
                System.out.println("Cliente nulo");
            }
        } catch (Exception e) {
            throw(e);
        }
    }

    /**
     * Salva em arquivos os relacionamentos entre clientes e seus veiculos
     * @param cliente Cliente   
     * @param veiculo Veiculo
     * @throws IOException
     */
    public static void salvarClienteVeiculo(Cliente cliente, Veiculo veiculo) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("clienteVeiculo.txt", true));

        bw.newLine();
        bw.write(cliente.getId()+";"+veiculo.getId());
        bw.close();
    }

    /**
     * Cadastra um novo veiculo
     * @throws IOException
     */
    public static void cadastrarVeiculo() throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("veiculos.txt", true));

        Scanner s = new Scanner(System.in);

        System.out.println("Digite a placa");
        String placa = s.nextLine();

        int id = Dao.veiculos.size();
        Veiculo v = fabricaVeiculo.create();
        //Veiculo v = new Veiculo(id, placa);

        v.setId(id);
        v.setPlaca(placa);

        bw.newLine();
        bw.write(v.getPlaca());
        bw.close();
        Dao.veiculos.add(v);
    }

    /**
     * Cadastra um novo cliente
     * @throws IOException
     */
    public static void cadastrarCliente() throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("clientes.txt", true));

        Scanner s = new Scanner(System.in);

        System.out.println("Digite o seu nome");
        String nome = s.nextLine();

        Integer id = Dao.clientes.size();

        Cliente c = new Cliente(nome, id, null);

        bw.newLine();
        bw.write(c.toString());
        bw.close();
        Dao.clientes.add(c);
    }

}
