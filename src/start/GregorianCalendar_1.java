package start;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GregorianCalendar_1 extends JPanel implements ActionListener {

    JPanel panel;
    JLabel labelMonth, LabelDayNedeli, labelOtstup, labelGod;
    JButton bNext, bPrevious, bGodNext, bGodPrevious;
    JButton b[] = new JButton[31];
    private static String[] month = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь",};
    private static String[] weeks = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс",};
    private static GregorianCalendar calendar = new GregorianCalendar();
    private static GregorianCalendar dataDay = new GregorianCalendar();
    private static boolean flag = false;
    private ImageIcon iconTrue, iconFalse, iconActive, icoActiveTrue;
    private static JavaTicks urlJavaTicks;
    private static Map<Path, String> map = new Hashtable<>();
    private ArrayList<Path> list = new ArrayList<>();
    private final Font font = new Font("Arial", Font.PLAIN, 20);

    private static int otstupXXX = 0;
    private Calendar c;

    /**
     *
     * @param urlJavaTicks параметр для заполнения списка заметок
     * @throws HeadlessException Фигня автоматом сгенирилась Конструктор
     * инициализирует начальное состояние Кплендаря с подгрузгой дат создания
     * заметок и выделения этих дат для последующего обзора в обзорном списке
     * отфильтрованном по дате
     */
    public GregorianCalendar_1(JavaTicks urlJavaTicks) throws HeadlessException {
        this.urlJavaTicks = urlJavaTicks;

        setSize(210, 300);
        setLayout(null);
        setOpaque(false);
// формирование массива заметок
        inicDateFile();

// Иконки дат    TickFalse    TickTrue    TickActiveTrue
        iconActive = new ImageIcon(JavaTicks.class.getResource("/image/TickActive.png")); // Иконка текущей даты
        iconFalse = new ImageIcon(JavaTicks.class.getResource("/image/TickFalse.png")); // Иконка даты без заметки
        iconTrue = new ImageIcon(JavaTicks.class.getResource("/image/TickTrue.png")); // Иконка даты с заметкой-ками
        icoActiveTrue = new ImageIcon(JavaTicks.class.getResource("/image/TickActiveTrue.png")); // Иконка даты с заметкой-ками

// Панель содержащая календарь
        panel = new JPanel(null);
        panel.setOpaque(false);
        panel.setBounds(10, 100, 380, 400);

// Инициализация значений даты
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        c = (Calendar) calendar.getInstance().clone();

// Вызов формирующих календарь методов      
        getOtstupX();
        initCalendarNew(panel);

// Метка отображения года 
        labelGod = new JLabel(String.valueOf(c.get(GregorianCalendar.YEAR)));
        labelGod.setBounds(75, 20, 55, 20);
        labelGod.setFont(font);
        labelGod.setForeground(Color.LIGHT_GRAY);
        labelGod.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                bNext.setVisible(false);
                bPrevious.setVisible(false);
                bGodNext.setVisible(true);
                bGodPrevious.setVisible(true);
            }

        });
        add(labelGod);

// метка отображающая текущий месяц
        labelMonth = new JLabel(month[c.get(GregorianCalendar.MONTH)]);
        labelMonth.setBounds(75, 50, 55, 20);
        labelMonth.setForeground(Color.LIGHT_GRAY);
        labelMonth.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                bNext.setVisible(true);
                bPrevious.setVisible(true);
                bGodNext.setVisible(false);
                bGodPrevious.setVisible(false);
            }

        });
        add(labelMonth);
        add(panel);

// Метка отображающая дни нидели
        LabelDayNedeli = new JLabel("Пн    Вт    Ср    Чт    Пт   Сб    Вс");
        LabelDayNedeli.setForeground(Color.LIGHT_GRAY);
        LabelDayNedeli.setBounds(10, 75, 200, 20);
        add(LabelDayNedeli);

