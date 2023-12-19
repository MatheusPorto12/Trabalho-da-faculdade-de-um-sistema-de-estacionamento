package main.testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTeste {
	Cliente cliente;
    Veiculo veic = null;
	
	@BeforeEach
	public void run() {
		cliente = new Cliente("Gabriel" , "0");
		veic = new Veiculo("NYA0978");
	}
	
	@Test
    public void validarVeiculoTest(){
        boolean res = cliente.validarVeiculo(veic);

        Assert.assertTrue(res);
    }
	
	@Test
	public void possuiVeiculoTest(){
		boolean res = cliente.possuiVeiculo("NYA0978");
		Assert.assertTrue(res);
	}
	
	@Test
	public void totalDeUsosTest(){
		int total = cliente.totalDeUsos();
		boolean res = total >= 0 ? true : false;
		Assert.assertTrue(res);
	}
	
	@Test
	public void arrecadadoPorVeiculoTest(){
		int total = cliente.arrecadadoPorVeiculo("NYA0978");
		boolean res = total >= 0 ? true : false;
		Assert.assertTrue(res);
	}
	
	@Test
	public void arrecadadoTotalTest(){
		int total = cliente.arrecadadoTotal();
		boolean res = total >= 0 ? true : false;
		Assert.assertTrue(res);
	}
	
}
