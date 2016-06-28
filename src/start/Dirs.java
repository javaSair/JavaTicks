package start;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dirs {
 
    private JavaTicks r;
    private StandardPanel s;

    public Dirs(JavaTicks r) {
        this.r = r;

    }

    private Path path;
    
       public void createDir(Boolean f,String s){
    
           
//            r.setDirZametki(Paths.get(s));
           
//           HashSet<Path> hesh = new HashSet<>();
//           hesh.add(Paths.get(s));

  
//                hesh.forEach((a)-> r.setDirZametki(a));
//             r.setDirZametki(Paths.get(s));
//                System.out.println(r.getListSize());
                
            r.geturlPanelBlockZametok().repaintSpisok();
             PanelBlockZametok  p = new PanelBlockZametok(r);
             p.repaintSpisok();
    
       }
/**
 * Получить количество существующих заметок
 */
    public void createDir() {

        // Проверка доступности директориии для заметок установленной по умолчанию методом  getNameDefaultDir();
        File fileDir = new File((path = Paths.get(r.getNameDefaultDir()).toAbsolutePath()).toString());
        try {
            if (fileDir.exists() == false) { // Если дериктории нету создать
                boolean flag = fileDir.mkdir();
//            System.out.println("Директория " + (flag ? "создана" : "не создана"));

                Files.createDirectories(Paths.get(r.getNameDefaultDir()).toAbsolutePath());

            } else { // Если директория существует прочитать  все поддиректории в динамический список

                r.clearListLabel(); // очистить список заметок
                DirectoryStream<Path> dir = Files.newDirectoryStream(path);

                for (Path x : dir) {
                    r.setDirZametki(x.toAbsolutePath());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Dirs.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
/**
 * @param dirName 
 * Создание директории и файла заметки для новой заметки
 */
    public void createDir(String dirName) {

        // Проверка доступности директориии создаваемой заметоки
        File fileDir = new File((path = Paths.get(r.getNameDefaultDir() + "\\" + dirName).toAbsolutePath()).toString());

        if (fileDir.exists() == false) { // Если директории не существует создать и переключиться на редактирование заметки
            boolean flag = fileDir.mkdir();

            try {
                // Создать файл заметки и директорию для ее файлов
                Files.createDirectories(fileDir.toPath()); // директория заметки
                Files.createDirectories(Paths.get(fileDir.getAbsolutePath() + "\\Files")); //директория для файлов
                Files.createFile(Paths.get(fileDir.getAbsolutePath() + "\\" + fileDir.getName() + ".txt")); //Файл заметки
                // Обновить список файлов заметки
//                r.frameTicks.clearListButtonAddFile(); // Очистить коллекчию
//                try {
//                    r.frameTicks.setVisibleList(false);
//                } catch (InterruptedException ex) {   Logger.getLogger(Dirs.class.getName()).log(Level.SEVERE, null, ex);
//                }
                r.getFrameTicks().setNameZametki(fileDir.getName());
                r.setContentPane(r.getFrameTicks());
                try {
                    r.getFrameTicks().setVisibleFileList(false); // Очистить список
                    r.getFrameTicks().clearListButtonAddFile(); // Очистить коллекчию
                    r.getFrameTicks().repaintIco();
                    r.getFrameTicks().setActivatedAdd(true);
                    r.getFrameTicks().setTextBlock("");
                } catch (InterruptedException ex) {   Logger.getLogger(Dirs.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(Dirs.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else { // Если директория существует уведомить об этом
            if(r.getPanel().equals(r.getFrameTicks())){
                
            }else{
                       s = r.getStandardPanel();
                       s.setVisibalMessage(s.fonPoppupMessag);
            }
     

        }
    }

}
 