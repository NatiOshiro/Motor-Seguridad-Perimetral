package ar.edu.unahur.obj2.motor;

import java.util.ArrayList;
import java.util.List;

public class PoliticaPemisiva implements PoliticasDeFiltrado{

  private List<PoliticasDeFiltrado> Politicas = new ArrayList<>();
  
  public PoliticaPemisiva(List<PoliticasDeFiltrado> politicas) {
    Politicas = politicas;
  }

  public void agregarPoliticas (PoliticasDeFiltrado unaPolitica){
    Politicas.add(unaPolitica);
  }

  public void eliminarPolitica(PoliticasDeFiltrado unaPolitica){
    Politicas.remove(unaPolitica);
  }
  @Override
  public Boolean rechazar(PaqueteRed unPaquete) {
    return Politicas.stream().allMatch(p ->p.equals(unPaquete));
  }

}
