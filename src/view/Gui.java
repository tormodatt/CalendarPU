package view;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*; 
import java.awt.event.*; 
public class Gui extends JFrame{
	private JTextField window, agendawindow; 
	private JButton one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen,sixteen,  seventeen, eighteen, nineteen, twenty, twentyone, twentytwo, twentythree, twentyfour, twentyfive, twentysix, twentyseven, twentyeight, twentynine, thirty, thirtyone; 
	private String agenda, appointment, calendar, inbox; 
	private JPanel contentPanel; 
	public Gui(){
		super("Calendar"); 
		window = new JTextField(null, 55);
		window.setEditable(false);
		//setter dimmensjon på vinduet
		Dimension dimdim = new Dimension(580, 120); 
		window.setPreferredSize(dimdim);


		//lager knapper
		one = new JButton("1");
		two = new JButton("2");
		three = new JButton("3");
		four = new JButton("4");
		five = new JButton("5");
		six = new JButton("6");
		seven = new JButton("7");
		eight = new JButton("8");
		nine = new JButton("9");
		ten = new JButton("10");
		eleven = new JButton("11");
		twelve= new JButton("12");
		thirteen = new JButton("13");
		fourteen = new JButton("14");
		fifteen = new JButton("15");
		sixteen = new JButton("16");
		seventeen = new JButton("17");
		eighteen = new JButton("18");
		nineteen = new JButton("19");
		twenty = new JButton("20");
		twentyone = new JButton("21"); 
		twentytwo = new JButton("22"); 
		twentythree = new JButton("23"); 
		twentyfour = new JButton("24"); 
		twentyfive = new JButton("25"); 
		twentysix = new JButton("26"); 
		twentyseven = new JButton("27"); 
		twentyeight = new JButton("28"); 
		twentynine = new JButton("29"); 
		thirty = new JButton("30"); 
		thirtyone = new JButton("31"); 

		//setter størrelse på knappen
		Dimension dim = new Dimension(50,50); 
		one.setPreferredSize(dim);
		two.setPreferredSize(dim);
		three.setPreferredSize(dim);
		four.setPreferredSize(dim);
		five.setPreferredSize(dim);
		six.setPreferredSize(dim);
		seven.setPreferredSize(dim);
		eight.setPreferredSize(dim);
		nine.setPreferredSize(dim);
		ten.setPreferredSize(dim);
		eleven.setPreferredSize(dim); 
		twelve.setPreferredSize(dim);
		thirteen.setPreferredSize(dim);
		fourteen.setPreferredSize(dim);
		fifteen.setPreferredSize(dim);
		sixteen.setPreferredSize(dim);
		seventeen.setPreferredSize(dim);
		eighteen.setPreferredSize(dim);
		nineteen.setPreferredSize(dim);
		twenty.setPreferredSize(dim);
		twentyone.setPreferredSize(dim);
		twentytwo.setPreferredSize(dim);
		twentythree.setPreferredSize(dim);
		twentyfour.setPreferredSize(dim);
		twentyfive.setPreferredSize(dim);
		twentysix.setPreferredSize(dim);
		twentyseven.setPreferredSize(dim);
		twentyeight.setPreferredSize(dim);
		twentynine.setPreferredSize(dim);
		thirty.setPreferredSize(dim);
		thirtyone.setPreferredSize(dim);


		agendawindow = new JTextField(null, 20);
		agendawindow.setEditable(false);
		//setter størrelsen på boksen
		Dimension dimbox = new Dimension(80, 80); 
		agendawindow.setPreferredSize(dimbox);


		contentPanel = new JPanel(); 
		contentPanel.setBackground(Color.pink);
		contentPanel.setLayout(new FlowLayout()); 

		contentPanel.add(agendawindow, BorderLayout.EAST); 
		contentPanel.add(window, BorderLayout.PAGE_END); 

		contentPanel.add(one); contentPanel.add(two); contentPanel.add(three); contentPanel.add(four); contentPanel.add(five); contentPanel.add(six); contentPanel.add(seven); 	contentPanel.add(agendawindow, BorderLayout.EAST); 
		contentPanel.add(eight); contentPanel.add(nine); contentPanel.add(ten); contentPanel.add(eleven); contentPanel.add(twelve); contentPanel.add(thirteen); contentPanel.add(fourteen); 
		contentPanel.add(fourteen); contentPanel.add(fifteen); contentPanel.add(seventeen); contentPanel.add(eighteen); contentPanel.add(nineteen); contentPanel.add(twenty); contentPanel.add(twentyone); 
		contentPanel.add(twentytwo); contentPanel.add(twentythree); contentPanel.add(twentyfour); contentPanel.add(twentyfive); contentPanel.add(twentysix); contentPanel.add(twentyseven);contentPanel.add(twentyeight);  
		contentPanel.add(twentynine); contentPanel.add(thirty); contentPanel.add(thirtyone);

		this.setContentPane(contentPanel);



	}

	public static void main(String[] args) {
		Gui g = new Gui(); 
		g.setSize(650,650); 
		g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		g.setVisible(true);
		g.setResizable(false); 
	}
}


