package ec.edu.epn.poo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);

        //HashMap de envios en transito: la clave es la guia,
        //el valor es el objeto Envio (puede ser nacional, express o internacional)
        HashMap<String, Envio> enTransito = new HashMap<>();

        //Envios que ya fueron entregados
        ArrayList<Envio> entregados = new ArrayList<>();

        //Contador unico para toda la empresa (NAC1001, EXP1002, INT1003...)
        int contador = 1001;
        int opcion = 0;

        //Menu que se repite hasta que el empleado elija salir
        while (opcion != 5) {
            System.out.println();
            System.out.println("1. Registrar envio");
            System.out.println("2. Consultar envio");
            System.out.println("3. Entregar envio");
            System.out.println("4. Listar entregados");
            System.out.println("5. Salir");
            System.out.print("Ingrese su opcion: ");

            //Todo se lee como texto con nextLine() y luego se convierte con parsing
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                System.out.println("Tipo (1 nacional, 2 express, 3 internacional): ");
                int tipo = Integer.parseInt(sc.nextLine());
                System.out.println("Destinatario: ");
                String destinatario = sc.nextLine();
                System.out.println("Ciudad de destino: ");
                String ciudad = sc.nextLine();
                System.out.println("Peso en kg: ");
                double peso = Double.parseDouble(sc.nextLine());
                Envio e = null;

                if (peso <= 0) {
                    System.out.println("El peso debe ser mayor que cero. No se registro el envio");
                } else if (tipo == 1) {
                    System.out.println("Entrega a domicilio (S/N): ");
                    boolean domicilio = sc.nextLine().equals("S");
                    e = new EnvioNacional("NAC" + String.valueOf(contador), destinatario, ciudad, peso, domicilio);
                } else if (tipo == 2) {
                    System.out.println("Entrega a domicilio (S/N): ");
                    boolean domicilio = sc.nextLine().equals("S");
                    System.out.println("El cliente eligio 24 horas (S/N): ");
                    String respuesta = sc.nextLine();
                    if (respuesta.equals("S")) {
                        e = new EnvioExpress("EXP" + String.valueOf(contador), destinatario, ciudad, peso, domicilio, 24);
                    } else {
                        e = new EnvioExpress("EXP" + String.valueOf(contador), destinatario, ciudad, peso, domicilio);
                    }
                } else if (tipo == 3) {
                    System.out.println("Valor de la compra en dolares: ");
                    double valor = Double.parseDouble(sc.nextLine());
                    EnvioInternacional inter = new EnvioInternacional("INT" + String.valueOf(contador), destinatario, ciudad, peso, valor);
                    if (inter.cumple4x4()) {
                        e = inter;
                    } else {
                        System.out.println("Envio rechazado: no cumple la regla 4x4");
                    }
                } else {
                    System.out.println("Tipo invalido");
                }
                if (e != null) {
                    enTransito.put(e.getGuia(), e);
                    contador++;
                    System.out.println("Guia: " + e.getGuia() + " | Costo: " + e.calcularCosto());
                }

            } else if (opcion == 2) {
                System.out.println("Guia: ");
                String guia = sc.nextLine();
                if (enTransito.containsKey(guia)) {
                    Envio e = enTransito.get(guia);
                    System.out.println(e.describir() + " | Costo: " + e.calcularCosto());
                } else {
                    System.out.println("No existe un envio en transito con esa guia");
                }

            } else if (opcion == 3) {
                System.out.println("Guia: ");
                String guia = sc.nextLine();
                if (enTransito.containsKey(guia)) {
                    Envio e = enTransito.get(guia);
                    enTransito.remove(guia);
                    entregados.add(e);
                    System.out.println("Envio " + guia + " entregado");
                } else {
                    System.out.println("No existe un envio en transito con esa guia");
                }

            } else if (opcion == 4) {
                if (entregados.size() == 0) {
                    System.out.println("Todavia no hay envios entregados");
                } else {
                    double total = 0;
                    for (Envio e : entregados) {
                        System.out.println(e.describir() + " | Costo: " + e.calcularCosto());
                        total = total + e.calcularCosto();
                    }
                    System.out.println("Total recaudado: " + total);
                }

            } else if (opcion != 5) {
                System.out.println("Opcion invalida");
            }
        }
    }
}
