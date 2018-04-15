package costosModif2;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.*;

import javax.swing.JButton;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.alee.extended.date.WebDateField;
//import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
final public class Kardex extends JPanel implements java.awt.event.ActionListener{
    double inventarioInicial=0;
    private double costoII;
    private boolean peps;
    static private ArrayList <ArrayList <String>> kardex;
    double inventarioFinal;
    private double saldo;
    private double devoluciones=0;
    private ArrayList<Adquisicion> adquisiciones;
    //private int nroSal =1;
    double compras=0;
    final private JTable table;
    //static private JFrame frame;
    static private JButton printButton;
    static private JButton newadquision;
    static private JButton newsalida;
    static private JSpinner jNoP, jsNp;
    //static private JComboBox jcbP=new JComboBox(new String[]{"No","Si"});
    static private JButton devolucionProveedor;/*este es el q tenia*/
    static private JButton devolucionAlmacen;
    static private JButton importacionMaterial;
    String unidades="";
    double descuentos=0;
    private int numero=1, numeroA=1, maxOp=1;
    private ArrayList<Integer> idsEntregas= new ArrayList<Integer>();
    
    private int nOPa, id;//de poco uso pero importante 
    private ArrayList<ordenP> ordenes;
   
    private ArrayList <ArrayList<String>> cEntradas= new ArrayList<ArrayList<String>>();
    private ArrayList <ArrayList<String>> cSalidas= new ArrayList<ArrayList<String>>();
    
    //private int id;
    private ArrayList <ArrayList <String>> productos = new ArrayList<ArrayList<String>>();
    
    public Kardex ( double inventarioInicial, double costoII , boolean peps, String fechaII, final int nOP) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //frame = new JFrame();
        this.inventarioInicial = inventarioFinal = inventarioInicial ;
        this.costoII = costoII;
        this.peps = peps;
        this.nOPa = nOP;
        saldo = inventarioInicial*costoII;
        adquisiciones = new ArrayList<Adquisicion> ();
        adquisiciones.add (new Adquisicion (inventarioInicial , costoII) );
        /*aqui*/ordenes = new ArrayList<ordenP>();
        kardex = new ArrayList<ArrayList<String>>();
        ArrayList<String> a = new ArrayList<String>();
        a.add("1");a.add(fechaII);a.add("Inventario Inicial");a.add(""+costoII);a.add("");a.add("");a.add("" +inventarioInicial);a.add ( "" );a.add ( "");a.add ( ""+(redondea(inventarioInicial * costoII)));
        kardex.add ( a );
        table = new JTable();
        actualizar();
        table.setPreferredScrollableViewportSize(new Dimension(900, 200));
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        JPanel botones=new JPanel();
        botones.setAlignmentX(Component.CENTER_ALIGNMENT);
        newadquision=new JButton("Nueva Compra");
        newadquision.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             final JFrame jif=new JFrame("Compra de Material");
             jif.setIconImage(new ImageIcon("images/icono2.png").getImage());
             jif.setSize(320,210);
             jif.setResizable(false); 
             jif.setLocationRelativeTo(null);
             jif.setLayout(new GridLayout(6,2,5,5));
             jif.add(new JLabel("Fecha"));
             final WebDateField fecha = new WebDateField();
             fecha.setEditable(false);
             jif.add(fecha);
             jif.add(new JLabel("IVA"));
             final JComboBox jch=new JComboBox(new String[]{"NETO","IVA"});
             jif.add(jch);
             jif.add(new JLabel("Cantidad en ["+unidades+"]"));
             final JTextField jt=new JTextField();
             jif.add(jt);
             //jif.add(new JLabel("Descuento"));
             //final JTextField jt3=new JTextField("0");
             //jif.add(jt3);
             jif.add(new JLabel("Costo Unitario por ["+unidades+"]"));
             final JTextField jt1=new JTextField();
             jif.add(jt1);
             jif.add(new JLabel("Costo Total"));
             final JTextField jt2=new JTextField();
             jt2.setEditable(false);
             jif.add(jt2);
             
             JButton jb=new JButton("Aceptar");
             jif.add(jb);
             
