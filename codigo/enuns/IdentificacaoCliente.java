package enuns;

public enum IdentificacaoCliente {
    IDENTIFICADO(true),
    NAO_IDENTIFICADO(false);

    boolean value;

    IdentificacaoCliente(boolean value){
        this.value = value;
    }
}
