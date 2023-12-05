import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> dataset = Dataset.getDataset();
        Collections.sort(dataset);

        DiscretMaths.ResultadoEstadisticas resultado = DiscretMaths.calcularEstadisticas(dataset);
        double media = resultado.media;
        ArrayList<Integer> moda = resultado.moda;

        System.out.println(dataset);

        System.out.println("Las medidas de Tendencia Central:");
        System.out.println("Media (promedio): " + media);
        System.out.println("Mediana: " + DiscretMaths.calcularMediana(dataset, true));
        System.out.println("Moda: " + moda);
        System.out.println("Datos de salida desde la clase Dataset:");
        System.out.println("| Intervalo     | Frecuencia Absoluta | Punto Medio | Frecuencia Acumulada | Frecuencia Relativa | Frecuencia Relativa Acumulada | Porcentaje |");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");

        int rango = dataset.get(dataset.size() - 1) - dataset.get(0);
        int numClases = (int) Math.ceil(1 + 3.322 * Math.log10(dataset.size()));
        double amplitudIntervalo = (double) rango / numClases;

        ArrayList<DiscretMaths.Intervalo> intervalos = new ArrayList<>();
        double limiteInferior = dataset.get(0);

        for (int i = 0; i < numClases - 1; i++) {
            double limiteSuperior = limiteInferior + amplitudIntervalo;
            int frecuenciaAbsoluta = 0;
            for (int data : dataset) {
                if (data >= limiteInferior && data < limiteSuperior) {
                    frecuenciaAbsoluta++;
                }
            }
            DiscretMaths.Intervalo intervalo = new DiscretMaths.Intervalo(limiteInferior, limiteSuperior, frecuenciaAbsoluta);
            intervalos.add(intervalo);
            limiteInferior = limiteSuperior;
        }

        int frecuenciaAbsoluta = 0; //Calculo F.Absoluta
        for (int data : dataset) {
            if (data >= limiteInferior && data <= dataset.get(dataset.size() - 1)) {
                frecuenciaAbsoluta++;
            }
        }
        DiscretMaths.Intervalo ultimoIntervalo = new DiscretMaths.Intervalo(limiteInferior, dataset.get(dataset.size() - 1), frecuenciaAbsoluta);
        intervalos.add(ultimoIntervalo);

        int total = dataset.size();
        int frecuenciaAcumulada = 0;
        double frecuenciaRelativaAcumulada = 0;

        for (DiscretMaths.Intervalo intervalo : intervalos) {
            intervalo.calcularPuntoMedio(); //Calculo de Punto Medio
            frecuenciaAcumulada += intervalo.getFrecuenciaAbsoluta();
            intervalo.setFrecuenciaAcumulada(frecuenciaAcumulada); //Calculo F.Acumulada
            intervalo.calcularFrecuenciaRelativa(total);
            frecuenciaRelativaAcumulada += intervalo.getFrecuenciaRelativa(); //Calculo de F.Relativa Acumulada
            intervalo.setFrecuenciaRelativaAcumulada(frecuenciaRelativaAcumulada);
            intervalo.calcularPorcentaje();
            System.out.println(intervalo);
        }
    }
}