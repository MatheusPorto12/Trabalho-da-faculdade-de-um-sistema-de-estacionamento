package main;

import java.time.LocalDateTime;

import interfaces.ICategoriaCliente;

public class Mensalista implements ICategoriaCliente{
    Cliente cli;
    boolean pago = false;

    public Mensalista(Cliente c){
        this.cli=c;
    }
    
    /**
	 * Calcula o valor a pagar pelo mensalista.
	 */
    public double calcularPagamento(){
        System.out.print("Mensalista");
        if(pago){ return 0d; }


        if(LocalDateTime.now().getDayOfMonth() >= 30){
            return 500.00;
        }else if(LocalDateTime.now().getDayOfMonth() < 30){
            this.pago = false;
            return 0d;
        }else{ return 0d; }
    }
}
