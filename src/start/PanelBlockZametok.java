package start;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Pavel Mayatnikov Этот класс реализует панель редактирования
 */
public class PanelBlockZametok extends JPanel implements ActionListener {

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
//            System.out.println("Вызов обновления конфиг файла заметки");
//            System.out.println("Передача аргумента "+name.getText());

    }

    private JPanel panelVidAll; // панель отображения всего (по умолчанию)
    private JPanel panelVidMetka; // панель отображения по метке

    private JComboBox<String> cBox;

    ArrayList<String> sList = new ArrayList<>();

    ArrayList<String> getListSortedReitings() {
        return (ArrayList<String>) sList.clone();
    }

    private static ArrayList<PanelCreateZametks> listPanel = new ArrayList<>();

    /**
     *
     * @param reiting Реализация отображения по рейтингу заметки
     */
    Thread tVidPoReitingu;

    void vidPoReitingu(int reiting) {

                setVisibalMetkiReitinga(reiting);
                listPanel2 = new ArrayList<>();
                urlJavaTicks.clearListLabel(); // очистить список заметок

                Dirs d = urlJavaTicks.getDirs();
                for (int i = 0; i < listPanel.size(); i++) {
                     
                        if (listPanel.get(i).getReiting() == reiting) {
                    
                            if(!urlJavaTicks.getListZametki().contains(listPanel.get(i)))
//                            System.out.println(listPanel.get(i).toString());
                             urlJavaTicks.setDirZametki(Paths.get(listPanel.get(i).toString()));
                        }
                    
                }
 d.createDir(false, "false");
//       repaintSpisok();

    }

    /**
     * Реализация отображения всего содержимого
     */
    void repaintSpisok() {
//        Runnable r = new Runnable() {
//
//            @Override
//            public void run() {
//
//
//            }
//        };
//        new Thread(r).start();
        
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
                for (int i = 0; i < listPanel.size(); i++) {
                    panelZametki.add(listPanel.get(i), Integer.valueOf(8));

                }
//                    System.out.println(listPanel.get(i)); 

                updatePreferedSize();
                urlJavaTicks.setContentPane(this);

                scrol.revalidate();
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

    public PanelBlockZametok(JavaTicks urlJavaTicks) {
        this.urlJavaTicks = urlJavaTicks;

//        urlPanelBlockZametok = this;

        panelZametki = new JPanel(true);
        panelZametki.setLayout(null);
        panelZametki.setBounds(20, 150, 450, 330);
        panelZametki.setSize(450, 330 + (listPanel.size() * 20));
        panelZametki.setBackground(new Color(34, 35, 35));
        panelZametki.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelZametki.setPreferredSize(new Dimension(panelZametki.getPreferredSize().width, 330));
//      panelZametki.setOpaque(false);

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
        add(urlPanelTopMenu, Integer.valueOf(9));

        panelVidReiting = new JPanel();
        panelVidReiting.setBounds(300, 90, 140, 30);
        initbReiting();
        panelVidReiting.add(bReiting[0]);
        panelVidReiting.add(bReiting[1]);
        panelVidReiting.add(bReiting[2]);
        panelVidReiting.add(bReiting[3]);
        panelVidReiting.add(bReiting[4]);
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

}
