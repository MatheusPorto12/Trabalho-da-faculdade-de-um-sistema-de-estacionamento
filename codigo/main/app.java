package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import interfaces.ICategoriaCliente;

public class app {
    
    
    /** 
     * @param args[]
     * @throws IOException
     */
    public static void main(String args[]) throws IOException{
        try{
            carregarDados();
        }catch(FileNotFoundException e){
            System.out.println("Não foi possível encontrar um dos arquivos: " + e.getLocalizedMessage());
        }
        menu();
    }

    /**
     * Menu principa que faz a chamada dos outros menus do sistema
     */
    public static void menu(){
        Scanner s = new Scanner(System.in);


        System.out.print("\nO que deseja fazer?\n");
        System.out.print("  1-Listagens\n");
        System.out.print("  2-Relatorios\n");
        System.out.print("  3-Clientes\n");
        System.out.print("  4-Estacionamento\n");

        System.out.println("Selecione uma opção\n");
        int opcao = s.nextInt();

        switch(opcao){
            case 1:
                Listagens();
                break;
             case 2:
                Relatorios();
                break;
             case 3:
                Clientes();
                break;
            case 4:
                try {
                    Estacionamento();
                } catch (IOException e) {
                    e.getLocalizedMessage();
                }
                break;
            default:
                menu();
                break;
        }
    }

    //#region Menus
    /**
     * Menu de listagens
     */
    public static void Listagens(){
        System.out.print("Listagens\n\n");
        System.out.print("  1-Listar Clientes\n");
        System.out.print("  2-Listar Veiculos\n");
        System.out.print("  3-Listar Estacionamentos\n\n");
        

        Scanner s1 = new Scanner(System.in);

        System.out.print("\nO que deseja?\n");

        int opcao = s1.nextInt();

        switch(opcao){
            case 1:
                for(ICategoriaCliente c : Dao.clientes){
                    System.out.println(c.toString());
                }
                menu();
                break;
             case 2:
                for(Veiculo v: Dao.veiculos){
                    System.out.println(v.toString());
                }
                menu();
                break;
             case 3:
                for(Estacionamento e : Dao.estacionamentos){
                    System.out.println(e.toString());
                }
                menu();
                break;
            default:
                menu();
                break;
        }
    }

    /**
     * Menu de relatórios
     */
    public static void Relatorios(){
        System.out.print("Relatorios\n\n");
        System.out.print("  1-Valor arrecadado estacionamento\n");
        System.out.print("  2-Valor arrecadado mês(Estacionamento)\n");
        System.out.print("  3-Valor médio de utilização\n");
        System.out.print("  4-Ranking dos clientes\n\n");

        System.out.print("O que deseja?");


        Scanner s = new Scanner(System.in);
        int opcao = s.nextInt();

        double total = 0d;
        System.out.println("Selecione");
        for(int i = 0; i< Dao.estacionamentos.size(); i++){
            System.out.println(i + "- " + Dao.estacionamentos.get(i).toString());
        }
        int selecao = s.nextInt();
        Estacionamento est = Dao.estacionamentos.get(selecao);

        switch(opcao){
            case 1:
                total = est.totalArrecadado();
                System.out.print(total);
                menu();
                break;
            case 2:
                    System.out.println("Selecione(1-janeiro e assim por diante)");
                    int mes = s.nextInt();
                     if(mes > 12 ||  mes < 1){
                        System.out.println("Digite um mês válido da próxima");
                        menu();
                    }

                    total = est.arrecadacaoNoMes(mes);
                    System.out.println(total);
                menu();
                break;
            case 3:
                total = est.valorMedioPorUso();
                System.out.println(total);
                menu();
                break;
            case 4:
                menu();
                break;
            default:
                menu();
                break;
        }
    }

