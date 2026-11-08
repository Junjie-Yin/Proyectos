package CITIM21_09.model;

import es.upm.etsisi.fis.model.IPuntuacion;

public class Puntuacion implements IPuntuacion {

    private long partidaId;
    private long puntos;


    public Puntuacion(long id, long puntos) {
        this.puntos = puntos;
        this.partidaId = id;
    }

    public long getPartidaId() {
        return partidaId;
    }

    @Override
    public void setPartidaId(Long aLong) {
        this.partidaId = aLong;
    }

    @Override
    public long getPuntos() {
        return puntos;
    }

    @Override
    public IPuntuacion clonePuntuacion() {
        return new Puntuacion(partidaId, puntos);
    }

    @Override
    public void setPuntuacion(long l) {
        this.puntos = l;
    }

    @Override
    public String toString() {
        return "Puntuacion{" +
                "partidaId=" + partidaId +
                ", puntos=" + puntos +
                '}';
    }

}
