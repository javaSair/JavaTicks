package start;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Patch;
import javax.swing.JLabel;

public class NewAnimatedIconFile {
    
    private JavaTicks urlJavaTicks;
    private FileReader reader;
    private FileWriter writer;
    private String zametka;
    private String nameFile;
    private String nameDefaultDir;
    private FileDialog fileDialog;
    private FrameTicks urlFrameTicks;
    
     

    /**
     * Установить директорю меток по умолчанию 
     * Установить директорю выбранной заметки
     */
    void settDir(){
        nameDefaultDir = urlJavaTicks.getNameDefaultDir();
        zametka = nameDefaultDir+"\\"+urlFrameTicks.getNameZametki();
    }
    
    
    
    /**
     * 
     * @param s
     * @return 
     * Получить Имя добавляемого в заметку файла
     * Создать директорию заметки в заданной по умолчанию директории
     * скопировать в директорию заметки добавляемый к ней файл
     */
    String copyFile(String s) { // s == файл который надо скопировать
       try {   
        
        if(Files.exists(Paths.get(s))){ // проверить существунт ли файл для копирования
//            if(Files.exists(Paths.get(zametka))) // Проверить существует ли директория
//            Files.createDirectories(Paths.get(zametka)); // Создать директорию заметки если она не существует
            
            nameFile =  urlFrameTicks.getJavaTicks().getNameDefaultDir()+ "\\" + urlFrameTicks.getNameZametki() + "\\Files\\"+s.substring(s.lastIndexOf("\\")+1);
//            System.out.println(nameFile);
            copyFile();
           Files.copy(Paths.get(s), Paths.get(nameFile).toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);
          }      
            } catch (IOException ex) { return "Ошибка копирования в файл"; }
       
        return nameFile;
        
    }

    private void copyFile() {
        try {
            writer = new FileWriter(new File(nameFile));
//            writer.write(zametka);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public NewAnimatedIconFile(FrameTicks urlClass) {

        this.urlFrameTicks = urlClass;
         

    }

}
