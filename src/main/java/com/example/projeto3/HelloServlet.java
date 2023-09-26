package com.example.projeto3;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        String caminho=getServletContext().getRealPath("/WEB-INF/receitasdosite.xml");

            Document doc=FabricaDeDocumento.geraDocumento(caminho);
            Element raiz=(Element) doc.getFirstChild();  //doc.getDocumentElement();

//            Node irmao=raiz.getElementsByTagName("cabecalho").item(0);

            out.print("<p>A raiz é: "+ raiz.getNodeName());

           NodeList filhos= raiz.getChildNodes();
           for (int i = 0; i < filhos.getLength(); i++){
               if(filhos.item(i).getNodeType()==Node.ELEMENT_NODE) {
                   out.print("<p>Filho " + i + "º é: " + filhos.item(i).getNodeName());
               }
           }
//            int tam=filhos.getLength();
//            out.print("<ul>");
//        boolean primeiro=true;
//            for(int i=0;i<tam;i++)
//            {
//                Node filho= filhos.item(i);
//
//                if(filho.getNodeType()==Node.ELEMENT_NODE) {
//                    if(primeiro)
//                    {
//                        Element filhoElemento=(Element)filho;
//                        filhoElemento.setAttribute("nome","José Silva");
//                    }
//                    out.print("<li>" + filho.getNodeName() + ": " + filho.getFirstChild().getNodeValue());
//                    primeiro=false;
//                }
//            }
//            out.print("</ul>");
//            out.print("<p>"+raiz.getElementsByTagName("de").item(0).getAttributes().getNamedItem("nome").getNodeValue());
//            out.print("<p>"+raiz.getElementsByTagName("de").item(0).getAttributes().item(0).getNodeValue());
//            out.print("<p>"+((Element)raiz.getElementsByTagName("de").item(0)).getAttribute("nome"));
//
//        NamedNodeMap atributos=raiz.getAttributes();
//        tam=atributos.getLength();
//        out.print("<ul>");
//        for(int i=0;i<tam;i++)
//        {
////            Attr atributo=(Attr)atributos.item(i);
////            out.print("<li>"+atributo.getName()+": "+atributo.getValue());
//            Node atributo=atributos.item(i);
//            out.print("<li>"+atributo.getNodeName()+": "+atributo.getNodeValue());
//        }

        out.print("</ul>");
        out.println("</body></html>");
    }

}