/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


    function loadContent(file,id) {
        var xhr = new XMLHttpRequest();
        // On souhaite juste récupérer le contenu du fichier, la méthode GET suffit amplement :
        xhr.open('GET', file);
        xhr.addEventListener('readystatechange', function() { // On gère ici une requête asynchrone
            if (xhr.readyState === 4 && xhr.status === 200) { // Si le fichier est chargé sans erreur
                document.getElementById(id).innerHTML = xhr.responseText; // Et on affiche !
            }
        }, false);
        xhr.send(null); // La requête est prête, on envoie tout !
    } 
    (function() { // Comme d'habitude, une IIFE pour éviter les variables globales

        var inputs = document.getElementsByTagName('a'),
            inputsLen = inputs.length;

        for (var i = 0 ; i < inputsLen ; i++) {
            
            inputs[i].addEventListener('click', function() {
                console.log(this.id);
                loadContent("../BCMS/"+this.id,this.title); // À chaque clique, un fichier sera chargé dans la page
            }, false);

        }

    })();