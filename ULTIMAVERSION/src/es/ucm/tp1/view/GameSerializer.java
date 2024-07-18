package es.ucm.tp1.view;
import  es.ucm.tp1.logic.Game;

public class GameSerializer {
   private static final String MSG_SERIALIZER="----ROAD FIGHTER SERIALIZED ----";
   public static String Serializermsg(Game g) {
	   StringBuilder s=new StringBuilder();
	   s.append(MSG_SERIALIZER);
	   s.append("\nLevel: "+g.getLevel());
	   s.append("\nCycles: "+g.getCycle());
	   s.append("\nCoins: "+g.playerCoins());
	   if(g.isTestMode()) {
		   s.append("\nEllapsed Time:  "+g.time());
	   }
	   s.append("\nGameObjects :\n");
	   s.append(g.symbolpl()+" ( "+g.posplayer()+","+g.rowplayer()+")\n");
    for(int i=0;i<g.getlength();i++) {
    	for(int j=0;j<g.getlevelrow();j++) {
    		s.append(g.serializetoString(i,j));
    	}
    }
	   
	return s.toString();
	   
   }
   public static void printSerializer(Game game) {
	   System.out.println(Serializermsg(game));
   }
   public static void dump(String str) {
	   System.out.println(str);
   }
}
