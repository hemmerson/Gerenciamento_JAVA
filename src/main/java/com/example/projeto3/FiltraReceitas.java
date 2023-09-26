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

    private String pegaPorTag(String tag, String valor) throws TransformerException {
        Node noReceita = null;
        NodeList filhos = doc.getElementsByTagName(tag);

        for (int i = filhos.getLength()-1; i >= 0 ; i--){
            Node noFilho = filhos.item(i);
            if (noFilho != null) {
                if (!noFilho.getFirstChild().getNodeValue().equals(valor)) {
                    noReceita = noFilho.getParentNode();
                    noReceita.getParentNode().removeChild(noReceita);
                }
            }
        }
        return serealizar(doc);
    }

    public String pegaPorIngrediente(String valor) throws TransformerException {
        Node noReceita;
        NodeList ingredientes = doc.getElementsByTagName("ingredientes");

        for (int i = 0; i < ingredientes.getLength(); i++){
            Node noIngrediente = ingredientes.item(i);
            if (noIngrediente != null) {
                NodeList filhos = noIngrediente.getChildNodes();
                for (int j = 0; j < filhos.getLength(); j++) {
                    if (filhos.item(j).getNodeType() == Node.ELEMENT_NODE && filhos.item(j) != null) {
                        if (!filhos.item(j).getFirstChild().getNodeValue().equals(valor)) {
                            noReceita = noIngrediente.getParentNode();
                            noReceita.getParentNode().removeChild(noReceita);
                            break;
                        }
                    }
                }
            }
        }
        return serealizar(doc);
    }

    public String retornaListaPorIngrediente(String valor) throws TransformerException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc2 = documentBuilder.newDocument();

        Element receitas = doc2.createElement("receitas");
        doc2.appendChild(receitas);

        Node noReceita;
        NodeList ingredientes = doc.getElementsByTagName("ingredientes");

        for (int i = 0; i < ingredientes.getLength(); i++){
            Node noIngrediente = ingredientes.item(i);
            if (noIngrediente != null) {
                NodeList filhos = noIngrediente.getChildNodes();
                for (int j = 0; j < filhos.getLength(); j++) {
                    if (filhos.item(j).getNodeType() == Node.ELEMENT_NODE && filhos.item(j) != null) {
                        if (filhos.item(j).getFirstChild().getNodeValue().equals(valor)) {
                            noReceita = noIngrediente.getParentNode();
                            Node receita = doc2.importNode(noReceita, true);
                            receitas.appendChild(receita);
                            break;
                        }
                    }
                }
            }
        }
        return serealizar(doc2);
    }

    public String pegaPorNome(String valor) throws TransformerException {
        NodeList filhos = doc.getElementsByTagName("nome");
        for(int i = 0; i < filhos.getLength(); i++){
            Node noFilho = filhos.item(i);
            if (noFilho != null){
                if (noFilho.getFirstChild().getNodeValue().equals(valor)){
                    return serealizar(noFilho.getParentNode());
                }
            }
        }
        return null;
    }
}
