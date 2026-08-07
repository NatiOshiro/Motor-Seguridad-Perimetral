package ar.edu.unahur.obj2.motor;

import java.util.ArrayList;
import java.util.List;

public class PoliticaEstricta implements PoliticasDeFiltrado{
  private List <PoliticasDeFiltrado> Politicas = new ArrayList<>(); 

  @Override
  public Boolean rechazar(PaqueteRed unPaquete) {
    return;
  }

}
