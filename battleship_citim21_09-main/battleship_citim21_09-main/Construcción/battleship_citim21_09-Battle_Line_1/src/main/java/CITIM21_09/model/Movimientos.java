package CITIM21_09.model;

import es.upm.etsisi.fis.model.IMovimiento;

public class Movimientos implements IMovimiento {

    private long partidaId;
    private int fila;
    private int columna;
    private long time;


    public Movimientos(long partidaId, int fila, int columna, long time) {
        this.partidaId = partidaId;
        this.fila = fila;
        this.columna = columna;
        this.time = time;
    }

    @Override
    public IMovimiento cloneMovimiento() {
        return new Movimientos(this.partidaId, this.fila, columna, this.time);
    }

    public long getPartidaId() {
        return partidaId;
    }

    @Override
    public void setPartidaId(Long partidaId) {
        if (partidaId != null) {
            this.partidaId = partidaId;
        }
    }

    public int getFila() {
        return fila;
    }

    @Override
    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public void setColumna(int columna) {
        this.columna = columna;
    }

    public long getTime() {
        return time;
    }

    @Override
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Movimientos{" +
                "partidaId=" + partidaId +
                ", fila=" + fila +
                ", columna=" + columna +
                ", time=" + time +
                '}';
    }
}
