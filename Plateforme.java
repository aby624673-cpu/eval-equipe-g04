public class Plateforme {
    private final Compte[]comptes=new Compte[Parametres.CAPACITE];
    private int Comptes;
    private final String []journal=new String[100];
    private int nombreLignes;
    private long totalFrais;
    public boolean ajoute (Compte compte){
        if(compte == null || nombre >=comptes.length || recherche(compte.getNumero()) != null){
            return false;
        }
        comptes[nombre] = compte;
        nombre++;  
        return true;
        }
        public Compte recherche(String numero){
            for(int i=0; i<nombre; i++){
                if(comptes[i].getNumero().equals(numero)){
                    return comptes[i];
                }
            }
            return null;
        }
        public int getNombre(){
            return nombre;
        }
        public boolean tranferer(String de, String vers, long montant){
            if(montant <= 0 || de == null || vers == null|| de.equals(vers)||nombreLignes >= journal.length){
                return false;
            }
            Compte source = recherche(de);
            Compte destination = recherche(vers);
            if(source == null || destination == null){
                return false;
            }
            long frais = source.frais(montant);
            long montantTotal = montant + frais;
            if(montant>source.plafond()||montantTotal>source.getSolde()||montant>long.MAX_VALUE-destination.getSolde()){
                return false;
              
    }
    if(!source.retirer(montantTotal)){
        return false;
    }
   if(!destinataire.deposer(montant)){
    source.deposer(montantTotal); 
    return false; 
    }
    totalFrais += frais;
    int numero Transaction = nombreLignes + 1;
    String identifiant="TX" +(numeroTransaction<10?"0000" :  numeroTransaction<100?"00" :"0" )+ numeroTransaction;
    journal[nombreLignes] = identifiant + ""+ de + "->" + vers + " : " + montant + "FCFA (frais : " + frais + ")";
    nombreLignes++;
    return true;
}
public long getTotalFrais() {
    return totalFrais;  

}
public String historique(String numero){
    if(numero == null){
        return "";
    }
    StringBuilder resultat = new StringBuilder();
    for(int i=0; i<nombreLignes; i++){
       
            String ligne = journal[i];
           int separateur = ligne.indexOf(":");
           int debutNumero = ligne.indexOf("->")+1;
           int debutDestinataire = separateur + 4;
           int finDestinataire = ligne.indexOf("", debutDestinataire);
           String source = ligne.substring(debutNumero, separateur -1);
           String destinataire = ligne.substring(debutDestinataire, finDestinataire);
           if(source.equals(numero)||destinataire.equals(numero)){

            if(resultat.length() > 0){
                resultat.append("\n");
            }
            resultat.append(ligne);
        }
    }
    return resultat.toString();
}
pubilc int []compter ParType(){
    int standard = 0;
    int premium = 0;
    int marchand = 0;
    for(int i=0;i<nombre;i++){
        if(comptes[i] instanceof CompteMarchand){
            marchand++;


    }else if(comptes[i] instanceof ComptePremium){
        premium++;
    }
    else if(comptes[i] instanceof CompteStandard){
       standard++;
    
}
}
return new int[]{standard, premium, marchand};
}
public void trierParSolde(){
    for(int i=0; i<nombre-1; i++){
        compte courant = comptes[i];
        int position = i - 1;
        while(position >= 0 && comptes[position].getSolde() > courant.getSolde()|| (comptes[position].getSolde() == courant.getSolde() && (comptes[position].getNumero().compareTo(courant.getNumero()) > 0))){
            comptes[position + 1] = comptes[position];
            position--;
            }
            comptes[position + 1] = courant;
        }
    }
    public compte []compte(){
        compte[] copie = new compte[nombre];
        for(int i=0; i<nombre; i++){
            copie[i] = comptes[i];
        }
        return copie;
    }
    public compte[]comptesAuDessus(long seuil){
        int nombreCorespondant = 0;
        for(int i=0; i<nombre; i++){
            if(comptes[i].getSolde() > seuil){
                nombreCorespondant++;
            }
        }
        compte[] resultat = new compte[nombreCorespondant];
        int position = 0;
        for(int i=0; i<nombre; i++){
            if(comptes[i].getSolde() > seuil){
                resultat[position] = comptes[i];
                position++;
            }
        }
        return resultat;
    }
}
          