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

        PrintWriter out = response.getWriter();

        String busca = request.getParameter("nome");
        String caminho = getServletContext().getRealPath("/WEB-INF/receitasdosite.xml");

        if (busca != null) {
            try {
                FiltraReceitas receitas = new FiltraReceitas(caminho);
                out.print(receitas.pegaReceita(busca));
            } catch (Exception e) {
                out.print("Receita não encontrada");
            }
        } else
            throw new RuntimeException("Você precisa informar o parâmetro nome.");
    }
}