    /**
     * Menu de clientes
     */
    public static void Clientes(){
        System.out.print("Clientes\n\n");
        System.out.print("  1-Cadastrar cliente\n");
        System.out.print("  2-Adicionar veiculo\n");
        System.out.print("  3-Estacionar veiculo\n");
        System.out.print("  4-Retirar veiculo\n");
        System.out.print("  5-Usos de estacionamentos\n");
        System.out.print("  6-Arrecadado no mês\n");
        System.out.print("  7-Médio por horista\n");
        System.out.print("  8-Cadastrar Veiculo\n\n");

        System.out.print("O que deseja?");


        Scanner scannerClientes = new Scanner(System.in);
        int opcao = scannerClientes.nextInt();

        switch(opcao){
            case 1:
                //Limpar o console em qualquer SO
                System.out.print("\033\143");
                try {
                    Dao.cadastrarCliente();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                menu();
                break;
            case 2:
                //Limpar o console em qualquer SO
                System.out.print("\033\143");
                try{

                    Cliente c = seletorCliente();
                    System.out.print("Qual veiculo deseja adicionar?\n\n");

                    for(int i = 0; i< Dao.veiculos.size(); i++){
                        Veiculo tempVeiculo = Dao.veiculos.get(i);
                        if(!tempVeiculo.getTemDono()){
                            System.out.println(tempVeiculo.getId() + "- " + tempVeiculo.getPlaca());
                        }
                    }

                    int selecao = scannerClientes.nextInt();
                    Veiculo v = Dao.veiculos.get(selecao);

                    
                    c.addVeiculo(v);
                }catch (IndexOutOfBoundsException e) {
                    System.out.println("Não foi possível localizar o id selecionado");
                }

                menu();
                break;
            case 3:
                //Limpar o console em qualquer SO
                System.out.print("\033\143");

                //Chama o seletor de estacionamentos
                Estacionamento e1 = seletorEstacionamentos();

                //Chama o menu novamente se o estacionamento não possuir clientes
                if(e1.getClientes().size() <= 0){ 
                    System.out.println("Estacionamento não possui clientes");
                    menu();
                }

                Cliente c1 = seletorClientePorEstacionamento(e1);

                if(c1.getVeiculosCount() <= 0){ 
                    System.out.println("Cliente selecionado não possui veículos cadastrados");
                    menu();
                }
                
                Veiculo v1 = seletorVeiculosDisponiveisPorCliente(c1);

                System.out.print("Quer algum serviço?\n\n");
                System.out.print("  1-Manobrista?\n");
                System.out.print("  2-Lavagem?\n");
                System.out.print("  3-Polimento?\n");
                System.out.print("  4-Nenhum?\n");

                int selecaoServicos = scannerClientes.nextInt();
                switch(selecaoServicos){
                    case 1:
                        Servicos serv = new Servicos("MANOBRISTA");
                        e1.estacionar(v1.getPlaca(), serv);
                        break;
                    case 2:
                        Servicos serv1 = new Servicos("LAVAGEM");
                        e1.estacionar(v1.getPlaca(), serv1);
                        break;
                    case 3:
                        Servicos serv3 = new Servicos("POLIMENTO");
                        e1.estacionar(v1.getPlaca(), serv3);
                        break;
                    case 4:
                        Servicos serv4 = new Servicos("NENHUM");
                        e1.estacionar(v1.getPlaca(), serv4);
                        break;
                        
                }
                menu();
                break;
            case 4:
                //Limpar o console em qualquer SO
                System.out.print("\033\143");

                Estacionamento e2 = seletorEstacionamentos();
                if(e2.getClientes().size() <= 0){ 
                    System.out.println("Estacionamento não possui clientes");
                    menu();
                }

                Cliente c2 = seletorClientePorEstacionamento(e2);

                if(c2.getVeiculosCount() <= 0){ 
                    System.out.println("Cliente selecionado não possui veículos cadastrados");
                    menu();
                }
            
                Veiculo v2 = seletorVeiculosEstacionadosPorCliente(c2);

                try{
                    e2.sair(v2.getPlaca());
                    System.out.println("Total pago pelo cliente: " + c2.calcularPagamento() + " ");
                }catch(IOException e){
                    System.out.println(e.getLocalizedMessage());
                }
                menu();
                break;
            case 5:
                //Limpar o console em qualquer SO
                System.out.print("\033\143");

                Estacionamento e3 = seletorEstacionamentos();
                Cliente c3 = seletorClientePorEstacionamento(e3);

                System.out.println(c3.usoDeEstacionamento(e3.toString()));

                menu();
                break;
            case 6:
                //Limpar o console em qualquer SO
                System.out.print("\033\143");

                Estacionamento est = seletorEstacionamentos();
                if(est.getClientes().size() <= 0){ 
                    System.out.println("Estacionamento não possui clientes");
                    menu();
                }
                Cliente c4 = seletorClientePorEstacionamento(est);

                System.out.println("Selecione(1-janeiro e assim por diante)");
                int mes = scannerClientes.nextInt();

                if(mes > 12 ||  mes < 1){
                    System.out.println("Digite um mês válido da próxima");
                    menu();
                }

                double total = c4.arrecadadoNoMes(mes);
                System.out.println(total);
                menu();
                break;
            case 7:
                try{
                    Estacionamento e = seletorEstacionamentos();
                    System.out.println(String.format("%.2f", e.valorMedio()));
                }catch(Exception e){
                    System.out.print(e);
                }
                menu();
                break;
            case 8:
                try {
                    Dao.cadastrarVeiculo();
                } catch (IOException e) {
                    e.getLocalizedMessage();
                }
            default :
                menu();
                break;
        }
    }

    /**
     * Menu do estacionamento
     */
    public static void Estacionamento() throws IOException{
        System.out.print("Estacionamento\n\n");
        System.out.print("    1-Adicionar cliente\n");
        System.out.print("    2-Alterar categoria do cliente\n\n");

        System.out.print("O que deseja?");


        Scanner s = new Scanner(System.in);
        int opcao = s.nextInt();

        switch (opcao) {
            case 1:
                System.out.println("Qual estacionamento?\n\n");
                for(int i = 0; i< Dao.estacionamentos.size(); i++){
                    System.out.println(i+"- "+ Dao.estacionamentos.get(i).toString());
                }

                int selecao1 = s.nextInt();
                Estacionamento e1 = Dao.estacionamentos.get(selecao1);

                System.out.println("Qual cliente?\n\n");

                for(int i = 0; i< Dao.clientes.size(); i++){
                    System.out.println(Dao.clientes.get(i).toString());
                }

                int selecao2 = s.nextInt();
                Cliente c = Dao.clientes.get(selecao2);

                System.out.print("Qual categoria?\n\n");
                System.out.print("0- Mensalista?\n");
                System.out.print("1- De turno?\n");
                System.out.print("2- Horista?\n");

                int selecaoCategoria = s.nextInt();

                switch (selecaoCategoria) {
                    case 0:
                        c.setCategoria(new Mensalista(c));
                        break;
                    case 1:
                        System.out.println("Qual turno?\n\n");
                        System.out.print("1- manha?\n2- tarde\n3- noite\n\n");
                        int turno = s.nextInt();
                        String turnoSring = "";

                        switch (turno) {
                            case 1:
                                turnoSring = "manha";
                                break;
                            case 2:
                                turnoSring = "tarde";
                                break;
                            case 3:
                                turnoSring = "noite";
                                break;
                        
                            default:
                                turnoSring = "manha";
                                break;
                        }

                        c.setCategoria(new DeTurno(c, turnoSring));
                        break;
                    
                    case 2:
                        c.setCategoria(new Horista(c));    
                        break;
                    default:
                        System.out.println("Opção inválida");
                    break;
                }
                e1.addCliente(c);
                Dao.salvarClienteEstacionamento(c, e1);

                menu();
                break;
            case 2:
                System.out.println("Qual estacionamento?\n\n");
                for(int i = 0; i< Dao.estacionamentos.size(); i++){
                    System.out.println(i+"- "+ Dao.estacionamentos.get(i).toString());
                }

                int selecaoEstacionamento = s.nextInt();
                Estacionamento estacionamentoSelecionado = Dao.estacionamentos.get(selecaoEstacionamento);

                System.out.println("Qual cliente?\n\n");

                for(int i = 0; i< estacionamentoSelecionado.getClientes().size(); i++){
                    System.out.println(estacionamentoSelecionado.getClientes().get(i).toString());
                }

                int selecaoCliente = s.nextInt();
                Cliente clienteSelecionado = estacionamentoSelecionado.getById(selecaoCliente);

                if(clienteSelecionado == null){ menu(); }

                System.out.print("Qual categoria?\n\n");
                System.out.print("0- Mensalista?\n");
                System.out.print("1- De turno?\n");
                System.out.print("2- Horista?\n");

                int selecaoCategoria2 = s.nextInt();

                switch (selecaoCategoria2) {
                    case 0:
                        clienteSelecionado.setCategoria(new Mensalista(clienteSelecionado));
                        break;
                    case 1:
                        System.out.println("Qual turno?\n\n");
                        System.out.print("1- manha?\n2- tarde\n3- noite\n\n");
                        int turno = s.nextInt();
                        String turnoSring = "";

                        switch (turno) {
                            case 1:
                                turnoSring = "manha";
                                break;
                            case 2:
                                turnoSring = "tarde";
                                break;
                            case 3:
                                turnoSring = "noite";
                                break;
                        
                            default:
                                turnoSring = "manha";
                                break;
                        }

                        clienteSelecionado.setCategoria(new DeTurno(clienteSelecionado, turnoSring));
                        break;
                    case 2:
                        clienteSelecionado.setCategoria(new Horista(clienteSelecionado));
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
                menu();
            default:
                menu();
                break;
        }
    }
    //#endregion

    //#region carga de dados
    public static void carregarDados() throws IOException{
        BufferedReader bre = new BufferedReader(new FileReader("estacionamentos.txt"));
        String linhae = "";
        
        while((linhae = bre.readLine()) != null){
            String[] linhas = linhae.split(";", 0);
            Estacionamento e = new Estacionamento(Integer.parseInt(linhas[0]), linhas[1], Integer.parseInt(linhas[2]), Integer.parseInt(linhas[3]));
            Dao.estacionamentos.add(e);	
        }

        BufferedReader brv = new BufferedReader(new FileReader("veiculos.txt"));
        String linhav = "";
        
        while((linhav = brv.readLine()) != null){
            String[] linhas = linhav.split(";", 0);
            Veiculo v = Dao.fabricaVeiculo.create();
            v.setId(Integer.parseInt(linhas[0]));
            v.setPlaca(linhas[1]);
            //Veiculo v = new Veiculo(Integer.parseInt(linhas[0]), linhas[1]);
            Dao.veiculos.add(v);
        }

        BufferedReader brc = new BufferedReader(new FileReader("clientes.txt"));
        String linhac = "";
        
        while((linhac = brc.readLine()) != null){
            String[] linhas = linhac.split(";", 0);
            Cliente c = new Cliente(linhas[0], Integer.parseInt(linhas[1]), null);
            Dao.clientes.add(c);
        }
        carregarDadosInternos();
    }

    public static void carregarDadosInternos() throws NumberFormatException, IOException, FileNotFoundException{
        //ClienteEstacionamento
        BufferedReader brec = new BufferedReader(new FileReader("estacionamentoClientes.txt"));
        String linhaec = "";
        
        while((linhaec = brec.readLine()) != null){
            String[] linhas = linhaec.split(";", 0);
            Dao.estacionamentos.get(Integer.parseInt(linhas[0])).addCliente(Dao.clientes.get(Integer.parseInt(linhas[1])));
        }

        //ClienteVeiculos
        BufferedReader brcv = new BufferedReader(new FileReader("clienteVeiculos.txt"));
        String linhacv = "";
        
        while((linhacv = brcv.readLine()) != null){
            String[] linhas = linhacv.split(";", 0);
            Dao.clientes.get(Integer.parseInt(linhas[0])).addVeiculo(Dao.veiculos.get(Integer.parseInt(linhas[1])));
        }

        //Usos de vagas
        BufferedReader bruv = new BufferedReader(new FileReader("usoDeVagas.txt"));
        String linhauv = "";

        while((linhauv = bruv.readLine()) != null){
            String[] linhas = linhauv.split(";", 0);

            Estacionamento e = Dao.estacionamentos.get(Integer.parseInt(linhas[2]));
            UsoDeVaga u = new UsoDeVaga(e.getVagaAleatoria(), linhas[3], linhas[4], new Servicos(linhas[5]));
            Cliente c = Dao.clientes.get(Integer.parseInt(linhas[0]));
            Veiculo v = c.getVeiculo(linhas[1]);

            v.adicionarUso(u);
        }
    }
    //#endregion

    //#region listas para seleções
    /**
     * Mostra na tela o seletor de estacionamentos
     */
    public static Estacionamento seletorEstacionamentos(){
        Scanner seletor = new Scanner(System.in);

        System.out.println("Qual estacionamento?\n\n");

        for(int i = 0; i< Dao.estacionamentos.size(); i++){
            System.out.println(i+"- "+ Dao.estacionamentos.get(i).toString());
        }

        int selecioando = seletor.nextInt();
        
        return Dao.estacionamentos.get(selecioando);
    }

    /**
     * Mostra na tela o seletor de clientes do estacionamento
     * @param e
     */
    public static Cliente seletorClientePorEstacionamento(Estacionamento e){
        Scanner seletor = new Scanner(System.in);

        System.out.print("Qual cliente?\n\n");

        for(int i = 0; i< e.getClientes().size(); i++){
            System.out.println(e.getClientes().get(i).toString());
        }

        int selecao2 = seletor.nextInt();

        Cliente c = e.getById(selecao2);

        return c;
    }

    /**
     * Mostra na tela o seletor de veiculos disponíveis para um cliente específico
     * @param c
     */
    public static Veiculo seletorVeiculosDisponiveisPorCliente(Cliente c){
        Scanner seletor = new Scanner(System.in);

        System.out.print("Qual veiculo?\n\n");
        System.out.println(c.imprimirVeiculosDisponiveis());

        int selecao6 = seletor.nextInt(); 

        return c.getVeiculo(selecao6);       
    }

    /**
     * Mostra na tela o seletor de veiculos estacionados para um cliente específico
     * @param c
     */
    public static Veiculo seletorVeiculosEstacionadosPorCliente(Cliente c){
        Scanner seletor = new Scanner(System.in);

        System.out.print("Qual veiculo?\n\n");
        System.out.println(c.imprimirVeiculosEstacionados());

        int selecao6 = seletor.nextInt(); 

        return c.getVeiculo(selecao6);       
    }

    /**
     * Mostra na tela o seletor de clientes no sistema
     */
    public static Cliente seletorCliente(){
        Scanner seletor = new Scanner(System.in);

        System.out.print("Qual cliente?\n\n");
        for(Cliente c: Dao.clientes){
            System.out.println(c.toString());
        }

        int selecaoCliente = seletor.nextInt();

        return Dao.clientes.get(selecaoCliente);
    }
    //#endregion
}
