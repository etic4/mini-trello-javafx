# Projet ANC3 2021 - Trello - Groupe a03

                                                                    Etienne Carlier  
                                                                    Denis Capece

## Notes de livraison itération 1
* Pas de bug connu;
* Toutes les fonctionnalités sont implémentées;
* Choix de conception:
    + Dans le _model_: 
        - Utilisation d'interfaces, de classe abstraite et de génériques pour factoriser les méthodes.
        - Utilisation d'une façade (_BoardFacade_).
        - Implémentation des _ObservableList\<Column>_ dans _Board_ et _ObservableList\<Card>_ dans _Column_.
        - Implémentation des _BooleanProperties_ utiles à l'activation des différents boutons de déplacement.
        - Enum pour les directions de la méthode _Move()_ dans _BoardFacade_. 
  + Dans les _views_:
    - Tous les éléments implémentés via JavaFx (pas de .fxml).
    - Factorisation de la méthode de configuration des boutons.

    

## Notes de livraison itération 2
* Pas de bug connu
* Toutes les fonctionnalités sont implémentées
* Choix de conception concernant les instructions pour l'itération 2:
    * Un manager de commandes singleton
    * Une classe Memento par objet, paramétrisable à l'instanciation en fonction du type de commande réalisée
    * Pas de clonage en profondeur
* refactorisation du modèle pour mieux gérer la visibilité de ses éléments
* refactorisation assez profonde des view et view models:
    * Le choix a été fait de faire systématiquement appel à des méthode du view model en réponse à des demandes d'action de l'utilisateur ie lors de l'édition d'un titre, du move d'un carte, etc.
    * Un ensemble TrelloModel / TrelloViewModel / trelloViewModel a été créé, notamment pour contenir la barre de menu
    * TrelloViewModel est un singleton pour faciliter les bindings avec des cartes par exemple
    * Les bouttons sont maintenant une classe qui hérite de Button
    * Idem pour le menu contextuel
    * La classe EditableLabel a été refactorisée et fire maintenant un event lorsque l'edit du texte est terminé
    
    
## Notes de livraison itération 3

## Notes de livraison itération 4


