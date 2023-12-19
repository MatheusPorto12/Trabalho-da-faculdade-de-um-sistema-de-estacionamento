 
import org.junit.Test;

public class TesteUsoDeVaga {
    Vaga teste= new Vaga('B', 01);
    UsoDeVaga A = new UsoDeVaga(teste);
    @Test
    public void teste1sair(){
      assert (A.sair()>=0);
    }
    @Test
    public void testeFornecerServiço(){
        String m="MANOBRISTA";
        A.fornecerServiço(m);
        Servicos b = new Servicos(m);
        assert (A.fornecerServiço(m).toString().equals(b.toString()));
    }
    @Test
    public void testeValorPago(){
        Vaga nova = new Vaga('A', 3);
        UsoDeVaga n= new UsoDeVaga(nova);
        n.fornecerServiço("LAVAGEM");
        n.sair();
        n.valorPago();
        assert(n.valorPago()>=20);
    }
}