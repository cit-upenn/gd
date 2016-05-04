1. Project Name:
--------------------------
Good Dictionary (GD)

2. Project Description:
--------------------------
GD is a versatile English dictionary. In Good Dictionary, users can search for the definitions, the Chinese translation, and the thesaurus (synonyms, antonyms, etc.) of an English vocabulary. For the definition part, we parsed a dictionary txt file to populate our local sqlite database. Both the Thesaurus and the English-Chinese translation come from external APIs (Merriam-Webster Thesaurus API and Yandex API). In addition, our dictionary possesses two impressive features: 

  i) predictive text/autocompletion- when the user starts typing in the search field, we will display vocabulary predictions in the left sidebar. To lookup a word, the user can either select a word from the prediction section or hit enter; 
  
  ii) word notebook- the user will be able to add unfamiliar vocabularies to the in-dictionary notebook for later reference. The saved notes are also stored in our database. 

3. Technologies applied:
--------------------------
- Trie Tree
- SQLite DB
- 2 External APIs (Merriam-Webster Thesaurus API and Yandex Translation API)
- Java GUI
- Model View Controller (MVC) Design Pattern
- Formal Design (CRC Diagram and Sequence Diagrams)

4. Data Structures utilized:
--------------------------
1)  Tree   2)  ArrayList   3)  HashMap   4)  HashSet

5. General Work Breakdown:
--------------------------
Qingxiao Dong -
SQLite Database setup, Trie Tree (major data structure), Merriam-Webster Dictionary API connection.

Jue Liu -
Model, View, Controller (GUI).

Yujie (Lydia) Li -
Parsers (data-cleaning), Yandex Translation API connection.

6. User Manual: 
--------------------------
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page1.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page2.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page3.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page4.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page5.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page6.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page7.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page8.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page9.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page10.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page11.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page12.png "")
![alt text](https://github.com/cit-upenn/gd/blob/master/GDUserManual/file-page13.png "")
