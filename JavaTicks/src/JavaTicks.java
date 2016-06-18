package start;

//Решено - открытие заметки при клике на кнопке открыть заметку(при пустом поле .
//Решено - После деактивации (свертывания) списка файлов и после этого создании новой заметки деактивация не снималась 
//В процессе - Меню опции не реализованы
//
 
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class JavaTicks extends JFrame {
    
    private static Path path;
    FrameTicks frameTicks;
    StandardPanel urlStandardPanel;
    private JTextArea text;
    private Point point;
    private JLabel b;
    private static Path nameDir = Paths.get("C:\\DirZametki");
    private Dirs urlDirs;
    
    
    /*
    Получить ссылку на экземпляр класса Dirs
    */
    Dirs getDirs(){
        return urlDirs;
    }
    
    JavaTicks getJavaTicks(){
        return this;
    }
    
    StandardPanel getStandardPanel(){
        return urlStandardPanel;
    }
    
    
    String getNameDefaultDir(){
        return nameDir.toAbsolutePath().toString();
    }
    
   static ArrayList<Path> DirZametks = new ArrayList<>();
   
   
   ArrayList<Path> getListZametki(){
       return DirZametks;
   }
   
   
   
 
   //добавить существующие директории в ArrayList
  void setDirZametki(Path dirName){
      DirZametks.add(dirName);
  }
  
  // Получить количество существующих заметок
   int getListSize(){
       return DirZametks.size();
   }
   
    Container getPanel(){
        return this.getContentPane();
    }
    
 // Очистить 
   void clearListLabel(){
       DirZametks.clear();
   }
    
    

    public JavaTicks() {
        // Получить количество существующих заметок
       urlDirs = new Dirs(this);
       urlDirs.createDir();
//        AWTUtilities.setWindowOpacity(this, 0.5f);
        setUndecorated(true); // Убрать верхний бордер
        setLayout(new FlowLayout());
        setSize(495, 495);
        setLocationRelativeTo(null); // появление по середине окна
        setIconImage(new ImageIcon("src\\image\\ICO3.png").getImage());
        

        
        
        

//Реалзовать перемещение окна         
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                point = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(getLocation().x - (point.x - e.getX()), getLocation().y - (point.y - e.getY()));
            }

        });
// Инициализация панелей компонентов
        frameTicks = new FrameTicks(this);
        setContentPane(urlStandardPanel = new StandardPanel(this));
        

 
        setVisible(true);
        repaint();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new JavaTicks().setBackground(new Color(255, 255, 255, 30)));  
    
        
        
        
        
    }

}
