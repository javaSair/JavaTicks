package start;

// В процессе     
// Реализовать потиповое добавление иконок файлов
import com.sun.java.swing.plaf.windows.WindowsScrollPaneUI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

public class FrameTicks extends JLayeredPane implements MouseListener {

    private static  OutputStream file = null;
    private JLabel StrokaN;
    private JLabel nameZametki = new JLabel("Тестовый");
    private NewAnimatedIconFile urlNewAnimatedIconFile;
    private JButton labelJavaICO;
    private JavaTicks urlJavaTicks;
    private static ArrayList<JButton> listButtonAddFile = new ArrayList<>();
    private FileDialog fileDialog; 
    private static JButton b;
    private JButton bAddFile;
    private JButton bAddFileText;
    private JButton bAddFileText2;
    private JButton bAddFileText3;
    private JButton bClose;
    private JButton bSave;
    private JTextArea text;
    private JTextArea textNew;

    PanelTopMenu urlPanelTopMenu;

    private final JLabel LABELFON;

    private static boolean flag = false; // Индикатор статуса свернуто\развернуто

    void clearListButtonAddFile() {
        listButtonAddFile = new ArrayList<>();
    }

    
    void setVisibalButtonSave(boolean flag){
        bSave.setVisible(flag);
    }
    
    
    // Сделать добавление файла активным|неактивным
    void setActivatedAdd(boolean b) {
        bAddFile.setEnabled(b);
    }

    // Сделать добавление файла активным|неактивным
    void setVisibaldAdd(boolean b) {
        bAddFile.setVisible(b);
    }

    void setTextBlock(String strong) {
        textNew.setText(strong);
    }

    /**
     *
     * @param newName Установить новое имя заметки
     */
    void setNameZametki(String newName) {
        nameZametki.setText(newName);
    }

    String getNameZametki() {
        return nameZametki.getText();
    }

    // Получить имя активной заметки
    String getActiveNameZametki() {
        return nameZametki.getName();
    }

