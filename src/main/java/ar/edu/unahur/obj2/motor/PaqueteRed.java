package ar.edu.unahur.obj2.motor;

/**
 * Alerta
 */

public class PaqueteRed {

  private Integer IPOrigen;
  private Integer IPDestino;
  private Integer Puerto;
  
  public PaqueteRed(Integer iPOrigen, Integer iPDestino, Integer puerto) {
    IPOrigen = iPOrigen;
    IPDestino = iPDestino;
    Puerto = puerto;
  }

  public Integer getIPOrigen() {
    return IPOrigen;
  }

  public Integer getIPDestino() {
    return IPDestino;
  }

  public Integer getPuerto() {
    return Puerto;
  }

}
