package main;

import enuns.ServicosEnum;

public class Servicos{
    ServicosEnum tipoServico;

    public Servicos(String tipo){
        String t = tipo;
        t.toUpperCase();

        switch(t){
            case "MANOBRISTA":
                this.tipoServico = ServicosEnum.MANOBRISTA;
                break;
            case"LAVAGEM":
                this.tipoServico = ServicosEnum.LAVAGEM;
                break;
            case"POLIMENTO":
            this.tipoServico = ServicosEnum.POLIMENTO;
                break;
            case"NENHUM":
                this.tipoServico = ServicosEnum.NENHUM;
                break;
        }
    }
    
    /**
	 * Getters and Setters
	 * */
    public Double valorServico(){
        return this.tipoServico.getValor();
    }

    /**
	 * Getters and Setters
	 * */
    public double tempoMin(){
        return this.tipoServico.getTempoMin();
    }

    /**
	 * Getters and Setters
	 * */
    public String nome(){
        return this.tipoServico.getNome();
    }

    /**
	 * Getters and Setters
	 * */
    public String toString(){
        return nome();
    }
}