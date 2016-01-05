package network;

import java.io.Serializable;
import java.util.Vector;

public class LettersMessage implements Serializable {
	 private static final long serialVersionUID = 17;
     
     private Vector<String> lettersPlaced;
     private Vector<Integer> lettersLocation;
    
     public LettersMessage(Vector<String> lettersPlaced,Vector<Integer> lettersLocation) {
    	 this.lettersPlaced = lettersPlaced;
    	 this.lettersLocation = lettersLocation;
     }
    
     public Vector<String> getLettersPlaced() {
    	 return lettersPlaced;
     }
     public Vector<Integer> getLettersLocation() {
    	 return lettersLocation;
     }
    
     public void setLettersPlaced(Vector<String> lettersPlaced2) {
    	 lettersPlaced = lettersPlaced2;
     }
     public void setLettersLocation(Vector<Integer> lettersLocation2) {
    	 lettersLocation = lettersLocation2;
     }
}
