package start;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadingZametki  {

    private SeekableByteChannel cahnel = null;
    private Thread t;
    private JavaTicks urlJavaTicks;
//    private  Scanner scan;
    private String name;
    private FileReader fileTicks;
    private FrameTicks urlFrameTicks;

    public LoadingZametki(JavaTicks urlJavaTicks, String nameZametki) {
         this.name = nameZametki;
        this.urlJavaTicks = urlJavaTicks;
        this.urlFrameTicks = urlJavaTicks.frameTicks;
  
    }

 
    public void runScan(){
        String ticks = urlJavaTicks.getNameDefaultDir() + "\\" + name;
        System.out.println(ticks);
        if (Files.exists(Paths.get(ticks))) {
            urlJavaTicks.frameTicks.setNameZametki(name);
            System.out.println(ticks + " Найден");
            if (Files.exists(Paths.get(urlJavaTicks.getNameDefaultDir() + "\\" + name + "\\Files"))) {
                try {
                    //                urlJavaTicks.frameTicks.animationScrolFile();
                    urlJavaTicks.frameTicks.setVisibleFileList(false);
                } catch (InterruptedException ex) {   Logger.getLogger(LoadingZametki.class.getName()).log(Level.SEVERE, null, ex);
                }
                urlJavaTicks.frameTicks.clearListButtonAddFile(); // Очистить коллекчию
                  boolean count = true;
                try {
                    DirectoryStream<Path> dir = Files.newDirectoryStream(Paths.get(urlJavaTicks.getNameDefaultDir() + "\\" + name + "\\Files"));
                    for (Path x : dir) {
                        urlFrameTicks.setImageAdFile(x.toAbsolutePath().toString()); // Передать существующий файл
                   count =urlJavaTicks.frameTicks.getLenghtList()<6;
                    }
                    urlJavaTicks.frameTicks.setActivatedAdd(true);
                    
                    {// Блок проверки количества файлов в заметке
                  
                        if(urlJavaTicks.frameTicks.getLenghtList()>6){
                     urlJavaTicks.frameTicks.setVisibaldAdd(count);
                    }
                      } } catch (IOException ex) {
                    System.err.println(ex);
                }
            
            // Если файл не существует создать
        if(!Files.exists(Paths.get(ticks+"\\"+ticks.substring(ticks.lastIndexOf("\\")+1)+".txt"))){
                try {
                    Files.createFile(Paths.get(ticks+"\\"+ticks.substring(ticks.lastIndexOf("\\")+1)+".txt"));
                } catch (IOException ex) {
                    Logger.getLogger(LoadingZametki.class.getName()).log(Level.SEVERE, null, ex);
                }
        }else{ // в противном случае прочитать его содержимое
 try ( FileInputStream fileRead = new FileInputStream(Paths.get(ticks+"\\"+ticks.substring(ticks.lastIndexOf("\\")+1)+".txt").toAbsolutePath().toString())
       ){

     String codingRead = "Windows-1251";
     
     Reader r=new BufferedReader(new InputStreamReader(fileRead, codingRead));
     
     
                String contentTextBlock = "";
                int i = 0;
                
              while((i=r.read())!=-1){
                  contentTextBlock += (char)i;
              }

              System.out.println(contentTextBlock);
                urlFrameTicks.setTextBlock(contentTextBlock);
                urlFrameTicks.setNameZametki(name);
            } catch (IOException ex) {
                Logger.getLogger(LoadingZametki.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
          urlJavaTicks.urlStandardPanel.setVisiblePanelOpen(false);
//            urlJavaTicks.urlStandardPanel.add(urlFrameTicks,Integer.valueOf(8));
//             urlJavaTicks.urlStandardPanel.revalidate();
//              urlJavaTicks.urlStandardPanel.repaint();
          urlJavaTicks.setContentPane(urlJavaTicks.frameTicks);
        }else{
            urlJavaTicks.urlStandardPanel.setVisibalMessage(urlJavaTicks.urlStandardPanel.fonPoppupMessagNO);
        }       
      

        
    }

}
}
