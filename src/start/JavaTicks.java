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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class JavaTicks extends JFrame {

//   private final String S = "../";  // для jar
    private String S = "";             // для IDE

    String getS() {
        return S;
    }

    private static Path path;
    private FrameTicks frameTicks;
    StandardPanel urlStandardPanel;
    private JTextArea text;
    private Point point;
    private JLabel b;
    private static Path nameDir = Paths.get("C:\\DirZametki");
    private Dirs urlDirs;
    private PanelBlockZametok urlPanelBlockZametok;
    private int reiting = 0;
    private String metka = "без_метки";

    // Конфиг файл
    private FileReader fileConf;

    // Получение уровня важности заметки
    int getReitingZametki() {
        return reiting;
    }

    // Установка уровня важности заметки
    void setReitingZametki(int i) {
        if (i <= 5) {
            reiting = i;
        } else {
            reiting = 0;
        }
    }
    
    String getMetki(){
        return metka;
    }
    
    
    String getConfZametki(String nameZametki){
        
           String path = getNameDefaultDir()+"\\"+nameZametki+"\\conf.txt";
        if(Files.isRegularFile(Paths.get(path).toAbsolutePath())){ // Проверка - доступности конфиг-файла
            String s ="";
//           System.out.println(path);
            try {
                FileReader readZ = new FileReader(path);
                try {
                    while(readZ.ready()){
                        s+=(char)readZ.read();
                    }
                   
                readZ.close();
                
                } catch (IOException ex) {
                    Logger.getLogger(JavaTicks.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JavaTicks.class.getName()).log(Level.SEVERE, null, ex);
            } 
        return s;
        
    }else{
             return "METKA=без_метки"+";\n"+"REITING=0;";
        }
       
    }
    
    
    
    // Обновление конфиг-файла заметки
    void UpdateConfigZametki(String nameZametki,String nameMetki,int xxx){
        String path = getNameDefaultDir()+"\\"+nameZametki;
        if(Files.isDirectory(Paths.get(path).toAbsolutePath())){
//            System.out.println("Найден "+path);
            path+="\\conf.txt";
            try {
                PrintWriter wr = new PrintWriter(path, "UTF-8");
                wr.write("METKA="+metka+";"
                        + ""+"REITING="+xxx+";"); // Обновить конфиг-файл заметки
                wr.flush();
                wr.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JavaTicks.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(JavaTicks.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Обновление конфиг-файла программы
    void setSystemConf(){
        
    }
    

    // Чтение конфиг-файла и инициализация переменных
    void readConf() {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                String name = "";
                try {
                    while (fileConf.ready()) { // Реализовать подгрузку начальных значений
                        name += (char) fileConf.read();
                    }
                    StringTokenizer s = new StringTokenizer(name, ";");
                    while (s.hasMoreElements()) {
                        System.out.println(s.nextElement());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(JavaTicks.class.getName()).log(Level.SEVERE, null, ex); // Создать всплывающее окно с выбором решения
                }
            }
        };
        new Thread(r).start();
    }

    // Реализовать пакет иконок для разных форматов добавляемых к заметки файлов 
    private final TreeMap<String, ImageIcon> IcoMap = new TreeMap<>();

    // Реализовать получение иконки типа
    ImageIcon GetFileTipICO(String tipFile) {
        if (IcoMap.containsKey(tipFile)) {
            return IcoMap.get(tipFile);
        }
        return IcoMap.get("none");
    }

    FrameTicks getFrameTicks() {
        return frameTicks;
    }


    /*
     Получить ссылку на экземпляр класса Dirs
     */
    Dirs getDirs() {
        return urlDirs;
    }

    JavaTicks getJavaTicks() {
        return this;
    }

    StandardPanel getStandardPanel() {
        return urlStandardPanel;
    }

    PanelBlockZametok geturlPanelBlockZametok() {
        return urlPanelBlockZametok;
    }

    String getNameDefaultDir() {
        return nameDir.toAbsolutePath().toString();
    }

     ArrayList<Path> DirZametks = new ArrayList<>();

    ArrayList<Path> getListZametki() {
        return DirZametks;
    }

    //добавить существующие директории в ArrayList
    void setDirZametki(Path dirName) {
        DirZametks.add(dirName);
    }

    // Получить количество существующих заметок
    int getListSize() {
        return DirZametks.size();
    }

    Container getPanel() {
        return this.getContentPane();
    }

    // Очистить 
    void clearListLabel() {
        DirZametks.clear();
    }
    
 void printList(){
     for(int i=0;i<DirZametks.size();i++)
         System.out.println(DirZametks.get(i));
 }
    
    

    public JavaTicks() {

        try {
            fileConf = new FileReader("conf.txt");
        } catch (FileNotFoundException ex) {
            try {
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("conf.txt"), "utf-8"));
                fileConf = new FileReader("conf.txt");
            } catch (IOException ex1) {
                Logger.getLogger(JavaTicks.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        readConf();
        IcoMap.put("txt", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/txtTip.png")));
        IcoMap.put("ai", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/aiTip.png")));
        IcoMap.put("class", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/classTip.png")));
        IcoMap.put("doc", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/docTip.png")));
        IcoMap.put("eps", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/epsTip.png")));
        IcoMap.put("gif", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/gifTip.png")));
        IcoMap.put("java", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/javaTip.png")));
        IcoMap.put("jpg", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/jpgTip.png")));
        IcoMap.put("none", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/noneTip.png")));
        IcoMap.put("pdf", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/pdfTip.png")));
        IcoMap.put("png", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/pngTip.png")));
        IcoMap.put("ppt", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/pptTip.png")));
        IcoMap.put("psd", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/psdTip.png")));
        IcoMap.put("rar", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/rarTip.png")));
        IcoMap.put("xml", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/xmlTip.png")));
        IcoMap.put("zip", new ImageIcon(JavaTicks.class.getResource(S + "/image/tipFile/zipTip.png")));

        // Получить количество существующих заметок
        urlDirs = new Dirs(this);
        urlDirs.createDir();
//        AWTUtilities.setWindowOpacity(this, 0.5f);
        setUndecorated(true); // Убрать верхний бордер
        setLayout(new FlowLayout());
        setSize(495, 495);
        setLocationRelativeTo(null); // появление по середине окна
        setIconImage(new ImageIcon(JavaTicks.class.getResource(S + "/image/ICO3.png")).getImage());

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
        urlPanelBlockZametok = new PanelBlockZametok(this);

        setVisible(true);
        repaint();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new JavaTicks().setBackground(new Color(255, 255, 255, 30)));

    }

}
