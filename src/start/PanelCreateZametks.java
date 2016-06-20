package start;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
       if(flag){
           setBorder(BorderFactory.createEtchedBorder()); 
       }else{
            setBorder(null);
       }
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
//         setBackground(Color.red);
//         setBorder(BorderFactory.createEtchedBorder());
      setBounds(getW(), getH(), 450, 20);
        
        System.out.println("path "+path);
     
        String strong = path.toString();

       
        //кнопка удаления заметки
        dell = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS()+"/image/Navigation/CreatePanel/ico/delete.png")));


        //кнопка открытия заметки    
        create = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS()+"/image/Navigation/CreatePanel/ico/process.png")));

        // кнопка открытия папки заметки
        open = new JButton(new ImageIcon(JavaTicks.class.getResource(urlJavaTicks.getS()+"/image/Navigation/CreatePanel/ico/folder.png")));
  
        
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
        add(dell,Integer.valueOf(11));
        
//кнопка редактирования
        create.setBorder(null);
        create.setContentAreaFilled(false);
        create.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        create.setBounds(380, 0, 20, 20);
        create.addMouseListener(this);
        create.setToolTipText("редактировать");
         add(create,Integer.valueOf(9));
        
//кнопка открытия 
        open.setBorder(null);
        open.setContentAreaFilled(false);
        open.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        open.setBounds(360, 0, 20, 20);
        open.addMouseListener(this);
        open.setToolTipText("открыть");
        add(open,Integer.valueOf(9));
        
        
        
        
        SetVisibal(false);
        
    }

    @Override
    public String toString() {
        return String.valueOf(path);
    }
    
    
    
    
    // Рекурсивное удаление папок\файлов
    void delete(Path p){
        if(!p.toFile().exists()){
            return;
        }
        boolean flag = false;
        if(p.toFile().isDirectory()){
            for(File x :Paths.get(p.toAbsolutePath().toString()).toFile().listFiles()){
                if(x.isDirectory()){
                    delete(x.toPath());
                }else{
                    x.delete();
                }
              
            }
          flag = p.toFile().delete();
        } 
        System.out.println("Удален? "+flag);
    }
    
    
    
    
    
    
    
    
    
    
    
    

    Path getPath() {
        return path;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(path.toAbsolutePath().toString());
  
        
        if(e.getSource().equals(dell)){
          
            delete(path);
                 
//            urlJavaTicks.frameTicks.setNameZametki(path.toAbsolutePath().toString());
            Dirs d = urlJavaTicks.getDirs();
            d.createDir();
            
             urlJavaTicks.geturlPanelBlockZametok().repaintSpisok();
             PanelBlockZametok  p = new PanelBlockZametok(urlJavaTicks);
             p.repaintSpisok();
            
            
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
        SetVisibal(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        SetVisibal(false);
    }

}
