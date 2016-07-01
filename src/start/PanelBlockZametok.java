package start;

import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


 

/**
 *
 * @author Pavel Mayatnikov 
 * Этот класс реализует панель редактирования заметок
 */
public class PanelBlockZametok extends JPanel implements ActionListener,MouseListener {

    private JavaTicks urlJavaTicks;
    private PanelTopMenu urlPanelTopMenu;
    private JLabel LABELFON;
//    private PanelBlockZametok urlPanelBlockZametok;
//    private PanelBlockZametok urlPanelBlockZametok2;
    private JScrollPane scrol;
    private JPanel panelZametki;

    private ImageIcon icoVidFalse = new ImageIcon(JavaTicks.class.getResource("/image/StatusMetki/IcoFalse.png")); // Иконка в состоянии - Не выбрано
    private ImageIcon icoVidTrue = new ImageIcon(JavaTicks.class.getResource("/image/StatusMetki/IcoTrue.png"));  // Иконка в состоянии - Выбрано   
    
    // Реализация фильтрованного отображения
    private static ArrayList<PanelCreateZametks> listPanel2; // Список удовлетворяющих фильтру по рейтингу
    private JPanel panelVidReiting; // панель отображения по рейтингу
    // Далее кнопки для рейтинга

    private static int reiting = 1; // Инициализация с указанным рейтингом
    private static String metka = "отобразить по метке";
    private static JTextField textFilterMetki;
    
//    private final Choice c = new Choice();
   private JButton bVidAll; 
          private final  String[] METKI = {"работа","заказ","отдых","учеба","мероприятия"};
       private String[] massMetok ;
    JButton[] bReiting = new JButton[5];
       private ArrayList<String> listMetok = new ArrayList<>();
    
    
        void inicMetok() {
           
//           strList.removeAll();
//System.out.println("DS");
   
           if(Files.isRegularFile(Paths.get("conf.txt"))){
                popMenu.removeAll();
//               System.out.println("Найдено");
            Scanner scan;
               try {
                   scan = new Scanner(Paths.get("conf.txt"),"UTF-8");
             
            String str="";
            
            while(scan.hasNext()){
                str+=scan.next();
//                System.out.println("Получение "+str);
            }
      
            StringTokenizer token = new StringTokenizer(str, ";");
            massMetok = new String[token.countTokens()];
            int in = 0;
           while(token.hasMoreElements()){
               String s = token.nextToken();
               if(s.substring(0, s.indexOf("=")).equalsIgnoreCase("metka")){
                   String metka = s.substring(s.indexOf("=")+1);
                   JMenuItem jm = new JMenuItem(metka);
                   jm.addActionListener((e)->{vidPoMetki(jm.getText());});
                   popMenu.add(jm);
//                   massMetok[in++] = metka;
                   
               }
           }  
//           strList = new JList<>(massMetok);
//           strList.revalidate();
            
           
             } catch (IOException ex) { System.err.println(ex); }
    }else{// Если файла не существует то получить значения по умолчанию
                
               for(int i=0;i<METKI.length;i++){
//                   listMetok.add(alternativeMetks[i]);
                     JMenuItem jm = new JMenuItem(METKI[i]);
                   jm.addActionListener((e)->{vidPoMetki(jm.getText());});
                   popMenu.add(jm);
               }   
           }
   
           
           
          
    
   }
    
    
    
    
    
    
    
    
    
    
    
    
    

