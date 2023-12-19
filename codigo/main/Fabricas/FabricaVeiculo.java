package main.Fabricas;

import interfaces.IFabrica;
import main.Veiculo;

public class FabricaVeiculo implements IFabrica<Veiculo> {

    @Override
    public Veiculo create() {

        return new Veiculo();
    }
    
}
