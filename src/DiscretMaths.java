import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class DiscretMaths {
    public static ResultadoEstadisticas calcularEstadisticas(ArrayList<Integer> dataset) {
        ResultadoEstadisticas resultado = new ResultadoEstadisticas();
        resultado.media = calcularMedia(dataset);
        resultado.moda = calcularModa(dataset);
        return resultado;
    }

    public static double calcularMedia(ArrayList<Integer> dataset) {
        int sum = 0;
        for (int value : dataset) {
            sum += value;
        }
        return (double) sum / dataset.size();
    }

    public static ArrayList<Integer> calcularModa(ArrayList<Integer> dataset) {
        HashMap<Integer, Integer> freqMap = new HashMap<>();
        for (int value : dataset) {
            freqMap.put(value, freqMap.getOrDefault(value, 0) + 1);
        }
        int maxFreq = 0;
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            maxFreq = Math.max(maxFreq, entry.getValue());
        }
        ArrayList<Integer> modeList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() == maxFreq) {
                modeList.add(entry.getKey());
            }
        }
        return modeList;
    }

    public static double calcularMediana(ArrayList<Integer> dataset, boolean ordenado) {
        if (!ordenado) {
            Collections.sort(dataset);
        }
        int n = dataset.size();
        if (n % 2 == 0) {
            return (dataset.get(n / 2 - 1) + dataset.get(n / 2)) / 2.0;
        } else {
            return dataset.get(n / 2);
        }
    }

    public static class ResultadoEstadisticas {
        public double media;
        public ArrayList<Integer> moda;
    }

    public static class Intervalo {
        private final double limiteInferior;
        private final double limiteSuperior;
        private final int frecuenciaAbsoluta;
        private double puntoMedio;
        private int frecuenciaAcumulada;
        private double frecuenciaRelativa;
        private double frecuenciaRelativaAcumulada;
        private double porcentaje;

        public Intervalo(double limiteInferior, double limiteSuperior, int frecuenciaAbsoluta) {
            this.limiteInferior = limiteInferior;
            this.limiteSuperior = limiteSuperior;
            this.frecuenciaAbsoluta = frecuenciaAbsoluta;
        }

        public int getFrecuenciaAbsoluta() {
            return this.frecuenciaAbsoluta; //Suma de valores del intervalo -1
        }

        public double getFrecuenciaRelativa() {
            return this.frecuenciaRelativa; // Se divide la suma de lo resultados de valor absoluta entre cada valor individualmente: 5/50= 0.10
        }

        public void calcularPuntoMedio() {
            this.puntoMedio = (this.limiteInferior + this.limiteSuperior) / 2; //Se suma el Lim.inferior + Lim.Superior / 2: 10+19 = 29/2 = 14.5
        }

        public void setFrecuenciaAcumulada(int frecuenciaAcumulada) {
            this.frecuenciaAcumulada = frecuenciaAcumulada; //Es una suma en diagonal de la frecuencia absoluta: 5+11 = 16, 16+8 = 24
        }

        public void calcularFrecuenciaRelativa(int total) {
            this.frecuenciaRelativa = (double) this.frecuenciaAbsoluta / total; // Es la suma de los valores de F.Absoluta entre cada valor:5+11+8+5+8+6+7 = 50 5/50 = 0.10
        }

        public void setFrecuenciaRelativaAcumulada(double frecuenciaRelativaAcumulada) {
            this.frecuenciaRelativaAcumulada = frecuenciaRelativaAcumulada; //Es una suma en diagonal de la frecuencia relativa: 0.10+0.22 = 0.32, 0.32+0.16 = 0.48
        }

        public void calcularPorcentaje() {
            this.porcentaje = this.frecuenciaRelativaAcumulada * 100; // Se multiplica la frecuencia acumulada x 100
        }

        public String toString() {
            return String.format("| %.2f - %.2f   | %d                   | %.2f        | %d                    | %.2f                | %.2f                          | %.2f%%     |",
                    limiteInferior, limiteSuperior, frecuenciaAbsoluta, puntoMedio, frecuenciaAcumulada,
                    frecuenciaRelativa, frecuenciaRelativaAcumulada, porcentaje);
        }
    }
}