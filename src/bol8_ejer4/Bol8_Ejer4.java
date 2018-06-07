/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bol8_ejer4;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author ero
 */
class Principal extends JFrame implements ActionListener {

    JCheckBox numero;
    JCheckBox[] arrayNumeros;
    int x = 10;
    int y = 5;
    int contSeleccionados = 0;
    JButton jugar;
    ArrayList<Integer> numerosAleatorios;
    ArrayList<Integer> numerosSeleccionados;
    ArrayList<Integer> numerosAcertados;
    JLabel numeroAciertos;
    JLabel aciertos;
    JLabel[] arrayAciertosErrores;
    int xx = 5;

    public Principal() {
        super("Lotería primitiva");
        this.setLayout(null);

        arrayNumeros = new JCheckBox[49];
        for (int i = 0; i < 49; i++) {
            numero = crearNumero(i + 1);
            numero.setLocation(x, y);
            arrayNumeros[i] = numero;
            if ((i + 1) % 7 == 0) {
                y += 30;
                x = 10;
            } else {
                x += 50;
            }
            this.add(numero);
        }
        jugar = new JButton("Jugar");
        jugar.setSize(jugar.getPreferredSize());
        jugar.setLocation(150, y + 30);
        jugar.setEnabled(false);
        jugar.addActionListener(this);
        this.add(jugar);

        numeroAciertos = new JLabel();
        numeroAciertos.setLocation(25, jugar.getY() + 50);
        this.add(numeroAciertos);

        numerosSeleccionados = new ArrayList<>();
        numerosAleatorios = new ArrayList<>();
    }

    private JCheckBox crearNumero(int nombre) {
        JCheckBox chbNumero = new JCheckBox(nombre + "");
        chbNumero.setName(nombre + "");
        chbNumero.setSize(chbNumero.getPreferredSize());
        chbNumero.addMouseListener(new MouseHandler());
        return chbNumero;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < arrayNumeros.length; i++) {
            arrayNumeros[i].setEnabled(false);
            arrayNumeros[i].remove(this);
        }
        arrayAciertosErrores = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            aciertos = new JLabel();
            aciertos.setSize(20, 20);
            aciertos.setLocation(xx, numeroAciertos.getY() + 50);
            arrayAciertosErrores[i] = aciertos;
            this.add(aciertos);
            xx += 30;
        }
        numerosAcertados = new ArrayList<>();
        numerosAleatorios = numerosAleatorios(numerosAleatorios);
        comprobarAciertos(numerosSeleccionados, numerosAleatorios);
        for (Integer numero : numerosSeleccionados) {
            System.out.print(numero + " ");
        }
        System.out.println("");
        for (Integer numero : numerosAleatorios) {
            System.out.print(numero + " ");
        }
        System.out.println("");
        numerosAcertados.removeAll(numerosAcertados);
        numerosAleatorios.removeAll(numerosAleatorios);
        numerosSeleccionados.removeAll(numerosSeleccionados);
        contSeleccionados=0;
        for (int i = 0; i < arrayNumeros.length; i++) {
            arrayNumeros[i].setEnabled(true);
            arrayNumeros[i].setSelected(false);
        }
        jugar.setEnabled(false);
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            JCheckBox seleccionado = (JCheckBox) e.getComponent();
            if (seleccionado.isSelected()) {
//                System.out.println("seleccionado");
                contSeleccionados++;
                numerosSeleccionados.add(Integer.parseInt(seleccionado.getName()));
            } else {
//                System.out.println("desseleccionado");
                contSeleccionados--;
                int numero = Integer.parseInt(seleccionado.getName());
                numerosSeleccionados.remove(numerosSeleccionados.indexOf(numero));
            }
//            System.out.println(contSeleccionados);
            if (contSeleccionados >= 6) {
                jugar.setEnabled(true);
            } else {
                jugar.setEnabled(false);
            }
            if (contSeleccionados > 6) {
                seleccionado.setSelected(false);
                JOptionPane.showMessageDialog(null, "Solo puedes marcar 6 numeros");
                JOptionPane.showMessageDialog(null, "Se ha desmarcado " + seleccionado.getName());
                int numero = Integer.parseInt(seleccionado.getName());
                numerosSeleccionados.remove(numerosSeleccionados.indexOf(numero));
                contSeleccionados--;
            }
        }
    }

    public ArrayList numerosAleatorios(ArrayList<Integer> aleatorios) {
        aleatorios.removeAll(aleatorios);
        while (aleatorios.size() < 6) {
            int aleatorio = (int) (Math.random() * 49 + 1);
            if (comprobarAleatorio(aleatorio, aleatorios)) {

            } else {
                aleatorios.add(aleatorio);
            }

        }

        return aleatorios;
    }

    private boolean comprobarAleatorio(int aleatorio, ArrayList<Integer> aleatorios) {
        for (int i = 0; i < aleatorios.size(); i++) {
            if (aleatorio == aleatorios.get(i)) {
                return true;
            }
        }
        return false;
    }

    public void comprobarAciertos(ArrayList<Integer> Jugador, ArrayList<Integer> aleatorios) {
        int cont = 0;
        for (int i = 0; i < Jugador.size(); i++) {
            for (int j = 0; j < aleatorios.size(); j++) {
                if (Jugador.get(i).equals(aleatorios.get(j))) {
                    System.out.println("igual");
                    System.out.println(j);
                    arrayAciertosErrores[i].setText(Jugador.get(i) + "");
                    arrayAciertosErrores[i].setForeground(Color.GREEN);
                    System.out.println("Has acertado el numero " + Jugador.get(i));
                    cont++;
                } else {
                    arrayAciertosErrores[i].setText(Jugador.get(i) + "");
                    arrayAciertosErrores[i].setForeground(Color.red);
                }
            }
        }
        numeroAciertos.setText("Has acertado " + cont);
        numeroAciertos.setSize(numeroAciertos.getPreferredSize());
        System.out.println("Has tenido " + cont + " aciertos");
    }

}

public class Bol8_Ejer4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Principal p = new Principal();
        p.setSize(370, 500);
        p.setLocationRelativeTo(null);
        p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setVisible(true);
    }

}
