package start;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * 
 * @author Pavel Mayatnikov
 * Панель с элементом редактирования заметки
 */
public class PanelCreateZametks extends JPanel implements MouseListener {
    private static int x =0;
    private static int y=0;
    
    private JButton dell;
    private JLabel name;
    private JButton open;
    private JButton create;
    private Path path;
    private JavaTicks urlJavaTicks;
    private ArrayList<PanelCreateZametks> listPanelCreateZametks = new ArrayList<>();

//private JPanel panel;
//private JScrollPane scrolBlock;
    // Инициировать все компоненты
 

    void SetVisibal(boolean flag){
        dell.setVisible(flag);
       open.setVisible(flag);
       create.setVisible(flag);
    }
    
    
  public  int getW() {
        return 20;
    }

    int getH() {
        return 150;
    }

    public PanelCreateZametks(JavaTicks t,Path path,int h) { // Задать расположение компонента при старте
        this.urlJavaTicks = t;
//        setForeground(Color.red);
        this.path = path;
         setLayout(null);
         setOpaque(false);
         setBackground(Color.red);
//         setBorder(BorderFactory.createEtchedBorder());
      setBounds(getW(), getH(), 450, 20);
        
        System.out.println(path);
     
        String strong = path.toString();

       
        //кнопка удаления заметки
        dell = new JButton(new ImageIcon(urlJavaTicks.getS()+"src\\image\\Navigation\\CreatePanel\\ico\\delete.png"));


        //кнопка открытия заметки    
        create = new JButton(new ImageIcon(urlJavaTicks.getS()+"src\\image\\Navigation\\CreatePanel\\ico\\process.png"));

        // кнопка открытия папки заметки
        open = new JButton(new ImageIcon(urlJavaTicks.getS()+"src\\image\\Navigation\\CreatePanel\\ico\\folder.png"));
  
        
         //Назание заметки
        name = new JLabel(strong.substring(strong.lastIndexOf("\\") + 1));
        name.setFont(new Font("Arial", Font.ITALIC, 11));
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
        add(dell,Integer.valueOf(11));
        
//кнопка редактирования
        create.setBorder(null);
        create.setContentAreaFilled(false);
        create.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        create.setBounds(380, 0, 20, 20);
        create.addMouseListener(this);
         add(create,Integer.valueOf(9));
        
//кнопка открытия 
        open.setBorder(null);
        open.setContentAreaFilled(false);
        open.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        open.setBounds(360, 0, 20, 20);
        open.addMouseListener(this);
        add(open,Integer.valueOf(9));
        
        
        
        
        SetVisibal(false);
        
    }

    @Override
    public String toString() {
        return String.valueOf(path);
    }
    
    
    
    
    
    

    Path getPath() {
        return path;
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
        SetVisibal(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        SetVisibal(false);
    }

}
