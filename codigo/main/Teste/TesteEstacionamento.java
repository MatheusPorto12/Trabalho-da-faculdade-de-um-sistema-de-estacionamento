package lib;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class TesteEstacionamento {
    Estacionamento teste = new Estacionamento("Porto's parking", 5, 3);
    Cliente nelson = new Cliente("Nelson", "nelsin");

    @Test
    public void testeAddCliente(){
     Estacionamento teste = new Estacionamento("Cleyton's parking", 10, 5) ;
     Cliente um= new Cliente("Matheus", "mat");
     teste.addCliente(um);
     assertEquals(teste.getClientes().get(0),um);
}
   @Test
   public void testeGerarVagas(){
    Estacionamento teste = new Estacionamento("Porto's parking", 5, 3);
    teste.gerarVagas();
    assertNotNull(teste.getVagas());
   }
   @Test
   public void testeEstacionar1(){
     teste.gerarVagas();
    Veiculo doNelson= new Veiculo("abc-1234");
    nelson.addVeiculo(doNelson);
    teste.estacionar(doNelson.getPlaca());
    assertTrue(teste.getVagas().get(1748225580).disponivel()==true);
   }
}