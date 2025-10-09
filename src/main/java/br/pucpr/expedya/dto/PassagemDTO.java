package br.pucpr.expedya.dto;

public class PassagemDTO {
    private String id;
    private String origem;
    private String destino;
    private String dataPartida;
    private String horaPartida;
    private String assento; // Ex: 12A, 14B
    private String classe; // ECONÔMICA, EXECUTIVA, PRIMEIRA

    public void setId(String id) { this.id = id; }

    public String getId() { return id; }

    public void setOrigem(String origem) { this.origem = origem; }

    public String getOrigem() { return origem; }

    public void setDestino(String destino) { this.destino = destino; }

    public String getDestino() { return destino; }

    public void setDataPartida(String dataPartida) { this.dataPartida = dataPartida; }

    public String getDataPartida() { return dataPartida; }

    public void setHoraPartida(String horaPartida) { this.horaPartida = horaPartida; }

    public String getHoraPartida() { return horaPartida; }

    public void setAssento(String assento) { this.assento = assento; }

    public String getAssento() { return assento; }

    public void setClasse(String classe) { this.classe = classe; }

    public String getClasse() { return classe; }
}
