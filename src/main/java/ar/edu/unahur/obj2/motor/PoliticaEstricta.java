package ar.edu.unahur.obj2.motor;

import java.util.ArrayList;
import java.util.List;

public class PoliticaEstricta implements PoliticasDeFiltrado{
  private List <PoliticasDeFiltrado> Politicas = new ArrayList<>(); 

  public PoliticaEstricta(List<PoliticasDeFiltrado> politicas) {
    Politicas = politicas;
  }
  @Override
  public Boolean rechazar(PaqueteRed unPaquete) {
    return Politicas.stream().anyMatch(p ->p.equals(unPaquete));
  }
  public void agregarPolitica(PoliticasDeFiltrado politica){
    Politicas.add(politica);
  }
  public void sacarPoliticas(PoliticasDeFiltrado politica){
    Politicas.remove(politica);
  }

  }

}
