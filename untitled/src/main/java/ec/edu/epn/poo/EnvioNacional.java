package ec.edu.epn.poo;

public class EnvioNacional extends Envio{
    private boolean entregaDomicilio;

    public EnvioNacional(String guia, String destinatario, String ciudadDestino, double pesoKg, boolean entregaDomicilio) {
        super(guia, destinatario, ciudadDestino, pesoKg);
        this.entregaDomicilio = entregaDomicilio;
    }

    public boolean isEntregaDomicilio() {
        return entregaDomicilio;
    }

    public void setEntregaDomicilio(boolean entregaDomicilio) {
        this.entregaDomicilio = entregaDomicilio;
    }

    @Override
    public double calcularCosto() {
        double costo = super.calcularCosto();
        if (entregaDomicilio) {
            costo = costo + 1.50;
        }
        return costo;
    }

    @Override
    public String describir() {
        String texto = " | retira en agencia";
        if (entregaDomicilio) {
            texto = " | entrega a domicilio";
        }
        return super.describir() + texto;
    }
}