    /**
     *
     * Метод возвращающий обект JavaTicks
     */
    JavaTicks getJavaTicks() {
        return urlJavaTicks;
    }

//Динамическое добавление файлов в существующую заметку   
    void setNewImageAdFile(String path) {
        bSave.setVisible(false);
        if (listButtonAddFile.size() > 6) {
            bAddFile.setVisible(false);
        } else {

            // Скопировать файл к себе в заметку
            if (Files.exists(Paths.get(path)) == false) {

            } else {

                urlNewAnimatedIconFile.copyFile(path);
                b = new JButton(urlJavaTicks.GetFileTipICO(path.substring(path.length() - 3))); //Иконка  Расширение файла
                b.setToolTipText(path.substring(path.lastIndexOf("\\") + 1));  // подсказка
                listButtonAddFile.add(b);
                b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                b.addActionListener((e)->{
                    Runtime r = Runtime.getRuntime();
                    try {
                         Process p = r.exec("explorer.exe "+path);
                    } catch (IOException ex) {
                        Logger.getLogger(FrameTicks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                b.setBorder(null);
                b.setContentAreaFilled(false);
                b.setBounds(getWidthAddFile() + (listButtonAddFile.size() * 5), 75, 51, 66);
                add(b, Integer.valueOf(2));
                repaint();

                repaintIco();
            }
        }

    }

    // Динамическое добавление существующих файлов в существующую заметку
    void setImageAdFile(String path) {
        bSave.setVisible(false);
        if (listButtonAddFile.size() > 6) {
            bAddFile.setVisible(false);
        } else {
            b = new JButton(urlJavaTicks.GetFileTipICO(path.substring(path.length() - 3))); //Иконка  Расширение файла
            b.setToolTipText(path.substring(path.lastIndexOf("\\") + 1));  // подсказка
            listButtonAddFile.add(b);
            b.setBorder(null);
             b.addActionListener((e)->{
                    Runtime r = Runtime.getRuntime();
                    try {
                        Process p = r.exec("explorer.exe "+path);
                    } catch (IOException ex) {
                        Logger.getLogger(FrameTicks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
              b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setContentAreaFilled(false);
            b.setBounds(getWidthAddFile() + 20, 75, 55, 66); 
            add(b, Integer.valueOf(2));
            repaint();

            repaintIco();
        }

    }

    void setVisibleFileList(boolean flag) throws InterruptedException {
        for (int i = 0; i < listButtonAddFile.size(); i++) {
            listButtonAddFile.get((listButtonAddFile.size() - 1) - i).setVisible(flag);
        }
    }

    // Список добавленных в заметку файлов
    int getLenghtList() {
        return listButtonAddFile.size();
    }

    void repaintIco() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
//              if (flag == true) {
//                    return;
//                }
                bAddFile.setVisible(false);
                if(!listButtonAddFile.isEmpty()){
                    bAddFile.setBounds(listButtonAddFile.get(listButtonAddFile.size()-1).getBounds().x+30, 70, 38, 23);
                bAddFile.setVisible(true);
                LABELFON.repaint();
                }else{
                bAddFile.setBounds(getWidthAddFile()+40, 70, 38, 23);
                bAddFile.setVisible(true);
                LABELFON.repaint();
                }
                
            }
        };
        new Thread(r).start();

    }

    int getWidthAddFile() {
        // 47 это начальная точка!
        // 66 ширина иконки  (-4 отступ)
        return ((listButtonAddFile.size() - 1) * 55) + 55;
    }

    void saveText() throws IOException{
        Runnable r = new Runnable() {

            @Override
            public void run() {
               
                  try {
                      System.out.println("Запись в файл");
                      
                      file = Files.newOutputStream(Paths.get(urlJavaTicks.getNameDefaultDir()+"\\"+getNameZametki()+"\\"+getNameZametki()+".txt"), StandardOpenOption.CREATE,StandardOpenOption.WRITE);
//             FileWriter wr = new FileWriter("");
                      
                      ByteArrayOutputStream outByte = new ByteArrayOutputStream();
                      
                      
                      System.out.println(getNameZametki()+".txt");
//                       byte[] b = textNew.getText().getBytes();
                       byte[] b = textNew.getText().getBytes();
                       outByte.write(b);
                       
                       outByte.writeTo(file);
                       file.close();
                return;
                
               
            } catch (IOException ex) {
                Logger.getLogger(FrameTicks.class.getName()).log(Level.SEVERE, null, ex);
            }
                  
      
            }
            
             
        }; new Thread(r).start();
        
        
    }
    
    
// 
    public FrameTicks(JavaTicks r) {

        // Кнопка сохранения 
        bSave = new JButton(new ImageIcon(JavaTicks.class.getResource("/image/seve.png")));
        bSave.setBounds(400, 76, 64, 64);
        bSave.setBorder(null);
        bSave.setContentAreaFilled(false);
        bSave.setToolTipText("сохранить");
        bSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bSave.addActionListener((e)->{ try {
            saveText();
            } catch (IOException ex) {
                Logger.getLogger(FrameTicks.class.getName()).log(Level.SEVERE, null, ex);
            }
 });
        bSave.setVisible(true);
       add(bSave,Integer.valueOf(6));

        urlNewAnimatedIconFile = new NewAnimatedIconFile(this);
        urlJavaTicks = r;
        setSize(495, 495);
        setLayout(null);
        setBorder(null);
        setBounds(0, 0, 495, 495);

// верхняя панель меню
        urlPanelTopMenu = new PanelTopMenu(getJavaTicks());
        urlPanelTopMenu.setBounds(0, 0, 477, 77);
        urlPanelTopMenu.setBorder(null);
        urlPanelTopMenu.setOpaque(false);
        add(urlPanelTopMenu, Integer.valueOf(9));

//Метка названия заметки
        // Создать фоновую  подложку
        JLabel labelPodFon = new JLabel();
        labelPodFon.setBounds(20, 385, 400, 20);
        labelPodFon.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 10, true));
//        add(labelPodFon, Integer.valueOf(3));
        nameZametki.setBounds(300, 48, 170, 20);
        nameZametki.setFont(new Font("Arial", Font.PLAIN, 15));
        nameZametki.setForeground(Color.white);
        nameZametki.setBorder(BorderFactory.createEtchedBorder());
        add(nameZametki, Integer.valueOf(8));//-------------------------------------------------------------------------

// Метка-кнопка свертывания развертывания списка файлов (в левом верхнем углу)
        labelJavaICO = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/FileTipICO.png")));
        labelJavaICO.setBorder(null);
        labelJavaICO.setContentAreaFilled(false);
        labelJavaICO.setBounds(0, 75, 56, 64);
        labelJavaICO.addMouseListener(this);
        labelJavaICO.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelJavaICO.addMouseListener(this);
        add(labelJavaICO, Integer.valueOf(8));

// Кнопка добавления файла
        bAddFile = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/newButton.png")));
        bAddFile.setToolTipText("Добавить файл");
        bAddFile.setBounds(getWidthAddFile() + 90, 70, 38, 23);
        bAddFile.setBorder(null);
        bAddFile.setContentAreaFilled(false);
        bAddFile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bAddFile.addActionListener((e) -> {
//        System.out.println("q");

            fileDialog = new FileDialog(urlJavaTicks);
            fileDialog.setPreferredSize(new Dimension(400, 400));
            fileDialog.setVisible(true);

            String s = fileDialog.getDirectory() + fileDialog.getFile();
            if (!s.equals("nullnull")) {
                if (flag == true) {
                    return;
                }
                setNewImageAdFile(s);
            }
//            System.out.println(s);

        });
        add(bAddFile, Integer.valueOf(8));

// Кнопки добавления из файла 
        bAddFileText = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/ReadFiles.png")));
        bAddFileText.setToolTipText("Прочитать из файла");
        bAddFileText.setBounds(-80, 305, 100, 25);
        bAddFileText.setBorder(null);
        bAddFileText.setContentAreaFilled(false);
        bAddFileText.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bAddFileText.addMouseListener(this);
        add(bAddFileText, Integer.valueOf(2));

        // Кнопка добавления из файла 
        bAddFileText2 = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/ReadFiles.png")));
        bAddFileText2.setBounds(-80, 255, 100, 25);
        bAddFileText2.setBorder(null);
        bAddFileText2.setContentAreaFilled(false);
        bAddFileText2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bAddFileText2.addMouseListener(this);
        add(bAddFileText2, Integer.valueOf(8));

        // Кнопка добавления из файла 
        bAddFileText3 = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/ReadFiles.png")));
        bAddFileText3.setBounds(-80, 280, 100, 25);
        bAddFileText3.setBorder(null);
        bAddFileText3.setContentAreaFilled(false);
        bAddFileText3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bAddFileText3.addMouseListener(this);
        add(bAddFileText3, Integer.valueOf(8));

// Альтернативный текстобой блок
        textNew = new JTextArea(20, 10);
        textNew.setBounds(20, 150, 450, 330);
//        textNew.setBackground(Color.red);
        textNew.setForeground(Color.LIGHT_GRAY);
        textNew.setCaretColor(Color.green);
        textNew.setBorder(BorderFactory.createEtchedBorder());
        textNew.addMouseListener(this);
        textNew.setOpaque(false);

        add(textNew, Integer.valueOf(2));

// Текстовой блок              
        text = new JTextArea(5, 5);
//        text.setBorder(null);
//        text.setBounds(20, 150, 400, 200);
//        text.setOpaque(false);
//        text.setBackground(new Color(255, 255, 255, 0));
        text.setForeground(Color.red);

// Скрол
        JScrollPane scrolPane = new JScrollPane(text);

        JScrollPane scrollPane = new JScrollPane();

        JViewport viewport = new JViewport();

        viewport.setView(new JPanel());

        viewport.setOpaque(false);

        scrollPane.setViewport(viewport);

        scrollPane.getViewport().setOpaque(false);

        scrollPane.setOpaque(false);

//         scrolPane.setBorder(null);
        scrolPane.setBounds(20, 150, 450, 330);
//        scrolPane.getViewport().setOpaque(false);
//        scrolPane.getViewport().setBackground(new Color(255, 255, 255, 30));
//        scrolPane.setOpaque(false);
//        add(scrolPane, Integer.valueOf(3));

// Обложка фона      
        LABELFON = new JLabel(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/StartFonImage.png")));
        LABELFON.setBounds(0, 0, 495, 495);
        add(LABELFON, Integer.valueOf(0));

//Реализовать перемещение окна
        setVisible(true);
    }

    void getbAddFile2() {
        bAddFile.setBounds(getWidthAddFile() + 40, 70, 38, 23);
    }

    // Анимация списка файлов (Свернуть/Развернуть)
    void animationScrolFile() {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                if (bAddFile.isEnabled()) {//если  не свернут свернуть
                    int i = listButtonAddFile.size();

                    metka:
                    while (true) {
                        try {
//                            System.out.println("q");
                            for (int x = 0; x < listButtonAddFile.size(); x++) {
                                i--;
                                for (int y = 0; y < x * 50 + (50); y += 10) {
                                    Thread.sleep(10);
                                    if (i < 0) {
                                        break metka;
                                    } else if (x == listButtonAddFile.size() - 1) {
                                        bAddFile.setBounds((getWidthAddFile() + 40 - y) - 15, 70, 38, 23);
                                    }
                                    listButtonAddFile.get(x).setBounds(x * 55 + 55 - y, 75, 55, 66);
                                    listButtonAddFile.get(x).repaint();
//                                        System.out.println(x);
                                }
                            }

                        } catch (InterruptedException ex) {
                            System.err.println("Exception sleep animation");
                        }
                        bSave.setVisible(true);
                    }
                    bAddFile.setEnabled(false);
                    bAddFile.setVisible(false);
                } else { // В противном случае развернуть
                    bSave.setVisible(false);
                    int i = listButtonAddFile.size();
                    if (i < 7) {
                        bAddFile.setVisible(true);
                    }
                    metka2:
                    while (true) {
                        try {
                            for (int x = 0; x < listButtonAddFile.size(); x++) {
                                i--;
                                for (int y = 0; y < ((listButtonAddFile.size() - 1) - x) * 60 + 65; y += 10) {
                                    Thread.sleep(10);
                                    if (i < 0) {
                                        break metka2;
                                    } else if (x == 0) {
                                        bAddFile.setBounds((y + 60) - 30, 70, 38, 23);
                                    }
                                    listButtonAddFile.get((listButtonAddFile.size() - 1) - x).setBounds(y, 75, 55, 66);
                                    listButtonAddFile.get((listButtonAddFile.size() - 1) - x).repaint();
//                                    System.out.println(listButtonAddFile.size() - 1 - x);
                                }
                            }

                        } catch (InterruptedException ex) {
                            System.err.println("Exception sleep animation");
                        }
                    }
                    bAddFile.setEnabled(true);

                }
            }
        };
        new Thread(r).start();
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getSource().equals(labelJavaICO)) {
            animationScrolFile();
        } else if (e.getSource().equals(textNew)) {
            if (listButtonAddFile.size()>0) {
            if (bAddFile.isEnabled()) {
                animationScrolFile();
            }
        }
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        if (e.getSource().equals(bAddFileText)) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while ((i += 10) != 90) {
                        try {
                            Thread.sleep(10);
//                            System.out.println("q");
                            bAddFileText.setBounds((-80) + i, 305, 100, 25);
                            bAddFileText.revalidate();

                        } catch (InterruptedException ex) {
                            System.err.println("Exception sleep animation");
                        }
                    }
                }
            };
            new Thread(r).start();
        }
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        if (e.getSource().equals(bAddFileText)) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while ((i += 3) < 80) {
                        try {
                            Thread.sleep(10);
//                            System.out.println("q");
                            bAddFileText.setBounds((0) - i, 305, 100, 25);
                            bAddFileText.revalidate();

                        } catch (InterruptedException ex) {
                            System.err.println("Exception sleep animation");
                        }
                    }
                }
            };
            new Thread(r).start();
        }
    }

}
