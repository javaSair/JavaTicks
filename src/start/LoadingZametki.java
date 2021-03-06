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

public class LoadingZametki {

    private SeekableByteChannel cahnel = null;
    private Thread t;
    private JavaTicks urlJavaTicks;
//    private  Scanner scan;
    private FileReader fileTicks;
    private FrameTicks urlFrameTicks;
    StandardPanel urlurlStandardPanel;
    private String kodirovka;
    public LoadingZametki(StandardPanel urlStandardPanel, JavaTicks urlJavaTicks) {
        this.urlJavaTicks = urlJavaTicks;
        this.urlurlStandardPanel = urlStandardPanel;

    }

    public void runScan(String nameZametki) {
//        System.out.println(nameZametki);
        String ticks = urlJavaTicks.getNameDefaultDir() + "\\" + nameZametki;
//        System.out.println(ticks);
        if (Files.exists(Paths.get(ticks)) == true) { // Заметка существует?
             
            urlJavaTicks.getFrameTicks().setNameZametki(nameZametki);
            if (Files.exists(Paths.get(urlJavaTicks.getNameDefaultDir() + "\\" + nameZametki + "\\Files"))) {
                try {
                    //                urlJavaTicks.frameTicks.animationScrolFile();
                    urlJavaTicks.getFrameTicks().setVisibleFileList(false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LoadingZametki.class.getName()).log(Level.SEVERE, null, ex);
                }
                urlJavaTicks.getFrameTicks().clearListButtonAddFile(); // Очистить коллекчию
              
                try {
                    DirectoryStream<Path> dir = Files.newDirectoryStream(Paths.get(urlJavaTicks.getNameDefaultDir() + "\\" + nameZametki + "\\Files"));
                    int count = 0;
                    for (Path x : dir) {
                        urlJavaTicks.getFrameTicks().setImageAdFile(x.toAbsolutePath().toString()); // Передать существующий файл
                         if (count++ > 6) {// Блок проверки количества файлов в заметке
                            urlJavaTicks.getFrameTicks().setVisibaldAdd(false); // Скрыть кнопку добавления файлов
                        }
                         
                        
//                        count = urlJavaTicks.frameTicks.getLenghtList() < 6;
                    }
                    urlJavaTicks.getFrameTicks().setActivatedAdd(true);
                    urlJavaTicks.getFrameTicks().getbAddFile2();
            
                } catch (IOException ex) {
                    System.err.println(ex);
                }

                // Если файл не существует создать
                if (!Files.exists(Paths.get(ticks + "\\" + ticks.substring(ticks.lastIndexOf("\\") + 1) + ".txt"))) {
                    try {
                        Files.createFile(Paths.get(ticks + "\\" + ticks.substring(ticks.lastIndexOf("\\") + 1) + ".txt"));
                    } catch (IOException ex) {
                        Logger.getLogger(LoadingZametki.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else { // в противном случае прочитать его содержимое
                    try (FileInputStream fileRead = new FileInputStream(Paths.get(ticks + "\\" + ticks.substring(ticks.lastIndexOf("\\") + 1) + ".txt").toAbsolutePath().toString())) 
                    {
 
//                        String codingRead = "Windows-1251";
//                        String codingRead = "UTF-8";

                        Reader r = new BufferedReader(new InputStreamReader(fileRead,  urlJavaTicks.getFrameTicks().getDecodeTXT()));

                        String contentTextBlock = "";
                        int i = 0;

                        while ((i = r.read()) != -1) {
                            contentTextBlock += (char) i;
                        }

//                        System.out.println(contentTextBlock);
                        urlJavaTicks.getFrameTicks().setTextBlock(contentTextBlock);
                        urlJavaTicks.getFrameTicks().setNameZametki(nameZametki);
                    } catch (IOException ex) {
                        Logger.getLogger(LoadingZametki.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                urlJavaTicks.urlStandardPanel.setVisiblePanelOpen(false);
//            urlJavaTicks.urlStandardPanel.add(urlFrameTicks,Integer.valueOf(8));
//             urlJavaTicks.urlStandardPanel.revalidate();
//              urlJavaTicks.urlStandardPanel.repaint();
                urlJavaTicks.getFrameTicks().setActivatedAdd(true);
                urlJavaTicks.setContentPane(urlJavaTicks.getFrameTicks());
            }

        } else {
//            System.out.println("проверка не пройдена");
            urlurlStandardPanel.setVisibalMessage(urlurlStandardPanel.fonPoppupMessagNO);
        }

    }
}
