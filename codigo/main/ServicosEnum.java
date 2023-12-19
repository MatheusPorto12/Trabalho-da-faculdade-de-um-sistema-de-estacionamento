package main;

public enum ServicosEnum {
    MANOBRISTA(5.0, 0.0),
    LAVAGEM(20.0,60.0),
    POLIMENTO(45.0,120.0);

    private double tempoMin;
    private double valor;

     ServicosEnum(Double valor , double tempo){
      this.tempoMin=tempo;
      this.valor=valor;
}
public double getTempoMin(){
   return tempoMin;
}
public Double getValor(){
   return valor;
}
}