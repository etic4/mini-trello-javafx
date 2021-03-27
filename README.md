# Projet ANC3 2021 - Trello - Groupe A10
## Notes de livraison itération 1 (Groupe A03)
        Etienne Carlier  
        Denis Capece
* Pas de bug connu;
* Toutes les fonctionnalités sont implémentées;
* Choix de conception:
    + Dans _le model_: 
        - Utilisation d'interfaces, de classe abstraite et de génériques pour factoriser les méthodes.
        - Utilisation d'une façade (_BoardFacade_).
        - Implémentation des _ObservableList\<Column>_ dans _Board_ et _ObservableList\<Card>_ dans _Column_.
        - Implémentation des _BooleanProperties_ utiles à l'activation des différents boutons de déplacement.
        - Enum pour les directions de la méthode _Move()_ dans _BoardFacade_. 
  + Dans les _views_:
    - Tous les éléments implémentés via JavaFx (pas de .fxml).
    - Factorisation de la méthode de configuration des boutons.
    

## Notes de livraison itération 2 
        Etienne Carlier
* Pas de bug connu
* Toutes les fonctionnalités sont implémentées
* Choix de conception concernant les instructions pour l'itération 2:
    * Un _CommandManager_ est un singleton
    * Une classe _Memento_ par objet, paramétrisable à l'instanciation en fonction du type de commande réalisée
    * Pas de clonage en profondeur mais des commandes inversée pour restaurer l'état antérieur à partir de références aux objets
* Un grand nombre de changements ont été apportés par rapport à l'itération 1:
    * refactorisation du modèle (remplacement des interfaces et des méthodes par défault par des classe) pour mieux gérer la visibilité de ses éléments et implémenter correctement le _pattern facade_
    * refactorisation assez profonde des view et view models en vue de les clarifier et de les simplifier:
        * utlilisation d'une feuille de style css pour l'essentiel du styling des élements
        * Le choix a été fait de faire systématiquement appel à des méthode du view model par la view en réponse à des action de l'utilisateur (ie lors de l'édition d'un titre, du move d'un carte, etc..)
        * Un ensemble TrelloModel / TrelloViewModel / trelloViewModel a été créé, notamment pour contenir la barre de menu principale mais aussi pour rationnaliser la création et le seed data du board
        * TrelloViewModel est un singleton pour faciliter les bindings des éléments du menu principal
        * Les boutons sont maintenant une classe qui hérite de Button
        * Le menu contextuel (delete) est maintenant une classe fille de _ContexMenu_
        * La classe _EditableLabel_ a été refactorisée et fire maintenant un event lorsque l'edit du texte est terminé
        
    
## Notes de livraison itération 3

## Notes de livraison itération 4


