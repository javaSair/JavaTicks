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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;








/**
 *
 * @author Pavel Mayatnikov Этот класс реализует панель редактирования
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
    private JList<String> strList;  // Список меток
    private final Choice c = new Choice();
   private JButton bVidAll; 
    
    
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
    private String[] massMetok = {"-------", "не забыть", "колым", "отдых", "без_метки","работа", " "};
    private DefaultComboBoxModel<String> difComBox = new DefaultComboBoxModel<>(massMetok); // модель списка комбо-бокса
 
 

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
    void initChoice(){
        for(int i=0;i<massMetok.length;i++){
            c.add(massMetok[i]);
        }
    }
    
    
    
    public PanelBlockZametok(JavaTicks urlJavaTicks) {
        this.urlJavaTicks = urlJavaTicks;
        
 // Кнопка: отобразить все       
        bVidAll = new JButton("Отобразить все");
        bVidAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bVidAll.setBounds(20, 130, 100, 20);
        bVidAll.setBorder(null);
//        bVidAll.setContentAreaFilled(false);
        bVidAll.addActionListener((e)->{ urlPanelTopMenu.setVidAll();});
        add(bVidAll);
        
// Реализация списка меток
      DefaultListModel defM = new DefaultListModel();
      strList = new JList<>(massMetok);
      strList.setBounds(100, 130, 100, 20);
       strList.addListSelectionListener((ListSelectionEvent e) -> {
      vidPoMetki(strList.getSelectedValue());
        });
 
        initChoice();
    c.addItemListener((ItemEvent e) -> {
        if(c.getSelectedItem()!= null)
            vidPoMetki(c.getSelectedItem());
        });
       
      // Скрол для списка меток 
       scrolPanemetki  = new JScrollPane(c);
       scrolPanemetki.setBounds(280,130, 100,20);
       scrolPanemetki.setOpaque(false);
       scrolPanemetki.setHorizontalScrollBarPolicy(scrolPanemetki.HORIZONTAL_SCROLLBAR_NEVER);
       scrolPanemetki.setVerticalScrollBarPolicy(scrolPanemetki.VERTICAL_SCROLLBAR_NEVER);
       scrolPanemetki.setBorder(null);
       scrolPanemetki.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(scrolPanemetki,Integer.valueOf(8));
      
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
        panelVidReiting.setBounds(130, 120, 140, 30); //370, 130, 100, 20
        initbReiting();
        panelVidReiting.add(bReiting[0]);
        panelVidReiting.add(bReiting[1]);
        panelVidReiting.add(bReiting[2]);
        panelVidReiting.add(bReiting[3]);
        panelVidReiting.add(bReiting[4]);
//        panelVidReiting.setVisible(false);
        add(panelVidReiting);

        // Обложка фона      
        LABELFON = new JLabel(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/StartFonImage.png")));
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
