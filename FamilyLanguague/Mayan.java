class Mayan extends Language{

    Mayan (String languageName, int speakers){
      super(languageName, speakers, "Central America", "verb-object-subject");
    }
    @Override
    public void getInfo(){
     super.getInfo();
    System.out.println("Fun fact: " + this.name + "is an ergative language.");
    }
  
  }