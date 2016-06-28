package start;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


//
/**
 *
 * @author Pavel Mayatnikov Панель с элементом редактирования заметки
 */
public class PanelCreateZametks extends JPanel implements MouseListener, ActionListener {

    private static int x = 0;
    private static int y = 0;
    
    private JButton dell;
    private JLabel name;
    private JButton open;
    private JButton create;
    private Path path;
    private JavaTicks urlJavaTicks;
    private ArrayList<PanelCreateZametks> listPanelCreateZametks = new ArrayList<>();
    
    
    
    private JLabel metka;
    private int znachimost =0;

    // Работа с меткой
   private ImageIcon icoVidFalse = new ImageIcon(JavaTicks.class.getResource("/image/StatusMetki/IcoFalse.png")); // Иконка в состоянии - Не выбрано
    private ImageIcon icoVidTrue = new ImageIcon(JavaTicks.class.getResource("/image/StatusMetki/IcoTrue.png"));  // Иконка в состоянии - Выбрано   
   
    private JButton[] metkaZnachimosti = new JButton[5]; // редактирование метки при наведении
    private JComboBox cBox; // отображение метки при потере фокуса 

    
    
    
    
    
    /**
     * 
     * @return 
     * Вернуть рейтинг заметки
     */
    public int getReiting(){
        return znachimost;
    }
    
       
  
    public String getZaMetka(){
        return name.getText();
    }
    public String getMetka(){
        return metka.getText();
    }
    
    public void setMetka(String newMetka){
         metka.setText(newMetka);
    }
    
    
    void updateZametkietki(){
        urlJavaTicks.UpdateConfigZametki(name.getText(),metka.getText(),znachimost);
    }
    
    /**
     *
     * @param x Установка нового приоретера заметки
     */
    void setVisibalMetkaZnacheniya(int x) {
            int xxx = 0;
        if (x <= 5) {
            for (int i = 0; i < metkaZnachimosti.length; i++) {
                if (i < x) {
                    ++xxx;
                    metkaZnachimosti[i].setIcon(icoVidTrue);
                     metkaZnachimosti[i].setBorder(null);
                     metkaZnachimosti[i].setContentAreaFilled(false);
                     
                }else{
                    metkaZnachimosti[i].setIcon(icoVidFalse);
                     metkaZnachimosti[i].setBorder(null);
                     metkaZnachimosti[i].setContentAreaFilled(false);
                }
            }
            znachimost = x;
            urlJavaTicks.setReitingZametki(x);
            urlJavaTicks.UpdateConfigZametki(name.getText(),metka.getText(),xxx);
        }
//            System.out.println("Вызов обновления конфиг файла заметки");
//            System.out.println("Передача аргумента "+name.getText());
            
    }
    /**
     *
     * @param x Инициализация приоретера заметки
     */
    void setVisibalMetkaZnacheniya() {
           
        if (znachimost <= 5) {
            for (int i = 0; i < metkaZnachimosti.length; i++) {
                if (i < znachimost) {
                    metkaZnachimosti[i].setIcon(icoVidTrue);
                     metkaZnachimosti[i].setBorder(null);
                     metkaZnachimosti[i].setContentAreaFilled(false);
                }else{
                    metkaZnachimosti[i].setIcon(icoVidFalse);
                     metkaZnachimosti[i].setBorder(null);
                     metkaZnachimosti[i].setContentAreaFilled(false);
                }
            }
 
        }
//            System.out.println("Вызов обновления конфиг файла заметки");
//            System.out.println("Передача аргумента "+name.getText());
            
    }

    /**
     * Инициализация меток значимости
     */
    private void initMetkaZnachimosti() {
        int x = 160;
        for (int i = 0; i < metkaZnachimosti.length; i++) {
            metkaZnachimosti[i] = new JButton(icoVidFalse);
            metkaZnachimosti[i].setActionCommand(String.valueOf(i));
            metkaZnachimosti[i].addMouseListener(this);
            metkaZnachimosti[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            metkaZnachimosti[i].setBorder(null);
            metkaZnachimosti[i].setContentAreaFilled(false);
            metkaZnachimosti[i].setBounds(x, 0, 20, 20);
            metkaZnachimosti[i].addActionListener(this);
            x += 20;
        }
        metkaZnachimosti[0].setIcon(icoVidTrue);
    }
    
    
void setInitZnacheniya(){
    StringTokenizer s = new StringTokenizer(urlJavaTicks.getConfZametki(name.getText()), ";");
//   System.out.println(name.getText());
        while(s.hasMoreElements()){
            String g = s.nextToken();
            // Нахождение идентификатора значения
                String x = g.substring(0, g.lastIndexOf("="));
//             System.out.print("Значение = "+x+";");
         if(x.equals("REITING")){
           // Нахождение значения
      String  j = g.substring(g.lastIndexOf("=")+1);
//          System.out.println("Значение "+j);
      znachimost = Integer.valueOf(j);
            setVisibalMetkaZnacheniya();// Обработать исключение
         }else{
             if(x.equals("METKA")){ 
                  String  j =g.substring(g.lastIndexOf("=")+1);
                  
                     metka.setText(j);
//                     metka.setText(j);
//          System.out.println("Значение "+j);
               
             }
         }
        }
}

 
//private JPanel panel;
//private JScrollPane scrolBlock;
    // Инициировать все компоненты
    void setVisibalBlock(boolean flag) {
        dell.setVisible(flag);
        open.setVisible(flag);
        create.setVisible(flag);
//        metkaZnachimosti[0].setVisible(flag);
//        metkaZnachimosti[1].setVisible(flag);
//        metkaZnachimosti[2].setVisible(flag);
//        metkaZnachimosti[3].setVisible(flag);
//        metkaZnachimosti[4].setVisible(flag);
 
        if (flag) {
            setBorder(BorderFactory.createEtchedBorder());
        } else {
            setBorder(null);
        }
        revalidate();
    }

    public int getW() {
        return 20;
    }

    int getH() {
        return 150;
    }

    public PanelCreateZametks(JavaTicks t, Path path, int h) { // Задать расположение компонента при старте
        
        this.urlJavaTicks = t;
//        setForeground(Color.red);
        this.path = path;
        setLayout(null);
        setOpaque(false);
        addMouseListener(this);
        
        
        znachimost  = urlJavaTicks.getReitingZametki();
//         setBackground(Color.red);
//         setBorder(BorderFactory.createEtchedBorder());
        setBounds(getW(), getH(), 450, 20);

//        System.out.println("path "+path);
        String strong = path.toString();

        //кнопка удаления заметки
        dell = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/Navigation/CreatePanel/ico/delete.png")));

        //кнопка открытия заметки    
        create = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/Navigation/CreatePanel/ico/process.png")));

        // кнопка открытия папки заметки
        open = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS() + "/image/Navigation/CreatePanel/ico/folder.png")));

        //Назание заметки
        name = new JLabel(strong.substring(strong.lastIndexOf("\\") + 1));
