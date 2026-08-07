package ar.edu.unahur.obj2.motor;

public class BloqueoPorPuerto implements PoliticasDeFiltrado{
  private Integer PuertoRestringido;

  @Override
  public Boolean rechazar(PaqueteRed unPaquete) {
    return unPaquete.getIPDestino() == PuertoRestringido;
  }

}
