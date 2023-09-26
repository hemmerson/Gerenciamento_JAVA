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

@WebServlet(name = "BuscaReceita", value = "/buscareceita")
public class BuscarReceita extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/xml");
        request.setCharacterEncoding("UTF-8");

        // Hello
        PrintWriter out = response.getWriter();
        String busca = request.getParameter("nome");

        String caminho = getServletContext().getRealPath("/WEB-INF/receitasdosite.xml");

        Document doc = FabricaDeDocumento.geraDocumento(caminho);

        try {
            FiltraReceitas filtro = new FiltraReceitas(caminho);
            String texto = filtro.pegaPorNome(busca);
            out.print(texto);
        } catch (TransformerException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}