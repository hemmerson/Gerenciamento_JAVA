onload = function () {
    ajax("nomereceitas", function () {
        if (this.readyState == 4 && this.status == 200) {
            const doc = this.responseXML;
            listaMenu(doc);
        }
    });
}

function mostraNome(doc){
    document.getElementById("nome_receita").innerHTML = doc.getElementsByTagName("nome")[0].textContent;
}

function listaMenu(doc) {
    const receitas = doc.getElementsByTagName("nome");
    let texto = "";
    let nomeReceita = "";
    for (let receita of receitas) {

        texto += `<li><a href="#" onclick=preencheDados('buscareceita?nome=${encodeURI(receita.textContent)}')>${receita.textContent}</a></li>`
    }
    document.getElementById("receitas").innerHTML = texto;
}

function preencheDados(receita){
    let texto = "<h2 id=\"nome_receita\">Nome receita</h2>\n" +
        "                <hr>\n" +
        "                <h4>Ingredientes:</h4>\n" +
        "                <ul id=\"ingredientes\"></ul>\n" +
        "                <hr>\n" +
        "                <h4>Modo de Preparo:</h4>\n" +
        "                <ol id=\"modo_preparo\"></ol>\n" +
        "                <hr>\n" +
        "                <h4>Ilustrações:</h4>\n" +
        "                <div id=\"images\"></div>";
    document.getElementById("principal").innerHTML = texto;
    console.log(receita);
    ajax(receita, function () {
        if (this.readyState == 4 && this.status == 200) {
            const doc = this.responseXML;
            mostraNome(doc);
            mostraIngredientes(doc);
            mostraModoPreparo(doc);
            mostraImages(doc);
        }
    });
}

function mostraIngredientes(doc){
    const ingredientes = doc.getElementsByTagName("ingrediente");
    let texto = "";
    for(let ingrediente of ingredientes){
        texto += `<li>${ingrediente.textContent}</li>`
    }
    document.getElementById('ingredientes').innerHTML=texto;

}

function mostraImages(doc){
    const images = doc.getElementsByTagName("foto");
    let texto = "";
    for(let image of images){
        texto += `<img src='${image.textContent}'></li>`
    }
    document.getElementById('images').innerHTML=texto;

}

function mostraModoPreparo(doc){
    const modos = doc.getElementsByTagName("passo");
    let texto = "";
    for(let modo of modos){
        texto += `<li>${modo.textContent}</li>`
    }
    document.getElementById('modo_preparo').innerHTML=texto;

}

function buscar(){
    let pesquisa = document.getElementById("txt-pesquisar").value;
    console.log(`buscaingrediente?ingrediente=${pesquisa}`);
    ajax(`buscaingrediente?ingrediente=${pesquisa}`, function () {
        if (this.readyState == 4 && this.status == 200) {
            const doc = this.responseXML;
            const nomeReceitas = doc.getElementsByTagName("nome");
            let texto = "";
            if (nomeReceitas != null) {
                for (let nome of nomeReceitas) {
                    console.log(nome.firstChild.textContent);
                    texto += `<h1>${nome.firstChild.textContent}</h1><hr>`;
                }
            }
            document.getElementById("principal").innerHTML = texto;
        }
    });
}


function ajax(recurso, funcao) {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = funcao;
    xhr.open("get", recurso, true);
    xhr.send();
}