             jt1.addKeyListener(new KeyListener() {
                    public void keyTyped(KeyEvent e) {}
                    public void keyPressed(KeyEvent e) {}
                    public void keyReleased(KeyEvent e) {
                     try { 
                           int a=Integer.parseInt(jt.getText());
                           double b=VistaKardes.convertir(jt1.getText());
                           jt2.setText(""+(a*b));
                        } catch (Exception ex) {}
                    }
             });
             jt.addKeyListener(new KeyListener() {
                    public void keyTyped(KeyEvent e) {}
                    public void keyPressed(KeyEvent e) {}
                    public void keyReleased(KeyEvent e) {
                     try { 
                    	 int a=Integer.parseInt(jt.getText());
                         double b=VistaKardes.convertir(jt1.getText());
                         jt2.setText(""+(a*b));
                        } catch (Exception ex) {}
                    }
                });
             jb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        double a=0;
                        double b=0;
                        double c=0;
                        int d=0;
                        try {
                            a=Integer.parseInt(jt.getText());
                            b=VistaKardes.convertir(jt1.getText());
                            c=VistaKardes.convertir(jt2.getText());
                            //d=Integer.parseInt(jt3.getText());
                            //Kardex.this.descuentos+=((d/100.0)*c);
                            if(jch.getSelectedIndex()==1){
                              //c-=((d/100.0)*c);
                              c*=0.87;
                              b=c/a;
                            }
                            else{  c-=((d/100.0)*c);
                                   b=c/a;
                            }
                        } catch (Exception ec) {
                            jif.dispose();
                            return;
                        }
                        Kardex.this.adquisicion(fecha.getText().replace('.', '/'), a, b,true);
                        jif.dispose();
                    }
                });
             JButton jb1=new JButton("Cancelar");
             jif.add(jb1);
             jb1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                      jif.dispose();
                    }
                });
             jif.setVisible(true);
            }
        });
        botones.add(newadquision);
        newsalida=new JButton("Nueva Entrega");
        newsalida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int n=Integer.parseInt(jNoP.getValue().toString());
            	nOPa = n;
            	if(nOPa==1)
            		salida_devolucion("Entrega Material");
            	else if(nOPa==2)
            		salida_devolucionP2("Entrega Material");
            	else if(nOPa==3)
            		salida_devolucionP3("Entrega Material");
            	else if(nOPa==4)
            		salida_devolucionP4("Entrega Material");
            	else
            		salida_devolucionP5("Entrega Material");
            }
        });
        devolucionProveedor=new JButton("Devolucion Proveedor");
        devolucionProveedor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             salida_devolucion("Devolucion");
            }
        });
        /*****/
        devolucionAlmacen= new JButton("Devolucion Almacen");
        devolucionAlmacen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             salida_devolucionAlmacen();
            }
        });
        importacionMaterial= new JButton("Importacion Material");
        importacionMaterial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 final JFrame jif=new JFrame("Importacion Material");
            	 int n=Integer.parseInt(jsNp.getValue().toString());
            	 maxOp=Math.max(maxOp, n);/*var q vas a usar*/
            	 jif.setIconImage(new ImageIcon("images/icono2.png").getImage());
            	 jif.setSize(300,120+(n*90));
                 jif.setLayout(new GridLayout(3+(n*3),2,5,5));
                 jif.setResizable(false); 
                 jif.setLocationRelativeTo(null);
                 
                 jif.add(new JLabel("Fecha"));
                 final WebDateField fecha = new WebDateField();
                 fecha.setEditable(false);
                 jif.add(fecha);
                 
                 jif.add(new JLabel("Nombre Producto 1"));
                 final JTextField jt1nom=new JTextField();
                 jif.add(jt1nom);
                 final JLabel jl1=new JLabel("Cantidad");
                 jif.add(jl1);
                 final JTextField jt2=new JTextField();
                 jif.add(jt2);
                 final JLabel jl2=new JLabel("P.u.");
                 jif.add(jl2);
                 final JTextField jt3=new JTextField();
                 jif.add(jt3);
                 
                 JTextField jt2nom=new JTextField();
                 JTextField jt4=new JTextField();
                 JTextField jt5=new JTextField();
                 JTextField jt3nom=new JTextField();
                 JTextField jt6=new JTextField();
                 JTextField jt7=new JTextField();
                 

                 JTextField jt4nom=new JTextField();
                 JTextField jt8=new JTextField();
                 JTextField jt9=new JTextField();
                 
                 if(n==2||n==3||n==4){
                	 jif.add(new JLabel("Nombre Producto 2"));
                     jif.add(jt2nom);
                     final JLabel jl3=new JLabel("Cantidad");
                     jif.add(jl3);                    
                     jif.add(jt4);
                     final JLabel jl4=new JLabel("P.u.");
                     jif.add(jl4);                    
                     jif.add(jt5);
                     jt2nom.addKeyListener(new KeyListener() {
                         public void keyTyped(KeyEvent e) {}
                         public void keyPressed(KeyEvent e) {}
                         public void keyReleased(KeyEvent e) {
                       		jl3.setText("Cantidad de "+jt2nom.getText());
                       		jl4.setText("P.u. de "+jt2nom.getText()+" [$us]");
                         }
                     });
                 }
                 if(n==3||n==4){                                     
                     jif.add(new JLabel("Nombre Producto 3"));                    
                     jif.add(jt3nom);
                     final JLabel jl5=new JLabel("Cantidad");
                     jif.add(jl5);                    
                     jif.add(jt6);
                     final JLabel jl6=new JLabel("P.u.");
                     jif.add(jl6);                    
                     jif.add(jt7);
                     jt3nom.addKeyListener(new KeyListener() {
                         public void keyTyped(KeyEvent e) {}
                         public void keyPressed(KeyEvent e) {}
                         public void keyReleased(KeyEvent e) {
                       		jl5.setText("Cantidad de "+jt3nom.getText());
                       		jl6.setText("P.u. de "+jt3nom.getText()+" [$us]");
                         }
                     });
                 }
                 if(n==4){                                     
                     jif.add(new JLabel("Nombre Producto 4"));                    
                     jif.add(jt4nom);
                     final JLabel jl7=new JLabel("Cantidad");
                     jif.add(jl7);                    
                     jif.add(jt8);
                     final JLabel jl8=new JLabel("P.u.");
                     jif.add(jl8);                    
                     jif.add(jt9);
                     jt4nom.addKeyListener(new KeyListener() {
                         public void keyTyped(KeyEvent e) {}
                         public void keyPressed(KeyEvent e) {}
                         public void keyReleased(KeyEvent e) {
                       		jl7.setText("Cantidad de "+jt4nom.getText());
                       		jl8.setText("P.u. de "+jt4nom.getText()+" [$us]");
                         }
                     });
                     
                 }
                 /*continuar*/
                 jif.add(new JLabel("Tipo de cambio Bs/$us"));
                 final JTextField jtTC=new JTextField("6.97");
                 jif.add(jtTC);
                 jt1nom.addKeyListener(new KeyListener() {
                     public void keyTyped(KeyEvent e) {}
                     public void keyPressed(KeyEvent e) {}
                     public void keyReleased(KeyEvent e) {
                   		jl1.setText("Cantidad de "+jt1nom.getText());
                   		jl2.setText("P.u. de "+jt1nom.getText()+" [$us]");
                     }
                 });

                 JButton jb=new JButton("Aceptar");
                 jif.add(jb);
                 jb.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e){
                    	 //try{
                    		 ArrayList<String> a1 = new ArrayList<String>();
                			 a1.add(jt1nom.getText());a1.add(jt2.getText());a1.add(jt3.getText());a1.add("");a1.add("");
                			 a1.add("");a1.add("");a1.add ("");a1.add ("");a1.add ("");a1.add ("");
                			 productos.add(a1);
/**aqui estas trabajando*/ 
                    		 if(n==2||n==3||n==4){
                    			 ArrayList<String> a2 = new ArrayList<String>();
                    			 a2.add(jt2nom.getText());a2.add(jt4.getText());a2.add(jt5.getText());a2.add("");a2.add("");
                    			 a2.add("");a2.add("");a2.add ("");a2.add ("");a2.add ("");a2.add ("");
                    			 productos.add(a2);
                    		 }
                    		 
                    		 if(n==3||n==4){
                    			 ArrayList<String> a3 = new ArrayList<String>();
                    			 a3.add(jt3nom.getText());a3.add(jt6.getText());a3.add(jt7.getText());a3.add("");a3.add("");
                    			 a3.add("");a3.add("");a3.add ("");a3.add ("");a3.add ("");a3.add ("");
                    			 productos.add(a3);
                    		 }

                    		 if(n==4){
                    			 ArrayList<String> a4 = new ArrayList<String>();
                    			 a4.add(jt4nom.getText());a4.add(jt8.getText());a4.add(jt9.getText());a4.add("");a4.add("");
                    			 a4.add("");a4.add("");a4.add ("");a4.add ("");a4.add ("");a4.add ("");
                    			 productos.add(a4);
                    		 }
                    		 
                    		 ArrayList<String> af = new ArrayList<String>();
                			 af.add("TOTAL");af.add("");af.add("");af.add("");af.add("");
                			 af.add("");af.add("");af.add ("");af.add ("");af.add ("");af.add ("");
                			 productos.add(af);
                			 
                    		 double tipCamb= VistaKardes.convertir(jtTC.getText());
                    		 String f= fecha.getText().replace('.', '/');
                    		 
                    		 final ImportacionMaterial impM= new ImportacionMaterial(n, f, tipCamb, productos, Kardex.this);
                    		 jif.dispose();
                    	 //} catch (Exception ex) {System.out.println("algo estas haciendo mal");}
                         jif.dispose();
                         /***Estito faltaba para hacer varias importacines*/productos = new ArrayList<ArrayList<String>>();
                       }
                 });
                 JButton jb2=new JButton("Canelar");
                 jif.add(jb2);
                 jb2.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e){
                       jif.dispose();
                     }
                 });
                 jif.setVisible(true);
            }
        });
        printButton = new JButton("Imprimir");
        printButton.addActionListener(this);
        botones.add(newsalida);
        final JLabel jlOP = new JLabel("N° O.P.");
        botones.add(jlOP);
		jNoP = new JSpinner(new SpinnerNumberModel(nOPa, 1, 5, 1));
        botones.add(jNoP);
        /**final JLabel jlP = new JLabel("Porcentaje");
        botones.add(jlP);
        botones.add(jcbP);*/
        botones.add(devolucionProveedor);
        //botones.add(jb);
        botones.add(devolucionAlmacen);
        botones.add(importacionMaterial);
        final JLabel jlNp = new JLabel("N° Productos");
        botones.add(jlNp);
		jsNp = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
		botones.add(jsNp);
        /**final JButton jbDevol=new JButton("Eliminar Ultimo");
        botones.add(jbDevol);
        jbDevol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		eliminarUltimo();
            	}
            });*/
        botones.add(printButton);
        
        /*para concentracion entradas*/
        JButton jbtCe = new JButton("Mostrar Concetracion de Entradas");
        botones.add(jbtCe);
        jbtCe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/*ArrayList<ArrayList<String>> cEntradasAux = new ArrayList<ArrayList<String>>();
            	cEntradasAux= cEntradas;*/
            	new concentracionEntradas(cEntradas);
        		//cEntradasAux = new ArrayList<ArrayList<String>>();
        	}
        });
        JButton jbtCs = new JButton("Mostrar Concetracion de Salidas");
        botones.add(jbtCs);
        jbtCs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//llenarcSalidas();
        		//new concentracionSalidas(cSalidas, maxOp);
        	}
        });
        /*si o si al final tiene q estar*/
        this.add(botones);
    }
    public void salida_devolucion(final String res){
            final JFrame jif=new JFrame(res);
            jif.setIconImage(new ImageIcon("images/icono2.png").getImage());
             jif.setSize(300,120);
             jif.setResizable(false);
             jif.setLocationRelativeTo(null);
             jif.setLayout(new GridLayout(3,2,5,5));
             jif.add(new JLabel("Fecha"));
             final WebDateField fecha = new WebDateField();
             fecha.setEditable(false);
             jif.add(fecha);
             jif.add(new JLabel("Cantidad ["+unidades+"]"));
             final JTextField jt=new JTextField();
             jif.add(jt);
             JButton jb=new JButton("Aceptar");
             jif.add(jb);
             jb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	double a=0;
                        numero=numero+1;
                        idsEntregas.add(numero);
                        String f=fecha.getText().replace('.', '/'); 
                        try {
                            a=VistaKardes.convertir(jt.getText());
                        } catch (Exception ec) {
                            jif.dispose();
                             return;
                        }
                        if(res.equals("Devolucion")){ 
                        	Kardex.this.devolucion(f, a);
                        }
                        else {
                        	ordenP op=new ordenP(f);
                        	Kardex.this.salida(f,"Entrega  O.P. unica", a);
                        	double pu= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                        	op.agregarOp("O.P. unica",pu);
                        	ordenes.add(op);
                        }
                        jif.dispose();
                    }
                });
             JButton jb1=new JButton("Cancelar");
             jif.add(jb1);
             jb1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                      jif.dispose();
                    }
                });
             jif.setVisible(true);
    }
    /**/
    public void salida_devolucionP2(final String res){
        final JFrame jif=new JFrame(res);
        jif.setIconImage(new ImageIcon("images/icono2.png").getImage());
         jif.setSize(300,180);
         jif.setResizable(false);
         jif.setLocationRelativeTo(null);
         jif.setLayout(new GridLayout(5,2,5,5));
         jif.add(new JLabel("Fecha"));
         final WebDateField fecha = new WebDateField();
         fecha.setEditable(false);
         jif.add(fecha);
         jif.add(new JLabel("O.P. # 1["+unidades+"]"));
         final JTextField jt=new JTextField();
         jif.add(jt);
         jif.add(new JLabel("O.P. # 2["+unidades+"]"));
         final JTextField jt2=new JTextField();
         jif.add(jt2);
         jif.add(new JLabel("Total"));
         final JTextField jt6=new JTextField();
         jt6.setEditable(false);
         jif.add(jt6);
         JButton jb=new JButton("Aceptar");
         jif.add(jb);
         /**/
         jt.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    jt6.setText(""+(a+b));
                 } catch (Exception ex) {}
             }
         });
         jt2.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    jt6.setText(""+(a+b));
                 } catch (Exception ex) {}
             }
         });/**/
         jb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double a=0, b=0;
                	String f=fecha.getText().replace('.','/');
                    numero=numero+1;
                    idsEntregas.add(numero);
                    try {
                        a=VistaKardes.convertir(jt.getText());
                        b=VistaKardes.convertir(jt2.getText());
                    } catch (Exception ec) {
                        jif.dispose();
                         return;
                    }
                    if(res.equals("Devolucion")){ 
                    	Kardex.this.devolucion(f, a);
                    	//Kardex.this.devolucion(f, b);
                    }
                    else {
                    	ordenP op=new ordenP(f);
                    	Kardex.this.salida(f,"Entrega  O.P. #1", a);
                    	double pu1= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #1",pu1);

                    	Kardex.this.salida(f,"             O.P. #2", b);
                    	double pu2= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #2", pu2);
                    	ordenes.add(op);
                    	}
                    jif.dispose();
                }
            });
         JButton jb1=new JButton("Cancelar");
         jif.add(jb1);
         jb1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                  jif.dispose();
                }
            });
         jif.setVisible(true);
    }
    public void salida_devolucionP3(final String res){
        final JFrame jif=new JFrame(res);
        jif.setIconImage(new ImageIcon("images/icono2.png").getImage());
         jif.setSize(300,210);
         jif.setResizable(false);
         jif.setLocationRelativeTo(null);
         jif.setLayout(new GridLayout(6,2,5,5));
         jif.add(new JLabel("Fecha"));
         final WebDateField fecha = new WebDateField();
         fecha.setEditable(false);
         jif.add(fecha);
         jif.add(new JLabel("O.P. # 1["+unidades+"]"));
         final JTextField jt=new JTextField();
         jif.add(jt);
         jif.add(new JLabel("O.P. # 2["+unidades+"]"));
         final JTextField jt2=new JTextField();
         jif.add(jt2);
         jif.add(new JLabel("O.P. # 3["+unidades+"]"));
         final JTextField jt3=new JTextField();
         jif.add(jt3);
         jif.add(new JLabel("Total"));
         final JTextField jt6=new JTextField();
         jt6.setEditable(false);
         jif.add(jt6);
         JButton jb=new JButton("Aceptar");
         jif.add(jb);
         /**/
         jt.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    jt6.setText(""+(a+b+c));
                 } catch (Exception ex) {}
             }
         });
         jt2.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    jt6.setText(""+(a+b+c));
                 } catch (Exception ex) {}
             }
         });
         jt3.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    jt6.setText(""+(a+b+c));
                 } catch (Exception ex) {}
             }
         });/**/
         jb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double a=0, b=0, c=0;
                    numero=numero+1;
                    idsEntregas.add(numero);
                    String f=fecha.getText().replace('.', '/');
                    try {
                        a=VistaKardes.convertir(jt.getText());
                        b=VistaKardes.convertir(jt2.getText());
                        c=VistaKardes.convertir(jt3.getText());
                    } catch (Exception ec) {
                        jif.dispose();
                         return;
                    }
                    if(res.equals("Devolucion")){ 
                    	Kardex.this.devolucion(f, a);
                    	/*Kardex.this.devolucion(f, b);
                    	Kardex.this.devolucion(f, c);*/
                    }
                    else {
                    	ordenP op=new ordenP(f);
                    	Kardex.this.salida(f,"Entrega  O.P. #1", a);
                    	double pu1= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #1",pu1);
                    	
                    	Kardex.this.salida(f,"             O.P. #2", b);
                    	double pu2= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #2",pu2);
                    	
                    	Kardex.this.salida(f,"             O.P. #3", c);
                    	double pu3= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #3",pu3);
                    	ordenes.add(op);                    	}
                    jif.dispose();
                }
            });
         JButton jb1=new JButton("Cancelar");
         jif.add(jb1);
         jb1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                  jif.dispose();
                }
            });
         jif.setVisible(true);
    }
    public void salida_devolucionP4(final String res){
        final JFrame jif=new JFrame(res);
        jif.setIconImage(new ImageIcon("images/icono2.png").getImage());
         jif.setSize(300,240);
         jif.setResizable(false);
         jif.setLocationRelativeTo(null);
         jif.setLayout(new GridLayout(7,2,5,5));
         jif.add(new JLabel("Fecha"));
         final WebDateField fecha = new WebDateField();
         fecha.setEditable(false);
         jif.add(fecha);
         jif.add(new JLabel("O.P. # 1["+unidades+"]"));
         final JTextField jt=new JTextField();
         jif.add(jt);
         jif.add(new JLabel("O.P. # 2["+unidades+"]"));
         final JTextField jt2=new JTextField();
         jif.add(jt2);
         jif.add(new JLabel("O.P. # 3["+unidades+"]"));
         final JTextField jt3=new JTextField();
         jif.add(jt3);
         jif.add(new JLabel("O.P. # 4["+unidades+"]"));
         final JTextField jt4=new JTextField();
         jif.add(jt4);
         jif.add(new JLabel("Total"));
         final JTextField jt6=new JTextField();
         jt6.setEditable(false);
         jif.add(jt6);
         JButton jb=new JButton("Aceptar");
         jif.add(jb);
         /**/
         jt.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    double d=VistaKardes.convertir(jt4.getText());
                    jt6.setText(""+(a+b+c+d));
                 } catch (Exception ex) {}
             }
         });
         jt2.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    double d=VistaKardes.convertir(jt4.getText());
                    jt6.setText(""+(a+b+c+d));
                 } catch (Exception ex) {}
             }
         });
         jt3.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    double d=VistaKardes.convertir(jt4.getText());
                    jt6.setText(""+(a+b+c+d));
                 } catch (Exception ex) {}
             }
         });
         jt4.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    double d=VistaKardes.convertir(jt4.getText());
                    jt6.setText(""+(a+b+c+d));
                 } catch (Exception ex) {}
             }
         });
         jb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double a=0, b=0, c=0, d=0;
                    numero=numero+1;
                    idsEntregas.add(numero);
                    String f=fecha.getText().replace('.', '/');
                    try {
                        a=VistaKardes.convertir(jt.getText());
                        b=VistaKardes.convertir(jt2.getText());
                        c=VistaKardes.convertir(jt3.getText());
                        d=VistaKardes.convertir(jt4.getText());
                    } catch (Exception ec) {
                        jif.dispose();
                         return;
                    }
                    if(res.equals("Devolucion")){ 
                    	Kardex.this.devolucion(f, a);
                    	/*Kardex.this.devolucion(f, b);
                    	Kardex.this.devolucion(f, c);
                    	Kardex.this.devolucion(f, d);*/
                    }
                    else {
                    	ordenP op=new ordenP(f);
                    	Kardex.this.salida(f,"Entrega  O.P. #1", a);
                    	double pu1= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #1",pu1);

                    	Kardex.this.salida(f,"             O.P. #2", b);
                    	double pu2= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #2",pu2);
                    	
                    	Kardex.this.salida(f,"             O.P. #3", c);
                    	double pu3= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #3",pu3);
                    	
                    	Kardex.this.salida(f,"             O.P. #4", d);
                    	double pu4= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #4",pu4);
                    	ordenes.add(op);                    	}
                    jif.dispose();
                }
            });
         JButton jb1=new JButton("Cancelar");
         jif.add(jb1);
         jb1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                  jif.dispose();
                }
            });
         jif.setVisible(true);
    }
    public void salida_devolucionP5(final String res){
        final JFrame jif=new JFrame(res);
        jif.setIconImage(new ImageIcon("images/icono2.png").getImage());
         jif.setSize(300,270);
         jif.setResizable(false);
         jif.setLocationRelativeTo(null);
         jif.setLayout(new GridLayout(8,2,5,5));
         jif.add(new JLabel("Fecha"));
         final WebDateField fecha = new WebDateField();
         fecha.setEditable(false);
         jif.add(fecha);
         jif.add(new JLabel("O.P. # 1["+unidades+"]"));
         final JTextField jt=new JTextField();
         jif.add(jt);
         jif.add(new JLabel("O.P. # 2["+unidades+"]"));
         final JTextField jt2=new JTextField();
         jif.add(jt2);
         jif.add(new JLabel("O.P. # 3["+unidades+"]"));
         final JTextField jt3=new JTextField();
         jif.add(jt3);
         jif.add(new JLabel("O.P. # 4["+unidades+"]"));
         final JTextField jt4=new JTextField();
         jif.add(jt4);
         jif.add(new JLabel("O.P. # 5["+unidades+"]"));
         final JTextField jt5=new JTextField();
         jif.add(jt5);
         jif.add(new JLabel("Total"));
         final JTextField jt6=new JTextField();
         jt6.setEditable(false);
         jif.add(jt6);
         JButton jb=new JButton("Aceptar");
         jif.add(jb);
         /**/
         jt.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    double d=VistaKardes.convertir(jt4.getText());
                    double e5=VistaKardes.convertir(jt5.getText());
                    //if(a>0||b>0||c>0||d>0||e5>0)
                    	jt6.setText(""+(a+b+c+d+e5));
                 } catch (Exception ex) {}
             }
         });
         jt2.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    double d=VistaKardes.convertir(jt4.getText());
                    double e5=VistaKardes.convertir(jt5.getText());
                    //if(a>0||b>0||c>0||d>0||e5>0)
                    	jt6.setText(""+(a+b+c+d+e5));
                 } catch (Exception ex) {}
             }
         });
         jt3.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    double d=VistaKardes.convertir(jt4.getText());
                    double e5=VistaKardes.convertir(jt5.getText());
                    //if(a>0||b>0||c>0||d>0||e5>0)
                    	jt6.setText(""+(a+b+c+d+e5));
                 } catch (Exception ex) {}
             }
         });
         jt4.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    double d=VistaKardes.convertir(jt4.getText());
                    double e5=VistaKardes.convertir(jt5.getText());
                    //if(a>0||b>0||c>0||d>0||e5>0)
                    	jt6.setText(""+(a+b+c+d+e5));
                 } catch (Exception ex) {}
             }
         });
         jt5.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    double d=VistaKardes.convertir(jt4.getText());
                    double e5=VistaKardes.convertir(jt5.getText());
                    //if(a>0||b>0||c>0||d>0||e5>0)
                    	jt6.setText(""+(a+b+c+d+e5));
                 } catch (Exception ex) {}
             }
         });
         /**jt6.addKeyListener(new KeyListener() {
             public void keyTyped(KeyEvent e) {}
             public void keyPressed(KeyEvent e) {}
             public void keyReleased(KeyEvent e) {
              try { 
                    double a=VistaKardes.convertir(jt.getText());
                    double b=VistaKardes.convertir(jt2.getText());
                    double c=VistaKardes.convertir(jt3.getText());
                    double d=VistaKardes.convertir(jt4.getText());
                    double e5=VistaKardes.convertir(jt5.getText());
                    double f=VistaKardes.convertir(jt6.getText());
                	if(a<=0)
                		jt.setText(""+(f-(b+c+d+e5)));
                	else if(b<=0)
                		jt2.setText(""+(f-(a+c+d+e5)));
                	else if(c<=0)
                		jt3.setText(""+(f-(a+b+d+e5)));
                	else if(d<=0)
                		jt4.setText(""+(f-(b+c+a+e5)));
                	else if(e5<=0)
                		jt5.setText(""+(f-(b+c+d+a)));
                 } catch (Exception ex) {}
             }
         });/**/
         jb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double a=0, b=0, c=0;
                    double d=0, e5=0;
                    numero=numero+1;
                    idsEntregas.add(numero);
                    String f=fecha.getText().replace('.', '/');
                    try {
                        a=VistaKardes.convertir(jt.getText());
                        b=VistaKardes.convertir(jt2.getText());
                        c=VistaKardes.convertir(jt3.getText());
                        d=VistaKardes.convertir(jt4.getText());
                        e5=VistaKardes.convertir(jt5.getText());
                    } catch (Exception ec) {
                        jif.dispose();
                         return;
                    }
                    if(res.equals("Devolucion")){ 
                    	Kardex.this.devolucion(f, a);
                    	/*Kardex.this.devolucion(f, b);
                    	Kardex.this.devolucion(f, c);
                    	Kardex.this.devolucion(f, d);
                    	Kardex.this.devolucion(f, e5);*/
                    }
                    else {
                    	ordenP op= new ordenP(f);
                    	Kardex.this.salida(f,"Entrega  O.P. #1", a);
                    	double pu1= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #1",pu1);
                    	
                    	Kardex.this.salida(f,"             O.P. #2", b);
                    	double pu2= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #2",pu2);
                    	
                    	Kardex.this.salida(f,"             O.P. #3", c);
                    	double pu3= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #3",pu3);
                    	
                    	Kardex.this.salida(f,"             O.P. #4", d);
                    	double pu4= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #4",pu4);
                    	
                    	Kardex.this.salida(f,"             O.P. #5", e5);
                    	double pu5= Double.parseDouble(kardex.get(kardex.size()-1).get(3));
                    	op.agregarOp("O.P. #5",pu5);
                    	ordenes.add(op);                    	}
                    jif.dispose();
                }
            });
         JButton jb1=new JButton("Cancelar");
         jif.add(jb1);
         jb1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                  jif.dispose();
                }
            });
         jif.setVisible(true);
    }
    /**/
    public void actionPerformed(java.awt.event.ActionEvent ignore) {
        MessageFormat header = new MessageFormat("Page {0,number,integer}");
        try {
            table.print(JTable.PrintMode.FIT_WIDTH, header, null);
        } catch (java.awt.print.PrinterException e) {
            System.err.format("Cannot print %s%n", e.getMessage());
        }
    }
    
    public void adquisicion(String fecha, double cantidad, double costo, boolean Adquisicion){    
        ArrayList<String> a = new ArrayList<String>();
 
        adquisiciones.add (new Adquisicion (cantidad , costo) );
        inventarioFinal += cantidad;
        double varN=redondea(cantidad * costo);
        saldo = redondea(saldo+varN);
        compras = redondea(compras+varN);
        numero=numero+1;
        String detalle="";
        if(Adquisicion){
        	detalle=numeroA+"° Compra de Material";
        	numeroA+=1;
        }else
        	detalle="Devolucion Almacen";
        a.add(""+numero);a.add (fecha);a.add (detalle);a.add(""+costo);a.add(""+cantidad);a.add("");a.add(""+inventarioFinal);a.add(""+varN);a.add("");a.add(""+saldo);
        kardex.add ( a );
        actualizar();
        /*para la cEntradas*/
        if(Adquisicion)
        	addEntrada(fecha, detalle, ""+costo, ""+cantidad, ""+varN);
    }
    /*faltaba*/
    public void importacion(String fecha, double cantidad, double costo){
        ArrayList<String> a = new ArrayList<String>();
        
        adquisiciones.add (new Adquisicion (cantidad , costo) );
        inventarioFinal += cantidad;
        double valor=redondea(cantidad * costo);
        saldo += valor;
        compras += valor;
        numero=numero+1;
        String detalle="Importacion Material";
        a.add(""+numero);a.add (fecha);a.add (detalle);a.add(""+costo);a.add(""+cantidad);a.add("");a.add(""+inventarioFinal);
        a.add(""+valor);a.add("");a.add(""+saldo);
        kardex.add ( a );
        actualizar();
        /*para la cEntradas*/
        addEntrada(fecha, detalle, ""+costo, ""+cantidad, ""+redondea(costo*cantidad));
    }
    public void addEntrada(String fecha, String detalle, String pu, String q, String valor){
        ArrayList<String> cE = new ArrayList<String>();
        cE.add(fecha);cE.add(detalle);cE.add(pu);cE.add(q);cE.add(valor);
        cEntradas.add(cE);
    }
    /**public void eliminarUltimo(){
    	if(kardex.size()>1){
    		double restII = VistaKardes.convertir(kardex.get(kardex.size()-2).get(6));
    		double resSal = VistaKardes.convertir(kardex.get(kardex.size()-2).get(9));
    		//if(sale){
    			inventarioFinal =(long)restII;//cantidad;
    			saldo = resSal;
    		kardex.remove(kardex.size()-1);
    		//adquisiciones.remove(adquisiciones.size()-1);
    		actualizar();
    	}
    }*/
    
    /**REVISAR AQUI*/
    public void salida1(String fecha,int nOP,String nom, double cantidad){
        int cont = 0;
        double cantidadAux=cantidad;
        Adquisicion ad = null;
        while(cont < adquisiciones.size () && cantidad > 0)
            {if(peps){
                ad = adquisiciones.get(cont);
              }
                  else{
                ad = adquisiciones.get(adquisiciones.size ()-(cont+1));
               }
                double disp = ad.cantidad;
                ArrayList<String> a = new ArrayList<String>();
                if(disp - cantidad >= 0){
                    ad.cantidad -= cantidad;
                    inventarioFinal -= cantidad;
                    saldo -= redondea(cantidad * ad.costo);
                    saldo= redondea(saldo);/***ultima linea*/
                    /**el error se econtraba aqui -4.547473508864641E-12 dentro de este if*/
                    /**para corregir la falta de cero*/if (saldo<0) saldo=0;
                    a.add(""+numero);a.add(fecha);a.add(nom);a.add (""+ad.costo);a.add ("");a.add(""+cantidad);a.add (""+inventarioFinal);a.add ("");a.add (""+(redondea(cantidad * ad.costo)));a.add (""+saldo);  
                    cantidad = 0;
                }
                else
                {
                    inventarioFinal -= disp;
                    saldo -= redondea(disp * ad.costo);
                    saldo= redondea(saldo);/***ultima linea*/
                    /**para corregir la falta de cero*/if (saldo<0) saldo=0;
                    a.add(""+numero);a.add(fecha);a.add(nom);a.add (""+ad.costo);a.add ("");a.add(""+disp);a.add (""+inventarioFinal);a.add ("");a.add (""+(redondea(disp* ad.costo)));a.add (""+saldo);
                    cantidad -= disp; 
                    ad.cantidad = 0;
             }
             kardex.add(a);
             cont++;
             //eliminarVacios();
             //System.out.println("por q no entra");
             actualizar();
             if(nom.equals("Devolucion Proveedor")){
             	/*para la cEntradas*/
                 addEntrada(fecha, nom, ""+ad.costo, ""+cantidadAux, ""+redondea(disp* ad.costo));
             }
      }
    }
    int Ndevolucion=0;
    public void salida(String fecha,String dato,double cantidad){
      salida1(fecha,nOPa,dato,cantidad);
      //nroSal++;
    }
    public void devolucion(String fecha,double cantidad){
        Ndevolucion++;
        devoluciones+=cantidad;
        salida1(fecha,Ndevolucion,"Devolucion Proveedor",cantidad);
    }
    public void actualizar()
    {
    	eliminarVacios();
        Object []   head = {"Nº", "FECHA", "DETALLE", "P.U.", "ENTRADA", "SALIDA","SALDO", "INGRESO", "EGRESO", "SALDO"};
        Object [][] alfa = new Object[kardex.size()][10];
        for(int i = 0; i < kardex.size(); i++)
        {
            alfa [i] = kardex.get(i).toArray();   
        }
        table.setModel(new javax.swing.table.DefaultTableModel(alfa, head){
            public boolean isCellEditable(int rowIndex , int columnIndex) {
                return false; 
            }
        });
        int anchos [] = {55,150,300,103,200,200,200,200,200,200}; 
        table.setFillsViewportHeight(true);
        table.getTableHeader().setForeground(Color.blue);
        table.getTableHeader().setBackground(Color.green);
        table.getTableHeader().setFont(new Font("Arial",2,12));
        for(int i=0; i<10; i++){
        	table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
    public void salida_devolucionAlmacen(){
        final JFrame jif=new JFrame("Devoucion Almacen");
        jif.setIconImage(new ImageIcon("images/icono2.png").getImage());
        jif.setSize(300,180);
        jif.setResizable(false);
        jif.setLocationRelativeTo(null);
        jif.setLayout(new GridLayout(5,2,5,5));
        jif.add(new JLabel("Fecha"));
        final WebDateField fecha = new WebDateField();
        fecha.setEditable(false);
        jif.add(fecha);
        jif.add(new JLabel("Proviene de la fecha"));
        final JComboBox jcb=new JComboBox(obtenerFechasDOrdnes());
        jif.add(jcb);
        jif.add(new JLabel("De la orden"));
        JComboBox jcb2=new JComboBox(new String[]{"",""});
        jif.add(jcb2);
        jif.add(new JLabel("Cantidad ["+unidades+"]"));
        final JTextField jt=new JTextField();
        jif.add(jt);
        JButton jb=new JButton("Aceptar");
        jif.add(jb);
        jcb.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
            	   id=jcb.getSelectedIndex();
            	   //System.out.println(id);
            	   jcb2.setModel(new DefaultComboBoxModel(ordenes.get(id).getOrdenes().toArray())); 
               }
        });
        jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String f=fecha.getText().replace('.', '/');
					double cantidad=VistaKardes.convertir(jt.getText());
					int identificador=jcb2.getSelectedIndex();//ERA -1 solcuion a 1 op
					//System.out.println(identificador);
					double costo=ordenes.get(id).getPreciosU().get(identificador); 
					adquisicion(f, cantidad, costo,false);
				} catch (Exception e) {}
				jif.dispose();
			}
		});
        JButton jb1=new JButton("Cancelar");
        jif.add(jb1);
        jb1.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e){
                 jif.dispose();
               }
           });
        jif.setVisible(true);
    }
    /****ultimo trabajando**///cSalidas idsEntregas
    public void llenarcSalidas(){
    	for (int i=0; i<idsEntregas.size()-1;i++){
    		for(int k=0; k<kardex.size()-1;k++){
    			int pos=Integer.parseInt(kardex.get(k).get(0));
    			if(idsEntregas.get(i)==pos){
    				cSalidas.add(kardex.get(k));
    				System.out.println(kardex.get(k));/**por verse*/
    			}
    		}
    	}
    	for(int j=0; j<kardex.size()-1;j++){
    		String comp=kardex.get(j).get(1);
    		if(comp.equals("Devolucion Proveedor")){
    			cSalidas.add(kardex.get(j));
    		}
    	}
    }
    public String[] obtenerFechasDOrdnes(){
    	String[] res= new String[ordenes.size()]; 
    	for(int i=0;i<ordenes.size();i++){
    		res[i]=ordenes.get(i).getFecha();
    	}
    	return res;
    }
    public String[] obtenerUltimosPu(ArrayList<String> ordns){
    	String[] res=new String[ordns.size()];
    	for(int i=0;i<ordns.size();i++){
    		res[i]=ordns.get(i);
    	}
    	return res;
    }
    static public double redondea(double n){
		String nu=""+n;
        double res=Double.parseDouble(nu);
        int canDecimales=(nu.substring(nu.indexOf(".")+1)).length(); 
        //System.out.println(""+canDecimales);
        if(canDecimales>3){
            nu=nu.substring(0, nu.indexOf(".")+5);
            res=Double.parseDouble(nu);
            res=redondeaB(res);
        }
        return res;
    }
	static public double redondeaB(double numero){
	      String ver=""+numero;
	      double resultado=numero; int decimales=3;
	      int canDecimales=(ver.substring(ver.indexOf(".")+1)).length(); 
	      if(canDecimales>decimales){
	    	  BigDecimal res;
	    	  res = new BigDecimal(numero).setScale(decimales, BigDecimal.ROUND_UP);
	    	  resultado = res.doubleValue();
	      }
	      return resultado; 
	}
    public void eliminarVacios(){
    	for(int i=0; i<kardex.size(); i++){
    		String verifica= kardex.get(i).get(5);
    		if(verifica.equals("0.0")){
    			kardex.remove(i);
    		}
    	}
    }
}
class Adquisicion
    {
        double cantidad;
        double costo;
        public Adquisicion ( double cantidad , double costo ) {
            this.cantidad = cantidad;
            this.costo = costo;
        }
    }

