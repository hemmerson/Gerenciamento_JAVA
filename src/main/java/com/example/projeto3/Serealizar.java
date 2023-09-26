package com.example.projeto3;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "serealizar", value = "/serealizar")
public class Serealizar extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/xml");

        // Hello
        PrintWriter out = response.getWriter();

        String caminho = getServletContext().getRealPath("/WEB-INF/receitasdosite.xml");

        Document doc = FabricaDeDocumento.geraDocumento(caminho);

        try {
            FiltraReceitas filtro = new FiltraReceitas(caminho);
            String texto = filtro.serealizar(doc);
            out.print(texto);
        } catch (TransformerException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }

    }

}