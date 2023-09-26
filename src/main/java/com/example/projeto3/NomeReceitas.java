package com.example.projeto3;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "NomeReceita", value = "/nomereceitas")
public class NomeReceitas extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/xml");
        request.setCharacterEncoding("UTF-8");

        // Hello
        PrintWriter out = response.getWriter();

        String caminho = getServletContext().getRealPath("/WEB-INF/receitasdosite.xml");

        Document doc = FabricaDeDocumento.geraDocumento(caminho);
        NodeList nomes = doc.getElementsByTagName("nome");
        String texto="<receitas>";
        try {
            for (int i = 0; i < nomes.getLength(); i++) {
                texto += "<nome>"+nomes.item(i).getFirstChild().getNodeValue()+"</nome>";
            }
            texto += "</receitas>";
            out.print(texto);
        } catch (DOMException e) {
            throw new RuntimeException(e);
        }
    }
}