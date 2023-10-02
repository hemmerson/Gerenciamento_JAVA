package com.example.projeto3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;

public class FiltraReceitas {
    private Document doc;
    public FiltraReceitas(String caminho) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory fabrica=DocumentBuilderFactory.newInstance();
        DocumentBuilder construtor= fabrica.newDocumentBuilder();
        doc=construtor.parse(caminho);
    }

    public String serealizar(Node no) throws TransformerException {
        TransformerFactory fabrica = TransformerFactory.newInstance();
        Transformer transformador = fabrica.newTransformer();
        DOMSource fonte = new DOMSource(no);
        ByteArrayOutputStream fluxo = new ByteArrayOutputStream();
        StreamResult saida = new StreamResult(fluxo);
        transformador.transform(fonte, saida);
        return fluxo.toString();
    }

    public String pegaNome(){
        Element raiz = doc.getDocumentElement();
        NodeList nomes = raiz.getElementsByTagName("nome");
        int tam = nomes.getLength();
        StringBuilder texto = new StringBuilder();
        texto.append("<receitas>");
        for(int i = 0; i < tam; i++){
            Node noNome = nomes.item(i);
            if (noNome != null){
                texto.append("<item>");
                texto.append(noNome.getFirstChild().getNodeValue());
                texto.append("</item>");
            }
        }
        texto.append("</receitas>");
        return texto.toString();
    }

    public String pegaReceita(String nome) throws TransformerException {
        Element raiz = doc.getDocumentElement();
        NodeList nomes = raiz.getElementsByTagName("nome");
        int tam = nomes.getLength();
        for (int i = 0; i < tam; i++){
            Node noNome = nomes.item(i);
            if (noNome.getFirstChild().getNodeValue().equals(nome)){
                return serealizar(noNome.getParentNode());
            }
        }
        throw new RuntimeException("Receita não encontrada");
    }

    public String pegaPorIngrediente(String ingrediente){
        Element raiz = doc.getDocumentElement();
        NodeList ingredientes = raiz.getElementsByTagName("ingrediente");
        int tam = ingredientes.getLength();
        StringBuilder texto = new StringBuilder();
        texto.append("<receitas>");
        for(int i = 0; i < tam; i++){
            Node noIngrediente = ingredientes.item(i);
            if (noIngrediente.getFirstChild().getNodeValue().equals(ingrediente)){
                Element receita = (Element) noIngrediente.getParentNode().getParentNode();
                texto.append("<item>");
                texto.append(receita.getElementsByTagName("nome").item(0).getTextContent());
                texto.append("</item>");
            }
        }
        texto.append("</receitas>");
        return texto.toString();
    }
}
