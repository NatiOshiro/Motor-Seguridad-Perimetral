package ar.edu.unahur.obj2.motor;

public class BloqueoPorIP implements PoliticasDeFiltrado{
  private Integer IpMaliciosos;

  public BloqueoPorIP(Integer ipMaliciosos) {
    IpMaliciosos = ipMaliciosos;
  }

  @Override
  public Boolean rechazar(PaqueteRed unPaquete) {
    return unPaquete.getIPOrigen().equals(IpMaliciosos);
  }

}