//        name.setFont(new Font("Arial", Font.ITALIC, 11));
        name.setForeground(Color.GREEN);
//        name.setPreferredSize(new Dimension(150, 20));
        name.setBounds(10, 0, 150, 20);

//        name.setVerticalTextPosition(JLabel.CENTER);
//        name.setHorizontalTextPosition(JLabel.LEFT);
        add(name);

//кнопка удаления
        dell.setBorder(null);
        dell.setContentAreaFilled(false);
        dell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dell.setBounds(410, 0, 20, 20);
        dell.addMouseListener(this);
        dell.setToolTipText("удалить");
        add(dell, Integer.valueOf(11));

//кнопка редактирования
        create.setBorder(null);
        create.setContentAreaFilled(false);
        create.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        create.setBounds(380, 0, 20, 20);
        create.addMouseListener(this);
        create.setToolTipText("редактировать");
        add(create, Integer.valueOf(9));

//кнопка открытия 
        open.setBorder(null);
        open.setContentAreaFilled(false);
        open.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        open.setBounds(360, 0, 20, 20);
        open.addMouseListener(this);
        open.setToolTipText("открыть");
        add(open, Integer.valueOf(9));
        
        metka = new JLabel(urlJavaTicks.getMetki());
//        metka.addMouseListener(this);
        metka.setBorder(null);
        metka.setBounds(265, 0, 100, 20);
//        metka.setVisible(false);
    add(metka);
        
        
        
        initMetkaZnachimosti();
        add(metkaZnachimosti[0]);
        add(metkaZnachimosti[1]);
        add(metkaZnachimosti[2]);
        add(metkaZnachimosti[3]);
        add(metkaZnachimosti[4]);

        setInitZnacheniya();
        
        setVisibalBlock(false);

    }

    @Override
    public String toString() {
        return String.valueOf(path);
    }

    // Рекурсивное удаление папок\файлов
    void delete(Path p) {
        if (!p.toFile().exists()) {
            return;
        }
        boolean flag = false;
        if (p.toFile().isDirectory()) {
            for (File x : Paths.get(p.toAbsolutePath().toString()).toFile().listFiles()) {
                if (x.isDirectory()) {
                    delete(x.toPath());
                } else {
                    x.delete();
                }

            }
            flag = p.toFile().delete();
        }
//        System.out.println("Удален? "+flag);
    }

    Path getPath() {
        return path;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println(path.toAbsolutePath().toString());

        if (e.getSource().equals(dell)) {
            delete(path);
//            urlJavaTicks.frameTicks.setNameZametki(path.toAbsolutePath().toString());
            Dirs d = urlJavaTicks.getDirs();
            d.createDir();
            urlJavaTicks.geturlPanelBlockZametok().repaintSpisok();
            PanelBlockZametok p = new PanelBlockZametok(urlJavaTicks);
            p.repaintSpisok();
        } else if (e.getSource().equals(create)) {
            String s = path.toString().substring(path.toString().lastIndexOf("\\") + 1);
            //urlJavaTicks.getStandardPanel().getLoadingZametki().runScan(openNameZametka.getText());
            urlJavaTicks.getFrameTicks().setNullTextFile();
            urlJavaTicks.getStandardPanel().getLoadingZametki().runScan(s);
            urlJavaTicks.setContentPane(urlJavaTicks.getFrameTicks());
            urlJavaTicks.revalidate();
        } else if (e.getSource().equals(open)) {
            Runtime r = Runtime.getRuntime();
            try {
                Process p = r.exec("explorer.exe " + path);
            } catch (IOException ex) {
                Logger.getLogger(PanelCreateZametks.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        setVisibalBlock(true);
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setVisibalBlock(false);
         
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "0": {
                setVisibalMetkaZnacheniya(1);
            }
            break;

            case "1": {
                setVisibalMetkaZnacheniya(2);
            }
            break;

            case "2": {
                setVisibalMetkaZnacheniya(3);
            }
            break;

            case "3": {
                setVisibalMetkaZnacheniya(4);
            }
            break;

            case "4": {
                setVisibalMetkaZnacheniya(5);
            }
            break;

        }
    }

}
