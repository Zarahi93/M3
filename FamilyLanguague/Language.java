class Language {
    //Crear variables protegidas
      protected String name;
      protected int numSpeakers;
      protected String regionsSpoken;
      protected String wordOrder;
    
      Language (String langName, int speakers, String regions, String wdOrder){
        this.name = langName;
        this.numSpeakers = speakers;
        this.regionsSpoken = regions;
        this.wordOrder = wdOrder;
      }
    
      public static void main(String[] args) {
        Language spanish = new Language("Spanish", 555000000, "Spain, Latin America, and Equatorial Guinea", "subject-verb-object");
        spanish.getInfo();
    
        Mayan chontal = new Mayan("Chontal", 60563);  
        chontal.getInfo();
    
       SinoTibetan  mandarin = new SinoTibetan("Mandarin Chinese",1110000000);
       mandarin.getInfo();
    
       SinoTibetan  burmese = new SinoTibetan("Burmese",33000000);
       burmese.getInfo();
      }
    
      public void getInfo(){
      System.out.println(this.name + " is spoken by " + this.numSpeakers + " people mainly in " + this.regionsSpoken + ".");
      System.out.println("The language follows the word order: " + this.wordOrder +".");
      }
    }