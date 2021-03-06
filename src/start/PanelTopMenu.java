 
package start;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

 
public class PanelTopMenu extends JPanel implements MouseListener{

     
    
    
    
     
    void updateCountLabek(){
    
                urlJavaTicks.clearListLabel(); // очистить список заметок
                urlJavaTicks.getDirs().createDir();  //заного заполнить список 
                urlJavaTicks.getStandardPanel().updateNumZametci();
                
    
    }
   
    
    

        private final ArrayList<ImageIcon> listIcon = new ArrayList<>();
        private JButton b1, b2, b3, b4, b5, b6, b7; // кнопки навигационной меню
    
        // Выпадающая из меню Панель добавления заметки
    private JPanel panelAdd;

    // Панель открытия существующей заметки
    private JPanel panelOpen;
    
         JLabel fonPoppupMessag;
     JLabel fonPoppupMessagNO;

    private JLabel labelPanelFon; // метка-фон навигационной меню
    private JLabel NumbetNameZametks;
    private JavaTicks urlJavaTicks;
    private FrameTicks urlFrameTicks;
    private JButton bClose;
    private JLabel LABELFON;
    private JButton openZametka; // открытие существующей заметки
    private JButton newAddZametka; // Создание новой заметки
    private JTextField newNameZametka; // поле для новой заметки
    private JTextField openNameZametka; // поле для существующей заметки
   
     
    void setVidAll(){
        b4.doClick();
    }
    
    
    public PanelTopMenu(JavaTicks urlJavaTicks1) {
        

        
        
        
        this.urlJavaTicks = urlJavaTicks1;
        this.urlFrameTicks = urlJavaTicks.getFrameTicks();
        setLayout(null);
        
        // Сообщение о наличии создаваемой заметки
        fonPoppupMessag = new JLabel(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/search/Message.png"))); // Нужно Изменить иконку
        fonPoppupMessag.setBounds(60, 68, 90, 50);
        fonPoppupMessag.setVisible(false);
        add(fonPoppupMessag, Integer.valueOf(3));
        
        
        fonPoppupMessagNO = new JLabel(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/search/MessageNo.png"))); // Нужно Изменить иконку
        fonPoppupMessagNO.setBounds(120, 68, 90, 50);
        fonPoppupMessagNO.setVisible(false);
        add(fonPoppupMessagNO, Integer.valueOf(3));

// Выпадающая из меню Панель добавления заметки
        panelAdd = new JPanel();
        panelAdd.setLayout(null);
        panelAdd.setBorder(BorderFactory.createEtchedBorder());
        panelAdd.setBounds(30, 48, 180, 20);
        panelAdd.setOpaque(false);
        panelAdd.addMouseListener(this);
        
    // Поле добавления новой заметки
        newNameZametka = new JTextField(5);
        newNameZametka.setText("заметка");

        newNameZametka.setBounds(0, 0, 100, 20);
        newNameZametka.setBorder(BorderFactory.createLineBorder(new Color(195, 195, 195)));
        newNameZametka.setCaretColor(Color.GREEN);
        newNameZametka.setOpaque(false);
        newNameZametka.setForeground(new Color(195, 195, 195));
        newNameZametka.addMouseListener(this);
        newNameZametka.addActionListener((e) -> {
            
//            urlJavaTicks.frameTicks.setNameZametki(newNameZametka.getText());
            Dirs d = urlJavaTicks.getDirs();
            d.createDir(newNameZametka.getText());
        });
        
       

    // Кнопка создания заметки
        newAddZametka = new JButton(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/b1.png")));
        newAddZametka.setText("добавить");
        newAddZametka.setForeground(Color.LIGHT_GRAY);
        newAddZametka.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newAddZametka.setVerticalTextPosition(JButton.CENTER);
        newAddZametka.setHorizontalTextPosition(JButton.CENTER);
        newAddZametka.setBounds(100, 0, 80, 20);
        newAddZametka.addActionListener((e) -> {
          
//            urlJavaTicks.frameTicks.setNameZametki(newNameZametka.getText());
            Dirs d = urlJavaTicks.getDirs();
            d.createDir(newNameZametka.getText());
             urlJavaTicks.getFrameTicks().setVisibalPanelSave(true);
        });     
        newAddZametka.setBorder(null);
        newAddZametka.setContentAreaFilled(false);
        panelAdd.add(newNameZametka,Integer.valueOf(4));
        panelAdd.add(newAddZametka, Integer.valueOf(6));
        add(panelAdd, Integer.valueOf(8));
        panelAdd.setToolTipText("panelAdd");
        panelAdd.setVisible(false);
        
        
        
        
 // Панель открытия существующей заметки  
        panelOpen= new JPanel();
        panelOpen.setLayout(null);
        panelOpen.setBorder(BorderFactory.createEtchedBorder());
        panelOpen.setBounds(100, 48, 180, 20);
        panelOpen.setOpaque(false);
        panelOpen.setToolTipText("panelOpen");
        panelOpen.setVisible(false);
        
        
    // Кнопка открытия
        openZametka = new JButton(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/b1.png")));
        openZametka.setText("открыть");
        openZametka.setForeground(Color.LIGHT_GRAY);
        openZametka.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        openZametka.setVerticalTextPosition(JButton.CENTER);
        openZametka.setHorizontalTextPosition(JButton.CENTER);
        openZametka.setBounds(100, 0, 80, 20);
        openZametka.addMouseListener(this);
        openZametka.addActionListener((e) -> {
          if (!"".equals(openNameZametka.getText())) {
                urlJavaTicks.getStandardPanel().getLoadingZametki().runScan(openNameZametka.getText());
            } else {
                openNameZametka.setText("Введите текст");

            }
        });     
        openZametka.setBorder(null);
        openZametka.setContentAreaFilled(false);
        
// Поле открываемой заметки        
        openNameZametka = new JTextField(5);
//        openNameZametka.setText("заметка");

        openNameZametka.setBounds(0, 0, 100, 20);
        openNameZametka.setBorder(BorderFactory.createLineBorder(new Color(195, 195, 195)));
        openNameZametka.setCaretColor(Color.GREEN);
        openNameZametka.setOpaque(false);
        openNameZametka.setForeground(new Color(195, 195, 195));
        openNameZametka.addMouseListener(this);
        openNameZametka.addMouseListener(this);
        openNameZametka.addActionListener((e) -> {
            
            
                                if(!"".equals(openNameZametka.getText())){
                 
//                new LoadingZametki(urlJavaTicks, openNameZametka.getText()).runScan();
                        }else{
                            openNameZametka.setText("Введите текст");
                            
                        }
              
         });

// Добавить все на понель открытия заметки
            panelOpen.add(openZametka,Integer.valueOf(6));       
            panelOpen.add(openNameZametka,Integer.valueOf(6));
            
            add(panelOpen,Integer.valueOf(9));

// Инициализировать и добавить в панель кнопки задач
        // кнопка закрытия 
        b1 = new JButton("Выход", new ImageIcon(JavaTicks.class.getResource("/image/Navigation/b1.png")));
        b1.setVerticalTextPosition(JButton.CENTER);
        b1.setHorizontalTextPosition(JButton.CENTER);
        b1.setBounds(405, 15, 65, 20);
        b1.setActionCommand("b1");
        b1.setBorder(null);
        b1.setForeground(Color.LIGHT_GRAY);
        b1.setContentAreaFilled(false);
        b1.addMouseListener(this);
        b1.addActionListener((e) -> {
            System.exit(0);
        });
        b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(b1, Integer.valueOf(6));
        repaint();

        
        b2 = new JButton("Контакт", new ImageIcon(JavaTicks.class.getResource("/image/Navigation/b1.png")));
        b2.setVerticalTextPosition(JButton.CENTER);
        b2.setHorizontalTextPosition(JButton.CENTER);
        b2.setBounds(340, 15, 65, 20);
        b2.setBorder(null);
        b2.setForeground(Color.LIGHT_GRAY);
        b2.setContentAreaFilled(false);
        b2.addMouseListener(this);
        b2.setActionCommand("b2");
        b2.addActionListener((e)->{
                    KontactPanel cont = new KontactPanel(urlJavaTicks);
                    urlJavaTicks.setContentPane(cont);
                    urlJavaTicks.revalidate();
        });
        b2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(b2, Integer.valueOf(6));
        repaint();

        b3 = new JButton("Опции", new ImageIcon(JavaTicks.class.getResource("/image/Navigation/b1.png")));
        b3.setVerticalTextPosition(JButton.CENTER);
        b3.setHorizontalTextPosition(JButton.CENTER);
        b3.setBounds(285, 15, 65, 20);
        b3.setBorder(null);
        b3.setForeground(Color.LIGHT_GRAY);
        b3.setContentAreaFilled(false);
        b3.addMouseListener(this);
        b3.addActionListener((e)->{
            urlJavaTicks.setContentPane(urlJavaTicks.getPanelOption());
            urlJavaTicks.revalidate();
        });
        b3.setActionCommand("b3");
        b3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(b3, Integer.valueOf(6));
        repaint();

        b4 = new JButton("обзор", new ImageIcon(JavaTicks.class.getResource("/image/Navigation/b1.png")));
        b4.setVerticalTextPosition(JButton.CENTER);
        b4.setHorizontalTextPosition(JButton.CENTER);
        b4.setBounds(220, 15, 65, 20);
        b4.setBorder(null);
        b4.setForeground(Color.LIGHT_GRAY);
        b4.setContentAreaFilled(false);
        b4.addMouseListener(this);
        b4.addActionListener((e)->{
//            urlJavaTicks.setContentPane(urlJavaTicks.geturlPanelBlockZametok()); // Перед отображением редактируемой заметки панели переключить панель
 
            Dirs d = urlJavaTicks.getDirs();
            d.createDir(); // Перечитать все заметки
            urlJavaTicks.geturlPanelBlockZametok().repaintSpisok();
//                 urlJavaTicks.geturlPanelBlockZametok().inicMetok();
             PanelBlockZametok  p = new PanelBlockZametok(urlJavaTicks);
             p.repaintSpisok();
            
            // Добавить все заметки на панель и отобразить
                
        });
        b4.setActionCommand("b4");
        b4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(b4, Integer.valueOf(6));
        repaint();

        b5 = new JButton("открыть", new ImageIcon(JavaTicks.class.getResource("/image/Navigation/b1.png")));
        b5.setVerticalTextPosition(JButton.CENTER);
        b5.setHorizontalTextPosition(JButton.CENTER);
        b5.setBounds(150, 15, 65, 20);
        b5.setBorder(null);
        b5.setForeground(Color.LIGHT_GRAY);
        b5.setContentAreaFilled(false);
        b5.addMouseListener(this);
        b5.setActionCommand("b5");
        b5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(b5, Integer.valueOf(6));
        repaint();

        b6 = new JButton("создать", new ImageIcon(JavaTicks.class.getResource("/image/Navigation/b1.png")));
        b6.setVerticalTextPosition(JButton.CENTER);
        b6.setHorizontalTextPosition(JButton.CENTER);
        b6.setToolTipText("Добавить новую заметку");
        b6.setBounds(82, 15, 65, 20);
        b6.setBorder(null);
        b6.setForeground(Color.LIGHT_GRAY);
        b6.setContentAreaFilled(false);
//        b6.setActionCommand("b6");
        b6.addMouseListener(this);
//        b6.addActionListener((e)->{urlFrameTicks.setVisibalButtonSave(true);});
        b6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(b6, Integer.valueOf(8));
        repaint();

        b7 = new JButton("Домой", new ImageIcon(JavaTicks.class.getResource("/image/Navigation/b1.png")));
        b7.setVerticalTextPosition(JButton.CENTER);
        b7.setHorizontalTextPosition(JButton.CENTER);
        b7.setBounds(15, 15, 65, 20);
        b7.setBorder(null);
        b7.setForeground(Color.LIGHT_GRAY);
        b7.setContentAreaFilled(false);
        b7.addMouseListener(this);
        b7.setActionCommand("b7");
        b7.addActionListener((e)->{
//           urlJavaTicks.getStandardPanel().remove(StandardPanel.getGregorianCalendar_1());
//           urlJavaTicks.getStandardPanel().add(new GregorianCalendar_1(urlJavaTicks));
//            System.out.println("b7");
            updateCountLabek();
            urlJavaTicks.getStandardPanel().setVisiblePanelAdd(false);
            urlJavaTicks.getStandardPanel().setVisiblePanelOpen(false);
            urlJavaTicks.setContentPane(urlJavaTicks.getStandardPanel());
            urlJavaTicks.revalidate();
        });
        b7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(b7, Integer.valueOf(6));
        repaint();

        // Заполнить список иконками
        listIcon.add(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/fon.png")));
        listIcon.add(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/fon1.png")));
        listIcon.add(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/fon2.png")));
        listIcon.add(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/fon3.png")));
        listIcon.add(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/fon4.png")));
        listIcon.add(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/fon5.png")));
        listIcon.add(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/fon6.png")));
        listIcon.add(new ImageIcon(JavaTicks.class.getResource("/image/Navigation/fon7.png")));

        labelPanelFon = new JLabel(listIcon.get(0));
        labelPanelFon.setBounds(10, 0, 475, 48);
        labelPanelFon.setBorder(null);
        labelPanelFon.setOpaque(false);
        labelPanelFon.addMouseListener(this);
     add(labelPanelFon, Integer.valueOf(6));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(newNameZametka)) {
            newNameZametka.selectAll();
        } else if (e.getSource().equals(b6)) { //Создать заметку
            if(!panelAdd.isVisible()){
                
//                urlJavaTicks.getStandardPanel().setVisiblePanelAdd(true);
               setVisibalButtons(panelAdd, true); 
//                panelAdd.setVisible(true);
//                panelOpen.setVisible(false);
//            newNameZametka.selectAll();
            
            }else{
//                 panelAdd.setVisible(false);
//                panelOpen.setVisible(true);
             setVisibalButtons(panelAdd, false);
            }
        }else if(e.getSource().equals(b5)){// Открыть заметку
            if(!panelOpen.isVisible()){
                setVisibalButtons(panelOpen, true);
            }else{
            setVisibalButtons(panelOpen, false); 
            }
        }else if(e.getSource().equals(openNameZametka) | e.getSource().equals(openZametka)){
                openNameZametka.selectAll();
            } 
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource().equals(b1)) {
            labelPanelFon.setIcon(listIcon.get(7));
        } else if (e.getSource().equals(b2)) {
            labelPanelFon.setIcon(listIcon.get(6));
        } else if (e.getSource().equals(b3)) {
            labelPanelFon.setIcon(listIcon.get(5));
        } else if (e.getSource().equals(b4)) {
            labelPanelFon.setIcon(listIcon.get(4));
        } else if (e.getSource().equals(b5)) {
            labelPanelFon.setIcon(listIcon.get(3));
        } else if (e.getSource().equals(b6)) {
            labelPanelFon.setIcon(listIcon.get(2));
        } else if (e.getSource().equals(b7)) {
            labelPanelFon.setIcon(listIcon.get(1));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(labelPanelFon)) {
            labelPanelFon.setIcon(listIcon.get(0));
        } 
    }
    
    
    
        void setVisibalButtons(JPanel b,boolean f){
        panelAdd.setVisible(false);
        panelOpen.setVisible(false);
        
        b.setVisible(f);
        System.out.println(b.getToolTipText());
    }
    
    
}
