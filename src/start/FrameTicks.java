package start;
   
// Реализованно - потиповое добавление иконок файлов 
 // В процессе - Файл чтения в заметку (реализовать разные кодировки чтения)
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream; 
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton; 
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane; 
import javax.swing.JPanel;
import javax.swing.JTextArea; 
import javax.swing.JToggleButton;

public class FrameTicks extends JLayeredPane implements MouseListener,ActionListener{

    private static OutputStream file = null;
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
    private String textFile;  // Файл чтения в заметку (реализовать разные кодировки чтения)
    PanelTopMenu urlPanelTopMenu;
    private JToggleButton tUTF,tWin1251;
    private JPanel panelTextDecode;

    private final JLabel LABELFON;

    private static boolean flag = false; // Индикатор статуса свернуто\развернуто
    private JComboBox<String> box;
    private int reiting = 1;
    
    
     void setNullTextFile(){
        textFile = "";
    }
    
    
    String getDecodeTXT(){
        if(tUTF.isSelected()){
            return tUTF.getActionCommand();
        }else
            return tWin1251.getActionCommand();
    }
    
    
    
    void clearListButtonAddFile() {
        listButtonAddFile = new ArrayList<>();
    }

    void setVisibalPanelSave(boolean flag) {
        panelVidReiting.setVisible(flag);
        box.setVisible(flag);
        panelTextDecode.setVisible(flag);
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
        setVisibalPanelSave(false);
        if (listButtonAddFile.size() > 6) {
            bAddFile.setVisible(false);
        } else {

            // Скопировать файл к себе в заметку
            if (Files.exists(Paths.get(path)) == false) {

            } else {

                urlNewAnimatedIconFile.copyFile(path);
                b = new JButton(urlJavaTicks.GetFileTipICO(path.substring(path.lastIndexOf(".")+1))); //Иконка  Расширение файла
                b.setToolTipText(path.substring(path.lastIndexOf("\\") + 1));  // подсказка
                listButtonAddFile.add(b);
                b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                b.addActionListener((e) -> {
                    Runtime r = Runtime.getRuntime();
                    try {
                        Process p = r.exec("explorer.exe " + path);
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
        setVisibalPanelSave(false);
        if (listButtonAddFile.size() > 6) {
            bAddFile.setVisible(false);
        } else {
            b = new JButton(urlJavaTicks.GetFileTipICO(path.substring(path.lastIndexOf(".") + 1))); //Иконка  Расширение файла
            b.setToolTipText(path.substring(path.lastIndexOf("\\") + 1));  // подсказка
            listButtonAddFile.add(b);
            b.setBorder(null);
            b.addActionListener((e) -> {
                Runtime r = Runtime.getRuntime();
                try {
                    Process p = r.exec("explorer.exe " + path);
                    p.destroy();
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
                if (!listButtonAddFile.isEmpty()) {
                    bAddFile.setBounds(listButtonAddFile.get(listButtonAddFile.size() - 1).getBounds().x + 30, 70, 38, 23);
                    bAddFile.setVisible(true);
                    LABELFON.repaint();
                } else {
                    bAddFile.setBounds(getWidthAddFile() + 40, 70, 38, 23);
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

    void saveText() throws IOException {
        Runnable r = new Runnable() {

            @Override
            public void run() {

                try {
                    // Сохранение в UTF-8
PrintWriter wr = new PrintWriter(Paths.get(urlJavaTicks.getNameDefaultDir() + "\\" + getNameZametki() + "\\" + getNameZametki() + ".txt").toFile());
                    char[] b = textNew.getText().toCharArray();
                    wr.println(b);
                   

                     wr.flush();
                    wr.close();
                    return;

                } catch (IOException ex) {
                    Logger.getLogger(FrameTicks.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        };
        new Thread(r).start();

    }
    
     private  String[] alternativeMetks = {"работа","заказ","отдых","учеба","мероприятия"};
        private ArrayList<String> listMetok = new ArrayList<>();
     private DefaultComboBoxModel<String> boxModel ;
     
    void updateConfSyst(){
        try{
            PrintWriter wr = new PrintWriter(new OutputStreamWriter(new FileOutputStream("conf.txt"), "utf-8"));

//               for(int i=0;i<alternativeMetks.length;i++){
//                   wr.write("METKA="+alternativeMetks[i]+";");
//               }
               for(int x =0;x<listMetok.size();x++)
                   wr.write("METKA="+listMetok.get(x)+";");
               wr.close();
               
        
           
           
             } catch (IOException ex) {
            try {
              PrintWriter f = new PrintWriter(new OutputStreamWriter(new FileOutputStream("conf.txt"), "utf-8"));         
            f.close();
            } catch (IOException ex1) {
                Logger.getLogger(FrameTicks.class.getName()).log(Level.SEVERE, null, ex1);
            }
                   
                    boxModel = new DefaultComboBoxModel<>(alternativeMetks);
               }
    }
    
    
        void getMassivMetok(){
             ArrayList<String> s = new ArrayList<>(listMetok);
             for(int i=0;i<alternativeMetks.length;i++){
                 s.add(alternativeMetks[i]);
             }
              for(int i=0;i<s.size();i++)
                  urlJavaTicks.geturlPanelBlockZametok().setMetki(s.get(i));
                  urlJavaTicks.geturlPanelBlockZametok().setInitializedMetki();
    }   
  

    void initBoxModel() {
        
        
        
        boxModel = new DefaultComboBoxModel<>();
   
           if(Files.isRegularFile(Paths.get("conf.txt"))){
            Scanner scan;
               try {
                   scan = new Scanner(Paths.get("conf.txt"),"UTF-8");
             
            String str="";
            
            while(scan.hasNext()){
                str+=scan.next();
            }
            System.out.println(str);
            StringTokenizer token = new StringTokenizer(str, ";");
           while(token.hasMoreElements()){
               String s = token.nextToken();
               if(s.substring(0, s.indexOf("=")).equalsIgnoreCase("metka")){
                   String metka = s.substring(s.indexOf("=")+1);
                 listMetok.add(metka);
                   System.out.println(metka);
                   boxModel.addElement(metka);
                   box = new JComboBox<>(boxModel);
               }
           }
            if(boxModel.getSize()==0){
                 for(int i=0;i<alternativeMetks.length;i++)
                   listMetok.add(alternativeMetks[i]);
               boxModel = new DefaultComboBoxModel<>(alternativeMetks);
           }
                  
           
             } catch (IOException ex) {
                try {
                   PrintWriter wr = new PrintWriter(new OutputStreamWriter(new FileOutputStream("conf.txt"), "utf-8"));
                   wr.close();
                } catch (IOException ex1) {
                    Logger.getLogger(FrameTicks.class.getName()).log(Level.SEVERE, null, ex1);
                }
                   String[] alternativeMetks = {"работа","заказ","отдых","учеба","мероприятия"};
                    boxModel = new DefaultComboBoxModel<>(alternativeMetks);
                   
               }
    }else{// Если файла не существует то получить значения по умолчанию
               for(int i=0;i<alternativeMetks.length;i++)
                   listMetok.add(alternativeMetks[i]);
               
           }
   
           
           
          
    
   }
    private ImageIcon icoVidFalse = new ImageIcon(JavaTicks.class.getResource("/image/StatusMetki/IcoFalse.png")); // Иконка в состоянии - Не выбрано
    private ImageIcon icoVidTrue = new ImageIcon(JavaTicks.class.getResource("/image/StatusMetki/IcoTrue.png"));  // Иконка в состоянии - Выбрано   
    private JPanel panelVidReiting;
    
        JButton[] bReiting = new JButton[5];

    private void initbReiting() {
        int x = 300;
        for (int i = 0; i < bReiting.length; i++) {
            bReiting[i] = new JButton(icoVidFalse);
            bReiting[i].setBorder(null);
            bReiting[i].setContentAreaFilled(false);
            bReiting[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            bReiting[i].setActionCommand(String.valueOf(i + 1));
            bReiting[i].addActionListener(this);
//            bReiting[i].setBounds(x, 90, 20, 20);
        }
        setVisibalMetkiReitinga(reiting);
    }
    
    void setVisibalMetkiReitinga(int x) {
        if (x < 6) {
            for (int i = 0; i < bReiting.length; i++) {
                if (i < x) {
                    bReiting[i].setIcon(icoVidTrue);
                } else {
                    bReiting[i].setIcon(icoVidFalse);
                }
            }
        }

    }
    
    
   void setSelektBox(String metka){
       box.setSelectedItem(metka);
       if(!box.getSelectedItem().toString().equals(metka)){
           listMetok.add(metka);
           boxModel.addElement(metka);
       box.setSelectedItem(metka);
     updateConfSyst();
       }
   } 
    
   void setReiting(int newReiting){
       reiting = newReiting;
       setVisibalMetkiReitinga(newReiting);
   }
    
// 
    public FrameTicks(JavaTicks r) {
   
        
            // Список меток
            initBoxModel();
        box  = new JComboBox<>(boxModel);
        box.setEditable(true);
        box.setBounds(190, 80, 137, 25);
        box.addActionListener((e)->{
            String s = box.getSelectedItem().toString();
            if(!listMetok.contains(s)){
                 listMetok.add(s);
            boxModel.addElement(s);
             updateConfSyst();
            }
            
        });
        box.addItemListener((e)->{urlJavaTicks.UpdateConfigZametki(getNameZametki(), box.getSelectedItem().toString(), reiting);});
        add(box);
        
        panelTextDecode = new JPanel();
        panelTextDecode.setBounds(300, 76, 130, 64);
        
        panelTextDecode.setBorder(null);
        panelTextDecode.setOpaque(false);
        
                // Реализация чтения текста в разных кодировках
        tWin1251 = new JToggleButton(new ImageIcon(JavaTicks.class.getResource("/image/codirovka/win1251False.png")));
        tWin1251.setSelectedIcon(new ImageIcon(JavaTicks.class.getResource("/image/codirovka/win1251True.png")));
        tWin1251.setBounds(300, 80, 64, 21);
        tWin1251.setActionCommand("Windows-1251");
        tWin1251.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        tWin1251.setToolTipText("Отобразить заметку в кодеровке Windows1251");
        tWin1251.addActionListener((e)->{
            tUTF.setSelected(false);
            newTextBlock(textFile);
        
        });
        panelTextDecode.add(tWin1251);
//        tUTF.addActionListener(nul);
        
        
        tUTF = new JToggleButton(new ImageIcon(JavaTicks.class.getResource("/image/codirovka/utfFalse.png")));
        tUTF.setSelectedIcon(new ImageIcon(JavaTicks.class.getResource("/image/codirovka/utfTrue.png")));
        tUTF.setBounds(300, 115, 64, 21);
        tUTF.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        tUTF.setActionCommand("UTF-8");
        tUTF.setToolTipText("Отобразить заметку в кодеровке UTF-8");
        tUTF.setSelected(true);
        tUTF.addActionListener((e)->{
            tWin1251.setSelected(false);
            newTextBlock(textFile);
        });
        panelTextDecode.add(tUTF);
        
      

        
        
        
        
        // Кнопка сохранения 
        bSave = new JButton(new ImageIcon(JavaTicks.class.getResource("/image/seve.png")));
        bSave.setBounds(400, 76, 64, 64);
        bSave.setBorder(null);
        bSave.setContentAreaFilled(false);
        bSave.setToolTipText("сохранить");
        bSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bSave.addActionListener((e) -> {
            try {
                saveText();
            } catch (IOException ex) {
                Logger.getLogger(FrameTicks.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//        bSave.setVisible(true);
     add(bSave, Integer.valueOf(6));

       add(panelTextDecode,Integer.valueOf(6));   
        
        
        
        
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
        bAddFileText.addActionListener((e)->{
            fileDialog = new FileDialog(urlJavaTicks);
            fileDialog.setPreferredSize(new Dimension(400, 400));
            fileDialog.setVisible(true);
         textFile = fileDialog.getDirectory()+"\\"+fileDialog.getFile();
         
            newTextBlock(textFile);
            
        });
        
            
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
        textNew.setLineWrap(true);
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
//         scrolPane.setBorder(null);
//        scrolPane.getViewport().setOpaque(false);
//        scrolPane.getViewport().setBackground(new Color(255, 255, 255, 30));
//        scrolPane.setOpaque(false);
//        add(scrolPane, Integer.valueOf(3));
// Обложка фона      
        LABELFON = new JLabel(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/StartFonImage.png")));
        LABELFON.setBounds(0, 0, 495, 495);
        add(LABELFON, Integer.valueOf(0));

               panelVidReiting = new JPanel();
        panelVidReiting.setBounds(190, 110, 137, 30); //370, 130, 100, 20
        initbReiting();
        setVisibalMetkiReitinga(reiting);
        panelVidReiting.add(bReiting[0]);
        panelVidReiting.add(bReiting[1]);
        panelVidReiting.add(bReiting[2]);
        panelVidReiting.add(bReiting[3]);
        panelVidReiting.add(bReiting[4]);
//        panelVidReiting.setVisible(false);
//        add(panelVidReiting,Integer.valueOf(9));
        
        
        
//Реализовать перемещение окна
        setVisible(true);
    }

    void getbAddFile2() {
        bAddFile.setBounds(getWidthAddFile() + 40, 70, 38, 23);
    }
    

    // Анимация списка файлов (Свернуть/Развернуть)
   private Thread tAnimationScrolFile = null;
    void animationScrolFile() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
       if(listButtonAddFile.isEmpty()){
           setVisibalPanelSave(true); 
               bAddFile.setEnabled(true);
               bAddFile.setVisible(true);
       }else{
                           if (bAddFile.isEnabled()) {//если  не свернут свернуть
                    int i = listButtonAddFile.size();

                  
             metka: while (true) {
                        try {
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
                                }
                            }

                        } catch (InterruptedException ex) {
                            System.err.println("Exception sleep animation");
                        }
                        setVisibalPanelSave(true);
                    }
                    bAddFile.setEnabled(false);
                    bAddFile.setVisible(false);
                } else { // В противном случае развернуть
                    setVisibalPanelSave(false);
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
                                }
                            }

                        } catch (InterruptedException ex) {
                            System.err.println("Exception sleep animation");
                        }
                    }
                    bAddFile.setEnabled(true);

                }
       }
     

           tAnimationScrolFile.interrupt(); // Прервать поток анимации
            tAnimationScrolFile = null; // обнулить его значение
            }
        };
      
        if(tAnimationScrolFile == null){
          tAnimationScrolFile = new Thread(r, "AnimatedSlider"); // Создать поток реализующий анимацию
          tAnimationScrolFile.start();  // Запустить анимацию
      }else{
           // Если поток еще выполняется нечего не делать
      }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getSource().equals(labelJavaICO)) {
           
            animationScrolFile();
        } else if (e.getSource().equals(textNew)) {
            if (listButtonAddFile.size() > 0) {
                if (bAddFile.isEnabled()) { // Проверить что шторка раздвинута
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
    
    
    
    
    
    private void newTextBlock(String ticks){
//        textNew
     
        if(String.valueOf(ticks).equals("null")) // Проверить что файл был выбран в противном случае передать ссылку на заметку
            ticks = urlJavaTicks.getNameDefaultDir()+"\\"+getNameZametki()+"\\"+getNameZametki()+".txt";
        if(Files.exists(Paths.get(ticks).toAbsolutePath())){
          String s = Paths.get(ticks).toAbsolutePath().toString();
            if(s.substring(s.length()-3).equals("txt") | s.substring(s.length()-3).equals("rtf") | s.substring(s.length()-3).equals("doc")){
               try (FileInputStream fileRead = new FileInputStream(Paths.get(ticks).toAbsolutePath().toString())) {

                        String codingRead = "Windows-1251";
//                        String codingRead = "UTF-8";

                        Reader r = new BufferedReader(new InputStreamReader(fileRead, getDecodeTXT()));

                        String contentTextBlock = "";
                        int i = 0;

                        while ((i = r.read()) != -1) {
                            contentTextBlock += (char) i;
                        }

                        urlJavaTicks.getFrameTicks().setTextBlock(contentTextBlock);
                        urlJavaTicks.setContentPane(urlJavaTicks.getFrameTicks());
                    } catch (IOException ex) {
                        Logger.getLogger(LoadingZametki.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }else{ // В противном случае прочитать заметку
 
                if(textNew.getText() != null){
                                   try (FileInputStream fileRead2 = new FileInputStream(Paths.get(urlJavaTicks.getNameDefaultDir()+"\\"+getNameZametki()+"\\"+getNameZametki()+".txt").toAbsolutePath().toString())) {

                        Reader r1 = new BufferedReader(new InputStreamReader(fileRead2, getDecodeTXT()));

                        String contentTextBlock = "";
                        int i = 0;

                        while ((i = r1.read()) != -1) {
                            contentTextBlock += (char) i;
                        }

                        urlJavaTicks.getFrameTicks().setTextBlock(contentTextBlock);
                        urlJavaTicks.setContentPane(urlJavaTicks.getFrameTicks());
                    } catch (IOException ex) {
                        Logger.getLogger(LoadingZametki.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
                
            }
         }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        switch(e.getActionCommand()){
            
            case "1":{
                   setVisibalMetkiReitinga(1);
                     reiting = 1;
                     urlJavaTicks.UpdateConfigZametki(getNameZametki(), box.getSelectedItem().toString(), reiting);
            }break;
            case "2":{
                   setVisibalMetkiReitinga(2);
                   reiting = 2;
                   urlJavaTicks.UpdateConfigZametki(getNameZametki(), box.getSelectedItem().toString(), reiting);
            }break;
            case "3":{
                       setVisibalMetkiReitinga(3);
                   reiting = 3;
                   urlJavaTicks.UpdateConfigZametki(getNameZametki(), box.getSelectedItem().toString(), reiting);
            }break;
            case "4":{
                       setVisibalMetkiReitinga(4);
                   reiting = 4;
                   urlJavaTicks.UpdateConfigZametki(getNameZametki(), box.getSelectedItem().toString(), reiting);
            }break;
            case "5":{
                       setVisibalMetkiReitinga(5);
                   reiting = 5;
                   urlJavaTicks.UpdateConfigZametki(getNameZametki(), box.getSelectedItem().toString(), reiting);
            }break;
            default : break;
        }
      
        
    }
    
    
    

}
