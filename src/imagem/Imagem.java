package imagem;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;



public class Imagem extends JFrame {

    private JDesktopPane theDesktop;
    private int[][] matrizRedImagem, matrizGreenImagem, matrizBlueImagem;
    JFileChooser fileChooser = new JFileChooser();
    String path = "";

    public void aplicarEscalaCinza() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();
        int matrizCinza[][] = new int[matrizVermelha.length][matrizVermelha[0].length];

        for (int pixelHorizontal = 0; pixelHorizontal < matrizVermelha.length; pixelHorizontal++) {
            for (int pixelVertical = 0; pixelVertical < matrizVermelha[pixelHorizontal].length; pixelVertical++) {
                matrizCinza[pixelHorizontal][pixelVertical] = (matrizVermelha[pixelHorizontal][pixelVertical] + matrizVerde[pixelHorizontal][pixelVertical] + matrizAzul[pixelHorizontal][pixelVertical]) / 3;
            }
        }

        gerarImagem(matrizCinza, matrizCinza, matrizCinza);
    }

    public void aplicarImagemBinaria() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();
        int matrizBinaria[][] = new int[matrizVermelha.length][matrizVermelha[0].length];
        int limite = 167;

        for (int i = 0; i < matrizVermelha.length; i++) {
            for (int j = 0; j < matrizVermelha[i].length; j++) {
                matrizBinaria[i][j] = (matrizVermelha[i][j] + matrizVerde[i][j] + matrizAzul[i][j]) / 3;
                if (matrizBinaria[i][j] <= limite) {
                    matrizBinaria[i][j] = 255;
                } else {
                    matrizBinaria[i][j] = 0;
                }
            }
        }

        gerarImagem(matrizBinaria, matrizBinaria, matrizBinaria);
    }

    public void aplicarImagemNegativa() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        for (int i = 0; i < matrizVermelha.length; i++) {
            for (int j = 0; j < matrizVermelha[i].length; j++) {
                matrizVermelha[i][j] = 255 - matrizVermelha[i][j];
                matrizVerde[i][j] = 255 - matrizVerde[i][j];
                matrizAzul[i][j] = 255 - matrizAzul[i][j];
            }
        }

        gerarImagem(matrizVermelha, matrizVerde, matrizAzul);
    }

    public void aplicarCorDominante() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        for (int i = 0; i < matrizVermelha.length; i++) {
            for (int j = 0; j < matrizVermelha[i].length; j++) {
                if (matrizVermelha[i][j] > matrizVerde[i][j] && matrizVermelha[i][j] > matrizAzul[i][j]) {
                    matrizVermelha[i][j] = 255;
                    matrizVerde[i][j] = 0;
                    matrizAzul[i][j] = 0;
                }
                if (matrizVerde[i][j] > matrizVermelha[i][j] && matrizVerde[i][j] > matrizAzul[i][j]) {
                    matrizVermelha[i][j] = 0;
                    matrizVerde[i][j] = 255;
                    matrizAzul[i][j] = 0;
                }
                if (matrizAzul[i][j] > matrizVerde[i][j] && matrizAzul[i][j] > matrizVermelha[i][j]) {
                    matrizVermelha[i][j] = 0;
                    matrizVerde[i][j] = 0;
                    matrizAzul[i][j] = 255;
                }
            }
        }

        gerarImagem(matrizVermelha, matrizVerde, matrizAzul);
    }

    public void aplicarEscalaCinzaEscuro() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        for (int i = 0; i < matrizVermelha.length; i++) {
            for (int j = 0; j < matrizVermelha[i].length; j++) {
                //uma das cores é a maior
                if (matrizVermelha[i][j] < matrizVerde[i][j] && matrizVermelha[i][j] < matrizAzul[i][j]) {
                    matrizVerde[i][j] = matrizVermelha[i][j];
                    matrizAzul[i][j] = matrizVermelha[i][j];
                }
                if (matrizVerde[i][j] < matrizVermelha[i][j] && matrizVerde[i][j] < matrizAzul[i][j]) {
                    matrizVermelha[i][j] = matrizVerde[i][j];
                    matrizAzul[i][j] = matrizVerde[i][j];
                }
                if (matrizAzul[i][j] < matrizVerde[i][j] && matrizAzul[i][j] < matrizVermelha[i][j]) {
                    matrizVermelha[i][j] = matrizAzul[i][j];
                    matrizVerde[i][j] = matrizAzul[i][j];
                }
            }
        }

        gerarImagem(matrizVermelha, matrizVerde, matrizAzul);
    }

    public void aplicarEscalaCinzaClaro() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        for (int i = 0; i < matrizVermelha.length; i++) {
            for (int j = 0; j < matrizVermelha[i].length; j++) {
                if (matrizVermelha[i][j] > matrizVerde[i][j] && matrizVermelha[i][j] > matrizAzul[i][j]) {
                    matrizVerde[i][j] = matrizVermelha[i][j];
                    matrizAzul[i][j] = matrizVermelha[i][j];
                }
                if (matrizVerde[i][j] > matrizVermelha[i][j] && matrizVerde[i][j] > matrizAzul[i][j]) {
                    matrizVermelha[i][j] = matrizVerde[i][j];
                    matrizAzul[i][j] = matrizVerde[i][j];
                }
                if (matrizAzul[i][j] > matrizVerde[i][j] && matrizAzul[i][j] > matrizVermelha[i][j]) {
                    matrizVermelha[i][j] = matrizAzul[i][j];
                    matrizVerde[i][j] = matrizAzul[i][j];
                }
            }
        }

        gerarImagem(matrizVermelha, matrizVerde, matrizAzul);
    }

    //reconhecer se a imagem é de um celular ou de uma caneta
    //obs: na foto, a sombra do objeto pod interferir
    public void reconhecerImagem() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();
        int matrizBinaria[][] = new int[matrizVermelha.length][matrizVermelha[0].length];
        int limite = 100; //pode variar

        //utilizando a lógica da imagem binária
        //aqui, o fundo vai ficar preto, e o objeto branco
        for (int pixelHorizontal = 0; pixelHorizontal < matrizVermelha.length; pixelHorizontal++) {
            for (int pixelVertical = 0; pixelVertical < matrizVermelha[pixelHorizontal].length; pixelVertical++) {
                matrizBinaria[pixelHorizontal][pixelVertical] = (matrizVermelha[pixelHorizontal][pixelVertical] + matrizVerde[pixelHorizontal][pixelVertical] + matrizAzul[pixelHorizontal][pixelVertical]) / 3;
                if (matrizBinaria[pixelHorizontal][pixelVertical] <= limite) {
                    matrizBinaria[pixelHorizontal][pixelVertical] = 255;
                } else {
                    matrizBinaria[pixelHorizontal][pixelVertical] = 0;
                }
            }
        }

        gerarImagem(matrizBinaria, matrizBinaria, matrizBinaria);

        int limiarAltura = 38; //38% da altura, neste contexto
        int meio = matrizBinaria[0].length / 2;
        int alturaObjeto = 0, alturaTotal = matrizBinaria.length;

        System.out.println(matrizVermelha.length + " - " + matrizVermelha[0].length);

        for (int i = 0; i < matrizBinaria.length; i++) {
            if (matrizBinaria[i][meio] == 255) {
                alturaObjeto++;
            }
        }

        //converte para escala de 100% usando regra de 3
        int proporcaoObjeto = (alturaObjeto * 100) / alturaTotal;

        JOptionPane.showMessageDialog(rootPane, "Proporção do objeto: " + proporcaoObjeto + "%");
        if (proporcaoObjeto > limiarAltura) { //verificar esse numero 
            JOptionPane.showMessageDialog(rootPane, "Celular");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Caneta");
        }
    }

    //reduzir a imagem em 50% -> tirar uma linha, colocar uma linha 
    //exemplo: pegar só os pares
    //vai perder informação
    public void diminuirImagem() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();
        int porcentagemReducao = Integer.parseInt(JOptionPane.showInputDialog("Informe a porcentagem de redução (0 - 100)"));

        int alturaOriginal = matrizVermelha.length, larguraOriginal = matrizVermelha[0].length;
        int alturaNova = (alturaOriginal * porcentagemReducao) / 100, larguraNova = (larguraOriginal * porcentagemReducao) / 100;
        int matrizVermelhaReduzida[][] = new int[alturaNova][larguraNova];
        int matrizVerdeReduzida[][] = new int[alturaNova][larguraNova];
        int matrizAzulReduzida[][] = new int[alturaNova][larguraNova];

        for (int i = 0, k = 0; i < matrizVermelhaReduzida.length; i++, k += 2) {
            for (int j = 0, l = 0; j < matrizVermelhaReduzida[0].length; j++, l += 2) {
                int original_i = i * alturaOriginal / alturaNova;
                int original_j = j * larguraOriginal / larguraNova;

                matrizVermelhaReduzida[i][j] = matrizVermelha[original_i][original_j];
                matrizVerdeReduzida[i][j] = matrizVerde[original_i][original_j];
                matrizAzulReduzida[i][j] = matrizAzul[original_i][original_j];
            }
        }

        gerarImagem(matrizVermelhaReduzida, matrizVerdeReduzida, matrizAzulReduzida);
    }

    public void aumentarImagem() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();
        int porcentagemAumento = Integer.parseInt(JOptionPane.showInputDialog("Informe a porcentagem de aumento (100 - ?)"));

        int alturaOriginal = matrizVermelha.length, larguraOriginal = matrizVermelha[0].length;
        int alturaNova = (alturaOriginal * porcentagemAumento) / 100, larguraNova = (larguraOriginal * porcentagemAumento) / 100;
        int matrizVermelhaAmpliada[][] = new int[alturaNova][larguraNova];
        int matrizVerdeAmpliada[][] = new int[alturaNova][larguraNova];
        int matrizAzulAmpliada[][] = new int[alturaNova][larguraNova];

        for (int i = 0; i < alturaNova; i++) {
            for (int j = 0; j < larguraNova; j++) {
                int original_i = i * alturaOriginal / alturaNova;
                int original_j = j * larguraOriginal / larguraNova;

                matrizVermelhaAmpliada[i][j] = matrizVermelha[original_i][original_j];
                matrizVerdeAmpliada[i][j] = matrizVerde[original_i][original_j];
                matrizAzulAmpliada[i][j] = matrizAzul[original_i][original_j];
            }
        }

        gerarImagem(matrizVermelhaAmpliada, matrizVerdeAmpliada, matrizAzulAmpliada);
    }

    public void rotacionarImagem() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        int matrizVermelhaRotacionada[][] = new int[matrizVermelha[0].length][matrizVermelha.length];
        int matrizVerdeRotacionada[][] = new int[matrizVerde[0].length][matrizVerde.length];
        int matrizAzulRotacionada[][] = new int[matrizAzul[0].length][matrizAzul.length];

        //não deixar a imagem espelhada (exemplo do celular), a imagem vai "deitar"
        for (int i = 0; i < matrizVermelhaRotacionada.length; i++) {
            for (int j = 0; j < matrizVermelhaRotacionada[0].length; j++) {
                matrizVermelhaRotacionada[matrizVermelha[0].length - j - 1][i] = matrizVermelha[i][j];
                matrizVerdeRotacionada[matrizVermelha[0].length - j - 1][i] = matrizVerde[i][j];
                matrizAzulRotacionada[matrizVermelha[0].length - j - 1][i] = matrizAzul[i][j];
            }
        }

        gerarImagem(matrizVermelhaRotacionada, matrizVerdeRotacionada, matrizAzulRotacionada);

        int matrizVermelhaR[][] = new int[matrizVermelha[0].length][matrizVermelha.length];
        int matrizVerdeR[][] = new int[matrizVerde[0].length][matrizVerde.length];
        int matrizAzulR[][] = new int[matrizAzul[0].length][matrizAzul.length];

        for (int i = 0, k = matrizVermelhaRotacionada.length - 1; i < matrizVermelhaRotacionada.length; i++, k--) {
            for (int j = 0, l = matrizVermelhaRotacionada[0].length - 1; j < matrizVermelhaRotacionada[0].length; j++, l--) {
                matrizVermelhaR[i][j] = matrizVermelhaRotacionada[k][l];
                matrizVerdeR[i][j] = matrizVerdeRotacionada[k][l];
                matrizAzulR[i][j] = matrizAzulRotacionada[k][l];
            }
        }

        gerarImagem(matrizVermelhaR, matrizVerdeR, matrizAzulR);
    }

    public void transformarHSV() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        int matrizH[][] = new int[matrizVermelha.length][matrizVermelha[0].length];
        int matrizS[][] = new int[matrizVerde.length][matrizVerde[0].length];
        int matrizV[][] = new int[matrizAzul.length][matrizAzul[0].length];

        // Convertendo imagem RGB para HSV
        for (int i = 0; i < matrizVermelha.length; i++) {
            for (int j = 0; j < matrizVermelha[0].length; j++) {
                int r = matrizVermelha[i][j];
                int g = matrizVerde[i][j];
                int b = matrizAzul[i][j];

                // Normalizando os valores RGB para estar entre 0 e 1
                float[] hsv = new float[3];
                Color.RGBtoHSB(r, g, b, hsv);

                // Convertendo os valores para o intervalo de 0 a 255
                int hue = (int) (hsv[0] * 255);
                int saturation = (int) (hsv[1] * 255);
                int value = (int) (hsv[2] * 255);

                // Armazena os valores HSV nas matrizes correspondentes
                matrizH[i][j] = hue;
                matrizS[i][j] = saturation;
                matrizV[i][j] = value;
            }
        }

        // Gerar imagens com um dos componentes no máximo
        int[][] matrizHMax = matrizH.clone(); // H máximo, S e V iguais a 255
        int[][] matrizSMax = new int[matrizS.length][matrizS[0].length]; // H e V iguais a 0, S máximo
        int[][] matrizVMax = new int[matrizV.length][matrizV[0].length]; // H e S iguais a 0, V máximo

        // Converter matrizes HSV de volta para RGB
        int[][] matrizRGBHMax = converterHSVparaRGB(matrizHMax, matrizS, matrizV);
        int[][] matrizRGBSMax = converterHSVparaRGB(matrizH, matrizSMax, matrizV);
        int[][] matrizRGBVMax = converterHSVparaRGB(matrizH, matrizS, matrizVMax);

        // Exibir as três imagens alteradas na interface
        gerarImagem(matrizRGBHMax, matrizRGBSMax, matrizRGBVMax);
    }

    public int[][] converterHSVparaRGB(int[][] matrizH, int[][] matrizS, int[][] matrizV) {
        int largura = matrizH.length;
        int altura = matrizH[0].length;
        int[][] matrizRGB = new int[largura][altura]; // Armazena o valor RGB como um único inteiro

        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                float h = matrizH[i][j] / 255.0f;
                float s = matrizS[i][j] / 255.0f;
                float v = matrizV[i][j] / 255.0f;

                int rgb = Color.HSBtoRGB(h, s, v);
                matrizRGB[i][j] = rgb;
            }
        }

        return matrizRGB;
    }

    public void geraHistograma() {
        int[] histograma = new int[256]; // Array para armazenar o histograma
        int step = 2; // Largura de cada barra no histograma
        int altura = 300; // Altura máxima do histograma
        int[][] histoPlot = new int[altura][256 * step]; // Matriz para plotar o histograma
        int[][] matrizVermelha = obterMatrizVermelha();
        int[][] matrizVerde = obterMatrizVerde();
        int[][] matrizAzul = obterMatrizAzul();

        // Calcula o histograma
        for (int i = 0; i < matrizAzul.length; i++) {
            for (int j = 0; j < matrizAzul[0].length; j++) {
                int media = (matrizVermelha[i][j] + matrizVerde[i][j] + matrizAzul[i][j]) / 3;
                histograma[media]++;
            }
        }

        // Encontra o valor máximo no histograma
        int max = Arrays.stream(histograma).max().orElse(1) + 1;

        // Desenha o histograma na matriz histoPlot
        int c2 = 0;
        for (int c = 0; c < histograma.length; c++) {
            // Redimensiona o nível de acordo com a altura máxima
            int novoNivel = altura * histograma[c] / max;
            // Calcula o topo da barra
            int topo = histoPlot.length - novoNivel;

            // Preenche a coluna no histoPlot para desenhar a barra do histograma
            for (int i = topo; i < histoPlot.length; i++) {
                for (int j = c2; j < c2 + step; j++) {
                    histoPlot[i][j] = 255 - (histoPlot.length - 1 - i) * 255 / altura; // Gradiente de intensidade
                }
            }
            c2 += step; // Move para a próxima posição na matriz histoPlot
        }

        // Chama o método para gerar a imagem com base no histoPlot
        gerarImagem(histoPlot, histoPlot, histoPlot);
    }

    public void Equalizar() {
        int[][] matrizVermelha = obterMatrizVermelha();
        int[][] matrizVerde = obterMatrizVerde();
        int[][] matrizAzul = obterMatrizAzul();

        // Calcula os histogramas para cada canal de cor
        int[] histogramaR = calcularHistograma(matrizVermelha);
        int[] histogramaG = calcularHistograma(matrizVerde);
        int[] histogramaB = calcularHistograma(matrizAzul);

        // Calcula a função de distribuição acumulada (CDF)
        int[] cdfR = calcularCDF(histogramaR);
        int[] cdfG = calcularCDF(histogramaG);
        int[] cdfB = calcularCDF(histogramaB);

        // Aplica a equalização nos canais de cor
        int[][] matrizEQVermelha = aplicarEqualizacao(matrizVermelha, cdfR);
        int[][] matrizEQVerde = aplicarEqualizacao(matrizVerde, cdfG);
        int[][] matrizEQAzul = aplicarEqualizacao(matrizAzul, cdfB);

        // substitui as matrizes originais pelas equalizadas
        matrizVermelha = matrizEQVermelha;
        matrizVerde = matrizEQVerde;
        matrizAzul = matrizEQAzul;

        // Exemplo: gerar e exibir a imagem equalizada
        gerarImagem(matrizVermelha, matrizVerde, matrizAzul);
    }

    // Função para calcular o histograma de um canal de cor
    private int[] calcularHistograma(int[][] matrizCor) {
        int[] histograma = new int[256]; // 256 intensidades possíveis (0 a 255)

        for (int i = 0; i < matrizCor.length; i++) {
            for (int j = 0; j < matrizCor[0].length; j++) {
                int intensidade = matrizCor[i][j];
                histograma[intensidade]++;
            }
        }

        return histograma;
    }

    // Função para calcular a função de distribuição acumulada (CDF) a partir do histograma
    private int[] calcularCDF(int[] histograma) {
        int[] cdf = new int[256]; // CDF para 256 intensidades possíveis

        cdf[0] = histograma[0];
        for (int i = 1; i < 256; i++) {
            cdf[i] = cdf[i - 1] + histograma[i];
        }

        return cdf;
    }

    // Função para aplicar a equalização usando a CDF
    private int[][] aplicarEqualizacao(int[][] matrizCor, int[] cdf) {
        int largura = matrizCor.length;
        int altura = matrizCor[0].length;
        int[][] matrizEqualizada = new int[largura][altura];

        int totalPixels = largura * altura;
        float[] f = new float[256];
        for (int i = 0; i < 256; i++) {
            f[i] = (float) cdf[i] / totalPixels;
        }

        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                int intensidadeOriginal = matrizCor[i][j];
                int novaIntensidade = Math.round(255 * f[intensidadeOriginal]);
                matrizEqualizada[i][j] = novaIntensidade;
            }
        }

        return matrizEqualizada;
    }

    public void MudarAngulo() {
        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        int anguloUsuario = Integer.parseInt(JOptionPane.showInputDialog("Informe o ângulo que você deseja rotacionar:"));

        int tamanho = 5;

        // Convertendo ângulo para radianos (pois Math.sin e Math.cos usam radianos)
        double anguloRad = Math.toRadians(anguloUsuario);

        // Matrizes rotacionadas
        int matrizVermelhaRotacionada[][] = new int[tamanho][tamanho];
        int matrizVerdeRotacionada[][] = new int[tamanho][tamanho];
        int matrizAzulRotacionada[][] = new int[tamanho][tamanho];

        // Centro da matriz para usar como ponto de rotação 
        int centroX = tamanho / 2;
        int centroY = tamanho / 2;

        // Aplicando a fórmula de rotação para cada elemento das matrizes originais
        for (int j = 0; j < tamanho; j++) {
            for (int i = 0; i < tamanho; i++) {
                // Calculando coordenadas relativas ao centro
                int relX = i - centroX;
                int relY = j - centroY;

                // Aplicando a fórmula de rotação
                int novoX = centroX + (int) Math.round(relX * Math.cos(anguloRad) - relY * Math.sin(anguloRad));
                int novoY = centroY + (int) Math.round(relX * Math.sin(anguloRad) + relY * Math.cos(anguloRad));

                // Verificando limites para garantir que estamos dentro da matriz
                if (novoX >= 0 && novoX < tamanho && novoY >= 0 && novoY < tamanho) {
                    // Copiando valores das matrizes originais para as rotacionadas
                    matrizVermelhaRotacionada[novoY][novoX] = matrizVermelha[j][i];
                    matrizVerdeRotacionada[novoY][novoX] = matrizVerde[j][i];
                    matrizAzulRotacionada[novoY][novoX] = matrizAzul[j][i];
                }
            }
        }

        gerarImagem(matrizVermelhaRotacionada, matrizVerdeRotacionada, matrizAzulRotacionada);
    }

    public void FiltroMedia() {

        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        int tam = matrizVermelha.length;

        int matrizVermelhaMedia[][] = new int[tam][tam];  // Definindo novo tamanho
        int matrizVerdeMedia[][] = new int[tam][tam];
        int matrizAzulMedia[][] = new int[tam][tam];

        int mascara = 3; // Tamanho da matriz , filtro(máscara)

        int mascaraQtPixel = mascara / 2;  // Quantidade de pixels aplicados(considerados)

        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                int somaVermelha = 0;
                int somaVerde = 0;
                int somaAzul = 0;
                int cont = 0;

                // Calculando a média 
                for (int y = -mascaraQtPixel; y <= mascaraQtPixel; y++) {
                    for (int x = -mascaraQtPixel; x <= mascaraQtPixel; x++) {
                        int vizinhoX = i + x;
                        int vizinhoY = j + y;

                        if (vizinhoX >= 0 && vizinhoX < tam && vizinhoY >= 0 && vizinhoY < tam) {
                            somaVermelha += matrizVermelha[vizinhoY][vizinhoX];
                            somaVerde += matrizVerde[vizinhoY][vizinhoX];
                            somaAzul += matrizAzul[vizinhoY][vizinhoX];
                            cont++;
                        }
                    }
                }

                matrizVermelhaMedia[j][i] = somaVermelha / cont;
                matrizVerdeMedia[j][i] = somaVerde / cont;
                matrizAzulMedia[j][i] = somaAzul / cont;

            }
        }

        gerarImagem(matrizVermelhaMedia, matrizVerdeMedia, matrizAzulMedia);

    }

    public void FiltroMediana() {

        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        int tam = matrizVermelha.length;

        int matrizVermelhaMediana[][] = new int[tam][tam];  // Definindo novo tamanho
        int matrizVerdeMediana[][] = new int[tam][tam];
        int matrizAzulMediana[][] = new int[tam][tam];

        int mascara = 3; // Tamanho da matriz , filtro(máscara)
        int mascaraQtPixel = mascara / 2;  // Quantidade de pixels aplicados(considerados)
        int QtElementos = mascara * mascara; // Para criar um array de 9 elementos

        for (int j = 0; j < tam; j++) {
            for (int i = 0; i < tam; i++) {
                int[] vizinhosVermelhos = new int[QtElementos];
                int[] vizinhosVerdes = new int[QtElementos];
                int[] vizinhosAzuis = new int[QtElementos];
                int index = 0;

                // Coletando valores da vizinhança
                for (int y = -mascaraQtPixel; y <= mascaraQtPixel; y++) {
                    for (int x = -mascaraQtPixel; x <= mascaraQtPixel; x++) {
                        int vizinhoX = i + x;
                        int vizinhoY = j + y;

                        if (vizinhoX >= 0 && vizinhoX < tam && vizinhoY >= 0 && vizinhoY < tam) {
                            vizinhosVermelhos[index] = matrizVermelha[vizinhoY][vizinhoX];
                            vizinhosVerdes[index] = matrizVerde[vizinhoY][vizinhoX];
                            vizinhosAzuis[index] = matrizAzul[vizinhoY][vizinhoX];
                            index++;
                        }
                    }
                }

                // Calculando a mediana dos valores coletados
                matrizVermelhaMediana[j][i] = calculeMediana(vizinhosVermelhos, index);
                matrizVerdeMediana[j][i] = calculeMediana(vizinhosVerdes, index);
                matrizAzulMediana[j][i] = calculeMediana(vizinhosAzuis, index);
            }
        }

        gerarImagem(matrizVermelhaMediana, matrizVerdeMediana, matrizAzulMediana);

    }

    private int calculeMediana(int[] valores, int tam) {
        Arrays.sort(valores, 0, tam);
        int meio = tam / 2;
        if (tam % 2 == 0) {
            return (valores[meio - 1] + valores[meio]) / 2;
        } else {
            return valores[meio];
        }
    }

    public void FiltroGaussio() {

        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        int tam = matrizVermelha.length;

        int matrizVermelhaGau[][] = new int[tam][tam];  // Definindo novo tamanho
        int matrizVerdeGau[][] = new int[tam][tam];
        int matrizAzulGau[][] = new int[tam][tam];

        int mascara = 3; // Tamanho da matriz , filtro(máscara)
        double desvio = 1.0;
        double[][] mascaraGaussiana = novoGaussiano(mascara, desvio);
        int mascaraQtPixel = mascara / 2;  // Quantidade de pixels aplicados(considerados)

        for (int j = 0; j < tam; j++) {
            for (int i = 0; i < tam; i++) {
                double somaVermelha = 0;
                double somaVerde = 0;
                double somaAzul = 0;
                double somaKernel = 0;

                for (int y = -mascaraQtPixel; y <= mascaraQtPixel; y++) {
                    for (int x = -mascaraQtPixel; x <= mascaraQtPixel; x++) {
                        int vizinhoX = i + x;
                        int vizinhoY = j + y;

                        if (vizinhoX >= 0 && vizinhoX < tam && vizinhoY >= 0 && vizinhoY < tam) {
                            double peso = mascaraGaussiana[y + mascaraQtPixel][x + mascaraQtPixel];
                            somaVermelha += matrizVermelha[vizinhoY][vizinhoX] * peso;
                            somaVerde += matrizVerde[vizinhoY][vizinhoX] * peso;
                            somaAzul += matrizAzul[vizinhoY][vizinhoX] * peso;
                            somaKernel += peso;
                        }
                    }
                }

                matrizVermelhaGau[j][i] = (int) Math.round(somaVermelha / somaKernel);
                matrizVerdeGau[j][i] = (int) Math.round(somaVerde / somaKernel);
                matrizAzulGau[j][i] = (int) Math.round(somaAzul / somaKernel);
            }
        }

        gerarImagem(matrizVermelhaGau, matrizVerdeGau, matrizAzulGau);

    }

    private double[][] novoGaussiano(int tamanho, double sigma) {
        double[][] kernel = new double[tamanho][tamanho];
        double soma = 0;
        int offset = tamanho / 2;
        double sigma2 = sigma * sigma;

        for (int y = -offset; y <= offset; y++) {
            for (int x = -offset; x <= offset; x++) {
                double valor = (1 / (2 * Math.PI * sigma2)) * Math.exp(-(x * x + y * y) / (2 * sigma2));
                kernel[y + offset][x + offset] = valor;
                soma += valor;
            }
        }

        // Normalizando o kernel
        for (int y = 0; y < tamanho; y++) {
            for (int x = 0; x < tamanho; x++) {
                kernel[y][x] /= soma;
            }
        }

        return kernel;
    }

    public void FiltroSobel() {

        int matrizVermelha[][] = obterMatrizVermelha();
        int matrizVerde[][] = obterMatrizVerde();
        int matrizAzul[][] = obterMatrizAzul();

        int tam = matrizVermelha.length;

        int matrizVermelhaSol[][] = new int[tam][tam];  // Definindo novo tamanho
        int matrizVerdeSol[][] = new int[tam][tam];
        int matrizAzulSol[][] = new int[tam][tam];

        int[][] sobelGx = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
        };

        int[][] sobelGy = {
            {1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}
        };

        int mascara = 3; // Tamanho da matriz , filtro(máscara)    
        int mascaraQtPixel = mascara / 2;  // Quantidade de pixels aplicados(considerados)

        for (int j = 0; j < tam; j++) {
            for (int i = 0; i < tam; i++) {
                int somaVermelhaGx = 0;
                int somaVermelhaGy = 0;
                int somaVerdeGx = 0;
                int somaVerdeGy = 0;
                int somaAzulGx = 0;
                int somaAzulGy = 0;

                for (int y = -mascaraQtPixel; y <= mascaraQtPixel; y++) {
                    for (int x = -mascaraQtPixel; x <= mascaraQtPixel; x++) {
                        int vizinhoX = i + x;
                        int vizinhoY = j + y;

                        if (vizinhoX >= 0 && vizinhoX < tam && vizinhoY >= 0 && vizinhoY < tam) {
                            int pesoGx = sobelGx[y + mascaraQtPixel][x + mascaraQtPixel];
                            int pesoGy = sobelGy[y + mascaraQtPixel][x + mascaraQtPixel];

                            somaVermelhaGx += matrizVermelha[vizinhoY][vizinhoX] * pesoGx;
                            somaVermelhaGy += matrizVermelha[vizinhoY][vizinhoX] * pesoGy;

                            somaVerdeGx += matrizVerde[vizinhoY][vizinhoX] * pesoGx;
                            somaVerdeGy += matrizVerde[vizinhoY][vizinhoX] * pesoGy;

                            somaAzulGx += matrizAzul[vizinhoY][vizinhoX] * pesoGx;
                            somaAzulGy += matrizAzul[vizinhoY][vizinhoX] * pesoGy;
                        }
                    }
                }

                int magnitudeVermelha = (int) Math.sqrt(somaVermelhaGx * somaVermelhaGx + somaVermelhaGy * somaVermelhaGy);
                int magnitudeVerde = (int) Math.sqrt(somaVerdeGx * somaVerdeGx + somaVerdeGy * somaVerdeGy);
                int magnitudeAzul = (int) Math.sqrt(somaAzulGx * somaAzulGx + somaAzulGy * somaAzulGy);

                matrizVermelhaSol[j][i] = Math.min(255, magnitudeVermelha);
                matrizVerdeSol[j][i] = Math.min(255, magnitudeVerde);
                matrizAzulSol[j][i] = Math.min(255, magnitudeAzul);
            }
        }

        gerarImagem(matrizVermelhaSol, matrizVerdeSol, matrizAzulSol);

    }
   

    public Imagem() {

        super("PhotoIFMG");

        JMenuBar barraNavegacao = new JMenuBar();
        JMenu menuAbrirImagem = new JMenu("Abrir");
        JMenuItem manuAbrirImagem = new JMenuItem("Abir uma imagem de arquivo");
        JMenuItem newFrame = new JMenuItem("Internal Frame");
        menuAbrirImagem.add(manuAbrirImagem);
        menuAbrirImagem.add(newFrame);

        barraNavegacao.add(menuAbrirImagem);
        JMenu menuProcessarImagem = new JMenu("Processar");
        JMenuItem menuEscalaCinza = new JMenuItem("Escala de cinza");
        JMenuItem menuImagemBinaria = new JMenuItem("Imagem binária");
        JMenuItem menuImagemNegativa = new JMenuItem("Negativa");
        JMenuItem menuCorDominante = new JMenuItem("Cor dominante");
        JMenuItem menuCinzaEscuro = new JMenuItem("Cinza escuro");
        JMenuItem menuCinzaClaro = new JMenuItem("Cinza claro");
        JMenuItem menuExtra1 = new JMenuItem("Extra 1 (manter cor)");
        JMenuItem menuExtra2 = new JMenuItem("Extra 2 (retirar cor)");
        JMenuItem menuReconhecimento = new JMenuItem("Reconhecimento de objetos (celular ou caneta)");
        JMenuItem menuDiminuir = new JMenuItem("Diminuir imagem");
        JMenuItem menuAumentar = new JMenuItem("Aumentar imagem");
        JMenuItem menuRotacionar90 = new JMenuItem("Rotacionar 90° (esquerda e direita)");
        JMenuItem menuRGB_HSV = new JMenuItem("RGB -> HSV");
        JMenuItem menu_HSV = new JMenuItem("Transformar para HSV");
        JMenuItem menuHistograma = new JMenuItem("Gerar Histograma");
        JMenuItem menuEQ = new JMenuItem("Equalizar imagem ");
        JMenuItem menuRot = new JMenuItem("Rotacionar imagem");
        JMenuItem menuMedia = new JMenuItem("Média");
        JMenuItem menuMediana = new JMenuItem("Mediana");
        JMenuItem menuGaussiano = new JMenuItem("Filtro Gaussiano");
        JMenuItem menuSobel = new JMenuItem("Filtro Sobel");

        menuProcessarImagem.add(menuEscalaCinza);
        menuProcessarImagem.add(menuImagemBinaria);
        menuProcessarImagem.add(menuImagemNegativa);
        menuProcessarImagem.add(menuCorDominante);
        menuProcessarImagem.add(menuCinzaEscuro);
        menuProcessarImagem.add(menuCinzaClaro);
        menuProcessarImagem.add(menuExtra1);
        menuProcessarImagem.add(menuExtra2);
        menuProcessarImagem.add(menuReconhecimento);
        menuProcessarImagem.add(menuDiminuir);
        menuProcessarImagem.add(menuAumentar);
        menuProcessarImagem.add(menuRotacionar90);
        menuProcessarImagem.add(menuRGB_HSV);
        menuProcessarImagem.add(menu_HSV);
        menuProcessarImagem.add(menuHistograma);
        menuProcessarImagem.add(menuEQ);
        menuProcessarImagem.add(menuRot);
        menuProcessarImagem.add(menuMedia);
        menuProcessarImagem.add(menuMediana);
        menuProcessarImagem.add(menuGaussiano);
        menuProcessarImagem.add(menuSobel);

        barraNavegacao.add(menuProcessarImagem);
        setJMenuBar(barraNavegacao);

        theDesktop = new JDesktopPane();
        getContentPane().add(theDesktop);

        newFrame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JInternalFrame frame = new JInternalFrame("Exemplo", true, true, true, true);

                Container container = frame.getContentPane();
                MyJPanel panel = new MyJPanel();
                container.add(panel, BorderLayout.CENTER);
                frame.pack();
                theDesktop.add(frame);
                frame.setVisible(true);
            }
        });

        //ler imagem, jpg é mais recomendado
        manuAbrirImagem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.CANCEL_OPTION) {
                    return;
                }
                path = fileChooser.getSelectedFile().getAbsolutePath();
                JInternalFrame frame = new JInternalFrame("Exemplo", true, true, true, true);
                Container container = frame.getContentPane();
                MyJPanel panel = new MyJPanel();
                container.add(panel, BorderLayout.CENTER);
                frame.pack();
                theDesktop.add(frame);
                frame.setVisible(true);
            }
        });

        menuEscalaCinza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                aplicarEscalaCinza();

                /*int[][] mat = rgbMat.elementAt(0);
                int[][] mat2 = rgbMat.elementAt(1);
                int[][] mat3 = rgbMat.elementAt(2);

                geraImagem(mat3, mat2, mat);*/
            }
        });

        menuImagemBinaria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                aplicarImagemBinaria();
            }
        });

        menuImagemNegativa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                aplicarImagemNegativa();
            }
        });

        menuCorDominante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                aplicarCorDominante();
            }

        });

        menuCinzaEscuro.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                aplicarEscalaCinzaEscuro();
            }
        });

        menuCinzaClaro.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                aplicarEscalaCinzaClaro();
            }
        });

        //extra: Um usuário escolhe um cor (RGB), esse pixel vai ser mantido (se for maior que 167) e os outros serão acinzentados
        menuExtra1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String op = JOptionPane.showInputDialog(rootPane, "Escolha uma cor para ser mantida (r g b)");

                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);

                int matrizVermelha[][] = obterMatrizVermelha();
                int matrizVerde[][] = obterMatrizVerde();
                int matrizAzul[][] = obterMatrizAzul();
                int matrizCinza[][] = new int[matrizVermelha.length][matrizVermelha[0].length];

                switch (op) {
                    case "r":
                        for (int i = 0; i < matrizVermelha.length; i++) {
                            for (int j = 0; j < matrizVermelha[i].length; j++) {
                                matrizCinza[i][j] = (matrizVermelha[i][j] + matrizVerde[i][j] + matrizAzul[i][j]) / 3;
                                if (matrizVermelha[i][j] < 167) {
                                    matrizVermelha[i][j] = matrizCinza[i][j];
                                }
                            }
                        }
                        gerarImagem(matrizVermelha, matrizCinza, matrizCinza);
                        break;
                    case "g":
                        for (int i = 0; i < matrizVermelha.length; i++) {
                            for (int j = 0; j < matrizVermelha[i].length; j++) {
                                matrizCinza[i][j] = (matrizVermelha[i][j] + matrizVerde[i][j] + matrizAzul[i][j]) / 3;
                                if (matrizVerde[i][j] < 167) {
                                    matrizVerde[i][j] = matrizCinza[i][j];
                                }
                            }
                        }
                        gerarImagem(matrizCinza, matrizVerde, matrizCinza);
                        break;
                    case "b":
                        for (int i = 0; i < matrizVermelha.length; i++) {
                            for (int j = 0; j < matrizVermelha[i].length; j++) {
                                matrizCinza[i][j] = (matrizVermelha[i][j] + matrizVerde[i][j] + matrizAzul[i][j]) / 3;
                                if (matrizAzul[i][j] < 167) {
                                    matrizAzul[i][j] = matrizCinza[i][j];
                                }
                            }
                        }
                        gerarImagem(matrizCinza, matrizCinza, matrizAzul);
                        break;
                    default:
                        JOptionPane.showMessageDialog(rootPane, "Opção inválida");
                }
            }
        });

        menuExtra2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String op = JOptionPane.showInputDialog(rootPane, "Escolha uma cor para ser removida (r g b)");

                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);

                int matrizVermelha[][] = obterMatrizVermelha();
                int matrizVerde[][] = obterMatrizVerde();
                int matrizAzul[][] = obterMatrizAzul();
                int matrizCinza[][] = new int[matrizVermelha.length][matrizVermelha[0].length];

                switch (op) {
                    case "r":
                        for (int i = 0; i < matrizVermelha.length; i++) {
                            for (int j = 0; j < matrizVermelha[i].length; j++) {
                                matrizVermelha[i][j] = 0;
                            }
                        }
                        gerarImagem(matrizVermelha, matrizVerde, matrizAzul);
                        break;
                    case "g":
                        for (int i = 0; i < matrizVermelha.length; i++) {
                            for (int j = 0; j < matrizVermelha[i].length; j++) {
                                matrizVerde[i][j] = 0;
                            }
                        }
                        gerarImagem(matrizVermelha, matrizVerde, matrizAzul);
                        break;
                    case "b":
                        for (int i = 0; i < matrizVermelha.length; i++) {
                            for (int j = 0; j < matrizVermelha[i].length; j++) {
                                matrizAzul[i][j] = 0;
                            }
                        }
                        gerarImagem(matrizVermelha, matrizVerde, matrizAzul);
                        break;
                    default:
                        JOptionPane.showMessageDialog(rootPane, "Opção inválida");
                }
            }
        });

        menuReconhecimento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                reconhecerImagem();
            }
        });

        menuDiminuir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                diminuirImagem();
            }
        });

        menuAumentar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                aumentarImagem();
            }
        });

        menuRotacionar90.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                rotacionarImagem();
            }
        });

        menuRGB_HSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                transformarHSV();
            }
        });

        menuHistograma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                geraHistograma();
            }

        });

        menuEQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                Equalizar();
            }

        });

        menuRot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                MudarAngulo();
            }

        });

        menuMedia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                FiltroMedia();
            }

        });

        menuMediana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                FiltroMediana();
            }

        });

        menuGaussiano.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                FiltroGaussio();
            }

        });
        
        menuSobel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<int[][]> rgbMat = getMatrixRGB();
                matrizRedImagem = rgbMat.elementAt(0);
                matrizGreenImagem = rgbMat.elementAt(1);
                matrizBlueImagem = rgbMat.elementAt(2);
                FiltroSobel();
            }

        });
      


        setSize(600, 400);
        setVisible(true);

    }

    //ler matrizes da imagem
    public Vector<int[][]> getMatrixRGB() {

        BufferedImage imagem;
        int[][] matrizRImagem = null;
        int[][] matrizGImagem = null;
        int[][] matrizBImagem = null;

        try {
            imagem = ImageIO.read(new File(path));
            int[][] pixelData = new int[imagem.getHeight() * imagem.getWidth()][3];
            matrizRImagem = new int[imagem.getHeight()][imagem.getWidth()];
            matrizGImagem = new int[imagem.getHeight()][imagem.getWidth()];
            matrizBImagem = new int[imagem.getHeight()][imagem.getWidth()];
            int counter = 0;

            for (int i = 0; i < imagem.getHeight(); i++) {
                for (int j = 0; j < imagem.getWidth(); j++) {
                    matrizRImagem[i][j] = getPixelData(imagem, j, i)[0];
                    matrizGImagem[i][j] = getPixelData(imagem, j, i)[1];
                    matrizBImagem[i][j] = getPixelData(imagem, j, i)[2];

                    /*for(int k = 0; k < rgb.length; k++){
                        pixelData[counter][k] = rgb[k];
                    }*/
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Vector<int[][]> rgb = new Vector<int[][]>();
        rgb.add(matrizRImagem);
        rgb.add(matrizGImagem);
        rgb.add(matrizBImagem);

        return rgb;
    }

    //cria imagem da matriz
    private void gerarImagem(int matrix1[][], int matriz2[][], int matriz3[][]) {
        int[] pixels = new int[matrix1.length * matrix1[0].length * 3];
        BufferedImage image = new BufferedImage(matrix1[0].length, matrix1.length, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        int pos = 0;

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                pixels[pos] = matrix1[i][j];
                pixels[pos + 1] = matriz2[i][j];
                pixels[pos + 2] = matriz3[i][j];
                pos += 3;
            }
        }

        raster.setPixels(0, 0, matrix1[0].length, matrix1.length, pixels);

        //Abre Janela da imagem
        JInternalFrame frame = new JInternalFrame("Processada", true, true, true, true);

        Container container = frame.getContentPane();
        MyJPanel panel = new MyJPanel();
        panel.setImageIcon(new ImageIcon(image));
        container.add(panel, BorderLayout.CENTER);
        frame.pack();
        theDesktop.add(frame);
        frame.setVisible(true);
    }

    public int[][] obterMatrizVermelha() {
        return matrizRedImagem;
    }

    public int[][] obterMatrizVerde() {
        return matrizGreenImagem;
    }

    public int[][] obterMatrizAzul() {
        return matrizBlueImagem;
    }

    private static int[] getPixelData(BufferedImage img, int x, int y) {
        int argb = img.getRGB(x, y);
        int rgb[] = new int[]{
            (argb >> 16) & 0xff, //red
            (argb >> 8) & 0xff, //green
            (argb) & 0xff //blue
        };

        return rgb;
    }

    class MyJPanel extends JPanel {

        private ImageIcon imageIcon;

        public MyJPanel() {
            imageIcon = new ImageIcon(path);
        }

        public void setImageIcon(ImageIcon i) {
            imageIcon = i;
        }

        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            imageIcon.paintIcon(this, g, 0, 0);
        }

        public Dimension getPreferredSize() {
            return new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        }
    }

    public static void main(String[] args) {
        Imagem app = new Imagem();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