// Кнопки переключения месяцев
        bNext = new JButton(">>");
        bNext.setFont(font);
        bNext.setForeground(Color.LIGHT_GRAY);
        bNext.setBounds(150, 50, 30, 20);
        bNext.setBorder(null);
        bNext.setContentAreaFilled(false);
        bNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bNext.addActionListener((e) -> {

//            System.out.println("в перед");
            c.add(GregorianCalendar.MONTH, 1);
            calendar.set(GregorianCalendar.MONTH, c.get(GregorianCalendar.MONTH));
            labelMonth.setText((month[c.get(GregorianCalendar.MONTH)]));
            getOtstupX();

//            System.out.println(labelMonth.getText() + " отступ " + otstupXXX);
//                panel.revalidate();
            remove(panel);
            panel = null;

            panel = new JPanel(null);
            panel.setOpaque(false);
            panel.setBounds(10, 100, 380, 400);
            initCalendarNew(panel);

        });
        add(bNext);

        bPrevious = new JButton("<<");
        bPrevious.setFont(font);
        bPrevious.setForeground(Color.LIGHT_GRAY);
        bPrevious.setBounds(5, 50, 30, 20);
        bPrevious.setBorder(null);
        bPrevious.setContentAreaFilled(false);
        bPrevious.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bPrevious.addActionListener((e) -> {
//            System.out.println("назад");
            c.add(GregorianCalendar.MONTH, -1);
            calendar.set(GregorianCalendar.MONTH, c.get(GregorianCalendar.MONTH));
            labelMonth.setText((month[c.get(GregorianCalendar.MONTH)]));
            getOtstupX();

//            System.out.println(labelMonth.getText() + " отступ " + otstupXXX);
//                panel.revalidate();
            remove(panel);
            panel = null;

            panel = new JPanel(null);
            panel.setOpaque(false);
            panel.setBounds(10, 100, 380, 400);
            initCalendarNew(panel);

        });
        add(bPrevious);

