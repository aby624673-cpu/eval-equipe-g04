
public class CompteMarchand extends Compte {

    private String enseigne;

    public CompteMarchand(String numero, String titulaire, String enseigne) {
        super(numero, titulaire);
        this.enseigne = enseigne;
    }
    //accesseur de l attribut enseigne
    public String getEnseigne() {
        return enseigne;
    }
    //redefinition des methodes de la classe compte sur la classe CompteMarchant.java
    @Override
    public long frais(long montant) {
        return montant * TAUX_FRAIS / 2000;
    }

    @Override
    public long plafond() {
        return 5 * PLAFOND_RETRAIT;
    }

    @Override
    public String type() {
        return "MAR";
    }

    @Override
    public String toString() {
        return super.toString() + " (" + enseigne + ")";
    }
    


}
