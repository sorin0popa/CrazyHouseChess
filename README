Pentru rularea xboardului si a engineului in modul debug:
  make  && xboard  -fcp "make run" -debug

    Pentru implementarea functiei euristice am folosit algoritmul negamax cu alpha-beta pruning pe baza
valorii materiale a pieselor si a pozitiei lor de pe tabla de sah, dar si in functie de piesele
de drop-in ale albului si negrului. De asemenea, am acordat si o prioritate mutarilor in functie
daca este o captura sau o mutare in fata sau in spate, deoarece in general este mai bine sa capturezi o piesa si
am sortat mutarile in functie de aceasta prioritate. Pentru a imbunatati performanta, am folosit
matrici pentru pozitiile fiecarei piese inspirate din : https://www.chessprogramming.org/Simplified_Evaluation_Function,
cu mici ajustari.
    In aceasta etapa, am ales sa nu mai folosim functia de copyBoard() deoarece era foarte ineficienta
si am implementat functiile : reverseChangeBoard() care da reverse la mutarea anterioara, indiferent
daca este o mutare speciala sau o mutare normala si reverseAddToBoard() care da reverse la ultimul
drop-in, iar pentru acestea am implementat clasele GameState si GameMove care retin ultima mutare
a negrului sau a albului, pieceBesides() care calculeaza cate piese sunt in jurul unei piese, iar mai
tarziu o folosim pentru a afla cate piese sunt in jurul regelui advers.
    Strategii folosite:
    - se incearca capturarea cat mai multor piese;
    - se incearca punerea pieselor de drop-in astfel incat acestea sa dea sah regelui advers in functie de
cate piese are in jurul lui (functia pieceBesides din clasa King), daca aceste piese sunt suficient de mult aparate de alte piese 
(functia nr_def_greater_nr_attack din SidePiece);
    - la drop-in, se favorizeaza piesele cu cea mai mare valoare strategica si de pozitia strategica a piesei si de pozitia regilor;
    - am oferit valori materiale fiecarei piese, dar si valori strategice in functie de pozitia acestora.

Prioritatile le-am ales in urmatorul mod:

DROP_IN: variaza intre -2.0 si 2.0, dar variaza in functie de pozitia strategica a piesei si de pozitia regilor
Capturi: variaza intre 0.90 si 1.0, in functie de valoarea materiala a piese
Mutari in fata: intre 0.4 si 0.5
Mutari in spate: intre 0.1 si 0.35

Acestea sunt utile pentru a maximiaza sansele sa alegem cea mai buna mutare din prima, reducand astfel timpul petrecut in aflarea solutiei 
cu negamax.

Pentru evaluarea tablei, am adunat materialul de pe tabla cu materialul din afara tablei injumatatit si cu valoarea pozitiei strategica a
fiecarei piese (un numar foarte mic, astfel incat valorea materiala de pe tabla sa dicteze scorul tablei, apoi cel din afara tablei,
si la urma pozitia strategica a piesei).

Alte surse de inspiratie :
https://www.freecodecamp.org/news/simple-chess-ai-step-by-step-1d55a9266977/
https://www.chessprogramming.org/Main_Page
