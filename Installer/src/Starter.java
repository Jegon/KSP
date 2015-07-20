import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by Jegoni on 22.05.2015.
 */
public class Starter {
    Starter(){
        try{
            File f = new File("H:/KP/KP.jar");
            File d = new File("H:/KP");
            File mjvsrc = new File("//SERVTRM/Alle/10CInf/KP/KP.jar");
            File out = new File("H:/KP/KP.jar");
            if(f.exists() && !f.isDirectory()) {
                Process mjv = Runtime.getRuntime().exec("cmd /c \"java -jar H:/KP/KP.jar\"");
            }else{
                if(d.mkdir()){
                    System.out.println("Kollege, ich habn Ordner KP under H:/ erstellt, kay?");
                    Files.copy(mjvsrc.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Process mjv = Runtime.getRuntime().exec("cmd /c \"java -jar H:/KP/KP.jar\"");
                }else{
                    System.err.println("Konnte Ordner KP unter H:/ nicht erstellen!");
                    Files.copy(mjvsrc.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Process mjv = Runtime.getRuntime().exec("cmd /c \"java -jar H:/KP/KP.jar\"");
                }
            }
        }catch(Exception ex){
            System.err.println("Ach du Schei**!");
            ex.printStackTrace();
        }
    }
}
