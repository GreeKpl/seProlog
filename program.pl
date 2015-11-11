:- dynamic
    prawda/1,
    falsz/1,
    nie_ma_choroby/1,
    choroba/1,
    lek_na_chorobe_ktorej_nie_mamy/1,
    lek_na_inna_chorobe/1,
    lek/1.


lek(gripex).
lek(aspiryna).
lek(antybiotyk).
lek(chinina).
lek('lek przeciwzapalny').
lek('oklad na noge').
lek('oklad z lodu').
lek('unieruchomienie').


lek_na_chorobe(gripex, grypa).
lek_na_chorobe(aspiryna, przeziebienie).
lek_na_chorobe(antybiotyk, odra).
lek_na_chorobe(chinina, malaria).
lek_na_chorobe('lek przeciwzapalny', 'zwyrodnienie stawow').
lek_na_chorobe('oklad na noge', 'zwichniecie stawu kolanowego').
lek_na_chorobe('oklad z lodu', 'brak dloni').
lek_na_chorobe('elastyczne unieruchomienie', 'skrecenie kostki').


start :- lek(R), propozycja(R).
propozycja(X) :- \+lek_na_chorobe_ktorej_nie_mamy(X), \+lek_na_inna_chorobe(X), przedstaw_propozycje(X), wyczysc_wiedze.
propozycja(_) :- powiedz_o_braku_propozycji, wyczysc_wiedze.
lek_na_chorobe_ktorej_nie_mamy(X) :- nie_ma_choroby(Y), lek_na_chorobe(X, Y).
lek_na_inna_chorobe(X) :- choroba(Y), \+lek_na_chorobe(X, Y).




nie_ma_choroby('zwichniecie stawu kolanowego') :- jest_objaw('brak nogi').
nie_ma_choroby('skrecenie kostki') :- jest_objaw('brak nogi').


choroba('skrecenie kostki') :- jest_objaw('bol promieniujacy stopy').
choroba('zwyrodnienie stawow') :- jest_objaw('bol kolana'), jest_objaw('bol lokcia').
choroba('zwichniecie stawu kolanowego') :- jest_objaw('bol nogi').
choroba('grypa') :- jest_objaw('wysoka temperatura').
choroba('malaria') :- jest_objaw('drgawki'), jest_objaw('wymioty'), jest_objaw('dreszcze').




jest_objaw(X) :- prawda(X), \+falsz(X).
jest_objaw(X) :- nieokreslony(X), czy(X), read(Odp),
	(Odp = 't' -> assertz(prawda(X));
		assertz(falsz(X)), fail).

nieokreslony(X) :- \+falsz(X), \+prawda(X).

czy(X) :- write('czy odczuwasz: '), write(X), write('? (t/n)'), nl.


przedstaw_propozycje(X) :- write('proponowany lek: '), write(X), nl.
powiedz_o_braku_propozycji :- write('nie umiem zaproponowac leku. Skontaktuj sie z lekarzem'), nl.

wyczysc_wiedze :- retractall(prawda(_)), retractall(falsz(_)).

