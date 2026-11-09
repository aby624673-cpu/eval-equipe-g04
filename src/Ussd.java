public final class Ussd {
    /* Classe utilitaire pour la gestion des numéros USSD */
    private Ussd() {
    }

    public static boolean numeroValide(String numero) {  
        if (numero == null || numero.length() != 9) {
            return false;
        }
        String prefixe = numero.substring(0, 2);                                          
        if (!prefixe.equals("70") && !prefixe.equals("75") && !prefixe.equals("76")
            && !prefixe.equals("77") && !prefixe.equals("78")) {                                 
            return false;
        }
        for (int i = 0; i < numero.length(); i++) {                                             
            char caractere = numero.charAt(i);                                                  
            if (caractere < '0' || caractere > '9') {                                            
                return false;
            }
        }
        return true;
    }


    public static String normaliser(String texte) {
        if (texte == null) {   
            return null;
        }
        StringBuilder compact = new StringBuilder();                         
        for (int i = 0; i < texte.length(); i++) {
            char caractere = texte.charAt(i);
            if (caractere != ' ' && caractere != '.' && caractere != '-') {   
                compact.append(caractere);   
            }
        }

        String numero = compact.toString();     
        if (numero.startsWith("+221")) {        
            numero = numero.substring(4);       
        } else if (numero.startsWith("00221")) {
            numero = numero.substring(5);     
        }
        return numeroValide(numero) ? numero : null;  
    }

    public static String masquer(String numero) {
        if (numero == null || numero.length() < 4) {    
            return null;
        }
        StringBuilder resultat = new StringBuilder();     
        resultat.append(numero.charAt(0)).append(numero.charAt(1));   
        for (int i = 2; i < numero.length() - 2; i++) {   
            resultat.append('*');
        }
        resultat.append(numero.charAt(numero.length() - 2)).append(numero.charAt(numero.length() - 1));   
        return resultat.toString();
    }

    public static long lireMontant(String texte) {
        if (texte == null || texte.length() < 1 || texte.length() > 9) {  
        }
        long montant = 0;                  
        for (int i = 0; i < texte.length(); i++) {  
            char caractere = texte.charAt(i);    
            if (caractere < '0' || caractere > '9') {  
                return -1;
            }
            montant = montant * 10 + caractere - '0';  
        }
        return montant;
    }

  
    public static String formaterMontant(long montant) {
        String texte = Long.toString(montant);     
        StringBuilder resultat = new StringBuilder();  
        int debutChiffres = texte.charAt(0) == '-' ? 1 : 0;    
        for (int i = 0; i < texte.length(); i++) {           
            if (i > debutChiffres && (texte.length() - i) % 3 == 0) {  
                resultat.append(' ');
            }
            resultat.append(texte.charAt(i)); 
        }
        return resultat.toString(); 
    }

    public static String[] decouper(String code) {  
        if (code == null || code.length() < 3 || code.charAt(0) != '*' 
                || code.charAt(code.length() - 1) != '#') {   
            return null;
        }

        int nombreChamps = 1;  
        int debutChamp = 1;
        for (int i = 1; i < code.length() - 1; i++) {  
            char caractere = code.charAt(i);  
            if (caractere == '#') {  
                return null;
            }
            if (caractere == '*') {  
                if (i == debutChamp) {  
                    return null;
                }
                nombreChamps++; 
                debutChamp = i + 1; 
            }
        }
        if (debutChamp == code.length() - 1) {  
            return null;
        }

        String[] champs = new String[nombreChamps];  
        int numeroChamp = 0;
        debutChamp = 1;  
        for (int i = 1; i < code.length() - 1; i++) {  
            if (code.charAt(i) == '*') {  
                champs[numeroChamp] = code.substring(debutChamp, i); 
                numeroChamp++; 
                debutChamp = i + 1;
            }
        }
        champs[numeroChamp] = code.substring(debutChamp, code.length() - 1);  
        return champs;
    }

    public static String executer(Plateforme plateforme, String appelant, String code) {
        String[] champs = decouper(code);  
        if (champs == null) {
            return "Code invalide";
        }
        if (!champs[0].equals("144")) {
            return "Service inconnu";
        }
        Compte source = plateforme.rechercher(appelant);
        if (source == null) {
            return "Compte inconnu";
        }
        if (champs.length < 2) {
            return "Code invalide";
        }
        if (!champs[1].equals("1") && !champs[1].equals("2")) {  
            return "Service inconnu";
        }
        if (champs[1].equals("2")) {
            if (champs.length != 2) {
                return "Code invalide";
            }
            return "Solde : " + formaterMontant(source.getSolde()) + " FCFA";
        }
        if (champs.length != 4) {
            return "Code invalide";
        }

        String destinataire = normaliser(champs[2]);
        if (destinataire == null) {
            return "Numero invalide";
        }
        long montant = lireMontant(champs[3]);
        if (montant <= 0) {
            return "Montant invalide";
        }

        Compte compteDestinataire = plateforme.rechercher(destinataire); 
        long frais = source.frais(montant);
        if (compteDestinataire == null || !plateforme.transferer(appelant, destinataire, montant)) {   //
            return "Transfert refuse";
        }
        return "Transfert de " + formaterMontant(montant) + " FCFA vers "
                + masquer(destinataire) + " effectue. Frais : " + formaterMontant(frais) + " FCFA";
    }
}
