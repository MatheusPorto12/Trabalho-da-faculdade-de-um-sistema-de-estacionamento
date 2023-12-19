package enuns;

public enum ServicosEnum {
   NENHUM("Nenhum", 0d,0d),
   MANOBRISTA("Manobrista", 5.0, 0.0),
   LAVAGEM("Lavagem", 20.0,60.0),
   POLIMENTO("Polimento", 45.0,120.0);

   private String nome;
   private double tempoMin;
   private double valor;

   ServicosEnum(String nome, Double valor , double tempo){
      this.nome = nome;
      this.tempoMin=tempo;
      this.valor=valor;
   }

   public double getTempoMin(){
      return tempoMin;
   }

   public Double getValor(){
      return valor;
   }

   public String getNome(){
      return nome;
   }
}