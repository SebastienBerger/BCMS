/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    function loadContent(file,id) {
        var xhr = new XMLHttpRequest();
        // On souhaite juste récupérer le contenu du fichier, la méthode GET suffit amplement :
        xhr.open('GET', file,false);
        xhr.addEventListener('readystatechange', function() { // On gère ici une requête asynchrone
            console.log(window.parent.document.getElementById(id));
            if (xhr.readyState === 4 && xhr.status === 200) { // Si le fichier est chargé sans erreur
                console.log(window.parent.document.getElementById(id));
                window.parent.document.getElementById(id).innerHTML = xhr.responseText; // Et on affiche !
            }
        }, false);
        xhr.send(null); // La requête est prête, on envoie tout !
    } 
    function loadIframe(file,id) {
        var xhr = new XMLHttpRequest();
        // On souhaite juste récupérer le contenu du fichier, la méthode GET suffit amplement :
        xhr.open('GET', file,false);
        xhr.addEventListener('readystatechange', function() { // On gère ici une requête asynchrone
            console.log(window.parent.document.getElementById(id));
            if (xhr.readyState === 4 && xhr.status === 200) { // Si le fichier est chargé sans erreur
                console.log(window.parent.document.getElementById(id));
                window.parent.document.getElementById(id).innerHTML = "<iframe src='"+file+"'></iframe>"; // Et on affiche !
            }
        }, false);
        xhr.send(null); // La requête est prête, on envoie tout !
    } 
    