    private void initbReiting() {
        int x = 300;
        for (int i = 0; i < bReiting.length; i++) {
            bReiting[i] = new JButton(icoVidFalse);
            bReiting[i].setBorder(null);
            bReiting[i].setContentAreaFilled(false);
            bReiting[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            bReiting[i].setActionCommand(String.valueOf(i + 1));
            bReiting[i].addActionListener(this);
            bReiting[i].setBounds(x, 90, 20, 20);
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

    private JPanel panelVidAll; // панель отображения всего (по умолчанию)

    private JPanel panelVidMetka; // панель отображения по метке
    // далее список меток
   private JPopupMenu popMenu;
   private JMenu podMenu;
  private JLabel labelPopMenuMetok;
   
  
 
    
    void setMetki(String nameMetki){
        listMetok.add(nameMetki);
    }
    
    void setInitializedMetki(){
        massMetok = new String[listMetok.size()];
        massMetok = (String[]) listMetok.toArray();
    }
    
   
  
 
 

    ArrayList<String> sList = new ArrayList<>();

    ArrayList<String> getListSortedReitings() {
        return (ArrayList<String>) sList.clone();
    }

    private static ArrayList<PanelCreateZametks> listPanel = new ArrayList<>();

    /**
     *
     * @param reiting Реализация отображения по рейтингу заметки
     */
    void vidPoReitingu(int reiting) {
        this.reiting = reiting;

        setVisibalMetkiReitinga(reiting);

        urlJavaTicks.clearListLabel(); // очистить список заметок

        Dirs d = urlJavaTicks.getDirs();
        for (int i = 0; i < listPanel.size(); i++) {

            if (listPanel.get(i).getReiting() == reiting) {
                urlJavaTicks.setDirZametki(Paths.get(listPanel.get(i).toString()));
            }

        }
        d.createDir(false, "false");
//       repaintSpisok();

    }

    /**
     *
     * @param reiting Реализация отображения по метке
     */
    void vidPoMetki(String metka) {
        this.metka = metka;
//                listPanel2 = new ArrayList<>();
        urlJavaTicks.clearListLabel(); // очистить список заметок

        Dirs d = urlJavaTicks.getDirs();
        for (int i = 0; i < listPanel.size(); i++) {
            if (listPanel.get(i).getMetka().equalsIgnoreCase(metka)) {
                System.out.println(listPanel.get(i).toString());
                urlJavaTicks.setDirZametki(Paths.get(listPanel.get(i).toString()));
            }

        }
        d.createDir(false, "false");
//        repaintSpisok();

    }

    /**
     * Реализация отображения всего содержимого
     */
    void repaintSpisok() {

        listPanel.clear();
        for (int i = 0; i < urlJavaTicks.getListSize(); i++) {
//                    System.out.println(i);

            PanelCreateZametks p = new PanelCreateZametks(urlJavaTicks, urlJavaTicks.getListZametki().get(i), i * 20);
            p.setBorder(null);
//                    p.setOpaque(false);
            p.setBounds(0, i * 20, 430, 20);
//                    p.setInitZnacheniya();

//                    p.addMouseListener(new MouseAdapter() {
//
//                        @Override
//                        public void mouseEntered(MouseEvent e) {
//                            p.SetVisibalMetki(false);
//                        }
//
//                        @Override
//                        public void mouseExited(MouseEvent e) {
//                            p.SetVisibalMetki(true);
//                        }
//
//                    }
//                    );
//                    p.setBorder(BorderFactory.createEtchedBorder());
            listPanel.add(p);
        }
        for (int j = 0; j < listPanel.size(); j++) {
            panelZametki.add(listPanel.get(j), Integer.valueOf(8));

        }

            setVisibalMetkiReitinga(reiting);
        
//            cBox.setSelectedIndex(0);

        updatePreferedSize();
        urlJavaTicks.setContentPane(this);
//
//                scrol.revalidate();
        urlJavaTicks.revalidate();

    }

    //обновление по всему содержимому
    void updatePreferedSize() {
        panelZametki.setPreferredSize(new Dimension(panelZametki.getPreferredSize().width, listPanel.size() * 20 + 20));
    }

    // Обновление в фильтре по рейтингу
    void updatePreferedSize2() {
        panelZametki.setPreferredSize(new Dimension(panelZametki.getPreferredSize().width, listPanel2.size() * 20 + 20));
    }

    private JScrollPane scrolPanemetki;

    
    
    public PanelBlockZametok(JavaTicks urlJavaTicks) {
        this.urlJavaTicks = urlJavaTicks;
        
        
        labelPopMenuMetok = new JLabel("Вывести по метке");
        labelPopMenuMetok.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelPopMenuMetok.addMouseListener(this);
        labelPopMenuMetok.setBounds(300,130, 150,20);
        labelPopMenuMetok.setForeground(Color.LIGHT_GRAY);
        add(labelPopMenuMetok,Integer.valueOf(9));
        
        
        popMenu  = new JPopupMenu();
        add(popMenu,Integer.valueOf(9));
      
        inicMetok();// инициализация и добавление меток JPopupMenu
        labelPopMenuMetok.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) { 
                popMenu.setInvoker(labelPopMenuMetok);
                popMenu.setLocation(labelPopMenuMetok.getLocationOnScreen().x, labelPopMenuMetok.getLocationOnScreen().y+20);
                popMenu.setVisible(true);
            }
               
        });
        
 // Кнопка: отобразить все       
        bVidAll = new JButton("Отобразить все");
        bVidAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bVidAll.setBounds(20, 130, 100, 20);
        bVidAll.setBorder(null);
//        bVidAll.setContentAreaFilled(false);
        bVidAll.addActionListener((e)->{ urlPanelTopMenu.setVidAll();});
        add(bVidAll);
        
 
      
// Поле для ввода и последующей фильтрации вывода заметок по заданной метке   
        textFilterMetki = new JTextField(100);
        textFilterMetki.setBounds(100, 90, 100, 30);
        textFilterMetki.setOpaque(false);
        textFilterMetki.setCaretColor(Color.GREEN);
        textFilterMetki.setForeground(Color.LIGHT_GRAY);
        textFilterMetki.addActionListener((e)->{ vidPoMetki(textFilterMetki.getText());});
//        add(textFilterMetki);
        
// ПАнель заметок
        panelZametki = new JPanel(true);
        panelZametki.setLayout(null);
        panelZametki.setBounds(20, 150, 450, 330);
        panelZametki.setSize(450, 330 + (listPanel.size() * 20));
        panelZametki.setBackground(new Color(34, 35, 35));
        panelZametki.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelZametki.setPreferredSize(new Dimension(panelZametki.getPreferredSize().width, 330));
 
        // прокрутка для заметок
        scrol = new JScrollPane(panelZametki, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrol.setViewportView(panelZametki);
        scrol.setBorder(null);
        scrol.setOpaque(false);
        scrol.setBounds(20, 150, 450, 330);
        add(scrol);

        setLayout(null);
        setBounds(0, 0, 495, 495);
        setOpaque(false);

        // верхняя панель меню
        urlPanelTopMenu = new PanelTopMenu(urlJavaTicks);
        urlPanelTopMenu.setBounds(0, 0, 477, 77);
        urlPanelTopMenu.setBorder(null);
        urlPanelTopMenu.setOpaque(false);
        add(urlPanelTopMenu, Integer.valueOf(7));

        panelVidReiting = new JPanel();
        panelVidReiting.setBounds(140, 120, 140, 30); //370, 130, 100, 20
        initbReiting();
        panelVidReiting.add(bReiting[0]);
        panelVidReiting.add(bReiting[1]);
        panelVidReiting.add(bReiting[2]);
        panelVidReiting.add(bReiting[3]);
        panelVidReiting.add(bReiting[4]);
//        panelVidReiting.setVisible(false);
        add(panelVidReiting);

        // Обложка фона      
        LABELFON = new JLabel(new ImageIcon(JavaTicks.class.getResource("/image/StartFonImage.png")));
        LABELFON.setBounds(0, 0, 495, 495);
        add(LABELFON, Integer.valueOf(3));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
 
    
        
        

        if (e.getSource().equals(bReiting[0])) {
            setVisibalMetkiReitinga(1);
            vidPoReitingu(1);
        } else if (e.getSource().equals(bReiting[1])) {
            setVisibalMetkiReitinga(2);
            vidPoReitingu(2);
        } else if (e.getSource().equals(bReiting[2])) {
            setVisibalMetkiReitinga(3);
            vidPoReitingu(3);
        } else if (e.getSource().equals(bReiting[3])) {
            setVisibalMetkiReitinga(4);
            vidPoReitingu(4);
        } else if (e.getSource().equals(bReiting[4])) {
            setVisibalMetkiReitinga(5);
            vidPoReitingu(5);
        }  

//        switch (e.getActionCommand()) {
//            case "1": {
//                setVisibalMetkiReitinga(1);
//                vidPoReitingu(1);
//                
//            } break;
//
//            case "2": {
//                setVisibalMetkiReitinga(2);
//                vidPoReitingu(2);
//              
//            } break;
//
//            case "3": {
//                setVisibalMetkiReitinga(3);
//                vidPoReitingu(3);
//            } break;
//
//            case "4": {
//                setVisibalMetkiReitinga(4);
//                vidPoReitingu(4);
//            } break;
//
//            case "5": {
//                setVisibalMetkiReitinga(5);
//                vidPoReitingu(5);
//            } break;
//            default : break;
//        };
    }

    @Override
    public void mouseClicked(MouseEvent e) {
 
    }

    @Override
    public void mousePressed(MouseEvent e) {
    
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
  
    }

    @Override
    public void mouseEntered(MouseEvent e) {
  
    }

    @Override
    public void mouseExited(MouseEvent e) {
    
    }

}
