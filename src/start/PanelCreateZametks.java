package start;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.util.ArrayList;
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
    private ArrayList<PanelCreateZametks> listPanelCreateZametks = new ArrayList<>();

//private JPanel panel;
//private JScrollPane scrolBlock;
    // Инициировать все компоненты
 

    int getW() {
        return 20;
    }

    int getH() {
        return 150;
    }

    public PanelCreateZametks(Path path,int x,int h,int width,int height) { // Задать расположение компонента при старте
        System.out.println(path);
     
        String strong = path.toString();

        //Назание заметки
        name = new JLabel(strong.substring(strong.lastIndexOf("\\") + 1), new ImageIcon("src\\image\\Navigation\\CreatePanel\\fonName.png"), JLabel.CENTER);

        //кнопка удаления заметки
        dell = new JButton(new ImageIcon("src\\image\\Navigation\\CreatePanel\\ico\\delete.png"));


        //кнопка открытия заметки    
        create = new JButton(new ImageIcon("src\\image\\Navigation\\CreatePanel\\ico\\process.png"));

        // кнопка открытия папки заметки
        open = new JButton(new ImageIcon("src\\image\\Navigation\\CreatePanel\\ico\\folder.png"));
  
        
        
        this.path = path;

        setLayout(null);

// имя заметки        
        name.setBounds(getW(), getH(), 450, 20);
        name.setVerticalTextPosition(JLabel.CENTER);
        name.setHorizontalTextPosition(JLabel.LEFT);
        add(name, Integer.valueOf(9));

//кнопка удаления
        dell.setBorder(null);
        dell.setContentAreaFilled(false);
        dell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dell.setBounds(450, getH()+x, 20, 20);
        add(dell,Integer.valueOf(9));
        
//кнопка редактирования
        create.setBorder(null);
        create.setContentAreaFilled(false);
        create.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        create.setBounds(430, getH()+x, 20, 20);
         add(create,Integer.valueOf(9));
        
//кнопка открытия 
        open.setBorder(null);
        open.setContentAreaFilled(false);
        open.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        open.setBounds(430, getH()+x, 20, 20);
        add(open,Integer.valueOf(9));
        
        
        x=+20;
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