// Кнопки прокрутки года
        bGodNext = new JButton(">>");
        bGodNext.setFont(font);
        bGodNext.setForeground(Color.LIGHT_GRAY);
        bGodNext.setBounds(150, 20, 30, 20);
        bGodNext.setBorder(null);
        bGodNext.setContentAreaFilled(false);
        bGodNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bGodNext.addActionListener((e) -> {

//            System.out.println("в перед");
            c.add(GregorianCalendar.YEAR, 1);
            calendar.set(GregorianCalendar.YEAR, c.get(GregorianCalendar.YEAR));
            labelGod.setText(String.valueOf(c.get(GregorianCalendar.YEAR)));
            getOtstupX();

//            System.out.println(labelMonth.getText() + " отступ " + otstupXXX);
//                panel.revalidate();
            remove(panel);
            panel = null;

            panel = new JPanel(null);
            panel.setOpaque(false);
            panel.setBounds(10, 100, 380, 400);
            initCalendarNew(panel);

        });

        bGodNext.setVisible(false);
        add(bGodNext);

        bGodPrevious = new JButton("<<");
        bGodPrevious.setFont(font);
        bGodPrevious.setForeground(Color.LIGHT_GRAY);
        bGodPrevious.setBounds(5, 20, 30, 20);
        bGodPrevious.setBorder(null);
        bGodPrevious.setContentAreaFilled(false);
        bGodPrevious.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bGodPrevious.addActionListener((e) -> {
//            System.out.println("назад");
            c.add(GregorianCalendar.YEAR, -1);
            calendar.set(GregorianCalendar.YEAR, c.get(GregorianCalendar.YEAR));
            labelGod.setText(String.valueOf(c.get(GregorianCalendar.YEAR)));
            getOtstupX();

//            System.out.println(labelMonth.getText() + " отступ " + otstupXXX);
//                panel.revalidate();
            remove(panel);
            panel = null;

            panel = new JPanel(null);
            panel.setOpaque(false);
            panel.setBounds(10, 100, 380, 400);
            initCalendarNew(panel);

        });
        bGodPrevious.setVisible(false);
        add(bGodPrevious);
        update();
        setVisible(true);

    }

    /**
     *
     * @param p панель содержащая календарь формируемая динамически
     * Инициализация и формирование начального состояния календаря, после
     * запроса на отображения нового месяца Также после клика по кнопкам
     * (стрелка в лево или в право от месяца) динамически формирует календарь
     */
    private void initCalendarNew(JPanel p) {
        int globalDay = 0;
        int dayOfMont = 1;
        int maximemDayOfMont = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int otstupY = 0;
        int otstupX = otstupXXX * 20;
        int day = otstupXXX;
        int month = calendar.get(GregorianCalendar.MONTH);

        int deysMont = calendar.get(GregorianCalendar.MONDAY);

        while (maximemDayOfMont-- > 0) {

            switch (weeks[getWeekDay(day)]) { // Вычислить первый день месяца (отстп)
                case "Вс": {

                    otstupY += 25;
                    otstupX = 0;
//                    System.out.println("Отступ 7");
                    day = 2;
                }
                break;
                case "Пн": {
                    otstupX = 25;
//                    System.out.println("Отступ 1");
                    day = 3;
                }
                break;
                case "Вт": {
                    otstupX = 50;
//                    System.out.println("Отступ 2");
                    day = 4;
                }
                break;
                case "Ср": {
                    otstupX = 75;
//                    System.out.println("Отступ 3");
                    day = 5;
                }
                break;
                case "Чт": {
                    otstupX = 100;
//                    System.out.println("Отступ 4");
                    day = 6;
                }
                break;
                case "Пт": {
                    otstupX = 125;
//                    System.out.println("Отступ 5");
                    day = 7;
                }
                break;
                case "Сб": {
                    otstupX = 150;
//                    System.out.println("Отступ 6");
                    day = 1;
                }
                break;
            }

            if (getColorButton(c.get(GregorianCalendar.YEAR), c.get(GregorianCalendar.MONTH) + 1, dayOfMont)) {
                b[dayOfMont - 1] = new JButton(iconTrue);

                b[dayOfMont - 1].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//                b[dayOfMont - 1].setForeground(Color.DARK_GRAY);
                {// Блок формирования даты для последующей проверки и вывода
                    String nameAllDate = String.valueOf(c.get(GregorianCalendar.YEAR));
                    int days = dayOfMont;
                    int mesyac = c.get(GregorianCalendar.MONTH) + 1;
                    if (mesyac < 10) {
                        nameAllDate += "-" + 0 + mesyac;
                    } else {
                        nameAllDate += "-" + mesyac;
                    }
                    if (days < 10) {
                        nameAllDate += "-" + 0 + days;
                    } else {
                        nameAllDate += "-" + days;
                    }
                    b[dayOfMont - 1].setActionCommand(nameAllDate);
                }

            } else {
                b[dayOfMont - 1] = new JButton(iconFalse);

            }
            b[dayOfMont - 1].setBounds(otstupX, otstupY, 20, 20);
            b[dayOfMont - 1].setBorder(null);
            b[dayOfMont - 1].setPreferredSize(new Dimension(20, 20));
            b[dayOfMont - 1].setText(String.valueOf(dayOfMont));
            if (dayOfMont == dataDay.get(GregorianCalendar.DAY_OF_MONTH)
                    && c.get(GregorianCalendar.MONTH) + 1 == dataDay.get(GregorianCalendar.MONTH) + 1
                    && c.get(GregorianCalendar.YEAR) == dataDay.get(GregorianCalendar.YEAR)) {
                if (getColorButton(c.get(GregorianCalendar.YEAR), c.get(GregorianCalendar.MONTH) + 1, dayOfMont)) {
                    // Если текущая дата с заметками
                    b[dayOfMont - 1].setIcon(icoActiveTrue);

                    b[dayOfMont - 1].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else { // Если текущая дата без заметок
                    b[dayOfMont - 1].setIcon(iconActive);

                }
            }
            b[dayOfMont - 1].setHorizontalTextPosition(JButton.CENTER);
            b[dayOfMont - 1].setVerticalTextPosition(JButton.CENTER);
            b[dayOfMont - 1].setContentAreaFilled(false);
            b[dayOfMont - 1].addActionListener(this);
            b[dayOfMont - 1].setForeground(Color.LIGHT_GRAY);
            p.add(b[dayOfMont - 1]);
            dayOfMont++;

//            calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
        };

        add(p);
        repaint();
    }

    /**
     *
     * @return return int Возвращает отступ в днях недели для точного добавления
     * первого дня месяца
     */
    private static int getOtstupX() {

        switch (weeks[getWeekDay(calendar.get(GregorianCalendar.DAY_OF_WEEK))]) {
            case "Пн": {
                otstupXXX = 1;
                return 1;
            }
            case "Вт": {
                otstupXXX = 2;
                return 2;
            }
            case "Ср": {
                otstupXXX = 3;
                return 3;
            }
            case "Чт": {
                otstupXXX = 4;
                return 4;
            }
            case "Пт": {
                otstupXXX = 5;
                return 5;
            }
            case "Сб": {
                otstupXXX = 6;
                return 6;
            }
            case "Вс": {
                otstupXXX = 7;
                return 7;
            }
        }
        return 0;
    }

    /**
     * Метод возвращает Отформатированный день недели
     *
     * @param day День недели формирующийся из GregorianCalendar
     * @return Отформатированный день недели
     */
    private static int getWeekDay(int day) {
        switch (day) {
            case 1: {
                return 6;
            }
            case 2: {
                return 0;
            }
            case 3: {
                return 1;
            }
            case 4: {
                return 2;
            }
            case 5: {
                return 3;
            }
            case 6: {
                return 4;
            }

        }
        return 5;
    }

    /**
     *
     * @param date Запрос на формирование массива заметок созданых в указанную
     * дату и последующее их отображение в обзорном окне заметок
     */
    private void getZametkiPoDate(String date) {
        urlJavaTicks.clearListLabel();

        Collection<String> setValue = map.values();
        Collection<Path> setKeys = map.keySet();

        Iterator<String> iterValue = setValue.iterator();
        Iterator<Path> iterKeys = setKeys.iterator();

        while (iterValue.hasNext()) {
            String nameValue = iterValue.next();
            Path nameKey = iterKeys.next();

            if (nameValue.equals(date)) {
                urlJavaTicks.setDirZametki(nameKey); // тут мы формируем массив заметок для последующего его отображения
            }

        }
        urlJavaTicks.getDirs().createDir(true, "Go");
    }

    private void update() {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);

                        if (urlJavaTicks.getListSize() != map.size()) {
//                                System.out.println(urlJavaTicks.getListSize()+" "+map.size());
                                map.clear();
                                inicDateFile();
                            getOtstupX();
                            remove(panel);
                            panel = null;
                            panel = new JPanel(null);
                            panel.setOpaque(false);
                            panel.setBounds(10, 100, 380, 400);
                            initCalendarNew(panel);
                            
                        }

                    } catch (InterruptedException ex) {
                    }
                }

            }
        };
        new Thread(r).start();
    }

    /**
     * Инициализация и формирования списка заметок с получением к каждой своей
     * даты создания
     */
    private static void inicDateFile() {

        try {
            DirectoryStream<Path> dir = Files.newDirectoryStream(Paths.get(urlJavaTicks.getNameDefaultDir()));

            for (Path x : dir) {
                BasicFileAttributes atribute = Files.readAttributes(x, BasicFileAttributes.class);
                String time = String.valueOf(atribute.lastModifiedTime());
                map.put(x, time.substring(0, time.lastIndexOf('T')));
            }
//        BasicFileAttributes atribute = Files.readAttributes(null, null, options)
 
        } catch (IOException ex) {
            Logger.getLogger(GregorianCalendar_1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param yer - год
     * @param month - месяц
     * @param day - день
     * @return Возвращает результат проверки запрошенной даты если дата найдена
     * в списке заметок то текущая дата должна быть выделена цветом для
     * последующей навигации
     */
    boolean getColorButton(int yer, int month, int day) {

        String allDate = String.valueOf(yer);
        int d = day;
        int m = month;

        if (m < 10) {
            allDate += "-" + 0 + m;
        } else {
            allDate += "-" + m;
        }
        if (d < 10) {
            allDate += "-" + 0 + d;
        } else {
            allDate += "-" + d;
        }

        return map.containsValue(allDate);
    }

    /**
     *
     * @param e Обработка событий нажатия на кнопки дат календаря
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        getZametkiPoDate(e.getActionCommand());
    }
}
